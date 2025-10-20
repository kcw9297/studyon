<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" href="<c:url value='/css/page/login/login.css'/>">
    <script src="<c:url value='/js/page/login/login.js'/>"></script>
</head>

<body>
  <div class="login-box">
    <span class="close-btn" onclick="window.location.href='/'">&times;</span>
    <img class="login-logo" src="<c:url value='/img/png/logo_login.png'/>" alt="로그인로고" />

<form class="login-form" action="<c:url value='/login/process'/>" method="post">
    <input type="text" name="email" placeholder="이메일">
    <div class="password-box">
        <input type="password" name="password" placeholder="비밀번호">
        <span class="toggle">
            <img src="<c:url value='/img/png/eyeoff.png'/>" alt="비밀번호 보기" />
        </span>
    </div>
    <div class="divider-login"></div>
    <button type="submit">로그인</button>
</form>

    <div class="login-links">
      <a href="#">비밀번호 찾기</a> |
      <a href="#">회원가입</a> |
      <a href="#">아이디(이메일) 찾기</a>
    </div>

    <div class="social-login">
      <div class="social-icons">
        <a href="/oauth2/authorization/google">
            <img src="<c:url value='/img/png/google.png'/>" alt="구글 로그인">
        </a>
        <a href="/oauth2/authorization/naver">
            <img src="<c:url value='/img/png/naver.png'/>" alt="구글 로그인">
        </a>
      </div>
    </div>
  </div>
</body>
</html>
