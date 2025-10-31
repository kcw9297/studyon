<%@ page contentType ="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- ✅ 메인 배너 -->
<div class="main-banner-container">
    <img src="<c:url value='/img/png/banner1.png'/>" class="main-banner fade active" alt="강사 배너1">
    <img src="<c:url value='/img/png/banner2.png'/>" class="main-banner fade" alt="강사 배너2">
</div>

<!-- ✅ 강사 프로필 영역 -->
<div class="teacher-profile-wrapper" data-teacher-id="${teacher.teacherId}">
    <div class="teacher-img-area">
        <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="강사이미지" class="teacher-img">
    </div>

    <div class="teacher-info-box">
        <div class="teacher-header">
            <h2 class="teacher-name">이정우 강사</h2>
            <span class="teacher-badge">수학 전문가</span>
        </div>
        <p class="teacher-desc">
            10년 경력의 수능 수학 전문 강사로, 개념부터 실전까지 체계적인 학습을 돕습니다.<br>
            인프런 베스트셀러 강의 <strong>“수학의 정석 실전편”</strong>으로 누적 수강생 12,000명을 달성했습니다.
        </p>

        <div class="teacher-stats">
            <div class="stat-box">
                <div class="stat-label">총 수강생</div>
                <div class="stat-value">12,384명</div>
            </div>
            <div class="stat-box">
                <div class="stat-label">총 강의 수</div>
                <div class="stat-value">8개</div>
            </div>
        </div>

        <div class="teacher-detail">
            <p><strong>전공 :</strong> 서울대학교 수학교육과</p>
            <p><strong>경력 :</strong> 메가스터디 온라인 강사 6년 / 현 StudyOn 전임 강사</p>
            <p><strong>담당 과목 :</strong> 수학 I · II / 미적분 / 확률과 통계</p>
        </div>
    </div>
</div>
<button class="teacher-lecture-all-button">전체 강의 보기</button>



<div class="main-content-container">
    <div id="lecturePage" data-subject="${subject.name()}">
        <div class="recomment-lecture-title">
            ${subject.value} 주간 인기/추천 강의
        </div>
        <div class="recent-lecture-container">
            <!-- best_lectures.js -->
        </div>
        <div class="recomment-lecture-title">
            요새 뜨는 강의
        </div>
        <div class="recent-lecture-container">
            <!-- recent_lecture.js -->
        </div>
        <div class="recomment-lecture-title">
            최근 수강평
        </div>
        <div class="lecture-comment-box">

        </div>
    </div>
</div>



