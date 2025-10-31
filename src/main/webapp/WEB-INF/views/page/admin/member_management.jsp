<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/member_management.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/member_management_paging.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="member"/>
</jsp:include>

<div class="admin-content-container">
    <div class="admin-header-bar">
        <h2 class="admin-page-title">회원 관리</h2>
        <button id="downloadPdfBtn" class="btn-download">PDF로 저장</button>
    </div>
    <!-- 검색 바 -->
    <div class="member-search-bar">
        <select id="searchType" name="filter">
            <option value="">전체</option>
            <option value="email">이메일</option>
            <option value="nickname">이름</option>
        </select>
        <select id="roleFilter" name="role">
            <option value="">전체 권한</option>
            <option value="USER">학생</option>
            <option value="TEACHER">강사</option>
            <option value="ADMIN">관리자</option>
        </select>
        <select id="isActiveFilter">
            <option value="">전체 상태</option>
            <option value="1">활성</option>
            <option value="0">비활성</option>
        </select>
        <input type="text" id="keyword" name="keyword" placeholder="회원 이름 또는 이메일 검색..."/>

        <button id="memberSearchBtn" type="button">검색</button>
    </div>

    <!-- 회원 테이블 -->
    <div class="member-table-wrapper">
        <table class="member-table">
            <thead>
            <tr>
                <th>No</th>
                <th>ID</th>
                <th>닉네임</th>
                <th>이메일</th>
                <th>권한</th>
                <th>상태</th>
                <th>가입일</th>
                <th>최근 로그인 일시</th>
                <th>관리</th>
            </tr>
            </thead>
            <tbody id="memberTableBody">
            <!-- JS 회원 목록 렌더링 -->
            </tbody>
            <!--
            <tbody>
            <tr>
                <td>1</td>
                <td>김효상</td>
                <td>kinhyo97@studyon.com</td>
                <td>관리자</td>
                <td>활성</td>
                <td>2025-10-21</td>
                <td>🟢</td>
                <td><a class="management-button" href="#">관리</a></td>
            </tr>
            <tr>
                <td>2</td>
                <td>박지민</td>
                <td>pjm@studyon.com</td>
                <td>강사</td>
                <td>비활성</td>
                <td>2025-10-15</td>
                <td>🔴</td>
                <td><a class="management-button" href="#">관리</a></td>
            </tr>
            <tr>
                <td>3</td>
                <td>김한재</td>
                <td>khj@studyon.com</td>
                <td>학생</td>
                <td>비활성</td>
                <td>2025-10-15</td>
                <td>🔴</td>
                <td><a class="management-button" href="#">관리</a></td>
            </tr>
            </tbody>
            <-- 안쓰는 부분
            <tbody id="memberTableBody">
            <c:forEach var="member" items="${memberList}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${member.name}</td>
                    <td>${member.email}</td>
                    <td>${member.role}</td>
                    <td>
                        <c:choose>
                            <c:when test="${member.active}"><span class="status-active">활성</span></c:when>
                            <c:otherwise><span class="status-banned">정지</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td><fmt:formatDate value="${member.createdAt}" pattern="yyyy-MM-dd" /></td>
                    <td>
                        <button class="btn-view">보기</button>
                        <button class="btn-ban">정지</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            -->
        </table>
        <!-- 페이징 -->
        <div id="pagination" class="pagination"></div>
    </div>

</div>


<div id="memberModal" class="modal-overlay">
    <div class="modal-content">
        <span class="close-btn">&times;</span>
        <label class="modal-title">회원 상세정보</label>

        <div class="modal-info">
            <p><strong>닉네임:</strong> <span id="modalName">-</span></p>
            <p><strong>이메일:</strong> <span id="modalEmail">-</span></p>
            <p><strong>권한:</strong> <span id="modalRole">-</span></p>
            <p><strong>상태:</strong> <span id="modalStatus"></span>✏️</p>
            <p><strong>가입일:</strong> <span id="modalDate">-</span></p>
            <p><strong>최근 로그인 일시:</strong> <span id="modalLoginDate">-</span></p>
            <!--
            <button class="btn-view" data-id="${m.memberId}">재활성</button>
            <button class="btn-ban" data-id="${m.memberId}">정지</button>
            -->
        </div>

        <div class="modal-buttons">
            <button id="toggleBtn" class="btn-ban">비활성</button>
            <button id="closeModalBtn" class="btn-view">닫기</button>
        </div>
    </div>
