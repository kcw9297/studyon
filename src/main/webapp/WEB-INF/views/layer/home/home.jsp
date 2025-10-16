<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>



<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>기본 레이아웃 | Oneday OneClass</title>
  <link rel="stylesheet" href="/css/MainTemplate.css">
  <link rel="stylesheet" href="/css/Main.css">
</head>
<body>
  <div id="wrap">
    <main id="container">
        <div class="center-box">
            <header id="header">
                <div class="header-container">
                    <div class="header-logo">
                        <img src="<c:url value="/images/logo.png"/>" id="logo" alt="image">
                    </div>
                    <div class="header-search">

                      <div class="search-box">
                        <input type="text" id="searchInput" placeholder="검색어를 입력하세요">
                        <button id="searchBtn"><img src="<c:url value="/images/search.png"/>" class="svg-search" alt="image"></button>
                      </div>
                      <div id="searchResults" class="search-results">
                        <div class="search-result-item">수학 강의</div>
                        <div class="search-result-item">영어 회화</div>
                        <div class="search-result-item">국어 문법</div>
                      </div>
                      
                    </div>
                    <div class="header-info">
                      <button><img class="svg-list" src="/images/list.png"></button>
                      <a href="#" id="loginModalBtn" class="modal"><img src="/images/login.png" class="svg-login"></a>
                    </div>
                </div>
            </header>
            
            <div id="content">콘텐츠영역
                <div id="empty-box"></div>
                <div class="main-banner-container">
                  <img src="/images/TeacherProfileImg.png" alt="메인비주얼이미지" class="main-banner">
                </div>
                <div class="nav">
                    <a href="#">수학</a>
                    <a href="#">영어</a>
                    <a href="#">국어</a>
                    <a href="#">과학탐구</a>
                    <a href="#">사회탐구</a>
                </div>

                <label class="lecture-section-title">최근 등록된 강의</label>

                <div class ="recent-lecture-container">
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                </div>

              <label class="lecture-section-title">최근 등록된 강의</label>

                <div class ="recent-lecture-container">
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                  <div class="recent-lecture-item">
                    <img src="/images/sample1.png" alt="강의이미지">
                    <div class="lecture-info">
                      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
                      <p class="lecture-info-text">인프런</p>
                      <p class="lecture-info-text">₩90,000</p>
                      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>          
                    </div>
                  </div>
                </div>
            </div>
            <footer id="footer">
              <div class="footer-container">
                <div class="footer-left">
                  <h3>StudyOn</h3>
                  <p>배움의 즐거움을 매일, StudyOn</p>
                </div>
                <div class="footer-right">
                  <p>© 2025 StudyOn All rights reserved.</p>
                </div>
              </div>
            </footer>
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
              <img src="/images/kakao.png" alt="카카오 로그인">
          </a>
          <a href="#">
              <img src="/images/google.png" alt="구글 로그인">
          </a>
          <a href="#">
              <img src="/images/naver.png" alt="구글 로그인">
          </a>
        </div>
      </div>
    </div>
  </div>
  <script src="/js/Maintemplate.js"></script>
</body>
</html>
