<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/teacher_management.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="teacher"/>
</jsp:include>

<div id="pageRoot" data-context-path="${pageContext.request.contextPath}">
    <div class="admin-content-container">
        <div class="admin-header-bar">
            <h2 class="admin-page-title">강사 관리</h2>
            <a href="/admin/teacher_management/new">
                <button id="createTeacherBtn" class="create-teacher-btn">강사 등록</button>
            </a>
        </div>

        <!-- 검색 바 -->
        <div class="member-search-bar">
            <select id="searchType">
                <option value="">전체</option>
                <option value="email">이메일</option>
                <option value="nickname">이름</option>
            </select>
            <select id="subjectFilter" name="subject">
                <option value="">전체과목</option>
                <option value="KOREAN">국어</option>
                <option value="ENGLISH">영어</option>
                <option value="MATH">수학</option>
                <option value="SOCIAL">사회탐구</option>
                <option value="SCIENCE">과학탐구</option>
            </select>
            <input type="text" id="keyword" placeholder="회원 이름 또는 이메일 검색..." />

            <button id="teacherSearchBtn">검색</button>
        </div>

        <!-- 회원 테이블 -->
        <div class="member-table-wrapper">
            <table class="member-table">
                <thead>
                <tr>
                    <th>No</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>담당과목</th>
                    <th>총 리뷰 수</th>
                    <th>평균 평점</th>
                    <th>총 수강생 수</th>
                    <th>관리</th>
                </tr>
                </thead>
                <tbody id="teachersTableBody">
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
                    <td><a class="management-button" href="#">상세정보</a></td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>박지민</td>
                    <td>pjm@studyon.com</td>
                    <td>강사</td>
                    <td>비활성</td>
                    <td>2025-10-15</td>
                    <td>🔴</td>
                    <td><a class="management-button" href="#">상세정보</a></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>김한재</td>
                    <td>pjm@studyon.com</td>
                    <td>학생</td>
                    <td>비활성</td>
                    <td>2025-10-15</td>
                    <td>🔴</td>
                    <td><a class="management-button" href="#">상세정보</a></td>
                </tr>
                </tbody>
                <tbody>
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
            <div id="pagination" class="pagination"></div>
        </div>
    </div>

    <div id="memberModal" class="modal-overlay">
        <div class="modal-content">
            <span class="close-btn">&times;</span>
            <label class="modal-title">강사 상세정보</label>

            <div class="modal-info">
                <p><strong>이름:</strong> <span id="modalName">-</span></p>
                <p><strong>이메일:</strong> <span id="modalEmail">-</span></p>
                <p><strong>담당 과목:</strong> <span id="modalSubject">-</span></p>
                <p><strong>총 리뷰 수:</strong> <span id="modalReviews">-</span></p>
                <p><strong>평균 평점:</strong> <span id="modalRating">-</span></p>
                <p><strong>총 수강생 수:</strong> <span id="modalTotalStudents">-</span></p>
            </div>

            <div class="modal-buttons">
                <button id="toggleBtn"  class="btn-ban">관리</button>
                <button id="closeModalBtn" class="btn-view">닫기</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value='/js/page/admin/teacher_management.js'/>"></script>
<script src="<c:url value='/js/page/admin/teacher_management_modal.js'/>"></script>
<script src="<c:url value='/js/page/admin/management_toggle.js'/>"></script>
<!--
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const modal = document.getElementById("memberModal");
        const closeBtn = document.querySelector(".close-btn");
        const closeModalBtn = document.getElementById("closeModalBtn");

        // 관리 버튼 클릭 시
        document.querySelectorAll(".management-button").forEach(btn => {
            btn.addEventListener("click", (e) => {
                e.preventDefault();
                const row = e.target.closest("tr");

                // 데이터 추출
                const name = row.children[1].innerText;
                const email = row.children[2].innerText;
                const role = row.children[3].innerText;
                const status = row.children[4].innerText;
                const date = row.children[5].innerText;

                // 모달 채우기
                document.getElementById("modalName").innerText = name;
                document.getElementById("modalEmail").innerText = email;
                document.getElementById("modalRole").innerText = role;
                document.getElementById("modalStatus").innerText = status;
                document.getElementById("modalDate").innerText = date;

                // 모달 표시
                modal.style.display = "flex";
            });
        });

        // 닫기 버튼
        closeBtn.addEventListener("click", () => modal.style.display = "none");
        closeModalBtn.addEventListener("click", () => modal.style.display = "none");

        // 바깥 클릭 시 닫기
        window.addEventListener("click", (e) => {
            if (e.target === modal) modal.style.display = "none";
        });
    });
</script>
-->