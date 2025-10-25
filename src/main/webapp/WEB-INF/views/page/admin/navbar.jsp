<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/navbar.css'/>">

<div class="nav-bar">
    <div class="nav-item ${param.active == 'dashboard' ? 'active' : ''}" onclick="location.href='/admin'">대시보드</div>
    <div class="nav-item ${param.active == 'member' ? 'active' : ''}" onclick="location.href='/admin/member_management'">회원관리</div>
    <div class="nav-item ${param.active == 'teacher' ? 'active' : ''}" onclick="location.href='/admin/teacher_management'">강사관리</div>
    <div class="nav-item ${param.active == 'support' ? 'active' : ''}" onclick="location.href='/admin/support'">고객상담</div>
    <%--<div class="nav-item ${param.active == 'report' ? 'active' : ''}" onclick="location.href='/admin/report_management'">신고관리</div>--%>
    <div class="nav-item ${param.active == 'banner' ? 'active' : ''}" onclick="location.href='/admin/banner_management'">배너관리</div>
    <div class="nav-item ${param.active == 'statistics' ? 'active' : ''}" onclick="location.href='/admin/lecture_statistics'">강의통계</div>
    <div class="nav-item ${param.active == 'notice' ? 'active' : ''}" onclick="location.href='/admin/notice_management'">공지사항등록</div>
    <div class="nav-item ${param.active == 'sales' ? 'active' : ''}" onclick="location.href='/admin/sales_management'">판매관리</div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const navItems = document.querySelectorAll(".nav-item");

        navItems.forEach(item => {
            item.addEventListener("click", (e) => {
                // 1️⃣ 모든 active 제거
                navItems.forEach(i => i.classList.remove("active"));

                // 2️⃣ 클릭된 항목에 active 추가
                item.classList.add("active");
            });
        });
    });
</script>