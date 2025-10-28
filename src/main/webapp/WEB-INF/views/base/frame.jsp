<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!doctype html>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <%-- Spring Security CSRF Token --%>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <%-- Title --%>
        <link rel="icon" href="<c:url value='/img/png/icon_title.png'/>" type="image/x-icon">
        <title>STUDY ON</title>

        <%-- JQuery --%>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

        <%-- 공용 CSS --%>
        <link rel="stylesheet" type="text/css" href="<c:url value='/css/base/frame.css'/>">
        <link rel="stylesheet" type="text/css" href="<c:url value='/css/page/mypage/mypage.css'/>">


    </head>

    <%-- body 본문 --%>
    <body>
        <div id="wrap">
            <main id="container">
                <div class="center-box">

                    <%-- 공용 헤더 --%>
                    <header id="header">
                        <jsp:include page="/WEB-INF/views/base/header.jsp" />
                    </header>

                    <%-- 메인 콘텐츠 --%>
                    <div id="content">
                        <jsp:include page="${body}" />
                    </div>

                    <%-- 공용 푸터 --%>
                    <footer id="footer">
                        <jsp:include page="/WEB-INF/views/base/footer.jsp" />
                    </footer>

                </div>
            </main>
        </div>
    </body>

    <%-- 공용 Script --%>
    <script src="<c:url value='/js/base/header.js'/>"></script>
    <script src="<c:url value='/js/base/maintemplate.js'/>"></script>
</html>


