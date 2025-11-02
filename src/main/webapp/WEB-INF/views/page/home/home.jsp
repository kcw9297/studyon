<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="home-nav">
    <a href="<c:url value='/lecture/recommend/MATH'/>">수학</a>
    <a href="<c:url value='/lecture/recommend/ENGLISH'/>">영어</a>
    <a href="<c:url value='/lecture/recommend/KOREAN'/>">국어</a>
    <a href="<c:url value='/lecture/recommend/SCIENCE'/>">과학탐구</a>
    <a href="<c:url value='/lecture/recommend/SOCIAL'/>">사회탐구</a>
    <a href="<c:url value='/teacher/find/MATH'/>">강사리스트</a>
</div>

<%-- 홈 베너 모달 --%>
<jsp:include page="/WEB-INF/views/page/home/home_banner.jsp" />

<%-- 공지사항 팝업 모달 --%>
<jsp:include page="/WEB-INF/views/page/home/home_notice_popup_modal.jsp" />


<!-- 최근 등록된 강의 -->
<label class="lecture-section-title">최근 등록된 강의</label>
<div class="recent-lecture-container" id="recentLectureContainer">
    <!-- JS에서 렌더링될 영역 -->
</div>

<!-- 최근 인기 강의 -->
    <label class="lecture-section-title">최근 인기 강의</label>
<div class="recent-lecture-container" id="popularLectureContainer">
    <!-- JS에서 렌더링될 영역 -->
</div>