</div>

<!-- member_management.js -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        console.log("[INIT] 회원 목록 불러오기 시작");

        // 페이지용 변수
        let currentPage = 1;
        const pageSize = 10;
        const tbody = document.getElementById("memberTableBody");
        const pagination = document.getElementById("pagination");

        // [1] 메인 함수 - 회원 목록 불러오기
        function loadMembers(page, searchType, keyword) {
            if (!page || isNaN(page) || page < 1) page = 1;

            const role = document.getElementById("roleFilter") ? document.getElementById("roleFilter").value : "";
            const isActive = document.getElementById("isActiveFilter") ? document.getElementById("isActiveFilter").value : "";

            const params = new URLSearchParams({page: page, size: pageSize});
            if (searchType) params.append("filter", searchType);
            if (keyword) params.append("keyword", keyword);
            if (role) params.append("role", role);
            if (isActive !== "") params.append("isActive", isActive);

            const url = "/admin/api/members/list?" + params.toString();
            console.log("[FETCH] " + url);

            fetch(url, {
                method: "GET",
                headers: {
                    "X-Requested-From": window.location.pathname + window.location.search
                }
            })
                .then(function (res) {
                    return res.json();
                })
                .then(function (json) {
                    if (!json.success) {
                        console.error("[ERROR] 요청 실패:", json ? json.message : "");
                        return;
                    }

                    const raw = json.data;
                    const members = Array.isArray(raw.data) ? raw.data : [];
                    console.log("[DEBUG] 서버에서 받은 member 리스트:", members);

                    currentPage = raw.currentPage || page;
                    const totalPages = raw.totalPage || 1;
                    const totalMembers = raw.dataCount || 0;

                    console.log("[DATA] 회원 " + members.length + "명 / 총 " + totalMembers + "명");
                    renderMemberTable(members);
                    renderPagination(totalPages);
                })
                .catch(function (err) {
                    console.error("[ERROR] fetch 실패:", err);
                });
        }

        // [2] 회원 목록 렌더링
        function renderMemberTable(members) {
            if (!tbody) {
                console.error("memberTableBody 요소를 찾을 수 없습니다.");
                return;
            }

            const role = document.getElementById("roleFilter") ? document.getElementById("roleFilter").value : "";
            const isActive = document.getElementById("isActiveFilter") ? document.getElementById("isActiveFilter").value : "";
            const keyword = document.getElementById("keyword") ? document.getElementById("keyword").value.trim() : "";

            tbody.innerHTML = "";

            if (!members || members.length === 0) {
                const roleLabel = {"USER": "학생", "TEACHER": "강사", "ADMIN": "관리자"}[role] || "";
                const activeLabel = {"true": "활성", "false": "비활성"}[isActive] || "";
                const conditions = [];
                if (activeLabel) conditions.push(activeLabel);
                if (roleLabel) conditions.push(roleLabel);
                if (keyword) conditions.push('"' + keyword + '"');

                const message = (conditions.length > 0)
                    ? conditions.join(" ") + "에 해당하는 회원이 없습니다."
                    : "조회된 회원이 없습니다.";

                tbody.innerHTML =
                    "<tr><td colspan='8' style='text-align:center; color:#888; padding:20px; font-size:16px; background:#fff'>" +
                    message + "</td></tr>";
                console.log("[EMPTY]", message);
                return;
            }

            members.forEach(function (m, index) {
                const tr = document.createElement("tr");
                const rowNo = (currentPage - 1) * pageSize + index + 1;
                const lastLogin = m.lastLoginAt ? trimDateTime(m.lastLoginAt) : "기록 없음";
                const joinDate = m.cdate ? new Date(m.cdate).toLocaleDateString() : "-";
                const status = m.isActive ? "활성" : "비활성";

                tr.innerHTML =
                    "<td>" + rowNo + "</td>" +
                    "<td>" + m.memberId + "</td>" +
                    "<td>" + m.nickname + "</td>" +
                    "<td>" + (m.email || "-") + "</td>" +
                    "<td>" + convertRole(m.role) + "</td>" +
                    "<td>" + (m.isActive
                        ? "<span class='status-active'>활성</span>"
                        : "<span class='status-banned'>비활성</span>") + "</td>" +
                    "<td>" + joinDate + "</td>" +
                    "<td>" + lastLogin + "</td>" +
                    "<td><button class='btn-view' data-member-id='" + m.memberId + "'>보기/관리</button></td>";
                tbody.appendChild(tr);
            });
            console.log("[RENDER] " + members.length + "명의 회원 렌더링 완료");
        }

        // [3] 페이지네이션
        function renderPagination(totalPages) {
            pagination.innerHTML = "";
            if (!totalPages || totalPages <= 0) return;

            const maxVisible = 5;
            const currentGroup = Math.ceil(currentPage / maxVisible);
            const start = (currentGroup - 1) * maxVisible + 1;
            const end = Math.min(totalPages, start + maxVisible - 1);

            const prev = document.createElement("button");
            prev.textContent = "◀";
            prev.className = "page-btn " + (currentGroup === 1 ? "disabled" : "");
            prev.onclick = function () {
                if (currentGroup > 1) {
                    const prevGroupLastPage = (currentGroup - 2) * maxVisible + 1;
                    loadMembers(prevGroupLastPage);
                }
            };
            pagination.appendChild(prev);

            for (let i = start; i <= end; i++) {
                const btn = document.createElement("button");
                btn.textContent = i;
                btn.className = "page-btn " + (i === currentPage ? "active" : "");
                btn.onclick = function () {
                    loadMembers(i);
                };
                pagination.appendChild(btn);
            }

            const next = document.createElement("button");
            next.textContent = "▶";
            next.className = "page-btn " + (end >= totalPages ? "disabled" : "");
            next.onclick = function () {
                if (end < totalPages) {
                    const nextGroupFirstPage = end + 1;
                    loadMembers(nextGroupFirstPage);
                }
            };
            pagination.appendChild(next);
        }

        // [4] 권한 변환
        function convertRole(role) {
            const map = {
                "ROLE_ADMIN": "관리자",
                "ROLE_TEACHER": "강사",
                "ROLE_STUDENT": "학생"
            };
            return map[role] || "-";
        }

        // [5] 날짜 포맷
        function formatDate(dateStr) {
            if (!dateStr) return "-";
            return new Date(dateStr).toLocaleDateString("ko-KR");
        }

        // 초기 로드
        loadMembers(1);

        // 검색 버튼 이벤트
        const searchBtn = document.getElementById("memberSearchBtn");
        if (searchBtn) {
            searchBtn.addEventListener("click", function () {
                console.log("[SEARCH] 검색 버튼 클릭 감지됨");
                document.getElementById("keyword").blur();
                tbody.innerHTML = "<tr><td colspan='8' style='text-align:center; color:#777;'>불러오는 중...</td></tr>";

                const searchType = document.getElementById("searchType") ? document.getElementById("searchType").value : "";
                const keyword = document.getElementById("keyword") ? document.getElementById("keyword").value.trim() : "";

                loadMembers(1, searchType, keyword);
            });
        }

        // 엔터키 검색
        const keywordInput = document.getElementById("keyword");
        if (keywordInput) {
            keywordInput.addEventListener("keypress", function (e) {
                if (e.key === "Enter") {
                    e.preventDefault();
                    if (searchBtn) searchBtn.click();
                }
            });
        }

        ["searchType", "roleFilter", "isActiveFilter"].forEach(function (id) {
            const el = document.getElementById(id);
            if (el) {
                el.addEventListener("change", function () {
                    console.log("[FILTER] " + id + " 변경됨 -> 자동 새로고침");
                    const searchType = document.getElementById("searchType") ? document.getElementById("searchType").value : "";
                    const keyword = document.getElementById("keyword") ? document.getElementById("keyword").value.trim() : "";
                    loadMembers(1, searchType, keyword);
                });
            }
        });

        // [6] PDF 출력
        function renderPdf() {
            console.log("[PDF] 회원 목록 PDF 다운로드 시작");
            const searchType = document.getElementById("searchType") ? document.getElementById("searchType").value : "";
            const keyword = document.getElementById("keyword") ? document.getElementById("keyword").value.trim() : "";
            const role = document.getElementById("roleFilter") ? document.getElementById("roleFilter").value : "";
            const isActive = document.getElementById("isActiveFilter") ? document.getElementById("isActiveFilter").value : "";

            const params = new URLSearchParams();
            if (searchType) params.append("filter", searchType);
            if (keyword) params.append("keyword", keyword);
            if (role) params.append("role", role);
            if (isActive !== "") params.append("isActive", isActive);

            fetch("/admin/api/members/export/pdf?" + params.toString(), {
                method: "GET",
                headers: {"X-Requested-From": window.location.pathname + window.location.search}
            })
                .then(function (res) {
                    if (!res.ok) throw new Error("HTTP 상태 코드 " + res.status);
                    return res.blob();
                })
                .then(function (blob) {
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement("a");
                    a.href = url;
                    a.download = "회원목록.pdf";
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                    window.URL.revokeObjectURL(url);
                    console.log("[PDF] 다운로드 완료");
                })
                .catch(function (err) {
                    console.error("[ERROR] PDF 다운로드 실패:", err);
                });
        }

        const pdfBtn = document.getElementById("downloadPdfBtn");
        if (pdfBtn) {
            pdfBtn.addEventListener("click", function () {
                renderPdf();
            });
        }

        // [7] ISO 시간 문자열 포맷
        function trimDateTime(datetime) {
            if (!datetime) return "-";
            return datetime.replace("T", " ").split(".")[0];
        }
    });
