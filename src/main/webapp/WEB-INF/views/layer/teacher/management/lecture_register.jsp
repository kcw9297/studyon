<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- Local CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/layer/teacher/management/lecture_register.css'/>">


<div class="lecture-resister-wrapper">
<div id="empty-box"></div>
<div class="resister-title">
  강의등록
</div>
<form>
  <label class="resister-description" for="lecture-title">강의 제목</label>

  <div class="resister-description">
    <input class="resister-lecture-title" type="text" id="lecture-title" name="lecture-title" required>
  </div>
  <div class="line-dividebox-10px"></div>
    <label class="resister-description">강의 소개</label>
  <div>
    <textarea class="resister-lecture-description" id="lecture-description" name="lecture-description" required></textarea>
  </div>
  <div class="line-dividebox-10px"></div>
  <label class="resister-description">강의 대상</label>
  <div>
    <select class="resister-lecture-target" id="lecture-category" name="lecture-category" required>
      <option value="">선택하세요</option>
      <option value="programming">고1</option>
      <option value="design">고2</option>
      <option value="marketing">고3</option>
    </select>
  </div>
  <div class="line-dividebox-10px"></div>
  <label class="resister-description">판매 가격</label>
  <div>
    <input class="resister-lecture-price" type="number" id="lecture-price" name="lecture-price" min="0" required>원
  </div>
  <div class="line-dividebox-10px"></div>
  <label class="resister-description">썸네일 이미지 등록</label>
  <div>
    <input type="file" id="lecture-thumbnail" name="lecture-thumbnail" accept="image/*" required>
  </div>
  <div class="line-dividebox-10px"></div>
  <div>
    <label class="resister-description">목차</label>
  </div>
  <button type="button" id="add-lecture-btn">+ 강의 추가</button>
  <div id="lecture-list-box"></div>
</form>
</div>
<div class="submit-box">
<button class="submit-button" type="submit">강의 등록하기</button>
</div>
<div id="empty-box"></div>


<%-- Local Script --%>
<script src="<c:url value='/js/layer/teacher/management/lecture_register.js'/>"></script>