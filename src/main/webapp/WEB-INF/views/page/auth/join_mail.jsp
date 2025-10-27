<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value='/img/png/icon_title.png'/>" type="image/x-icon">
    <title>STUDY ON | 회원가입</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/css/page/auth/auth_mail.css'/>">
    <link rel="icon" href="<c:url value='/img/favicon.ico'/>" type="image/x-icon">
</head>

<body>
<div id="wrap">
    <div class="auth-box">
        <div class="auth-title">인증 메일을 보냈어요 ✉️</div>

        <div class="auth-icon-box">
            <img src="<c:url value='/img/png/present.png'/>" alt="이메일 아이콘" class="auth-icon">
        </div>

        <div class="auth-description">
            회원가입을 위해 이메일 인증이 필요해요.<br>
            가입하신 이메일 주소로 인증 메일을 보냈습니다.<br>
            메일함에서 인증 링크를 클릭해<br>
            회원가입을 완료해주세요!
        </div>

        <button class="auth-button" onclick="location.href='<c:url value='/'/>'">홈으로 돌아가기</button>
    </div>
</div>

<style>
    html, body {
        height: 100%;
        margin: 0;
        padding: 0;
        background: #f9f9f9;
        font-family: 'Noto Sans KR', sans-serif;
    }

    #wrap {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100%;
    }

    .auth-box {
        width: 500px;
        height:450px;
        background: #fff;
        padding: 50px 60px;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0,0,0,0.1);
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align: center;
        gap: 25px;
    }

    .auth-title {
        font-size: 28px;
        font-weight: bold;
        color: #2ecc71;
        margin-bottom: 10px;
    }

    .auth-icon-box {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .auth-icon {
        width: 120px;
        height: auto;
    }

    .auth-description {
        font-size: 16px;
        color: #555;
        line-height: 1.6;
    }

    .auth-button {
        margin-top: 15px;
        width: 100%;
        height: 55px;
        border: none;
        border-radius: 8px;
        background-color: #2ecc71;
        color: white;
        font-size: 18px;
        font-weight: bold;
        cursor: pointer;
        transition: background-color 0.25s ease;
    }

    .auth-button:hover {
        background-color: #27ae60;
    }
</style>
</body>
</html>
