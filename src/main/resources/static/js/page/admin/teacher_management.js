document.addEventListener("DOMContentLoaded", () => {
    // ✅ 페이지용 변수
    let currentPage = 1;
    const pageSize = 10;
    const tbody = document.getElementById("teachersTableBody");


    // [1] 메인 함수 - 강사 목록 불러오기
    function loadTeachers(page) {
        if (!page || isNaN(page) || page < 1) page = 1;

        const searchType = document.getElementById("searchType")?.value || "";
        const keyword = document.getElementById("keyword")?.value.trim() || "";
        const subject = document.getElementById("subjectFilter")?.value || "";

        const params = new URLSearchParams({
            page,
            size: pageSize
        });

        if (searchType) params.append("filter", searchType);
        if (keyword) params.append("keyword", keyword);
        if (subject) params.append("subject", subject);

        const url = `/api/teachers?${params.toString()}`;
        console.log(`[FETCH] ${url}`);

        fetch(url, {
            method: "GET",
            headers: { "X-Requested-From": window.location.pathname + window.location.search ,
            },
        })
            .then(res => res.json())
            .then(json => {
                if (!json.success) {
                    console.error("[ERROR] 요청 실패:", json?.message);
                    return;
                }

                const raw = json.data;
                const teachers = Array.isArray(raw.data) ? raw.data : [];
                console.log("[DEBUG] 서버에서 받은 member 리스트:", teachers);

                currentPage = raw.currentPage || page;
                const totalPages = raw.totalPage || 1;

                const totalteachers = raw.dataCount || 0;

                console.log(`[DATA] 회원 ${teachers.length}명 / 총 ${totalteachers}명`);
                renderTeachersTable(teachers);
                renderPagination(totalPages);
            })
            .catch(err => console.error("[ERROR] fetch 실패:", err));
    }

    // [2] 페이지네이션 렌더링
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
            btn.onclick = () => loadTeachers(i);
            pagination.appendChild(btn);
        }

        // ▶ 다음 그룹 버튼
        const next = document.createElement("button");
        next.textContent = "▶";
        next.className = `page-btn ${end >= totalPages ? "disabled" : ""}`;
        next.onclick = () => {
            if (end < totalPages) {
                const nextGroupFirstPage = end + 1;
                loadTeachers(nextGroupFirstPage);
            }
        };
        pagination.appendChild(next);
    }

    // [3] 강사 테이블 렌더링
    function renderTeachersTable(teachers) {
        if (!tbody) return;

        tbody.innerHTML = ""; // 초기화
        if (!teachers.length) {
            tbody.innerHTML = `<tr><td colspan="8">등록된 강사가 없습니다.</td></tr>`;
            return;
        }

        teachers.forEach((t, idx) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${(currentPage - 1) * pageSize + idx + 1}</td>
                <td>${t.nickname || "-"}</td>
                <td>${t.email || "-"}</td>
                <td>${t.role?.replace("ROLE_", "") || "강사"}</td>
                <td>${t.active ? "<span class='status-active'>활성</span>" : "<span class='status-banned'>비활성</span>"}</td>
                <td>${t.createdAt ? new Date(t.createdAt).toLocaleDateString() : "-"}</td>
                <td>${t.lastLoginAt ? new Date(t.lastLoginAt).toLocaleDateString() : "-"}</td>
                <td><a href="#" class="management-button" data-id="${t.memberId}">상세정보</a></td>
            `;
            tbody.appendChild(row);
        });

        attachModalEvents();
    }

    // [4] 모달 이벤트 등록
    function attachModalEvents() {
        document.querySelectorAll(".management-button").forEach(btn => {
            btn.addEventListener("click", (e) => {
                e.preventDefault();
                const row = e.target.closest("tr");
                // 클릭할 때마다 DOM에서 새로 찾기 (스코프 안전)
                const modal = document.getElementById("memberModal");
                const closeBtn = modal.querySelector(".close-btn");
                const closeModalBtn = modal.querySelector("#closeModalBtn");

                const name = row.children[1].innerText;
                const email = row.children[2].innerText;
                const role = row.children[3].innerText;
                const status = row.children[4].innerText;
                const date = row.children[5].innerText;

                document.getElementById("modalName").innerText = name;
                document.getElementById("modalEmail").innerText = email;
                document.getElementById("modalRole").innerText = role;
                document.getElementById("modalStatus").innerText = status;
                document.getElementById("modalDate").innerText = date;

                modal.style.display = "flex";
                // 닫기 버튼
                if (closeBtn) closeBtn.onclick = () => (modal.style.display = "none");
                if (closeModalBtn) closeModalBtn.onclick = () => (modal.style.display = "none");
                window.onclick = (e) => {
                    if (e.target === modal) modal.style.display = "none";
                };
            });
        });
    }

    // [5] 검색 버튼 이벤트
    const searchBtn = document.getElementById("searchBtn");
    if (searchBtn) {
        searchBtn.addEventListener("click", (e) => {
            e.preventDefault();
            loadTeachers(1);
        });
    }

    // [5-1] 엔터키 검색
    const keywordInput = document.getElementById("keyword");
    if (keywordInput) {
        keywordInput.addEventListener("keypress", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                searchBtn.click();
            }
        });
    }

    // ✅ 초기 실행
    loadTeachers(currentPage);
});
