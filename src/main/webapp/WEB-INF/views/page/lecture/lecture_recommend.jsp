<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_recommend.css'/>">

<div id="content">
    <div id="empty-box"></div>
    <div class="main-container">
      <div class="sidebar-container">
        <div class="recommend-nav">
            <a href="<c:url value='/lecture/recommend/MATH'/>" class="nav-item">수학</a>
            <a href="<c:url value='/lecture/recommend/ENGLISH'/>" class="nav-item">영어</a>
            <a href="<c:url value='/lecture/recommend/KOREAN'/>" class="nav-item">국어</a>
            <a href="<c:url value='/lecture/recommend/SCIENCE'/>" class="nav-item">과학탐구</a>
            <a href="<c:url value='/lecture/recommend/SOCIAL'/>" class="nav-item">사회탐구</a>
      </div>
    </div>
    <div class="main-content-container">
      <div class="recomment-lecture-title">
        ${subject.value} 주간 인기/추천 강의
      </div>

        <div class ="recent-lecture-container">
        <c:forEach var="bestLecture" items="${bestLectures}">
          <div class="recent-lecture-item">
            <img src="/img/png/sample1.png" alt="강의이미지">
            <div class="lecture-info">
              <p class="lecture-title">${bestLecture.title}</p>
              <p class="lecture-info-text">${bestLecture.nickname}</p>
              <p class="lecture-info-text">₩<fmt:formatNumber value="${bestLecture.price}" type="number"/></p>
                <p class="lecture-info-text"><!--⭐<c:out value="${bestLecture.averageRate}" />-->
                    &#x1F9F8;
                    <c:choose>
                        <c:when test="${bestLecture.totalStudents >= 10}">
                            10+
                        </c:when>
                        <c:otherwise>
                            <c:out value="${bestLecture.totalStudents}" />
                        </c:otherwise>
                    </c:choose>
                </p>
                <!--🧸 띄어쓰기 없이 출력 안돼서 &#x1F9F8; html 엔티티로 교체 -->
            </div>
          </div>
        </c:forEach>
      </div>
        <div class="recomment-lecture-title">
            최근 수강평
        </div>
        <c:forEach var="review" items="${reviews}">
            <div class="lecture-comment-box">
              <div class="lecture-comment-box-item">
                <div class="lecture-comment-username">${review.nickname}</div>
                <div class="lecture-comment-comment">${review.content}</div>
             </div>
            </div>
        </c:forEach>
        <div class="recomment-lecture-title">
            요새 뜨는 강의
        </div>

        <div class ="recent-lecture-container">
            <c:forEach var="recentLecture" items="${recentLectures}">
              <div class="recent-lecture-item">
                <img src="/img/png/sample1.png" alt="강의이미지">
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
        </div>
    </div>
</div>

