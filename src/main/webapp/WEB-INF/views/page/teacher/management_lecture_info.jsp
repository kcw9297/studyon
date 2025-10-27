<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />
<div class="lecture-view-wrapper">
    <div class="view-content">
        <div class="view-title">강의 상세 보기</div>

        <div class="view-section">
            <label class="view-label">강의 제목</label>
            <div id="lecture-title" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">강사명</label>
            <div id="teacherName" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">강의 소개</label>
            <div id="lecture-description" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">강의 대상</label>
            <div id="lecture-target" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">강의 과목</label>
            <div id="lecture-subject" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">난이도</label>
            <div id="lecture-difficulty" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">판매 가격</label>
            <div id="lecture-price" class="view-value"></div>
        </div>

        <div class="view-section">

            <label class="view-label">썸네일 이미지</label>

            <div id="lecture-thumbnail" class="lecture-thumbnail">
                썸네일을 등록해주세요 📷
            </div>
            <input type="file" id="thumbnail-upload" accept="image/*" style="display:none;">
        </div>


        <div class="view-section">
            <label class="view-label">강의 목차</label>
            <div id="lecture-list-box"></div>
        </div>
    </div>
</div>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/teacher/management_lecture_view.css'/>">

<script>
    document.addEventListener("DOMContentLoaded", async () => {
        const path = window.location.pathname;
        const lectureId = path.split("/").pop();
        const thumbImg = document.getElementById("lecture-thumbnail");
        const fileInput = document.getElementById("thumbnail-upload");
        const thumbBox = document.getElementById("lecture-thumbnail");
        console.log(lectureId);

        thumbImg.addEventListener("click", () => {
            fileInput.click();
        });

        fileInput.addEventListener("change", async (e) => {
            const file = e.target.files[0];
            if (!file) return;

            const form = new FormData();
            form.append("file", file);

            console.log("작동");

            try {
                const res = await fetch("/api/teachers/management/lecture/"+lectureId+"/thumbnail", {
                    method: "PATCH",
                    body: form
                });
                console.log("작동2");
                const json = await res.json();
                if (json.status === "OK") {
                    alert("썸네일이 변경되었습니다.");
                    thumbImg.src = URL.createObjectURL(file);
                } else {
                    alert(json.message || "업로드 실패");
                }
            } catch (err) {
                console.error("썸네일 업로드 실패:", err);
            }
        });


        try {
            const res = await fetch("/api/teachers/management/lecture/" + lectureId + "/thumbnail/view");
            const thumbBox = document.getElementById("lecture-thumbnail");

            if (res.ok) {
                console.log("✅ 썸네일 이미지 로드 성공");
                thumbBox.innerHTML =
                    '<img src="/api/teachers/management/lecture/' + lectureId + '/thumbnail/view?ts=' + Date.now() + '"' +
                    ' alt="강의 썸네일"' +
                    ' style="width:100%; height:100%; border-radius:10px; object-fit:cover;">';
            } else {
                console.warn("❌ 썸네일 없음, 기본 문구 표시");
                thumbBox.textContent = "썸네일을 등록해주세요 📷";
            }
        } catch (err) {
            console.error("🚨 썸네일 로드 실패:", err);
            thumbBox.textContent = "썸네일을 등록해주세요 📷";
        }









        if (!lectureId) {
            console.error("❌ lectureId가 없습니다.");
            return;
        }

        try {
            const response = await fetch("/api/teachers/management/lectureinfo/" + lectureId);
            const json = await response.json();
            const lecture = json.data;
            console.log(lecture);

            // ✅ 기본 정보 표시
            document.getElementById("teacherName").innerText = lecture.teacherName;
            document.getElementById("lecture-title").innerText = lecture.title;
            document.getElementById("lecture-description").innerText = lecture.description;
            document.getElementById("lecture-target").innerText = lecture.target;
            document.getElementById("lecture-subject").innerText = lecture.subject;
            document.getElementById("lecture-difficulty").innerText = lecture.difficulty;
            document.getElementById("lecture-price").innerText = "₩" + lecture.price.toLocaleString();

            // ✅ 썸네일 이미지 표시
            const thumbnailElem = document.getElementById("lecture-thumbnail");

            // ✅ 강의 목차 렌더링
            const listBox = document.getElementById("lecture-list-box");
            listBox.innerHTML = "";
            lecture.videos.forEach(video => {
                const item = document.createElement("div");
                item.classList.add("lecture-item");
                item.innerHTML = `
                <div class="lecture-index">${video.index}강</div>
                <div class="lecture-info">
                    <div class="lecture-title">${video.title}</div>
                    <div class="lecture-file">파일: ${video.fileName}</div>
                </div>
            `;
                listBox.appendChild(item);
            });
        } catch (err) {
            console.error("🚨 강의 불러오기 실패:", err);
        }
    });
