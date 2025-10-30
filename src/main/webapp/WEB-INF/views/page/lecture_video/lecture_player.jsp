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
        <div class="curriculum-section-button-box">
            <button class="qna-button">QNA</button>
            <button class="sidebar-close">닫기 ✖</button>
        </div>

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

    <%-- QNA SECTION--%>
    <div class="qna-section">
        <div class="qna-section-button-box">
            <button class="curriculum-button">curriculum</button>
            <button class="qna-sidebar-close">닫기 ✖</button>
        </div>

        <div class="qna-title">QNA</div>
        <label class="index-name">index</label>
        <%-- 여기가 이제 질문글 등록하는 곳 --%>
        <%-- 여기가 이제 이전에 학생들이 질문 올렸던 QNA 올리는 곳--%>

        <div class="qna-input-box">
            <form id="qnaForm" onsubmit="return false;" class="qnaForm-style">
                <input id="title" class="qna-title-input-style" placeholder="질문 제목을 입력하세요">
                <textarea id="qna-input" class="qna-textarea" placeholder="이 강의에 대한 질문을 입력하세요."></textarea>
                <button type="button" class="qna-submit">등록</button>
            </form>
        </div>

        <hr class="qna-divider">

        <!-- 하드코딩된 질문/답변 -->
        <div class="qna-list">
            <div class="qna-item">
                <div class="qna-item-text-question">오리엔테이션 영상에서 사용된 프로그램 이름이 궁금합니다.</div>
                <div class="qna-answer">
                    <label class="qna-item-teachername">👩‍🏫 강사:</label>
                    <label class="qna-item-answer">파워포인트 2021 버전입니다.</label>
                </div>
            </div>
            <hr class="qna-divider">
        </div>

    </div>
    <button class="sidebar-open fixed" style="display:none;">커리큘럼 보기 ▶</button>
</div>

