<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/teacher_register.css'/>">


<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="teacher"/>
</jsp:include>

<div class="admin-content-container">
    <form action="<c:url value='/admin/api/members/teacher/register'/>" method="post" class="teacher-create-form">
        <div class="form-section">
            <h3>기본 정보</h3>

            <div class="form-row">
                <label for="email">이메일</label>
                <input type="email" id="email" name="email" required placeholder="example@studyon.com">
            </div>

            <div class="form-row">
                <label for="nickname">이름 (닉네임)</label>
                <input type="text" id="nickname" name="nickname" required placeholder="강사 이름 입력">
            </div>

            <div class="form-row">
                <label for="password">임시 비밀번호</label>
                <input type="password" id="password" name="password" required placeholder="임시 비밀번호">
                <small class="hint">최초 로그인 시 변경하도록 안내됩니다.</small>
            </div>
        </div>

        <div class="form-section">
            <h3>강사 관련 정보</h3>

            <div class="form-row">
                <label for="subject">담당 과목</label>
                <select id="subject" name="subject" required>
                    <option value="">선택</option>
                    <option value="KOREAN">국어</option>
                    <option value="ENGLISH">영어</option>
                    <option value="MATH">수학</option>
                    <option value="SOCIAL">사회탐구</option>
                    <option value="SCIENCE">과학탐구</option>
                </select>
            </div>

            <div class="form-row">
                <label for="description">강사 소개</label>
                <textarea id="description" name="description" rows="5" placeholder="강의 철학, 커리큘럼 등을 간단히 작성하세요."></textarea>
            </div>
        </div>

        <div class="form-buttons">
            <button type="button" id="createTeacherBtn" class="create-teacher-btn">등록하기</button>
            <button type="reset" class="btn-reset">초기화</button>
        </div>
    </form>
</div>

<script src="<c:url value='/js/page/admin/teacher_register.js'/>"></script>
