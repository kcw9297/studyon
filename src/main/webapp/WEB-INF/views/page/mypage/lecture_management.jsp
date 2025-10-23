<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<section class="courses">
    <div class="mypage-title">강의 관리</div>
    <div>
        <jsp:include page="/WEB-INF/views/page/mypage/lecture_management_navbar.jsp" />
        <div class="courses-list">
            <div class="courses-item">
                <div class="courses-thumbnail">
                    <a href="">
                        <img src="<c:url value='/img/png/menhera.png'/>" class="mypage-profile" alt="프로필 사진">
                    </a>
                </div>
                <div class="courses-lecture"></div>
                <div class="courses-teacher"></div>
                <div class="courses-percent">
                    <progress class="courses-progress" value="50" min="0" max="100"></progress>
                </div>
                <div class="courses-class">
                    <div class="courses-text1">15강</div>
                    <div class="courses-text2">&nbsp;/ 30강 </div>
                    <div class="courses-text2">&nbsp;(50%)</div>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="courses">
    <div class="mypage-title">강의 관리</div>
    <div>
        <jsp:include page="/WEB-INF/views/page/mypage/lecture_management_navbar.jsp" />
        <div class="courses-list">
            <!-- 여기에 렌더링될 리스트 -->
        </div>
    </div>
</section>

<script>
    let lectures;
    document.addEventListener("DOMContentLoaded", async () => {
        let lectures;

        try {
            const response = await fetch("/api/mypage/lectures?subject=all");
            if (!response.ok) throw new Error("강의 목록 요청 실패");
            const lectures = await response.json();
            console.log("✅ 강의 데이터:", lectures);


            document.querySelector(".courses-lecture").textContent = lectures[0].lectureTitle;
            document.querySelector(".courses-teacher").textContent = lectures[0].teacherName;

            const list = document.querySelector(".courses-list");
            list.innerHTML ="";

            lectures.forEach(lecture => {
                const item = document.createElement("div");
                item.classList.add("courses-item");

                const titleDiv = document.createElement("div");
                titleDiv.classList.add("courses-lecture");
                titleDiv.textContent = lecture.lectureTitle;

                const teacherDiv = document.createElement("div");
                teacherDiv.classList.add("courses-teacher");
                teacherDiv.textContent = lecture.teacherName;

                item.appendChild(titleDiv);
                item.appendChild(teacherDiv);
                list.appendChild(item);
            });










        } catch (err) { // ✅ 괄호 추가
            console.error("❌ 오류 발생:", err);
        }
    }
    );
</script>