<style>

    .teacher-lecture-all-button{
        border:2px solid black;
        padding:5px;
        font-weight:bold;
        font-size:18px;
        background-color:orange;
    }

    /* ✅ 강사 프로필 */
    .teacher-profile-wrapper {
        display: flex;
        align-items: center;
        gap: 50px;
        background: #fff;
        border-radius: 16px;
        padding: 40px 50px;
        width:100%;
    }

    .teacher-img-area {
        flex: 0 0 auto;
    }

    .teacher-img {
        border: 6px solid #3b82f6;
        width: 240px;
        height: 240px;
        border-radius: 50%;
        object-fit: cover;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
    }


    .teacher-info-box {
        flex: 1;
    }
    .teacher-header {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 10px;
    }

    .teacher-name {
        font-size: 32px;
        font-weight: 700;
        color: #222;
    }

    .teacher-badge {
        background: #007bff;
        color: #fff;
        font-size: 14px;
        font-weight: 600;
        padding: 6px 10px;
        border-radius: 6px;
    }

    /* 소개문 */
    .teacher-desc {
        font-size: 17px;
        color: #444;
        line-height: 1.6;
        margin-bottom: 25px;
    }

    /* 통계 정보 */
    .teacher-stats {
        display: flex;
        align-items: center;
        gap: 40px;
        margin-bottom: 25px;
    }

    .stat-box {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
    }

    .stat-label {
        font-size: 14px;
        color: #777;
    }

    .stat-value {
        font-size: 20px;
        font-weight: 700;
        color: #111;
    }

    .teacher-name {
        font-size: 30px;
        font-weight: 700;
        margin-bottom: 10px;
    }

    .teacher-desc {
        font-size: 18px;
        margin-bottom: 20px;
        color: #444;
    }

    .teacher-detail p {
        margin: 5px 0;
        font-size: 16px;
        color: #666;
    }

    /* ✅ 강의 목록 공통 */
    .lecture-section-title {
        font-size: 24px;
        font-weight: bold;
        margin: 40px 0 15px 15px;
    }

    .recent-lecture-container {
        display: grid;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;
        box-sizing: border-box;
        background-color: white;
        margin-bottom:30px;
        padding: 10px;
    }

    .recent-lecture-item {
        width: 260px;
        height: auto;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        box-shadow: inset 0 0 0 2px rgba(0, 0, 0, 0.4);
    }

    .recent-lecture-thumbnail {
        width: 100%;
        height: 180px;
        object-fit: cover;
        border-bottom: 1px solid #ddd;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }


    /* 배너 */
    .main-banner-container {
        position: relative;
        width: 100%;
        height: 450px;
        overflow: hidden;
        border-radius: 10px;
        margin-bottom: 20px;
        margin-top:40px;
    }

    .main-banner {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
        opacity: 0;
        transition: opacity 1.5s ease-in-out;
    }

    .main-banner.active {
        opacity: 1;
    }

    /* lecture-item 관련 css*/

    .recent-lecture-container {
        display: grid;
        height:auto;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;             /* ✅ 1420px 고정 대신 부모에 맞춤 */
        box-sizing: border-box;  /* ✅ padding/gap 계산 포함 */
        background-color: rgb(255, 255, 255);
    }


    .recent-lecture-item {
        width: 260px;            /* 고정 폭 (5등분용) */
        height: auto;           /* 고정 높이 */
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 10px;
        display: flex;
        flex-direction: column;  /* ✅ 세로로 쌓기 */
        justify-content: flex-start; /* 위쪽부터 쌓기 */
        box-sizing: border-box;
    }

    .recent-lecture-item:hover {
        background-color: #bbb;
        transform: translateY(-5px);
        cursor: pointer;
    }

    .lecture-section-title{
        font-size: 24px;
        font-weight: bold;
    }

    .lecture-comment-box{
        width:100%;
        height:100%;
        background-color: rgb(255, 255, 255);
    }

    .lecture-comment-box-item{
        display:flex;
        flex-direction:row;
        border: 2px solid black;
        padding:3px;
        margin-bottom:3px;
        border-radius:10px;
    }

    .lecture-comment-username{
        font-size:20px;
        font-weight:bold;
        margin-right:10px;
    }

    .lecture-comment-comment{
        font-size:20px;
        font-weight:bold;
    }

    .recomment-lecture-title{
        font-size:30px;
        font-weight:bold;
        text-align: left;
        margin-top:10px;
        margin-bottom:10px;
    }

    .lecture-comment-username{
        color: rgb(131, 212, 129);
    }

    .recent-lecture-thumbnail {
        width: 100%;
        height: 180px;
        object-fit: cover;
        border-bottom: 1px solid #ddd;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }

</style>

<%-- Local Script --%>
<!-- best_lectures.js -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // ✅ teacherId 읽기
        const wrapper = document.querySelector(".teacher-profile-wrapper");
        const teacherId = wrapper ? wrapper.dataset.teacherId : "";

        const params = new URLSearchParams();
        params.append("teacherId", teacherId);

        fetch("/api/teachers/profile/bestLecture", {
            method: "POST",
            headers: { "X-Requested-From": window.location.pathname + window.location.search },
            body: params
        })
            .then(function(res) { return res.json(); })
            .then(function(json) {
                console.log("서버 응답:", json);
                renderBestLectures(json.data);
            })
            .catch(function(err) {
                console.error("수강평 불러오기 실패:", err);
            });

        function renderBestLectures(bestLectures) {
            const teacherBestSection = document.getElementById("teacherBestLectures");
            if (!teacherBestSection) {
                console.error("teacherBestLectures 섹션을 찾을 수 없습니다.");
                return;
            }

            const container = teacherBestSection.querySelector(".recent-lecture-container");
            if (!container) {
                console.error("recent-lecture-container 요소를 찾을 수 없습니다.");
                return;
            }

            container.innerHTML = "";

            if (!bestLectures || bestLectures.length === 0) {
                container.innerHTML = "<p>강의 정보가 조회되지 않습니다.</p>";
                return;
            }

            bestLectures.forEach(function(bestLecture) {
                const item = document.createElement("div");
                item.classList.add("recent-lecture-item");

                // 🧩 변수로 미리 포맷팅
                const price = bestLecture.price ? bestLecture.price.toLocaleString("ko-KR") : "0";
                const students = bestLecture.totalStudents >= 10 ? "10+" : bestLecture.totalStudents;
                const rating = (bestLecture.averageRate != null ? bestLecture.averageRate : "N/A");
                const description = bestLecture.description || "강의 소개가 없습니다.";

                // div 클릭 시 들어갈 링크
                const detailUrl = "/lecture/detail/" + bestLecture.lectureId;

                // ✅ innerHTML 안전 문자열로 작성
                item.innerHTML =
                    "<a href='" + detailUrl + "'>" +
                    "<img src='/img/png/sample1.png' alt='강의이미지' class='recent-lecture-thumbnail'>" +
                    "<div class='lecture-info'>" +
                    "<p class='lecture-title'>" + bestLecture.title + "</p>" +
                    "<p class='lecture-info-text'>" + description + "</p>" +
                    "<p class='lecture-info-text'>₩" + price + "</p>" +
                    "<p class='lecture-info-text'>⭐ " + rating + "</p>" +
                    "<p class='lecture-info-text'>&#x1F9F8; " + students + "</p>" +
                    "</div>" +
                    "</a>";

                container.appendChild(item);
            });
        }
    });
