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
        <div class="teacher-list-title" style="margin-bottom: 20px;">${subject.value} 선생님</div>

     <div class ="recent-lecture-container"></div>
</div>

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


<%-- Local Script --%>
<!-- teacher_list_by_subject.js -->
<script>

    document.addEventListener("DOMContentLoaded", () => {
        const teacherJSP = document.getElementById("teacher").dataset.subject;

        const params = new URLSearchParams();
        params.append("subject", teacherJSP);

        fetch(`/api/teachers/subject/\${teacherJSP}`, {
            method: "GET",
            headers: { 'X-Requested-From': window.location.pathname + window.location.search }
        })
            .then(res => res.json())
            .then(json => {
                console.log("📦 서버 응답:", json);
                console.log("👩‍🏫 선생님 수:", json.data?.length);
                renderSubjectTeachers(json.data);
            })
            .catch(err => console.log("관련 선생님 정보 불러오기 실패 : ", err));

        function renderSubjectTeachers(teachers) {
            const titles = document.querySelector(".teacher-list-title");
            let container = titles?.nextElementSibling;

            if (!container) {
                console.error("과목별 선생님 조회 중 문제 발생");
                return;
            }

            container.innerHTML = ""; // 기존 내용 초기화

            if (!teachers || teachers.length === 0) {
                container.innerHTML = `<p class="no-lecture">해당 과목의 선생님이 존재하지 않습니다.</p>`;
                return;
            }

            teachers.forEach((teacher) => {
                const thumbnailSrc = teacher.thumbnailPath
                    ? "${fileDomain}/" + teacher.thumbnailPath // DB의 file_path 그대로 붙임
                    : "/img/png/default_image.png";
                console.log(teacher.thumbnailPath);

                const item = document.createElement("div");
                item.classList.add("recent-lecture-item");
                const hf = "/teacher/profile/" + teacher.teacherId;


                item.innerHTML = `




                <a href="\${hf}">
                    <img src="\${thumbnailSrc}" alt="강의 썸네일" class="recent-lecture-thumbnail">
                    <div class="lecture-info">
                        <p class="lecture-title">\${teacher.nickname}</p>
                        <p class="lecture-info-text">\${teacher.description || "소개가 없습니다."}</p>
                    </div>
                </a>
            `;

                container.appendChild(item);
            })
        }
    })

</script>