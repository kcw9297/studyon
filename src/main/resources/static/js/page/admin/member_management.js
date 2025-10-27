document.addEventListener("DOMContentLoaded", () => {
    console.log("[INIT] 회원 목록 불러오기 시작");


    let currentPage = 1;
    const pageSize = 10;

    const tbody = document.getElementById("memberTableBody");
    const pagination = document.getElementById("pagination");

    // ✅ [1] 메인 함수 - 회원 목록 불러오기
    function loadMembers(page = 1) {
        console.log(`[FETCH] /admin/api/members/list?page=${page}&size=${pageSize}`);

        fetch(`/admin/api/members/list?page=${page}&size=${pageSize}`, {
            method: "GET",
            headers: { 'X-Requested-From': window.location.pathname + window.location.search }
        })
            .then(res => res.json())
            .then(json => {
                if (!json.success) {
                    console.error("[ERROR] 요청 실패:", json?.message);
                    return;
                }

                const raw = json.data;

                // ✅ 구조 매핑
                const members = raw.data || [];
                currentPage = raw.currentPage || 1;   // 1-based
                const totalPages = raw.totalPage || 1;
                const totalMembers = raw.dataCount || 0;

                console.log(`[DATA] 회원 ${members.length}명 / 총 ${totalMembers}명`);
                renderMemberTable(members);
                renderPagination(raw?.totalPage ?? 1);
            })
            .catch(err => console.error("[ERROR] fetch 실패:", err));
    }
    /**
     * 회원 목록 테이블 렌더링
     */
    function renderMemberTable(members) {
        const tbody = document.getElementById("memberTableBody");
        if (!tbody) {
            console.error("memberTableBody 요소를 찾을 수 없습니다.");
            return;
        }

        tbody.innerHTML = ""; // 기존 내용 비우기

        if (!members || members.length === 0) {
            tbody.innerHTML = `
            <tr><td colspan="8" style="text-align:center;">회원 데이터가 없습니다.</td></tr>
        `;
            return;
        }

        members.forEach((m, index) => {
            const tr = document.createElement("tr");

            const lastLogin = m.isActive ? "🟢" : "🔴";
            const joinDate = m.cdate ? new Date(m.cdate).toLocaleDateString() : "-";
            const status = m.isActive ? "활성" : "비활성";

            tr.innerHTML = `
            <td>${(currentPage - 1) * pageSize + index + 1}</td>
            <td>${m.nickname}</td>
            <td>${m.email ?? "-"}</td>
            <td>${convertRole(m.role)}</td>
            <td>${status}</td>
            <td>${joinDate}</td>
            <td>${lastLogin}</td>
            <td><a href="#" class="management-button" data-member-id="${m.memberId}">관리</a></td>
        `;

            tbody.appendChild(tr);
        });
        console.log(`[RENDER] ${members.length}명의 회원 렌더링 완료`);

        // 여기서 이벤트 다시 연결
        attachModalEvents();
    }

// ✅ [3] 페이지네이션 렌더링
    function renderPagination(totalPages) {
        pagination.innerHTML = "";

        if (totalPages <= 1) return;

        const maxVisible = 5;
        let start = Math.max(1, currentPage - Math.floor(maxVisible / 2));
        let end = Math.min(totalPages - 1, start + maxVisible - 1);

        if (end - start < maxVisible - 1) {
            start = Math.max(1, end - maxVisible + 1);
        }

        // 이전 버튼
        const prev = document.createElement("button");
        prev.textContent = "◀";
        prev.className = `page-btn ${currentPage === 1 ? "disabled" : ""}`;
        prev.onclick = () => currentPage > 1 && loadMembers(currentPage - 1);
        pagination.appendChild(prev);

        // 페이지 번호
        for (let i = start; i <= end; i++) {
            const btn = document.createElement("button");
            btn.textContent = i;
            btn.className = `page-btn ${i === currentPage ? "active" : ""}`;
            btn.onclick = () => loadMembers(i);
            pagination.appendChild(btn);
        }

        // 다음 버튼
        const next = document.createElement("button");
        next.textContent = "▶";
        next.className = `page-btn ${currentPage === totalPages ? "disabled" : ""}`;
        next.onclick = () => currentPage < totalPages && loadMembers(currentPage + 1);
        pagination.appendChild(next);
    }

    // ✅ 권한 변환 함수 추가
    function convertRole(role) {
        switch (role) {
            case "ROLE_ADMIN": return "관리자";
            case "ROLE_TEACHER": return "강사";
            case "ROLE_STUDENT": return "학생";
            default: return "-";
        }
    }
    // 모달 창 먹히는 함수 코드 그대로 가져옴
    function attachModalEvents() {
        const modal = document.getElementById("memberModal");
        const closeBtn = document.querySelector(".close-btn");
        const closeModalBtn = document.getElementById("closeModalBtn");

        // 관리 버튼 클릭 이벤트 새로 연결
        document.querySelectorAll(".management-button").forEach(btn => {
            btn.addEventListener("click", (e) => {
                e.preventDefault();
                const row = e.target.closest("tr");
                if (!row) return;

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
            });
        });

        // 닫기 이벤트 한 번만 등록
        if (closeBtn && !closeBtn.dataset.bound) {
            closeBtn.dataset.bound = "true";
            closeBtn.addEventListener("click", () => modal.style.display = "none");
        }
        if (closeModalBtn && !closeModalBtn.dataset.bound) {
            closeModalBtn.dataset.bound = "true";
            closeModalBtn.addEventListener("click", () => modal.style.display = "none");
        }

        window.addEventListener("click", (e) => {
            if (e.target === modal) modal.style.display = "none";
        });
    }

    // ✅ 초기 실행
    loadMembers();
});