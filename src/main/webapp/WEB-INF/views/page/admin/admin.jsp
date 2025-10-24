<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/admin.css'/>">

<div id="empty-box"></div>
<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="dashboard"/>
</jsp:include>
<div class="admin-content-container">
    <!-- 신규 회원 수 카드 -->
    <div class="dashboard-card">
        <div class="card-icon">👤</div>
        <div class="card-info">
            <div class="card-title">신규 회원 수</div>
            <div class="card-number">52명</div>
            <div class="card-sub">오늘 가입</div>
        </div>
    </div>

    <!-- 이번달 매출 카드 -->
    <div class="dashboard-card sales">
        <div class="card-icon">💰</div>
        <div class="card-info">
            <div class="card-title">이번달 매출</div>
            <div class="card-number">₩3,280,000</div>
            <div class="card-sub">10월 기준</div>
        </div>
    </div>

    <div class="dashboard-card mvp">
        <div class="card-icon">🏆</div>
        <div class="card-info">
            <div class="card-title">이번달 MVP 강사</div>
            <div class="card-number">최예나 강사</div>
            <div class="card-sub">매출 ₩3,280,000 (10월 기준)</div>
        </div>
    </div>

    <div class="dashboard-card loser">
        <div class="card-icon">💀</div>
        <div class="card-info">
            <div class="card-title">이번달 꼴등 강사</div>
            <div class="card-number">박패배 강사</div>
            <div class="card-sub">매출 ₩0 (방출 후보)</div>
        </div>
    </div>

    <div class="dashboard-card loser">
        <div class="card-icon">💀</div>
        <div class="card-info">
            <div class="card-title">이번달 꼴등 강사</div>
            <div class="card-number">박패배 강사</div>
            <div class="card-sub">매출 ₩0 (방출 후보)</div>
        </div>
    </div>

    <div class="status-card">
        <div class="status-icon">👥</div>
        <div class="status-info">
            <div class="status-title">총 회원 수</div>
            <div class="status-value">1,284명</div>
        </div>
    </div>

    <div class="status-card">
        <div class="status-icon">🎓</div>
        <div class="status-info">
            <div class="status-title">전체 강의 수</div>
            <div class="status-value">86개</div>
        </div>
    </div>

    <div class="status-card">
        <div class="status-icon">💡</div>
        <div class="status-info">
            <div class="status-title">활성 사용자 수</div>
            <div class="status-value">312명</div>
            <div class="status-sub">(최근 7일 로그인)</div>
        </div>
    </div>

    <div class="status-card system">
        <div class="status-icon">🖥️</div>
        <div class="status-info">
            <div class="status-title">시스템 상태</div>
            <div class="status-value">✅ 정상 작동</div>
        </div>
    </div>
</div>

<script src="<c:url value='/js/page/admin/admin_statistics.js'/>"></script>