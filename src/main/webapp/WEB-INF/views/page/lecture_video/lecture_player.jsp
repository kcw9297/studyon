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
        <button class="sidebar-close">닫기 ✖</button>
        <button class="qna-button">QNA</button>
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
    <button class="sidebar-open fixed" style="display:none;">커리큘럼 보기 ▶</button>
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

    .sidebar-button.fixed {
        position: fixed;   /* 닫혔을 때 오른쪽 위에 고정 */
        top: 20px;
        right: 20px;
        z-index: 9999;
    }

    .sidebar-open.fixed {
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 9999;
        background: #007bff;
        color: white;
        border: none;
        padding: 10px 14px;
        border-radius: 8px;
        cursor: pointer;
        box-shadow: 0 4px 6px rgba(0,0,0,0.3);
    }

    .sidebar-close {
        display: block;
        margin-left: auto;
        background: #ff4d4d;
        color: white;
        border: none;
        padding: 5px 10px;
        border-radius: 6px;
        cursor: pointer;
    }

</style>

<script>


    document.addEventListener("DOMContentLoaded", async () => {
        const urlParams = new URLSearchParams(window.location.search);
        const lectureId = urlParams.get("lectureId");
        const openBtn = document.querySelector(".sidebar-open");
        const closeBtn = document.querySelector(".sidebar-close");

        const sidebarBtn = document.querySelector(".sidebar-button");
        const curriculumSection = document.querySelector(".curriculum-section");

        if (sidebarBtn && curriculumSection) {
            sidebarBtn.addEventListener("click", () => {
                curriculumSection.style.display = "none"; // 🔒 클릭 시 닫힘
            });
        }

        closeBtn.addEventListener("click", () => {
            curriculumSection.style.display = "none";
            openBtn.style.display = "block"; // 오른쪽 상단 버튼 표시
        });

        // ✅ 열기 버튼
        openBtn.addEventListener("click", () => {
            curriculumSection.style.display = "block";
            openBtn.style.display = "none"; // 다시 숨기기
        });






        try {
            // ✅ 1. API 호출
            const response = await fetch("/api/lecture/video/lectureindex/" + lectureId);
            if (!response.ok) throw new Error("강의를 불러올 수 없습니다.");

            const json = await response.json();
            const indexList = json.data || [];
            console.log("🎬 목차 데이터:", indexList);

            // ✅ 2. HTML 요소 참조
            const list = document.querySelector(".curriculum-list");
            const video = document.querySelector("video");
            const source = video.querySelector("source");
            list.innerHTML = "";

            // ✅ 3. 첫 번째 영상 자동 재생
            const firstVideo = indexList.find(v => v.videoFilePath);
            if (firstVideo) {
                source.src = "/upload/" + firstVideo.videoFilePath; // 경로 주의!
                video.load();
                video.play(); // 자동 재생
                console.log("🎬 첫 영상 자동 재생:", firstVideo.videoFileName);
            }

            // ✅ 4. 목차 리스트 렌더링
            indexList.forEach((item, i) => {
                const itemBox = document.createElement("div");
                itemBox.classList.add("curriculum-item-box");

                const titleSpan = document.createElement("span");
                titleSpan.classList.add("curriculum-item");
                titleSpan.textContent = (item.indexNumber || i + 1) + "강. " + item.indexTitle;

                // ✅ 클릭 시 영상 변경
                itemBox.addEventListener("click", () => {
                    if (!item.videoFilePath) {
                        alert("영상이 등록되지 않았습니다.");
                        return;
                    }
                    source.src = "/upload/" + item.videoFilePath;
                    video.load();
                    video.play();
                    console.log("🎬 영상 변경:", item.videoFileName);
                });

                itemBox.appendChild(titleSpan);
                list.appendChild(itemBox);
            });

        } catch (err) {
            console.error("❌ 강의를 불러올 수 없습니다:", err);
        }
    });

</script>
