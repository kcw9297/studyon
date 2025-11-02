<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/teacher/management_profile.css'/>">

<div id="content">
    <jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

    <div class="teacher-profile-container">
        <!-- 프로필 왼쪽 이미지 -->
        <div class="teacher-photo">
            <div class="photo-wrapper" id="photo-wrapper">
                <img id="teacher-img"
                     src="<c:url value='/img/png/teacher-profile-img.png'/>"
                     alt="강사 이미지" class="teacher-img">
                <div class="photo-overlay">
                    <span class="overlay-text">사진 변경</span>
                </div>
            </div>
            <input type="file" id="profile-upload" accept="image/*" style="display:none;">
        </div>
        <!-- 오른쪽 정보 -->
        <div class="teacher-info-box">
            <h2 class="teacher-name">한석원</h2>
            <p class="teacher-email">📧 tjrdnjs@gmail.com</p>
            <div class="teacher-stats">
                <div class="stat-item">
                    <span class="stat-label">강의 수</span>
                    <span class="stat-value">10개</span>
                </div>
                <div class="stat-item">
                    <span class="stat-label">수강생 수</span>
                    <span class="stat-student">200명</span>
                </div>
                <div class="stat-item">
                    <span class="stat-label">평균 평점</span>
                    <span class="stat-rate">⭐ 4.5 / 5.0</span>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", async () => {
        try{
           const res = await fetch("/api/teachers/management/profile");
           const result = await res.json();
           const data = result.data;
           console.log(data);

           const teacherName = document.querySelector(".teacher-info-box .teacher-name")
            const lectureCount = document.querySelector(".teacher-info-box .stat-value")
            const teacherEmail = document.querySelector(".teacher-info-box .teacher-email")
            const teacherRate = document.querySelector(".teacher-info-box .stat-rate")
            const student = document.querySelector(".teacher-info-box .stat-student")

            teacherName.textContent = data.nickname;
            lectureCount.textContent = data.lectureCount;
            teacherEmail.textContent = data.email;
            teacherRate.textContent = data.averageRating;
            student.textContent = data.totalStudent;

            const imgElem = document.querySelector("#teacher-img");
            if (imgElem) {
                imgElem.src = ${not empty sessionScope.profile.profileImage} ?
                    "${fileDomain}/${sessionScope.profile.profileImage.filePath}" : "<c:url value='/img/png/default_image.png'/>";
            }

        } catch (err) {
            console.error("선생님 프로필 로드 실패:", err);
        }
        const photoWrapper = document.querySelector("#photo-wrapper");
        const inputFile = document.querySelector("#profile-upload");
        const allowedExt = ["png", "jpg", "jpeg", "webp"];

        if (photoWrapper && inputFile) {
            photoWrapper.addEventListener("click", () => inputFile.click());

            inputFile.addEventListener("change", async (e) => {
                const file = e.target.files[0];
                if (!file) return;

                // 파일 확장자 검사
                const ext = file.name.split(".").pop().toLowerCase();
                if (!allowedExt.includes(ext)) {
                    alert("PNG, JPG, JPEG, WEBP 파일만 가능합니다.");
                    return;
                }

                // 파일 크기 검사
                const maxSize = 5 * 1024 * 1024;
                if (file.size > maxSize) {
                    alert("파일 크기는 5MB 이하만 가능합니다.");
                    return;
                }

                await uploadTeacherProfileImage(file);
            });
        }
    })

    async function uploadTeacherProfileImage(file) {
        const form = new FormData();
        form.append("profileImage", file);

        try {
            const res = await fetch("/api/members/profile_image", {
                method: "PATCH",
                body: form
            });

            if (!res.ok) {
                alert("업로드 실패 (로그인 만료 혹은 서버 오류)");
                return;
            }

            // 성공 시 미리보기 업데이트
            const imgElem = document.querySelector("#teacher-img");
            const profileElem = document.querySelector(".profile-img");
            imgElem.src = profileElem.src = URL.createObjectURL(file) || "<c:url value='/img/png/default_image.png'/>";
            profileElem.src = profileElem.src = URL.createObjectURL(file) || "<c:url value='/img/png/default_image.png'/>";
            alert("✅ 프로필 이미지가 변경되었습니다.");
        } catch (err) {
            console.error("프로필 업로드 실패:", err);
            alert("❌ 업로드 중 오류가 발생했습니다.");
        }
    }
</script>

<style>
    #content{
        min-height: 400px;
    }
    .teacher-profile-container {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        gap: 60px;
        background: #fff;
        padding: 60px 100px;
        border-radius: 12px;
        width: 1200px;
        margin: 40px auto;
    }

    .teacher-photo {
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align: center;
    }

    .teacher-img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 50%;
        border: 4px solid #2ecc71;
        transition: 0.3s;
    }

    .photo-wrapper {
        position: relative;
        width: 300px;
        height: 300px;
        border-radius: 50%;
        overflow: hidden;
        cursor: pointer;
    }

    .photo-overlay {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0);
        color: #fff;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: 600;
        font-size: 20px;
        opacity: 0;
        transition: background 0.3s, opacity 0.3s;
        border-radius: 50%;
    }

    .photo-wrapper:hover .photo-overlay {
        background: rgba(0, 0, 0, 0.4);
        opacity: 1;
    }

    .overlay-text {
        text-align: center;
    }
    .teacher-info-box {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .teacher-name {
        font-size: 28px;
        font-weight: bold;
        margin-bottom: 10px;
    }

    .teacher-email {
        color: #555;
        font-size: 16px;
        margin-bottom: 25px;
    }

    .teacher-stats {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .stat-item {
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid #eee;
        padding: 10px 0;
        width:70%;
    }

    .stat-label {
        color: #666;
        font-size: 16px;
    }

    .stat-value {
        font-weight: 600;
        color: #2ecc71;
        font-size: 17px;
    }

    .profile-buttons {
        margin-top: 25px;
        display: flex;
        gap: 10px;
    }

    .btn-edit, .btn-dashboard {
        border: none;
        padding: 10px 20px;
        border-radius: 6px;
        font-weight: 600;
        cursor: pointer;
        transition: 0.2s;
    }

    .btn-edit {
        background: #2ecc71;
        color: white;
    }

    .btn-dashboard {
        background: #f4f4f4;
        color: #333;
    }

    .btn-edit:hover {
        background: #27ae60;
    }

    .btn-dashboard:hover {
        background: #ddd;
    }
</style>
