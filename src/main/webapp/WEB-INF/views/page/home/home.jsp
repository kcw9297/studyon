<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="empty-box"></div>
<div class="main-banner-container">
  <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="메인비주얼이미지" class="main-banner">
</div>
<div class="nav">
    <a href="<c:url value='/lecture/recommend/MATH'/>">수학</a>
    <a href="<c:url value='/lecture/recommend/ENGLISH'/>">영어</a>
    <a href="<c:url value='/lecture/recommend/KOREAN'/>">국어</a>
    <a href="<c:url value='/lecture/recommend/SCIENCE'/>">과학탐구</a>
    <a href="<c:url value='/lecture/recommend/SOCIAL'/>">사회탐구</a>
</div>

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


<%-- Local Script --%>
<script src="<c:url value='/js/page/home/home_lectures.js'/>"></script>



