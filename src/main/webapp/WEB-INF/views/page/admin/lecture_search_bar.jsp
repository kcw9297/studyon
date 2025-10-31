<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<style>
    .lecture-search-bar {
        background: #f8f9fa;
        border-radius: 10px;
        padding: 25px;
        margin: 0 20px 20px 20px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.05);
    }

    .search-row {
        display: flex;
        align-items: center;
        gap: 15px;
        margin-bottom: 15px;
    }

    .search-row:last-child {
        margin-bottom: 0;
    }

    .search-row label {
        font-weight: 600;
        font-size: 14px;
        min-width: 90px;
        color: #333;
    }

    /* 입력 필드 */
    .search-row input[type="text"] {
        flex: 1;
        padding: 10px 14px;
        border: 1px solid #ddd;
        border-radius: 6px;
        font-size: 14px;
        transition: border-color 0.2s;
    }

    .search-row input[type="text"]:focus {
        outline: none;
        border-color: #4a90e2;
    }

    .search-row input[type="number"] {
        width: 140px;
        padding: 10px 14px;
        border: 1px solid #ddd;
        border-radius: 6px;
        font-size: 14px;
    }

    .search-row select {
        min-width: 140px;
        padding: 10px 14px;
        border: 1px solid #ddd;
        border-radius: 6px;
        font-size: 14px;
        background: white;
        cursor: pointer;
    }

    /* 체크박스 그룹 */
    .checkbox-group {
        display: flex;
        gap: 20px;
        flex-wrap: wrap;
        align-items: center;
    }

    .checkbox-group label {
        font-weight: normal;
        font-size: 14px;
        min-width: auto;
        display: flex;
        align-items: center;
        gap: 6px;
        cursor: pointer;
        color: #555;
    }

    .checkbox-group input[type="checkbox"] {
        width: 16px;
        height: 16px;
        cursor: pointer;
    }

    /* 세부과목 버튼 */
    .detail-subject-btn {
        padding: 10px 20px;
        background: white;
        border: 1px solid #ddd;
        border-radius: 6px;
        font-size: 14px;
        cursor: pointer;
        transition: all 0.2s;
        display: flex;
        align-items: center;
        gap: 5px;
    }

    .detail-subject-btn:hover {
        background: #f0f0f0;
        border-color: #4a90e2;
    }

    /* 검색 버튼 */
    .btn-search {
        background: #4a90e2;
        color: white;
        border: none;
        padding: 12px 40px;
        border-radius: 6px;
        font-size: 15px;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.2s;
        box-shadow: 0 2px 4px rgba(74, 144, 226, 0.3);
    }

    .btn-search:hover {
        background: #357ac8;
        box-shadow: 0 4px 8px rgba(74, 144, 226, 0.4);
        transform: translateY(-1px);
    }

    /* 구분선 */
    .search-row.divider {
        border-top: 1px solid #e0e0e0;
        padding-top: 15px;
        margin-top: 5px;
    }

    /* 세부과목 모달 */
    .subject-detail-modal {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
        z-index: 1000;
        justify-content: center;
        align-items: center;
    }

    .subject-detail-modal.active {
        display: flex;
    }

    .subject-detail-content {
        background: white;
        border-radius: 12px;
        padding: 30px;
        width: 600px;
        max-height: 70vh;
        overflow-y: auto;
        position: relative;
        box-shadow: 0 10px 30px rgba(0,0,0,0.3);
    }

    .subject-detail-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 25px;
        padding-bottom: 15px;
        border-bottom: 2px solid #f0f0f0;
    }

    .subject-detail-header h3 {
        font-size: 20px;
        font-weight: bold;
        color: #333;
    }

    .modal-close-btn {
        background: none;
        border: none;
        font-size: 28px;
        color: #999;
        cursor: pointer;
        line-height: 1;
        padding: 0;
        width: 30px;
        height: 30px;
    }

    .modal-close-btn:hover {
        color: #333;
    }

    .subject-detail-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 15px;
    }

    .subject-detail-grid label {
        display: flex;
        align-items: center;
        gap: 8px;
        padding: 10px 12px;
        background: #f8f9fa;
        border-radius: 6px;
        cursor: pointer;
        transition: all 0.2s;
        font-size: 14px;
    }

    .subject-detail-grid label:hover {
        background: #e9ecef;
    }

    .subject-detail-grid input[type="checkbox"] {
        width: 18px;
        height: 18px;
    }

    .modal-actions {
        margin-top: 25px;
        padding-top: 20px;
        border-top: 1px solid #f0f0f0;
        display: flex;
        justify-content: flex-end;
        gap: 10px;
    }

    .btn-modal-confirm {
        padding: 10px 25px;
        background: #4a90e2;
        color: white;
        border: none;
        border-radius: 6px;
        font-size: 14px;
        font-weight: bold;
        cursor: pointer;
        transition: 0.2s;
    }

    .btn-modal-confirm:hover {
        background: #357ac8;
    }