</script>

<!-- recent_lectures.js -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const teacherRecentEl = document.getElementById("teacherRecentLectures");
        const teacherId = teacherRecentEl ? teacherRecentEl.dataset.teacherId : "";

        const params = new URLSearchParams();
        params.append("teacherId", teacherId);

        // ✅ GET 요청에는 body 사용하지 않도록 수정
        fetch("/api/teachers/profile/recentLecture?" + params.toString(), {
            method: "GET",
            headers: { "X-Requested-From": window.location.pathname + window.location.search }
        })
            .then(function(res) { return res.json(); })
            .then(function(json) {
                console.log("서버 응답:", json);
                renderRecentLectures(json.data);
            })
            .catch(function(err) {
                console.error("최근 강의 불러오기 실패:", err);
            });

        function renderRecentLectures(recentLectures) {
            const teacherRecentSection = document.getElementById("teacherRecentLectures");
            if (!teacherRecentSection) {
                console.error("teacherRecentLectures 요소를 찾을 수 없습니다.");
                return;
            }

            const container = teacherRecentSection.querySelector(".recent-lecture-container");
            if (!container) {
                console.error("recent-lecture-container 요소를 찾을 수 없습니다.");
                return;
            }

            container.innerHTML = "";

            if (!recentLectures || recentLectures.length === 0) {
                container.innerHTML = "<p>강의 정보가 조회되지 않습니다.</p>";
                return;
            }

            recentLectures.forEach(function(recentLecture) {
                const item = document.createElement("div");
                item.classList.add("recent-lecture-item");

                // 🧩 데이터 포맷팅
                const price = recentLecture.price ? recentLecture.price.toLocaleString("ko-KR") : "0";
                const students = recentLecture.totalStudents >= 10 ? "10+" : recentLecture.totalStudents;
                const rating = (recentLecture.averageRate != null ? recentLecture.averageRate : "N/A");
                const description = recentLecture.description || "강의 소개가 없습니다.";
                const detailUrl = "/lecture/detail/" + recentLecture.lectureId;

                item.innerHTML =
                    "<a href='" + detailUrl + "'>" +
                    "<img src='/img/png/sample1.png' alt='강의이미지' class='recent-lecture-thumbnail'>" +
                    "<div class='lecture-info'>" +
                    "<p class='lecture-title'>" + recentLecture.title + "</p>" +
                    "<p class='lecture-info-text'>" + description + "</p>" +
                    "<p class='lecture-info-text'>₩" + price + "</p>" +
                    "<p class='lecture-info-text'>⭐ " + rating + "</p>" +
                    "<p class='lecture-info-text'>&#x1F9F8; " + students + "</p>" +
                    "</div>" +
                    "</a>";

                container.appendChild(item);
            });
        }
    });
</script>

<!-- profile_reviews.js -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const teacherEl = document.getElementById("teacherComment");
        const teacherId = teacherEl ? teacherEl.dataset.teacherId : "";

        const params = new URLSearchParams();
        params.append("teacherId", teacherId);

        // ✅ URL 내 {teacherId} 부분은 실제 값으로 치환 필요
        fetch("/api/lectures/reviews/teacher/" + teacherId, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: params
        })
            .then(function(res) { return res.json(); })
            .then(function(json) {
                console.log("서버 응답:", json);
                renderComments(json.data);
            })
            .catch(function(err) {
                console.error("수강평 불러오기 실패:", err);
            });

        function renderComments(comments) {
            const container = document.querySelector(".lecture-comment-box");
            if (!container) {
                console.error("lecture-comment-box 요소를 찾을 수 없습니다.");
                return;
            }

            container.innerHTML = "";

            if (!comments || comments.length === 0) {
                container.innerHTML = "<p>아직 등록된 수강평이 없습니다.</p>";
                return;
            }

            comments.forEach(function(comment) {
                const item = document.createElement("div");
                item.classList.add("lecture-comment-box-item");
                item.innerHTML =
                    "<div class='lecture-comment-username'>" + comment.nickname + "</div>" +
                    "<div class='lecture-comment-comment'>" + comment.content + "</div>";
                container.appendChild(item);
            });
        }
    });
</script>


<script>
    // ✅ 배너 페이드 전환
    document.addEventListener("DOMContentLoaded", () => {
        const banners = document.querySelectorAll(".main-banner");
        let current = 0;
        setInterval(() => {
            banners[current].classList.remove("active");
            current = (current + 1) % banners.length;
            banners[current].classList.add("active");
        }, 4000);
    });
</script>
