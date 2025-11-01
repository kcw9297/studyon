<%@ page contentType ="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- ✅ 강사 프로필 영역 -->
<div class="teacher-profile-wrapper" data-teacher-id="${teacher.teacherId}">
    <div class="teacher-img-area">
        <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="강사이미지" class="teacher-img">
    </div>

    <div class="teacher-info-box">
        <div class="teacher-header">
            <h2 class="teacher-name">이정우 강사</h2>
            <span class="teacher-badge">수학 전문가</span>
        </div>
        <p class="teacher-desc">
            10년 경력의 수능 수학 전문 강사로, 개념부터 실전까지 체계적인 학습을 돕습니다.<br>
            인프런 베스트셀러 강의 <strong>“수학의 정석 실전편”</strong>으로 누적 수강생 12,000명을 달성했습니다.
        </p>

        <div class="teacher-stats">
            <div class="stat-box">
                <div class="stat-label">총 수강생</div>
                <div class="stat-value">12,384명</div>
            </div>
            <div class="stat-box">
                <div class="stat-label">총 강의 수</div>
                <div class="stat-value">8개</div>
            </div>
        </div>

        <div class="teacher-detail">
            <p><strong>전공 :</strong> 서울대학교 수학교육과</p>
            <p><strong>경력 :</strong> 메가스터디 온라인 강사 6년 / 현 StudyOn 전임 강사</p>
            <p><strong>담당 과목 :</strong> 수학 I · II / 미적분 / 확률과 통계</p>
        </div>
    </div>
</div>
<button class="teacher-lecture-all-button">전체 강의 보기</button>
<style>

    .teacher-lecture-all-button{
        border:2px solid black;
        padding:5px;
        font-weight:bold;
        font-size:18px;
        background-color:orange;
    }

    /* ✅ 강사 프로필 */
    .teacher-profile-wrapper {
        display: flex;
        align-items: center;
        gap: 50px;
        background: #fff;
        border-radius: 16px;
        padding: 40px 50px;
        width:100%;
    }

    .teacher-img-area {
        flex: 0 0 auto;
    }

    .teacher-img {
        border: 6px solid #3b82f6;
        width: 240px;
        height: 240px;
        border-radius: 50%;
        object-fit: cover;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
    }


    .teacher-info-box {
        flex: 1;
    }
    .teacher-header {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 10px;
    }

    .teacher-name {
        font-size: 32px;
        font-weight: 700;
        color: #222;
    }

    .teacher-badge {
        background: #007bff;
        color: #fff;
        font-size: 14px;
        font-weight: 600;
        padding: 6px 10px;
        border-radius: 6px;
    }

    /* 소개문 */
    .teacher-desc {
        font-size: 17px;
        color: #444;
        line-height: 1.6;
        margin-bottom: 25px;
    }

    /* 통계 정보 */
    .teacher-stats {
        display: flex;
        align-items: center;
        gap: 40px;
        margin-bottom: 25px;
    }

    .stat-box {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
    }

    .stat-label {
        font-size: 14px;
        color: #777;
    }

    .stat-value {
        font-size: 20px;
        font-weight: 700;
        color: #111;
    }

    .teacher-name {
        font-size: 30px;
        font-weight: 700;
        margin-bottom: 10px;
    }

    .teacher-desc {
        font-size: 18px;
        margin-bottom: 20px;
        color: #444;
    }

    .teacher-detail p {
        margin: 5px 0;
        font-size: 16px;
        color: #666;
    }

    /* ✅ 강의 목록 공통 */
    .lecture-section-title {
        font-size: 24px;
        font-weight: bold;
        margin: 40px 0 15px 15px;
    }

    .recent-lecture-container {
        display: grid;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;
        box-sizing: border-box;
        background-color: white;
        margin-bottom:30px;
        padding: 10px;
    }

    .recent-lecture-item {
        width: 260px;
        height: auto;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        box-shadow: inset 0 0 0 2px rgba(0, 0, 0, 0.4);
    }

    .recent-lecture-thumbnail {
        width: 100%;
        height: 180px;
        object-fit: cover;
        border-bottom: 1px solid #ddd;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }


    /* 배너 */
    .main-banner-container {
        position: relative;
        width: 100%;
        height: 450px;
        overflow: hidden;
        border-radius: 10px;
        margin-bottom: 20px;
        margin-top:40px;
    }

    .main-banner {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
        opacity: 0;
        transition: opacity 1.5s ease-in-out;
    }

    .main-banner.active {
        opacity: 1;
    }

    /* lecture-item 관련 css*/

    .recent-lecture-container {
        display: grid;
        height:auto;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;             /* ✅ 1420px 고정 대신 부모에 맞춤 */
        box-sizing: border-box;  /* ✅ padding/gap 계산 포함 */
        background-color: rgb(255, 255, 255);
    }


    .recent-lecture-item {
        width: 260px;            /* 고정 폭 (5등분용) */
        height: auto;           /* 고정 높이 */
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 10px;
        display: flex;
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

    .lecture-comment-box{
        width:100%;
        height:100%;
        background-color: rgb(255, 255, 255);
    }

    .lecture-comment-box-item{
        display:flex;
        flex-direction:row;
        border: 2px solid black;
        padding:3px;
        margin-bottom:3px;
        border-radius:10px;
    }

    .lecture-comment-username{
        font-size:20px;
        font-weight:bold;
        margin-right:10px;
    }

    .lecture-comment-comment{
        font-size:20px;
        font-weight:bold;
    }

    .recomment-lecture-title{
        font-size:30px;
        font-weight:bold;
        text-align: left;
        margin-top:10px;
        margin-bottom:10px;
    }

    .lecture-comment-username{
        color: rgb(131, 212, 129);
    }

    .recent-lecture-thumbnail {
        width: 100%;
        height: 180px;
        object-fit: cover;
        border-bottom: 1px solid #ddd;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }

</style>


<script>

    document.addEventListener("DOMContentLoaded", async () => {
            const bestRes = await fetch(`/api/teachers/profile/bestLecture?teacherId=${teacherId}`);
            const bestData = await bestRes.json();
            console.log("🔥 [베스트 강의 데이터]", bestData);

            // ✅ 2️⃣ 최신 강의 불러오기
            const recentRes = await fetch(`/api/teachers/profile/recentLecture?teacherId=${teacherId}`);
            const recentData = await recentRes.json();
            console.log("🆕 [최신 강의 데이터]", recentData);

            // ✅ 3️⃣ 최근 수강평 불러오기
            const reviewRes = await fetch(`/api/teachers/reviews/teacher/${teacherId}`);
            const reviewData = await reviewRes.json();
            console.log("💬 [최근 수강평 데이터]", reviewData);

            const profileRes = await fetch(`/api/teachers/profile/detail/${teacherId}`);
            const profileData = await profileRes.json();
            console.log("👩‍🏫 [선생님 프로필 데이터]", profileData);
    })
</script>

