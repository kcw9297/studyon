<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture_video/lecture_player.css'/>">

<div class="player-wrapper">
    <div class="player-section">
        <video width="100%" height="auto" controls>
            <source src="<c:url value='/video/1강 - 수학의정석.mp4'/>" type="video/mp4">
        </video>
    </div>
    <div class="curriculum-section">
      <div class="curriculum-title">커리큘럼</div>
      <div class="lecture-title">
        생성형 AI 특성과 PPT 제작
      </div>
      <div class="lecture-progress">
        수강중 1/3<br>
        33%<br>
      </div>
      <div class="progress-bar">
        <div class="progress-fill" id="progress-fill" style="width: 33%;"></div>
      </div>

      <div class="curriculum-list">
        <div class="curriculum-item-box">
          <span class="curriculum-item">1.오리엔테이션 및 강의 소개</span><br>
          <label class="curriculum-time">10:00</label>
        </div>
        <div class="curriculum-item-box">
          <span class="curriculum-item">2.오리엔테이션 및 강의 소개</span><br>
          <label class="curriculum-time">10:00</label>
        </div>
        <div class="curriculum-item-box">
          <span class="curriculum-item">3.오리엔테이션 및 강의 소개</span><br>
          <label class="curriculum-time">10:00</label>
        </div>
      </div>
    </div>
</div>

<style>
    .player-section {
        flex: 4;  /* 화면의 75% */
        display:flex;
        background: black;
        text-align:center;
        align-items:center;
        justify-content: center;

    }
</style>