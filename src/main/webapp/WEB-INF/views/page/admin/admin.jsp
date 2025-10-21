<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
        <div id="content">콘텐츠영역
            <div id="empty-box"></div>

            <div>
              <div class="nav-bar">
                <div class="nav-item active">대시보드</div>
                  <div class="nav-item" id="nav-member">회원관리</div>
                  <div class="nav-item" id="nav-teacher">강사관리</div>
                  <div class="nav-item" id="nav-support">고객상담</div>
                  <div class="nav-item" id="nav-report">신고관리</div>
                  <div class="nav-item" id="nav-banner">배너관리</div>
                  <div class="nav-item" id="nav-instructor">선생님관리</div>
                  <div class="nav-item" id="nav-lecture">강의관리</div>
                  <div class="nav-item" id="nav-stat">강의통계</div>
                  <div class="nav-item" id="nav-coupon">쿠폰관리</div>
                  <div class="nav-item" id="nav-notice">공지사항등록</div>
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
