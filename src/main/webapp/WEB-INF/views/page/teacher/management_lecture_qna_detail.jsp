<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

<div class="qna-detail-container">

    <h2 class="qna-detail-title">📘 내 답변 상세보기</h2>

    <button class="video-move-button">해당 강의로 이동 🎥</button>

    <!-- ✅ 질문 카드 -->
    <div class="qna-detail-card">
        <div class="qna-question-meta">
            <span class="student-name"></span>
            <span class="qna-date"></span>
        </div>

        <div class="qna-question-title">
        </div>

        <div class="qna-question-content">
        </div>
    </div>

    <!-- ✅ 답변 카드 -->
    <div class="qna-answer-card">
        <div class="answer-meta">
            <span class="teacher-name"></span>
            <span class="answer-date"></span>
        </div>

        <div class="answer-content">
        </div>

        <div class="answer-manage-box">
            <button class="answer-edit">✏️ 수정</button>
            <button class="answer-delete">🗑️ 삭제</button>
        </div>
    </div>

</div>

<style>
    /* ====== 전체 ====== */
    .qna-detail-container {
        width: 100%;
        min-height: 750px;
        background: #f8f9fa;
        border-radius: 12px;
        padding: 50px 80px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        gap: 20px;
        font-family: 'Noto Sans KR', sans-serif;
    }

    /* ===== 제목 ===== */
    .qna-detail-title {
        font-size: 26px;
        font-weight: 700;
        color: #222;
    }

    /* ===== 공통 카드 ===== */
    .qna-detail-card, .qna-answer-card {
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 10px;
        box-shadow: 0 3px 6px rgba(0,0,0,0.06);
        padding: 25px 30px;
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    /* ===== 질문 ===== */
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

    /* ===== 답변 ===== */
    .qna-answer-card {
        border-left: 5px solid #2ecc71;
    }

    .answer-meta {
        display: flex;
        justify-content: space-between;
        font-size: 14px;
        color: #666;
    }

    .answer-content {
        background: #f9fff9;
        border-radius: 8px;
        border: 1px solid #cdeccd;
        padding: 15px;
        font-size: 15px;
        color: #2c3e50;
        line-height: 1.6;
        white-space: pre-line;
        height:auto;
    }

    /* ===== 수정/삭제 버튼 ===== */
    .answer-manage-box {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        margin-top: 10px;
    }

    .answer-edit {
        padding: 8px 16px;
        border-radius: 8px;
        background: #3498db;
        color: #fff;
        border: none;
        font-weight: 600;
        cursor: pointer;
        transition: 0.2s ease;
    }

    .answer-edit:hover {
        background: #217dbb;
    }

    .answer-delete {
        padding: 8px 16px;
        border-radius: 8px;
        background: #e74c3c;
        color: #fff;
        border: none;
        font-weight: 600;
        cursor: pointer;
        transition: 0.2s ease;
    }

    .answer-delete:hover {
        background: #c0392b;
    }

    /* ===== 이동 버튼 ===== */
    .video-move-button {
        width: 230px;
        height: 45px;
        align-self: flex-start;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
        font-size: 16px;
        font-weight: 600;
        border: none;
        border-radius: 12px;
        color: #fff;
        cursor: pointer;
        background: linear-gradient(135deg, #27ae60, #2ecc71);
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
</style>

<script>
    document.addEventListener("DOMContentLoaded", async function() {
        const params = new URLSearchParams(window.location.search);
        const questionId = params.get("id");
        const answerEditBtn = document.querySelector(".answer-edit")
        const deleteBtn = document.querySelector(".answer-delete")

        deleteBtn.addEventListener("click",async () =>{
            await fetch("/api/teachers/management/qna/deleteQuestion/" + questionId, {
                method: "DELETE"
            });
            alert("강의가 삭제되었습니다.");
            window.location.href="/teacher/management/qna";
        })
        console.log("삭제이후");

        answerEditBtn.addEventListener("click", async() =>{
            window.location.href="/teacher/management/qna/updateQna";
        })

        if (!questionId) {
            alert("잘못된 접근입니다.");
            return;
        }

        try {
            const res = await fetch("/api/teachers/management/qna/detail/" + questionId);
            const json = await res.json();
            const data = json.data;
            console.log(data);

            document.querySelector(".student-name").textContent = "질문자: " + data.studentName;
            document.querySelector(".qna-date").textContent = new Date(data.createdAt).toLocaleDateString();
            document.querySelector(".qna-question-title").textContent = "Q. " + data.title;
            document.querySelector(".qna-question-content").textContent = data.content;
            document.querySelector(".teacher-name").textContent = "👩‍🏫 작성 강사: " + data.teacherName;
            document.querySelector(".answer-date").textContent =
                data.answeredAt ? new Date(data.answeredAt).toLocaleDateString() : "-";
            document.querySelector(".answer-content").textContent =
                data.answerContent || "아직 답변이 작성되지 않았습니다.";

            const moveBtn = document.querySelector(".video-move-button");
            moveBtn.addEventListener("click", function() {
                window.location.href = "/lecture/player?lectureId=" + data.lectureId + "&index=" + data.indexTitle;
            });

        } catch (err) {
            console.error("🚨 상세 데이터 불러오기 실패:", err);
            alert("QnA 정보를 불러올 수 없습니다.");
        }
    });
</script>
