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
            <input type="text" id="keyword" placeholder="회원 이름 또는 이메일 검색..."/>

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
                <!--
                <button id="toggleBtn" class="btn-ban">관리</button>
                -->
                <button id="closeModalBtn" class="btn-view">닫기</button>
            </div>
        </div>
    </div>
</div>

<!-- teacher_management.js -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        // 페이지용 변수
        let currentPage = 1;
        const pageSize = 10;
        const tbody = document.getElementById("teachersTableBody");
        const pagination = document.getElementById("pagination");

        const subjectMap = {
            KOREAN: "국어",
            ENGLISH: "영어",
            MATH: "수학",
            SOCIAL: "사회탐구",
            SCIENCE: "과학탐구"
        };

        // 선생님 프로필 링크용 contextPath
        const pageRoot = document.getElementById("pageRoot");
        const contextPath = pageRoot ? pageRoot.dataset.contextPath || "" : "";

        const subjectFilter = document.getElementById("subjectFilter");
        if (subjectFilter) {
            subjectFilter.addEventListener("change", function () {
                console.log("[과목 변경] " + subjectFilter.value);
                const searchType = document.getElementById("searchType") ? document.getElementById("searchType").value : "";
                const keyword = document.getElementById("keyword") ? document.getElementById("keyword").value.trim() : "";
                loadTeachers(1, searchType, keyword);
            });
        }

        // [1] 메인 함수 - 강사 목록 불러오기
        function loadTeachers(page, searchType, keyword) {
            if (!page || isNaN(page) || page < 1) page = 1;

            searchType = searchType || (document.getElementById("searchType") ? document.getElementById("searchType").value : "");
            keyword = keyword || (document.getElementById("keyword") ? document.getElementById("keyword").value.trim() : "");

            const params = new URLSearchParams({page: page, size: pageSize});
            const subject = document.getElementById("subjectFilter") ? document.getElementById("subjectFilter").value : "";
            if (subject) params.append("subject", subject);
            if (searchType) params.append("filter", searchType);
            if (keyword) params.append("keyword", keyword);

            const url = "/api/teachers?" + params.toString();
            console.log("[FETCH] " + url);

            fetch(url, {
                method: "GET",
                headers: {
                    "X-Requested-From": window.location.pathname + window.location.search
                }
            })
                .then(async function (res) {
                    if (!res.ok) throw new Error("서버 오류 발생 (" + res.status + ")");
                    return await res.json();
                })
                .then(function (json) {
                    if (!json || typeof json !== "object") throw new Error("잘못된 서버 응답 구조입니다.");
                    if (!json.success) throw new Error(json.message || "요청 처리 중 오류가 발생했습니다.");

                    const raw = json.data || {};
                    const teachers = Array.isArray(raw.data) ? raw.data : [];

                    if (!teachers || teachers.length === 0) {
                        tbody.innerHTML =
                            "<tr><td colspan='7' style='text-align:center; color:gray;'>" +
                            "'" + (keyword || subject || "검색") + "'에 해당하는 강사 정보가 없습니다." +
                            "</td></tr>";
                        console.warn("⚠️ 조회 결과 없음 - JS단 안전 처리");
                        renderPagination(1);
                        return;
                    }

                    currentPage = raw.currentPage || 1;
                    const totalPages = raw.totalPage || 1;
                    renderTeachersTable(teachers);
                    renderPagination(totalPages);
                })
                .catch(function (err) {
                    console.error("[ERROR] 요청 실패:", err);
                    tbody.innerHTML =
                        "<tr><td colspan='7' style='text-align:center;'>❌ 오류가 발생했습니다.<br>" +
                        (err.message || "서버와의 통신에 실패했습니다.") +
                        "</td></tr>";
                    renderPagination(1);
                });
        }

        // [2] 페이지네이션
        function renderPagination(totalPages) {
            pagination.innerHTML = "";
            if (!totalPages || totalPages <= 0) return;

            const maxVisible = 5;
            const currentGroup = Math.ceil(currentPage / maxVisible);
            const start = (currentGroup - 1) * maxVisible + 1;
            const end = Math.min(totalPages, start + maxVisible - 1);

            const prev = document.createElement("button");
            prev.textContent = "◀";
            prev.className = "page-btn " + (currentGroup === 1 ? "disabled" : "");
            prev.onclick = function () {
                if (currentGroup > 1) {
                    const prevGroupLastPage = (currentGroup - 2) * maxVisible + 1;
                    loadTeachers(prevGroupLastPage);
                }
            };
            pagination.appendChild(prev);

            for (let i = start; i <= end; i++) {
                const btn = document.createElement("button");
                btn.textContent = i;
                btn.className = "page-btn " + (i === currentPage ? "active" : "");
                btn.onclick = function () {
                    loadTeachers(i);
                };
                pagination.appendChild(btn);
            }

            const next = document.createElement("button");
            next.textContent = "▶";
            next.className = "page-btn " + (end >= totalPages ? "disabled" : "");
            next.onclick = function () {
                if (end < totalPages) {
                    const nextGroupFirstPage = end + 1;
                    loadTeachers(nextGroupFirstPage);
                }
            };
            pagination.appendChild(next);
        }

        // [3] 강사 테이블 렌더링
        function renderTeachersTable(teachers) {
            if (!tbody) return;
            tbody.innerHTML = "";

            if (!teachers.length) {
                tbody.innerHTML = "<tr><td colspan='8'>등록된 강사가 없습니다.</td></tr>";
                return;
            }

            teachers.forEach(function (t, idx) {
                const row = document.createElement("tr");
                row.innerHTML =
                    "<td>" + ((currentPage - 1) * pageSize + idx + 1) + "</td>" +
                    "<td><a href='" + contextPath + "/teacher/profile/" + t.teacherId + "'>" +
                    (t.nickname || "-") + "</a></td>" +
                    "<td>" + (t.email || "-") + "</td>" +
                    "<td>" + (subjectMap[t.subject] || "-") + "</td>" +
                    "<td>" + (t.totalReview ? t.totalReview : 0) + "</td>" +
                    "<td>" +
                    (t.averageRating !== null && t.averageRating !== undefined
                            ? Number(t.averageRating).toFixed(1)
                            : "-"
                    ) +
                    "</td>"
                    +
                    "<td>" + (t.totalStudents ? t.totalStudents : 0) + "</td>" +
                    "<td><a href='#' class='management-button' data-teacher-id='" + t.memberId + "'>상세정보</a></td>";
                tbody.appendChild(row);
            });

            attachModalEvents();
        }

        // [4] 모달 이벤트 등록
        function attachModalEvents() {
            const buttons = document.querySelectorAll(".management-button");
            buttons.forEach(function (btn) {
                btn.addEventListener("click", function (e) {
                    e.preventDefault();
                    const row = e.target.closest("tr");
                    const modal = document.getElementById("memberModal");
                    const closeBtn = modal.querySelector(".close-btn");
                    const closeModalBtn = modal.querySelector("#closeModalBtn");

                    const name = row.children[1].innerText;
                    const email = row.children[2].innerText;
                    const subjectName = row.children[3].innerText;
                    const reviews = row.children[4].innerText;
                    const rating = row.children[5].innerText;
                    const totalStudents = row.children[6].innerText;

                    document.getElementById("modalName").innerText = name;
                    document.getElementById("modalEmail").innerText = email;
                    document.getElementById("modalSubject").innerText = subjectName;
                    document.getElementById("modalReviews").innerText = reviews;
                    document.getElementById("modalRating").innerText = rating;
                    document.getElementById("modalTotalStudents").innerText = totalStudents;

                    modal.style.display = "flex";
                    if (closeBtn) closeBtn.onclick = function () {
                        modal.style.display = "none";
                    };
                    if (closeModalBtn) closeModalBtn.onclick = function () {
                        modal.style.display = "none";
                    };
                    window.onclick = function (e) {
                        if (e.target === modal) modal.style.display = "none";
                    };
                });
            });
        }

        // [5] 검색 버튼 이벤트
        const searchBtn = document.getElementById("teacherSearchBtn");
        if (searchBtn) {
            searchBtn.addEventListener("click", function () {
                console.log("[검색 클릭] 필터 다시 읽기 후 첫 페이지 로드");
                const keywordEl = document.getElementById("keyword");
                if (keywordEl) keywordEl.blur();

                tbody.innerHTML = "<tr><td colspan='8' style='text-align:center; color:#777;'>불러오는 중...</td></tr>";
                const searchType = document.getElementById("searchType") ? document.getElementById("searchType").value : "";
                const keyword = document.getElementById("keyword") ? document.getElementById("keyword").value.trim() : "";
                loadTeachers(1, searchType, keyword);
            });
        }

        // [5-1] 엔터키 검색
        const keywordInput = document.getElementById("keyword");
        if (keywordInput) {
            keywordInput.addEventListener("keypress", function (e) {
                if (e.key === "Enter") {
                    e.preventDefault();
                    if (searchBtn) searchBtn.click();
                }
            });
        }

        // ✅ 초기 실행
        loadTeachers(currentPage);
    });
