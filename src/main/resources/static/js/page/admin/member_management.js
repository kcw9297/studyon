document.addEventListener("DOMContentLoaded", () => {
    console.log("[INIT] 회원 목록 불러오기 시작");

    // 페이지용 변수
    let currentPage = 1;
    const pageSize = 10;
    const tbody = document.getElementById("memberTableBody");
    const pagination = document.getElementById("pagination");

    // [1] 메인 함수 - 회원 목록 불러오기
    function loadMembers(page) {

        // 페이지값 기본 보장
        if (!page || isNaN(page) || page < 1) page = 1;
        // ✅ DOM 요소에서 값 읽기
        const searchType = document.getElementById("searchType")?.value || "";
        const keyword = document.getElementById("keyword")?.value.trim() || "";
        const role = document.getElementById("roleFilter")?.value || "";
        const isActive = document.getElementById("isActiveFilter")?.value || "";
        // ✅ 쿼리 파라미터 구성
        const params = new URLSearchParams({
            page,
            size: pageSize,
        });

        // ✅ 필요한 값만 추가 (값이 있을 때만 append)
        if (searchType) params.append("filter", searchType);
        if (keyword) params.append("keyword", keyword);
        if (role) params.append("role", role);
        if (isActive !== "") params.append("isActive", isActive);

        const url = `/admin/api/members/list?${params.toString()}`;
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
                const members = Array.isArray(raw.data) ? raw.data : [];
                console.log("[DEBUG] 서버에서 받은 member 리스트:", members);

                currentPage = raw.currentPage || page;
                const totalPages = raw.totalPage || 1;

                const totalMembers = raw.dataCount || 0;

                console.log(`[DATA] 회원 ${members.length}명 / 총 ${totalMembers}명`);
                renderMemberTable(members);
                renderPagination(totalPages);
            })
            .catch(err => console.error("[ERROR] fetch 실패:", err));
    }


    // [2] 회원 목록 렌더링
    function renderMemberTable(members) {
        if (!tbody) {
            console.error("memberTableBody 요소를 찾을 수 없습니다.");
            return;
        }


        // ✅ 항상 최신 필터 상태 읽기
        const role = document.getElementById("roleFilter")?.value || "";
        const isActive = document.getElementById("isActiveFilter")?.value || "";
        const keyword = document.getElementById("keyword")?.value.trim() || "";

        tbody.innerHTML = ""; // 기존 내용 비우기

        if (!members || members.length === 0) {

            // 이름 매핑
            const roleLabel = { "USER": "학생", "TEACHER": "강사", "ADMIN": "관리자" }[role] || "";
            const activeLabel = { "true": "활성", "false": "비활성" }[isActive] || "";

            const conditions = [activeLabel, roleLabel, keyword && `"${keyword}"`].filter(Boolean);
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

        members.forEach((m, index) => {
            const tr = document.createElement("tr");

            const rowNo = (currentPage - 1) * pageSize + index + 1;
            const lastLogin = m.lastLoginAt ? trimDateTime(m.lastLoginAt) : "기록 없음";
            const joinDate = m.cdate ? new Date(m.cdate).toLocaleDateString() : "-";
            const status = m.isActive ? "활성" : "비활성";

            tr.innerHTML = `
            <td>${rowNo}</td>
            <td>${m.memberId}</td>
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
                <button class="btn-view" data-member-id="${m.memberId}">보기/관리</button>
                <!-- <button class="btn-ban" data-id="${m.memberId}">관리</button>  -->
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

        if (!totalPages || totalPages <= 0) return;

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

    // [4] 권한 변환 함수 (사용 예정)
    function convertRole(role) {
        const map = {
            "ROLE_ADMIN": "관리자",
            "ROLE_TEACHER": "강사",
            "ROLE_STUDENT": "학생",
        };
        return map[role] ?? "-";
    }

    // [5] 날짜 포맷 변환 함수
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
            tbody.innerHTML = "<tr><td colspan=\"8\" style=\"text-align:center; color:#777;\">불러오는 중...</td></tr>";
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
    ["searchType", "roleFilter", "isActiveFilter"].forEach(id => {
        const el = document.getElementById(id);
        if (el) {
            el.addEventListener("change", () => {
                console.log(`[FILTER] ${id} 변경됨 -> 자동 새로고침`);
                loadMembers(1);
            });
        }
    });

    // [6] PDF 출력 함수
    function renderPdf() {
        console.log("[PDF] 회원 목록 PDF 다운로드 시작");
        // [1] 현재 화면 필터 상태 읽기
        const searchType = document.getElementById("searchType")?.value || "";
        const keyword = document.getElementById("keyword")?.value.trim() || "";
        const role = document.getElementById("roleFilter")?.value || "";
        const isActive = document.getElementById("isActiveFilter")?.value || "";

        // [2] 쿼리 파라미터 구성
        const params = new URLSearchParams();
        if (searchType) params.append("filter", searchType);
        if (keyword) params.append("keyword", keyword);
        if (role) params.append("role", role);
        if (isActive !== "") params.append("isActive", isActive);

        // [3] api 가져온 후 fetch
        fetch(`/admin/api/members/export/pdf?${params.toString()}`, {
            method: "GET",
            headers: {"X-Requested-From": window.location.pathname + window.location.search},
        })
            .then(res => {
                if (!res.ok) throw new Error(`HTTP 상태 코드 ${res.status}`);
                return res.blob(); // PDF는 바이너리로 받기
            })
            
            // [2] then : document 파일 바이너릭 저장 로직
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement("a");
                a.href = url;
                a.download = "회원목록.pdf"; // 파일명 지정
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
                window.URL.revokeObjectURL(url);
                console.log("[PDF] 다운로드 완료");
            })
            .catch(err => console.error("[ERROR] PDF 다운로드 실패:", err));
    }

    // [6-*] Pdf 다운로드 버튼 이벤트 로직
    const pdfBtn = document.getElementById("downloadPdfBtn");
    if (pdfBtn) {
        pdfBtn.addEventListener("click", () => {
            renderPdf();
        });
    }

    // [7] ISO 형식 시간 문자열을 보기 좋게 포맷
    function trimDateTime(datetime) {
        if (!datetime) return "-";
        return datetime.replace("T", " ").split(".")[0]; // "2025-10-28 20:45:11"
    }

});