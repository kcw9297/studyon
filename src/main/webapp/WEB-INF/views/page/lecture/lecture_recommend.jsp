<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_recommend.css'/>">

<div id="content">
    <div class="main-container">
      <div class="sidebar-container">
          <div class="recommend-nav">
              <a href="<c:url value='/lecture/list'/>" class="nav-item">전체</a>
              <c:forEach var="subject" items="${subjects}">
                  <a href="<c:url value='/lecture/recommend/${subject}'/>" class="nav-item">${subject.value}</a>
              </c:forEach>
          </div>
    </div>
    <div class="main-content-container">
        <div id="lecturePage" data-subject="${subject.name()}">
          <div class="recomment-lecture-title">
            ${subject.value} 주간 인기/추천 강의
          </div>
                <div class="recent-lecture-container">
                    <!-- best_reviews.js -->
                </div>
                <div class="recomment-lecture-title">
                    최근 수강평
                </div>

                <div class="lecture-comment-box">
                    <!-- ✅ 비동기로 강의를 채울 빈 컨테이너
                    recent_reviews.js-->
                </div>

                <div class="recomment-lecture-title">
                    요새 뜨는 강의
                </div>
                <div class="recent-lecture-container">
                    <!-- recent_lecture.js -->
                </div>
            </div>
        </div>
    </div>
</div>


<style>
    .lecture-info{
        padding-left:4px;
        margin-top:0px;
        padding-bottom: 5px;
    }
</style>

<!-- best_lecture.js -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const lecturePage = document.getElementById("lecturePage");
        const subjectFromJSP = lecturePage ? lecturePage.dataset.subject : "";
        const count = 4;

        fetch("/api/lectures/best?subject=" + subjectFromJSP + "&count=" + count, {
            method: "GET",
            headers: { "X-Requested-From": window.location.pathname + window.location.search }
        })
            .then(function(res) { return res.json(); })
            .then(function(json) {
                const parsedData = json.data;
                console.log("✅ 최근 인기 강의 데이터:", parsedData);
                renderBestLectures(parsedData);
            })
            .catch(function(err) {
                console.error("강의 추천 페이지 주간 인기 강의 조회 실패 :", err);
            });

        function renderBestLectures(lectures) {
            const titles = document.querySelectorAll(".recomment-lecture-title");
            let container = null;

            titles.forEach(function(title) {
                if (title.textContent.trim().indexOf("주간 인기/추천 강의") !== -1) {
                    container = title.nextElementSibling;
                }
            });

            if (!container) {
                console.error("강의 추천 페이지 인기 강의 컨테이너 조회 실패");
                return;
            }

            container.innerHTML = "";

            if (!lectures || lectures.length === 0) {
                container.innerHTML = "<p>인기 강의가 없습니다.</p>";
                return;
            }

            lectures.forEach(function(bestLecture) {
                const item = document.createElement("div");
                const detailUrl = "/lecture/detail/" + bestLecture.lectureId;
                const thumbnailSrc = bestLecture.thumbnailImagePath ? "${fileDomain}/\${bestLecture.thumbnailImagePath}" : "/img/png/default_image.png";
                item.classList.add("recent-lecture-item");
                item.innerHTML =
                    "<a href='" + detailUrl + "'>" +
                    "<img src='" + thumbnailSrc + "' alt='강의이미지' class='recent-lecture-thumbnail' " +
                    "onerror=\"this.onerror=null; this.src='/img/png/default_image.png';\">" +
                    "<div class='lecture-info'>" +
                    "<strong class='lecture-title'>" + bestLecture.title + "</strong>" +
                    "<p class='lecture-info-text'>" + bestLecture.teacherNickname + "</p>" +
                    "<p class='lecture-info-text'>₩" + Number(bestLecture.price).toLocaleString() + "</p>" +
                    "<p class='lecture-info-text'>🧸 " + (bestLecture.totalStudents >= 10 ? "10+" : bestLecture.totalStudents) + "</p>" +
                    "</div>" +
                    "</a>";

                container.appendChild(item);
            });
        }
    });
