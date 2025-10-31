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

                item.classList.add("recent-lecture-item");
                item.innerHTML =
                    "<a href='" + detailUrl + "'>" +
                    "<img src='/img/png/sample1.png' alt='강의이미지' class='recent-lecture-thumbnail'>" +
                    "<div class='lecture-info'>" +
                    "<p class='lecture-title'>" + bestLecture.title + "</p>" +
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
                console.log("✅ 최근 강의 데이터:", parsedData);
                renderRecentLectures(parsedData);
            })
            .catch(function(err) {
                console.error("최근 강의 데이터 요청 실패:", err);
            });

        // ✅ DOM 렌더링 함수
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
                item.classList.add("recent-lecture-item");

                item.innerHTML =
                    "<a href='" + detailUrl + "'>" +
                    "<img src='/img/png/sample1.png' alt='강의이미지' class='recent-lecture-thumbnail'>" +
                    "<div class='lecture-info'>" +
                    "<p class='lecture-title'>" + recentLecture.title + "</p>" +
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