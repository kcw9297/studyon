<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link rel="stylesheet" href="<c:url value='/css/page/auth/login.css'/>">
</head>

<body>
    <%--nickname & password modal--%>
    <jsp:include page="/WEB-INF/views/page/auth/modal_edit_password.jsp" />
    <jsp:include page="/WEB-INF/views/page/auth/modal_edit_password_success.jsp" />

  <div class="login-box">
    <span class="close-btn" onclick="window.location.href='/'">&times;</span>
    <img class="login-logo" onclick="window.location.href='/'" src="<c:url value='/img/png/logo_login.png'/>" alt="로그인로고" />

<form class="login-form" action="<c:url value='/login/process'/>" method="post">
    <input type="text" id="email" name="email" placeholder="이메일">
    <div class="password-box">
        <input type="password" id="password" name="password" placeholder="비밀번호">
        <input type="hidden" name="redirect" value="${param.redirect}"> <%-- 쿼리스트링 중, "rediect" 주소를 담아 보냄 --%>
        <span class="toggle">
            <img src="<c:url value='/img/png/eyeoff.png'/>" alt="비밀번호 보기" />
        </span>
    </div>
    <div class="text-error" id="loginError"></div>
    <div class="divider-login"></div>
    <button type="submit">로그인</button>
</form>

    <div class="login-links">
      <a class="findPassword" href="#">비밀번호 찾기</a> |
      <a href="/join">회원가입</a>
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


<script>

    document.addEventListener("DOMContentLoaded", () => {
        const toggle = document.querySelector(".toggle img");
        const findPassword = document.querySelector(".findPassword");
        const passwordInput = document.querySelector(".password-box input");
        const form = document.querySelector(".login-form");
        const sendEditPasswordEmailBtn = document.querySelector("#sendEditPasswordEmailBtn");
        const closeEditPasswordModalBtn = document.querySelector("#closePasswordModalBtn");
        const closeMailSuccessBtn = document.querySelector("#closeMailSuccessBtn");

        toggle.addEventListener("click", () => {
            const type = passwordInput.getAttribute("type");
            if (type === "password") {
                passwordInput.setAttribute("type", "text");
                toggle.src = "/img/png/eyeshow.png";
            } else {
                passwordInput.setAttribute("type", "password");
                toggle.src = "/img/png/eyeoff.png";
            }
        });


        findPassword.addEventListener("click", (e) => {
            e.preventDefault();
            openPasswordModal();
        });

        sendEditPasswordEmailBtn.addEventListener("click", (e) => {
            e.preventDefault();
            sendEditPasswordRequestMail();
        });

        closeEditPasswordModalBtn.addEventListener("click", (e) => {
            e.preventDefault();
            closePasswordModal();
        });

        closeMailSuccessBtn.addEventListener("click", (e) => {
            e.preventDefault();
            closeMailSendSuccessModal();
        });





        form.addEventListener("submit", async (e) => {
            e.preventDefault();

            try {

                // REST API 요청
                const res = await fetch(form.action, {
                    method: form.method,
                    body: new FormData(form)
                });

                // JSON 데이터 파싱
                const rp = await res.json();
                console.log("서버 응답:", rp);

                // 실패 처리
                if (!res.ok || !rp.success) {
                    const errorElem = document.getElementById(`loginError`);
                    if (errorElem) errorElem.textContent = rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.";
                    return;
                }

                // 성공 처리
                window.location.href =  rp.redirect || "/";

            } catch (error) {
                console.error("로그인 실패:", error);
            }




        });
    });


    function openPasswordModal() {
        const modal = document.getElementById("passwordEditModal");
        if (modal) modal.style.display = "flex";
    }

    function closePasswordModal() {
        const modal = document.getElementById("passwordEditModal");
        modal.querySelectorAll('input').forEach(input => input.value = '');
        modal.querySelectorAll('textarea').forEach(textarea => textarea.value = '');
        modal.querySelectorAll('.text-error').forEach(textError => textError.textContent = '');
        if (modal) modal.style.display = "none";
    }

    function openMailSendSuccessModal() {
        const modal = document.getElementById("mailSendSuccessModal");
        if (modal) modal.style.display = "flex";
    }

    function closeMailSendSuccessModal() {
        const modal = document.getElementById("mailSendSuccessModal");
        if (modal) modal.style.display = "none";
    }


    async function sendEditPasswordRequestMail() {

        try {

            // form 데이터 생성
            const editPasswordEmail = document.getElementById("editPasswordEmail").value;

            const form = new FormData();
            form.append("email", editPasswordEmail);

            // REST API 요청
            const res = await fetch(`/api/auth/edit-password/request`, {
                method: "POST",
                body: form
            });

            // JSON 데이터 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 실패 처리
            if (!res.ok || !rp.success) {
                const errorElem = document.getElementById(`editPasswordError`);
                if (errorElem) errorElem.textContent = rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.";
                return;
            }

            // 성공 처리
            openMailSendSuccessModal();
            closePasswordModal();

        } catch (error) {
            console.error("비밀번호 초기화 실패:", error);
        }
    }

</script>