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
        const isRefunded = document.getElementById("isRefunded")?.value;
        if (isRefunded !== "") params.append("isRefunded", isRefunded === "1" ? true : false);

        // 정렬 조건 추가
        const orderSelect = document.getElementById("orderBy");
        if (orderSelect) {
            orderSelect.addEventListener("change", () => {
                const searchType = document.getElementById("searchType")?.value || "";
                const keyword = document.getElementById("keyword")?.value.trim() || "";
                loadPayments(1, searchType, keyword);
            });
        }


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
                    renderPagination(1);
                    return;
                }

                currentPage = raw.currentPage || 1;

                renderPaymentTable(payments);
                renderPagination(totalPages);
            })
            .catch((err) => {
                console.error("[ERROR] 결제 데이터 로드 실패:", err);
                tbody.innerHTML = `
                    <tr><td colspan="8" style="text-align:center; color:gray;">❌ 데이터가 없습니다. ${err.message}</td></tr>`;
                renderPagination(1);
            });
    }

    // [2] 테이블 렌더링
    function renderPaymentTable(payments) {
        tbody.innerHTML = "";
        payments.forEach((p, idx) => {
            const refundStatus = p.isRefunded
                ? `<span class="status-refunded">환불완료</span>`
                : `<span class="status-paid">정상결제</span>`;

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
        pagination.innerHTML = "";

        if (!totalPages || totalPages <= 0) return;

        const maxVisible = 5;
        const currentGroup = Math.ceil(currentPage / maxVisible);
        const start = (currentGroup - 1) * maxVisible + 1;
        const end = Math.min(totalPages, start + maxVisible - 1);

        // 이전 버튼
        const prev = document.createElement("button");
        prev.textContent = "◀";
        prev.className = `page-btn ${currentGroup === 1 ? "disabled" : ""}`;
        prev.onclick = () => {
            if (currentGroup > 1) {
                const prevGroupLastPage = (currentGroup - 2) * maxVisible + 1;
                loadTeachers(prevGroupLastPage);
            }
        };
        pagination.appendChild(prev);

        // 페이지 번호
        for (let i = start; i <= end; i++) {
            const btn = document.createElement("button");
            btn.textContent = i;
            btn.className = `page-btn ${i === currentPage ? "active" : ""}`;
            btn.onclick = () => loadPayments(i);
            pagination.appendChild(btn);
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

    // [6] PDF 버튼
    const pdfBtn = document.getElementById("downloadPdfBtn");
    if (pdfBtn) {
        pdfBtn.addEventListener("click", () => {
            alert("📄 PDF 저장 기능은 다음 단계에서 구현됩니다.");
        });
    }

    // ✅ 초기 실행
    loadPayments(currentPage);
});
