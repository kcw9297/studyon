document.addEventListener("DOMContentLoaded", () => {
  const toggle = document.querySelector(".toggle img");
  const passwordInput = document.querySelector(".password-box input");
  const form = document.querySelector(".login-form");

  toggle.addEventListener("click", () => {
    const type = passwordInput.getAttribute("type");
    if (type === "password") {
      passwordInput.setAttribute("type", "text");
      toggle.src = "/img/png/eyeshow.png";
    } else {
      passwordInput.setAttribute("type", "password");
      toggle.src = "/img/png/eyeoff.png";
    }
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    try {
        const res = await fetch(form.action, {
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            method: form.method,
            body: new URLSearchParams(new FormData(form))
        });

        const data = await res.json().catch(() => ({})); // JSON 파싱 실패 대비

        if (!res.ok || !data.success) {
            console.log("data = {}", data)
            alert((data.fieldErrors && data.fieldErrors.errorGlobal) || "로그인 실패");
            return;
        }

        if (data.success) window.location.href =  data.redirect || "/";


    } catch (err) {
        alert("요청 에러: " + err.message);
    }
  });
});
