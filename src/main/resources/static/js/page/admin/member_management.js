document.addEventListener("DOMContentLoaded", () => {
    console.log("[INIT] 회원 목록 불러오기 시작");

    // 페이지용 변수
    let currentPage = 1;
    const pageSize = 10;
    const tbody = document.getElementById("memberTableBody");
    const pagination = document.getElementById("pagination");

    // ✅ [1] 메인 함수 - 회원 목록 불러오기
    function loadMembers(page = 1) {

        // 검색용 변수
        const searchType = document.getElementById("searchType")?.value || "";
        const keyword = document.getElementById("keyword")?.value.trim() || "";
        const role = document.getElementById("roleFilter")?.value || "";

        // 쿼리 파라미터 구성
        const params = new URLSearchParams({
            page,
            size: pageSize,
        });

        // keyword가 있을 때만 filter도 같이 붙이기
        if (keyword) {
            if (searchType) params.append("filter", searchType);
            params.append("keyword", keyword);
        }

        // role 필터는 단독 가능
        if (role) params.append("role", role);

        const url = `/admin/api/members/list?${params.toString()}`;
        console.log(`[FETCH] ${url}`);


        fetch(url, {
            method: "GET",
            headers: {'X-Requested-From': window.location.pathname + window.location.search}
        })
            .then(res => res.json())
            .then(json => {
                if (!json.success) {
                    console.error("[ERROR] 요청 실패:", json?.message);
                    return;
                }

                // ✅ 올바른 구조 접근
                const raw = json.data;
                const members = Array.isArray(raw.data) ? raw.data : [];

                currentPage = raw.currentPage || 1;
                const totalPages = raw.totalPage || 1;
                const totalMembers = raw.dataCount || 0;

                console.log(`[DATA] 회원 ${members.length}명 / 총 ${totalMembers}명`);
                renderMemberTable(members);
                renderPagination(totalPages);
            })
            .catch(err => console.error("[ERROR] fetch 실패:", err));
    }

    /**
     * 회원 목록 테이블 렌더링
     */
    function renderMemberTable(members) {
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
            <td>
                ${m.isActive
                    ? `<span class="status-active">활성</span>`
                    : `<span class="status-banned">비활성</span>`}
            </td>
            <td>${joinDate}</td>
            <td>${lastLogin}</td>
            <td>
                <button class="btn-view" data-member-id="${m.memberId}">보기</button>
                <button class="btn-ban" data-id="${m.memberId}">관리</button>
            </td>
            <!--
            <td><a href="#" class="management-button" data-member-id="${m.memberId}">관리</a></td>
            -->
            `;

            tbody.appendChild(tr);
        });
        console.log(`[RENDER] ${members.length}명의 회원 렌더링 완료`);

    }

    // [3] 페이지네이션 렌더링
    function renderPagination(totalPages) {
        pagination.innerHTML = "";

        if (totalPages <= 1) return;

        const maxVisible = 5;
        let start = Math.max(1, currentPage - Math.floor(maxVisible / 2));
        let end = Math.min(totalPages, start + maxVisible - 1);

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
            case "ROLE_ADMIN":
                return "관리자";
            case "ROLE_TEACHER":
                return "강사";
            case "ROLE_STUDENT":
                return "학생";
            default:
                return "-";
        }
    }

    // 날짜 포맷
    function formatDate(dateStr) {
        if (!dateStr) return "-";
        return new Date(dateStr).toLocaleDateString("ko-KR");
    }

    // 초기 로드
    loadMembers(1);

    // 검색 버튼 이벤트
    const searchBtn = document.getElementById("memberSearchBtn");
    if (searchBtn) {
        searchBtn.addEventListener("click", () => {
            console.log("[SEARCH] 검색 버튼 클릭 감지됨");
            document.getElementById("keyword").blur();
            tbody.innerHTML = "<tr><td colspan='8' style='text-align:center;'>검색 중...</td></tr>";
            loadMembers(1);
        });
    }

    //  엔터키 검색
    const keywordInput = document.getElementById("keyword");
    if (keywordInput) {
        keywordInput.addEventListener("keypress", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                searchBtn.click();
            }
        });
    }
});