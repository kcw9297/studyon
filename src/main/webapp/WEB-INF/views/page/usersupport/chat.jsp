<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/user_support/chat.css'/>">
<div id="content">

    <div class="user-support-chat-container">
        <div class="chat-box">
            <div class="chat-header">
                <h3>고객 상담 센터</h3>
                <span class="chat-agent">오늘도 좋은하루 되세요</span>
            </div>

            <div class="chat-messages">
            </div>

            <div class="chat-input">
                <input type="text" placeholder="메시지를 입력하세요..." />
                <button id="send">전송</button>
            </div>
        </div>
    </div>
</div>

<%--<script>--%>
<%--    const loginMemberId = ${sessionScope.member.memberId};--%>
<%--</script>--%>

<script src="<c:url value='/js/page/user_support/chat.js'/>"></script>