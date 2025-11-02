<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/notice_management.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="notice"/>
</jsp:include>

<%--nickname & password modal--%>
<jsp:include page="/WEB-INF/views/page/admin/notice_title_edit_modal.jsp" />

<div class="admin-content-container">
    <div class="notice-board-container">
        <h2 class="admin-page-title">공지사항 이미지 관리</h2>

        <!-- 카드 6개 영역 (3열 2행) -->
        <div class="notice-card-grid">
            <!-- 카드 1~6 반복 -->
            <c:forEach begin="1" end="6" var="i">
                <div class="notice-card" data-card-idx="${i}">
                    <div class="notice-image-wrapper">
                        <img src=""
                             class="notice-image"
                             alt="공지사항 ${i}" />
                        <div class="notice-image-overlay">
                            <span>공지 이미지 변경</span>
                        </div>
                        <input type="file" class="notice-image-input" accept="image/*" style="display: none;" />
                    </div>
                    <div class="notice-info-box">
                        <div class="notice-title-box">
                            <div class="notice-title-with-edit">
                                <span class="notice-title">공지사항 제목 ${i}</span>
                                <button class="notice-title-edit-btn" onclick="editNoticeTitle(${i})">✏️</button>
                            </div>
                            <button class="notice-reset-btn" onclick="initialize(${i})">🗑️</button>
                        </div>
                        <div class="notice-status-box">
                            <span class="notice-status-label">게시 상태:</span>
                            <button class="notice-status-btn inactive" onclick="toggleNoticeStatus(${i})">비활성</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<style>
    .admin-content-container {
        min-height: 600px;
        height: auto;
        padding: 20px;
    }

    .notice-board-container {
        width: 98%;
        margin: 0 auto;
        background: #fff;
        border-radius: 10px;
        padding: 25px;
        /* 그림자 제거 */
    }

    .admin-page-title {
        font-size: 24px;
        font-weight: 700;
        color: #333;
        margin-bottom: 30px;
        text-align: center;
    }

    /* 카드 그리드 레이아웃 - 3열 2행 고정 */
    .notice-card-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
        margin-top: 20px;
    }

    /* 개별 카드 스타일 */
    .notice-card {
        background: #fff;
        border: 2px solid #ddd;
        border-radius: 15px;
        padding: 15px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .notice-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 15px rgba(0,0,0,0.15);
    }

    /* 이미지 영역 */
    .notice-image-wrapper {
        position: relative;
        width: 100%;
        height: 350px;
        margin-bottom: 15px;
        cursor: pointer;
        border-radius: 10px;
        overflow: hidden;
    }

    .notice-image {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 10px;
        transition: filter 0.3s ease, transform 0.3s ease;
    }

    .notice-image-wrapper:hover .notice-image {
        filter: brightness(70%);
        transform: scale(1.03);
    }

    /* 이미지 오버레이 */
    .notice-image-overlay {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 16px;
        font-weight: bold;
        opacity: 0;
        transition: opacity 0.3s ease;
        border-radius: 10px;
    }

    .notice-image-wrapper:hover .notice-image-overlay {
        opacity: 1;
    }

    /* 공지 정보 영역 */
    .notice-info-box {
        padding: 10px 5px;
    }

    /* 제목 영역 - 제목+편집버튼 묶음과 초기화 버튼을 양쪽 끝에 배치 */
    .notice-title-box {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 12px;
    }

    /* 제목과 편집 버튼을 함께 묶는 컨테이너 */
    .notice-title-with-edit {
        display: flex;
        align-items: center;
        gap: 5px;
        flex: 1;
        overflow: hidden;
    }

    .notice-title {
        font-size: 16px;
        font-weight: bold;
        color: #333;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    /* 편집 버튼 */
    .notice-title-edit-btn {
        background: none;
        border: none;
        font-size: 18px;
        cursor: pointer;
        opacity: 0.6;
        transition: opacity 0.2s ease, transform 0.2s ease;
        padding: 0;
        flex-shrink: 0;
    }

    .notice-title-edit-btn:hover {
        opacity: 1;
        transform: scale(1.2);
    }

    /* 초기화 버튼 - 우측 끝 배치 */
    .notice-reset-btn {
        background: none;
        border: none;
        font-size: 18px;
        cursor: pointer;
        opacity: 0.6;
        transition: opacity 0.2s ease, transform 0.2s ease;
        padding: 0;
        flex-shrink: 0;
        margin-left: 8px;
    }

    .notice-reset-btn:hover {
        opacity: 1;
        transform: scale(1.2);
    }

    /* 게시 상태 영역 */
    .notice-status-box {
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 10px;
    }

    .notice-status-label {
        font-size: 14px;
        color: #666;
        font-weight: 600;
    }

    .notice-status-btn {
        padding: 6px 14px;
        border: none;
        border-radius: 8px;
        font-size: 13px;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.2s ease;
    }

    .notice-status-btn.active {
        background-color: #27ae60;
        color: white;
    }

    .notice-status-btn.inactive {
        background-color: #e74c3c;
        color: white;
    }

    .notice-status-btn:hover {
        opacity: 0.85;
        transform: scale(1.05);
    }
</style>


<script src="<c:url value='/js/page/admin/notice_management.js'/>"></script>
<script>
    document.addEventListener("DOMContentLoaded", () => {

        // 공지사항 카드 데이터 조회
        loadNoticeCard()


        // 모든 카드에 이미지 클릭 이벤트 추가
        document.querySelectorAll('.notice-card').forEach(card => {
            const imageWrapper = card.querySelector('.notice-image-wrapper');
            const imageInput = card.querySelector('.notice-image-input');
            const imageElement = card.querySelector('.notice-image');
            const cardIdx = card.getAttribute('data-card-idx');
            const allowedExt = ["png", "jpg", "jpeg", "webp"];

            // 이미지 영역 클릭 시 파일 선택 창 열기
            imageWrapper.addEventListener('click', () => {
                imageInput.click();
            });

            // 파일 선택 시 이미지 미리보기
            imageInput.addEventListener('change', (e) => {
                const file = e.target.files[0];
                if (file) {
                    // 이미지 파일인지 확인
                    if (!file.type.startsWith('image/')) {
                        alert('이미지 파일만 업로드 가능합니다.');
                        imageInput.value = '';
                        return;
                    }

                    // 파일 확장자 검사
                    const fileExt = file.name.split(".").pop().toLowerCase();
                    if (!allowedExt.includes(fileExt)) {
                        alert("PNG, JPG, JPEG 형식의 정적 이미지 파일만 업로드 가능합니다.");
                        imageInput.value = ""; // 선택 초기화
                        return;
                    }

                    // 파일 크기 체크 (10MB 제한)
                    if (file.size > 10 * 1024 * 1024) {
                        alert('이미지 크기는 10MB 이하여야 합니다.');
                        imageInput.value = ""; // 선택 초기화
                        return;
                    }

                    // FileReader로 미리보기
                    const reader = new FileReader();
                    reader.onload = (event) => {
                        imageElement.src = event.target.result;
                        console.log(`카드 \${cardIdx} 이미지 변경됨`);

                        // 이미지 업로드 수행
                        editNoticeImage(cardIdx, file);
                    };
                    reader.readAsDataURL(file);
                }
            });
        });
    });

    // 공지글 조회
    async function loadNoticeCard() {

        try {
            const res = await fetch(`/admin/api/notices`, {
                method: "GET"
            });

            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {

                // 동적 로딩 시, 권한이 없거나 로그인하지 않은 경우
                if (rp.statusCode === 401 || rp.statusCode === 403) {

                    // 홈으로 리다이렉트
                    window.location.href = "/"

                    // 로직 중단
                    return;
                }

                // 기타 예기치 않은 오류가 발생한 경우
                alert(rp.message || "공지사항 로드 중 오류가 발생했습니다.");
                return;
            }

            // 공지사항 정보 조회
            const notices = rp.data;

            // 내용 채우기
            notices.forEach(notice => {
                const cardIdx = notice.idx;
                const card = document.querySelector(`.notice-card[data-card-idx="\${cardIdx}"]`);

                if (!card) {
                    console.warn(`카드 인덱스 \${cardIdx}에 해당하는 카드를 찾을 수 없습니다.`);
                    return;
                }

                // 1. 이미지 설정
                const imageElement = card.querySelector('.notice-image');
                imageElement.src = notice.noticeImage ? "${fileDomain}/\${notice.noticeImage.filePath}" : "<c:url value='/img/png/default_image.png'/>";

                // 2. 제목 설정
                const titleElement = card.querySelector('.notice-title');
                titleElement.textContent = notice.title ? notice.title : `제목 입력 \${cardIdx}`;

                // 3. 활성 상태 설정
                const statusBtn = card.querySelector('.notice-status-btn');
                if (notice.isActivate) {
                    statusBtn.classList.remove('inactive');
                    statusBtn.classList.add('active');
                    statusBtn.textContent = '활성';
                } else {
                    statusBtn.classList.remove('active');
                    statusBtn.classList.add('inactive');
                    statusBtn.textContent = '비활성';
                }

                console.log(`카드 \${cardIdx} 데이터 채우기 완료`);
            });


        } catch (error) {
            console.error('공지사항 업로드 오류:', error);
            alert('공지사항 업로드 중 오류가 발생했습니다.');
        }

    }


    // 게시 상태 토글 함수
    function toggleNoticeStatus(cardIdx) {
        const card = document.querySelector(`.notice-card[data-card-idx="\${cardIdx}"]`);
        const statusBtn = card.querySelector('.notice-status-btn');

        // 실제 서버에 상태 업데이트 요청
        updateNoticeStatus(cardIdx, statusBtn.classList.contains('active'));
    }

    // 초기화 버튼 함수
    async function initialize(cardIdx) {
        console.log(`카드 \${cardIdx}: 초기화 요청`);

        if(confirm(`현재 공지사항을 초기화 하시겠습니까?\n선택 공지사항 번호 : \${cardIdx}`)) {

            try {

                // REST API 요청
                const res = await fetch(`/admin/api/notices/\${cardIdx}/initialize`, {
                    method: "PUT"
                });

                // 서버 JSON 응답 문자열 파싱
                const rp = await res.json();
                console.log("서버 응답:", rp);

                // 요청 실패 처리
                if (!res.ok || !rp.success) {

                    // 로그인이 필요한 경우
                    if (rp.statusCode === 401) {

                        // 로그인 필요 안내 전달
                        if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                            window.location.href = rp.redirect || "/login";
                        }

                        // 로직 중단
                        return;
                    }

                    // 권한이 부족한 경우
                    if (rp.statusCode === 403) {
                        alert(rp.message || "접근 권한이 없습니다.");
                        return;
                    }

                    // 기타 예기치 않은 오류가 발생한 경우
                    alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                    return;
                }

                // 성공 응답
                alert(rp.message || "공지사항 초기화를 완료했습니다.");

                // 초기화 결과 표시
                const card = document.querySelector(`.notice-card[data-card-idx="\${cardIdx}"]`);
                const imageElement = card.querySelector('.notice-image');
                const titleElement = card.querySelector('.notice-title');
                const statusBtn = card.querySelector('.notice-status-btn');

                imageElement.src = "<c:url value='/img/png/default_image.png'/>";
                titleElement.textContent = `제목 입력 \${cardIdx}`;
                statusBtn.classList.remove('active');
                statusBtn.classList.add('inactive');
                statusBtn.textContent = '비활성';

            } catch (error) {
                console.error('초기화 실패 오류:', error);
                alert('초기화 중 오류가 발생했습니다.');
            }
        }
    }


    // 이미지 수정
    async function editNoticeImage(cardIdx, file) {
        const formData = new FormData();
        formData.append('noticeImageFile', file);
        formData.append('idx', cardIdx);

        try {
            const res = await fetch(`/admin/api/notices/\${cardIdx}/notice_image`, {
                headers: {'X-Requested-From': window.location.pathname + window.location.search},
                method: "PATCH",
                body: formData
            });

            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {

                // 로그인이 필요한 경우
                if (rp.statusCode === 401) {

                    // 로그인 필요 안내 전달
                    if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                        window.location.href = rp.redirect || "/login";
                    }

                    // 로직 중단
                    return;
                }

                // 권한이 부족한 경우
                if (rp.statusCode === 403) {
                    alert(rp.message || "접근 권한이 없습니다.");
                    return;
                }

                // 기타 예기치 않은 오류가 발생한 경우
                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            // 공지명 동적 변경




        } catch (error) {
            console.error('이미지 업로드 오류:', error);
            alert('이미지 업로드 중 오류가 발생했습니다.');
        }
    }


    // 게시 상태 업데이트 함수 (서버 통신)
    async function updateNoticeStatus(cardIdx, isActive) {
        try {

            // REST API 요청
            const url = isActive ? `/admin/api/notices/\${cardIdx}/inactivate` : `/admin/api/notices/\${cardIdx}/activate`;
            const res = await fetch(url, {
                method: 'PATCH'
            });

            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {

                // 로그인이 필요한 경우
                if (rp.statusCode === 401) {

                    // 로그인 필요 안내 전달
                    if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                        window.location.href = rp.redirect || "/login";
                    }

                    // 로직 중단
                    return;
                }

                // 권한이 부족한 경우
                if (rp.statusCode === 403) {
                    alert(rp.message || "접근 권한이 없습니다.");
                    return;
                }

                // 기타 예기치 않은 오류가 발생한 경우
                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            // 성공 처리
            const card = document.querySelector(`.notice-card[data-card-idx="\${cardIdx}"]`);
            const statusBtn = card.querySelector('.notice-status-btn');

            if (statusBtn.classList.contains('active')) {
                statusBtn.classList.remove('active');
                statusBtn.classList.add('inactive');
                statusBtn.textContent = '비활성';
                console.log(`카드 \${cardIdx}: 비활성으로 변경`);
            } else {
                statusBtn.classList.remove('inactive');
                statusBtn.classList.add('active');
                statusBtn.textContent = '활성';
                console.log(`카드 \${cardIdx}: 활성으로 변경`);
            }

        } catch (error) {
            console.error('상태 업데이트 오류:', error);
            alert('상태 업데이트 중 오류가 발생했습니다.');
        }
    }
</script>
