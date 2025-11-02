<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- LECTURE DETAIL CONTROLLER --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_detail.css'/>">
</head>
<body>
<section class="summary">
    <div class="summary-top">
        <div class="summary-text">
            <div class="summary-lecture">
                <div class="summary-tag" data-en="<c:out value='${lecture.subject}'/>"><c:out value="${lecture.subject}"/></div>
                <div class="summary-name">${lecture.title}</div>
                <div class="summary-explain">

                    <%--<p>${lecture.description}</p>--%>
                    <p>${lecture.summary}</p>
                </div>
            </div>
            <div class="summary-report">
                <div class="summary-review">
                    <div class="summary-star">★</div>
                    <div class="summary-score">&nbsp;<fmt:formatNumber value="${lecture.averageRate}" pattern="#0.0"/>&nbsp;</div>
                    <div class="summary-count">수강평 <fmt:formatNumber value="${reviewCount}" type="number"/>개</div>
                </div>
                <div class="summary-student">
                    <div class="summary-member">
                        <img src="<c:url value='/img/png/student.png'/>" alt="수강생">
                    </div>
                    <div class="summary-total">&nbsp;수강생 <fmt:formatNumber value="${lecture.totalStudents}" type="number"/>명</div>
                </div>
            </div>
        </div>
        <div class="summary-thumbnail">

        <c:choose>
            <%--
            <c:when test="${not empty lecture.thumbnailFile and lecture.thumbnailFile.fileId ne 0}">
            </c:when>
            --%>
            <c:when test="${not empty lecture.thumbnailFile and lecture.thumbnailFile.fileId ne 0}">
                <img src="${fileDomain}/${lecture.thumbnailFile.filePath}" class="summary-thumbnail" alt="강의 썸네일">
            </c:when>

            <c:otherwise>
                <img src="<c:url value='/img/png/thumbnail.png'/>" alt="기본 이미지">
            </c:otherwise>
        </c:choose>

        </div>
    </div>
    <div class="summary-bottom">
        <div class="summary-introduce">
            <div class="summary-category">
                <p>강사 :</p>
                <p>커리큘럼 :</p>
                <p>난이도 :</p>
            </div>
            <div class="summary-item">
                <p>${teacher.member.nickname}</p>
                <p>${lecture.videoCount}강</p>
                <p data-en="<c:out value='${lecture.difficulty}'/>"><c:out value="${lecture.difficulty}" /></p>
            </div>
        </div>
        <div class="summary-pay">
            <div class="summary-money"><fmt:formatNumber value="${lecture.price}" type="number"/>원</div>
            <div class="summary-buttons">
                <button class="summary-like" data-lecture-id="${lecture.lectureId}" data-member-id="${loginMember.memberId}">
                    <img src="/img/png/like1.png" alt="좋아요">
                    <span class="like-count">${lecture.likeCount}</span>
                </button>
                <c:if test="${isBuyer}">
                    <button class="summary-purchase" onclick="window.location.href='/player?lectureId=1'">
                        수강하러 가기
                    </button>
                </c:if>

                <c:if test="${not isBuyer}">
                    <button class="summary-purchase" id="purchase">
                        바로 구매하기
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</section>
</body>
</html>

<script>
    document.addEventListener("DOMContentLoaded", async () => {
        const likeBtn = document.querySelector(".summary-like");
        if (!likeBtn) return;

        const lectureId = likeBtn.dataset.lectureId;
        const memberId = likeBtn.dataset.memberId;
        const img = likeBtn.querySelector("img");
        const count = likeBtn.querySelector(".like-count");

        try {
            // ✅ 좋아요 상태 조회 API 호출
            const res = await fetch(`/lecture-like/${lectureId}/status?memberId=${memberId}`);
            if (!res.ok) throw new Error("상태 요청 실패 " + res.status);
            const data = await res.json();

            console.log("💗 좋아요 상태 확인:", data);

            // ✅ 좋아요 여부에 따라 하트 이미지 설정
            img.src = data.liked ? "/img/png/like2.png" : "/img/png/like1.png";
            count.textContent = data.likeCount;

            // 일반 학생만 좋아요 가능
            likeBtn.addEventListener("click", async () => {
                try {
                    const method = data.liked ? "DELETE" : "POST";
                    const url = `/lecture-like/${lectureId}/${data.liked ? "remove" : "add"}`;
                    const dto = { lectureId, memberId };

                    const res2 = await fetch(url, {
                        method: method,
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(dto)
                    });

                    const result = await res2.json();
                    console.log("🔥 좋아요 토글 결과:", result);

                    // 상태 업데이트
                    data.liked = result.liked;
                    img.src = data.liked ? "/img/png/like2.png" : "/img/png/like1.png";
                    count.textContent = result.likeCount;

                } catch (err) {
                    console.error("❌ 좋아요 토글 실패:", err);
                }
            });


        } catch (err) {
            console.error("❌ 초기 좋아요 상태 로드 실패:", err);
        }


        document.getElementById("purchase").addEventListener("click", async (e) => {
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
    });
</script>
