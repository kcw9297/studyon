<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<link rel="stylesheet" type="text/css"
      href="<c:url value='/css/page/teacher/management_lecture_register.css'/>">

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

<div class="lecture-resister-wrapper">
    <div class="resister-title">강의 등록</div>

    <!-- ✅ 폼 형식으로 변경 -->
    <form action="/api/teachers/lecture/register" method="post">
        <!-- 제목 -->
        <label class="resister-description" for="title">강의 제목</label>
        <div>
            <input class="resister-lecture-title" type="text" id="title" name="title" required>
        </div>

        <!-- 소개 -->
        <label class="resister-description" for="description">강의 소개</label>
        <div>
            <textarea class="resister-lecture-description" id="description" name="description" required></textarea>
        </div>

        <!-- 대상 -->
        <label class="resister-description" for="target">강의 대상</label>
        <div>
            <select class="resister-lecture-target" id="target" name="target" required>
                <option value="">선택하세요</option>
                <option value="HIGH1">고1</option>
                <option value="HIGH2">고2</option>
                <option value="HIGH3">고3</option>
            </select>
        </div>

        <!-- 난이도 -->
        <label class="resister-description" for="difficulty">난이도</label>
        <div>
            <select class="resister-lecture-target" id="difficulty" name="difficulty" required>
                <option value="">선택하세요</option>
                <option value="BASIC">기초</option>
                <option value="STANDARD">핵심</option>
                <option value="ADVANCED">심화</option>
                <option value="MASTER">응용</option>
                <option value="EXPERT">최상급</option>
            </select>
        </div>

        <!-- 과목 -->
        <label class="resister-description" for="subject">과목</label>
        <div>
            <select class="resister-lecture-target" id="subject" name="subject" required>
                <option value="">선택하세요</option>
                <option value="KOREAN">국어</option>
                <option value="ENGLISH">영어</option>
                <option value="MATH">수학</option>
                <option value="SCIENCE">과학</option>
                <option value="SOCIETY">사회</option>
            </select>
        </div>

        <!-- 서브 과목 -->
        <div id="subject-detail-box" style="margin-top: 10px;"></div>


        <!-- 가격 -->
        <label class="resister-description" for="price">판매 가격</label>
        <div>
            <input class="resister-lecture-price" type="number" id="price" name="price" min="0" required> 원
        </div>

        <!-- 커리큘럼 -->
        <div>
            <label class="resister-description">강의 목차</label>
        </div>
        <button type="button" id="add-lecture-btn">+ 강의 추가</button>
        <div id="lecture-list-box"></div>

        <!-- 제출 버튼 -->
        <div class="submit-box">
            <button class="submit-button" type="submit">강의 등록하기</button>
        </div>
    </form>
