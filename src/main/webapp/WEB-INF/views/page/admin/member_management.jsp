<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/member_management.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="member"/>
</jsp:include>

<div class="admin-content-container">
    <div class="admin-header-bar">
        <h2 class="admin-page-title">회원 조회</h2>
        <button id="downloadPdfBtn" class="btn-download">PDF로 저장</button>
    </div>
    <!-- 검색 바 -->
    <div class="member-search-bar">
        <select id="searchType" name="filter">
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
        <input type="text" id="keyword" name="keyword" placeholder="회원 이름 또는 이메일 검색..." />

        <button id="memberSearchBtn" type="button">검색</button>
    </div>

    <!-- 회원 테이블 -->
    <div class="member-table-wrapper">
        <table class="member-table">
            <thead>
            <tr>
                <th>No</th>
                <th>닉네임</th>
                <th>이메일</th>
                <th>권한</th>
                <th>상태</th>
                <th>가입일</th>
                <th>로그인</th>
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
            -->
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
        </table>

        <!-- 페이징 -->
        <div id="pagination" class="pagination-container"></div>
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

<style>
    .admin-content-container {
        display:flex;
        flex-direction: column;
        border:2px solid black;
        min-height: 600px;
        height:auto;
        width:100%;

    }
    .admin-page-title {
        font-size: 22px;
        font-weight: bold;
        color: #333;
        padding: 10px;
    }

    /* 검색바 */
    .member-search-bar {
        display: flex;
        align-items: center;
        gap: 10px;
        width: 100%;
        padding: 10px;
    }

    .member-search-bar input {
        flex: 7; /* 4 비율 */
        padding: 8px 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 14px;
    }

    .member-search-bar select {
        flex: 1; /* 4 비율 */
        padding: 8px 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 14px;
    }

    .member-search-bar button {
        flex: 1; /* 1 비율 */
        background: #4a90e2;
        color: white;
        border: none;
        padding: 8px 15px;
        border-radius: 6px;
        cursor: pointer;
        transition: 0.2s;
    }

    .member-search-bar button:hover {
        background: #357ac8;
    }

    /* 테이블 */
    .member-table-wrapper {
        width: 100%;
        background: #fff;
        border-radius: 10px;
        box-shadow: 0 3px 8px rgba(0,0,0,0.1);
        overflow: hidden;
    }

    .member-table {
        width: 100%;
        border-collapse: collapse;
        text-align: center;
    }

    /* 전체 공통 스타일 */
    .member-table th {
        background-color: #f5f6fa;
        color: #444;
        font-family: "Noto Sans KR", sans-serif;
        font-size: 16px;
        padding: 12px;
        border-bottom: 1px solid #ccc;
    }
    .member-table td {
        text-align: center;
        padding: 10px 12px;
        border-bottom: 1px solid #f0f0f0;
        color: #333;
    }

    .member-table th:nth-child(1) { width: 5%; }   /* No */
    .member-table th:nth-child(2) { width: 15%; }  /* 이름 */
    .member-table th:nth-child(3) { width: 20%; }  /* 이메일 */
    .member-table th:nth-child(4) { width: 10%; }  /* 권한 */
    .member-table th:nth-child(5) { width: 10%; }  /* 상태 */
    .member-table th:nth-child(6) { width: 15%; }  /* 가입일 */
    .member-table th:nth-child(7) { width: 10%; }  /* 관리 */

    .member-table tr:hover {
        background: #f9f9fc;
    }

    /* 상태 */
    .status-active {
        color: #27ae60;
        font-weight: bold;
    }

    .status-banned {
        color: #e74c3c;
        font-weight: bold;
    }

    /* 버튼 */
    .btn-view, .btn-ban {
        padding: 6px 10px;
        border: none;
        border-radius: 5px;
        color: white;
        cursor: pointer;
        font-size: 13px;
        transition: all 0.2s ease;
    }

    .btn-view {
        background: #4a90e2;
    }
    .btn-view:hover {
        background: #357ac8;
    }

    .btn-ban {
        background: #e74c3c;
        margin-left: 5px;
    }
    .btn-ban:hover {
        background: #c0392b;
    }

    .management-button{
        color:pink;
    }

    .modal-overlay {
        display: none; /* 기본 숨김 */
        position: fixed;
        top: 0; left: 0;
        width: 100%; height: 100%;
        background: rgba(0, 0, 0, 0.4);
        z-index: 999;
        justify-content: center;
        align-items: center;
    }

    /* 모달 본체 */
    .modal-content {
        background: #fff;
        padding: 25px 30px;
        border-radius: 10px;
        width: 800px;
        height:600px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.2);
        position: relative;
        animation: fadeIn 0.3s ease;
    }

    /* 닫기버튼 */
    .close-btn {
        position: absolute;
        right: 15px;
        top: 10px;
        font-size: 30px;
        cursor: pointer;
    }

    /* 내용 */
    .modal-info p {
        margin: 10px 0;
        font-size: 15px;
    }

    /* 버튼 영역 */
    .modal-buttons {
        margin-top: 20px;
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        width: 300px;
        height: 50px;
    }

    /* 애니메이션 */
    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(-20px); }
        to { opacity: 1; transform: translateY(0); }
    }

    .modal-info p {
        font-size: 16px; /* ✅ 원하는 크기로 조정 (예: 14px~18px 권장) */
        color: #333;     /* 글자색도 변경 가능 */
    }

    .modal-title{
        font-size:40px;
        font-weight: bold;
    }

    .modal-info p {
        font-size: 16px; /* ✅ 원하는 크기로 조정 (예: 14px~18px 권장) */
        color: #333;     /* 글자색도 변경 가능 */
    }

    .admin-header-bar {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 10px 20px;
        width: 100%;
    }
    .btn-download {
        background-color: #4a90e2;
        color: #fff;
        border: none;
        border-radius: 6px;
        padding: 8px 15px;
        font-size: 14px;
        font-weight: bold;
        cursor: pointer;
        transition: 0.2s;
    }

    .btn-download:hover {
        background-color: #357ac8;
    }
