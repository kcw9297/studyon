<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>


<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>기본 레이아웃 | 선생님 프로필</title>
  <link rel="stylesheet" href="<c:url value='/css/base/main-template.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/page/teacher/management/teacher-management.css'/>">
  <link rel="icon" href="/images/favicon.ico" type="image/x-icon">
</head>
<body>
  <div id="wrap">
    <main id="container">
        <div class="center-box">
            <header id="header">
                <div class="header-container">
                    <div class="header-logo">
                        <img src="<c:url value='/img/png/logo_login.png'/>" id="logo"alt="로고이미지">
                    </div>
                    <div class="header-search">검색창</div>
                    <div class="header-info">
                      <a href="#" id="loginModalBtn" class="modal">로그인</a>
                    </div>
                </div>
            </header>
            <div id="content">콘텐츠영역
                <div id="empty-box"></div>
                <div class="TeacherManagement-navbar">
                    <a href="#" class="nav-item">강사 메인</a>
                    <a href="#" class="nav-item">강의 등록</a>
                    <a href="#" class="nav-item">강의 통계</a>
                    <a href="#" class="nav-item">내 강의관리</a>
                    <a href="#" class="nav-item">수강평 관리</a>
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
                        <div class="teacher-details-label">수강생 수 : 200<br>
                        <div class="teacher-details-label">평균 평점 : 4.5/5.0</div>
                    </div>
                  </div>
            </div>
        </div>
    </main>
  </div>
  <script src="<c:url value='/js/base/Maintemplate.js'/>"></script>
</body>
</html>
