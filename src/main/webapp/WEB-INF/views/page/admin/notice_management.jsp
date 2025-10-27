<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/notice_management.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="notice"/>
</jsp:include>

<div class="admin-content-container">
    <div class="notice-board-container">
        <h2 class="admin-page-title">공지사항 목록</h2>
        <div class="button-div">
        <button class="resister-notice-button">공지사항 등록</button>
        </div>
            <!-- 검색창 -->
        <div class="notice-search-bar">
            <input type="text" placeholder="제목 또는 내용 검색..." />
            <select>
                <option value="">전체 유형</option>
                <option value="NORMAL">일반공지</option>
                <option value="EVENT">이벤트</option>
                <option value="SYSTEM">시스템</option>
            </select>
            <button>검색</button>
        </div>

        <!-- 테이블 -->
        <table class="notice-board-table">
            <thead>
            <tr>
                <th>No</th>
                <th>제목</th>
                <th>유형</th>
                <th>등록기간</th>
                <th>팝업여부</th>
                <th>작성일</th>
                <th>관리</th>
            </tr>
            </thead>
            <tbody>
            <%--
            <tr>
                <td>1</td>
                <td class="title-cell">[시스템 점검 안내] 10월 25일(금) 02:00 ~ 05:00</td>
                <td>시스템</td>
                <td>2025-10-20 ~ 2025-10-25</td>
                <td><span class="popup-badge on">ON</span></td>
                <td>2025-10-22</td>
                <td>
                    <button class="btn-view">보기</button>
                    <button class="btn-delete">삭제</button>
                </td>
            </tr>
            --%>
            </tbody>
        </table>
    </div>
</div>




<%-- 모달창 --%>
<div class="notice-container" id="noticeModal">
    <h2 class="admin-page-title">공지사항 등록</h2>

    <form id="noticeForm" method="post">
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" placeholder="공지 제목을 입력하세요" />
            <div class="asynchronous-message-wrong" id="titleError"></div>
        </div>

        <div class="form-group">
            <label for="startedAt">등록기간</label>
            <label>from</label>
            <input type="date" id="startedAt" name="startedAt" />
            <div class="asynchronous-message-wrong" id="startedAtError"></div>
            <label for="endedAt">to</label>
            <input type="date" id="endedAt" name="endedAt" />
            <div class="asynchronous-message-wrong" id="endedAtError"></div>
        </div>

        <div class="form-group">
            <label for="modal-content">내용</label>
            <textarea id="modal-content" name="content" placeholder="공지 내용을 입력하세요" rows="8" required></textarea>
            <div class="asynchronous-message-wrong" id="contentError"></div>
        </div>

        <div class="form-group">
            <label for="noticeType">공지 유형</label>
            <select id="noticeType" name="noticeType">
                <option value="NORMAL">일반공지</option>
                <option value="EVENT">이벤트</option>
                <option value="SYSTEM">시스템 점검</option>
            </select>
        </div>
        <div class="asynchronous-message-wrong" id="noticeTypeError"></div>

        <div class="form-actions">
            <button type="submit" class="btn-submit">등록하기</button>
            <button type="button" id="closeModalBtn" class="btn-cancel">닫기</button>
        </div>
    </form>
</div>