</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const form = document.getElementById("lectureForm");
        const addBtn = document.getElementById("add-lecture-btn");
        const listBox = document.getElementById("lecture-list-box");
        const subjectSelect = document.getElementById("subject");
        const detailBox = document.getElementById("subject-detail-box");
        const subjectDetails = {
            KOREAN: [
                { name: "공통 국어", value: "KOREAN_COMMON" },
                { name: "화법과 작문", value: "KOREAN_SPEECH_WRITING" },
                { name: "언어와 매체", value: "KOREAN_LANGUAGE_MEDIA" },
                { name: "문학", value: "KOREAN_LITERATURE" },
                { name: "비문학(독서)", value: "KOREAN_READING" },
                { name: "문법", value: "KOREAN_GRAMMAR" }
            ],
            MATH: [
                { name: "수학 I", value: "MATH_I" },
                { name: "수학 II", value: "MATH_II" },
                { name: "기하", value: "GEOMETRY" }
            ],
            ENGLISH: [
                { name: "독해", value: "ENGLISH_READING" },
                { name: "듣기", value: "ENGLISH_LISTENING" },
                { name: "문법", value: "ENGLISH_GRAMMAR" },
                { name: "어휘", value: "ENGLISH_VOCAB" },
                { name: "회화", value: "ENGLISH_CONVERSATION" }
            ],
            SOCIAL: [
                { name: "생활과 윤리", value: "SOCIAL_ETHICS_LIFE" },
                { name: "윤리와 사상", value: "SOCIAL_ETHICS_THOUGHT" },
                { name: "한국지리", value: "SOCIAL_KOREAN_GEOGRAPHY" },
                { name: "세계지리", value: "SOCIAL_WORLD_GEOGRAPHY" },
                { name: "동아시아사", value: "SOCIAL_EAST_ASIA_HISTORY" },
                { name: "세계사", value: "SOCIAL_WORLD_HISTORY" },
                { name: "경제", value: "SOCIAL_ECONOMY" },
                { name: "정치와 법", value: "SOCIAL_POLITICS_LAW" },
                { name: "사회·문화", value: "SOCIAL_CULTURE" }
            ],
            SCIENCE: [
                { name: "물리 I", value: "SCIENCE_PHYSICS_I" },
                { name: "화학 I", value: "SCIENCE_CHEMISTRY_I" },
                { name: "생명과학 I", value: "SCIENCE_BIOLOGY_I" },
                { name: "지구과학 I", value: "SCIENCE_EARTH_I" },
                { name: "물리 II", value: "SCIENCE_PHYSICS_II" },
                { name: "화학 II", value: "SCIENCE_CHEMISTRY_II" },
                { name: "생명과학 II", value: "SCIENCE_BIOLOGY_II" },
                { name: "지구과학 II", value: "SCIENCE_EARTH_II" }
            ]
        };




        subjectSelect.addEventListener("change", (e) => {
            const selected = e.target.value;
            detailBox.innerHTML = ""; // 초기화

            if (!selected) return;

            const options = subjectDetails[selected] || [];

            // ✅ 세부 과목 select 생성
            const detailSelect = document.createElement("select");
            detailSelect.id = "subjectDetail";
            detailSelect.name = "subjectDetail";
            detailSelect.required = true;
            detailSelect.classList.add("resister-lecture-target");

            // 기본 옵션
            const defaultOpt = document.createElement("option");
            defaultOpt.value = "";
            defaultOpt.textContent = "세부 과목을 선택하세요";
            detailSelect.appendChild(defaultOpt);

            // 세부 과목 추가
            options.forEach(detail => {
                const opt = document.createElement("option");
                opt.value = detail.toUpperCase().replace(/\s|\·/g, "_"); // ENUM 호환용
                opt.textContent = detail;
                detailSelect.appendChild(opt);
            });

            // ✅ 화면에 표시
            const label = document.createElement("label");
            label.classList.add("resister-description");
            label.textContent = "세부 과목";

            detailBox.appendChild(label);
            detailBox.appendChild(detailSelect);
        });subjectSelect.addEventListener("change", (e) => {
            const selected = e.target.value;
            detailBox.innerHTML = ""; // 초기화

            if (!selected) return;

            const options = subjectDetails[selected] || [];

            // ✅ 세부 과목 select 생성
            const detailSelect = document.createElement("select");
            detailSelect.id = "subjectDetail";
            detailSelect.name = "subjectDetail";
            detailSelect.required = true;
            detailSelect.classList.add("resister-lecture-target");

            // 기본 옵션
            const defaultOpt = document.createElement("option");
            defaultOpt.value = "";
            defaultOpt.textContent = "세부 과목을 선택하세요";
            detailSelect.appendChild(defaultOpt);

            // ✅ ENUM value (영문)으로 전송, 사용자에게는 한글 표시
            options.forEach(detail => {
                const opt = document.createElement("option");
                opt.value = detail.value;       // 서버 ENUM과 동일
                opt.textContent = detail.name;  // 사용자 표시용
                detailSelect.appendChild(opt);
            });

            const label = document.createElement("label");
            label.classList.add("resister-description");
            label.textContent = "세부 과목";

            detailBox.appendChild(label);
            detailBox.appendChild(detailSelect);
        });








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
            <input type="text" name="curriculumTitles" placeholder="강의 제목 입력" required>
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
            e.preventDefault();

            const formData = new FormData(form);
            const params = new URLSearchParams(formData);

            try {
                const res = await fetch("/api/teachers/lecture/register", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: params
                });

                const json = await res.json();
                if (res.ok) {
                    alert(json.message || "강의 등록이 완료되었습니다!");
                    form.reset();
                    listBox.innerHTML = "";
                } else {
                    alert(json.message || "등록 실패");
                }
            } catch (err) {
                console.error("❌ 오류:", err);
                alert("서버 오류가 발생했습니다.");
            }
        });
    });




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