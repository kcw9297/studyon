/**
 * 관리자 페이지 공통 토글/모달 관리 스크립트
 * ver 1.0 - 2025-10-29
 * 적용 대상: member_management.jsp, teacher_management.jsp
 */
document.addEventListener("DOMContentLoaded", () => {
    console.log("[INIT] 공통 모달 이벤트 로드됨 (management_toggle.js)");

    const modal = document.getElementById("memberModal");
    if (!modal) {
        console.warn("[WARN] 모달 요소(#memberModal)를 찾을 수 없습니다.");
        return;
    }

    const toggleBtn = document.getElementById("toggleBtn");
    const closeBtn = document.querySelector(".close-btn");
    const closeModalBtn = document.getElementById("closeModalBtn");

    const closeModal = () => (modal.style.display = "none");

    closeBtn?.addEventListener("click", closeModal);
    closeModalBtn?.addEventListener("click", closeModal);
    window.addEventListener("click", (e) => {
        if (e.target === modal) closeModal();
    });

    // ✅ 보기 버튼 클릭
    document.addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-view");
        if (!btn) return;

        const row = btn.closest("tr");
        if (!row) return;

        // ✅ 데이터 구분: member / teacher
        const type = modal.dataset.type ?? "member";
        modal.dataset.type = type;

        if (type === "teacher") {
            setTeacherModal(row, btn);
        } else {
            setMemberModal(row, btn);
        }

        modal.style.display = "flex";
    });

    // ✅ 상태 토글 클릭
    toggleBtn?.addEventListener("click", async () => {
        const type = modal.dataset.type ?? "member";
        const targetId =
            type === "teacher"
                ? modal.dataset.teacherId
                : modal.dataset.memberId;

        const name = document.getElementById("modalName")?.innerText || "-";
        const currentStatus = document
            .getElementById("modalStatus")
            ?.innerText.trim();

        if (!targetId) {
            alert(`${type === "teacher" ? "강사" : "회원"} 정보를 찾을 수 없습니다.`);
            return;
        }

        const confirmMsg =
            currentStatus === "활성"
                ? `${name}님을 비활성화(정지)하시겠습니까?`
                : `${name}님을 활성화(해제)하시겠습니까?`;

        if (!confirm(confirmMsg)) return;

        try {
            const url =
                type === "teacher"
                    ? `/admin/api/teachers/toggle/${targetId}`
                    : `/admin/api/members/toggle/${targetId}`;

            console.log("[FETCH]", url);
            const res = await fetch(url, {
                method: "POST",
                headers: {
                    "X-Requested-From": window.location.pathname,
                },
            });
            const json = await res.json();

            console.log("[DEBUG] 서버 응답:", json);

            if (!json.success) {
                alert("⚠️ 상태 변경 실패: " + (json.message ?? "요청 실패"));
                return;
            }

            const isActive = json.data?.isActive ?? false;
            const newStatus = isActive ? "활성" : "비활성";

            document.getElementById("modalStatus").innerText = newStatus;
            toggleBtn.innerText = isActive ? "정지" : "해제";

            // 테이블 반영
            const row = document.querySelector(
                `button[data-${type}-id="${targetId}"]`
            )?.closest("tr");
            if (row) {
                row.children[4].innerHTML = isActive
                    ? `<span class="status-active">활성</span>`
                    : `<span class="status-banned">비활성</span>`;
            }

            alert("상태가 변경되었습니다.");
            closeModal();
            window.location.reload();
        } catch (err) {
            console.error("[ERROR]", err);
            alert("상태 변경 중 오류가 발생했습니다.");
        }
    });

    /** 🧩 회원용 모달 세팅 */
    function setMemberModal(row, btn) {
        const name = row.children[2].innerText;
        const email = row.children[3].innerText;
        const role = row.children[4].innerText;
        const status = row.children[5].innerText.trim();
        const date = row.children[6].innerText;
        const loginAt = row.children[7].innerText;
        const memberId = btn.dataset.memberId;

        modal.dataset.memberId = memberId;

        document.getElementById("modalName").innerText = name;
        document.getElementById("modalEmail").innerText = email;
        document.getElementById("modalRole").innerText = role;
        document.getElementById("modalStatus").innerText = status;
        document.getElementById("modalDate").innerText = date;
        document.getElementById("modalLoginDate").innerText = loginAt || "기록 없음";
        toggleBtn.innerText = status === "활성" ? "정지" : "해제";
    }

    /** 🧩 강사용 모달 세팅 */
    function setTeacherModal(row, btn) {
        const name = row.children[1].innerText;
        const email = row.children[2].innerText;
        const subject = row.children[3].innerText;
        const status = row.children[4].innerText.trim();
        const date = row.children[5].innerText;
        const loginAt = row.children[6].innerText;
        const teacherId = btn.dataset.teacherId;

        modal.dataset.teacherId = teacherId;

        document.getElementById("modalName").innerText = name;
        document.getElementById("modalEmail").innerText = email;
        document.getElementById("modalSubject").innerText = subject;
        document.getElementById("modalStatus").innerText = status;
        document.getElementById("modalDate").innerText = date;
        document.getElementById("modalLoginDate").innerText = loginAt || "기록 없음";
        toggleBtn.innerText = status === "활성" ? "비활성" : "활성";
    }
});
