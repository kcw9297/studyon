<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="content">
    <jsp:include page="lecture_summary.jsp"/>
    <jsp:include page="lecture_navigation.jsp"/>
    <jsp:include page="lecture_introduce.jsp"/>
</div>
<script src="<c:url value='/js/page/lecture/lecture_detail.js'/>"></script>


<script>

    document.querySelector(".summary-purchase").addEventListener("click", async (e) => {
        e.preventDefault();

        try {
            // form 생성
            const lectureId = window.location.pathname.split("/").pop(); // 가장 마지막에 있는 경로
            const formData = new FormData();
            formData.append("lectureId", lectureId);

            // REST API 요청
            const res = await fetch(`/api/payments/access`, {
                headers: {'X-Requested-From': window.location.pathname + window.location.search},
                method: "POST",
                body: formData
            });

            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {

                // 로그인이 필요한 경우
                if (rp.statusCode === 401) {

                    // 로그인 필요 안내 전달
                    if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                        window.location.href = rp.redirect || "/login";
                    }

                    // 로직 중단
                    return;
                }

                // 권한이 부족한 경우
                if (rp.statusCode === 403) {
                    alert(rp.message || "접근 권한이 없습니다.");
                    return;
                }

                // 입력 값이 유효하지 않은 경우
                if (rp.statusCode === 400) {
                    alert(rp.message || "입력 값이 유효하지 않습니다.");
                    return;
                }

                // 기타 예기치 않은 오류가 발생한 경우
                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            // 성공 응답
            window.location.href = rp.redirect;


        } catch (error) {
            console.error('결제요청 실패 오류:', error);
        }

    })


</script>