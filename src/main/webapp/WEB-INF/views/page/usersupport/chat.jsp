<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/user_support/chat.css'/>">
<div id="content">

    <div class="user-support-chat-container">
        <div class="chat-box">
            <div class="chat-header">
                <h3>고객 상담 센터</h3>
                <span class="chat-agent">오늘도 좋은하루 되세요</span>
            </div>

            <div class="chat-messages">
            </div>

            <div class="chat-input">
                <input type="text" placeholder="메시지를 입력하세요..." />
                <button id="send">전송</button>
            </div>
        </div>
    </div>
</div>

<%--<script>--%>
<%--    const loginMemberId = ${sessionScope.member.memberId};--%>
<%--</script>--%>

<%--
<script src="<c:url value='/js/page/user_support/chat.js'/>"></script>
--%>


<script>
    document.addEventListener("DOMContentLoaded", () => {
        const sendBtn = document.getElementById("send"); // 전송 버튼
        const chatMessages = document.querySelector(".chat-messages"); // 메시지 리스트
        const input = document.querySelector(".chat-input input"); // 메시지 입력창
        const params = new URLSearchParams(window.location.search);
        const roomId = params.get("roomId") || "0";
        const socket = new WebSocket("${chatUrl}");

        // ✅ 관리자 ID 고정 (상담사)
        const ADMIN_ID = 51;

        socket.onopen = () => {
            console.log("✅ WebSocket 연결 성공!");

            // ✅ 입장 시 DB에 저장된 이전 메시지 불러오기
            fetch(`/usersupport/messages/\${roomId}`)
                .then(res => res.json())
                .then(messages => {
                    console.log("📥 기존 메시지 불러옴:", messages);
                    messages.forEach(msg => {
                        const div = document.createElement("div");
                        div.classList.add("message");

                        // ✅ senderId 기준으로 위치 구분
                        // ADMIN(51)은 오른쪽(agent), 나머지는 왼쪽(user)
                        if (parseInt(msg.senderId) === ADMIN_ID) {
                            div.classList.add("agent");
                        } else {
                            div.classList.add("user");
                        }

                        div.textContent = msg.message;
                        chatMessages.appendChild(div);
                    });
                    chatMessages.scrollTop = chatMessages.scrollHeight;
                })
                .catch(err => console.error("❌ 메시지 불러오기 실패:", err));
        };

        // ✅ WebSocket으로 수신되는 실시간 메시지 처리
        socket.onmessage = (event) => {
            const data = JSON.parse(event.data);
            console.log("📩 수신 성공:", data);

            const newMessage = document.createElement("div");
            newMessage.classList.add("message");

            // ✅ 서버에서 type 또는 sender로 구분
            if (data.type === "ADMIN" || parseInt(data.sender) === ADMIN_ID) {
                newMessage.classList.add("agent"); // 상담사(오른쪽)
            } else {
                newMessage.classList.add("user"); // 고객(왼쪽)
            }

            newMessage.textContent = data.msg;
            chatMessages.appendChild(newMessage);
            chatMessages.scrollTop = chatMessages.scrollHeight;
        };

        socket.onclose = () => console.warn("⚠️ WebSocket 연결 종료됨");
        socket.onerror = (err) => console.error("❌ WebSocket 에러:", err);

        // ✅ 메시지 전송 이벤트
        sendBtn.addEventListener("click", () => {
            const text = input.value.trim();
            if (text === "") return;

            // ✅ 서버로 전송
            socket.send(JSON.stringify({
                msg: text,
                roomId: roomId
            }));

            console.log("📤 메시지 전송 성공:", text, "(room:", roomId, ")");


            input.value = "";
            chatMessages.scrollTop = chatMessages.scrollHeight;
        });

        // ✅ 엔터키로 전송 가능
        input.addEventListener("keypress", (e) => {
            if (e.key === "Enter") sendBtn.click();
        });
    });

</script>