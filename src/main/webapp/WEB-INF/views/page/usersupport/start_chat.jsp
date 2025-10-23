<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/user_support/start_chat.css'/>">
<div id="empty-box"></div>
<div class="user-support-container">
  <div class="support-inner">
    <h2 class="support-title">💬 고객 상담을 시작해보세요</h2>
    <p class="support-desc">궁금한 점이 있으신가요? 아래 버튼을 눌러 상담을 시작해주세요.</p>
    <button id="support-button" class="support-start-button">상담 시작하기</button>
  </div>
</div>

<script src="<c:url value='/js/page/user_support/start_chat.js'/>"></script>