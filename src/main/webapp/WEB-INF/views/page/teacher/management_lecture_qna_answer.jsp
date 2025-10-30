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
            Q. 3강에서 예시 데이터는 어디서 확인할 수 있나요?
        </div>

        <div class="qna-question-content">
            영상에서 데이터셋을 사용한다고 하셨는데, 혹시 다운로드 링크를 받을 수 있을까요?
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
                <button class="answer-submit" type="submit">등록</button>
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
        console.log("📘 lectureQuestionId =", lectureQuestionId);

        if (!lectureQuestionId || lectureQuestionId === "null") {
            alert("잘못된 접근입니다. (질문 ID가 없습니다)");
            window.location.href = "/teacher/management/qna";
            return;
        }

        try {
            // ✅ 질문 상세 데이터 불러오기
            const res = await fetch("/api/teachers/management/qna/answer/" + lectureQuestionId);
            const json = await res.json();
            const data = json.data;
            console.log("📘 QnA Detail:", data);

            // ✅ 화면 데이터 주입
            document.querySelector(".student-name").textContent = "학생: " + data.studentName;
            document.querySelector(".qna-date").textContent = new Date(data.createdAt).toLocaleDateString();
            document.querySelector(".qna-question-title").textContent = "Q. " + data.title;
            document.querySelector(".qna-question-content").textContent = data.content;

            // ✅ 해당 강의로 이동 버튼
            const moveBtn = document.querySelector(".video-move-button");
            moveBtn.addEventListener("click", function() {
                if (!data.lectureId || !data.indexTitle) {
                    alert("강의 정보가 없습니다.");
                    return;
                }
                window.location.href = "/lecture/player?lectureId=" + data.lectureId + "&index=" + data.indexTitle;
            });

            console.log(lectureQuestionId);
            console.log(content);

            // ✅ 답변 등록 버튼 클릭 이벤트
            const submitBtn = document.querySelector(".answer-submit");
            const textarea = document.querySelector(".answer-textarea");
            const cancelBtn = document.querySelector(".answer-cancel");

            submitBtn.addEventListener("click", async function(event) {
                event.preventDefault();
                const content = textarea.value.trim();
                if (content.length === 0) {
                    alert("답변 내용을 입력하세요.");
                    return;
                }

                const formData = new FormData();
                console.log(lectureQuestionId);
                console.log(content);
                formData.append("lectureQuestionId", lectureQuestionId);
                formData.append("content", content);

                try {
                    const res2 = await fetch("/api/teachers/management/qna/answer", {
                        method: "POST",
                        body: formData
                    });
                    window.location.href = "/teacher/management/qna/detail?id="+lectureQuestionId;

                } catch (err) {
                }
            });

            // ✅ 취소 버튼
            cancelBtn.addEventListener("click", function() {
                if (confirm("답변 작성을 취소하시겠습니까?")) {
                    window.location.href = "/teacher/management/qna/detail?id=" + lectureQuestionId;
                }
            });

        } catch (err) {
            console.error("🚨 상세 데이터 불러오기 실패:", err);
        }
    });

</script>
