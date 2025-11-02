<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

<div class="unregistered-lecture-label">
  미등록 강의
</div>
<div class ="unregistered-lecture-container">
    <div class="unregistered-lecture-item">
        <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
        <div class="unregistered-lecture-info">
            <p class="unregistered-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
        </div>
    </div>
</div>

<div class="pending-lecture-label">
  승인 대기중인 강의
</div>
<div class ="pending-lecture-container">
  <div class="pending-lecture-item">
    <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
    <div class="pending-lecture-info">
      <p class="pending-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
    </div>
  </div>
</div>

<div class="resisted-lecture-label">
    등록된 강의
</div>
<div class ="registered-lecture-container">
    <div class="registered-lecture-item">
        <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
        <div class="registered-lecture-info">
            <p class="registered-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
        </div>
    </div>
</div>

<style>
    .TeacherManagement nav-item{
        margin-right:40px;
        font-size: 18px;
        color: #333;
        text-decoration: none;
    }

    .resisted-lecture-label{
        font-size:24px;
        font-weight: bold;
    }

    .pending-lecture-container,
    .registered-lecture-container,
    .unregistered-lecture-container{
        width: 260px;            /* 고정 폭 (5등분용) */
        height:auto;           /* 고정 높이 */
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 10px;
        display: flex;
        flex-direction: row;  /* ✅ 세로로 쌓기 */
        justify-content: flex-start; /* 위쪽부터 쌓기 */
        box-sizing: border-box;
        gap:10px;
    }

    .lecture-item:hover {
        background-color: #bbb;
        transform: translateY(-5px);
        cursor: pointer;
    }
</style>
<script>
    document.addEventListener("DOMContentLoaded", async () => {
        try {
            const res = await fetch("/api/teachers/management/lecturelist");
            const result = await res.json();
            console.log("서버응답:", result);

            const data = typeof result.data === "string" ? JSON.parse(result.data) : result.data;

            // 공통 렌더링 함수
            const renderLectures = (lectures, containerSelector, itemClassPrefix) => {
                const container = document.querySelector(containerSelector);
                container.innerHTML = ""; // 초기화

                if (!lectures || lectures.length === 0) {
                    const empty = document.createElement("p");
                    empty.textContent = "등록된 강의가 없습니다.";
                    empty.style.color = "#666";
                    empty.style.padding = "10px";
                    container.appendChild(empty);
                    return;
                }


                lectures.forEach(lecture => {
                    console.log("lecture 확인:", lecture);

                    const lectureId = lecture.lectureId; //
                    const item = document.createElement("div");
                    item.classList.add(itemClassPrefix + "-lecture-item");

                    // const img = document.createElement("img");
                    // img.src = "/img/png/thumbnail.png";
                    // img.classList.add("lecture-thumbnail");

                    const img = document.createElement("img");

                    img.src = "${fileDomain}/" + lecture.thumbnailImagePath;
                    img.classList.add("lecture-thumbnail");


                    // 클릭 시 이동
                    img.addEventListener("click", function() {
                        if (lectureId) {
                            console.log("이동할 lectureId:", lectureId);
                            window.location.href = "/teacher/management/lectureinfo/" + lectureId;
                        } else {
                            console.warn("⚠️ lectureId가 없습니다:", lecture);
                        }
                    });


                    // 정보 div
                    const infoDiv = document.createElement("div");
                    infoDiv.classList.add(`${itemClassPrefix}-lecture-info`);

                    // 제목
                    const title = document.createElement("p");
                    title.classList.add(`${itemClassPrefix}-lecture-title`);
                    title.textContent = lecture.title;


                    // 구조 결합
                    infoDiv.appendChild(title);
                    item.appendChild(img);
                    item.appendChild(infoDiv);
                    container.appendChild(item);
                });
            };

            // 각각의 상태별 렌더링
            renderLectures(data.unregistered, ".unregistered-lecture-container", "unregistered");
            renderLectures(data.pending, ".pending-lecture-container", "pending");
            renderLectures(data.registered, ".registered-lecture-container", "registered");

        } catch (error) {
            console.error("데이터 로드 실패:", error);
        }
    });
</script>

<style>
    /* ✅ 전체 컨테이너 레이아웃 */
    .unregistered-lecture-container,
    .pending-lecture-container,
    .registered-lecture-container {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
        justify-content: flex-start;
        background-color: transparent;
        border: none;
        margin-bottom: 40px;
    }

    /* ✅ 개별 강의 카드 */
    .unregistered-lecture-item,
    .pending-lecture-item,
    .registered-lecture-item {
        width: 320px;
        border: 1.5px solid #ddd;
        border-radius: 10px;
        overflow: hidden;
        background-color: #fff;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        transition: all 0.2s ease-in-out;
    }

    .unregistered-lecture-item:hover,
    .pending-lecture-item:hover,
    .registered-lecture-item:hover {
        transform: translateY(-6px);
        box-shadow: 0 6px 12px rgba(0,0,0,0.15);
    }

    /* ✅ 썸네일 이미지 */
    .lecture-thumbnail {
        width: 100%;
        height: 180px;
        object-fit: cover;
        border-bottom: 1px solid #eee;
        cursor: pointer;
    }

    /* ✅ 강의 제목 */
    .unregistered-lecture-title,
    .pending-lecture-title,
    .registered-lecture-title {
        font-size: 17px;
        font-weight: 600;
        color: #333;
        margin: 8px 10px 10px 10px;
        line-height: 1.3;
        word-break: keep-all;
    }

    /* ✅ 섹션 제목 라벨 */
    .unregistered-lecture-label,
    .pending-lecture-label,
    .resisted-lecture-label {
        font-size: 22px;
        font-weight: 700;
        color: #222;
        margin: 25px 0 10px 0;
        border-left: 6px solid #5cb85c;
        padding-left: 10px;
    }

    /* ✅ 기타 */
    .TeacherManagement nav-item {
        margin-right: 40px;
        font-size: 18px;
        color: #333;
        text-decoration: none;
    }
    .-lecture-info{
        margin-left:5px;
        margin-bottom:5px;
        font-weight: bold;
    }
</style>