document.addEventListener("DOMContentLoaded", () => {
    console.log("[INIT] 강사 모달 이벤트 등록 시작");

    const modal = document.getElementById("memberModal");
    const toggleBtn = document.getElementById("toggleBtn");
    const closeModalBtn = document.getElementById("closeModalBtn");
    const closeBtn = document.querySelector(".close-btn");

    // ✅ 보기 버튼 클릭
    document.addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-view");
        if (!btn) return;

        const row = btn.closest("tr");
        if (!row) return;

        // 🔹 강사 테이블 구조에 맞게 인덱스 조정
        const name = row.children[1].innerText;
        const email = row.children[2].innerText;
        const subject = row.children[3].innerText;
        const status = row.children[4].innerText.trim();
        const date = row.children[5].innerText;
        const loginAt = row.children[6].innerText;
        const teacherId = btn.dataset.teacherId;

        console.log("[DEBUG] 모달 세팅:", { teacherId, name, email, subject, status });

        // 모달 데이터 세팅
        document.getElementById("modalName").innerText = name;
        document.getElementById("modalEmail").innerText = email;
        document.getElementById("modalSubject").innerText = subject;
        document.getElementById("modalStatus").innerText = status;
        document.getElementById("modalDate").innerText = date;
        document.getElementById("modalLoginDate").innerText = loginAt || "기록 없음";

        modal.dataset.teacherId = teacherId;
        toggleBtn.innerText = status === "활성" ? "비활성" : "활성";
        modal.style.display = "flex";
    });

    // ✅ 닫기 버튼
    const closeModal = () => modal.style.display = "none";
    closeBtn.addEventListener("click", closeModal);
    closeModalBtn.addEventListener("click", closeModal);
    window.addEventListener("click", (e) => {
        if (e.target === modal) closeModal();
    });
});
