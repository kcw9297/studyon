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
        <!-- ✅ 이 요소들이 있어야 함 -->
        <h1 id="auth-title">인증 중...</h1>
        <div class="auth-icon-box">
            <img src="<c:url value='/img/png/present.png'/>" alt="이메일 아이콘" class="auth-icon">
        </div>
        <p id="auth-description">잠시만 기다려주세요.</p>
        <button class="auth-button" onclick="location.href='<c:url value='/login'/>'">로그인 페이지로 이동</button>
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

<script>
    document.addEventListener("DOMContentLoaded", async () => {

        // 쿼리스트링에서 token 가져오기
        const params = new URLSearchParams(window.location.search);
        const token = params.get('token');

        // ✅ 1. token 검증을 먼저
        if (!token) {
            alert("잘못된 접근입니다.");
            window.location.href = "/";
            return;
        }

        try {
            // FormData 생성
            const form = new FormData();
            form.append('token', token);

            // REST API 요청
            const res = await fetch("/api/auth/join", {
                method: "POST",
                body: form
            });

            // JSON 데이터 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // ✅ 2. 요소 가져오기 (null 체크)
            const authTitle = document.getElementById("auth-title");
            const authDescription = document.getElementById("auth-description");

            if (!authTitle || !authDescription) {
                console.error("페이지 요소를 찾을 수 없습니다.");
                return;
            }

            // ✅ 3. 성공/실패 처리
            if (!res.ok || !rp.success) {
                const message = rp.message || "서버 내부 오류";
                authTitle.textContent = "인증 실패";
                authDescription.innerHTML = `
                만약 가입한 이메일로 로그인되지 않으면,<br>
                다시 가입을 시도해 주시길 바랍니다.<br><br>
                <strong>원인:</strong> \${message}
            `;
            } else {
                const member = rp.data;
                authTitle.textContent = "가입을 축하합니다!";
                authDescription.innerHTML = `
                가입 이메일: <strong>\${member.email}</strong><br>
                가입일: <strong>\${member.cdate}</strong>
            `;
            }

        } catch (error) {
            console.error("인증 처리 실패:", error);

            // ✅ 에러 발생 시에도 안전하게 처리
            const authTitle = document.getElementById("auth-title");
            const authDescription = document.getElementById("auth-description");

            if (authTitle && authDescription) {
                authTitle.textContent = "오류 발생";
                authDescription.innerHTML = "인증 처리 중 오류가 발생했습니다.<br>다시 시도해주세요.";
            }
        }
    });

</script>

</html>
