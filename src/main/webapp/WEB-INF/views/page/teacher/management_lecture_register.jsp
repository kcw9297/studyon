<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<link rel="stylesheet" type="text/css"
      href="<c:url value='/css/page/teacher/management_lecture_register.css'/>">

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

<div class="lecture-resister-wrapper">
    <div class="resister-title">강의 등록</div>

    <!-- ✅ 폼 형식으로 변경 -->
    <form id="lectureForm">
        <!-- 제목 -->
        <label class="resister-description" for="title">강의 제목</label>
        <div>
            <input class="resister-lecture-title" type="text" id="title" name="title">
        </div>
        <div class="text-error" id="titleError"></div>

        <!-- 소개 -->
        <label class="resister-description" for="description">강의 소개</label>
        <div class="editor-wrapper" style="width: 100%; max-width: 1003px;">
            <textarea id="content" name="description" style="display:none;"></textarea>
            <iframe style="width: 100%; height: 853px;"
                    src="<c:url value="/editor?width=1000&height=800&editorId=${editorId}&fileUploadUrl=/teacher/api/lectures/cache/description-image"/>"></iframe>
        </div>
        <div class="text-error" id="descriptionError"></div>

        <!-- 대상 -->
        <label class="resister-description" for="target">강의 대상</label>
        <div>
            <select class="resister-lecture-target" id="target" name="target">
                <option value="">선택하세요</option>
                <c:forEach items="${targets}" var="target">
                    <option value="${target}">${target.value}</option>
                </c:forEach>
            </select>
        </div>
        <div class="text-error" id="targetError"></div>

        <!-- 난이도 -->
        <label class="resister-description" for="difficulty">난이도</label>
        <div>
            <select class="resister-lecture-target" id="difficulty" name="difficulty">
                <option value="">선택하세요</option>
                <c:forEach items="${difficulties}" var="difficulty">
                    <option value="${difficulty}">${difficulty.value}</option>
                </c:forEach>
            </select>
        </div>
        <div class="text-error" id="difficultyError"></div>

        <!-- 세부 과목 -->
        <label class="resister-description" for="subject">세부 과목</label>
        <div>
            <select class="resister-lecture-target" id="subjectDetail" name="subjectDetail">
                <option value="">선택하세요</option>
                <c:forEach items="${subjectDetails}" var="detail">
                    <option value="${detail}">${detail.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="text-error" id="subjectDetailError"></div>


        <!-- 가격 -->
        <label class="resister-description" for="price">판매 가격</label>
        <div>
            <input class="resister-lecture-price" type="number" id="price" name="price" min="0"> 원
        </div>
        <div class="text-error" id="priceError"></div>

        <!-- 커리큘럼 -->
        <div>
            <label class="resister-description">강의 목차</label>
        </div>
        <button type="button" id="add-lecture-btn">+ 강의 추가</button>
        <div id="lecture-list-box"></div>
        <div class="text-error" id="curriculumTitlesError"></div>
        <div>허용 특수문자 : _-.,/!?()[]{}</div>

        <!-- 제출 버튼 -->
        <div class="submit-box">
            <button class="submit-button" type="submit">강의 등록하기</button>
        </div>
    </form>
</div>

<script>

    // 스크립트 상수
    let isRunning = true;
    let updateTimer = null;

    document.addEventListener("DOMContentLoaded", () => {
        const form = document.getElementById("lectureForm");
        const addBtn = document.getElementById("add-lecture-btn");
        const listBox = document.getElementById("lecture-list-box");

        // 🔹 강의 번호 재정렬
        function updateNumbers() {
            const items = listBox.querySelectorAll(".lecture-item");
            items.forEach((item, i) => {
                item.querySelector(".lecture-number").textContent = "제 " + (i + 1) + "강 제목:";
            });
        }

        // 🔹 강의 추가
        addBtn.addEventListener("click", () => {
            const div = document.createElement("div");
            div.classList.add("lecture-item");
            div.innerHTML = `
            <label class="lecture-number"></label>
            <input type="text" name="curriculumTitles" placeholder="강의 제목 입력">
            <button type="button" class="remove-btn">✖</button>
        `;
            listBox.appendChild(div);
            updateNumbers();
        });

        // 🔹 삭제
        listBox.addEventListener("click", (e) => {
            if (e.target.classList.contains("initialize-btn")) {
                e.target.closest(".lecture-item").remove();
                updateNumbers();
            }
        });

        // 🔹 폼 제출
        form.addEventListener("submit", async (e) => {

            try {
                e.preventDefault();

                // REST API 요청
                form.querySelector('textarea[name="description"]').value = document.getElementById("content").value || "";
                const formData = new FormData(form);
                formData.append("editorId", "${editorId}");

                const res = await fetch("/teacher/api/lectures", {
                    headers: {'X-Requested-From': window.location.pathname + window.location.search},
                    method: "POST",
                    body: formData
                });

                // 서버 JSON 응답 문자열 파싱
                const rp = await res.json();
                console.log("서버 응답:", rp);

                // 요청 실패 처리
                if (!res.ok || !rp.success) {

                    // 로그인이 필요한 경우
                    if (rp.statusCode === 401) {

                        // 로그인 필요 안내 전달
                        if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                            window.location.href = rp.redirect || "/login";
                        }

                        // 로직 중단
                        return;
                    }

                    // 권한이 부족한 경우
                    if (rp.statusCode === 403) {
                        alert(rp.message || "접근 권한이 없습니다.");
                        return;
                    }

                    // 유효성 검사에 실패한 경우
                    if (rp.inputErrors) {
                        document.querySelectorAll(".text-error").forEach((el) => {el.textContent = "";});
                        Object.entries(rp.inputErrors).forEach(([field, message]) => {
                            const errorElem = document.getElementById(`\${field}Error`);
                            if (errorElem) errorElem.textContent = message;
                        });
                        return;
                    }


                    // 기타 예기치 않은 오류가 발생한 경우
                    alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                    return;
                }

                // 강의 등록 후, 강의 상세로 이동
                alert(rp.message || "강의 등록이 완료되었습니다.");
                window.location.href= rp.redirect || "/teacher/management/lecturelist";


            } catch (error) {
                console.error("프로필 로드 실패:", error);
            }


        });


    });

    // 에디터 내용 변동 시 처리 함수
    function onEditorContentChange(content) {
        document.getElementById("content").value = content;
        console.log(document.getElementById("content").value);
    }


