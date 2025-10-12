<!DOCTYPE html>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>로그인</title>
  <link rel="stylesheet" href="<c:url value="/css/login.css"/>">
</head>
<body>
  <div class="login-box">
    <span class="close-btn">&times;</span>
    <h2>one day One Class</h2>

<form class="login-form">
    <input type="email" placeholder="이메일">
    <div class="password-box">
        <input type="password" placeholder="비밀번호">
        <span class="toggle">
            <img src="<c:url value="/images/eyeoff.png"/>" alt="비밀번호 보기" />
        </span>
    </div>
    <button>로그인</button>
</form>

    <div class="login-links">
      <a href="#">비밀번호 찾기</a> |
      <a href="#">회원가입</a> |
      <a href="#">아이디(이메일) 찾기</a>
    </div>

    <div class="social-login">
      <p>소셜로그인</p>
      <div class="social-icons">
        <a href="#">
            <img src="<c:url value="/images/kakao.png"/>" alt="카카오 로그인">
        </a>
        <a href="#">
            <img src="<c:url value="/images/google.png"/>" alt="구글 로그인">
        </a>
      </div>
    </div>
  </div>

  <script src="<c:url value="/js/login.js"/>"></script>
</body>
</html>