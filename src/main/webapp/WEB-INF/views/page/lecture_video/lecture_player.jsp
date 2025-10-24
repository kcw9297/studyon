<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture_video/lecture_player.css'/>">

<div class="player-wrapper">
    <div class="player-section">
        <video width="100%" height="auto" controls>
            <source src="" type="video/mp4">
        </video>
    </div>
    <div class="curriculum-section">
      <div class="curriculum-title">커리큘럼</div>
      <div class="lecture-title">
        생성형 AI 특성과 PPT 제작
      </div>
      <div class="lecture-progress">
        수강중 1/3<br>
        33%<br>
      </div>
      <div class="progress-bar">
        <div class="progress-fill" id="progress-fill" style="width: 33%;"></div>
      </div>

      <div class="curriculum-list">
        <%--
          <div class="curriculum-item-box">
          <span class="curriculum-item">1.오리엔테이션 및 강의 소개</span><br>
          <label class="curriculum-time">10:00</label>
        </div>
        --%>
      </div>
    </div>
</div>

<style>
    .player-section {
        flex: 4;  /* 화면의 75% */
        display:flex;
        background: black;
        text-align:center;
        align-items:center;
        justify-content: center;

    }
    .curriculum-item {
        color: white;
        font-size: 16px;
        margin: 8px 0;
    }

</style>

<script>
    async function loadVideos() {
        try {
            //const response = await fetch("/api/lecture/video/list?lectureId=63");
            const urlParams = new URLSearchParams(window.location.search);
            const lectureId = urlParams.get("lectureId");

            const response = await fetch("/api/lecture/video/list?lectureId=" +lectureId);
            if (!response.ok) throw new Error("강의를 불러올 수 없습니다");
            const lectures = await response.json();
            console.log("강의데이터", lectures);

            const list = document.querySelector(".curriculum-list");
            const video = document.querySelector("video");
            const source = video.querySelector("source");
            list.innerHTML = "";


            if (lectures.length > 0) {
                source.src = lectures[0].videoUrl;
                video.load(); // <video> 로드
                console.log("🎬 첫 영상 자동 재생:", lectures[0].videoUrl);
            }

            lectures.forEach(lecture => {
                const itemBox = document.createElement("div");
                itemBox.classList.add("curriculum-item-box");

                const titleSpan = document.createElement("span");
                titleSpan.classList.add("curriculum-item");
                titleSpan.textContent = lecture.seq +"강" + lecture.title;
                console.log(lecture.title);
                // ✅ 영상 시간 (초 → mm:ss 형식으로 변환)
                const timeLabel = document.createElement("label");
                timeLabel.classList.add("curriculum-time");
                const minutes = Math.floor(lecture.duration / 60);
                const seconds = String(lecture.duration % 60).padStart(2, "0");
                timeLabel.textContent = minutes+":" + seconds;

                itemBox.appendChild(titleSpan);
                itemBox.appendChild(document.createElement("br"));
                itemBox.appendChild(timeLabel);

                itemBox.addEventListener("click", () => {
                    const video = document.querySelector("video source");
                    video.src = lecture.videoUrl;
                    video.parentElement.load(); // <video> 다시 로드
                    console.log("🎬 영상 변경: " + lecture.videoUrl);
                });

                list.appendChild(itemBox);
            });
        } catch (err) {
            console.error("❌ 강의를 불러올 수 없습니다:", err);
        }
    }

    document.addEventListener("DOMContentLoaded", loadVideos);
</script>