<style>
    .admin-content-container {
        border:2px solid black;
        min-height: 600px;
        height:auto;
    }

    .resister-notice-button {
        background-color: #4a90e2;
        color: #fff;
        border: none;
        padding: 10px 20px;
        border-radius: 8px;
        font-size: 15px;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.2s ease;
        box-shadow: 0 2px 6px rgba(0,0,0,0.15);
        margin-bottom:10px;
        text-align: right;
    }

    .resister-notice-button:hover {
        background-color: #357ac8;
        transform: translateY(-1px);
        box-shadow: 0 3px 10px rgba(0,0,0,0.2);
    }

    .resister-notice-button:active {
        transform: translateY(1px);
        box-shadow: 0 1px 4px rgba(0,0,0,0.2);
    }
    .button-div{
        text-align: right;
    }

    /*모달창*/
    .admin-page-title {
        font-size: 22px;
        font-weight: 700;
        color: #333;
        margin-bottom: 10px;
    }

    .form-group {
        display: flex;
        flex-direction: column;
        margin-bottom: 20px;
        height:auto;
    }

    .form-group label {
        font-weight: 600;
        margin-bottom: 8px;
        color: #444;
        height:auto;
    }

    .form-group input[type="text"]{
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 15px;
        height:auto;

    }

    .form-group textarea{
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 15px;
        height:auto;

    }

    .form-group select {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 15px;
        height:auto;
    }

    .checkbox-group {
        margin-top: 10px;
    }

    .form-actions {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
    }

    .btn-submit {
        background: #4a90e2;
        color: #fff;
        padding: 8px 16px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: 0.2s;
    }

    .btn-submit:hover {
        background: #357ac8;
    }

    .btn-cancel {
        background: #ccc;
        color: #333;
        padding: 8px 16px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
    }

    .notice-container {
        display: none; /* 🔥 기본적으로 숨김 */
        position: fixed;
        height:850px;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 1001;
        width: 600px;
        background: #fff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.2);
    }

    /*
    공지사항 리스트
    */
    .notice-board-container {
        width: 90%;
        margin: 0 auto;
        background: #fff;
        border-radius: 10px;
        padding: 25px;
        box-shadow: 0 3px 10px rgba(0,0,0,0.1);
    }

    .notice-search-bar {
        display: flex;
        gap: 10px;
        margin-bottom: 20px;
    }

    .notice-search-bar input {
        flex: 6;
        padding: 8px 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
    }

    .notice-search-bar select {
        flex: 2;
        padding: 8px 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
    }

    .notice-search-bar button {
        flex: 1;
        background:orange;
        color: white;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: 0.2s;
    }
    .notice-search-bar button:hover {
        background: #357ac8;
    }

    /* 테이블 */
    .notice-board-table {
        width: 100%;
        border-collapse: collapse;
        text-align: center;
    }

    .notice-board-table th {
        background: #f5f6fa;
        padding: 12px;
        border-bottom: 1px solid #ccc;
        font-weight: 600;
    }

    .notice-board-table td {
        padding: 10px;
        border-bottom: 1px solid #eee;
        font-size: 14px;
    }

    .notice-board-table tr:hover {
        background-color: #f9fbff;
    }

    .title-cell {
        text-align: left;
        padding-left: 15px;
    }

    /* 팝업 여부 뱃지 */
    .popup-badge {
        padding: 4px 8px;
        border-radius: 6px;
        font-weight: 600;
        color: #fff;
        font-size: 12px;
    }
    .popup-badge.on {
        background: #27ae60;
    }
    .popup-badge.off {
        background: #e74c3c;
    }

    /* 버튼 */
    .btn-view, .btn-delete {
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
    .btn-delete {
        background: #e74c3c;
        margin-left: 5px;
    }
    .btn-delete:hover {
        background: #c0392b;
    }

    .asynchronous-message {
        color: #999;
        font-size: 14px;
        opacity: 0.7;
    }

    .asynchronous-message-wrong {
        color: red;
        font-size: 14px;
        opacity: 0.7;
    }


</style>

<script src="<c:url value='/js/page/admin/notice_management.js'/>"></script>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const modal = document.getElementById("noticeModal");
        const overlay = document.createElement("div");
        overlay.classList.add("modal-overlay");
        document.body.appendChild(overlay);

        const openBtn = document.querySelector(".resister-notice-button");
        const closeBtn = document.getElementById("closeModalBtn");

        // ✅ 함수들을 먼저 정의

        // HTML 이스케이프 (XSS 방지)
        function escapeHtml(text) {
            if (!text) return '';
            const div = document.createElement('div');
            div.textContent = text;
            return div.innerHTML;
        }

        // 폼 초기화 함수
        function clearForm() {
            document.getElementById("noticeForm").reset();

            // 에러 메시지 초기화
            ['title', 'startedAt', 'endedAt', 'content', 'noticeType'].forEach(field => {
                const errorElem = document.getElementById(field + "Error");
                if (errorElem) errorElem.textContent = '';
            });
        }

        // 테이블에 공지사항 추가하는 함수
        function addNoticeToTable(notice) {
            const tbody = document.querySelector(".notice-board-table tbody");

            // 날짜
            const startedAt = notice.startedAt;
            const endedAt = notice.endedAt;

            // NoticeType 한글 변환
            const noticeTypeText = notice.noticeType.value;

            // 팝업 여부 (isActivate 기준)
            const popupBadge = notice.isActivate
                ? '<span class="popup-badge on">ON</span>'
                : '<span class="popup-badge off">OFF</span>';

            // 작성일 (cdate 사용)
            const createdDate = notice.cdate ? notice.cdate.split('T')[0] : new Date().toISOString().split('T')[0];

            // 새 행 생성
            const newRow = document.createElement('tr');
            newRow.setAttribute('data-notice-id', notice.noticeId);
            newRow.innerHTML = `
                <td>\${notice.noticeId}</td>
                <td class="title-cell">\${escapeHtml(notice.title)}</td>
                <td>\${noticeTypeText}</td>
                <td>\${startedAt} ~ \${endedAt}</td>
                <td>\${popupBadge}</td>
                <td>\${createdDate}</td>
                <td>
                    <button class="btn-view" onclick="viewNotice(\${notice.noticeId})">보기</button>
                    <button class="btn-delete" onclick="deleteNotice(\${notice.noticeId})">삭제</button>
                </td>
        `;

            // 테이블 맨 위에 추가
            tbody.insertBefore(newRow, tbody.firstChild);
        }

        // 🔹 모달 열기
        openBtn.addEventListener("click", () => {
            modal.style.display = "block";
            overlay.style.display = "block";
        });

        // 🔹 모달 닫기
        closeBtn.addEventListener("click", () => {
            modal.style.display = "none";
            overlay.style.display = "none";
            clearForm();
        });

        // 🔹 배경 클릭 시 닫기
        overlay.addEventListener("click", () => {
            modal.style.display = "none";
            overlay.style.display = "none";
            clearForm();
        });

        // 🔹 등록 버튼 제출
        const form = document.getElementById("noticeForm");
        form.addEventListener("submit", async (e) => {
            e.preventDefault();

            if (confirm("공지사항을 등록하시겠습니까?")) {

                try {
                    // FormData 생성
                    const formData = new FormData();
                    formData.append("title", document.getElementById("title").value);
                    formData.append("startedAt", document.getElementById("startedAt").value);
                    formData.append("endedAt", document.getElementById("endedAt").value);
                    formData.append("content", document.getElementById("modal-content").value);
                    formData.append("noticeType", document.getElementById("noticeType").value);

                    // REST API 요청
                    const res = await fetch("/admin/api/notices", {
                        method: "POST",
                        body: formData
                    });

                    // JSON 데이터 파싱
                    const rp = await res.json();
                    console.log("서버 응답:", rp);

                    // 실패 처리
                    if (!res.ok || !rp.success) {

                        // 로그인이 필요한 경우
                        if (rp.statusCode === 401) {
                            if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                                window.location.href = rp.redirect || "/login";
                            }
                            return;
                        }

                        // 권한이 부족한 경우
                        if (rp.statusCode === 403) {
                            alert(rp.message || "접근 권한이 없습니다.");
                            return;
                        }

                        // 유효성 검사에 실패한 경우
                        if (rp.inputErrors) {
                            Object.entries(rp.inputErrors).forEach(([field, message]) => {
                                const errorElem = document.getElementById(field + "Error");
                                if (errorElem) {
                                    errorElem.textContent = message;
                                }
                            });
                            return;
                        }

                        // 기타 예기치 않은 오류
                        alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                        return;
                    }

                    // ✅ 성공 처리 - 테이블에 즉시 추가
                    const notice = rp.data;
                    addNoticeToTable(notice);

                    // 모달 닫기
                    modal.style.display = "none";
                    overlay.style.display = "none";

                    // 폼 초기화
                    clearForm();
                    alert("공지사항이 등록되었습니다.");

                } catch (error) {
                    console.error("공지 등록 실패:", error);
                    alert("오류가 발생했습니다. 다시 시도해주세요.");
                }
            }
        });
    });


    // 폼 초기화 함수
    function clearForm() {
        document.getElementById("noticeForm").reset();

        // 에러 메시지 초기화
        ['title', 'startedAt', 'endedAt', 'content', 'noticeType'].forEach(field => {
            const errorElem = document.getElementById(`\${field}Error`);
            if (errorElem) errorElem.textContent = '';
        });
    }

    // ✅ 공지사항 보기 함수 (전역 함수로 선언)
    function viewNotice(noticeId) {
        console.log("공지사항 보기:", noticeId);
        alert("공지사항 ID: " + noticeId);
    }

    // ✅ 공지사항 삭제 함수 (전역 함수로 선언)
    async function deleteNotice(noticeId) {
    if (!confirm("정말 삭제하시겠습니까? noticeId = " + noticeId)) {
        return;
    }
}
</script>