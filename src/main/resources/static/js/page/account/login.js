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

          // REST API 요청
          const res = await fetch(form.action, {
              method: form.method,
              body: new FormData(form)
          });

          // JSON 데이터 파싱
          const rp = await res.json();
          console.log("서버 응답:", rp);

          // 실패 처리
          if (!res.ok || !rp.success) {
              const errorElem = document.getElementById(`loginError`);
              if (errorElem) errorElem.textContent = rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.";
              return;
          }

          // 성공 처리
          window.location.href =  rp.redirect || "/";

      } catch (error) {
          console.error("로그인 실패:", error);
      }




  });
});
