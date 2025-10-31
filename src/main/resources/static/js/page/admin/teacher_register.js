document.addEventListener("DOMContentLoaded", () => {
    console.log("[INIT] 강사 등록 페이지 로드 완료");

    const form = document.querySelector(".teacher-create-form");
    const btn = document.getElementById("createTeacherBtn");
    if (!form || !btn) return;

    btn.addEventListener("click", async (e) => {
        e.preventDefault();

        const email = form.email.value.trim();
        const nickname = form.nickname.value.trim();
        const password = form.password.value.trim();
        const subject = form.subject.value;
        const description = form.description.value.trim();

        // ✅ [1] 유효성 검사
        if (!email || !nickname || !password || !subject) {
            alert("⚠️ 필수 항목을 모두 입력해주세요.");
            return;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert("⚠️ 올바른 이메일 형식이 아닙니다.");
            return;
        }

        if (password.length < 4) {
            alert("⚠️ 비밀번호는 4자리 이상이어야 합니다.");
            return;
        }

        // ✅ [2] 이메일 중복검사
        const duplicateCheck = await fetch(`/admin/api/members/check-email?email=${encodeURIComponent(email)}`);
        const jsonCheck = await duplicateCheck.json();
        if (jsonCheck.exists) {
            alert("⚠️ 이미 등록된 이메일입니다.");
            return;
        }

        // ✅ [3] 서버 전송
        const formData = new FormData(form);

        try {
            const res = await fetch("/admin/api/members/teacher/register", {
                method: "POST",
                body: formData
            });

            const json = await res.json();
            console.log("[응답 JSON]", json);

            if (json.success) {
                alert("✅ 강사 등록이 완료되었습니다!");
                window.location.href = "/admin/teacher_management";
            } else {
                alert("❌ 등록 실패: " + (json.message || "서버 오류"));
            }
        } catch (err) {
            console.error("[등록 실패]", err);
            alert("⚠️ 등록 중 오류가 발생했습니다:\n" + err.message);
        }
    });
});