</script>

<style>


    .lecture-thumbnail {
        width: 100%;
        height: auto;
        border-radius: 10px;
        background-color: #f5f5f5;
        border: 1px dashed #bbb;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #777;
        font-size: 14px;
        font-weight: 500;
        text-align: center;
        cursor: pointer;
        transition: all 0.2s ease-in-out;
    }

    .lecture-thumbnail:hover {
        background-color: #e8f5e9;
        border-color: #7cb342;
        color: #558b2f;
    }

    .thumbnail-register-button {
        display: flex;                /* Flexbox로 중앙정렬 */
        justify-content: center;
        align-items: center;
        background: linear-gradient(135deg, #8fbc8f, #7fbf7f); /* 은은한 그라데이션 */
        color: white;                 /* 글자색은 흰색으로 */
        font-weight: bold;
        padding: 8px 18px;            /* 여백 살짝 넓게 */
        border-radius: 25px;          /* 둥글둥글하게 */
        height: 36px;
        border: none;                 /* 기본 테두리 제거 */
        cursor: pointer;              /* 마우스 커서 변경 */
        box-shadow: 0 2px 5px rgba(0,0,0,0.15); /* 살짝 그림자 */
        transition: all 0.2s ease-in-out;       /* 부드러운 애니메이션 */
    }

    .thumbnail-register-button:hover {
        background: linear-gradient(135deg, #7fbf7f, #6fae6f);
        transform: translateY(-2px);  /* 살짝 올라가는 효과 */
        box-shadow: 0 4px 8px rgba(0,0,0,0.2);
    }

    .lecture-view-wrapper {
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        background: #fff;
        padding-bottom: 80px;
    }

    .view-content {
        width: 80%;
        max-width: 1000px;
        background: #fafafa;
        border-radius: 12px;
        padding: 30px 40px;
        box-shadow: 0 5px 15px rgba(0,0,0,0.05);
        margin-top: 30px;
    }

    .view-title {
        font-size: 30px;
        font-weight: bold;
        text-align: center;
        color: #333;
        margin-bottom: 25px;
    }

    .view-section {
        margin-bottom: 20px;
    }

    .view-label {
        display: block;
        font-weight: 600;
        font-size: 18px;
        margin-bottom: 8px;
        color: #444;
    }

    .view-value {
        font-size: 17px;
        background: #f9f9f9;
        padding: 12px 15px;
        border-radius: 8px;
        border: 1px solid #ddd;
    }

    .thumbnail-box {
        display: flex;
        justify-content: flex-start;
        background: #fff;
        border: 1px dashed #ccc;
        border-radius: 10px;
        padding: 15px;
    }

    .thumbnail-preview {
        width: 50%;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    }

    #lecture-list-box {
        margin-top: 10px;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        background: #fff;
    }

    .lecture-item {
        display: flex;
        align-items: flex-start;
        gap: 15px;
        margin-bottom: 12px;
        padding-bottom: 10px;
        border-bottom: 1px solid #eee;
    }

    .lecture-item:last-child {
        border-bottom: none;
    }

    .button-box {
        display: flex;
        justify-content: center;
        gap: 10px;
        margin-top: 40px;
    }

    .list-change-btn, .edit-btn, .delete-btn {
        border: none;
        background: #f1f1f1;
        border-radius: 6px;
        padding: 10px 16px;
        cursor: pointer;
        font-weight: 500;
        transition: 0.2s;
    }

    .edit-btn:hover { background: #d5f5e3; }
    .delete-btn:hover { background: #f5b7b1; }

</style>