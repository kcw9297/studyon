<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<section class="likes">
    <div class="mypage-title">관심 목록</div>
    <div>
        <jsp:include page="likes_navbar.jsp"/>
        <div class="likes-list">
        <c:choose>
            <c:when test="${not empty likeList}">
                <c:forEach var="like" items="${likeList}">
                    <a class="likes-item" href="/lecture/detail/${like.lecture.lectureId}">
                        <div class="likes-thumbnail">
                            <img src="<c:url value='${like.lecture.thumbnailFile}'/>">
                            <form class="likes-deleteIcon" action="/mypage/likes/delete/${like.lecture.lectureId}" method="get">
                                <input type="hidden" name="subject" value="${selectedSubject}">
                                <button type="submit">
                                    <img src="<c:url value='/img/png/delete.png'/>">
                                </button>
                            </form>
                        </div>
                        <div class="likes-lecture">${like.lecture.title}</div>
                        <div class="likes-teacher">${like.lecture.teacher.member.nickname} 강사</div>
                        <div class="likes-price"><fmt:formatNumber value="${like.lecture.price}" type="number"/>원</div>
                        <div class="likes-report">
                            <div class="likes-review">
                                <div class="likes-star">★</div>
                                <div class="likes-score"><fmt:formatNumber value="${like.lecture.averageRate}" pattern="#0.0"/></div>
                                <div class="likes-count">(<fmt:formatNumber value="${like.lecture.likeCount}" type="number"/>)</div>
                            </div>
                            <div class="likes-student">
                                <div class="likes-member">
                                    <img src="<c:url value='/img/png/student.png'/>">
                                </div>
                                <div class="likes-total"><fmt:formatNumber value="${like.lecture.totalStudents}" type="number"/></div>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-likes">아직 관심목록이 없습니다.</div>
            </c:otherwise>
        </c:choose>
        </div>
    </div>
</section>
