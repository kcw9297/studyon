<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="password-reset-wrapper">
    <div class="password-reset-box">
        <h2>비밀번호 재설정</h2>
        <p class="password-reset-desc">새로운 비밀번호를 입력해주세요.</p>

        <form id="passwordResetForm">
            <input type="hidden" id="resetToken" name="token" value="${param.token}">
            <input type="hidden" id="email" name="email" value="${param.email}">

            <div class="form-group">
                <label for="newPassword">새 비밀번호</label>
                <input type="password" id="newPassword" name="newPassword" placeholder="새 비밀번호 입력" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">비밀번호 확인</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호 재입력" required>
            </div>

            <button type="submit" class="reset-btn">비밀번호 변경</button>
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

    .password-reset-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        min-height: calc(100vh - 100px); /* header + footer 높이 제외 */
        margin-top: 200px; /* 살짝 여백 */
    }

    .password-reset-box {
        background: #fff;
        border-radius: 16px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        padding: 40px 50px;
        width: 400px;
        text-align: center;
    }

    .password-reset-box h2 {
        font-size: 24px;
        font-weight: 700;
        color: #333;
        margin-bottom: 10px;
    }

    .password-reset-desc {
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

    .reset-btn {
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

    .reset-btn:hover {
        background-color: #218838;
    }
</style>
