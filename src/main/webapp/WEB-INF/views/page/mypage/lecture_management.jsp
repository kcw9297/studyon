<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<section class="courses">
    <div class="mypage-title">강의 관리</div>
    <div>
        <jsp:include page="/WEB-INF/views/page/mypage/lecture_management_navbar.jsp" />
        <div class="courses-list">
            <%--
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
            //const subject = window.location.pathname.split("/").pop();
            //const response = await fetch("/api/mypage/lectures?subject="+subject);
            //const response = await fetch("/api/mypage/lectures?subject=all")

            const params = new URLSearchParams(window.location.search);
            let subject = params.get("subject") || "all";
            console.log(subject);


            const response = await fetch("/api/mypage/lectures?subject="+subject);

            if (!response.ok) throw new Error("강의 목록 요청 실패");
            const lectures = await response.json();
            console.log("✅ 강의 데이터:", lectures);

            const list = document.querySelector(".courses-list");
            list.innerHTML = "";

            lectures.forEach(lecture => {
                const item = document.createElement("div");
                item.classList.add("courses-item");
                console.log("foreach 안의 lecture data:", JSON.stringify(lecture, null, 2)); // 보기 좋게 JSON으로 출력

                // ✅ 썸네일
                const thumbnail = document.createElement("a");
                thumbnail.classList.add("mypage-lecture-thumbnail");
                //thumbnail.href = `/player?lectureId=69`;
                thumbnail.href = "/player?lectureId="+lecture.lectureId;

                console.log("👉 썸네일 href:", thumbnail.href);

                const img = document.createElement("img");
                img.src = "http://localhost:8080/img/png/menhera.png"; // 하드코딩 썸네일
                img.alt = lecture.lectureTitle;
                img.classList.add("mypage-profile");
                thumbnail.appendChild(img);

                // ✅ 제목
                const titleDiv = document.createElement("div");
                titleDiv.classList.add("courses-lecture");
                titleDiv.textContent = lecture.lectureTitle;

                // ✅ 강사명
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
