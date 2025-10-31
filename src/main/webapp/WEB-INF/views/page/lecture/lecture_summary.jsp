<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_detail.css'/>">
</head>
<body>
<section class="summary">
    <div class="summary-top">
        <div class="summary-text">
            <div class="summary-lecture">
                <div class="summary-tag" data-en="<c:out value='${lecture.subject}'/>"><c:out value="${lecture.subject}"/></div>
                <div class="summary-name">${lecture.title}</div>
                <div class="summary-explain">
                    <p>${lecture.description}</p>
                </div>
            </div>
            <div class="summary-report">
                <div class="summary-review">
                    <div class="summary-star">★</div>
                    <div class="summary-score">&nbsp;<fmt:formatNumber value="${lecture.averageRate}" pattern="#0.0"/>&nbsp;</div>
                    <div class="summary-count">수강평 <fmt:formatNumber value="${reviewCount}" type="number"/>개</div>
                </div>
                <div class="summary-student">
                    <div class="summary-member">
                        <img src="<c:url value='/img/png/student.png'/>" alt="수강생">
                    </div>
                    <div class="summary-total">&nbsp;수강생 <fmt:formatNumber value="${lecture.totalStudents}" type="number"/>명</div>
                </div>
            </div>
        </div>
        <div class="summary-thumbnail">
            <c:choose>
                <c:when test="${not empty lecture.thumbnailFile and lecture.thumbnailFile.fileId ne 0}">
                </c:when>
                <c:otherwise>
                    <img src="<c:url value='/img/png/thumbnail.png'/>" alt="기본 이미지">
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="summary-bottom">
        <div class="summary-introduce">
            <div class="summary-category">
                <p>강사 :</p>
                <p>커리큘럼 :</p>
                <p>강의 시간 :</p>
                <p>난이도 :</p>
            </div>
            <div class="summary-item">
                <p>${teacher.member.nickname}</p>
                <p>${lecture.videoCount}강</p>
                <p>${lecture.totalDuration}시간</p>
                <p data-en="<c:out value='${lecture.difficulty}'/>"><c:out value="${lecture.difficulty}" /></p>
            </div>
        </div>
        <div class="summary-pay">
            <div class="summary-money"><fmt:formatNumber value="${lecture.price}" type="number"/>원</div>
            <div class="summary-buttons">
                <button class="summary-like" data-lecture-id="${lecture.lectureId}" data-member-id="${loginMember.memberId}">
                    <img src="/img/png/like1.png" alt="좋아요">
                    <span class="like-count">${lecture.likeCount}</span>
                </button>
                <button class="summary-purchase">
                    바로 구매하기
                </button>
            </div>
        </div>
    </div>
</section>
</body>
</html>