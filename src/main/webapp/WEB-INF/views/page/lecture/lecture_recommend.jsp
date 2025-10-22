<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_recommend.css'/>">

<div id="content">
    <div id="empty-box"></div>
    <div class="main-container">
      <div class="sidebar-container">
        <div class="recommend-nav">
            <a href="<c:url value='/lecture/recommend/MATH'/>" class="nav-item">수학</a>
            <a href="<c:url value='/lecture/recommend/ENGLISH'/>" class="nav-item">영어</a>
            <a href="<c:url value='/lecture/recommend/KOREAN'/>" class="nav-item">국어</a>
            <a href="<c:url value='/lecture/recommend/SCIENCE'/>" class="nav-item">과학탐구</a>
            <a href="<c:url value='/lecture/recommend/SOCIAL'/>" class="nav-item">사회탐구</a>
      </div>
    </div>
    <div class="main-content-container">
      <div class="recomment-lecture-title">
        ${subject.value} 주간 인기/추천 강의
      </div>
        <div class="recent-lecture-container"></div>
        <!-- ✅ 비동기로 강의를 채울 빈 컨테이너 -->

            <div class="recomment-lecture-title">
                최근 수강평
            </div>
            <!-- ✅ 비동기로 강의를 채울 빈 컨테이너 -->
            <div class="lecture-comment-box"></div>

            <div class="recomment-lecture-title">
                요새 뜨는 강의
            </div>
            <div class="recent-lecture-container"></div>
            <!-- ✅ 비동기로 강의를 채울 빈 컨테이너 -->
        </div>
    </div>
</div>

<div id="lecturePage" data-subject="${subject.name()}"></div>

<script src="<c:url value='/js/page/lecture/recent_lecture.js'/>"></script>
<script src="<c:url value='/js/page/lecture/best_lecture.js'/>"></script>
<script src="<c:url value='/js/page/lecture/recent_reviews.js'/>"></script>
<script src="<c:url value='/js/page/lecture/lecture_recommend.js'/>"></script>