<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/member_management.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/member_management_paging.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="member"/>
</jsp:include>

<div class="admin-content-container">
    <div class="admin-header-bar">
        <h2 class="admin-page-title">회원 관리</h2>
        <button id="downloadPdfBtn" class="btn-download">PDF로 저장</button>
    </div>
    <!-- 검색 바 -->
    <div class="member-search-bar">
        <select id="searchType" name="filter">
            <option value="">전체</option>
            <option value="email">이메일</option>
            <option value="nickname">이름</option>
        </select>
        <select id="roleFilter" name="role">
            <option value="">전체 권한</option>
            <option value="USER">학생</option>
            <option value="TEACHER">강사</option>
            <option value="ADMIN">관리자</option>
        </select>
        <select id="isActiveFilter">
            <option value="">전체 상태</option>
            <option value="1">활성</option>
            <option value="0">비활성</option>
        </select>
        <input type="text" id="keyword" name="keyword" placeholder="회원 이름 또는 이메일 검색..." />

        <button id="memberSearchBtn" type="button">검색</button>
    </div>

    <!-- 회원 테이블 -->
    <div class="member-table-wrapper">
        <table class="member-table">
            <thead>
            <tr>
                <th>No</th>
                <th>ID</th>
                <th>닉네임</th>
                <th>이메일</th>
                <th>권한</th>
                <th>상태</th>
                <th>가입일</th>
                <th>최근 로그인 일시</th>
                <th>관리</th>
            </tr>
            </thead>
            <tbody id="memberTableBody">
                <!-- JS 회원 목록 렌더링 -->
            </tbody>
            <!--
            <tbody>
            <tr>
                <td>1</td>
                <td>김효상</td>
                <td>kinhyo97@studyon.com</td>
                <td>관리자</td>
                <td>활성</td>
                <td>2025-10-21</td>
                <td>🟢</td>
                <td><a class="management-button" href="#">관리</a></td>
            </tr>
            <tr>
                <td>2</td>
                <td>박지민</td>
                <td>pjm@studyon.com</td>
                <td>강사</td>
                <td>비활성</td>
                <td>2025-10-15</td>
                <td>🔴</td>
                <td><a class="management-button" href="#">관리</a></td>
            </tr>
            <tr>
                <td>3</td>
                <td>김한재</td>
                <td>khj@studyon.com</td>
                <td>학생</td>
                <td>비활성</td>
                <td>2025-10-15</td>
                <td>🔴</td>
                <td><a class="management-button" href="#">관리</a></td>
            </tr>
            </tbody>
            <-- 안쓰는 부분
            <tbody id="memberTableBody">
            <c:forEach var="member" items="${memberList}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${member.name}</td>
                    <td>${member.email}</td>
                    <td>${member.role}</td>
                    <td>
                        <c:choose>
                            <c:when test="${member.active}"><span class="status-active">활성</span></c:when>
                            <c:otherwise><span class="status-banned">정지</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td><fmt:formatDate value="${member.createdAt}" pattern="yyyy-MM-dd" /></td>
                    <td>
                        <button class="btn-view">보기</button>
                        <button class="btn-ban">정지</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            -->
        </table>
        <!-- 페이징 -->
        <div id="pagination" class="pagination"></div>
    </div>

</div>



<div id="memberModal" class="modal-overlay">
    <div class="modal-content">
        <span class="close-btn">&times;</span>
        <label class="modal-title">회원 상세정보</label>

        <div class="modal-info">
            <p><strong>닉네임:</strong> <span id="modalName">-</span></p>
            <p><strong>이메일:</strong> <span id="modalEmail">-</span></p>
            <p><strong>권한:</strong> <span id="modalRole">-</span></p>
            <p><strong>상태:</strong> <span id="modalStatus"></span>✏️</p>
            <p><strong>가입일:</strong> <span id="modalDate">-</span></p>
            <p><strong>최근 로그인 일시:</strong> <span id="modalLoginDate">-</span></p>
            <!--
            <button class="btn-view" data-id="${m.memberId}">재활성</button>
            <button class="btn-ban" data-id="${m.memberId}">정지</button>
            -->
        </div>

        <div class="modal-buttons">
            <button id="toggleBtn" class="btn-ban">비활성</button>
            <button id="closeModalBtn" class="btn-view">닫기</button>
        </div>
    </div>
</div>


<script src="<c:url value='/js/page/admin/member_management.js'/>"></script>
<script src="<c:url value='/js/page/admin/member_management_modal.js'/>"></script>
<script src="<c:url value='/js/page/admin/management_toggle.js'/>"></script>