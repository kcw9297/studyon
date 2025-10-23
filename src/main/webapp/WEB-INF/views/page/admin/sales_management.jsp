<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<div id="empty-box"></div>

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="sales"/>
</jsp:include>

<div class="admin-content-container">
    <div class="test">
    </div>
</div>

<style>
    .admin-content-container {

    }
</style>

<script src="<c:url value='/js/page/admin/sales_management.js'/>"></script>
