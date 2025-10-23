<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/statistics_management.css'/>">
<div id="empty-box"></div>

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="statistics"/>
</jsp:include>

<div class="admin-content-container">

</div>

<style>
    .admin-content-container {
        border:2px solid black;
        min-height: 600px;
        height:auto;
    }
</style>

<script src="<c:url value='/js/page/admin/statistics_management.js'/>"></script>
