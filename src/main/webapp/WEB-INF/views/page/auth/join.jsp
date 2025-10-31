<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value='/img/png/icon_title.png'/>" type="image/x-icon">
    <title>STUDY ON | 회원가입</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/css/page/auth/join.css'/>">
    <link rel="icon" href="<c:url value='/img/favicon.ico'/>" type="image/x-icon">
</head>

<body>
<div id="wrap">
    <div class="join-box">
        <div class="join-title">회원가입</div>

        <label class="join-label">이메일</label>
        <input type="text" class="join-input" id="email" placeholder="이메일을 입력해주세요.">
        <div class="asynchronous-message" id="emailError">30자 이내 이메일 형식 입력</div>

        <label class="join-label">비밀번호</label>
        <input type="password" class="join-input" id="password" placeholder="비밀번호를 입력해주세요.">
        <div class="asynchronous-message" id="passwordError">6자 이상 20자 이하 영문/특수문자/숫자 입력</div>

        <label class="join-label">비밀번호 확인</label>
        <input type="password" class="join-input" id="passwordConfirm" placeholder="비밀번호를 다시 입력해주세요.">
        <div class="asynchronous-message" id="passwordConfirmError">비밀번호 다시 입력</div>

        <button class="join-button" id="joinBtn">이메일 인증하기</button>
        <div class="social-label">간편 회원가입</div>
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

    .asynchronous-message-right{
        color: green;
        font-size: 14px;
        opacity: 0.7;
    }
</style>
</body>

<script>

    const timers = {};
    const inputEmail = document.getElementById("email");
    const inputEmailError = document.getElementById("emailError");
    const inputPassword = document.getElementById("password");
    const inputPasswordError = document.getElementById("passwordError");
    const inputPasswordConfirm = document.getElementById("passwordConfirm");
    const inputPasswordConfirmError = document.getElementById("passwordConfirmError");


    /* 실시간 유효성 검사 */
    inputEmail.addEventListener("input", (e) => {
        const fieldName = e.target.name;
        clearTimeout(timers[fieldName]);
        timers[name] = setTimeout(() => {
            const email = inputEmail.value.trim();
            const pattern = new RegExp("^(?=.{1,30}$)[a-zA-Z0-9][a-zA-Z0-9._-]*@[a-zA-Z0-9][a-zA-Z0-9.-]*\\.[a-zA-Z]{2,}$");

            if (!email) {
                inputEmailError.textContent = "30자 이내 이메일 형식 입력";
                inputEmailError.className = "asynchronous-message";

            } else if (!pattern.test(email)) {
                inputEmailError.textContent = "30자 이내 이메일 형식을 입력해야 합니다.";
                inputEmailError.className = "text-error";

            } else {
                inputEmailError.textContent = "사용 가능한 이메일 입니다.";
                inputEmailError.className = "asynchronous-message-right";
            }
        }, 300);
    });

    inputPassword.addEventListener("input", (e) => {
        const fieldName = e.target.name;
        clearTimeout(timers[fieldName]);
        timers[name] = setTimeout(() => {
            const password = inputPassword.value.trim();
            const passwordConfirm = inputPasswordConfirm.value.trim();
            const pattern = new RegExp("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$)[a-zA-Z\\d@#$%^&+=!]{8,20}$");

            if (!password) {
                inputPasswordError.textContent = "8-20자 사이 공백제외 영문/숫자/특수문자 입력";
                inputPasswordError.className = "asynchronous-message";

            } else if (!pattern.test(password)) {
                inputPasswordError.textContent = "공백제외 영문/숫자/특수문자를 8~20자 사이로 입력해야 합니다.";
                inputPasswordError.className = "text-error";

                if (password !== passwordConfirm) {
                    inputPasswordConfirmError.textContent = "비밀번호와 비밀번호 확인이 일치하지 않습니다.";
                    inputPasswordConfirmError.className = "text-error";
                } else {
                    inputPasswordConfirmError.textContent = "비밀번호 입력과 일치합니다.";
                    inputPasswordConfirmError.className = "asynchronous-message-right";
                }

            } else {
                inputPasswordError.textContent = "사용 가능한 비밀번호 입니다";
                inputPasswordError.className = "asynchronous-message-right";

                if (password !== passwordConfirm) {
                    inputPasswordConfirmError.textContent = "비밀번호와 비밀번호 확인이 일치하지 않습니다.";
                    inputPasswordConfirmError.className = "text-error";
                } else {
                    inputPasswordConfirmError.textContent = "비밀번호 입력과 일치합니다.";
                    inputPasswordConfirmError.className = "asynchronous-message-right";
                }
            }
        }, 300);
    });

    inputPasswordConfirm.addEventListener("input", (e) => {
        const fieldName = e.target.name;
        clearTimeout(timers[fieldName]);
        timers[name] = setTimeout(() => {
            const password = inputPassword.value.trim();
            const passwordConfirm = inputPasswordConfirm.value.trim();

            if (!passwordConfirm) {
                inputPasswordConfirmError.textContent = "비밀번호 다시 입력";
                inputPasswordConfirmError.className = "asynchronous-message";

            } else if (password !== passwordConfirm) {
                inputPasswordConfirmError.textContent = "비밀번호와 비밀번호 확인이 일치하지 않습니다.";
                inputPasswordConfirmError.className = "text-error";

            } else {
                inputPasswordConfirmError.textContent = "비밀번호 입력과 일치합니다.";
                inputPasswordConfirmError.className = "asynchronous-message-right";
            }
        }, 300);
    });




    const joinBtn = document.getElementById("joinBtn");
    joinBtn.addEventListener("click", async (e) => {

        try {
            e.preventDefault();
            // form 생성
            const form = new FormData();
            form.append("email", inputEmail.value);
            form.append("password", inputPassword.value);

            // REST API 요청
            const res = await fetch("/api/auth/join/request", {
                method: "POST",
                body: form
            });

            // JSON 데이터 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 실패 처리
            if (!res.ok || !rp.success) {

                if (rp.message && rp.message.includes("이미 가입한 이메일")) {
                    inputEmailError.textContent = rp.message;
                    inputEmailError.className = "text-error";
                    return;
                }

                // 유효성 검사에 실패한 경우
                if (rp.inputErrors) {
                    Object.entries(rp.inputErrors).forEach(([field, message]) => {
                        const errorElem = document.getElementById(`\${field}Error`);
                        if (errorElem) {
                            errorElem.textContent = message;
                            errorElem.className = "text-error";
                        }
                    });
                    return;
                }

                // 그 외 예상 외 오류가 발생한 경우
                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            // 성공 처리
            window.location.href = rp.redirect || "/";

        } catch (error) {
            console.error("로그인 실패:", error);
        }
    });

</script>

</html>