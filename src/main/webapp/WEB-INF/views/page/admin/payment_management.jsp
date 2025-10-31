<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/payment_management.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="payment"/>
</jsp:include>


<div id="pageRoot" data-context-path="${pageContext.request.contextPath}">
    <div class="admin-content-container">
        <div class="admin-header-bar">
            <h2 class="admin-page-title">결제 관리</h2>
            <button id="downloadPdfBtn" class="btn-download">PDF로 저장</button>
        </div>

        <!-- ✅ 검색 필터 영역 (추가 가능) -->
        <div class="payment-search-bar">
            <select id="searchType">
                <option value="">전체</option>
                <option value="paymentUid">결제번호</option>
                <option value="lectureTitle">강의명</option>
                <option value="nickname">결제자(닉네임)</option>
            </select>
            <select id="isRefunded">
                <option value="">환불여부(전체)</option>
                <option value="1">환불완료</option>
                <option value="0">결제완료</option>
            </select>
            <select id="orderBy">
                <option value="">정렬기준(기본)</option>
                <option value="date">결제일순(최신)</option>
                <option value="amount">금액순(높은 금액)</option>
                <option value="refund">환불우선</option>
            </select>

            <input type="text" id="keyword" placeholder="검색어 입력"/>
            <button id="paymentSearchBtn">검색</button>
        </div>

        <!-- 결제 내역 테이블 -->
        <div class="payment-table-wrapper">
            <table class="payment-table">
                <thead>
                <tr>
                    <th>No</th>
                    <th>결제번호</th>
                    <th>강의명</th>
                    <th>결제자</th>
                    <th>결제금액</th>
                    <th>결제일</th>
                    <th>결제상태</th>
                    <th>관리</th>
                </tr>
                </thead>
                <tbody id="paymentTableBody">
                <!-- JS 렌더링 -->
                </tbody>
            </table>
        </div>
    </div>
    <div id="pagination" class="pagination"></div>

    <!-- 환불 모달 -->
    <div id="refundModal" class="refund-modal" style="display:none;">
        <div class="modal-content">
            <h3>환불 사유 입력</h3>
            <textarea id="refundReasonInput" placeholder="최대 15자까지 입력 가능합니다."></textarea>
            <div class="modal-actions">
                <button id="confirmRefundBtn" class="btn-confirm">환불하기</button>
                <button id="cancelRefundBtn" class="btn-cancel">취소</button>
            </div>
        </div>
    </div>
