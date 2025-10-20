<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>


<div id="empty-box"></div>
<div class="main-banner-container">
  <img src="<c:url value="/img/png/teacher_profile_img.png"/>" alt="메인비주얼이미지" class="main-banner">
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
    <img src="<c:url value="/img/png/sample1.png"/>" alt="강의이미지">
    <div class="lecture-info">
      <p class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</p>
      <p class="lecture-info-text">인프런</p>
      <p class="lecture-info-text">₩90,000</p>
      <p class="lecture-info-text">⭐4.9 (15) 🧸200+</p>
    </div>
  </div>
</div>



