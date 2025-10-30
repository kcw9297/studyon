<%-- 운영자 페이지 배너 관리 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/banner_management.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="banner"/>
</jsp:include>

<%--배너 제목 편집 modal--%>
<jsp:include page="/WEB-INF/views/page/admin/banner_title_edit_modal.jsp" />

<div class="admin-content-container">
    <div class="banner-board-container">
        <h2 class="admin-page-title">홈페이지 배너 관리</h2>

        <!-- 카드 9개 영역 (3열 3행) -->
        <div class="banner-card-grid">
            <!-- 카드 1~9 반복 -->
            <c:forEach begin="1" end="9" var="i">
                <div class="banner-card" data-card-idx="${i}">
                    <div class="banner-image-wrapper">
                        <img src=""
                             class="banner-image"
                             alt="배너 ${i}" />
                        <div class="banner-image-overlay">
                            <span>배너 이미지 변경</span>
                        </div>
                        <input type="file" class="banner-image-input" accept="image/*" style="display: none;" />
                    </div>
                    <div class="banner-info-box">
                        <div class="banner-title-box">
                            <div class="banner-title-with-edit">
                                <span class="banner-title">배너 제목 ${i}</span>
                                <button class="banner-title-edit-btn" onclick="editBannerTitle(${i})">✏️</button>
                            </div>
                            <button class="banner-reset-btn" onclick="initialize(${i})">🗑️</button>
                        </div>
                        <div class="banner-status-box">
                            <span class="banner-status-label">게시 상태:</span>
                            <button class="banner-status-btn inactive" onclick="toggleBannerStatus(${i})">비활성</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<style>
    .admin-content-container {
        border: 2px solid black;
        min-height: 600px;
        height: auto;
        padding: 20px;
    }

    .banner-board-container {
        width: 98%;
        margin: 0 auto;
        background: #fff;
        border-radius: 10px;
        padding: 25px;
    }

    .admin-page-title {
        font-size: 24px;
        font-weight: 700;
        color: #333;
        margin-bottom: 30px;
        text-align: center;
    }

    /* 카드 그리드 레이아웃 - 3열 3행 고정 */
    .banner-card-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
        margin-top: 20px;
    }

    /* 개별 카드 스타일 */
    .banner-card {
        background: #fff;
        border: 2px solid #ddd;
        border-radius: 15px;
        padding: 15px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .banner-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 15px rgba(0,0,0,0.15);
    }

    /* 이미지 영역 - 배너는 가로가 넓고 세로가 짧음 */
    .banner-image-wrapper {
        position: relative;
        width: 100%;
        height: 180px;
        margin-bottom: 15px;
        cursor: pointer;
        border-radius: 10px;
        overflow: hidden;
        background-color: #f5f5f5; /* 빈 공간 배경색 */
    }

    .banner-image {
        width: 100%;
        height: 100%;
        object-fit: contain; /* cover에서 contain으로 변경 */
        border-radius: 10px;
        transition: filter 0.3s ease, transform 0.3s ease;
    }

    .banner-image-wrapper:hover .banner-image {
        filter: brightness(70%);
        transform: scale(1.03);
    }

    /* 이미지 오버레이 */
    .banner-image-overlay {
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

    .banner-image-wrapper:hover .banner-image-overlay {
        opacity: 1;
    }

    /* 배너 정보 영역 */
    .banner-info-box {
        padding: 10px 5px;
    }

    /* 제목 영역 */
    .banner-title-box {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 12px;
    }

    .banner-title-with-edit {
        display: flex;
        align-items: center;
        gap: 5px;
        flex: 1;
        overflow: hidden;
    }

    .banner-title {
        font-size: 16px;
        font-weight: bold;
        color: #333;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    /* 편집 버튼 */
    .banner-title-edit-btn {
        background: none;
        border: none;
        font-size: 18px;
        cursor: pointer;
        opacity: 0.6;
        transition: opacity 0.2s ease, transform 0.2s ease;
        padding: 0;
        flex-shrink: 0;
    }

    .banner-title-edit-btn:hover {
        opacity: 1;
        transform: scale(1.2);
    }

    /* 초기화 버튼 */
    .banner-reset-btn {
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

    .banner-reset-btn:hover {
        opacity: 1;
        transform: scale(1.2);
    }

    /* 게시 상태 영역 */
    .banner-status-box {
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 10px;
    }

    .banner-status-label {
        font-size: 14px;
        color: #666;
        font-weight: 600;
    }

    .banner-status-btn {
        padding: 6px 14px;
        border: none;
        border-radius: 8px;
        font-size: 13px;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.2s ease;
    }

    .banner-status-btn.active {
        background-color: #27ae60;
        color: white;
    }

    .banner-status-btn.inactive {
        background-color: #e74c3c;
        color: white;
    }

    .banner-status-btn:hover {
        opacity: 0.85;
        transform: scale(1.05);
    }
</style>

<script>
    document.addEventListener("DOMContentLoaded", () => {

        // 배너 카드 데이터 조회
        loadBannerCard();

        // 모든 카드에 이미지 클릭 이벤트 추가
        document.querySelectorAll('.banner-card').forEach(card => {
            const imageWrapper = card.querySelector('.banner-image-wrapper');
            const imageInput = card.querySelector('.banner-image-input');
            const imageElement = card.querySelector('.banner-image');
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
                        imageInput.value = "";
                        return;
                    }

                    // 파일 크기 체크 (10MB 제한)
                    if (file.size > 10 * 1024 * 1024) {
                        alert('이미지 크기는 10MB 이하여야 합니다.');
                        imageInput.value = "";
                        return;
                    }

                    // FileReader로 미리보기
                    const reader = new FileReader();
                    reader.onload = (event) => {
                        imageElement.src = event.target.result;
                        console.log(`카드 \${cardIdx} 이미지 변경됨`);

                        // 이미지 업로드 수행
                        editBannerImage(cardIdx, file);
                    };
                    reader.readAsDataURL(file);
                }
            });
        });
    });

    // 배너 조회
    async function loadBannerCard() {
        try {
            const res = await fetch(`/admin/api/banners`, {
                method: "GET"
            });

            const rp = await res.json();
            console.log("서버 응답:", rp);

            if (!res.ok || !rp.success) {
                if (rp.statusCode === 401 || rp.statusCode === 403) {
                    window.location.href = "/";
                    return;
                }
                alert(rp.message || "배너 로드 중 오류가 발생했습니다.");
                return;
            }

            const banners = rp.data;

            banners.forEach(banner => {
                const cardIdx = banner.idx;
                const card = document.querySelector(`.banner-card[data-card-idx="\${cardIdx}"]`);

                if (!card) {
                    console.warn(`카드 인덱스 \${cardIdx}에 해당하는 카드를 찾을 수 없습니다.`);
                    return;
                }

                // 1. 이미지 설정
                const imageElement = card.querySelector('.banner-image');
                imageElement.src = banner.bannerImage ? `${fileDomain}/\${banner.bannerImage.filePath}` : "<c:url value='/img/png/default_member_profile_image.png'/>";

                // 2. 제목 설정
                const titleElement = card.querySelector('.banner-title');
                titleElement.textContent = banner.title ? banner.title : `제목 입력 \${cardIdx}`;

                // 3. 활성 상태 설정
                const statusBtn = card.querySelector('.banner-status-btn');
                if (banner.isActivate) {
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
            console.error('배너 로드 오류:', error);
        }
    }

    // 게시 상태 토글 함수
    function toggleBannerStatus(cardIdx) {
        const card = document.querySelector(`.banner-card[data-card-idx="\${cardIdx}"]`);
        const statusBtn = card.querySelector('.banner-status-btn');

        updateBannerStatus(cardIdx, statusBtn.classList.contains('active'));
    }

    // 초기화 버튼 함수
    async function initialize(cardIdx) {
        console.log(`카드 \${cardIdx}: 초기화 요청`);

        if(confirm(`현재 배너를 초기화 하시겠습니까?\n선택 배너 번호 : \${cardIdx}`)) {
            try {
                const res = await fetch(`/admin/api/banners/\${cardIdx}/initialize`, {
                    method: "PUT"
                });

                const rp = await res.json();
                console.log("서버 응답:", rp);

                if (!res.ok || !rp.success) {
                    if (rp.statusCode === 401) {
                        if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                            window.location.href = rp.redirect || "/login";
                        }
                        return;
                    }

                    if (rp.statusCode === 403) {
                        alert(rp.message || "접근 권한이 없습니다.");
                        return;
                    }

                    alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                    return;
                }

                alert(rp.message || "배너 초기화를 완료했습니다.");

                const card = document.querySelector(`.banner-card[data-card-idx="\${cardIdx}"]`);
                const imageElement = card.querySelector('.banner-image');
                const titleElement = card.querySelector('.banner-title');
                const statusBtn = card.querySelector('.banner-status-btn');

                imageElement.src = "<c:url value='/img/png/default_member_profile_image.png'/>";
                titleElement.textContent = `제목 입력 \${cardIdx}`;
                statusBtn.classList.remove('active');
                statusBtn.classList.add('inactive');
                statusBtn.textContent = '비활성';

            } catch (error) {
                console.error('초기화 실패 오류:', error);
            }
        }
    }

    // 이미지 수정
    async function editBannerImage(cardIdx, file) {
        const formData = new FormData();
        formData.append('bannerImageFile', file);
        formData.append('idx', cardIdx);

        try {
            const res = await fetch(`/admin/api/banners/\${cardIdx}/banner_image`, {
                headers: {'X-Requested-From': window.location.pathname + window.location.search},
                method: "PATCH",
                body: formData
            });

            const rp = await res.json();
            console.log("서버 응답:", rp);

            if (!res.ok || !rp.success) {
                if (rp.statusCode === 401) {
                    if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                        window.location.href = rp.redirect || "/login";
                    }
                    return;
                }

                if (rp.statusCode === 403) {
                    alert(rp.message || "접근 권한이 없습니다.");
                    return;
                }

                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

        } catch (error) {
            console.error('이미지 업로드 오류:', error);
        }
    }

    // 게시 상태 업데이트 함수
    async function updateBannerStatus(cardIdx, isActive) {
        try {
            const url = isActive ? `/admin/api/banners/\${cardIdx}/inactivate` : `/admin/api/banners/\${cardIdx}/activate`;
            const res = await fetch(url, {
                method: 'PATCH'
            });

            const rp = await res.json();
            console.log("서버 응답:", rp);

            if (!res.ok || !rp.success) {
                if (rp.statusCode === 401) {
                    if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                        window.location.href = rp.redirect || "/login";
                    }
                    return;
                }

                if (rp.statusCode === 403) {
                    alert(rp.message || "접근 권한이 없습니다.");
                    return;
                }

                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            const card = document.querySelector(`.banner-card[data-card-idx="\${cardIdx}"]`);
            const statusBtn = card.querySelector('.banner-status-btn');

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
        }
    }
</script>