<style>

    /* QNA SECTION CSS*/

    /* QNA 질답쪽*/

    .index-name{
        margin-bottom:5px;
    }
    .qna-item-text-question{
        display:flex;
        margin-bottom:5px;
        width:100%;
        height:auto;
        white-space: pre-wrap;   /* ✅ 줄바꿈 + 공백 그대로 표시 */
        word-break: break-word;
    }

    .qnaForm-style{
        margin-bottom:0px;
    }

    .qna-title-input-style {
        width: 100%;
        resize: vertical;
        border-radius: 6px;
        border: 1px solid #444;
        padding: 10px;
        font-size: 14px;
        background: #222;
        color: #fff;
        outline: none;
        transition: border-color 0.2s ease;
        margin-bottom:5px;
    }


    .qna-title {
        font-size: 24px;
        font-weight: bold;
        align-items: center;
        text-align: center;
        margin-bottom: 15px;
    }

    .qna-input-box {
        display: flex;
        flex-direction: column;
        gap: 8px;
        height:auto;
    }

    .qna-textarea {
        width: 100%;
        min-height: 100px;
        resize: vertical;
        border-radius: 6px;
        border: 1px solid #444;
        padding: 10px;
        font-size: 14px;
        background: #222;
        color: #fff;
        outline: none;
        transition: border-color 0.2s ease;
        margin-bottom:5px;
    }

    .qna-textarea:focus {
        border-color: #00bfff;
    }

    .qna-submit {
        align-self: flex-end;
        background: linear-gradient(135deg, #00c2ff, #007bff);
        color: white;
        border: none;
        border-radius: 6px;
        padding: 8px 14px;
        cursor: pointer;
        transition: background 0.25s ease;
        font-weight: 600;
    }

    .qna-submit:hover {
        background: linear-gradient(135deg, #0098d9, #0056b3);
    }

    .qna-list {
    }

    .qna-item {
    }

    .qna-item-text {
        font-size: 15px;
        margin-bottom: 6px;
        line-height: 1.4;
    }

    .qna-item-meta {
        font-size: 12px;
        color: #aaa;
        margin-bottom: 8px;
    }

    .qna-answer {
        background: rgba(255, 255, 255, 0.08);
        border-left: 3px solid #00bfff;
        padding: 8px 10px;
        border-radius: 4px;
        font-size: 14px;
        color: #e0e0e0;
    }

    .qna-divider {
        border: 0;
        height: 1px;
        background: #333;
        margin: 10px 0;
    }


    /* QNA 질답쪽*/

    .qna-title{
        font-size:24px;
        font-weight: bold;
        align-items: center;
        text-align: center;

    }

    .qna-sidebar-close{
        display: flex;
        flex-direction:column;
        margin-left: auto;
        background: #ff4d4d;
        color: white;
        border: none;
        padding: 5px 10px;
        border-radius: 6px;
        cursor: pointer;
        height:auto;

    }

    .qna-section-button-box{
        display:flex;
        flex-direction: row;
        justify-content: flex-end;
        height:29px;

    }

    .curriculum-button{
        width:auto;
        height:auto;
        padding:5px;
    }
    .qna-section{
        flex: 1;  /* 화면의 25% */
        flex-direction: column;
        background: #111;
        color: white;
        overflow-y: auto;
        padding:15px;
        padding-right:30px;
        display:none;
    }

    .curriculum-button {
        background: linear-gradient(135deg, #007bff, #00c2ff);
        color: #fff;
        font-weight: 600;
        border: none;
        border-radius: 8px;
        padding: 8px 14px;
        cursor: pointer;
        transition: all 0.25s ease;
        margin-bottom: 10px;
        align-self: flex-start;
        box-shadow: 0 3px 6px rgba(0,0,0,0.2);
    }

    .curriculum-button:hover {
        background: linear-gradient(135deg, #0056b3, #0098d9);
        transform: translateY(-2px);
    }

    .curriculum-button:active {
        transform: scale(0.96);
    }





    /* QNA SECTION CSS*/



    /* PLAYER SECTION CSS*/

    .curriculum-section-button-box{
        display:flex;
        flex-direction: row;
        justify-content: flex-end;
        height:29px;
    }

    .qna-button {
        display:flex;
        align-items:center;
        background: linear-gradient(135deg, #ff7b00, #ffb800);
        color: #fff;
        font-weight: 600;
        border: none;
        border-radius: 8px;
        padding: 8px 14px;
        cursor: pointer;
        transition: all 0.25s ease;
        margin-right: 10px;
        align-self: flex-start;
        box-shadow: 0 3px 6px rgba(0,0,0,0.2);
        height:29px;
    }

    .qna-button:hover {
        background: linear-gradient(135deg, #e26c00, #ff9e00);
        transform: translateY(-2px);
    }

    .qna-button:active {
        transform: scale(0.96);
    }

    .player-section {
        flex: 4;  /* 화면의 75% */
        display:flex;
        background: black;
        text-align:center;
        align-items:center;
        justify-content: center;

    }

    /* Curriculum Section */

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
        display: flex;
        flex-direction:column;
        margin-left: auto;
        background: #ff4d4d;
        color: white;
        border: none;
        padding: 5px 10px;
        border-radius: 6px;
        cursor: pointer;
        height:auto;
    }

    .curriculum-item-box{

    }

</style>

<script>

    let currentIndexId = 1;
    let currentLectureId = null;


    document.addEventListener("DOMContentLoaded", async () => {
        const urlParams = new URLSearchParams(window.location.search);
        const lectureId = urlParams.get("lectureId");
        const openBtn = document.querySelector(".sidebar-open");
        const closeBtn = document.querySelector(".sidebar-close");
        const sidebarCloseBtn = document.querySelector(".sidebar-close")
        const sidebarBtn = document.querySelector(".sidebar-button");
        const curriculumSection = document.querySelector(".curriculum-section");
        const qnaBtn = document.querySelector(".qna-button");
        const qnaSection = document.querySelector(".qna-section");
        const curriculumBtn = document.querySelector(".curriculum-button");
        const qnaSideCloseBtn = document.querySelector(".qna-sidebar-close");
        const submitBtn = document.querySelector(".qna-submit");

        //전역에 현재 lecture Id를 저장
        currentLectureId = lectureId;
        console.log("현재 강의 ID : " + lectureId);

        qnaBtn.addEventListener("click", () => {
            const isHidden = qnaSection.style.display === "none" || qnaSection.style.display === "";
            qnaSection.style.display = isHidden ? "flex" : "none";
            curriculumSection.style.display = "none";
            // 버튼 텍스트도 토글되게
            qnaBtn.textContent = isHidden ? "QNA" : "QNA";
            loadQNA();
        });

        curriculumBtn.addEventListener("click", () => {
            qnaSection.style.display = "none";
            curriculumSection.style.display = "block";
        })

        sidebarCloseBtn.addEventListener("click", () => {
            qnaSection.style.display = "none";
            curriculumSection.style.display = "none";
        })

        closeBtn.addEventListener("click", () => {
            qnaSection.style.display = "none";
            curriculumSection.style.display = "none";
        })

        qnaSideCloseBtn.addEventListener("click", () => {
            qnaSection.style.display = "none";
            curriculumSection.style.display = "none";
            openBtn.style.display = "block";
        })

        submitBtn.addEventListener("click" , () => {

            registerQuestion(lectureId, currentIndexId);
        })

        if (sidebarBtn && curriculumSection) {
            sidebarBtn.addEventListener("click", () => {
                curriculumSection.style.display = "none"; // 🔒 클릭 시 닫힘
            });
        }

        closeBtn.addEventListener("click", () => {
            curriculumSection.style.display = "none";
            qnaSection.style.display = "none";
            openBtn.style.display = "block"; // 오른쪽 상단 버튼 표시
        });

        // ✅ 열기 버튼
        openBtn.addEventListener("click", () => {
            curriculumSection.style.display = "block";
            openBtn.style.display = "none"; // 다시 숨기기
        });






        try {
            // ✅ 1. API 호출
            const response = await fetch("/api/lectures/video/lectureindex/" + lectureId);
            if (!response.ok) throw new Error("강의를 불러올 수 없습니다.");

            const json = await response.json();
            const indexList = json.data || [];
            console.log("🎬 목차 데이터:", indexList);

            //강의제목설정
            if (indexList.length > 0 && indexList[0].lectureTitle) {
                document.querySelector(".lecture-title").textContent = indexList[0].lectureTitle;
            }

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
                    //클릭한 인덱스 번호를 전역 index에 저장
                    currentIndexId = item.lectureIndexId;
                    console.log("현재 선택된 인덱스 넘버 : " + currentIndexId);

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

        async function loadQNA() {
            console.log("loadQNA 호출");
            const list = document.querySelector(".qna-list");
            const qnaItem = document.querySelector(".qna-item");
            const teacher = document.querySelector(".qna-item-teachername");
            const answer = document.querySelector(".qna-item-answer");
            const questionInput = document.getElementById("qna-input");
            const indexName = document.querySelector(".index-name");

            questionInput.value = "";
            indexName.value = "";
            list.innerHTML="";
            questionInput.textContent = "";

            console.log("List 지우기");

            try{
                console.log("try시작");
                const response = await fetch(
                    "/lecture/question_and_answer?lectureId=" + currentLectureId + "&lectureIndexId=" + currentIndexId
                );
                const jsonData = await response.json();
                const qnaData = jsonData.data || [];
                console.log("qnaDATA : ",qnaData);
                console.log(currentIndexId);

                const currentQna = qnaData.find(q => q.lectureIndexId === currentIndexId);
                if (currentQna) {
                    indexName.textContent = currentQna.indexTitle;
                } else {
                    indexName.textContent = "이 목차에는 QnA가 없습니다.";
                }


                qnaData.forEach(q => {
                    const item = document.createElement("div");
                    item.classList.add("qna-item");

                    // 질문 박스
                    const questionBox = document.createElement("div");
                    questionBox.classList.add("qna-item-question-box");

                    const title = document.createElement("div");
                    title.classList.add("qna-item-title");
                    title.textContent = "Q : " + q.title;

                    const content = document.createElement("div");
                    content.classList.add("qna-item-text-question");
                    content.textContent = q.content;

                    <%--const meta = document.createElement("div");--%>
                    <%--meta.classList.add("qna-item-meta");--%>
                    <%--meta.textContent = `작성자: ${q.memberName || "익명"} • 상태: ${q.isSolved ? "✅ 답변 완료" : "⌛ 대기 중"}`;--%>

                    questionBox.appendChild(title);
                    questionBox.appendChild(content);
                    // questionBox.appendChild(meta);

                    // 답변이 존재하는 경우
                    if (q.answerContent) {
                        const answerBox = document.createElement("div");
                        answerBox.classList.add("qna-answer");
                        answerBox.innerHTML = `
                    <label class="qna-item-teachername">👩‍🏫 ${q.teacherName || "강사"} :</label>
                    <label class="qna-item-answer">${q.answerContent}</label>
                `;
                        questionBox.appendChild(answerBox);
                    }

                    // 구분선
                    const divider = document.createElement("hr");
                    divider.classList.add("qna-divider");

                    list.appendChild(questionBox);
                    list.appendChild(divider);
                });
                console.log("qnadata쓰기완료");

                console.log("✅ QNA 데이터 렌더링 완료");


            }catch{

            }


            console.log("데이터 로딩 완료");
        }

        async function registerQuestion(lectureId,indexId){
            const textarea = document.getElementById("qna-input");
            const titleInput = document.getElementById("title");
            const titleContent = titleInput.value.trim();

            const question = textarea.value.trim();

            if (question === "" || titleContent === "") {
                alert("질문 제목과 내용을 모두 입력해주세요.");
                return;
            }

            try{
                const form = new FormData();
                form.append("lectureId", lectureId);
                form.append("lectureIndexId", indexId);
                form.append("content", question);
                form.append("title", titleContent);
                console.log("1");

                const res = await fetch("/lecture/question/register", {
                    headers: { 'X-Requested-From': window.location.pathname + window.location.search },
                    method: "POST",
                    body: form
                });
                console.log("서버 응답:" + res);

                textarea.value = "";
                titleInput.value = "";
                await loadQNA();
            }catch(error){
                console.error("질문 등록 실패");
            }
        }


        await loadQNA();


        //DOM RENDER END
    });

</script>
