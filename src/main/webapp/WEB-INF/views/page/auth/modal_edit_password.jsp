<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="passwordEditModal" class="password-modal">
    <div class="password-modal-content">
        <h2>비밀번호 재설정</h2>
        <p class="password-modal-desc">
            가입하신 이메일 주소를 입력하시면<br>
            비밀번호 재설정 링크를 보내드립니다.
        </p>
        <input type="text" id="editPasswordEmail" name="sendEmail" placeholder="이메일 주소 입력">
        <div class="text-error" id="editPasswordError"></div>
        <div class="modal-buttons">
            <button id="sendEditPasswordEmailBtn">메일 보내기</button>
            <button id="closePasswordModalBtn">취소</button>
        </div>
    </div>
</div>

<style>
    .password-modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.4);
        justify-content: center;
        align-items: center;
    }

    .password-modal-content {
        background: white;
        border-radius: 12px;
        padding: 30px 40px;
        text-align: center;
        width: 400px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        animation: fadeIn 0.3s ease;
    }

    .password-modal-content h2 {
        margin-bottom: 15px;
        font-size: 22px;
        font-weight: 600;
        color: #333;
    }

    .password-modal-desc {
        font-size: 14px;
        color: #555;
        margin-bottom: 20px;
        line-height: 1.4;
    }

    .password-modal-content input {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
        font-size: 16px;
    }

    .modal-buttons {
        margin-top: 25px;
        display: flex;
        justify-content: center;
        gap: 10px;
    }

    .modal-buttons button {
        padding: 10px 20px;
        border: none;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
        transition: background 0.2s ease;
    }

    #sendResetEmailBtn {
        background-color: #007bff;
        color: white;
    }

    #closePasswordModalBtn {
        background-color: #ccc;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: scale(0.95); }
        to { opacity: 1; transform: scale(1); }
    }
</style>
