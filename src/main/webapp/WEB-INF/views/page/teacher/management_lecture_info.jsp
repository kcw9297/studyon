<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/views/page/teacher/navbar.jsp" %>
<div class="lecture-register-wrapper">
    <div class="register-content">
        <div class="register-title">강의 등록</div>

        <form class="register-form">
            <div class="register-section">
                <label class="register-label">강의 제목</label>
                <div class="register-value">파이썬으로 배우는 AI 마스터과정</div>
            </div>

            <div class="register-section">
                <label class="register-label">강의 소개</label>
                <div class="register-value">파이썬으로 배우는 AI 마스터과정</div>
            </div>

            <div class="register-section">
                <label class="register-label">강의 대상</label>
                <div class="register-value">고3</div>
            </div>

            <div class="register-section">
                <label class="register-label">판매 가격</label>
                <div class="register-value">₩30,000</div>
            </div>

            <div class="register-section">
                <label class="register-label">썸네일 이미지</label>
                <div class="thumbnail-box">
                    <img src="<c:url value='/img/png/thumbnail.png'/>" class="thumbnail-preview">
                </div>
                <button type="button" class="thumbnail-change-btn">📷 사진 변경</button>
            </div>

            <div class="register-section">
                <label class="register-label">강의 목차</label>

                <div id="lecture-list-box">
                    <div class="lecture-item">
                        <div class="lecture-index">1강</div>
                        <div class="lecture-info">
                            <div class="lecture-title">파이썬 기본 문법</div>
                            <div class="lecture-file">파일: python_basic.mp4</div>
                        </div>
                    </div>

                    <div class="lecture-item">
                        <div class="lecture-index">2강</div>
                        <div class="lecture-info">
                            <div class="lecture-title">조건문과 반복문</div>
                            <div class="lecture-file">파일: python_loop.mp4</div>
                        </div>
                    </div>
                </div>
            </div>
            <button type="button" class="list-change-btn">목차 변경</button>


            <div class="submit-box">
                <button class="submit-button" type="submit">강의 등록하기</button>
            </div>
        </form>
    </div>
</div>

<style>
    .lecture-register-wrapper {
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        background: #fff;
        padding-bottom: 80px;
    }

    .register-content {
        width: 80%;
        max-width: 1000px;
        background: #fafafa;
        border-radius: 12px;
        padding: 30px 40px;
        box-shadow: 0 5px 15px rgba(0,0,0,0.05);
        margin-top:30px;
    }

    .register-title {
        font-size: 30px;
        font-weight: bold;
        text-align: center;
        color: #333;
        margin-bottom: 25px;
    }

    .register-section {
        margin-bottom: 20px;
    }

    .register-label {
        display: block;
        font-weight: 600;
        font-size: 18px;
        margin-bottom: 8px;
        color: #444;
    }

    .register-value {
        font-size: 17px;
        background: #f9f9f9;
        padding: 12px 15px;
        border-radius: 8px;
        border: 1px solid #ddd;
    }

    /* 썸네일 영역 */
    .thumbnail-box {
        display: flex;
        align-items: center;
        gap: 20px;
        background: #fefefe;
        border: 1px dashed #ccc;
        border-radius: 10px;
        padding: 15px;
    }

    .thumbnail-preview {
        width: 50%;
        height: auto;
        object-fit: cover;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    }

    .thumbnail-change-btn {
        border: none;
        background: #f1f1f1;
        border-radius: 6px;
        padding: 10px 16px;
        cursor: pointer;
        font-weight: 500;
        transition: 0.2s;
        margin-top:10px;
    }

    .thumbnail-change-btn:hover {
        background: #e0e0e0;
    }

    /* 강의 추가 영역 */
    #add-lecture-btn {
        background-color: #ffba49;
        color: #fff;
        border: none;
        border-radius: 8px;
        padding: 10px 20px;
        font-size: 16px;
        font-weight: 600;
        margin-top: 15px;
        cursor: pointer;
        transition: all 0.25s ease;
    }

    #add-lecture-btn:hover {
        background-color: #ff9f00;
        transform: translateY(-1px);
        box-shadow: 0 3px 6px rgba(255,159,0,0.3);
    }

    #lecture-list-box {
        margin-top: 10px;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        background: #fff;
    }

    .lecture-item {
        display: flex;
        align-items: flex-start;
        gap: 15px;
        margin-bottom: 12px;
        padding-bottom: 10px;
        border-bottom: 1px solid #eee;
    }

    .lecture-item:last-child {
        border-bottom: none;
    }

    .lecture-index {
        font-weight: 600;
        color: #2c3e50;
        width: 60px;
    }

    .lecture-info {
        display: flex;
        flex-direction: column;
        gap: 3px;
    }

    .lecture-title {
        font-size: 16px;
        font-weight: 500;
        color: #333;
    }

    .lecture-file {
        font-size: 14px;
        color: #888;
    }

    .submit-box {
        display: flex;
        justify-content: center;
        margin-top: 40px;
    }

    .submit-button {
        width: 260px;
        padding: 15px 20px;
        font-size: 18px;
        font-weight: 600;
        color: #fff;
        background: linear-gradient(135deg, #27ae60, #2ecc71);
        border: none;
        border-radius: 10px;
        cursor: pointer;
        transition: all 0.25s ease;
        box-shadow: 0 4px 10px rgba(39,174,96,0.25);
    }

    .submit-button:hover {
        background: linear-gradient(135deg, #219150, #27ae60);
        transform: translateY(-2px);
    }

    .list-change-btn{
        border: none;
        background: #f1f1f1;
        border-radius: 6px;
        padding: 10px 16px;
        cursor: pointer;
        font-weight: 500;
        transition: 0.2s;
    }
</style>

<script src="<c:url value='/js/page/teacher/management_lecture_register.js'/>"></script>
