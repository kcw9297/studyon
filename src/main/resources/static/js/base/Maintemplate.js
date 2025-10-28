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
