<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/teacher_list.css'/>">

<div id="content">
        <div class="nav-box">
            <a class="nav-item" href="<c:url value='/teacher/find/MATH'/>">수학</a>
            <a class="nav-item" href="<c:url value='/teacher/find/ENGLISH'/>">영어</a>
            <a class="nav-item" href="<c:url value='/teacher/find/KOREAN'/>">국어</a>
            <a class="nav-item" href="<c:url value='/teacher/find/SCIENCE'/>">과학탐구</a>
            <a class="nav-item" href="<c:url value='/teacher/find/SOCIAL'/>">사회탐구</a>
        </div>

        <%-- 과목 정보 --%>
        <div id="teacher" data-subject="${subject.name()}"></div>

        <div class="teacher-list-title">${subject.value} 선생님</div>
     <div class ="recent-lecture-container"></div>
</div>


<%-- Local Script --%>
<!-- teacher_list_by_subject.js -->
<script src="<c:url value='/js/page/teacher/teacher_list_by_subject.js'/>"></script>