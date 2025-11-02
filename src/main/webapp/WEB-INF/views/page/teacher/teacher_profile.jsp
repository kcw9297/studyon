<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- ✅ 강사 프로필 영역 -->
<div class="teacher-profile-wrapper">
    <div class="teacher-img-area">
        <c:choose>
            <c:when test="${not empty teacher.profileImagePath}">
                <img src="<c:url value='/upload/${teacher.profileImagePath}'/>" alt="강사 이미지" class="teacher-img">
            </c:when>
            <c:otherwise>
                <img src="<c:url value='/img/png/default_image.png'/>" alt="기본 이미지" class="teacher-img">
            </c:otherwise>
        </c:choose>
    </div>


    <div class="teacher-info-box">
        <div class="teacher-header">
            <h2 class="teacher-name">${teacher.nickname} 강사</h2>
            <span class="teacher-badge">${teacher.subject.value} 전문가</span>
        </div>

        <p class="teacher-desc">
            ${teacher.description}
        </p>

        <div class="teacher-stats">
            <div class="stat-box">
                <div class="stat-label">총 수강생</div>
                <div class="stat-value">
                    <fmt:formatNumber value="${teacher.totalStudents}" type="number"/>명
                </div>
            </div>
            <div class="stat-box">
                <div class="stat-label">총 강의 수</div>
                <div class="stat-value">${teacher.lectureCount}개</div>
            </div>
            <div class="stat-box">
                <div class="stat-label">평균 평점</div>
                <div class="stat-value">${teacher.averageRating}</div>
            </div>
        </div>

        <div class="teacher-detail">
            <p><strong>이메일 :</strong> ${teacher.email}</p>
            <p><strong>담당 과목 :</strong> ${teacher.subject.value}</p>
        </div>
    </div>
</div>

<div class="teacher-lecture-all-btn-box">
    <a href="<c:url value='/lecture/list?keyword=${fn:escapeXml(teacher.nickname)}'/>"
       class="teacher-lecture-all-button">
        🔎 ${teacher.nickname} 강사의 전체 강의 보기
    </a>
</div>
<!-- ✅ 강의 목록 -->
<div class="lecture-section">
    <h3 class="lecture-section-title">🆕 최신 등록 강의</h3>
    <div class="recent-lecture-container">
        <c:forEach var="lecture" items="${recentLectures}">
            <div class="recent-lecture-item">
                <a href="<c:url value='/lecture/detail/${lecture.lectureId}'/>">
                    <c:choose>
                        <c:when test="${not empty lecture.thumbnailImagePath}">
                            <img src="<c:url value='/upload/${lecture.thumbnailImagePath}'/>"
                                 class="recent-lecture-thumbnail" alt="썸네일">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/img/png/default_image.png'/>"
                                 class="recent-lecture-thumbnail" alt="기본 이미지">
                        </c:otherwise>
                    </c:choose>
                    <div class="lecture-title">${lecture.title}</div>
                </a>
            </div>
        </c:forEach>
    </div>

    <h3 class="lecture-section-title">💬 최근 수강평</h3>
    <div class="lecture-comment-box">
        <c:choose>
            <c:when test="${not empty recentReviews}">
                <c:forEach var="review" items="${recentReviews}">
                    <div class="lecture-comment-box-item">
                        <span class="lecture-comment-username">${review.nickname}</span>
                        <span class="lecture-comment-comment">${review.content}</span>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-review-message">등록된 수강평이 없습니다.</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>


