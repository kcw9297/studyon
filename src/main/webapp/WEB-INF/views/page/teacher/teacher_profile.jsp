<%@ page contentType ="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/teacher/management/teacher_profile.css'/>">

    <div id="content">콘텐츠영역
        <div id="empty-box"></div>
        <div class="teacher-img-area">
            <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="강사이미지" class="teacher-img">
        </div>
        <div>
            <button>모든 강의 보기</button>
        </div>
        <div>Best 강의 추천</div>
        <div class ="recent-lecture-container">
            <c:forEach var="bestLecture" items="${bestLectures}">
                <div class="recent-lecture-item">
                    <img src="<c:url value='/img/png/sample1.png'/>" alt="강의이미지">
                    <div class="lecture-info">
                        <p class="lecture-title">${bestLecture.title}</p>
                        <p class="lecture-info-text">${bestLecture.description}</p>
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
        <div>최근 등록된 강좌</div>
        <div class ="recent-lecture-container">
            <c:forEach var="recentLecture" items="${recentLectures}">
                <div class="recent-lecture-item">
                    <img src="<c:url value='/img/png/sample1.png'/>" alt="강의이미지">
                    <div class="lecture-info">
                        <p class="lecture-title">${recentLecture.title}</p>
                        <p class="lecture-info-text">${recentLecture.description}</p>
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
                    </div>
                </div>
            </c:forEach>
        </div>

        <div>생생 수강평</div>
        <div class="lecture-comment-box">
            <c:forEach var="comment" items="${comment}">
                <div class="lecture-comment-box-item">
                    <div class="lecture-comment-username">${comment.nickname}</div>
                    <div class="lecture-comment-comment">${comment.content}</div>
                </div>
            </c:forEach>
        </div>