</style>


<!-- 검색 바 -->
<div class="lecture-search-bar">
    <div class="search-row">
        <label for="searchType">검색 구분:</label>
        <select id="searchType" name="filter">
            <option value="">전체</option>
            <c:forEach var="filter" items="${filters}">
                <option value="${filter}">
                        ${filter.value}
                </option>
            </c:forEach>
        </select>

        <label>검색어:</label>
        <input type="text" id="keyword" name="keyword" placeholder="검색어를 입력하세요..." />
    </div>

    <div class="search-row">
        <label for="subject">과목:</label>
        <div class="checkbox-group">
            <c:forEach var="subject" items="${subjects}">
                <label>
                    <input type="checkbox" name="subjects" value="${subject}">
                        ${subject.value}
                </label>
            </c:forEach>
        </div>
    </div>

    <div class="search-row">
        <label for="subjectDetail">세부:</label>
        <button type="button" class="detail-subject-btn" id="openSubjectDetailModal">
            세부과목 선택 <span>▼</span>
        </button>
    </div>

    <div class="search-row">
        <label>가격 범위:</label>
        <input type="number" name="minPrice" placeholder="최소 금액" min="0" />
        <span>~</span>
        <input type="number" name="maxPrice" placeholder="최대 금액" min="0" />
    </div>

    <div class="search-row">
        <label for="onSales">판매 상태:</label>
        <c:forEach var="onSale" items="${onSales}">
            <label><input type="checkbox" name="onSales" value="${onSale}">
                    ${onSale.value}
            </label>
        </c:forEach>
    </div>

    <div class="search-row">
        <label>등록 상태:</label>
        <c:forEach var="status" items="${statuses}">
            <label><input type="checkbox" name="statuses" value="${status}">
                    ${status.value}
            </label>
        </c:forEach>
    </div>

    <div class="search-row">
        <label>학년:</label>
        <c:forEach var="target" items="${targets}">
            <label><input type="checkbox" name="targets" value="${target}">
                    ${target.value}
            </label>
        </c:forEach>
    </div>


    <div class="search-row">
        <label>난이도:</label>
        <c:forEach var="difficulty" items="${difficulties}">
            <label><input type="checkbox" name="difficulties" value="${difficulty}">
                    ${difficulty.value}
            </label>
        </c:forEach>
    </div>

    <div class="search-row">
        <label>정렬:</label>
        <c:forEach var="sort" items="${sorts}">
            <label><input type="radio" name="sort" value="${sort}">
                    ${sort.value}
            </label>
        </c:forEach>
    </div>

    <div class="search-row" style="justify-content: flex-end;">
        <button id="lectureSearchBtn" type="button" class="btn-search">검색</button>
    </div>
</div>

<!-- 세부과목 선택 모달 -->
<div class="subject-detail-modal" id="subjectDetailModal">
    <div class="subject-detail-content">
        <div class="subject-detail-header">
            <h3>세부과목 선택</h3>
            <button class="modal-close-btn" id="closeSubjectDetailModal">&times;</button>
        </div>

        <div class="subject-detail-grid">
            <c:forEach var="subjectDetail" items="${subjectDetails}">
                <label>
                    <input type="checkbox" name="subjectDetails" value="${subjectDetail}">
                        ${subjectDetail.name}
                </label>
            </c:forEach>
        </div>

        <div class="modal-actions">
            <button class="btn-modal-confirm" id="confirmSubjectDetail">확인</button>
        </div>
    </div>
</div>


<script>
    document.addEventListener("DOMContentLoaded", () => {
        const modal = document.getElementById('subjectDetailModal');
        const openBtn = document.getElementById('openSubjectDetailModal');
        const closeBtn = document.getElementById('closeSubjectDetailModal');
        const confirmBtn = document.getElementById('confirmSubjectDetail');

        // 모달 열기
        openBtn.addEventListener('click', () => {
            modal.classList.add('active');
        });

        // 모달 닫기
        closeBtn.addEventListener('click', () => {
            modal.classList.remove('active');
        });

        // 확인 버튼
        confirmBtn.addEventListener('click', () => {
            modal.classList.remove('active');
        });

        // 배경 클릭 시 닫기
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                modal.classList.remove('active');
            }
        });
    });
</script>