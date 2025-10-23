<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="empty-box"></div>
<div id="content">
    <jsp:include page="lecture_summary.jsp"/>
    <jsp:include page="lecture_navigation.jsp"/>
    <jsp:include page="lecture_introduce.jsp"/>
    <jsp:include page="lecture_algorithm.jsp"/>
</div>
<script src="lecture_detail.js"></script>
