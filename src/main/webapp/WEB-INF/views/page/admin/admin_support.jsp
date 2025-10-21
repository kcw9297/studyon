<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="content">콘텐츠영역
    <div id="empty-box"></div>

    <div>
      <div class="nav-bar">
        <div class="nav-item active">대시보드</div>
        <div class="nav-item">회원관리</div>
        <div class="nav-item">강사관리</div>
        <div class="nav-item">고객상담</div>
        <div class="nav-item">신고관리</div>
        <div class="nav-item">배너관리</div>
        <div class="nav-item">선생님관리</div>
        <div class="nav-item">강의관리</div>
        <div class="nav-item">강의통계</div>
        <div class="nav-item">쿠폰관리</div>
        <div class="nav-item">공지사항등록</div>
      </div>
    </div>

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
          <div class="chat-header">상담 중: 고객 1</div>
          <div class="chat-messages">
            <div class="message user">안녕하세요!</div>
            <div class="message agent">안녕하세요, 무엇을 도와드릴까요?</div>
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
