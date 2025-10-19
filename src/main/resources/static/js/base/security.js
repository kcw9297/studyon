// CSRF 토큰 쿠키 추출 함수
function getCSRFToken() {
    // 부모 페이지의 meta 태그에서 읽기
    try {
        return $(window.document).find('meta[name="_csrf"]').attr('content');
    } catch (e) {
        console.error('토큰 읽기 실패:', e);
        return null;
    }
}