</script>
<!-- member_management_modal.js -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        console.log("[INIT] 모달 이벤트 등록 시작");

        const modal = document.getElementById("memberModal");
        const toggleBtn = document.getElementById("toggleBtn");
        const closeModalBtn = document.getElementById("closeModalBtn");
        const closeBtn = document.querySelector(".close-btn");

        // ✅ (1) 회원 테이블에서 보기 버튼 클릭 시 모달 열기
        document.addEventListener("click", function (e) {
            const btn = e.target.closest(".btn-view");
            if (!btn) return; // 다른 버튼 클릭 시 무시

            const row = btn.closest("tr");
            if (!row) return;

            const name = row.children[2].innerText;
            const email = row.children[3].innerText;
            const role = row.children[4].innerText;
            const status = row.children[5].innerText.trim();
            const date = row.children[6].innerText;
            const loginAt = row.children[7].innerText;
            const memberId = btn.dataset.memberId;

            console.log("[DEBUG] 모달 세팅:", {
                memberId: memberId,
                name: name,
                email: email,
                status: status,
                loginAt: loginAt
            });

            // 모달 데이터 채우기
            document.getElementById("modalName").innerText = name;
            document.getElementById("modalEmail").innerText = email;
            document.getElementById("modalRole").innerText = role;
            document.getElementById("modalStatus").innerText = status;
            document.getElementById("modalDate").innerText = date;
            document.getElementById("modalLoginDate").innerText = loginAt ? trimDateTime(loginAt) : "기록 없음";

            modal.dataset.memberId = memberId;
            toggleBtn.innerText = (status === "활성") ? "정지" : "해제";
            modal.style.display = "flex";
        });

        // ✅ (2) 모달 닫기 (X 버튼, 닫기 버튼, 외부 클릭)
        function closeModal() {
            modal.style.display = "none";
        }

        if (closeModalBtn) closeModalBtn.addEventListener("click", closeModal);
        if (closeBtn) closeBtn.addEventListener("click", closeModal);

        window.addEventListener("click", function (e) {
            if (e.target === modal) closeModal();
        });

        // ✅ (3) 날짜 포맷 정리 함수
        function trimDateTime(datetime) {
            if (!datetime) return "-";
            // 'T' → 공백으로 바꾸고, 소수점(밀리초) 이후 제거
            return datetime.replace("T", " ").split(".")[0];
        }
    });
