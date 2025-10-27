<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="mailSendSuccessModal" class="mail-modal">
    <div class="mail-modal-content">
        <h2>메일 발송 완료</h2>
        <p class="mail-modal-desc">
            비밀번호 재설정 링크가 입력하신 이메일로 전송되었습니다.<br>
            메일함을 확인해주세요.
        </p>
        <div class="modal-buttons">
            <button id="closeMailSuccessBtn">확인</button>
        </div>
    </div>
</div>

<style>
    .mail-modal {
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

    .mail-modal-content {
        background: #fff;
        border-radius: 12px;
        padding: 40px 45px;
        text-align: center;
        width: 400px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        animation: fadeIn 0.3s ease;
    }

    .mail-modal-content h2 {
        margin-bottom: 15px;
        font-size: 22px;
        font-weight: 600;
        color: #333;
    }

    .mail-modal-desc {
        font-size: 15px;
        color: #555;
        line-height: 1.5;
        margin-bottom: 25px;
    }

    .modal-buttons {
        display: flex;
        justify-content: center;
        gap: 10px;
    }

    #closeMailSuccessBtn {
        padding: 10px 25px;
        background-color: #28a745;
        border: none;
        color: white;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
        transition: background 0.2s ease;
    }

    #closeMailSuccessBtn:hover {
        background-color: #218838;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: scale(0.95); }
        to { opacity: 1; transform: scale(1); }
    }
</style>
