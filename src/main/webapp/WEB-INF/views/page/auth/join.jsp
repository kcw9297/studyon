<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 | Oneday OneClass</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/css/page/auth/join.css'/>">
    <link rel="icon" href="<c:url value='/img/favicon.ico'/>" type="image/x-icon">
</head>

<body>
<div id="wrap">
    <div class="join-box">
        <div class="join-title">회원가입</div>

        <label class="join-label">이메일</label>
        <input type="text" class="join-input" placeholder="이메일을 입력해주세요.">
        <div class="asynchronous-message">6자 이상 20자 이하 영문/특수문자/숫자 입력</div>
        <div class="asynchronous-message-wrong">6자 이상 20자 이하 영문/특수문자/숫자 입력</div>

        <label class="join-label">비밀번호</label>
        <input type="password" class="join-input" placeholder="비밀번호를 입력해주세요.">
        <div class="asynchronous-message">6자 이상 20자 이하 영문/특수문자/숫자 입력</div>

        <label class="join-label">비밀번호 확인</label>
        <input type="password" class="join-input" placeholder="비밀번호를 다시 입력해주세요.">
        <div class="asynchronous-message">비밀번호가 일치하지않습니다.</div>

        <button onclick="location.href='/testboard/auth_mail'" class="join-button">이메일 인증하기</button>

        <div class="social-label">간편 회원가입</div>
        <div class="social-icons">
            <a href="#"><img src="<c:url value='/img/png/kakao.png'/>" class="카카오 로그인"></a>
            <a href="#"><img src="<c:url value='/img/png/google.png'/>" alt="구글 로그인"></a>
        </div>
    </div>
</div>

<style>
    html, body {
        height: 100%;
        margin: 0;
        padding: 0;
        background: #f9f9f9;
    }

    /* ✅ 화면 중앙 정렬 */
    #wrap {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
    }

    /* ✅ 폼 카드 */
    .join-box {
        width: 500px;
        background: #fff;
        padding: 50px 60px;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0,0,0,0.1);
        display: flex;
        flex-direction: column;
    }

    .join-title {
        font-size: 28px;
        font-weight: bold;
        color: #2ecc71;
        text-align: center;
        margin-bottom: 20px;
    }

    .join-label {
        font-size: 18px;
        margin-top: 10px;
        margin-bottom:10px;
    }

    .join-input {
        height: 50px;
        border: 1.5px solid #ccc;
        border-radius: 8px;
        padding: 0 15px;
        font-size: 16px;
        transition: 0.2s;
    }

    .join-input:focus {
        border-color: #2ecc71;
        box-shadow: 0 0 0 3px rgba(46,204,113,0.2);
        outline: none;
    }

    .join-button {
        height: 55px;
        border: none;
        background: #2ecc71;
        color: white;
        border-radius: 8px;
        font-size: 18px;
        font-weight: bold;
        cursor: pointer;
        transition: 0.2s;
        margin-top: 25px;
    }

    .join-button:hover {
        background: #27ae60;
    }

    .asynchronous-message {
        color: #999;
        font-size: 14px;
        opacity: 0.7;
    }

    .social-label {
        text-align: center;
        margin-top: 20px;
        font-weight: 600;
    }

    .social-icons {
        text-align: center;
    }

    .social-icons img {
        width: 45px;
        margin-top:10px;
        cursor: pointer;
    }
    .asynchronous-message-wrong{
        color: red;
        font-size: 14px;
        opacity: 0.7;
    }
</style>
</body>
</html>
