<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="header-container">
    <div class="header-logo" onclick="location.href='/'">
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

        <div style="gap:10px;">
            <c:if test="${isLogin}">
                <c:if test="${sessionScope.profile.role.role eq 'ADMIN'}">
                    <a style="border:2px solid black;" href="/admin"> 관리자 </a>
                </c:if>
            </c:if>
            <c:if test="${not isLogin}">
                <div class="register-tag">
                    <a href="/join"> 회원가입</a>
                </div>
            </c:if>
        </div>

        <c:if test="${isLogin and not empty sessionScope.profile and sessionScope.profile.role.role ne 'ADMIN'}">
            <div class="profile-img-div" onclick="location.href='${sessionScope.profile.role.role eq 'STUDENT' ? '/mypage/account' : '/teacher/management/profile'}'">
                <c:if test="${not empty sessionScope.profile.profileImage}">
                    <img src="${fileDomain}/${sessionScope.profile.profileImage.filePath}" class="profile-img" alt="프로필 사진">
                </c:if>
                <c:if test="${empty sessionScope.profile.profileImage}">
                    <img src="<c:url value='/img/png/default_member_profile_image.png'/>"; class="profile-img" alt="프로필 사진">
                </c:if>
            </div>
        </c:if>

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

<style>
    .profile-img{

        height:90%;
        width:45px;
        border-radius:90px;
    }
    .profile-img-div{
        display:flex;
        align-items: center;
        justify-content: center;
        height:85%;
        width:auto;
        cursor:pointer;


    }
    .register-tag {
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 8px 18px;                 /* ⬆ 여백 살짝 늘림 */
        border: 3px solid #551A8B;         /* 보라 계열 포인트 컬러 */
        border-radius: 25px;
        background-color: white;
        color: #551A8B;
        font-weight: 600;
        font-size: 15px;
        white-space: nowrap;
        height: 40px;                      /* ✅ header 높이에 맞게 조정 */
        cursor: pointer;
        transition: all 0.25s ease;
        box-shadow: 0 1px 3px rgba(0,0,0,0.05);
    }

    .register-tag:hover {
        background-color: #551A8B;
        color: white;
        transform: translateY(-2px);       /* 살짝 떠오르는 효과 */
        box-shadow: 0 3px 6px rgba(0,0,0,0.15);
    }

    .register-tag:active {
        transform: translateY(0);
        box-shadow: 0 1px 3px rgba(0,0,0,0.1);
    }

    .register-tag a {
        text-decoration: none;
        color: inherit;
        white-space: nowrap;
    }

</style>