</div>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        console.log("[INIT] 결제 관리 페이지 로드");

        let currentPage = 1;
        const pageSize = 10;
        const tbody = document.getElementById("paymentTableBody");
        const pagination = document.getElementById("pagination");
        const contextPath = document.getElementById("pageRoot")?.dataset.contextPath || "";

        // [1] 메인 함수 - 결제 내역 불러오기
        function loadPayments(page = 1, searchType = "", keyword = "") {
            if (!page || isNaN(page) || page < 1) page = 1;

            const params = new URLSearchParams({page: page, size: pageSize});

            if (searchType) params.append("filter", searchType);
            if (keyword) params.append("keyword", keyword);

            const isRefundedSelect = document.getElementById("isRefunded");
            const isRefundedValue = isRefundedSelect ? isRefundedSelect.value : "";

            if (isRefundedValue === "1") params.append("isRefunded", "true");
            else if (isRefundedValue === "0") params.append("isRefunded", "false");

            const orderByValue = document.getElementById("orderBy")?.value || "";
            if (orderByValue) params.append("orderBy", orderByValue);

            const url = "/admin/api/payments?" + params.toString();
            console.log("[FETCH] " + url);

            fetch(url, {
                method: "GET",
                headers: {
                    "X-Requested-From": window.location.pathname + window.location.search
                }
            })
                .then(async (res) => {
                    if (!res.ok) throw new Error("서버 오류 (" + res.status + ")");
                    return await res.json();
                })
                .then((json) => {
                    if (!json.success) throw new Error(json.message || "요청 처리 오류");
                    const raw = json.data || {};
                    const payments = Array.isArray(raw.data) ? raw.data : [];
                    const totalPages = raw.totalPage || 1;

                    if (page > totalPages) {
                        loadPayments(totalPages);
                        return;
                    }

                    if (!payments.length) {
                        const message = keyword
                            ? "'" + keyword + "'에 해당하는 결제 정보가 없습니다."
                            : "결제 정보가 없습니다.";
                        tbody.innerHTML =
                            "<tr><td colspan='8' style='text-align:center; color:gray;'>" +
                            message +
                            "</td></tr>";
                        return;
                    }

                    currentPage = page;
                    renderPaymentTable(payments);
                    renderPagination(totalPages);
                })
                .catch((err) => {
                    console.error("[ERROR] 결제 데이터 로드 실패:", err);
                    tbody.innerHTML =
                        "<tr><td colspan='8' style='text-align:center; color:gray;'>❌ 데이터가 없습니다. " +
                        err.message +
                        "</td></tr>";
                });
        }

        // [*] 필터 change 이벤트
        const refundSelect = document.getElementById("isRefunded");
        if (refundSelect) {
            refundSelect.addEventListener("change", () => {
                const searchType = document.getElementById("searchType")?.value || "";
                const keyword = document.getElementById("keyword")?.value.trim() || "";
                loadPayments(1, searchType, keyword);
            });
        }

        const orderSelect = document.getElementById("orderBy");
        if (orderSelect) {
            orderSelect.addEventListener("change", () => {
                const searchType = document.getElementById("searchType")?.value || "";
                const keyword = document.getElementById("keyword")?.value.trim() || "";
                loadPayments(1, searchType, keyword);
            });
        }

        // [2] 테이블 렌더링
        function renderPaymentTable(payments) {
            if (!payments || payments.length === 0) {
                const searchType = document.getElementById("searchType")?.value || "";
                const keyword = document.getElementById("keyword")?.value.trim() || "";
                const isRefunded = document.getElementById("isRefunded")?.value || "";
                const orderBy = document.getElementById("orderBy")?.value || "";

                const filterLabel = {
                    "paymentUid": "결제번호",
                    "lectureTitle": "강의명",
                    "nickname": "결제자",
                    "": "전체"
                }[searchType] || "";

                const refundLabel = {
                    "1": "환불완료",
                    "0": "결제완료",
                    "": "전체"
                }[isRefunded] || "";

                const orderLabel = {
                    "date": "결제일순",
                    "amount": "금액순",
                    "refund": "환불우선",
                    "": "기본 정렬"
                }[orderBy] || "";

                const conditions = [filterLabel, refundLabel, orderLabel, keyword ? '"' + keyword + '"' : ""].filter(Boolean);
                const message = conditions.length > 0
                    ? conditions.join(" ") + "에 해당하는 회원이 없습니다."
                    : "조회된 회원이 없습니다.";

                tbody.innerHTML =
                    "<tr><td colspan='8' style='text-align:center; color:#888; padding:20px; font-size:16px; background:#fff'>" +
                    message +
                    "</td></tr>";
                console.log("[EMPTY]", message);
                return;
            }

            tbody.innerHTML = "";

            payments.forEach((p, idx) => {
                const refundStatus = p.isRefunded
                    ? "<span class='status-refunded'>환불완료</span>"
                    : "<span class='status-paid'>결제완료</span>";

                const refundButton = p.isRefunded
                    ? "<button class='btn-refund' disabled style='opacity:0.5; cursor:not-allowed;'>환불완료</button>"
                    : "<button class='btn-refund' data-id='" + p.paymentId + "'>환불</button>";

                const tr = document.createElement("tr");
                tr.innerHTML =
                    "<td>" + ((currentPage - 1) * pageSize + idx + 1) + "</td>" +
                    "<td>" + (p.paymentUid || "-") + "</td>" +
                    "<td>" + (p.lectureTitle || "-") + "</td>" +
                    "<td>" + (p.nickname || "-") + "</td>" +
                    "<td>" + (p.paidAmount ? p.paidAmount.toLocaleString() : "0") + "원</td>" +
                    "<td>" + (p.cdate || "-") + "</td>" +
                    "<td>" + refundStatus + "</td>" +
                    "<td>" + refundButton + "</td>";
                tbody.appendChild(tr);
            });
        }

        // [3] 페이지네이션
        function renderPagination(totalPages) {
            totalPages = Math.ceil(totalPages);
            if (totalPages < 1) totalPages = 1;
            if (currentPage > totalPages) currentPage = totalPages;

            pagination.innerHTML = "";
            if (!totalPages || totalPages <= 0) return;

            const maxVisible = 5;
            const currentGroup = Math.ceil(currentPage / maxVisible);
            const start = (currentGroup - 1) * maxVisible + 1;
            const end = totalPages < start ? totalPages : Math.min(totalPages, start + maxVisible - 1);

            const prev = document.createElement("button");
            prev.textContent = "◀";
            prev.className = "page-btn " + (currentGroup === 1 ? "disabled" : "");
            prev.onclick = () => {
                if (currentGroup > 1) {
                    const prevGroupLastPage = (currentGroup - 2) * maxVisible + 1;
                    loadPayments(prevGroupLastPage);
                }
            };
            pagination.appendChild(prev);

            for (let i = start; i <= end; i++) {
                const btn = document.createElement("button");
                btn.textContent = i;
                btn.className = "page-btn " + (i === currentPage ? "active" : "");
                btn.onclick = () => {
                    if (i <= totalPages) loadPayments(i);
                };
                pagination.appendChild(btn);
            }

            const next = document.createElement("button");
            next.textContent = "▶";
            next.className = "page-btn " + (end >= totalPages ? "disabled" : "");
            next.onclick = () => {
                if (end < totalPages) {
                    const nextGroupFirstPage = end + 1;
                    loadPayments(nextGroupFirstPage);
                }
            };
            pagination.appendChild(next);
        }

        // [4] 검색 버튼
        const searchBtn = document.getElementById("paymentSearchBtn");
        if (searchBtn) {
            searchBtn.addEventListener("click", () => {
                const searchType = document.getElementById("searchType")?.value || "";
                const keyword = document.getElementById("keyword")?.value.trim() || "";
                loadPayments(1, searchType, keyword);
            });
        }

        // [5] 엔터 검색
        const keywordInput = document.getElementById("keyword");
        if (keywordInput) {
            keywordInput.addEventListener("keypress", (e) => {
                if (e.key === "Enter") {
                    e.preventDefault();
                    searchBtn.click();
                }
            });
        }

        // 초기 실행
        loadPayments(currentPage);

        // [6] PDF 다운로드
        function renderPdf() {
            console.log("[PDF] 결제 내역 PDF 다운로드 시작");

            const searchType = document.getElementById("searchType")?.value || "";
            const keyword = document.getElementById("keyword")?.value.trim() || "";
            const isRefunded = document.getElementById("isRefunded")?.value || "";
            const orderBy = document.getElementById("orderBy")?.value || "";

            const params = new URLSearchParams();
            if (searchType) params.append("filter", searchType);
            if (keyword) params.append("keyword", keyword);
            if (isRefunded !== "") params.append("isRefunded", isRefunded === "1" ? true : false);
            if (orderBy) params.append("orderBy", orderBy);

            fetch("/admin/api/payments/export/pdf?" + params.toString(), {
                method: "GET",
                headers: {
                    "X-Requested-From": window.location.pathname + window.location.search
                }
            })
                .then((res) => {
                    if (!res.ok) throw new Error("HTTP 상태 코드 " + res.status);
                    return res.blob();
                })
                .then((blob) => {
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement("a");
                    a.href = url;
                    a.download = "결제내역.pdf";
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                    window.URL.revokeObjectURL(url);
                    console.log("[PDF] 결제 내역 PDF 다운로드 완료 ✅");
                })
                .catch((err) => {
                    console.error("[ERROR] 결제 내역 PDF 다운로드 실패:", err);
                    alert("❌ PDF 다운로드 중 오류가 발생했습니다.");
                });
        }

        const pdfBtn = document.getElementById("downloadPdfBtn");
        if (pdfBtn) {
            pdfBtn.addEventListener("click", renderPdf);
        }

        // [7] 환불 모달 이벤트
        let currentRefundPaymentId = null;
        tbody.addEventListener("click", (e) => {
            if (e.target.classList.contains("btn-refund")) {
                const paymentId = e.target.dataset.id;
                currentRefundPaymentId = paymentId;
                document.getElementById("refundModal").style.display = "flex";
            }
        });

        document.getElementById("cancelRefundBtn").addEventListener("click", () => {
            document.getElementById("refundModal").style.display = "none";
            document.getElementById("refundReasonInput").value = "";
            currentRefundPaymentId = null;
        });

        document.getElementById("confirmRefundBtn").addEventListener("click", async () => {
            const reason = document.getElementById("refundReasonInput").value.trim();
            if (!reason) return alert("환불 사유를 입력해주세요.");
            if (reason.length > 15) return alert("15자 이내로 입력해주세요.");
            if (!confirm("정말 환불 처리하시겠습니까?")) return;

            try {
                const formData = new URLSearchParams();
                formData.append("refundReason", reason);

                const res = await fetch("/admin/api/payments/" + currentRefundPaymentId + "/refund", {
                    method: "PATCH",
                    headers: {
                        "X-Requested-From": window.location.pathname + window.location.search
                    },
                    body: formData
                });

                const result = await res.json();
                if (res.ok) {
                    alert("✅ 환불이 완료되었습니다.");
                    document.getElementById("refundModal").style.display = "none";
                    document.getElementById("refundReasonInput").value = "";
                    loadPayments(currentPage);
                } else {
                    alert("❌ " + (result.message || "환불 처리 실패"));
                }
            } catch (err) {
                console.error(err);
                alert("서버 오류가 발생했습니다.");
            }
        });
    });
</script>
