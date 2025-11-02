<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- 공지사항 팝업 모달 -->
<div id="noticePopupModal" class="notice-popup-modal">
    <div class="notice-popup-content">
        <!-- 공지사항 슬라이더 -->
        <div class="notice-slider-container">
            <div class="notice-slider" id="noticeSlider">
                <!-- 공지사항 이미지들이 동적으로 추가됨 -->
            </div>

            <!-- 이전/다음 버튼 -->
            <button class="notice-slider-btn prev-btn" onclick="prevNotice()">&lt;</button>
            <button class="notice-slider-btn next-btn" onclick="nextNotice()">&gt;</button>

            <!-- 페이지 인디케이터 -->
            <div class="notice-indicator" id="noticeIndicator"></div>
        </div>

        <!-- 하단 버튼 -->
        <div class="notice-popup-footer">
            <button class="notice-popup-btn dont-show-btn" onclick="dontShowToday()">
                오늘 더 이상 보지 않기
            </button>
            <button class="notice-popup-btn close-btn" onclick="closeNoticePopup()">
                닫기
            </button>
        </div>
    </div>
</div>

<style>
    /* 모달 배경 */
    .notice-popup-modal {
        display: none;
        position: fixed;
        z-index: 9999;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.6);
        animation: fadeIn 0.3s ease;
    }

    /* 모달 컨텐츠 */
    .notice-popup-content {
        position: relative;
        margin: 3% auto;
        width: 90%;
        max-width: 450px;
        background-color: #fff;
        border-radius: 12px;
        box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
        overflow: hidden;
        animation: slideDown 0.4s ease;
    }

    /* 슬라이더 컨테이너 */
    .notice-slider-container {
        position: relative;
        width: 100%;
        height: 650px;
        overflow: hidden;
    }

    /* 슬라이더 */
    .notice-slider {
        display: flex;
        transition: transform 0.5s ease;
        height: 100%;
    }

    /* 개별 공지사항 슬라이드 */
    .notice-slide {
        min-width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        box-sizing: border-box;
    }

    .notice-slide img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }

    /* 이전/다음 버튼 */
    .notice-slider-btn {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        background: rgba(255, 255, 255, 0.9);
        border: none;
        font-size: 24px;
        font-weight: bold;
        color: #333;
        width: 45px;
        height: 45px;
        border-radius: 50%;
        cursor: pointer;
        transition: all 0.2s ease;
        z-index: 10;
    }

    .notice-slider-btn:hover {
        background: rgba(255, 255, 255, 1);
        transform: translateY(-50%) scale(1.1);
    }

    .prev-btn {
        left: 15px;
    }

    .next-btn {
        right: 15px;
    }

    /* 페이지 인디케이터 */
    .notice-indicator {
        position: absolute;
        bottom: 15px;
        left: 50%;
        transform: translateX(-50%);
        display: flex;
        gap: 8px;
        z-index: 10;
    }

    .notice-indicator-dot {
        width: 10px;
        height: 10px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.5);
        cursor: pointer;
        transition: all 0.3s ease;
    }

    .notice-indicator-dot.active {
        background: #fff;
        width: 30px;
        border-radius: 5px;
    }

    /* 하단 버튼 영역 */
    .notice-popup-footer {
        display: flex;
        border-top: 1px solid #e0e0e0;
    }

    .notice-popup-btn {
        flex: 1;
        padding: 20px;
        border: none;
        font-size: 18px;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.2s ease;
    }

    .dont-show-btn {
        background-color: #6c757d;
        color: white;
        border-right: 1px solid #e0e0e0;
    }

    .dont-show-btn:hover {
        background-color: #5a6268;
    }

    .close-btn {
        background-color: #2c3e50;
        color: white;
    }

    .close-btn:hover {
        background-color: #1a252f;
    }

    /* 애니메이션 */
    @keyframes fadeIn {
        from { opacity: 0; }
        to { opacity: 1; }
    }

    @keyframes slideDown {
        from {
            opacity: 0;
            transform: translateY(-50px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    /* 반응형 */
    @media (max-width: 768px) {
        .notice-popup-content {
            width: 95%;
            max-width: 380px;
            margin: 5% auto;
        }

        .notice-slider-container {
            height: 550px;
        }

        .notice-popup-btn {
            font-size: 16px;
            padding: 18px;
        }
    }
</style>

<script>
    // 공지사항 데이터 저장
    let noticeData = [];
    let currentNoticeIndex = 0;
    let autoSlideInterval = null;

    // 페이지 로드 시 실행
    document.addEventListener("DOMContentLoaded", async () => {
        // 쿠키 확인
        if (getCookie('no-notice')) {
            console.log('오늘은 공지사항을 보지 않기로 설정됨');
            return;
        }

        // 공지사항 데이터 로드
        await loadNoticeData();
    });

    // 공지사항 데이터 로드
    async function loadNoticeData() {
        try {
            const res = await fetch('/api/notices', {
                method: 'GET'
            });

            const rp = await res.json();
            console.log("공지사항 응답:", rp);

            if (!res.ok || !rp.success) {
                console.error('공지사항 로드 실패:', rp.message);
                return;
            }

            noticeData = rp.data;

            // 활성화된 공지사항이 있는 경우에만 모달 표시
            if (noticeData && noticeData.length > 0) {
                createNoticeSlides();
                showNoticePopup();
                startAutoSlide();
            }

        } catch (error) {
            console.error('공지사항 로드 오류:', error);
        }
    }

    // 공지사항 슬라이드 생성
    function createNoticeSlides() {
        const slider = document.getElementById('noticeSlider');
        const indicator = document.getElementById('noticeIndicator');

        slider.innerHTML = '';
        indicator.innerHTML = '';

        noticeData.forEach((notice, index) => {
            // 슬라이드 생성
            const slide = document.createElement('div');
            slide.className = 'notice-slide';

            const img = document.createElement('img');
            if (notice.noticeImage && notice.noticeImage.filePath) {
                img.src = `${fileDomain}/\${notice.noticeImage.filePath}`;
            } else {
                img.src = "<c:url value='/img/png/default_image.png'/>";
            }
            img.alt = '공지사항 이미지';

            slide.appendChild(img);
            slider.appendChild(slide);

            // 인디케이터 생성
            const dot = document.createElement('div');
            dot.className = 'notice-indicator-dot';
            if (index === 0) dot.classList.add('active');
            dot.onclick = () => goToNotice(index);
            indicator.appendChild(dot);
        });
    }

    // 모달 표시
    function showNoticePopup() {
        const modal = document.getElementById('noticePopupModal');
        if (modal) {
            modal.style.display = 'block';
        }
    }

    // 모달 닫기
    function closeNoticePopup() {
        const modal = document.getElementById('noticePopupModal');
        if (modal) {
            modal.style.display = 'none';
        }
        stopAutoSlide();
    }

    // 다음 공지사항
    function nextNotice() {
        if (noticeData.length === 0) return;

        currentNoticeIndex = (currentNoticeIndex + 1) % noticeData.length;
        updateSlider();
        resetAutoSlide();
    }

    // 이전 공지사항
    function prevNotice() {
        if (noticeData.length === 0) return;

        currentNoticeIndex = (currentNoticeIndex - 1 + noticeData.length) % noticeData.length;
        updateSlider();
        resetAutoSlide();
    }

    // 특정 공지사항으로 이동
    function goToNotice(index) {
        currentNoticeIndex = index;
        updateSlider();
        resetAutoSlide();
    }

    // 슬라이더 업데이트
    function updateSlider() {
        const slider = document.getElementById('noticeSlider');
        const offset = -currentNoticeIndex * 100;
        slider.style.transform = `translateX(\${offset}%)`;

        // 인디케이터 업데이트
        const dots = document.querySelectorAll('.notice-indicator-dot');
        dots.forEach((dot, index) => {
            if (index === currentNoticeIndex) {
                dot.classList.add('active');
            } else {
                dot.classList.remove('active');
            }
        });
    }

    // 자동 슬라이드 시작
    function startAutoSlide() {
        autoSlideInterval = setInterval(() => {
            nextNotice();
        }, 3000);
    }

    // 자동 슬라이드 정지
    function stopAutoSlide() {
        if (autoSlideInterval) {
            clearInterval(autoSlideInterval);
            autoSlideInterval = null;
        }
    }

    // 자동 슬라이드 재시작
    function resetAutoSlide() {
        stopAutoSlide();
        startAutoSlide();
    }

    // 오늘 하루 보지 않기
    function dontShowToday() {
        setCookie('no-notice', 'true', 1);
        closeNoticePopup();
    }

    // 쿠키 설정
    function setCookie(name, value, days) {
        const date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        const expires = "expires=" + date.toUTCString();
        document.cookie = name + "=" + value + ";" + expires + ";path=/";
    }

    // 쿠키 가져오기
    function getCookie(name) {
        const nameEQ = name + "=";
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            let cookie = cookies[i].trim();
            if (cookie.indexOf(nameEQ) === 0) {
                return cookie.substring(nameEQ.length);
            }
        }
        return null;
    }

    // 모달 외부 클릭 시 닫기
    window.onclick = function(event) {
        const modal = document.getElementById('noticePopupModal');
        if (event.target === modal) {
            closeNoticePopup();
        }
    }
</script>
