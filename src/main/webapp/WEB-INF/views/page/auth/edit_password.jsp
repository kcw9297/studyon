<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="password-edit-wrapper">
    <div class="password-edit-box">
        <h2>비밀번호 재설정</h2>
        <p class="password-edit-desc">새로운 비밀번호를 입력해주세요.</p>

        <form id="passwordEditForm">
            <input type="hidden" id="editToken" name="token" value="${param.token}">

            <div class="form-group">
                <label for="newPassword">새 비밀번호</label>
                <input type="password" id="password" name="password" placeholder="새 비밀번호 입력" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">비밀번호 확인</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호 재입력" required>
            </div>

            <div class="asynchronous-message-wrong" id="passwordEditError" style="color: red;font-size: 14px;opacity: 0.7; margin-bottom: 10px;"></div>
            <button type="submit" class="edit-btn">비밀번호 변경</button>
        </form>
    </div>
</div>

<style>
    body {
        background-color: #f7f8fa;
        font-family: 'Noto Sans KR', sans-serif;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }

    .password-edit-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        min-height: calc(100vh - 100px); /* header + footer 높이 제외 */
        margin-top: 200px; /* 살짝 여백 */
    }

    .password-edit-box {
        background: #fff;
        border-radius: 16px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        padding: 40px 50px;
        width: 400px;
        text-align: center;
    }

    .password-edit-box h2 {
        font-size: 24px;
        font-weight: 700;
        color: #333;
        margin-bottom: 10px;
    }

    .password-edit-desc {
        font-size: 14px;
        color: #666;
        margin-bottom: 30px;
    }

    .form-group {
        text-align: left;
        margin-bottom: 20px;
    }

    .form-group label {
        display: block;
        font-size: 14px;
        font-weight: 600;
        color: #444;
        margin-bottom: 6px;
    }

    .form-group input {
        width: 100%;
        padding: 12px;
        border-radius: 8px;
        border: 1.5px solid #ddd;
        font-size: 15px;
        outline: none;
        transition: 0.2s ease;
    }

    .form-group input:focus {
        border-color: #28a745;
        box-shadow: 0 0 5px rgba(40,167,69,0.2);
    }

    .edit-btn {
        width: 90%;
        background-color: #28a745;
        color: white;
        border: none;
        border-radius: 8px;
        font-size: 16px;
        font-weight: 600;
        padding: 12px;
        cursor: pointer;
        transition: background-color 0.2s ease;
    }

    .edit-btn:hover {
        background-color: #218838;
    }
</style>


<script>

    const form = document.getElementById("passwordEditForm");

    form.addEventListener("submit", async (e) => {

        try {
            e.preventDefault();

            // 요소 값 추출
            const passwordEditError = document.getElementById("passwordEditError");
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;

            // 클라이언트 유효성 검사
            if (password !== confirmPassword) {
                passwordEditError.textContent = "비밀번호 확인과 일치하지 않습니다.";
                return;
            }

            // REST API 요청
            const res = await fetch("/api/auth/edit-password", {
                method: "PATCH",
                body: new FormData(form)
            });

            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {
                passwordEditError.textContent = rp.inputErrors.password || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.";
                return;
            }

            // 요청 성공 처리
            alert(rp.message || "비밀번호를 변경했습니다. 다시 로그인해 주세요.");
            window.location.href = rp.redirect || "/";


        } catch (error) {
            console.error("비밀번호 변경 실패:", error);
        }

    });




</script>
