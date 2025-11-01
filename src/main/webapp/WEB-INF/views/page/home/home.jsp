<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="home-nav">
    <a href="<c:url value='/lecture/recommend/MATH'/>">수학</a>
    <a href="<c:url value='/lecture/recommend/ENGLISH'/>">영어</a>
    <a href="<c:url value='/lecture/recommend/KOREAN'/>">국어</a>
    <a href="<c:url value='/lecture/recommend/SCIENCE'/>">과학탐구</a>
    <a href="<c:url value='/lecture/recommend/SOCIAL'/>">사회탐구</a>
    <a href="<c:url value='/teacher/find/MATH'/>">강사리스트</a>
</div>

<%-- 홈 베너 모달 --%>
<jsp:include page="/WEB-INF/views/page/home/home_banner.jsp" />

<%-- 공지사항 팝업 모달 --%>
<jsp:include page="/WEB-INF/views/page/home/home_notice_popup_modal.jsp" />


<!-- ✅ 최근 등록된 강의 -->
<label class="lecture-section-title">최근 등록된 강의</label>
<div class="recent-lecture-container" id="recentLectureContainer">
    <!-- JS에서 렌더링될 영역 -->
</div>

<!-- ✅ 최근 인기 강의 -->
<label class="lecture-section-title">최근 인기 강의</label>
<div class="recent-lecture-container" id="popularLectureContainer">
    <!-- JS에서 렌더링될 영역 -->
</div>

<style>
    .home-nav {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        gap: 20px;
        padding: 10px 0;
        position: sticky;
        top: 0;
        z-index: 100;
        margin-left:15px;
    }

    /* ✅ 링크 기본 스타일 */
    .home-nav a {
        position: relative;
        color: #333;
        font-size: 18px;
        font-weight: 600;
        text-decoration: none;
        transition: color 0.3s ease;
    }

    /* ✅ hover 시 밑줄 애니메이션 효과 */
    .home-nav a::after {
        content: '';
        position: absolute;
        width: 0%;
        height: 2px;
        bottom: -5px;
        left: 50%;
        background-color: #007bff;
        transition: all 0.3s ease;
        transform: translateX(-50%);
    }

    .home-nav a:hover {
        color: #007bff;
    }

    .home-nav a:hover::after {
        width: 100%;
    }

    /* ✅ 반응형: 작은 화면에서는 네비바 스크롤 */
    @media (max-width: 768px) {
        .home-nav {
            flex-wrap: nowrap;
            overflow-x: auto;
            gap: 25px;
            padding: 15px;
        }

        .home-nav a {
            font-size: 16px;
            flex-shrink: 0;
        }
    }

    .recent-lecture-container {
        display: grid;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;
        height: auto;
        box-sizing: border-box;
        background-color: white;
        margin-bottom:10px;
    }

    .recent-lecture-item {
        width: 260px;
        height: auto;
        box-shadow: inset 0 0 0 2px rgba(0, 0, 0, 0.5); /* ✅ 내부 border처럼 */
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        box-sizing: border-box;
    }

    .recent-lecture-item:hover {
        transform: translateY(-5px);
        cursor: pointer;
    }

    .lecture-section-title {
        font-size: 24px;
        font-weight: bold;
    }

    .lecture-info{
        margin-left:5px;
        margin-bottom:5px;
    }
    .recent-lecture-thumbnail {
        width: 100%;
        height: 180px;
        object-fit: cover;
        border-bottom: 1px solid #ddd;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }
    /*배너*/
    .main-banner-container {
        position: relative;
        width: 100%;
        height: 450px; /* 필요에 따라 조절 */
        overflow: hidden;
        border-radius: 10px;
    }

    .main-banner {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
        opacity: 0;
        transition: opacity 1.5s ease-in-out;
    }

    .main-banner.active {
        opacity: 1;
    }

    .fade {
        animation: fadeEffect 10s infinite;
    }

</style>

<%-- Local Script --%>
<script src="<c:url value='/js/page/home/home_lectures_view.js'/>">




</script>




