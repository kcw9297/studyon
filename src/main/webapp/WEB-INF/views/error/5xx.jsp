<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%-- Title --%>
    <link rel="icon" href="<c:url value='/img/png/icon_title.png'/>" type="image/x-icon">
    <title>STUDY ON</title>

    <link rel="stylesheet" href="<c:url value='/css/base/MainTemplate.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/error/error.css'/>">

</head>

<body>
  <div id="wrap">
    <main id="container">
        <div class="center-box">
            <div id="content">
                <div class="error-container">
                  <div class="error-box">
                    <img src="<c:url value='/img/png/logo_login.png'/>" class="error-logo">
                    <h1>500 ERROR</h1>
                    <p>죄송합니다. 서버 오류가 발생했습니다.<br>
                      접근하신 페이지의 요청 처리에 실패했습니다.<br>
                      잠시 후에 다시 시도해 주시길 바랍니다.</p>
                    <button class="home-btn" onclick="window.location.href='/'">홈으로</button>
                  </div>
              </div>
            </div>
        </div>
    </main>
  </div>
</body>
</html>
