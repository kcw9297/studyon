<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- ✅ 비밀번호 변경 모달 -->
<div id="passwordEditModal" class="password-modal">
    <div class="password-modal-content">
        <h2>비밀번호 변경</h2>
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

            <div class="modal-buttons">
                <button type="submit" class="reset-btn">변경하기</button>
                <button type="button" class="close-btn" onclick="closePasswordeditModal()">닫기</button>
            </div>
        </form>
    </div>
</div>

<!-- ✅ 스타일 -->
<style>
    .password-modal {
        display: none;
        position: fixed;
        z-index: 1000;
        inset: 0;
        background: rgba(0, 0, 0, 0.5);
        justify-content: center;
        align-items: center;
        backdrop-filter: blur(4px);
        animation: modalFadeIn 0.3s ease;
    }

    .password-modal-content {
        background: #fff;
        border-radius: 14px;
        padding: 40px 45px;
        text-align: center;
        width: 380px;
        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
        animation: popIn 0.35s ease;
    }

    .password-modal-content h2 {
        margin-bottom: 8px;
        font-size: 24px;
        font-weight: 700;
        color: #222;
    }

    .password-reset-desc {
        font-size: 14px;
        color: #666;
        margin-bottom: 25px;
    }

    .form-group {
        text-align: left;
        margin-bottom: 18px;
    }

    .form-group label {
        display: block;
        font-size: 14px;
        font-weight: 600;
        margin-bottom: 6px;
        color: #333;
    }

    .form-group input {
        width: 100%;
        padding: 12px 14px;
        border: 1px solid #d1d1d1;
        border-radius: 8px;
        font-size: 15px;
        outline: none;
        transition: 0.2s;
    }

    .form-group input:focus {
        border-color: #007bff;
        box-shadow: 0 0 4px rgba(0, 123, 255, 0.2);
    }

    .modal-buttons {
        display: flex;
        justify-content: center;
        gap: 12px;
        margin-top: 25px;
    }

    .modal-buttons button {
        padding: 12px 0;
        width: 48%;
        border-radius: 8px;
        font-size: 15px;
        font-weight: 600;
        cursor: pointer;
        border: none;
        transition: all 0.25s ease;
    }

    .reset-btn {
        background: linear-gradient(135deg, #007bff, #3399ff);
        color: white;
    }

    .reset-btn:hover {
        background: linear-gradient(135deg, #0056d6, #1a83ff);
    }

    .close-btn {
        background: #f0f0f0;
        color: #333;
    }

    .close-btn:hover {
        background: #e0e0e0;
    }

    /* ✅ 애니메이션 */
    @keyframes modalFadeIn {
        from { opacity: 0; }
        to { opacity: 1; }
    }

    @keyframes popIn {
        from { opacity: 0; transform: scale(0.9); }
        to { opacity: 1; transform: scale(1); }
    }
</style>