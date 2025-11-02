<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

<div class="teacher-review-container">
    <h1 class="page-title">📚 내 강의 수강평</h1>

    <div class="filter-box">
        <label for="lectureSelect">강의 선택:</label>
        <select id="lectureSelect">
            <option value="1"></option>
            <option value="2"></option>
            <option value="3"></option>
        </select>
    </div>

    <div id="reviewList" class="review-list"></div>
</div>

<script>
    let allReviews = [];
    // ✅ 별점 HTML 생성
    let displayCount = 5;
    const list = document.getElementById("reviewList");
    const select = document.getElementById("lectureSelect");

    const renderStars = (rating) => "★".repeat(rating) + "☆".repeat(5 - rating);

    // ✅ 리뷰 렌더링
    const renderReviews = (reviews) => {
        const list = document.getElementById("reviewList");
        list.innerHTML = "";

        if (!Array.isArray(reviews)) {
            console.warn("⚠️ 리뷰 데이터 형식이 배열이 아닙니다:", reviews);
            list.innerHTML = "<p class='no-review'>리뷰 데이터가 올바르지 않습니다.</p>";
            return;
        }

        if (reviews.length === 0) {
            list.innerHTML = "<p class='no-review'>아직 등록된 수강평이 없습니다.</p>";
            return;
        }

        reviews.forEach(r => {
            const div = document.createElement("div");
            div.classList.add("review-card");
            div.innerHTML = `
    <div class="review-header">
        <div class="review-author">${r.nickname}</div>
        <div class="review-date">${r.date}</div>
    </div>
    <div class="review-rating">\${renderStars(r.rating)} <span>\${r.rating.toFixed(1)}</span></div>
    <p class="review-content">\${r.content}</p>
`;
            list.appendChild(div);
        });
    };

    list.addEventListener("scroll", () => {
        if (list.scrollTop + list.clientHeight >= list.scrollHeight - 20) {
            displayCount += 5; // ✅ 스크롤 내려갈 때마다 5개씩 추가 표시
            const lectureId = Number(select.value);
            const filtered = allReviews.filter(r => Number(r.lectureId) === lectureId);
            renderReviews(filtered);
        }
    });





    // ✅ 강의 선택 이벤트
    document.getElementById("lectureSelect").addEventListener("change", (e) => {
        displayCount = 5;
        const lectureId = Number(e.target.value); // 문자열 → 숫자로 변환
        console.log("📘 선택된 강의 ID:", lectureId);

        // ✅ allReviews에서 해당 강의의 리뷰만 필터링
        const filteredReviews = allReviews.filter(r => Number(r.lectureId) === lectureId);
        console.log("🧾 필터링된 리뷰:", filteredReviews);

        // ✅ 화면 렌더링
        renderReviews(filteredReviews);
    });
    //console data
    document.addEventListener("DOMContentLoaded", async () => {
        try {
            // 1️⃣ 선생님 강의 목록 가져오기
            const lectureRes = await fetch("/api/teachers/management/reviews/lectures");
            const lectureResult = await lectureRes.json();
            console.log("🎓 내 강의 목록:", lectureResult.data);

            // 2️⃣ 첫 번째 강의 ID 선택
            const lectureList = lectureResult.data || [];
            const lectureSelect = document.getElementById("lectureSelect");
            lectureSelect.innerHTML = "";

            if (lectureList.length === 0) {
                lectureSelect.innerHTML = `<option>등록된 강의가 없습니다</option>`;
                return;
            }

            // 3️⃣ 드롭다운 채우기
            lectureList.forEach(l => {
                const opt = document.createElement("option");
                opt.value = l.lectureId;
                opt.textContent = l.title;
                lectureSelect.appendChild(opt);
            });

            // 4️⃣ 첫 강의 기준으로 리뷰 불러오기
            const firstLectureId = lectureList[2].lectureId;
            console.log("📘 첫 강의 ID:", firstLectureId);

            const reviewRes = await fetch('/api/teachers/management/reviews/' + firstLectureId);
            const reviewResult = await reviewRes.json();
            console.log("📝 리뷰 데이터:", reviewResult.data);

            allReviews = reviewResult.data || [];

        } catch (err) {
            console.error("🚨 데이터 불러오기 실패:", err);
        }
    });


</script>

<style>
    .teacher-review-container {
        max-width: 1400px;
        margin: 30px auto;
        padding: 10px 40px;
        background: #fff;
        border-radius: 18px;
    }

    .page-title {
        font-size: 26px;
        font-weight: 700;
        margin-bottom: 24px;
        display: flex;
        align-items: center;
        gap: 8px;
        color: #2c3e50;
    }

    .filter-box {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 30px;
    }

    #lectureSelect {
        padding: 8px 12px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 8px;
    }

    .review-list {
        display: flex;
        flex-direction: column;
        gap: 18px;
    }

    .review-card {
        background: #fafafa;
        border-radius: 12px;
        padding: 20px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.06);
    }

    .review-header {
        display: flex;
        justify-content: space-between;
        font-weight: 600;
        margin-bottom: 8px;
    }

    .review-author {
        color: #34495e;
    }

    .review-date {
        color: #888;
        font-size: 14px;
    }

    .review-rating {
        color: #f1c40f;
        font-size: 18px;
        font-weight: 600;
        margin-bottom: 10px;
    }

    .review-content {
        color: #333;
        font-size: 16px;
        line-height: 1.5;
    }

    .no-review {
        text-align: center;
        color: #aaa;
        padding: 40px 0;
        font-size: 17px;
    }
</style>
