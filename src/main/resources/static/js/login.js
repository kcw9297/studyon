document.addEventListener("DOMContentLoaded", () => {
  const toggle = document.querySelector(".toggle img");
  const passwordInput = document.querySelector(".password-box input");
  const form = document.querySelector(".login-form");

  toggle.addEventListener("click", () => {
    const type = passwordInput.getAttribute("type");
    if (type === "password") {
      passwordInput.setAttribute("type", "text");
      toggle.src = "/images/eyeshow.png";
    } else {
      passwordInput.setAttribute("type", "password");
      toggle.src = "/images/eyeoff.png";
    }
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = form.querySelector('input[type="email"]').value;
    const password = passwordInput.value;

    try {
      const res = await fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
      });

      if (!res.ok) throw new Error("서버 오류");

      const data = await res.json();

      if (data.success) {
        window.location.href = "/";
      } else {
        alert("로그인 실패: " + (data.message || "아이디/비밀번호 확인"));
      }
    } catch (err) {
      alert("요청 에러: " + err.message);
    }
  });
});
