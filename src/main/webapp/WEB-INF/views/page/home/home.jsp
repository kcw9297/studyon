<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="home-nav">
    <a href="<c:url value='/lecture/recommend/MATH'/>">수학</a>
    <a href="<c:url value='/lecture/recommend/ENGLISH'/>">영어</a>
    <a href="<c:url value='/lecture/recommend/KOREAN'/>">국어</a>
    <a href="<c:url value='/lecture/recommend/SCIENCE'/>">과학탐구</a>
    <a href="<c:url value='/lecture/recommend/SOCIAL'/>">사회탐구</a>
</div>
<div class="main-banner-container">
  <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="메인비주얼이미지" class="main-banner">
</div>


<label class="lecture-section-title">최근 등록된 강의</label>
    <div class ="recent-lecture-container">
        <c:forEach var="recentLecture" items="${recentLecture}">
          <div class="recent-lecture-item">
            <img src="<c:url value='/img/png/sample1.png'/>" alt="강의이미지">
            <div class="lecture-info">
              <p class="lecture-title">${recentLecture.title}</p>
              <p class="lecture-info-text">${recentLecture.nickname}</p>
              <p class="lecture-info-text">₩<fmt:formatNumber value="${recentLecture.price}" type="number"/></p>
                <p class="lecture-info-text"><!--⭐<c:out value="${recentLecture.averageRate}" />-->
                    &#x1F9F8;
                    <c:choose>
                        <c:when test="${recentLecture.totalStudents >= 10}">
                            10+
                        </c:when>
                        <c:otherwise>
                            <c:out value="${recentLecture.totalStudents}" />
                        </c:otherwise>
                    </c:choose>
                </p>
            <!--🧸 띄어쓰기 없이 출력 안돼서 &#x1F9F8; html 엔티티로 교체 -->
            </div>
          </div>
        </c:forEach>
    </div>
<label class="lecture-section-title">최근 인기 강의</label>
<div class ="recent-lecture-container">
    <c:forEach var="popularLecture" items="${popularLecture}">
        <div class="recent-lecture-item">
            <img src="<c:url value='/img/png/sample1.png'/>" alt="강의이미지">
            <div class="lecture-info">
                <p class="lecture-title">${popularLecture.title}</p>
                <p class="lecture-info-text">${popularLecture.nickname}</p>
                <p class="lecture-info-text">₩<fmt:formatNumber value="${popularLecture.price}" type="number"/></p>
                <p class="lecture-info-text"><!--⭐<c:out value="${popularLecture.averageRate}" />-->
                    &#x1F9F8;
                    <c:choose>
                        <c:when test="${popularLecture.totalStudents >= 10}">
                            10+
                        </c:when>
                        <c:otherwise>
                            <c:out value="${popularLecture.totalStudents}" />
                        </c:otherwise>
                    </c:choose>
                </p>
                <!--🧸 띄어쓰기 없이 출력 안돼서 &#x1F9F8; html 엔티티로 교체 -->
            </div>
        </div>
    </c:forEach>
</div>

<style>
    .home-nav {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        gap: 20px;
        padding: 10px 0;
        position: sticky;
        top: 0;
        z-index: 100;
        margin-left:15px;
    }

    /* ✅ 링크 기본 스타일 */
    .home-nav a {
        position: relative;
        color: #333;
        font-size: 18px;
        font-weight: 600;
        text-decoration: none;
        transition: color 0.3s ease;
    }

    /* ✅ hover 시 밑줄 애니메이션 효과 */
    .home-nav a::after {
        content: '';
        position: absolute;
        width: 0%;
        height: 2px;
        bottom: -5px;
        left: 50%;
        background-color: #007bff;
        transition: all 0.3s ease;
        transform: translateX(-50%);
    }

    .home-nav a:hover {
        color: #007bff;
    }

    .home-nav a:hover::after {
        width: 100%;
    }

    /* ✅ 반응형: 작은 화면에서는 네비바 스크롤 */
    @media (max-width: 768px) {
        .home-nav {
            flex-wrap: nowrap;
            overflow-x: auto;
            gap: 25px;
            padding: 15px;
        }

        .home-nav a {
            font-size: 16px;
            flex-shrink: 0;
        }
    }

    .recent-lecture-container {
        display: grid;
        grid-template-columns: repeat(5, 1fr);
        gap: 20px;
        width: 100%;
        height: auto;
        box-sizing: border-box;
        background-color: white;
        margin-bottom:10px;
    }

    .recent-lecture-item {
        width: 260px;
        height: auto;
        border:1px solid #2c3e50;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        box-sizing: border-box;
    }

    .recent-lecture-item:hover {
        transform: translateY(-5px);
        cursor: pointer;
    }

    .lecture-section-title {
        font-size: 24px;
        font-weight: bold;
    }

    .lecture-info{
        margin-left:5px;
    }

    .main-banner {
        width: 100%;
        height: 100%;
    }

    .main-banner-container {
        margin-bottom: 20px;
    }
</style>

