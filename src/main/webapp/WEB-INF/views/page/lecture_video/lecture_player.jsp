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

    document.addEventListener("DOMContentLoaded", async() =>{
        const urlParams = new URLSearchParams(window.location.search);
        const lectureId = urlParams.get("lectureId");
        console.log(lectureId);

        const response = await fetch("/api/teachers/management/lectureinfo/" + lectureId);
        console.log(response);
        const json = await response.json();
        const lecture = json.data;
        console.log(lecture);

    });

    async function loadVideos() {
        try {
            const urlParams = new URLSearchParams(window.location.search);
            const lectureId = urlParams.get("lectureId");

            // ✅ 1. index API 호출
            const response = await fetch("/api/teachers/management/lectureindex/" + lectureId);
            if (!response.ok) throw new Error("강의를 불러올 수 없습니다1");

            const jsondata = await response.json();
            const indexList = jsondata.data || [];
            console.log("🎬 목차 데이터", indexList);

            const list = document.querySelector(".curriculum-list");
            const video = document.querySelector("video");
            const source = video.querySelector("source");
            list.innerHTML = "";

            // ✅ 2. 첫 번째 영상 자동 재생
            const firstVideo = indexList.find(v => v.videoFileName);
            if (firstVideo) {
                source.src = "/upload/lecture_video/" + firstVideo.videoFileName;
                video.load();
                console.log("🎬 첫 영상 자동 재생:", firstVideo.videoFileName);
            }

            // ✅ 3. 목차 리스트 렌더링
            indexList.forEach((item, i) => {
                const itemBox = document.createElement("div");
                itemBox.classList.add("curriculum-item-box");

                const titleSpan = document.createElement("span");
                titleSpan.classList.add("curriculum-item");
                titleSpan.textContent = (item.indexNumber || i + 1) + "강. " + item.indexTitle;

                // 파일 이름 표시
                const timeLabel = document.createElement("label");
                timeLabel.classList.add("curriculum-time");
                timeLabel.textContent = item.videoFileName ? item.videoFileName : "영상 없음";

                itemBox.appendChild(titleSpan);
                itemBox.appendChild(document.createElement("br"));
                itemBox.appendChild(timeLabel);

                // ✅ 클릭 시 해당 영상 재생
                itemBox.addEventListener("click", () => {
                    if (!item.videoFileName) {
                        alert("영상이 등록되지 않았습니다.");
                        return;
                    }
                    source.src = "/upload/lecture_video/" + item.videoFileName;
                    video.load();
                    console.log("🎬 영상 변경:", item.videoFileName);
                });

                list.appendChild(itemBox);
            });
        } catch (err) {
            console.error("❌ 강의를 불러올 수 없습니다2:", err);
        }
    }

   // document.addEventListener("DOMContentLoaded", loadVideos);
</script>