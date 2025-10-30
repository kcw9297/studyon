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

        const params = new URLSearchParams({ page, size: pageSize });
        // 검색 조건
        if (searchType) params.append("filter", searchType);
        if (keyword) params.append("keyword", keyword);

        // 환불 여부
        const isRefundedSelect = document.getElementById("isRefunded");
        const isRefundedValue = isRefundedSelect?.value;

        // 환불 여부 판단
        if (isRefundedValue === "1") params.append("isRefunded", "true");
        else if (isRefundedValue === "0") params.append("isRefunded", "false");

        //  정렬 기준 추가
        const orderByValue = document.getElementById("orderBy")?.value || "";
        if (orderByValue) params.append("orderBy", orderByValue);

        const url = `/admin/api/payments?${params.toString()}`;
        console.log(`[FETCH] ${url}`);

        fetch(url, {
            method: "GET",
            headers: {
                "X-Requested-From": window.location.pathname + window.location.search,
            },
        })
            .then(async (res) => {
                if (!res.ok) throw new Error(`서버 오류 (${res.status})`);
                return await res.json();
            })
            .then((json) => {
                if (!json.success) throw new Error(json.message || "요청 처리 오류");
                const raw = json.data || {};
                const payments = Array.isArray(raw.data) ? raw.data : [];
                const totalPages = raw.totalPage || 1;

                // ✅ 페이지 범위를 초과했을 경우, 마지막 페이지로 자동 이동
                if (page > totalPages) {
                    loadPayments(totalPages);
                    return;
                }

                if (!payments.length) {
                    const message = keyword
                    ? `'${keyword}'에 해당하는 결제 정보가 없습니다.`
                    : `결제 정보가 없습니다.`;
                    tbody.innerHTML = `
                        <tr>
                            <td colspan="8" style="text-align:center; color:gray;">
                                ${message}
                            </td>
                        </tr>`;
                    return;
                }

                currentPage = page;

                renderPaymentTable(payments);
                renderPagination(totalPages);
            })
            .catch((err) => {
                console.error("[ERROR] 결제 데이터 로드 실패:", err);
                tbody.innerHTML = `
                    <tr><td colspan="8" style="text-align:center; color:gray;">❌ 데이터가 없습니다. ${err.message}</td></tr>`;
            });
    }


    // [*] 필터 change 이벤트
    // 환불여부 필터 change 이벤트
    const refundSelect = document.getElementById("isRefunded");
    if (refundSelect) {
        refundSelect.addEventListener("change", () => {
            const searchType = document.getElementById("searchType")?.value || "";
            const keyword = document.getElementById("keyword")?.value.trim() || "";
            loadPayments(1, searchType, keyword);
        });
    }

    // 정렬 기준 change 이벤트
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

        const paymentUid = document.getElementById("paymentUid")?.value || "";
        const lectureTitle = document.getElementById("lectureTitle")?.value || "";
        const nickname = document.getElementById("nickname")?.value.trim() || "";

        if (!payments || payments.length === 0) {

            // ?.value : 객체가 존재하면 value 속성에 접근하고, 존재하지 않으면 undefined를 반환
            // 이름 매핑
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

            // 조건 문구 조합
            const conditions = [filterLabel, refundLabel, orderLabel, keyword && `"${keyword}"`].filter(Boolean);
            
            // 최종 문구
            const message = conditions.length > 0
                ? `${conditions.join(" ")}에 해당하는 회원이 없습니다.`
                : "조회된 회원이 없습니다.";


            // 최종 렌더링(해당 회원 없을 시 뜨는 문구)
            tbody.innerHTML = `
            <tr><td colspan="8" style="
                        text-align:center;
                        color: #888;
                        padding: 20px;
                        font-size: 16px;
                        background: #fff
                    ">
                    ${message}
                </td></tr>
            `;
            console.log("[EMPTY]", message);
            return;
        }

        tbody.innerHTML = "";

        payments.forEach((p, idx) => {
            const refundStatus = p.isRefunded
                ? `<span class="status-refunded">환불완료</span>`
                : `<span class="status-paid">결제완료</span>`;

            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${(currentPage - 1) * pageSize + idx + 1}</td>
                <td>${p.paymentUid || "-"}</td>
                <td>${p.lectureTitle || "-"}</td>
                <td>${p.nickname || "-"}</td>
                <td>${p.paidAmount?.toLocaleString() || "0"}원</td>
                <td>${p.cdate || "-"}</td>
                <td>${refundStatus}</td>
                <td>
                    <a href="${contextPath}/admin/payment/detail/${p.paymentId}" 
                       class="management-button">상세보기</a>
                </td>
            `;
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

        // 이전 버튼
        const prev = document.createElement("button");
        prev.textContent = "◀";
        prev.className = `page-btn ${currentGroup === 1 ? "disabled" : ""}`;
        prev.onclick = () => {
            if (currentGroup > 1) {
                const prevGroupLastPage = (currentGroup - 2) * maxVisible + 1;
                loadPayments(prevGroupLastPage);
            }
        };
        pagination.appendChild(prev);

        // 페이지 번호
        for (let i = start; i <= end; i++) {
            const btn = document.createElement("button");
            btn.textContent = i;
            btn.className = `page-btn ${i === currentPage ? "active" : ""}`;
            pagination.appendChild(btn);

            btn.onclick = () => {
                if (i <= totalPages) loadPayments(i);
            };
        }

        // ▶ 다음 그룹 버튼
        const next = document.createElement("button");
        next.textContent = "▶";
        next.className = `page-btn ${end >= totalPages ? "disabled" : ""}`;
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


    // [6] PDF 출력 함수
    function renderPdf() {
        console.log("[PDF] 결제 내역 PDF 다운로드 시작");

        // [1] 현재 화면 필터 상태 읽기
        const searchType = document.getElementById("searchType")?.value || "";
        const keyword = document.getElementById("keyword")?.value.trim() || "";
        const isRefunded = document.getElementById("isRefunded")?.value || "";
        const orderBy = document.getElementById("orderBy")?.value || "";

        // [2] 쿼리 파라미터 구성
        const params = new URLSearchParams();
        if (searchType) params.append("filter", searchType);
        if (keyword) params.append("keyword", keyword);
        if (isRefunded !== "") params.append("isRefunded", isRefunded === "1" ? true : false);
        if (orderBy) params.append("orderBy", orderBy);

        // [3] PDF 요청 (GET)
        fetch(`/admin/api/payments/export/pdf?${params.toString()}`, {
            method: "GET",
            headers: {
                "X-Requested-From": window.location.pathname + window.location.search,
            },
        })
            .then((res) => {
                if (!res.ok) throw new Error(`HTTP 상태 코드 ${res.status}`);
                return res.blob(); // PDF는 바이너리 형태로 받기
            })
            .then((blob) => {
                // [4] Blob → 다운로드 처리
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement("a");
                a.href = url;
                a.download = "결제내역.pdf"; // 저장될 파일명
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

    // [6-*] PDF 버튼 클릭 이벤트 연결
    const pdfBtn = document.getElementById("downloadPdfBtn");
    if (pdfBtn) {
        pdfBtn.addEventListener("click", () => {
            renderPdf();
        });
    }

});
