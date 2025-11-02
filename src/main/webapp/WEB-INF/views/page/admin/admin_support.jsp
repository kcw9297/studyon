<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/admin_support.css'/>">

<div id="content">
    <jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
        <jsp:param name="active" value="support"/>
    </jsp:include>
    <div class="Support-Container">
      <div class="chat-layout">
        <!-- 왼쪽: 채팅방 목록 -->
          <div class="chat-room-list" id="room-container">
              <h3>채팅 목록</h3>
              <div class="toggle-box">
                  <button class="support-toggle-button active">전체</button>
                  <button class="support-toggle-button">대기중</button>
              </div>

              <!-- ✅ DB에서 가져온 rooms 출력 -->
              <c:forEach var="room" items="${rooms}">
                  <div class="room" data-room-id="${room.chatRoomId}">
                          ${room.roomName}
                  </div>
              </c:forEach>
          </div>

        <!-- 오른쪽: 채팅 내용 -->
        <div class="chat-window">
          <div class="chat-header">채팅방을 선택해주세요</div>
          <div class="chat-messages">
            <%--<div class="message user"></div>--%>
            <%--<div class="message agent"></div>--%>

          </div>
          <div class="chat-input">
            <input type="text" placeholder="메시지를 입력하세요..." />
            <button>전송</button>
          </div>
        </div>
      </div>
    </div>
</div>

<%--
<script src="<c:url value='/js/page/admin/admin_support.js'/>"></script>
--%>

<script>

    document.addEventListener("DOMContentLoaded", async () => {
        const roomContainer = document.getElementById("room-container");
        const chatMessages = document.querySelector(".chat-messages");
        const chatInput = document.querySelector(".chat-input input");
        const sendBtn = document.querySelector(".chat-input button");

        let activeRoomId = null;
        let socket = null;

        // ✅ WebSocket 연결
        function connectWebSocket() {
            socket = new WebSocket("${chatUrl}");
            socket.onopen = () => console.log("✅ WebSocket 연결 성공 (ADMIN)");

            socket.onmessage = (event) => {
                const data = JSON.parse(event.data);
                console.log("📩 서버 메시지 수신:", data);

                if (!chatMessages) return;

                // ✅ 송신자에 따라 정렬/색 구분
                const msgDiv = document.createElement("div");
                if (data.type === "ADMIN") {
                    msgDiv.classList.add("message", "agent");  // 상담사
                } else {
                    msgDiv.classList.add("message", "user");   // 고객
                }
                msgDiv.textContent = data.msg;

                chatMessages.appendChild(msgDiv);
                chatMessages.scrollTop = chatMessages.scrollHeight;
            };

            socket.onclose = () => console.warn("⚠️ WebSocket 연결 종료됨");
            socket.onerror = (err) => console.error("❌ WebSocket 에러:", err);
        }

        connectWebSocket();

        // ✅ 채팅방 목록 불러오기
        if (roomContainer) {
            try {
                // ✅ 이미 JSP에 렌더된 방들을 가져옴
                const rooms = roomContainer.querySelectorAll(".room");

                rooms.forEach(div => {
                    // ✅ 새 div 생성 X — 기존 div 그대로 사용
                    div.addEventListener("click", async () => {
                        activeRoomId = div.dataset.roomId;
                        console.log(`💬 방 클릭됨: \${activeRoomId}`);

                        if (socket && socket.readyState === WebSocket.OPEN) {
                            socket.send(JSON.stringify({
                                type: "ROOM_CHANGE",
                                roomId: parseInt(activeRoomId)
                            }));
                            console.log("👁️ 서버에 ROOM_CHANGE 전송:", activeRoomId);
                        }

                        // 활성화 표시
                        document.querySelectorAll(".room").forEach(r => r.classList.remove("active"));
                        div.classList.add("active");

                        try {
                            const response = await fetch(`/admin/support/messages/\${activeRoomId}`);
                            const messages = await response.json();

                            console.log("📥 받은 메시지 목록:", messages);
                            chatMessages.innerHTML = "";

                            // messages.forEach(msg => {
                            //     const msgDiv = document.createElement("div");
                            //     msgDiv.classList.add("message", msg.role === "ADMIN" ? "agent" : "user");
                            //     msgDiv.textContent = msg.message;
                            //     chatMessages.appendChild(msgDiv);
                            // });

                            messages.forEach(msg => {
                                const msgDiv = document.createElement("div");
                                msgDiv.classList.add("message");

                                // ✅ senderId 기준으로 왼쪽/오른쪽 구분
                                if (parseInt(msg.senderId) === 51) {
                                    msgDiv.classList.add("agent");  // 상담사
                                } else {
                                    msgDiv.classList.add("user");   // 고객
                                }

                                msgDiv.textContent = msg.message;
                                chatMessages.appendChild(msgDiv);
                            });
                        } catch (err) {
                            console.error("❌ 대화 불러오기 실패:", err);
                        }
                    });
                });

                console.log("✅ JSP 렌더된 방 이벤트 등록 완료:", rooms.length);
            } catch (err) {
                console.error("❌ 방 이벤트 등록 실패:", err);
            }
        }


        // ✅ 메시지 전송 이벤트
        sendBtn.addEventListener("click", () => {
            const text = chatInput.value.trim();
            if (!text || !socket || socket.readyState !== WebSocket.OPEN) {
                console.warn("⚠️ WebSocket 연결되지 않음");
                return;
            }

            if (!activeRoomId) {
                alert("먼저 채팅방을 선택하세요!");
                return;
            }

            // ✅ 서버에 보낼 데이터
            const payload = {
                msg: text,
                roomId: parseInt(activeRoomId)
            };

            console.log("📤 메시지 전송:", payload);
            socket.send(JSON.stringify(payload));

            // ✅ 프론트에서 본인 메시지 중복 표시 방지
            // 서버에서 echo 돌아올 때만 append 됨
            chatInput.value = "";
        });

        // 엔터키로 전송
        chatInput.addEventListener("keypress", (e) => {
            if (e.key === "Enter") sendBtn.click();
        });
    });

</script>