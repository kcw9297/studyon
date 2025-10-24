document.addEventListener("DOMContentLoaded", () => {

    // [1] 관리자 대시보드에서 총 회원 수 가져오기
    fetch("/api/members/countAll")
        .then(res => res.json())
        .then(json => {
            // ✅ RestUtils.ok() 구조를 고려해야 함
            if (!json.success) {
                console.error("회원 수 조회 실패:", json.message);
                return;
            }
            const count = json.data; // <-- 핵심 수정
            renderMemberCount(count);
        })
        .catch(err => console.error("총 회원 수 조회 실패:", err));

    // [2] 렌더링 함수 정의
    function renderMemberCount(count) {
        const valueElement = document.querySelector(".status-value");
        if (!valueElement) {
            console.error("status-value 요소를 찾을 수 없습니다.");
            return;
        }

        // 숫자를 3자리마다 콤마 + '명' 붙여서 표시
        valueElement.textContent = `${count.toLocaleString()}명`;
    }

    // [3] 추가로 인기 강의 등 다른 비동기도 병렬로 불러올 수 있음
    // fetch("/api/lecture/best", ...) ← 기존 로직 유지 가능
});