</script>



<style>

    .TeacherManagement-navbar{
        display: flex;
        background-color: #f2f2f2;
        padding: 10px;
        margin-bottom: 20px;
    }

    .TeacherManagement-navbar nav-item{
        margin-right:40px;
        font-size: 18px;
        color: #333;
        text-decoration: none;
    }


    #lecture-list-box {
        margin-top: 15px;
        padding: 15px;
        border: 1px solid #ddd;
        border-radius: 8px;
        background: #fafafa;
    }

    .lecture-item {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 10px;
    }

    .lecture-item label {
        width: 100px;
        font-weight: 500;
    }

    .lecture-item input {
        padding: 8px 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 15px;
    }

    .resister-title{
        font-size: 30px;
        font-weight: bold;
        align-items: center;
        text-align: center;
    }
    .lecture-title{
        font-size: 20px;
        font-weight: bold;
        align-items: center;
        text-align: center;
    }

    .resister-description {
        display: block;
        font-weight: 600;
        color: #444;
        font-size: 18px;
    }

    .resister-lecture-title{
        width: 50%;
        padding: 12px 14px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 8px;
        transition: border-color 0.2s ease, box-shadow 0.2s ease;
        box-sizing: border-box;
    }

    .resister-lecture-description{
        width: 50%;
        padding: 12px 14px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 8px;
        transition: border-color 0.2s ease, box-shadow 0.2s ease;
        box-sizing: border-box;
    }

    .resister-lecture-target{
        width: 10%;
        padding: 12px 14px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 8px;
        transition: border-color 0.2s ease, box-shadow 0.2s ease;
        box-sizing: border-box;
    }

    .resister-lecture-price{
        width: 10%;
        padding: 12px 14px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 8px;
        transition: border-color 0.2s ease, box-shadow 0.2s ease;
        box-sizing: border-box;
    }


    input[type="file"] {
        font-size: 15px;
        margin-top: 8px;
    }

    textarea {
        height: 140px;
        resize: vertical;
    }

    .line-dividebox-10px{
        width: 100%;
        height: 10px;
        background-color: white;
        display: flex
    }

    .lecture-resister-wrapper{
        display: flex;
        flex-direction: column;
        gap: 20px;
        width: 100%;
        padding-left:20px;
    }

    .lecture-input {
        width: 100%;
        padding: 10px;
        border: 2px solid #c9883f;
        border-radius: 8px;
        font-size: 15px;
        background-color: #fdfdfd;
    }

    /* 강의 영상 파일 input 전용 */
    .lecture-input-file {
        border: 1px dashed #aaa;
        padding: 8px;
        border-radius: 6px;
        background-color: #fcfcfc;
    }

    .lecture-input:focus {
        border-color: #d89c2e;
        outline: none;
        box-shadow: 0 0 5px rgba(212, 96, 29, 0.3);
    }

    input[type="text"].lecture-input {
        border: 2px solid #d6da0f;
        background-color: #f8fff8;
        font-size: 16px;
        border-radius: 8px;
        padding: 10px;
    }

    .fuckinglabel{
        width:100px;
    }

    .submit-box{
        display: flex;
        justify-content: center;
        margin-top: 20px;
        margin-bottom:10px;
    }

    .submit-button {
        display: inline-block;
        width: 100%;
        max-width: 300px;
        padding: 15px 20px;
        margin-top: 25px;
        font-size: 18px;
        font-weight: 600;
        color: white;
        background: linear-gradient(135deg, #27ae60, #2ecc71); /* 초록 계열 그라데이션 */
        border: none;
        border-radius: 10px;
        cursor: pointer;
        box-shadow: 0 3px 8px rgba(46, 204, 113, 0.3);
        transition: all 0.25s ease;
        text-align: center;
    }

    /* hover 효과 */
    .submit-button:hover {
        background: linear-gradient(135deg, #219150, #27ae60);
        box-shadow: 0 4px 12px rgba(39, 174, 96, 0.4);
        transform: translateY(-2px);
    }
</style>