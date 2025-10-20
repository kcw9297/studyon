<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="header-container">
    <div class="header-logo">
        <img src="<c:url value='${isLogin ? "/img/png/logo_login.png" : "/img/png/logo_logout.png"}'/>" id="logo" alt="image">
    </div>
    <div class="header-search">

        <div class="search-box">
            <input type="text" id="searchInput" placeholder="검색어를 입력하세요">
            <button id="searchBtn"><img src="<c:url value='/img/png/search.png'/>" class="svg-search" alt="image"></button>
        </div>
        <div id="searchResults" class="search-results">
            <div class="search-result-item">수학 강의</div>
            <div class="search-result-item">영어 회화</div>
            <div class="search-result-item">국어 문법</div>
        </div>

    </div>
    <div class="header-info">
        <button>
            <img src="<c:url value='/img/png/list.png'/>" class="svg-list" alt="리스트">
        </button>

        <c:if test="${not isLogin}">
            <a href="<c:url value='/login'/>" id="loginModalBtn" class="modal">
                <img src="<c:url value='/img/png/login.png'/>" class="svg-login" alt="로그인">
            </a>
        </c:if>

        <c:if test="${isLogin}">
            <a href="#" id="loginModalBtn" onclick="logout()" class="modal">
                <img src="<c:url value='/img/png/logout.png'/>" class="svg-login" alt="로그아웃">
            </a>
        </c:if>
    </div>
</div>
