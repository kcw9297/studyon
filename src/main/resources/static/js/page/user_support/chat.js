document.addEventListener("DOMContentLoaded", () => {
    const sendBtn = document.getElementById("send"); // 전송 버튼
    const chatMessages = document.querySelector(".chat-messages"); // 메시지 리스트
    const input = document.querySelector(".chat-input input"); // 메시지 입력창
    const params = new URLSearchParams(window.location.search);
    const roomId = params.get("roomId") || "0";

    const socket = new WebSocket("ws://localhost:8080/ws/chat");

    // ✅ 관리자 ID 고정 (상담사)
    const ADMIN_ID = 51;

    socket.onopen = () => {
        console.log("✅ WebSocket 연결 성공!");

        // ✅ 입장 시 DB에 저장된 이전 메시지 불러오기
        fetch(`/usersupport/messages/${roomId}`)
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


















// document.addEventListener("DOMContentLoaded", () => {
//     const sendBtn = document.getElementById("send"); // ID가 send인 버튼을 sendBtn 변수에 담음
//     const chatMessages = document.querySelector(".chat-messages"); // chat-messages 클래스를 가진 div 블록을 chatMessages 변수에 담음
//     const input = document.querySelector(".chat-input input"); // chat input의 input이 실제 메세지입력임 메세지 입력을 자바스크립트 input 변수에 담음
//     const params = new URLSearchParams(window.location.search);
//     const roomId = params.get("roomId") || "0";
//     const socket = new WebSocket("ws://localhost:8080/ws/chat");
//
//
//     socket.onopen = () => {
//         console.log("✅ WebSocket 연결 성공!");
//
//         // ✅ 입장 시 DB에 저장된 이전 메시지 불러오기
//         fetch(`/usersupport/messages/${roomId}`)
//             .then(res => res.json())
//             .then(messages => {
//                 console.log("📥 기존 메시지 불러옴:", messages);
//                 messages.forEach(msg => {
//                     const div = document.createElement("div");
//
//                     // ✅ senderId가 6(관리자)이면 agent, 아니면 user로 처리
//                     div.classList.add("message", msg.senderId === 1 ? "agent" : "user");
//
//                     div.textContent = msg.message;
//                     chatMessages.appendChild(div);
//                 });
//                 chatMessages.scrollTop = chatMessages.scrollHeight;
//             })
//             .catch(err => console.error("❌ 메시지 불러오기 실패:", err));
//     };
//
//     socket.onmessage = (event) => {
//         const data = JSON.parse(event.data);
//         console.log("📩 수신 성공:", data);  // ✅ 수신 성공 로그
//
//         const newMessage = document.createElement("div");
//         if (data.type === "ADMIN") {
//             newMessage.classList.add("message", "agent");  // 상담사 메시지
//         } else {
//             newMessage.classList.add("message", "user");   // 고객 메시지
//         }
//         newMessage.textContent = data.msg;
//
//         chatMessages.appendChild(newMessage);
//         chatMessages.scrollTop = chatMessages.scrollHeight;
//     };
//
//     sendBtn.addEventListener("click", () => {
//         const text = input.value.trim(); // ✅ 올바른 속성
//         if (text === "") return; // 빈 문자열이면 무시
//
//         socket.send(JSON.stringify({
//             msg: text,
//             roomId: roomId
//         }));
//
//         console.log("📤 메시지 전송 성공:", text, "(room:", roomId, ")"); // ✅ 추가
//
//
//         input.value = ""; // 입력칸 비우기
//         chatMessages.scrollTop = chatMessages.scrollHeight; // 스크롤을 맨 아래로 내림
//     });
//
//     //엔터키로도 전송가능함
//     input.addEventListener("keypress", (e) => {
//         if (e.key === "Enter") sendBtn.click();
//     });
// });
//

