document.addEventListener("DOMContentLoaded", () => {
    const sendBtn = document.getElementById("chat-send");
    const input = document.getElementById("chat-input");
    const messages = document.getElementById("chatbot-messages");

    // 메시지 추가 함수
    function addMessage(text, sender) {
        const msg = document.createElement("div");
        msg.classList.add("message", sender);
        msg.textContent = text;
        messages.appendChild(msg);
        messages.scrollTop = messages.scrollHeight;
    }

    // 서버로 비동기 요청 보내기
    async function sendMessageToServer(text) {
        try {
            const response = await fetch("/api/chatbot", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ message: text })
            });

            const data = await response.json();
            addMessage(data.answer, "bot");
        } catch (error) {
            addMessage("서버와 통신 중 오류가 발생했습니다.", "bot");
            console.error(error);
        }
    }

    // 전송 버튼 클릭 이벤트
    sendBtn.addEventListener("click", async () => {
        const text = input.value.trim();
        if (text === "") return;

        addMessage(text, "user");
        input.value = "";
        addMessage("답변을 불러오는 중...", "bot");

        await sendMessageToServer(text);
    });

    // 엔터키 입력 지원
    input.addEventListener("keydown", (e) => {
        if (e.key === "Enter") sendBtn.click();
    });
});