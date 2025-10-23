<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/admin_support.css'/>">

<div id="content">
    <div id="empty-box"></div>
    <jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
        <jsp:param name="active" value="support"/>
    </jsp:include>
    <div class="Support-Container">
      <div class="chat-layout">
        <!-- 왼쪽: 채팅방 목록 -->
          <div class="chat-room-list" id="room-container">
              <h3>채팅 목록</h3>
              <div class="toggle-box">
                  <button class="support-toggle-button active">전체</button>
                  <button class="support-toggle-button">대기중</button>
              </div>

              <!-- ✅ DB에서 가져온 rooms 출력 -->
              <c:forEach var="room" items="${rooms}">
                  <div class="room" data-room-id="${room.chatRoomId}">
                          ${room.roomName}
                  </div>
              </c:forEach>
          </div>

        <!-- 오른쪽: 채팅 내용 -->
        <div class="chat-window">
          <div class="chat-header">채팅방을 선택해주세요</div>
          <div class="chat-messages">
            <%--<div class="message user"></div>--%>
            <%--<div class="message agent"></div>--%>

          </div>
          <div class="chat-input">
            <input type="text" placeholder="메시지를 입력하세요..." />
            <button>전송</button>
          </div>
        </div>
      </div>
    </div>
</div>

<script src="<c:url value='/js/page/admin/admin_support.js'/>"></script>
