<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>기본 레이아웃 | Oneday OneClass</title>
  <link rel="stylesheet" href="MainTemplate.css">
  <link rel="stylesheet" href="payment.css">
  <link rel="icon" href="/images/favicon.ico" type="image/x-icon">
</head>
<body>
  <div id="wrap">
    <main id="container">
        <div class="center-box">
            <header id="header">
                <div class="header-container">
                  <div class="header-div">
                    <img src="resources/logo.png" id="logo"alt="로고이미지">
                  </div>
                  <div class="header-div">
                    결제하기
                  </div>
                  <div class="header-div">
                    Login
                  </div>
                </div>
            </header>
            <div id="content">콘텐츠영역
                <div id="empty-box"></div>
                <div id="empty-box"></div>
                <div class="content-wrapper">
                  <div class="lecture-info-box">
                    <div class="lecture-thumbnail">
                      <img src="resources/sample1.png" class="lecture-thumbnail-img"alt="강의이미지">
                    </div>
                    <div class="lecture-info">
                      <div class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</div>
                      <div class="lecture-subtitle">인프런</div>
                    </div>
                    <div class="lecture-price">₩35,000</div>
                  </div>
                  <div class="divide-title-box">할인정보</div>
                  <div class="coupon-box">
                    <button class="coupon-button">쿠폰선택</button>
                    <div class="coupon-info">사용가능한 쿠폰이 없습니다.</div>
                  </div>
                  <div class="divide-title-box">결제수단</div>
                  <div class="payment-method-box">
                    <input type="radio" id="card" name="payment" value="card" checked>
                    <label>토스 페이먼츠</label>
                    <img src="resources/Toss_Logo.png" class="payment-logo" alt="토스페이먼츠로고">
                  </div>
                  <div class="divide-title-box">총 결제액</div>
                  <div class="payment-summary-box">
                    <div class="summary-item">
                      <div class="summary-title">총 결제금액</div>
                      <div class="summary-price">₩35,000</div>
                    </div>
                  </div>
                  <button class="pay-button">결제하기</button>
                  <div id="empty-box"></div>
                
            </div>
            
        </div>
        <footer id="footer">푸터영역</footer>
        
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
