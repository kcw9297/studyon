<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/teacher_list.css'/>">

<div id="content">
        <div class="nav-box">
            <a class="nav-item" href="<c:url value='/teacher/find/MATH'/>">수학</a>
            <a class="nav-item" href="<c:url value='/teacher/find/ENGLISH'/>">영어</a>
            <a class="nav-item" href="<c:url value='/teacher/find/KOREAN'/>">국어</a>
            <a class="nav-item" href="<c:url value='/teacher/find/SCIENCE'/>">과학탐구</a>
            <a class="nav-item" href="<c:url value='/teacher/find/SOCIAL'/>">사회탐구</a>
        </div>

        <%-- 과목 정보 --%>


        <div id="teacher" data-subject="${subject.name()}"></div>
        <div class="teacher-list-title">${subject.value} 선생님</div>

     <div class ="recent-lecture-container"></div>
</div>


<%-- Local Script --%>
<!-- teacher_list_by_subject.js -->
<script src="<c:url value='/js/page/teacher/teacher_list_by_subject.js'/>"></script>

<style>

    .lecture-info{
        padding:5px;
        padding-top:0px;
    }
    .nav-box{
        display: flex;
        flex-direction: row;
        width: 100%;
    }

    .nav-item{
        width:100%;
        height: 40px;
        text-align: center;
        align-items: center;
        display: flex;
        justify-content: center;
        background-color: rgb(255, 255, 255);
        margin:10px;
        border:1px solid rgb(228, 216, 216);
        border-radius:10px;
        font-size:20px;
        font-weight:bold;
        color: rgba(0, 0, 0, 0.6);
    }

    .nav-item:hover{
        background-color: rgb(163, 206, 170);
        transition: all 0.3s ease;
        cursor:pointer;
    }


    .recent-lecture-container {
        display: grid;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;
        box-sizing: border-box;
        background-color: rgb(255, 255, 255);
    }


    .recent-lecture-item {
        width: 260px;
        height: auto;
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 10px;
        display: flex;
        /* overflow: hidden; */ /* 이미지가 넘칠 때 방지 */
        flex-direction: column;  /* ✅ 세로로 쌓기 */
        justify-content: flex-start; /* 위쪽부터 쌓기 */
        box-sizing: border-box;
    }

    .recent-lecture-item:hover {
        background-color: #bbb;
        transform: translateY(-5px);
        cursor: pointer;
    }

    .lecture-section-title{
        font-size: 24px;
        font-weight: bold;
    }
    .lecture-title{
        font-size:20px;
        font-weight: bold;
    }

    .teacher-list-title{
        font-size:30px;
        font-weight:bold;
        text-align: left;
        font-family: 'Noto Sans KR', sans-serif;
    }

    .recent-lecture-thumbnail {
        width: 100%;
        height: 200px;
        object-fit: cover;
        border-bottom: 1px solid #ddd;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }
</style>