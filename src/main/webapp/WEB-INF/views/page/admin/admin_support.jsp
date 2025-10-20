<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>기본 레이아웃 | Oneday OneClass</title>
  <link rel="stylesheet" href="MainTemplate.css">
  <link rel="stylesheet" href="AdminSupport.css">
  <link rel="icon" href="/images/favicon.ico" type="image/x-icon">
</head>
<body>
  <div id="wrap">
    <main id="container">
        <div class="center-box">
            <header id="header">
                <div class="header-container">
                    <div class="header-logo">
                        <img src="resources/logo.png" id="logo"alt="로고이미지">
                    </div>
                    <div class="header-search">
                      <input type="text" id="searchInput" placeholder="검색어를 입력하세요">
                      <button id="searchBtn">검색</button>
                    </div>
                    <div class="header-info">
                      <a href="#" id="loginModalBtn" class="modal">로그인</a>
                    </div>
                </div>
            </header>
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
                    <div class="chat-room-list">
                      <h3>채팅 목록</h3>
                      <div class="toggle-box">
                        <button class="support-toggle-button">전체</button>
                        <button class="support-toggle-button">대기중</button>
                      </div>
                      <div class="room">김승완</div>
                      <div class="room">고창우</div>
                      <div class="room">양진석</div>
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
              <img src="resources/kakao.png" alt="카카오 로그인">
          </a>
          <a href="#">
              <img src="resources/google.png" alt="구글 로그인">
          </a>
          <a href="#">
              <img src="resources/kakao.png" alt="구글 로그인">
          </a>
        </div>
      </div>

    </div>
  </div>
  <script src="Maintemplate.js"></script>
</body>
</html>
