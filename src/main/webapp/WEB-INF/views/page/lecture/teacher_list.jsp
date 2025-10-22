<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/teacher_list.css'/>">

<div id="content">
    <div id="empty-box"></div>
    <div class="nav-box">
        <a class="nav-item" href="<c:url value='/teacher/find/MATH'/>">수학</a>
        <a class="nav-item" href="<c:url value='/teacher/find/ENGLISH'/>">영어</a>
        <a class="nav-item" href="<c:url value='/teacher/find/KOREAN'/>">국어</a>
        <a class="nav-item" href="<c:url value='/teacher/find/SCIENCE'/>">과학탐구</a>
        <a class="nav-item" href="<c:url value='/teacher/find/SOCIAL'/>">사회탐구</a>
    </div>
    <div class="teacher-list-title">${subject.value} 선생님</div>
    <div class ="recent-lecture-container">
        <c:forEach var="teacher" items="${teachers}">
        <div class="recent-lecture-item">
            <img src="<c:url value='/img/png/sample1.png'/>" alt="강의이미지">
            <div class="lecture-info">
              <p class="lecture-title">${teacher.nickname}</p>
              <p class="lecture-info-text">${teacher.description}</p>
              <p class="lecture-info-text">⭐${teacher.averageRating}</p>
            </div>
          </div>
        </c:forEach>
    </div>
</div>


<%-- Local Script --%>
<script src="<c:url value='/js/page/lecture/teacher_list.js'/>"></script>
