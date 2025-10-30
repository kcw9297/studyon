<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/payment_management.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="payment"/>
</jsp:include>

<div id="pageRoot" data-context-path="${pageContext.request.contextPath}">
    <div class="admin-content-container">
        <div class="admin-header-bar">
            <h2 class="admin-page-title">결제 관리</h2>
            <button id="downloadPdfBtn" class="btn-download">PDF로 저장</button>
        </div>

        <!-- ✅ 검색 필터 영역 (추가 가능) -->
        <div class="payment-search-bar">
            <select id="searchType">
                <option value="">전체</option>
                <option value="paymentUid">결제번호</option>
                <option value="lectureTitle">강의명</option>
                <option value="nickname">결제자(닉네임)</option>
            </select>
            <select id="isRefunded">
                <option value="">환불여부(전체)</option>
                <option value="1">환불완료</option>
                <option value="0">결제완료</option>
            </select>
            <select id="orderBy">
                <option value="">정렬기준(기본)</option>
                <option value="date">결제일순(최신)</option>
                <option value="amount">금액순(높은 금액)</option>
                <option value="refund">환불우선</option>
            </select>

            <input type="text" id="keyword" placeholder="검색어 입력" />
            <button id="paymentSearchBtn">검색</button>
        </div>

        <!-- 결제 내역 테이블 -->
        <div class="payment-table-wrapper">
            <table class="payment-table">
                <thead>
                <tr>
                    <th>No</th>
                    <th>결제번호</th>
                    <th>강의명</th>
                    <th>결제자</th>
                    <th>결제금액</th>
                    <th>결제일</th>
                    <th>환불상태</th>
                    <th>관리</th>
                </tr>
                </thead>
                <tbody id="paymentTableBody">
                <!-- JS 렌더링 -->
                </tbody>
            </table>
        </div>
    </div>
    <div id="pagination" class="pagination"></div>
</div>

<script src="<c:url value='/js/page/admin/payment_management.js'/>"></script>