</script>

<!-- management_toggle.js -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        console.log("[INIT] 공통 모달 이벤트 로드됨 (management_toggle.jsp-inline)");

        const modal = document.getElementById("memberModal");
        if (!modal) {
            console.warn("[WARN] 모달 요소(#memberModal)를 찾을 수 없습니다.");
            return;
        }

        const toggleBtn = document.getElementById("toggleBtn");
        const closeBtn = document.querySelector(".close-btn");
        const closeModalBtn = document.getElementById("closeModalBtn");

        function closeModal() {
            modal.style.display = "none";
        }

        if (closeBtn) closeBtn.addEventListener("click", closeModal);
        if (closeModalBtn) closeModalBtn.addEventListener("click", closeModal);

        window.addEventListener("click", function (e) {
            if (e.target === modal) closeModal();
        });

        // ✅ 보기 버튼 클릭
        document.addEventListener("click", function (e) {
            const btn = e.target.closest(".btn-view");
            if (!btn) return;

            const row = btn.closest("tr");
            if (!row) return;

            // ✅ 데이터 구분: member / teacher
            const type = modal.dataset.type || "member";
            modal.dataset.type = type;

            if (type === "teacher") {
                setTeacherModal(row, btn);
            } else {
                setMemberModal(row, btn);
            }

            modal.style.display = "flex";
        });

        // ✅ 상태 토글 클릭
        if (toggleBtn) {
            toggleBtn.addEventListener("click", async function () {
                const type = modal.dataset.type || "member";
                const targetId = (type === "teacher") ? modal.dataset.teacherId : modal.dataset.memberId;

                const name = document.getElementById("modalName")?.innerText || "-";
                const currentStatus = document.getElementById("modalStatus")?.innerText.trim();

                if (!targetId) {
                    alert((type === "teacher" ? "강사" : "회원") + " 정보를 찾을 수 없습니다.");
                    return;
                }

                const confirmMsg = (currentStatus === "활성")
                    ? name + "님을 비활성화(정지)하시겠습니까?"
                    : name + "님을 활성화(해제)하시겠습니까?";

                if (!confirm(confirmMsg)) return;

                try {
                    const url = (type === "teacher")
                        ? "/admin/api/teachers/toggle/" + targetId
                        : "/admin/api/members/toggle/" + targetId;

                    console.log("[FETCH]", url);

                    const res = await fetch(url, {
                        method: "POST",
                        headers: {
                            "X-Requested-From": window.location.pathname
                        }
                    });

                    const json = await res.json();
                    console.log("[DEBUG] 서버 응답:", json);

                    if (!json.success) {
                        alert("⚠️ 상태 변경 실패: " + (json.message || "요청 실패"));
                        return;
                    }

                    const isActive = json.data?.isActive || false;
                    const newStatus = isActive ? "활성" : "비활성";

                    document.getElementById("modalStatus").innerText = newStatus;
                    toggleBtn.innerText = isActive ? "정지" : "해제";

                    // 테이블 반영
                    const row = document.querySelector(
                        "button[data-" + type + "-id='" + targetId + "']"
                    )?.closest("tr");

                    if (row) {
                        row.children[4].innerHTML = isActive
                            ? "<span class='status-active'>활성</span>"
                            : "<span class='status-banned'>비활성</span>";
                    }

                    alert("상태가 변경되었습니다.");
                    closeModal();
                    window.location.reload();

                } catch (err) {
                    console.error("[ERROR]", err);
                    alert("상태 변경 중 오류가 발생했습니다.");
                }
            });
        }

        /** 🧩 회원용 모달 세팅 */
        function setMemberModal(row, btn) {
            const name = row.children[2].innerText;
            const email = row.children[3].innerText;
            const role = row.children[4].innerText;
            const status = row.children[5].innerText.trim();
            const date = row.children[6].innerText;
            const loginAt = row.children[7].innerText;
            const memberId = btn.dataset.memberId;

            modal.dataset.memberId = memberId;

            document.getElementById("modalName").innerText = name;
            document.getElementById("modalEmail").innerText = email;
            document.getElementById("modalRole").innerText = role;
            document.getElementById("modalStatus").innerText = status;
            document.getElementById("modalDate").innerText = date;
            document.getElementById("modalLoginDate").innerText = loginAt || "기록 없음";
            toggleBtn.innerText = (status === "활성") ? "정지" : "해제";
        }

        /** 🧩 강사용 모달 세팅 */
        function setTeacherModal(row, btn) {
            const name = row.children[1].innerText;
            const email = row.children[2].innerText;
            const subject = row.children[3].innerText;
            const status = row.children[4].innerText.trim();
            const date = row.children[5].innerText;
            const loginAt = row.children[6].innerText;
            const teacherId = btn.dataset.teacherId;

            modal.dataset.teacherId = teacherId;

            document.getElementById("modalName").innerText = name;
            document.getElementById("modalEmail").innerText = email;
            document.getElementById("modalSubject").innerText = subject;
            document.getElementById("modalStatus").innerText = status;
            document.getElementById("modalDate").innerText = date;
            document.getElementById("modalLoginDate").innerText = loginAt || "기록 없음";
            toggleBtn.innerText = (status === "활성") ? "비활성" : "활성";
        }
    });
</script>