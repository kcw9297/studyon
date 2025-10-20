<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>

<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>WebSocket Handshake Test</title>
</head>
<body>
<h2>WebSocket Handshake Test</h2>
<button id="connectBtn">Connect</button>
<button id="sendBtn">Send Message</button>
<div id="log"></div>

<script>
    let socket;
    const log = msg => {
        const div = document.createElement("div");
        div.textContent = msg;
        document.getElementById("log").appendChild(div);
    };

    document.getElementById("connectBtn").onclick = () => {
        socket = new WebSocket("ws://localhost:8080/ws/chat");
        socket.onopen = () => log("✅ 연결 성공");
        socket.onmessage = e => log("📩 수신: " + e.data);
        socket.onclose = () => log("❌ 연결 종료");
        socket.onerror = e => log("⚠️ 에러: " + e);
    };

    document.getElementById("sendBtn").onclick = () => {
        if (socket && socket.readyState === WebSocket.OPEN) {
            socket.send("테스트 메세지");
        }
    };
</script>
</body>
</html>

