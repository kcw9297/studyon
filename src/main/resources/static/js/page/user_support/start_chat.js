document.addEventListener("DOMContentLoaded", () => {
    const supportBtn = document.getElementById("support-button");

    supportBtn.addEventListener("click", async () => {
        try {
            const response = await fetch("/api/usersupport/create-room", {
                method: "POST"
            });

            const data = await response.json();
            if (data.error) {
                alert(data.error);
                return;
            }

            const roomId = data.roomId;

            console.log("✅ 채팅방 생성 성공:", roomId);
            window.location.href = `/usersupport/chat?roomId=${roomId}`;
        } catch (error) {
            console.error("❌ 채팅방 생성 오류:", error);
            alert("채팅방 생성 실패");
        }
    });
});
