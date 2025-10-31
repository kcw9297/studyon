<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<style>

    .admin-header-bar {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 15px 25px;
        width: 100%;
        background: #fff;
    }

    .admin-page-title {
        font-size: 24px;
        font-weight: bold;
        color: #333;
    }

    .btn-download {
        background-color: #4a90e2;
        color: #fff;
        border: none;
        border-radius: 6px;
        padding: 10px 20px;
        font-size: 14px;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.2s;
        box-shadow: 0 2px 4px rgba(74, 144, 226, 0.3);
    }

    .btn-download:hover {
        background-color: #357ac8;
        transform: translateY(-1px);
        box-shadow: 0 4px 8px rgba(74, 144, 226, 0.4);
    }

    /* 강의 리스트 래퍼 */
    .lecture-list-wrapper {
        width: 100%;
        padding: 0 20px;
    }

    .lecture-list {
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        overflow: hidden;
    }

    /* 강의 아이템 */
    .lecture-item {
        display: grid;
        grid-template-columns: 140px 1fr 160px 160px 110px;
        align-items: center;
        gap: 20px;
        padding: 20px 25px;
        border-bottom: 1px solid #f0f0f0;
        transition: all 0.2s;
    }

    .lecture-item:hover {
        background: #f8f9fc;
        box-shadow: 0 2px 8px rgba(0,0,0,0.05);
    }

    .lecture-item:last-child {
        border-bottom: none;
    }

    /* 썸네일 */
    .lecture-thumbnail-link {
        display: block;
        position: relative;
        overflow: hidden;
        border-radius: 10px;
    }

    .lecture-thumbnail {
        width: 140px;
        height: 90px;
        object-fit: cover;
        border-radius: 10px;
        cursor: pointer;
        transition: transform 0.3s;
    }

    .lecture-thumbnail-link:hover .lecture-thumbnail {
        transform: scale(1.05);
    }

    /* 강의 정보 */
    .lecture-info {
        display: flex;
        flex-direction: column;
        gap: 10px;
        overflow: hidden;
    }

    .lecture-title-row {
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .lecture-target {
        display: inline-flex;
        align-items: center;
        gap: 5px;
        padding: 4px 10px;
        background: #e3f2fd;
        border-radius: 12px;
        font-size: 12px;
        font-weight: 600;
        color: #1976d2;
        white-space: nowrap;
        flex-shrink: 0;
    }

    .lecture-title {
        font-size: 17px;
        font-weight: bold;
        color: #333;
        text-decoration: none;
        cursor: pointer;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
        line-height: 1.4;
        transition: color 0.2s;
        flex: 1;
    }

    .lecture-title:hover {
        color: #4a90e2;
    }

    .lecture-meta {
        display: flex;
        gap: 12px;
        font-size: 14px;
        color: #666;
        flex-wrap: wrap;
    }

    .lecture-target {
        display: flex;
        align-items: center;
        gap: 5px;
        padding: 3px 8px;
        background: #e3f2fd;
        border-radius: 12px;
        font-size: 13px;
        font-weight: 600;
        color: #1976d2;
    }

    .lecture-teacher,
    .lecture-subject {
        display: flex;
        align-items: center;
        gap: 5px;
    }


    /* 통계 정보 */
    .lecture-stats {
        display: flex;
        flex-direction: column;
        gap: 10px;
        font-size: 14px;
    }

    .lecture-price {
        font-weight: bold;
        color: #e74c3c;
        font-size: 18px;
    }

    .lecture-students {
        color: #666;
        display: flex;
        align-items: center;
        gap: 5px;
    }

    .lecture-status {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .badge {
        padding: 6px 12px;
        border-radius: 15px;
        font-size: 12px;
        font-weight: bold;
        text-align: center;
        white-space: nowrap;
    }

    .badge-success {
        background: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
    }

    .badge-danger {
        background: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
    }

    .badge-approved {
        background: #cce5ff;
        color: #004085;
        border: 1px solid #b8daff;
    }

    .badge-pending {
        background: #fff3cd;
        color: #856404;
        border: 1px solid #ffeaa7;
    }

    .badge-rejected {
        background: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
    }

    /* 관리 버튼 */
    .lecture-actions {
        display: flex;
        justify-content: center;
    }

    .btn-manage {
        padding: 10px 20px;
        border: none;
        border-radius: 6px;
        background: #4a90e2;
        color: white;
        font-size: 14px;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.2s;
        box-shadow: 0 2px 4px rgba(74, 144, 226, 0.2);
    }

    .btn-manage:hover {
        background: #357ac8;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(74, 144, 226, 0.3);
    }

    /* 리스트가 비어있을 때 */
    .lecture-list-empty {
        padding: 60px 20px;
        text-align: center;
        color: #999;
        font-size: 16px;
    }

</style>


<!-- 강의 리스트 -->
<div class="lecture-list-wrapper">

    <!-- 강의 목록 -->
    <div class="lecture-list"></div>

    <!-- 페이징 -->
    <div id="pagination" class="pagination"></div>
</div>

<script>

    // 페이징 바
    const pagination = document.getElementById("pagination");

    // 상세과목 선택 모달
    const modal = document.getElementById('subjectDetailModal');
    const openBtn = document.getElementById('openSubjectDetailModal');
    const closeBtn = document.getElementById('closeSubjectDetailModal');
    const confirmBtn = document.getElementById('confirmSubjectDetail');


    document.addEventListener("DOMContentLoaded", ()=> {

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

    })

    // 페이징 렌더링
    function renderPagination(page) {

        // 페이지 데이터 분석
        const isStart = page.startPage;
        const isEnd = page.endPage;
        const groupSize = page.groupSize;
        const dataCount = page.dataCount;
        const currentGroupStartPage = page.currentGroupStartPage;
        const currentGroupEndPage = page.currentGroupEndPage;
        const previousGroupPage = page.previousGroupPage;
        const currentPage = page.currentPage;

        // 버튼
        const leftBtn = isStart
            ? `<button class="page-btn disabled">◀</button>`
            : `<button class="page-btn">◀</button>`;

        const rightBtn = isEnd
            ? `<button class="page-btn disabled">▶</button>`
            : `<button class="page-btn">▶</button>`;

        // 내부 버튼 (그룹)
        const innerBtn = [];
        for (let i = currentGroupStartPage; i <= currentGroupEndPage; i++) {
            const activeClass = (i === currentPage) ? 'active' : '';
            innerBtn.push(`<button class="page-btn \${activeClass}">\${i}</button>`);
        }

        pagination.innerHTML = '';
        pagination.innerHTML = leftBtn + innerBtn.join('') + rightBtn;
    }

</script>