<style>
    .home-nav {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        gap: 20px;
        padding: 10px 0;
        position: sticky;
        top: 0;
        z-index: 100;
        margin-left:15px;
    }

    .home-nav a {
        position: relative;
        color: #333;
        font-size: 18px;
        font-weight: 600;
        text-decoration: none;
        transition: color 0.3s ease;
    }

    .home-nav a::after {
        content: '';
        position: absolute;
        width: 0%;
        height: 2px;
        bottom: -5px;
        left: 50%;
        background-color: #007bff;
        transition: all 0.3s ease;
        transform: translateX(-50%);
    }

    .home-nav a:hover {
        color: #007bff;
    }

    .home-nav a:hover::after {
        width: 100%;
    }

    @media (max-width: 768px) {
        .home-nav {
            flex-wrap: nowrap;
            overflow-x: auto;
            gap: 25px;
            padding: 15px;
        }

        .home-nav a {
            font-size: 16px;
            flex-shrink: 0;
        }
    }

    .recent-lecture-container {
        display: grid;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;
        height: auto;
        box-sizing: border-box;
        background-color: white;
        margin-bottom:10px;
    }

    .recent-lecture-item {
        width: 260px;
        height: auto;
        box-shadow: inset 0 0 0 2px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        box-sizing: border-box;
    }

    .recent-lecture-item:hover {
        transform: translateY(-5px);
        cursor: pointer;
    }

    .lecture-section-title {
        font-size: 24px;
        font-weight: bold;
        margin-top:10px;
        margin-bottom:10px;
        display: block;
    }
    .label-title-box{
        margin-top:10px;
    }

    .lecture-info{
        margin-left:5px;
        margin-bottom:5px;
    }
    .recent-lecture-thumbnail {
        width: 100%;
        height: 180px;
        object-fit: cover;
        border-bottom: 1px solid #ddd;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }
    /*배너*/
    .main-banner-container {
        position: relative;
        width: 100%;
        height: 450px; /* 필요에 따라 조절 */
        overflow: hidden;
        border-radius: 10px;
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

    .fade {
        animation: fadeEffect 10s infinite;
    }
</style>

<%-- Local Script --%>
<script>

    document.addEventListener("DOMContentLoaded", () => {
        const count = 5;

        const params = new URLSearchParams();
        // 변수 바인딩 추가
        params.append("count", count.toString());


        // ✅ [1] 최근 등록된 강의 조회
        fetch(`/api/home/recent?count=\${count}`, {
            method: "GET",
        })
            .then(res => res.json())
            .then(json => {
                // ⚠️ 문자열을 실제 배열로 변환
                const parsedData = json.data;
                renderRecentLectures(parsedData);
            })
            .catch(err => console.error("홈화면 최근 강의 조회 실패 : ", err));

        // ✅ [2] 인기 강의 조회
        fetch(`/api/home/best?count=\${count}`, {
            method: "GET",
        })
            .then(res => res.json())
            .then(json => {
                // ⚠️ 문자열을 실제 배열로 변환
                const parsedData = json.data;
                renderBestLectures(parsedData);
            })
            .then(e => console.log(e))

            .catch(err => console.error("홈화면 인기 강의 조회 실패:", err));

        /* -- 렌더 함수 -- */

        function renderBestLectures(lectures) {
            // ✅ 단일 요소 선택
            const container = document.querySelector("#popularLectureContainer");

            if (!container) {
                console.error("홈화면 인기 강의 컨테이너 조회 실패");
                return;
            }

            container.innerHTML = "";

            if (!lectures || lectures.length === 0) {
                container.innerHTML = `<p>최근 인기 강의가 없습니다.</p>`;
                return;
            }
            console.log("best lecture =" , lectures);

            lectures.forEach(bestLecture => {
                const item = document.createElement("div");

                const detailUrl = `/lecture/detail/\${bestLecture.lectureId}`;

                // ✅ 썸네일 경로 처리
                const thumbnailSrc = bestLecture.thumbnailImagePath
                    ? "${fileDomain}/" + bestLecture.thumbnailImagePath
                    : "/img/png/default_image.png";

                item.classList.add("recent-lecture-item");
                const formattedRate = bestLecture.averageRate?.toFixed(1) ?? "0.0";
                item.innerHTML = `
            <a href="\${detailUrl}">
                <img src="\${thumbnailSrc}" alt="강의이미지" class="recent-lecture-thumbnail"
                 onerror="this.onerror=null; this.src='/img/png/default_image.png';">
                <div class="lecture-info">
                    <p class="lecture-title">\${bestLecture.title}</p>
                    <p class="lecture-info-text">\${bestLecture.teacherNickname}</p>
                    <p class="lecture-info-text">₩\${Number(bestLecture.price).toLocaleString()}</p>
                    <p class="lecture-info-text">
                         ⭐\${formattedRate}
                         🧸\${bestLecture.totalStudents >= 10 ? "10+" : bestLecture.totalStudents}
                    </p>
                </div>
            </a>
            `;
                container.appendChild(item);
            });
        }
        function renderRecentLectures(lectures) {
            // ✅ 단일 요소 선택
            const container = document.querySelector("#recentLectureContainer");

            if (!container) {
                console.error("홈화면 최신 강의 컨테이너 조회 실패");
                return;
            }

            container.innerHTML = "";

            if (!lectures || lectures.length === 0) {
                container.innerHTML = `<p>최신 강의가 없습니다.</p>`;
                return;
            }

            console.log("recent lecture =" , lectures);



            lectures.forEach(recentLecture => {
                const item = document.createElement("div");

                const detailUrl = `/lecture/detail/\${recentLecture.lectureId}`;
                const thumbnailSrc = recentLecture.thumbnailImagePath
                    ? "${fileDomain}/" + recentLecture.thumbnailImagePath
                    : "/img/png/default_image.png";


                item.classList.add("recent-lecture-item");
                const formattedRate = recentLecture.averageRate?.toFixed(1) ?? "0.0";

                item.innerHTML = `
                <a href="\${detailUrl}">
                    <img src="\${thumbnailSrc}" alt="강의이미지" class="recent-lecture-thumbnail">
                        <div class="lecture-info">
                            <p class="lecture-title">\${recentLecture.title}</p>
                            <p class="lecture-info-text">\${recentLecture.teacherNickname}</p>
                            <p class="lecture-info-text">₩\${Number(recentLecture.price).toLocaleString()}</p>
                            <p class="lecture-info-text">
                                 ⭐\${formattedRate}
                                 🧸\${recentLecture.totalStudents >= 10 ? "10+" : recentLecture.totalStudents}
                            </p>
                        </div>
                    </a>
            `;
                container.appendChild(item);
            });
        }
    });

</script>




