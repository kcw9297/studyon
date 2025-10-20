<%@ page contentType ="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>선생님 프로필 - StudyOn</title>
    <link rel="stylesheet" href="<c:url value='/css/base/frame.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/page/teacher/management/teacher_profile.css'/>">
    <link rel="icon" href="/images/favicon.ico" type="image/x-icon">
</head>
<body>
<div id="wrap">
    <main id="container">
        <div class="center-box">
            <header id="header">
                <div class="header-container">
                    <div class="header-logo">
                        <img src="<c:url value='/img/png/logo_logout.png'/>" id="logo"alt="로고이미지">
                    </div>
                    <div class="header-search">검색창</div>
                    <div class="header-info">
                        <a href="#" id="loginModalBtn" class="modal">로그인</a>
                    </div>
                </div>
            </header>
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
                    <c:forEach var="bl" items="${bestLectures}">
                    <div class="recent-lecture-item">
                        <img src="<c:url value='/img/png/sample1.png'/>" alt="강의이미지">
                        <div class="lecture-info">
                            <p class="lecture-title">${bl.title}</p>
                            <p class="lecture-info-text">${bl.description}</p>
                            <p class="lecture-info-text">₩<fmt:formatNumber value="${bl.price}" type="number"/></p>
                            <p class="lecture-info-text">⭐<c:out value="${bl.averageRate}" />
                                &#x1F9F8;
                                <c:choose>
                                    <c:when test="${bl.totalStudents >= 10}">
                                        10+
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${bl.totalStudents}" />
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
                    <c:forEach var="rl" items="${recentLectures}">
                    <div class="recent-lecture-item">
                        <img src="<c:url value='/img/png/sample1.png'/>" alt="강의이미지">
                        <div class="lecture-info">
                            <p class="lecture-title">${rl.title}</p>
                            <p class="lecture-info-text">${rl.description}</p>
                            <p class="lecture-info-text">₩<fmt:formatNumber value="${rl.price}" type="number"/></p>
                            <p class="lecture-info-text">⭐<c:out value="${rl.averageRate}" />
                                &#x1F9F8;
                                <c:choose>
                                    <c:when test="${rl.totalStudents >= 10}">
                                        10+
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${rl.totalStudents}" />
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                    </div>
                    </c:forEach>
                </div>

                <div>생생 수강평</div>
                <div class="lecture-comment-box">
                    <c:forEach var="comm" items="${comment}">
                    <div class="lecture-comment-box-item">
                        <div class="lecture-comment-username">${comm.nickname}</div>
                        <div class="lecture-comment-comment">${comm.content}</div>
                    </div>
                    </c:forEach>
                </div>



            </div>
            <footer id="footer">푸터영역</footer>
        </div>

    </main>
</div>
<div id="loginModalBg" class="modal-bg">
    <div class="modal-content">
        <span id="closeModal" class="close">&times;</span>
        <h2>StudyOn</h2>
        <input type="text" placeholder="이메일 입력">
        <input type="password" placeholder="비밀번호 입력">
        <div class="divider-line"></div>
        <button>로그인</button>
        <div class="resister-box">
            <a href="#">회원가입</a>
            <a href="#">아이디(이메일) 찾기</a>
            <a href="#">비밀번호 찾기</a>
        </div>
        <div class="divider-line"></div>
        <div class="social-login-box">
            <div class="social-icons">
                <a href="#">
                    <img src="<c:url value='/img/png/kakao.png'/>" alt="카카오 로그인">
                </a>
                <a href="#">
                    <img src="<c:url value='/img/png/google.png'/>" alt="구글 로그인">
                </a>
                <a href="#">
                    <img src="<c:url value='/img/png/kakao.png'/>" alt="구글 로그인">
                </a>
            </div>
        </div>

    </div>
</div>
<script src="<c:url value='/js/base/Maintemplate.js'/>"></script>
</body>
</html>
