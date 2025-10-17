<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>기본 레이아웃 | Oneday OneClass</title>
  <link rel="stylesheet" href="MainTemplate.css">
  <link rel="stylesheet" href="Resister.css">
  <link rel="icon" href="/images/favicon.ico" type="image/x-icon">
</head>
<body>
  <div id="wrap">
    <div class="resister-layout">
      <div class="resister-contatiner">
        <div class="resist-title-dividebox"></div>
        <div class="resist-title">회원가입</div>
        <div class="resist-dividebox-10px"></div>
        <div class="resist-dividebox-10px"></div>
        <div class="resist-dividebox-10px"></div>
        <div class="resist-label">이메일</div>
        <div class="resist-dividebox-10px"></div>
        <input type="text" class="resist-input" placeholder="이메일을 입력해주세요.">
        <div class="asynchronous-message">이미 사용 중인 이메일입니다.</div>
        <div class="resist-dividebox-10px"></div>
        
        <div class="resist-label">비밀번호</div>
        <div class="resist-dividebox-10px"></div>
        <input type="password" class="resist-input" placeholder="비밀번호를 입력해주세요.">
        <div class="asynchronous-message">이미 사용 중인 이메일입니다.</div>
        <div class="asynchronous-message">이미 사용 중인 이메일입니다.</div>
        <div class="asynchronous-message">이미 사용 중인 이메일입니다.</div>
        <div class="resist-dividebox-10px"></div>
        <div class="resist-label">비밀번호 확인</div>
        <div class="resist-dividebox-10px"></div>
        <input type="password" class="resist-input" placeholder="비밀번호를 다시 입력해주세요.">
        <div class="asynchronous-message">이미 사용 중인 이메일입니다.</div>
        <div class="resist-dividebox-10px"></div>
        <button class="resist-button">이메일 인증하기</button>
        <div class="resist-dividebox-10px"></div>
        <div class="resist-dividebox-10px"></div>

        <div class="social-resist-label-box">간편 회원가입</div>
        <div class="resist-dividebox-10px"></div>
        <div class="social-resist-box">
          <div class="social-icons">
            <a href="#">
                <img src="resources/kakao.png" alt="카카오 로그인">
            </a>
            <a href="#">
                <img src="resources/google.png" alt="구글 로그인">
            </a>
          </div>
        </div>
    </div>
  </div>
  <script src="Maintemplate.js"></script>
</body>
</html>