</script>
<script src="<c:url value='/js/page/lecture/recent_reviews.js'/>"></script>
<!-- recent_lecture.js -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const count = 4;
        const lecturePage = document.getElementById("lecturePage");
        const subjectFromJSP = lecturePage ? lecturePage.dataset.subject : "";

        fetch("/api/lectures/recent?subject=" + subjectFromJSP + "&count=" + count, {
            method: "GET",
            headers: { "X-Requested-From": window.location.pathname + window.location.search }
        })
            .then(function(res) {
                if (!res.ok) throw new Error("HTTP " + res.status);
                return res.json();
            })
            .then(function(json) {
                const parsedData = json.data;
                console.log("최근 강의 데이터:", parsedData);
                renderRecentLectures(parsedData);
            })
            .catch(function(err) {
                console.error("최근 강의 데이터 요청 실패:", err);
            });

        // DOM 렌더링 함수
        function renderRecentLectures(lectures) {
            const titles = document.querySelectorAll(".recomment-lecture-title");
            let container = null;

            titles.forEach(function(title) {
                if (title.textContent.trim().indexOf("요새 뜨는 강의") !== -1) {
                    container = title.nextElementSibling;
                }
            });

            if (!container) {
                console.error("최근 강의 조회 중 문제 발생");
                return;
            }

            container.innerHTML = "";

            if (!lectures || lectures.length === 0) {
                container.innerHTML = "<p class='no-lecture'>최근 등록된 강의가 없습니다.</p>";
                return;
            }

            lectures.forEach(function(recentLecture) {
                const item = document.createElement("div");
                const detailUrl = "/lecture/detail/" + recentLecture.lectureId;
                const thumbnailSrc = recentLecture.thumbnailImagePath ? "${fileDomain}/\${recentLecture.thumbnailImagePath}" : "/img/png/default_image.png";
                item.classList.add("recent-lecture-item");



                item.innerHTML =
                    "<a href='" + detailUrl + "'>" +
                    "<img src='" + thumbnailSrc + "' alt='강의이미지' class='recent-lecture-thumbnail' " +
                    "onerror=\"this.onerror=null; this.src='/img/png/default_image.png';\">" +
                    "<div class='lecture-info'>" +
                    "<strong class='lecture-title'>" + recentLecture.title + "</strong>" +
                    "<p class='lecture-info-text'>" + recentLecture.teacherNickname + "</p>" +
                    "<p class='lecture-info-text'>₩" + Number(recentLecture.price).toLocaleString() + "</p>" +
                    "<p class='lecture-info-text'>⭐ " + (recentLecture.averageRate != null ? recentLecture.averageRate : "0.0") +
                    " 🧸 " + (recentLecture.totalStudents >= 10 ? "10+" : recentLecture.totalStudents) + "</p>" +
                    "</div>" +
                    "</a>";

                container.appendChild(item);
            });
        }
    });
</script>
<script src="<c:url value='/js/page/lecture/lecture_recommend.js'/>"></script>

<style>
    /* MAIN + CONTAINER */
    .main-container{
        display: flex;
        flex-direction:row;
        width: 100%;
        height:100%;
        background-color: rgb(255, 255, 255);
    }

    .sidebar-container{
        width: 20%;
        height:100%;
        background-color: rgb(255, 255, 255);
    }

    .main-content-container{
        width: 80%;
        height:100%;
        padding-top:0px;
        padding-left:20px;
        background-color: rgb(255, 255, 255);

    }

    .nav{
        display: flex;
        flex-direction: column;
    }

    .nav-item {
        padding: 15px;
        border-bottom: 1px solid #ccc;
        text-decoration: none;
        color: black;
        display: block;
        transition: all 0.2s ease;
    }

    .nav-item:hover {
        background-color: #f5f5f5;
        color: #0078ff;
        border-bottom: 2px solid #0078ff;
        font-weight: 600;
    }

    /* Lecture Review PART */
    .lecture-comment-box {
        width: 100%;
        background-color: #fff;
        border: 2px solid #e0e0e0; /* 연한 회색 외곽선 */
        border-radius: 16px;
        padding: 20px 24px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.03); /* 은은한 그림자 */
        display: flex;
        flex-direction: column;
        gap: 12px; /* 아이템 간 간격 */
    }

    .lecture-comment-box-item {
        display: flex;
        align-items: flex-start;
        background-color: #fafafa;
        border: 1px solid #e6e6e6;
        border-radius: 12px;
        padding: 14px 18px;
        transition: all 0.25s ease;
    }

    .lecture-comment-box-item:hover {
        background-color: #fefefe;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
        transform: translateY(-2px);
    }

    .lecture-comment-username {
        font-size: 16px;
        font-weight: 600;
        color: #00b894;
        margin-right: 10px;
    }

    .lecture-comment-comment {
        font-size: 16px;
        font-weight: 400;
        color: #333;
        line-height: 1.6;
        word-break: keep-all;
        border-bottom: 1px solid rgba(0,0,0,0.2);
        margin-bottom:5px;
        padding-bottom:5px;
    }

    .lecture-comment-box-item div {
        display: flex;
        flex-direction: column;
    }

    .comment-profile {
        width: 42px;
        height: 42px;
        border-radius: 50%;
        object-fit: cover;
        margin-right: 14px;
        border: 1px solid #ddd;
        background-color: #fff;
    }

    @media (max-width: 768px) {
        .lecture-comment-box {
            padding: 14px;
        }
        .lecture-comment-box-item {
            flex-direction: column;
            padding: 12px;
        }
        .lecture-comment-username {
            font-size: 15px;
            margin-bottom: 4px;
        }
    }

    .recent-comment-box-item{
        border-bottom-color: #333333;
    }


    /* RECENT LECTURE PART */

    .recent-lecture-container {
        display: grid;
        height:auto;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;
        box-sizing: border-box;
        background-color: rgb(255, 255, 255);
    }

    .recent-lecture-item {
        width: 260px;
        height: auto;
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
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

    .recomment-lecture-title{
        font-size:30px;
        font-weight:bold;
        text-align: left;
        margin-top:10px;
        margin-bottom:10px;
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
