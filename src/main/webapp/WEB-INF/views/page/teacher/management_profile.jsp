<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/teacher/management_profile.css'/>">

<div id="content">
    <div id="empty-box"></div>
    <div class="teacher-management-navbar">
        <a href="<c:url value='/teacher/management/profile'/>" class="nav-item">강사 메인</a>
        <a href="<c:url value='/teacher/management/lectureregister'/>" class="nav-item">강의 등록</a>
        <a href="<c:url value='/teacher/management/statistics'/>" class="nav-item">강의 통계</a>
        <a href="<c:url value='/teacher/management/lectures'/>" class="nav-item">내 강의관리</a>
        <a href="<c:url value='/teacher/management/reviews'/>" class="nav-item">수강평 관리</a>
    </div>
    <div class="divide-box-30px"></div>
    <div class="teacher-photo">
          <img src="<c:url value='/img/png/teacher-profile-img.png'/>" alt="강사이미지" class="teacher-img">
        </div>
    <div class="Teacher-info-box">
      <div class="profile-change-button-box">
        <button class="profile-change-button">
          <img src="<c:url value='/img/png/upload.png'/>" class="svg-upload">
        </button>
      </div>

    <div class="teacher-details">
      <div class="divide-box-30px"></div>
        <div class="teacher-details-label">이름 : 한석원</div>
        <div class="teacher-details-label">email : tjrdnjs@gmail.com</div>
        <div class="teacher-details-label">강의 수 : 10</div>
        <div class="teacher-details-label">수강생 수 : 200<br></div>
        <div class="teacher-details-label">평균 평점 : 4.5/5.0</div>
    </div>
  </div>
</div>