</script>
<!-- teacher_management_modal.js -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        console.log("[INIT] 강사 모달 이벤트 등록 시작");

        const modal = document.getElementById("memberModal");
        const toggleBtn = document.getElementById("toggleBtn");
        const closeModalBtn = document.getElementById("closeModalBtn");
        const closeBtn = document.querySelector(".close-btn");

        // ✅ 보기 버튼 클릭
        document.addEventListener("click", function(e) {
            const btn = e.target.closest(".btn-view");
            if (!btn) return;

            const row = btn.closest("tr");
            if (!row) return;

            // 🔹 강사 테이블 구조에 맞게 인덱스 조정
            const name = row.children[1].innerText;
            const email = row.children[2].innerText;
            const subject = row.children[3].innerText;
            const status = row.children[4].innerText.trim();
            const date = row.children[5].innerText;
            const loginAt = row.children[6].innerText;
            const teacherId = btn.dataset.teacherId;

            console.log("[DEBUG] 모달 세팅:", {
                teacherId: teacherId,
                name: name,
                email: email,
                subject: subject,
                status: status
            });

            // 모달 데이터 세팅
            document.getElementById("modalName").innerText = name;
            document.getElementById("modalEmail").innerText = email;
            document.getElementById("modalSubject").innerText = subject;
            document.getElementById("modalStatus").innerText = status;
            document.getElementById("modalDate").innerText = date;
            document.getElementById("modalLoginDate").innerText = loginAt || "기록 없음";

            modal.dataset.teacherId = teacherId;
            toggleBtn.innerText = (status === "활성") ? "비활성" : "활성";
            modal.style.display = "flex";
        });

        // ✅ 닫기 버튼
        function closeModal() {
            modal.style.display = "none";
        }

        if (closeBtn) closeBtn.addEventListener("click", closeModal);
        if (closeModalBtn) closeModalBtn.addEventListener("click", closeModal);
        window.addEventListener("click", function(e) {
            if (e.target === modal) closeModal();
        });
    });
</script>

<script src="<c:url value='/js/page/admin/management_toggle.js'/>"></script>