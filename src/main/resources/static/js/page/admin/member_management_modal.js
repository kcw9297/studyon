document.addEventListener("DOMContentLoaded", () => {
    console.log("[INIT] 모달 이벤트 등록 시작");

    const modal = document.getElementById("memberModal");
    const toggleBtn = document.getElementById("toggleBtn");
    const closeModalBtn = document.getElementById("closeModalBtn");
    const closeBtn = document.querySelector(".close-btn");

    // ✅ (1) 회원 테이블에서 보기 버튼 클릭 시 모달 열기
    document.addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-view");
        if (!btn) return; // 다른 버튼 클릭 시 무시

        const row = btn.closest("tr");
        if (!row) return;

        const name = row.children[2].innerText;
        const email = row.children[3].innerText;
        const role = row.children[4].innerText;
        const status = row.children[5].innerText.trim();
        const date = row.children[6].innerText;
        const loginAt = row.children[7].innerText;
        const memberId = btn.dataset.memberId;

        console.log("[DEBUG] 모달 세팅:", { memberId, name, email, status, loginAt });

        // 모달 데이터 채우기
        document.getElementById("modalName").innerText = name;
        document.getElementById("modalEmail").innerText = email;
        document.getElementById("modalRole").innerText = role;
        document.getElementById("modalStatus").innerText = status;
        document.getElementById("modalDate").innerText = date;
        document.getElementById("modalLoginDate").innerText = loginAt ? trimDateTime(loginAt) : "기록 없음";

        modal.dataset.memberId = memberId;
        toggleBtn.innerText = status === "활성" ? "정지" : "해제";
        modal.style.display = "flex";
    });

    // ✅ (2) 모달 닫기 (X 버튼, 닫기 버튼, 외부 클릭)
    const closeModal = () => modal.style.display = "none";
    closeModalBtn.addEventListener("click", closeModal);
    closeBtn.addEventListener("click", closeModal);
    window.addEventListener("click", (e) => {
        if (e.target === modal) closeModal();
    });

    function trimDateTime(datetime) {
        if (!datetime) return "-";
        // 'T' → 공백으로 바꾸고, 소수점(밀리초) 이후 제거
        return datetime.replace("T", " ").split(".")[0];
    }
});
