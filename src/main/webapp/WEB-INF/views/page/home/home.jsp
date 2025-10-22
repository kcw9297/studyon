<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="empty-box"></div>
<div class="main-banner-container">
  <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="메인비주얼이미지" class="main-banner">
</div>
<div class="nav">
    <a href="<c:url value='/lecture/recommend/MATH'/>">수학</a>
    <a href="<c:url value='/lecture/recommend/ENGLISH'/>">영어</a>
    <a href="<c:url value='/lecture/recommend/KOREAN'/>">국어</a>
    <a href="<c:url value='/lecture/recommend/SCIENCE'/>">과학탐구</a>
    <a href="<c:url value='/lecture/recommend/SOCIAL'/>">사회탐구</a>
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
                <p class="lecture-info-text"><!--⭐<c:out value="${bl.averageRate}" />-->
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