<style>
    /* ✅ 전체 레이아웃 */
    body {
        background-color: #f9fafb;
        font-family: 'Pretendard', 'Noto Sans KR', sans-serif;
        color: #222;
    }

    /* ✅ 강사 프로필 박스 */
    .teacher-profile-wrapper {
        display: flex;
        align-items: center;
        gap: 60px;
        background: #fff;
        border-radius: 18px;
        padding: 50px 60px;
        margin: 10px auto;
        width: 90%;
        box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
        transition: all 0.3s ease;
    }

    .teacher-profile-wrapper:hover {
        transform: translateY(-4px);
        box-shadow: 0 10px 28px rgba(0, 0, 0, 0.12);
    }

    /* ✅ 강사 이미지 */
    .teacher-img-area {
        flex: 0 0 260px;
    }

    .teacher-img {
        width: 240px;
        height: 240px;
        border-radius: 50%;
        border: 5px solid #3b82f6;
        object-fit: cover;
        box-shadow: 0 6px 12px rgba(0,0,0,0.1);
    }

    /* ✅ 프로필 내용 */
    .teacher-info-box {
        flex: 1;
    }

    .teacher-header {
        display: flex;
        align-items: center;
        gap: 12px;
        margin-bottom: 12px;
    }

    .teacher-name {
        font-size: 36px;
        font-weight: 800;
    }

    .teacher-badge {
        background: linear-gradient(135deg, #3b82f6, #9333ea);
        color: #fff;
        font-size: 14px;
        font-weight: 600;
        padding: 6px 10px;
        border-radius: 8px;
    }

    /* ✅ 소개문 */
    .teacher-desc {
        font-size: 17px;
        color: #444;
        line-height: 1.7;
        margin-bottom: 25px;
    }

    /* ✅ 통계 박스 */
    .teacher-stats {
        display: flex;
        align-items: center;
        gap: 60px;
        margin-bottom: 30px;
    }

    .stat-box {
        background: #f3f4f6;
        padding: 14px 20px;
        border-radius: 12px;
        text-align: center;
        min-width: 130px;
    }

    .stat-label {
        font-size: 14px;
        color: #777;
    }

    .stat-value {
        font-size: 22px;
        font-weight: 700;
        color: #111;
    }

    /* ✅ 상세정보 */
    .teacher-detail p {
        font-size: 16px;
        margin: 5px 0;
        color: #555;
    }

    /* ✅ 강의 섹션 */
    .lecture-section {
        width: 90%;
        margin: 60px auto;
        margin-top:5px;
    }

    .lecture-section-title {
        font-size: 24px;
        font-weight: 800;
        margin-bottom: 15px;
        color: #1e293b;
        border-left: 6px solid #3b82f6;
        padding-left: 10px;
    }

    /* ✅ 강의 카드 */
    .recent-lecture-container {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(230px, 1fr));
        gap: 24px;
        margin-bottom: 40px;
    }

    .recent-lecture-item {
        background: #fff;
        border-radius: 14px;
        box-shadow: 0 3px 12px rgba(0,0,0,0.05);
        overflow: hidden;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .recent-lecture-item:hover {
        transform: translateY(-5px);
        box-shadow: 0 6px 16px rgba(0,0,0,0.1);
        cursor: pointer;
    }

    .recent-lecture-thumbnail {
        width: 100%;
        height: 150px;
        object-fit: cover;
    }

    .lecture-title {
        padding: 12px 16px;
        font-size: 16px;
        font-weight: 600;
        color: #333;
    }

    /* ✅ 리뷰 섹션 */
    .lecture-comment-box {
        background: #fff;
        border-radius: 16px;
        padding: 20px;
        box-shadow: 0 4px 14px rgba(0,0,0,0.05);
    }

    .lecture-comment-box-item {
        display: flex;
        align-items: baseline;
        gap: 10px;
        padding: 12px 0;
        border-bottom: 1px solid #eee;
    }

    .lecture-comment-box-item:last-child {
        border-bottom: none;
    }

    .lecture-comment-username {
        color: #3b82f6;
        font-weight: 700;
    }

    .lecture-comment-comment {
        color: #333;
        font-size: 16px;
    }

    .teacher-lecture-all-btn-box {
        text-align: left;
        margin-top: 5px;
        margin-bottom: 5px;
        margin-left:
                65px;
    }

    .teacher-lecture-all-button {
        display: inline-block;
        background: linear-gradient(135deg, #3b82f6, #60a5fa);
        color: white;
        font-size: 18px;
        font-weight: 700;
        padding: 14px 28px;
        border-radius: 10px;
        text-decoration: none;
        transition: all 0.2s ease-in-out;
        box-shadow: 0 4px 10px rgba(59, 130, 246, 0.3);
    }

    .teacher-lecture-all-button:hover {
        background: linear-gradient(135deg, #2563eb, #1d4ed8);
        transform: translateY(-2px);
        box-shadow: 0 6px 14px rgba(37, 99, 235, 0.4);
    }


</style>