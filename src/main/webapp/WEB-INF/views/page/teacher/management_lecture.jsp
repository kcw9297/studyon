<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

<div class="resisted-lecture-label">
  미등록 강의
</div>
<div class ="recent-lecture-container" onclick="location.href='/teacher/management/lectureinfo'">
    <div class="recent-lecture-item">
        <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
        <div class="lecture-info">
            <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
            <p class="lecture-info-text">인프런</p>
            <p class="lecture-info-text">₩90,000</p>
            <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>
        </div>
    </div>
</div>

<div class="resisted-lecture-label">
  승인 대기중인 강의
</div>
<div class ="recent-lecture-container">
  <div class="recent-lecture-item">
    <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
    <div class="lecture-info">
      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
      <p class="lecture-info-text">인프런</p>
      <p class="lecture-info-text">₩90,000</p>
      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>
    </div>
  </div>
</div>

<div class="resisted-lecture-label">
    등록된 강의
</div>
<div class ="recent-lecture-container">
    <div class="recent-lecture-item">
        <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
        <div class="lecture-info">
            <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
            <p class="lecture-info-text">인프런</p>
            <p class="lecture-info-text">₩90,000</p>
            <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>
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

    .recent-lecture-container {
        display: grid;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;
        height:100%;
        box-sizing: border-box;
        background-color: rgb(255, 255, 255);
    }


    .recent-lecture-item {
        width: 260px;            /* 고정 폭 (5등분용) */
        height:auto;           /* 고정 높이 */
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 10px;
        display: flex;
        flex-direction: column;  /* ✅ 세로로 쌓기 */
        justify-content: flex-start; /* 위쪽부터 쌓기 */
        box-sizing: border-box;
    }

    .recent-lecture-item:hover {
        background-color: #bbb;
        transform: translateY(-5px);
        cursor: pointer;
    }
</style>
<script>
    document.addEventListener("DOMContentLoaded", async() => {
        try{
            const res = await fetch("/api/teachers/management/lecturelist");
            const data = await res.json();

            console.log("강사 ID:", data.teacherId);
            console.log("등록대기 강의:", data.pending);
            console.log("등록완료 강의:", data.registered);
            console.log("미등록 강의:", data.unregistered);
        }catch{

        }

    });
</script>