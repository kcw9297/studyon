<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="TeacherManagement-navbar">
    <a href="<c:url value='/teacher/management/profile'/>" class="nav-item">강사 메인</a>
    <a href="<c:url value='/teacher/management/lectureregister'/>" class="nav-item">강의 등록</a>
    <a href="<c:url value='/teacher/management/statistics'/>" class="nav-item">강의 통계</a>
    <a href="<c:url value='/teacher/management/lecturelist'/>" class="nav-item">내 강의관리</a>
    <a href="<c:url value='/teacher/management/reviews'/>" class="nav-item">수강평 관리</a>
    <a href="<c:url value='/teacher/management/qna'/>" class="nav-item">QNA 관리</a>
</div>

<style>
    .TeacherManagement-navbar {
        display: flex;
        gap: 30px;
        background: #f8f9fa;
        padding: 15px 30px;
        border-bottom: 1px solid #ddd;
    }
    .nav-item {
        text-decoration: none;
        font-weight: 600;
        color: Black;
        transition: 0.2s;
    }
    .nav-item:hover {
        color: #2ecc71;
    }
</style>
