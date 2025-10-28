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

    // ✅ (3) 상태 토글 버튼
    toggleBtn.addEventListener("click", async () => {
        const memberId = modal.dataset.memberId;
        const name = document.getElementById("modalName").innerText;
        const currentStatus = document.getElementById("modalStatus").innerText.trim();

        if (!memberId) {
            alert("회원 정보를 찾을 수 없습니다.");
            return;
        }

        const confirmMsg = currentStatus === "활성"
            ? `${name}님을 비활성화(정지)하시겠습니까?`
            : `${name}님을 활성화(해제)하시겠습니까?`;

        if (!confirm(confirmMsg)) return;

        try {
            const res = await fetch(`/admin/api/members/toggle/${memberId}`, {
                method: "POST",
                headers: { "X-Requested-From": window.location.pathname + window.location.search },
            });
            const json = await res.json();

            console.log("[DEBUG] 서버 응답:", json);

            if (!json.success) {
                alert("⚠️ 상태 변경 실패: " + (json.message ?? "요청 실패"));
                return;
            }

            const isActive = json.data?.isActive ?? false;
            const newStatus = isActive ? "활성" : "비활성";

            // 모달 갱신
            document.getElementById("modalStatus").innerText = newStatus;
            toggleBtn.innerText = isActive ? "정지" : "해제";

            // 테이블도 즉시 반영
            const row = document.querySelector(`button[data-member-id="${memberId}"]`)?.closest("tr");
            if (row) {
                row.children[4].innerHTML = isActive
                    ? `<span class="status-active">활성</span>`
                    : `<span class="status-banned">비활성</span>`;
            }

            alert("상태가 변경되었습니다.");
            window.location.reload(); //  페이지 새로고침
            modal.style.display = "none";
        } catch (err) {
            console.error("[ERROR] 요청 중 오류:", err);
            alert("상태 변경 중 오류가 발생했습니다.");
        }
    });
    function trimDateTime(datetime) {
        if (!datetime) return "-";
        // 'T' → 공백으로 바꾸고, 소수점(밀리초) 이후 제거
        return datetime.replace("T", " ").split(".")[0];
    }
});
