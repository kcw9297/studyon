// const socket = new WebSocket("ws://localhost:8080/chat");

// socket.onopen = () => {
//   console.log("Websocket connected");
// }
// socket.send(JSON.stringify({
//   sender:"user",
//   message:text
// }))

// socket.onmessage = (event) => {
//   const data = JSON.parse(event.data);
//   const agentMsg = document.createElement("div");

//   agentMsg.classList.add("message", "agent");
//   agentMsg.textContent = data.message;

//   chatMessages.appendChild(agentMsg);
//   chatMessages.scrollTop = chatMessages.scrollHeight;
// }


document.addEventListener("DOMContentLoaded", () => {
    const sendBtn = document.getElementById("send"); // ID가 send인 버튼을 sendBtn 변수에 담음
    const chatMessages = document.querySelector(".chat-messages"); // chat-messages 클래스를 가진 div 블록을 chatMessages 변수에 담음
    const input = document.querySelector(".chat-input input"); // chat input의 input이 실제 메세지입력임 메세지 입력을 자바스크립트 input 변수에 담음
    const params = new URLSearchParams(window.location.search);
    const roomId = params.get("roomId") || "0";
    const socket = new WebSocket("ws://localhost:8080/ws/chat");


    socket.onopen = () => {
        console.log("✅ WebSocket 연결 성공!");

        // ✅ 입장 시 DB에 저장된 이전 메시지 불러오기
        fetch(`/usersupport/messages/${roomId}`)
            .then(res => res.json())
            .then(messages => {
                console.log("📥 기존 메시지 불러옴:", messages);
                messages.forEach(msg => {
                    const div = document.createElement("div");

                    // ✅ senderId가 6(관리자)이면 agent, 아니면 user로 처리
                    div.classList.add("message", msg.senderId === 6 ? "agent" : "user");

                    div.textContent = msg.message;
                    chatMessages.appendChild(div);
                });
                chatMessages.scrollTop = chatMessages.scrollHeight;
            })
            .catch(err => console.error("❌ 메시지 불러오기 실패:", err));
    };

    socket.onmessage = (event) => {
        const data = JSON.parse(event.data);
        console.log("📩 수신 성공:", data);  // ✅ 수신 성공 로그

        const newMessage = document.createElement("div");
        if (data.type === "ADMIN") {
            newMessage.classList.add("message", "agent");  // 상담사 메시지
        } else {
            newMessage.classList.add("message", "user");   // 고객 메시지
        }
        newMessage.textContent = data.msg;

        chatMessages.appendChild(newMessage);
        chatMessages.scrollTop = chatMessages.scrollHeight;
    };

    sendBtn.addEventListener("click", () => {
        const text = input.value.trim(); // ✅ 올바른 속성
        if (text === "") return; // 빈 문자열이면 무시

        socket.send(JSON.stringify({
            msg: text,
            roomId: roomId
        }));

        console.log("📤 메시지 전송 성공:", text, "(room:", roomId, ")"); // ✅ 추가


        input.value = ""; // 입력칸 비우기
        chatMessages.scrollTop = chatMessages.scrollHeight; // 스크롤을 맨 아래로 내림
    });

    //엔터키로도 전송가능함
    input.addEventListener("keypress", (e) => {
        if (e.key === "Enter") sendBtn.click();
    });
});


