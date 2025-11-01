<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/chatbot/chatbot.css'/>">
<div class="chatbot-container">
    <div class="chatbot-wrapper">
        <div class="chatbot-header">
            <h3>STUDY ON 챗봇 상담</h3>
        </div>

        <div class="chatbot-messages" id="chatbot-messages">
            <div class="message bot">안녕하세요! 어떤 도움이 필요하신가요?</div>
        </div>

        <div class="chatbot-input">
            <input type="text" id="chat-input" placeholder="메시지를 입력하세요...">
            <button id="chat-send">전송</button>
        </div>
    </div>
</div>

<script src="<c:url value='/js/page/chatbot/chatbot.js'/>"></script>

<style>
    .chatbot-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 900px;
        background-color: white;
    }

    .chatbot-wrapper {
        width: 1200px;
        height: 600px;
        background: white;
        border-radius: 15px;
        box-shadow: 0 0 15px rgba(0,0,0,0.1);
        display: flex;
        flex-direction: column;
        overflow: hidden;
        font-family: 'Noto Sans KR', sans-serif;
    }

    .chatbot-header {
        background: #1f2937;
        color: white;
        padding: 15px;
        font-weight: bold;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .chatbot-messages {
        flex: 1;
        padding: 15px;
        overflow-y: auto;
        background-color: #f4f6f8;
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    /* 메시지 기본 */
    .message {
        display: inline-block;
        padding: 10px 15px;
        border-radius: 16px;
        max-width: 75%;
        word-wrap: break-word;
        line-height: 1.4;
    }

    /* 봇 메시지 — 왼쪽 */
    .message.bot {
        align-self: flex-start;
        background: #e1e9f7;
        border-top-left-radius: 0;
    }

    /* 사용자 메시지 — 오른쪽 */
    .message.user {
        align-self: flex-end;
        background: #d1f7d6;
        border-top-right-radius: 0;
        text-align: right;
    }

    /* 입력창 */
    .chatbot-input {
        display: flex;
        border-top: 1px solid #ddd;
    }

    .chatbot-input input {
        flex: 1;
        border: none;
        padding: 12px;
        font-size: 14px;
        outline: none;
    }

    .chatbot-input button {
        background: #2563eb;
        color: white;
        border: none;
        padding: 12px 18px;
        cursor: pointer;
        font-weight: 500;
    }

    .chatbot-input button:hover {
        background: #1d4ed8;
    }

</style>