<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="empty-box"></div>
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