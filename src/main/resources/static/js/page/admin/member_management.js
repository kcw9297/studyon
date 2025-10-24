document.addEventListener("DOMContentLoaded", () => {
    console.log("[INIT] 회원 목록 불러오기 시작");

    const page = 1;
    const size = 10;

    fetch(`/admin/api/members/list?page=${page}&size=${size}`)
        .then(res => {
            console.log("[STEP1] 서버 응답 객체:", res);
            return res.json();
        })
        .then(json => {
            console.log("[STEP2] 파싱된 JSON:", json.data);

            if (!json.success) {
                console.error("[ERROR] 요청 실패:", json?.message);
                return;
            }

            let raw = json.data;

            // ✅ 문자열일 경우 JSON 파싱
            if (typeof raw === "string") {
                try {
                    raw = JSON.parse(raw);
                    console.log("[DEBUG] 문자열 JSON 파싱 완료:", raw);
                } catch (err) {
                    console.error("[ERROR] JSON 파싱 실패:", err);
                    raw = {};
                }
            }

            // ✅ 실제 회원 리스트는 raw.data 안에 있음
            const members = Array.isArray(raw.data) ? raw.data : [];

            console.log("[STEP3] 회원 리스트:", members);

            renderMemberTable(members);
        })
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

            const lastLogin = m.lastLoginAt ? "🟢" : "🔴";
            const joinDate = m.cdate ? new Date(m.cdate).toLocaleDateString() : "-";
            const status = m.isActive ? "활성" : "비활성";

            tr.innerHTML = `
            <td>${index + 1}</td>
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

        // ✅ 권한 변환 함수 추가
        function convertRole(role) {
            switch (role) {
                case "ROLE_ADMIN": return "관리자";
                case "ROLE_TEACHER": return "강사";
                case "ROLE_STUDENT": return "학생";
                default: return "-";
            }
        }
    }
});