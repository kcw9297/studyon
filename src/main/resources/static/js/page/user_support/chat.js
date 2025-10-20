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




document.addEventListener("DOMContentLoaded", () => {
  const searchInput = document.getElementById("searchInput");
  const searchResults = document.getElementById("searchResults");

  // 🔍 포커스 시 결과창 표시
  searchInput.addEventListener("focus", () => {
    searchResults.style.display = "flex";
  });

  // 🔍 입력 중일 때 (예: API 검색 연결 가능)
  searchInput.addEventListener("input", (e) => {
    const keyword = e.target.value.trim();
    if (keyword === "") {
      searchResults.style.display = "none";
    } else {
      searchResults.style.display = "flex";
    }
  });

  // ✅ input 외부 클릭 시 닫기
  document.addEventListener("click", (e) => {
    if (!searchResults.contains(e.target) && e.target !== searchInput) {
      searchResults.style.display = "none";
    }
  });
});
