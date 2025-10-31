<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

<div class="qna-detail-container">

    <h2 class="qna-detail-title">💬 학생 질문</h2>

    <button class="video-move-button">해당 비디오로 이동🏃‍➡️</button>

    <!-- ✅ 질문 카드 -->
    <div class="qna-detail-card">
        <div class="qna-question-meta">
            <span class="student-name">학생: 홍길동</span>
            <span class="qna-date">2025-10-30</span>
        </div>

        <div class="qna-question-title">
        </div>

        <div class="qna-question-content">
        </div>
    </div>

    <!-- ✅ 답변 입력 영역 -->
    <form id="answerForm">
        <div class="qna-answer-box">
            <label class="answer-label">👩‍🏫 강사 답변</label>
            <textarea class="answer-textarea" name="content" placeholder="학생의 질문에 대한 답변을 입력하세요." required></textarea>

            <input type="hidden" name="lectureQuestionId" id="lectureQuestionId">

            <div class="answer-button-box">
                <button class="answer-cancel" type="button">취소</button>
                <button class="answer-edit" type="submit">수정</button>
            </div>
        </div>
    </form>
</div>

<style>


    /* ====== 전체 컨테이너 ====== */
    .qna-detail-container {
        width: 100%;
        min-height: 750px;
        background: #f8f9fa;
        border-radius: 12px;
        padding: 50px 80px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        gap: 15px;
        font-family: 'Noto Sans KR', sans-serif;
    }

    /* ====== 제목 ====== */
    .qna-detail-title {
        font-size: 26px;
        font-weight: 700;
        color: #222;
    }

    /* ====== 질문 카드 ====== */
    .qna-detail-card {
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 10px;
        box-shadow: 0 3px 6px rgba(0,0,0,0.06);
        padding: 25px 30px;
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    .qna-question-meta {
        display: flex;
        justify-content: space-between;
        font-size: 14px;
        color: #888;
    }

    .qna-question-title {
        font-size: 18px;
        font-weight: 600;
        color: #333;
    }

    .qna-question-content {
        font-size: 15px;
        line-height: 1.6;
        color: #444;
        background: #fdfdfd;
        padding: 15px;
        border-radius: 8px;
        border: 1px solid #eee;
    }

    /* ====== 답변 입력 영역 ====== */
    .qna-answer-box {
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 10px;
        padding: 25px 30px;
        display: flex;
        flex-direction: column;
        gap: 15px;
        box-shadow: 0 3px 6px rgba(0,0,0,0.05);
    }

    .answer-label {
        font-weight: 600;
        font-size: 16px;
        color: #2c3e50;
    }

    .answer-textarea {
        width: 100%;
        min-height: 160px;
        resize: vertical;
        border-radius: 8px;
        border: 1px solid #ccc;
        padding: 12px;
        font-size: 15px;
        line-height: 1.5;
        box-sizing: border-box;
        transition: all 0.2s ease;
    }

    .answer-textarea:focus {
        border-color: #27ae60;
        box-shadow: 0 0 5px rgba(39, 174, 96, 0.25);
        outline: none;
    }

    /* ====== 버튼 ====== */
    .answer-button-box {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        margin-top: 5px;
    }

    .answer-submit {
        padding: 10px 20px;
        font-size: 15px;
        font-weight: 600;
        color: white;
        background: linear-gradient(135deg, #27ae60, #2ecc71);
        border: none;
        border-radius: 8px;
        cursor: pointer;
        box-shadow: 0 3px 6px rgba(46, 204, 113, 0.3);
        transition: all 0.25s ease;
    }

    .answer-submit:hover {
        background: linear-gradient(135deg, #219150, #27ae60);
        transform: translateY(-2px);
    }

    .answer-cancel {
        padding: 10px 18px;
        font-size: 15px;
        font-weight: 600;
        color: #666;
        background: #eee;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: all 0.2s ease;
    }

    .answer-cancel:hover {
        background: #ddd;
    }

    .video-move-button {
        width:15%;
        height:15px;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
        padding: 14px 26px;
        font-size: 17px;
        font-weight: 600;
        border: none;
        border-radius: 12px;
        color: #fff;
        cursor: pointer;
        background: linear-gradient(135deg, #27ae60, #2ecc71); /* 초록 계열 그라데이션 */
        box-shadow: 0 4px 10px rgba(46, 204, 113, 0.3);
        transition: all 0.25s ease;
    }

    .video-move-button:hover {
        transform: translateY(-2px);
        background: linear-gradient(135deg, #219150, #27ae60);
        box-shadow: 0 6px 14px rgba(39, 174, 96, 0.4);
    }

    .video-move-button:active {
        transform: scale(0.98);
    }

    .video-move-button i {
        font-size: 18px;
    }

    /* 비활성화 상태 */
    .video-move-button.disabled {
        background: #ccc;
        cursor: not-allowed;
        box-shadow: none;
    }

</style>


<script>
    document.addEventListener("DOMContentLoaded", async function() {
        // ✅ URL에서 questionId 파라미터 추출
        const params = new URLSearchParams(window.location.search);
        const lectureQuestionId = params.get("id"); // ✅ OK
        const cancelBtn = document.querySelector(".answer-cancel");
        const editBtn = document.querySelector(".answer-edit");
        console.log("📘 lectureQuestionId =", lectureQuestionId);
        const questionId = lectureQuestionId;


        try{
            console.log("try문 questionid = " , questionId);
            const res = await fetch("/api/teachers/management/qna/detail/" + questionId);
            const json = await res.json();
            const data = json.data;
            console.log(data)

            document.querySelector(".student-name").textContent = "질문자: " + data.studentName;
            document.querySelector(".qna-date").textContent = new Date(data.createdAt).toLocaleDateString();
            document.querySelector(".qna-question-title").textContent = "Q. " + data.title;
            document.querySelector(".qna-question-content").textContent = data.content;
            document.querySelector(".teacher-name").textContent = "👩‍🏫 작성 강사: " + data.teacherName;
            document.querySelector(".answer-date").textContent =
                data.answeredAt ? new Date(data.answeredAt).toLocaleDateString() : "-";
            document.querySelector(".answer-textarea").textContent =
                data.answerContent || "아직 답변이 작성되지 않았습니다.";
        }catch{

        }




        cancelBtn.addEventListener("click",  function () {
            if (confirm("답변 작성을 취소하시겠습니까?")) {
                window.location.href = "/teacher/management/qna/detail?id=" + lectureQuestionId;
            }
        });

        editBtn.addEventListener("click", async (event) => {
            console.log("edit 이후 lectureQuestionId = ", questionId);
            event.preventDefault();

            const Param = new URLSearchParams(window.location.search);
            const realParam = Param.get("lectureQuestionId");
            console.log("Param값 = ", realParam);

            const content = document.querySelector(".answer-textarea").value.trim();

            const formData = new FormData();
            formData.append("lectureQuestionId", lectureQuestionId);
            formData.append("content", content);

            try {
                const res = await fetch("/api/teachers/management/qna/updateQuestion", {
                    method: "PUT",
                    body: formData
                });
            } catch (err) {
                console.error("🚨 서버 통신 실패:", err);
                alert("서버와의 통신 중 오류가 발생했습니다.");
            }

            window.location.href = "/teacher/management/qna/detail?id=" + lectureQuestionId;
        });








        //DOMContent End
    })

</script>
