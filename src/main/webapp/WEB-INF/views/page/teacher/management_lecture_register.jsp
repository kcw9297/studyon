<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/teacher/management_lecture_register.css'/>">
<%@ include file="/WEB-INF/views/page/teacher/navbar.jsp" %>
<div class="lecture-resister-wrapper">
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

<style>
    .TeacherManagement-navbar{
        display: flex;
        background-color: #f2f2f2;
        padding: 10px;
        margin-bottom: 20px;
    }

    .TeacherManagement-navbar nav-item{
        margin-right:40px;
        font-size: 18px;
        color: #333;
        text-decoration: none;
    }


    #lecture-list-box {
        margin-top: 15px;
        padding: 15px;
        border: 1px solid #ddd;
        border-radius: 8px;
        background: #fafafa;
    }

    .lecture-item {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 10px;
    }

    .lecture-item label {
        width: 100px;
        font-weight: 500;
    }

    .lecture-item input {
        padding: 8px 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 15px;
    }

    .resister-title{
        font-size: 30px;
        font-weight: bold;
        align-items: center;
        text-align: center;
    }
    .lecture-title{
        font-size: 20px;
        font-weight: bold;
        align-items: center;
        text-align: center;
    }

    .resister-description {
        display: block;
        font-weight: 600;
        color: #444;
        font-size: 18px;
    }

    .resister-lecture-title{
        width: 50%;
        padding: 12px 14px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 8px;
        transition: border-color 0.2s ease, box-shadow 0.2s ease;
        box-sizing: border-box;
    }

    .resister-lecture-description{
        width: 50%;
        padding: 12px 14px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 8px;
        transition: border-color 0.2s ease, box-shadow 0.2s ease;
        box-sizing: border-box;
    }

    .resister-lecture-target{
        width: 10%;
        padding: 12px 14px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 8px;
        transition: border-color 0.2s ease, box-shadow 0.2s ease;
        box-sizing: border-box;
    }

    .resister-lecture-price{
        width: 10%;
        padding: 12px 14px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 8px;
        transition: border-color 0.2s ease, box-shadow 0.2s ease;
        box-sizing: border-box;
    }


    input[type="file"] {
        font-size: 15px;
        margin-top: 8px;
    }

    textarea {
        height: 140px;
        resize: vertical;
    }

    .line-dividebox-10px{
        width: 100%;
        height: 10px;
        background-color: white;
        display: flex
    }

    .lecture-resister-wrapper{
        display: flex;
        flex-direction: column;
        gap: 20px;
        width: 100%;
        padding-left:20px;
    }

    .lecture-input {
        width: 100%;
        padding: 10px;
        border: 2px solid #c9883f;
        border-radius: 8px;
        font-size: 15px;
        background-color: #fdfdfd;
    }

    /* 강의 영상 파일 input 전용 */
    .lecture-input-file {
        border: 1px dashed #aaa;
        padding: 8px;
        border-radius: 6px;
        background-color: #fcfcfc;
    }

    .lecture-input:focus {
        border-color: #d89c2e;
        outline: none;
        box-shadow: 0 0 5px rgba(212, 96, 29, 0.3);
    }

    input[type="text"].lecture-input {
        border: 2px solid #d6da0f;
        background-color: #f8fff8;
        font-size: 16px;
        border-radius: 8px;
        padding: 10px;
    }

    .fuckinglabel{
        width:100px;
    }

    .submit-box{
        display: flex;
        justify-content: center;
        margin-top: 20px;
    }

    .submit-button {
        display: inline-block;
        width: 100%;
        max-width: 300px;
        padding: 15px 20px;
        margin-top: 25px;
        font-size: 18px;
        font-weight: 600;
        color: white;
        background: linear-gradient(135deg, #27ae60, #2ecc71); /* 초록 계열 그라데이션 */
        border: none;
        border-radius: 10px;
        cursor: pointer;
        box-shadow: 0 3px 8px rgba(46, 204, 113, 0.3);
        transition: all 0.25s ease;
        text-align: center;
    }

    /* hover 효과 */
    .submit-button:hover {
        background: linear-gradient(135deg, #219150, #27ae60);
        box-shadow: 0 4px 12px rgba(39, 174, 96, 0.4);
        transform: translateY(-2px);
    }

</style>


<%-- Local Script --%>
<script src="<c:url value='/js/page/teacher/management_lecture_register.js'/>"></script>