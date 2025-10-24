<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/teacher/management_profile.css'/>">

<div id="content">
    <%@ include file="/WEB-INF/views/page/teacher/navbar.jsp" %>

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
                    <span class="stat-value">200명</span>
                </div>
                <div class="stat-item">
                    <span class="stat-label">평균 평점</span>
                    <span class="stat-value">⭐ 4.5 / 5.0</span>
                </div>
            </div>

            <div class="profile-buttons">
                <button class="btn-edit">정보 수정</button>
                <button class="btn-dashboard">통계 보기</button>
            </div>
        </div>
    </div>
</div>

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
