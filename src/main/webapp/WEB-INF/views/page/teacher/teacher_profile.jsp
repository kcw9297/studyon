<%@ page contentType ="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/teacher/management/teacher_profile.css'/>">

    <div id="content">
        <div id="empty-box"></div>
        <div class="teacher-img-area">
            <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="강사이미지" class="teacher-img">
        </div>

        <div id="teacherBestLectures" data-teacher-id="${teacherProfile.teacherId}">
            <div>Best 강의 추천</div>
            <div class ="recent-lecture-container"></div>
        </div>
        <div id="teacherRecentLectures" data-teacher-id="${teacherProfile.teacherId}">
            <div>최근 등록된 강좌</div>
            <div class="recent-lecture-container"></div>
        </div>

        <div id="teacherComment" data-teacher-id="${teacherProfile.teacherId}">
            <div>생생 수강평</div>
            <div class="lecture-comment-box"></div>
        </div>
    </div>


<%-- Local Script --%>

<script src="<c:url value='/js/page/teacher_profile/best_lectures.js'/>"></script>
<script src="<c:url value='/js/page/teacher_profile/recent_lectures.js'/>"></script>
<script src="<c:url value='/js/page/teacher_profile/profile_reviews.js'/>"></script>
