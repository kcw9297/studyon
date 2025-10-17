<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>기본 레이아웃 | Oneday OneClass</title>
  <link rel="stylesheet" href="MainTemplate.css">
  <link rel="stylesheet" href="UserSupportChat.css">
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
                      <button>전송</button>
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
