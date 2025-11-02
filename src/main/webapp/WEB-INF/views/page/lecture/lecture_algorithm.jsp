<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_detail.css'/>">
</head>
<body>
<section class="algorithm">
    <div class="algorithm-title">
        추천 강의
        <div class="algorithm-over">
            <button class="algorithm-no">
                <img src="<c:url value='/img/png/over1.png'/>">
            </button>
            <button class="algorithm-yes">
                <img src="<c:url value='/img/png/over2.png'/>">
            </button>
        </div>
    </div>
    <div class="algorithm-list">
        <c:forEach var="lecture" items="${recommendedByTeacher}">
            <a class="algorithm-item" href="/lecture/detail/${lecture.lectureId}">
                <div class="algorithm-thumbnail">
                    <c:choose>
                        <c:when test="${not empty lecture.thumbnailImagePath}">
                            <img src="${fileDomain}/${lecture.thumbnailImagePath}" alt="썸네일 이미지">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/img/png/thumbnail.png'/>" alt="기본 이미지">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="algorithm-lecture">${lecture.title}</div>
                <div class="algorithm-teacher">${lecture.teacherNickname} 강사</div>
                <div class="algorithm-price"><fmt:formatNumber value="${lecture.price}" type="number"/>원</div>
                <div class="algorithm-report">
                    <div class="algorithm-review">
                        <div class="algorithm-star">★</div>
                        <div class="algorithm-score"><fmt:formatNumber value="${lecture.averageRate}" pattern="#0.0"/></div>
                        <div class="algorithm-count">(<fmt:formatNumber value="${reviewCountMap[lecture.lectureId]}" type="number"/>)</div>
                    </div>
                    <div class="algorithm-student">
                        <div class="algorithm-member">
                            <img src="<c:url value='/img/png/student.png'/>" alt="학생">
                        </div>
                        <div class="algorithm-total"><fmt:formatNumber value="${lecture.totalStudents}" type="number"/></div>
                    </div>
                </div>
            </a>
        </c:forEach>
        <c:forEach var="lecture" items="${recommendedBySubject}">
            <a class="algorithm-item" href="/lecture/detail/${lecture.lectureId}">
                <div class="algorithm-thumbnail">
                    <c:choose>
                        <c:when test="${not empty lecture.thumbnailImagePath}">
                            <img src="${fileDomain}/${lecture.thumbnailImagePath}" alt="썸네일 이미지">
                        </c:when>
                        <c:otherwise>
                            <img src="<c:url value='/img/png/thumbnail.png'/>" alt="기본 이미지">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="algorithm-lecture">${lecture.title}</div>
                <div class="algorithm-teacher">${lecture.teacherNickname} 강사</div>
                <div class="algorithm-price"><fmt:formatNumber value="${lecture.price}" type="number"/>원</div>
                <div class="algorithm-report">
                    <div class="algorithm-review">
                        <div class="algorithm-star">★</div>
                        <div class="algorithm-score"><fmt:formatNumber value="${lecture.averageRate}" pattern="#0.0"/></div>
                        <div class="algorithm-count">(<fmt:formatNumber value="${reviewCountMap[lecture.lectureId]}" type="number"/>)</div>
                    </div>
                    <div class="algorithm-student">
                        <div class="algorithm-member">
                            <img src="<c:url value='/img/png/student.png'/>" alt="학생">
                        </div>
                        <div class="algorithm-total"><fmt:formatNumber value="${lecture.totalStudents}" type="number"/></div>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
    </div>
</section>
</body>
</html>