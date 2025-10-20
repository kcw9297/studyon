document.addEventListener("DOMContentLoaded", function () {
  const loginBtn = document.getElementById("loginModalBtn");
  const modalBg = document.getElementById("loginModalBg");
  const closeBtn = document.getElementById("closeModal");

  // 🔹 로그인 버튼 클릭 시 모달 열기
  loginBtn.addEventListener("click", function (e) {
    e.preventDefault();
    modalBg.style.display = "flex";
  });

  // 🔹 닫기 버튼 클릭 시 모달 닫기
  closeBtn.addEventListener("click", function () {
    modalBg.style.display = "none";
  });

  // 🔹 배경 클릭 시 모달 닫기
  window.addEventListener("click", function (e) {
    if (e.target === modalBg) {
      modalBg.style.display = "none";
    }
  });
});