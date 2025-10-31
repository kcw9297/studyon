<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="chatbot-root">
    <button id="chatbot-fab" title="챗봇 열기">💬</button>
</div>

<style>
    #chatbot-root {
        position: fixed;
        right: 24px;
        bottom: 24px;
        z-index: 9999;
    }

    #chatbot-fab {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        border: none;
        background: mediumseagreen;
        color: #fff;
        font-size: 26px;
        cursor: pointer;
        box-shadow: 0 4px 12px rgba(0,0,0,0.25);
        transition: all 0.25s ease;
    }

    #chatbot-fab:hover {
        transform: scale(1.1);
        background: #0056b3;
    }
</style>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const fab = document.getElementById("chatbot-fab");
        fab.addEventListener("click", () => {
            window.location.href="/chatbot/chat"
        });
    });
</script>