</style>

<script src="<c:url value='/js/page/admin/member_management.js'/>"></script>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const modal = document.getElementById("memberModal");
        const closeBtn = document.querySelector(".close-btn");
        const closeModalBtn = document.getElementById("closeModalBtn");

        // 정지 / 해제 버튼 (모달 안)
        const toggleBtn = document.getElementById("toggleBtn");

        // 관리 버튼 클릭 시
        // ✅ 이벤트 위임: tbody에 클릭 이벤트 등록
        document.getElementById("memberTableBody").addEventListener("click", (e) => {
            if (e.target.classList.contains("btn-view")) {
                e.preventDefault();
                const row = e.target.closest("tr");
                if (!row) return;

                // 데이터 추출
                const name = row.children[1].innerText;
                const email = row.children[2].innerText;
                const role = row.children[3].innerText;
                const status = row.children[4].innerText;
                const date = row.children[5].innerText;
                const memberId = e.target.dataset.memberId;

                // 모달 채우기
                document.getElementById("modalName").innerText = name;
                document.getElementById("modalEmail").innerText = email;
                document.getElementById("modalRole").innerText = role;
                document.getElementById("modalStatus").innerText = status;
                document.getElementById("modalDate").innerText = date;

                // 모달 자체에 memberId 저장
                modal.dataset.memberId = memberId;

                // 토글 역할 버튼 텍스트 설정
                toggleBtn.innerText = status === "활성" ? "정지" : "해제";

                // 모달 표시
                modal.style.display = "flex";
            }
        });

        // 모달 닫기 버튼
        closeBtn.addEventListener("click", () => modal.style.display = "none");
        closeModalBtn.addEventListener("click", () => modal.style.display = "none");

        // 바깥 클릭 시 닫기
        window.addEventListener("click", (e) => {
            if (e.target === modal) modal.style.display = "none";
        });


        toggleBtn.addEventListener("click", async () => {
            const memberId = modal.dataset.memberId;
            const name = document.getElementById("modalName").innerText;
            const currentStatus = document.getElementById("modalStatus").innerText.trim();


            if (!memberId) {
                alert("회원 정보를 찾을 수 없습니다.");
                return;
            }

            const confirmMsg = currentStatus === "활성"
                ? `${name}님을 비활성화(정지)하시겠습니까?`
                : `${name}님을 활성화(해제)하시겠습니까?`;

            if (!confirm(confirmMsg)) return;

            try {
                /* await fetch (await를 붙이면 비동기 로직에서 동기 로직이 필요할 경우 사용
                - ex) 요청 처리할 때까지 대기가 필요할 때
                 */
                const res = await fetch(`/admin/api/members/toggle/${memberId}`, {
                    method: "POST",
                    headers: { "X-Requested-From": window.location.pathname + window.location.search },
                });
                const json = await res.json();

                console.log("[DEBUG] 서버 응답 전체:", json);

                if (!json || !json.data) {
                    console.error("[ERROR] 서버에서 data가 null임:", json);
                    alert("상태 변경 실패: " + (json.message ?? "데이터가 비어 있습니다."));
                    return;
                }



                if (json.success) {
                    const newStatus = json.data.isActive ? "활성" : "비활성";
                    // 모달 상태 갱신
                    document.getElementById("modalStatus").innerText = newStatus;

                    // 버튼 텍스트 변경
                    toggleBtn.innerText = newStatus === "활성" ? "정지" : "해제";

                    // 테이블의 상태 칸도 즉시 갱신
                    const targetRow = document.querySelector(`button[data-member-id="${memberId}"]`)?.closest("tr");
                    if (targetRow) {
                        const statusCell = targetRow.children[4];
                        statusCell.innerHTML = json.data.isActive
                            ? `<span class="status-active">활성</span>`
                            : `<span class="status-banned">비활성</span>`;
                    }
                    alert("상태가 변경되었습니다.");
                } else {
                    alert("⚠️ 상태 변경 실패: " + (json.message ?? "서버에서 데이터를 받지 못했습니다."));
                    return;
                }
            } catch (err) {
                console.error("요청 중 오류:", err);
                alert("상태 변경 중 오류가 발생했습니다.");
            }
        });

    });
</script>