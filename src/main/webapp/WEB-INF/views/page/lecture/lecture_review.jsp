<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_detail.css'/>">
</head>
<body>
<section class="review-section">
<%--    <h2 class="review-title">💬 수강평</h2>--%>


    <!-- 리뷰 입력 영역 -->
<%--    <div class="review-input-box">--%>
<%--        <form>--%>
<%--            <textarea id="review-content" placeholder="수강 후기를 작성해주세요." maxlength="300"></textarea>--%>
<%--            <div>--%>
<%--                <select id="review-rate">--%>
<%--                    <option value="5">★★★★★ (5점)</option>--%>
<%--                    <option value="4">★★★★☆ (4점)</option>--%>
<%--                    <option value="3">★★★☆☆ (3점)</option>--%>
<%--                    <option value="2">★★☆☆☆ (2점)</option>--%>
<%--                    <option value="1">★☆☆☆☆ (1점)</option>--%>
<%--                </select>--%>
<%--                <button id="review-submit-Btn" class="submit">등록</button>--%>
<%--            </div>--%>
<%--        </form>--%>
<%--    </div>--%>
</section>
</body>
</html>

<style>
    .review-section {
        width: 100%;
        background: #fff;
        border-top: 1px solid #ddd;
        font-family: 'Noto Sans KR', sans-serif;
        margin-top:15px;
    }

    .review-title {
        font-size: 22px;
        font-weight: 700;
        margin-bottom: 20px;
    }

    /* 입력 영역 */
    .review-input-box {
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        margin-bottom: 30px;
        background: #fafafa;
    }

    .review-input-box textarea {
        width: 100%;
        min-height: 80px;
        border: 1px solid #ccc;
        border-radius: 6px;
        padding: 10px;
        font-size: 15px;
        resize: vertical;
        outline: none;
        box-sizing: border-box;
    }

    .review-input-bottom {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        margin-top: 10px;
        gap: 10px;
    }

    .review-input-bottom select {
        padding: 6px 10px;
        font-size: 14px;
        border-radius: 6px;
        border: 1px solid #ccc;
    }

    .review-input-bottom button {
        background: #27ae60;
        color: white;
        border: none;
        padding: 8px 18px;
        border-radius: 8px;
        cursor: pointer;
        font-weight: 600;
    }

    .review-input-bottom button:hover {
        background: #1f8e50;
    }

    /* 리뷰 카드 */
    .review-list {
        display: flex;
        flex-direction: column;
        gap: 5px;
    }

    .review-item {
        border: 1px solid #ddd;
        border-radius: 10px;
        padding: 15px 20px;
        background: #fff;
        box-shadow: 0 2px 5px rgba(0,0,0,0.05);
    }

    .review-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 14px;
        color: #555;
        margin-bottom: 10px;
    }

    .review-writer {
        font-weight: 600;
        color: #2c3e50;
    }

    .review-rating {
        color: #f1c40f;
        font-weight: 600;
    }

    .review-content {
        font-size: 15px;
        line-height: 1.6;
        color: #333;
    }

    /* 더보기 버튼 */
    .review-more {
        text-align: right;
        margin-top: 5px;
        margin-bottom: 20px;
    }

    .review-more button {
        background: #eee;
        border: none;
        border-radius: 6px;
        padding: 8px 16px;
        cursor: pointer;
        transition: all 0.2s ease;
    }

    .review-more button:hover {
        background: #ddd;
    }

</style>

<script>
    addEventListener("DOMContentLoaded", async () => {

        //전역변수 관리
        const pathParts = window.location.pathname.split("/");
        const lectureId = pathParts[pathParts.length - 1];
        const submitBtn = document.getElementById("review-submit-Btn");
        console.log("📘 lectureId =", lectureId);

        //수강평 읽기
        try {
            const response = await fetch("/api/lectures/reviews/" + lectureId);
            const json = await response.json();
            const reviews = json.data; // ✅ 서버에서 받은 리뷰 리스트
            console.log("lectureReviewData =", reviews);

            const reviewList = document.querySelector(".review-list");
            reviewList.innerHTML = ""; // 기존 목록 초기화

        <%--    reviews.forEach(review => {--%>
        <%--        const item = document.createElement("div");--%>
        <%--        item.classList.add("review-item");--%>

        <%--        item.innerHTML = `--%>
        <%--    <div class="review-header">--%>
        <%--        <span class="review-writer">\${review.nickname || "익명"}</span>--%>
        <%--        <span class="review-rating">\${"★".repeat(review.rating)}${"☆".repeat(5 - review.rating)}</span>--%>
        <%--        <span class="review-date">\${review.createdAt?.substring(0, 10) || ""}</span>--%>
        <%--    </div>--%>
        <%--    <div class="review-content">\${review.content}</div>--%>
        <%--`;--%>

        <%--        reviewList.appendChild(item);--%>
        <%--    });--%>

        } catch (err) {
            console.error("❌ 수강평 불러오기 실패:", err);
        }



        //수강평 등록
        submitBtn.addEventListener("click", async () => {
            try {
                const reviewRate = document.getElementById("review-rate");
                const reviewContent = document.getElementById("review-content");

                document.getElementById("review")
                const formData = new FormData();
                formData.append("lectureId", lectureId);
                formData.append("rating",reviewRate.value );
                formData.append("content", reviewContent.value);

                const res = await fetch("/api/lectures/reviews/create", {
                    method: "POST",
                    body: formData
                });

                if (res.ok) {
                    const data = await res.json();
                    console.log("등록 성공:", data);
                    alert("수강평이 등록되었습니다.");
                    location.reload();
                } else {
                    console.error("서버 응답 오류:", res.status);
                    alert("강의 등록 요청에 실패했습니다.");
                }
            } catch (err) {
                console.error("강의 등록 중 오류:", err);
                alert("강의 등록 중 문제가 발생했습니다.");
            }
        })
    });
</script>