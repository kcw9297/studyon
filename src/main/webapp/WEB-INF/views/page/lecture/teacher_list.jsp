<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>선생님 리스트</title>
  <link rel="stylesheet" href="<c:url value='/css/base/main-template.css' />">
  <link rel="stylesheet" href="<c:url value='/css/layer/lecture/teacher_list.css' />">
  <link rel="stylesheet" href="<c:url value='/css/base/Global.css' />">
  <link rel="icon" href="/images/favicon.ico" type="image/x-icon">
</head>
<body>

    <c:set var="ctx" value="${pageContext.request.contextPath}" />
  <div id="wrap">
    <main id="container">
        <div class="center-box">
            <header id="header">
                <div class="header-container">
                    <div class="header-logo">
                        <img src="<c:url value='/img/png/logo_logout.png'/>" id="logo"alt="로고이미지">
                    </div>
                    <div class="header-search">검색창</div>
                    <div class="header-info">
                      <a href="#" id="loginModalBtn" class="modal">로그인</a>
                    </div>
                </div>
            </header>
            <div id="content">콘텐츠영역
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
                    <c:forEach var="t" items="${teachers}">
                    <div class="recent-lecture-item">
                        <img src="<c:url value='/img/png/sample1.png'/>" alt="강의이미지">
                        <div class="lecture-info">
                          <p class="lecture-title">${t.nickname}</p>
                          <p class="lecture-info-text">${t.description}</p>
                          <p class="lecture-info-text">${t.averageRating}</p>
                        </div>
                      </div>
                    </c:forEach>
                </div>
            </div>
            <footer id="footer">푸터영역</footer>
        </div>
        
    </main>
  </div>
  <div id="loginModalBg" class="modal-bg">
    <div class="modal-content">
      <span id="closeModal" class="close">&times;</span>
      <h2>StudyOn</h2>
      <input type="text" placeholder="이메일 입력">
      <input type="password" placeholder="비밀번호 입력">
      <div class="divider-line"></div>
      <button>로그인</button>
      <div class="resister-box">
        <a href="#">회원가입</a>
        <a href="#">아이디(이메일) 찾기</a>
        <a href="#">비밀번호 찾기</a>
      </div>
      <div class="divider-line"></div>
      <div class="social-login-box">
        <div class="social-icons">
          <a href="#">
              <img src="<c:url value='/img/png/kakao.png'/>" alt="카카오 로그인">
          </a>
          <a href="#">
              <img src="<c:url value='/img/png/google.png'/>" alt="구글 로그인">
          </a>
          <a href="#">
              <img src="<c:url value='/img/png/kakao.png'/>" alt="구글 로그인">
          </a>
        </div>
      </div>

    </div>
  </div>
  <script src="<c:url value='/js/Maintemplate.js'/>"></script>
</body>
</html>
