<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

<div class="qna-main-container">
    <div class="qna-header">
        <label class="qna-title">Q&A</label>

        <div class="qna-filter-box">
            <select id="filterOption" class="qna-filter-select">
                <option value="all">전체보기</option>
                <option value="answered">답변 완료</option>
                <option value="unanswered">미답변</option>
            </select>
        </div>
    </div>

    <hr class="qna-divider">

    <div class="qna-list">
        <!-- ✅ 예시 항목 -->
        <div class="qna-item" onclick="window.location.href='/teacher/management/qna/detail'">
        <div class="qna-item-header">
                <div class="qna-item-left">
                    <span class="qna-status answered">✅ 답변 완료</span>
                    <span class="qna-item-title">PPT 템플릿은 어디서 다운로드 가능한가요?</span>
                </div>
                <span class="qna-item-date">2025.10.30</span>
            </div>
            <div class="qna-item-meta">
                <span class="qna-item-writer">익명 수강생</span> ·
                <span class="qna-item-index">[2강 - PPT 구성하기]</span>
            </div>
        </div>

            <div class="qna-item" onclick="window.location.href='/teacher/management/qna/answer'">
            <div class="qna-item-header">
                <div class="qna-item-left">
                    <span class="qna-status pending">⌛ 미답변</span>
                    <span class="qna-item-title">강의 영상 소리가 안 나와요.</span>
                </div>
                <span class="qna-item-date">2025.10.29</span>
            </div>
            <div class="qna-item-meta">
                <span class="qna-item-writer">김학생</span> ·
                <span class="qna-item-index">[1강 - 오리엔테이션]</span>
            </div>
        </div>
    </div>
</div>

<style>
    /* ===== 전체 영역 ===== */
    .qna-main-container {
        width: 100%;
        min-height: 850px;
        background: #fafafa;
        border-radius: 12px;
        padding: 30px 50px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        gap: 25px;
        font-family: 'Noto Sans KR', sans-serif;
    }

    /* ===== 헤더 ===== */
    .qna-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .qna-title {
        font-size: 26px;
        font-weight: 700;
        color: #222;
    }

    .qna-filter-select {
        padding: 8px 12px;
        border-radius: 8px;
        border: 1px solid #ccc;
        font-size: 15px;
        cursor: pointer;
        background: white;
        transition: all 0.2s ease;
    }

    .qna-filter-select:hover {
        border-color: #888;
    }

    .qna-divider {
        border: 0;
        height: 1px;
        background: #ddd;
    }

    /* ===== 리스트 ===== */
    .qna-list {
        display: flex;
        flex-direction: column;
        gap: 18px;
    }

    .qna-item {
        background: white;
        border-radius: 10px;
        padding: 18px 24px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        cursor: pointer;
        transition: all 0.25s ease;
    }

    .qna-item:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 10px rgba(0,0,0,0.08);
    }

    /* ===== QNA 헤더 ===== */
    .qna-item-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 6px;
    }

    .qna-item-left {
        display: flex;
        align-items: center;
        gap: 8px;
    }

    .qna-item-title {
        font-weight: 600;
        color: #333;
        font-size: 16px;
        line-height: 1.4;
        max-width: 700px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .qna-item-date {
        font-size: 13px;
        color: #777;
    }

    /* ===== QNA 상태 ===== */
    .qna-status {
        font-size: 13px;
        font-weight: 600;
        padding: 3px 8px;
        border-radius: 6px;
    }

    .qna-status.answered {
        background: #e7f9e9;
        color: #27ae60;
    }

    .qna-status.pending {
        background: #fff2e6;
        color: #e67e22;
    }

    /* ===== 메타 정보 ===== */
    .qna-item-meta {
        font-size: 14px;
        color: #666;
    }

    .qna-item-writer {
        font-weight: 500;
    }

    .qna-item-index {
        color: #888;
    }
</style>
<script>
    document.addEventListener("DOMContentLoaded", async () => {
        const response = await fetch("/api/teachers/management/qna");
        const jsondata = await response.json(); // ✅ await 추가!

        console.log(jsondata);        // 전체 응답 확인
        const list = jsondata.data;   // RestUtils.ok(response) 구조면 여기에 데이터가 있음
        console.log(list);
    });

</script>