<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="content">콘텐츠영역
    <div id="empty-box"></div>

    <div class="user-support-chat-container">
        <div class="chat-box">
            <div class="chat-header">
                <h3>고객 상담 센터</h3>
                <span class="chat-agent">상담원: 민지 상담사</span>
            </div>

            <div class="chat-messages">
                <div class="message agent">
                    안녕하세요 😊 StudyOn 고객센터입니다. 무엇을 도와드릴까요?
                </div>
                <div class="message user">
                    안녕하세요! 강의 환불 관련 문의드립니다.
                </div>
                <div class="message agent">
                    네, 어떤 강의인지 알려주시면 도와드리겠습니다.
                </div>
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