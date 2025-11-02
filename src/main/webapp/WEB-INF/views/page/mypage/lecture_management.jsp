<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<section class="courses">
    <div class="mypage-title">강의 관리</div>
    <div>
        <jsp:include page="/WEB-INF/views/page/mypage/lecture_management_navbar.jsp" />
        <div class="courses-list">
            <%-- LECTURE LIST FRAME
            <div class="courses-item">
                <div class="courses-thumbnail">
                    <a class="mypage-lecture-thumbnail" href="">
                        <img src="">
                    </a>
                </div>
                <div class="courses-lecture"></div>
                <div class="courses-teacher"></div>
                <div class="courses-percent">
                    <progress class="courses-progress" value="50" min="0" max="100"></progress>
                </div>
                <div class="courses-class">
                    <div class="courses-text1"></div>
                    <div class="courses-text2"></div>
                    <div class="courses-text2"></div>
                </div>
            </div>
            --%>
        </div>
    </div>
</section>

<script>
    document.addEventListener("DOMContentLoaded", async () => {
        try {
            const params = new URLSearchParams(window.location.search);
            let subject = params.get("subject") || "all";
            console.log(subject);


            const response = await fetch("/api/mypage/lectures?subject="+subject);

            if (!response.ok) throw new Error("강의 목록 요청 실패");
            const lectures = await response.json();
            console.log("강의 데이터:", lectures);

            const list = document.querySelector(".courses-list");
            list.innerHTML = "";

            lectures.forEach(lecture => {
                const item = document.createElement("div");
                item.classList.add("courses-item");
                console.log("foreach 안의 lecture data:", JSON.stringify(lecture, null, 2)); // 보기 좋게 JSON으로 출력

                // 썸네일
                const thumbnail = document.createElement("a");
                thumbnail.classList.add("mypage-lecture-thumbnail");
                thumbnail.href = "/player?lectureId="+lecture.lectureId;

                console.log("썸네일 href:", thumbnail.href);

                const img = document.createElement("img");
                img.src = lecture.thumbnailImagePath ? "${fileDomain}/" + lecture.thumbnailImagePath : "/img/png/default_image.png";



                img.alt = lecture.lectureTitle;
                img.classList.add("mypage-profile");
                thumbnail.appendChild(img);

                // 제목
                const titleDiv = document.createElement("div");
                titleDiv.classList.add("courses-lecture");
                titleDiv.textContent = lecture.lectureTitle;

                // 강사명
                const teacherDiv = document.createElement("div");
                teacherDiv.classList.add("courses-teacher");
                teacherDiv.textContent = lecture.teacherName;

                item.appendChild(thumbnail);
                item.appendChild(titleDiv);
                item.appendChild(teacherDiv);
                list.appendChild(item);
            });

        } catch (err) {
            console.error("❌ 오류 발생:", err);
        }
    });

</script>

<style>
    /* ===== courses(강의 관리) ===== */
    .courses{min-height:900px; display:flex; flex-direction:column; padding:20px; border:1px solid var(--light-gray); border-radius:20px; font-family:"Inter",sans-serif; font-style:normal; font-size:16px;}
    /* 강의 목록 */
    .courses-list{display:grid; grid-template-columns:1fr 1fr 1fr; gap:20px 40px; padding:10px;}
    .courses-item {
        display: flex;
        flex-direction: column;
        width: 320px;
        height: auto;
        border: 2px solid #ddd;          /* 연한 회색 테두리 */
        border-radius: 12px;             /* 살짝 둥근 모서리 */
        padding: 12px;                   /* 내부 여백 */
        background-color: #fff;          /* 흰 배경 */
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);  /* 살짝 그림자 */
        transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .courses-item:hover {
        transform: translateY(-4px);      /* hover 시 살짝 떠오름 */
        box-shadow: 0 4px 10px rgba(0,0,0,0.15);
    }
    .courses-thumbnail{position:relative;}
    .courses-thumbnail img{width:320px; height:180px; border-radius:15px;}
    .courses-lecture{font-size:20px; font-weight:var(--bold);}
    .courses-teacher{margin-bottom:5px; font-size:18px;}
    /* 강의 목록 - 진행률 바 */
    .courses-percent{margin-bottom:10px; padding:0; line-height:0;}
    .courses-progress{width:320px; height:10px; border:none; border-radius:100px; overflow:hidden; -webkit-appearance:none; appearance:none;}
    .courses-progress::-webkit-progress-bar{background-color:var(--light-gray);}
    .courses-progress::-webkit-progress-value{background-color:var(--deep-yellow);}
    /* 강의 목록 - 진행률 텍스트 */
    .courses-class{display:flex; flex-direction:row; align-items:center;}
    .courses-text1{font-size:18px;}
    .courses-text2{color:var(--deep-gray)}
    .mypage-profile{
        width:100%;
        height:160px;
    }

</style>
