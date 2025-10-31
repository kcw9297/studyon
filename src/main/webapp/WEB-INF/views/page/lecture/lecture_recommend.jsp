<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_recommend.css'/>">

<div id="content">
    <div class="main-container">
      <div class="sidebar-container">
          <div class="recommend-nav">
              <a href="<c:url value='/lecture/list'/>" class="nav-item">전체</a>
              <c:forEach var="subject" items="${subjects}">
                  <a href="<c:url value='/lecture/recommend/${subject}'/>" class="nav-item">${subject.value}</a>
              </c:forEach>
          </div>
    </div>
    <div class="main-content-container">
        <div id="lecturePage" data-subject="${subject.name()}">
          <div class="recomment-lecture-title">
            ${subject.value} 주간 인기/추천 강의
          </div>
                <div class="recent-lecture-container">
                    <!-- best_reviews.js -->
                </div>
                <div class="recomment-lecture-title">
                    최근 수강평
                </div>

                <div class="lecture-comment-box">
                    <!-- ✅ 비동기로 강의를 채울 빈 컨테이너
                    recent_reviews.js-->
                </div>

                <div class="recomment-lecture-title">
                    요새 뜨는 강의
                </div>
                <div class="recent-lecture-container">
                    <!-- recent_lecture.js -->
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='/js/page/lecture/recent_lecture.js'/>"></script>
<script src="<c:url value='/js/page/lecture/best_lecture.js'/>"></script>
<script src="<c:url value='/js/page/lecture/recent_reviews.js'/>"></script>
<script src="<c:url value='/js/page/lecture/lecture_recommend.js'/>"></script>