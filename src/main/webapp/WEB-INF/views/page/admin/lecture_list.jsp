<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<style>

    .search-lecture-item{
        display:flex;
        flex-direction: column;
    }

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
        grid-template-columns: 140px 1fr 160px 160px;
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
        width: 100%;
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
        padding: 10px 16px;
        border-radius: 20px;
        font-size: 14px;
        cursor: pointer;
        user-select: none;
        transition: all 0.2s;
        text-align: center;  /* 추가 */
    }

    .lecture-status .badge:hover {
        transform: translateY(-1px);
        box-shadow: 0 3px 8px rgba(0,0,0,0.15);
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

    .badge-registered {
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

    /* 리스트가 비어있을 때 */
    .lecture-list-empty {
        padding: 60px 20px;
        text-align: center;
        color: #999;
        font-size: 16px;
    }


    /* 모달 */

    /* 상태 관리 모달 */
    .status-change-modal {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,0.5);
        z-index: 1000;
        align-items: center;
        justify-content: center;
    }

    .status-change-modal.active {
        display: flex;
    }

    .status-modal-content {
        background: white;
        padding: 30px;
        border-radius: 12px;
        width: 450px;
        max-width: 90%;
        box-shadow: 0 10px 40px rgba(0,0,0,0.2);
    }

    .status-modal-header {
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 20px;
        color: #333;
    }

    .status-modal-body {
        margin-bottom: 20px;
    }

    .status-option-group {
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .status-option-btn {
        padding: 15px 20px;
        border: 2px solid #ddd;
        border-radius: 8px;
        background: white;
        font-size: 15px;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.2s;
        text-align: left;
    }

    .status-option-btn:hover {
        border-color: #4a90e2;
        background: #f8f9fc;
    }

    .status-option-btn.register {
        color: #004085;
    }

    .status-option-btn.register:hover {
        border-color: #004085;
        background: #e3f2fd;
    }

    .status-option-btn.reject {
        color: #721c24;
    }

    .status-option-btn.reject:hover {
        border-color: #721c24;
        background: #f8d7da;
    }

    .status-option-btn:disabled {
        opacity: 0.5;
        cursor: not-allowed;
        pointer-events: none;
    }

    .status-option-btn.selected {
        border-color: #4a90e2;
        background: #e3f2fd;
    }

    .status-option-btn.register.selected {
        border-color: #004085;
        background: #e3f2fd;
    }

    .status-option-btn.reject.selected {
        border-color: #721c24;
        background: #f8d7da;
    }

    .reject-reason-area {
        display: none;
        margin-top: 15px;
        padding: 15px;
        background: #fff3cd;
        border-radius: 8px;
        border: 1px solid #ffeaa7;
    }

    .reject-reason-area.active {
        display: block;
    }

    .reject-reason-label {
        display: block;
        font-size: 14px;
        font-weight: bold;
        color: #856404;
        margin-bottom: 8px;
    }

    .reject-reason-input {
        width: 100%;
        padding: 10px;
        border: 1px solid #ffeaa7;
        border-radius: 6px;
        font-size: 14px;
        resize: vertical;
        min-height: 100px;
        font-family: inherit;
    }

    .reject-reason-input:focus {
        outline: none;
        border-color: #856404;
    }

    .status-modal-footer {
        display: flex;
        gap: 10px;
        justify-content: flex-end;
        margin-top: 20px;
    }

    .btn-modal-cancel,
    .btn-modal-confirm {
        padding: 10px 20px;
        border: none;
        border-radius: 6px;
        font-size: 14px;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.2s;
    }

    .btn-modal-cancel {
        background: #95a5a6;
        color: white;
    }

    .btn-modal-cancel:hover {
        background: #7f8c8d;
    }

    .btn-modal-confirm {
        background: #4a90e2;
        color: white;
    }

    .btn-modal-confirm:hover {
        background: #357ac8;
    }

    .btn-modal-confirm:disabled {
        background: #ccc;
        cursor: not-allowed;
    }

</style>


<!-- 강의 리스트 -->
<div class="lecture-list-wrapper">

    <!-- 강의 목록 -->
    <div class="lecture-list"></div>

    <!-- 페이징 -->
    <div id="pagination" class="pagination"></div>
</div>

<!-- 상태 변경 모달 -->
<div id="statusChangeModal" class="status-change-modal">
    <div class="status-modal-content">
        <div class="status-modal-header">등록 상태 변경</div>
        <div class="status-modal-body">
            <div class="status-option-group">
                <button class="status-option-btn register" onclick="selectStatusOption('REGISTERED')">
                    ✓ 승인하기
                </button>
                <button class="status-option-btn reject" onclick="selectStatusOption('REJECTED')">
                    ✗ 반려하기
                </button>
            </div>
            <div id="rejectReasonArea" class="reject-reason-area">
                <label class="reject-reason-label">반려 사유를 입력해주세요 *</label>
                <textarea id="rejectReasonInput" class="reject-reason-input" placeholder="반려 사유를 상세히 입력해주세요..."></textarea>
            </div>
        </div>
        <div class="text-error-center" id="rejectReasonError"></div>
        <div class="status-modal-footer">
            <button class="btn-modal-cancel" onclick="closeStatusModal()">취소</button>
            <button id="btnModalConfirm" class="btn-modal-confirm" onclick="confirmStatusChange()">확인</button>
        </div>
    </div>
</div>


<script>

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

    // 페이징 바
    const pagination = document.getElementById("pagination");

    // 매핑 객체
    const SUBJECT_MAP = {
        <c:forEach var="subject" items="${subjects}" varStatus="status">
        "${subject}": "${subject.value}"${!status.last ? ',' : ''}
        </c:forEach>
    };

    const SUBJECT_DETAIL_MAP = {
        <c:forEach var="subjectDetail" items="${subjectDetails}" varStatus="status">
        "${subjectDetail}": "${subjectDetail.name}"${!status.last ? ',' : ''}
        </c:forEach>
    };

    const DIFFICULTY_MAP = {
        <c:forEach var="difficulty" items="${difficulties}" varStatus="status">
        "${difficulty}": "${difficulty.value}"${!status.last ? ',' : ''}
        </c:forEach>
    };

    const TARGET_MAP = {
        <c:forEach var="target" items="${targets}" varStatus="status">
        "${target}": "${target.value}"${!status.last ? ',' : ''}
        </c:forEach>
    };

    const ON_SALE_MAP = {
        true: "${onSales[0].value}",   // ON_SALE의 value
        false: "${onSales[1].value}"   // NOT_SALE의 value
    };

    const STATUS_MAP = {
        <c:forEach var="st" items="${statuses}" varStatus="status">
        "${st}": "${st.value}"${!status.last ? ',' : ''}
        </c:forEach>
    };

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



    // 강의 목록 렌더링
    function createLectureItem(lecture) {
        console.log('강의 목록 렌더링:', lecture);
        const imgSrc = lecture.thumbnailImagePath
            ? `${fileDomain}/\${lecture.thumbnailImagePath}`
            : `<c:url value='/img/png/default_member_profile_image.png'/>`;

        // 판매 상태 배지
        const saleBadgeClass = lecture.onSale ? 'badge-success' : 'badge-danger';
        const saleBadgeText = ON_SALE_MAP[lecture.onSale];

        // 등록 상태 배지
        let statusBadgeClass = 'badge-pending';
        if (lecture.lectureRegisterStatus === 'REGISTERED') {
            statusBadgeClass = 'badge-registered';
        } else if (lecture.lectureRegisterStatus === 'REJECTED') {
            statusBadgeClass = 'badge-rejected';
        }

        return `
        <div class="lecture-item">
            <a href="#" class="lecture-thumbnail-link">
                <img src="\${imgSrc}" alt="썸네일" class="lecture-thumbnail">
            </a>
            <div class="lecture-info">
                <div class="lecture-title-row">
                    <span class="lecture-target">🎓 \${TARGET_MAP[lecture.lectureTarget]}</span>
                    <a href="/lecture/detail/\${lecture.lectureId}" class="lecture-title">\${lecture.title}</a>
                </div>
                <div class="lecture-meta">
                    <span class="lecture-teacher">👤 \${lecture.teacherNickname}</span>
                    <span class="lecture-subject">📚 \${SUBJECT_MAP[lecture.subject]} > \${SUBJECT_DETAIL_MAP[lecture.subjectDetail]}</span>
                </div>
            </div>
            <div class="lecture-stats">
                <span class="lecture-price">₩\${lecture.price.toLocaleString('ko-KR')}</span>
                <span class="lecture-students">👥 \${lecture.totalStudents.toLocaleString('ko-KR')}명</span>
            </div>
            <div class="lecture-status">
                <span class="badge \${saleBadgeClass} on-sale-badge"
                      data-lecture-id="\${lecture.lectureId}"
                      onclick="toggleSaleStatus(\${lecture.lectureId}, \${lecture.onSale}, '\${lecture.lectureRegisterStatus}')">\${saleBadgeText}</span>
                <span class="badge \${statusBadgeClass} status-badge"
                      data-lecture-id="\${lecture.lectureId}"
                      onclick="openStatusModal(\${lecture.lectureId}, '\${lecture.lectureRegisterStatus}')">\${STATUS_MAP[lecture.lectureRegisterStatus]}</span>
            </div>
        </div>
    `;
    }

    // 판매 상태 토글
    function toggleSaleStatus(lectureId, onSaleStatus, registerStatus) {

        // 변경할 상태
        const statusText = ON_SALE_MAP[!onSaleStatus];

        // 상태변경 안내
        if (registerStatus !== 'REGISTERED') {
            alert("등록되지 않은 상품은 판매상태로 전환할 수 없습니다.");
            return;
        }

        if (confirm(`판매 상태를 '\${statusText}'로 변경하시겠습니까?`))
            updateOnSale(lectureId, !onSaleStatus);
    }


    async function updateOnSale(lectureId, afterOnSale) {

        try {

            // REST API 요청
            const form = new FormData();

            // 요청 URL
            const url = afterOnSale
                ? `/admin/api/lectures/\${lectureId}/start-sale`
                : `/admin/api/lectures/\${lectureId}/stop-sale`;

            const res = await fetch(url, {
                headers: {'X-Requested-From': window.location.pathname + window.location.search},
                method: "PATCH"
            });

            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {

                // 로그인이 필요한 경우
                if (rp.statusCode === 401) {

                    // 로그인 필요 안내 전달
                    if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                        window.location.href = rp.redirect || "/login";
                    }

                    // 로직 중단
                    return;
                }

                // 권한이 부족한 경우
                if (rp.statusCode === 403) {
                    alert(rp.message || "접근 권한이 없습니다.");
                    return;
                }

                // 기타 예기치 않은 오류가 발생한 경우
                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            // 버튼 변경 처리
            const onSaleBadge = document.querySelector(`.on-sale-badge[data-lecture-id="\${lectureId}"]`);
            const statusBadge = document.querySelector(`.on-sale-badge[data-lecture-id="\${lectureId}"]`);
            if (onSaleBadge) {
                // 클래스 변경
                if (afterOnSale) {
                    onSaleBadge.classList.remove('badge-danger');
                    onSaleBadge.classList.add('badge-success');
                    onSaleBadge.textContent = ON_SALE_MAP[true];
                } else {
                    onSaleBadge.classList.remove('badge-success');
                    onSaleBadge.classList.add('badge-danger');
                    onSaleBadge.textContent = ON_SALE_MAP[false];
                }
                // onclick 속성 업데이트
                onSaleBadge.setAttribute('onclick', `toggleSaleStatus(\${lectureId}, \${afterOnSale})`);
            }

        } catch (error) {
            console.error("프로필 로드 실패:", error);
        }

    }

    // 전역 변수
    let currentLectureId = null;
    let selectedStatus = null;

    // 등록 상태 변경 모달 열기
    function openStatusModal(lectureId, currentStatus) {

        // 반려 상태의 강의는 처리 불가
        if (currentStatus === 'REJECTED') {
            alert("반려 상태의 강의는 변경이 불가능합니다.");
            return;
        }

        currentLectureId = lectureId;
        selectedStatus = null;

        // 초기화
        document.getElementById('rejectReasonArea').classList.remove('active');
        document.getElementById('rejectReasonInput').value = '';
        document.getElementById('btnModalConfirm').disabled = false;
        document.getElementById("rejectReasonError").textContent = '';

        // 현재 상태에 따라, 버튼 비활성화
        const rejectBtn = document.querySelector(".status-option-btn.reject");
        const registerBtn = document.querySelector(".status-option-btn.register");

        // 모든 버튼 활성화 (초기화)
        registerBtn.disabled = false;
        rejectBtn.disabled = false;

        // 현재 상태에 따라 버튼 비활성화
        if (currentStatus === 'REGISTERED') {
            registerBtn.disabled = true;
        } else if (currentStatus === 'REJECTED') {
            rejectBtn.disabled = true;
            registerBtn.disabled = true;
        }

        // 모달 열기
        document.getElementById('statusChangeModal').classList.add('active');
    }

    // 상태 옵션 선택
    // 상태 옵션 선택
    function selectStatusOption(status) {
        // 비활성화된 버튼 클릭 방지
        if (event.target.disabled) {
            return;
        }

        selectedStatus = status;
        const rejectReasonArea = document.getElementById('rejectReasonArea');
        //rejectReasonArea.textContent = ''; // 반려 사유 텍스트 제거
        document.getElementById("rejectReasonError").textContent = ''; // 애러 텍스트 제거

        // 모든 버튼에서 selected 클래스 제거
        document.querySelectorAll('.status-option-btn').forEach(btn => {
            btn.classList.remove('selected');
        });

        // 클릭된 버튼에 selected 클래스 추가
        event.target.classList.add('selected');


        if (status === 'REJECTED') {
            rejectReasonArea.classList.add('active');
        } else {
            rejectReasonArea.classList.remove('active');
        }
    }

    // 상태 변경 확인
    // 상태 변경 확인
    async function confirmStatusChange() {
        // 상태 선택 확인
        if (!selectedStatus) {
            alert('변경할 상태를 선택해주세요.');
            return;
        }

        // REST API 요청 보내기
        try {

            // 요청 URL
            let url = '';
            let form; // 반려 처리의 경우, 사유를 포함하기 위해 사용

            // 선택한 처리에 따라, 요청할 URL 판별
            if (selectedStatus === 'REJECTED') {
                const reason = document.getElementById('rejectReasonInput').value.trim();
                if (!reason) {
                    alert('반려 사유를 입력해주세요.');
                    return;
                }
                url = `/admin/api/lectures/\${currentLectureId}/reject`;
                form = new FormData();
                form.append("rejectReason", reason);

            } else if (selectedStatus === 'REGISTERED') {
                url = `/admin/api/lectures/\${currentLectureId}/register`;
            }

            // REST API 요청을 위한 객체
            let restRequest = {
                headers: {'X-Requested-From': window.location.pathname + window.location.search},
                method: "PATCH"
            };

            // 폼 데이터가 있는 경우 추가
            if (form) restRequest.body = form;

            // API 요청 전송
            const res = await fetch(url, restRequest);

            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {

                // 로그인이 필요한 경우
                if (rp.statusCode === 401) {

                    // 로그인 필요 안내 전달
                    if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                        window.location.href = rp.redirect || "/login";
                    }

                    // 로직 중단
                    return;
                }

                // 권한이 부족한 경우
                if (rp.statusCode === 403) {
                    alert(rp.message || "접근 권한이 없습니다.");
                    return;
                }

                if (rp.statusCode === 400) {
                    const errorField = document.getElementById("rejectReasonError");
                    errorField.textContent = rp.inputErrors.rejectReason || rp.message || "입력하신 값을 다시 확인해 주세요.";
                    return;
                }

                // 기타 예기치 않은 오류가 발생한 경우
                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            // 버튼 변경 처리
            alert(rp.message || "처리를 완료했습니다");
            const statusBadge = document.querySelector(`.status-badge[data-lecture-id="\${currentLectureId}"]`);
            const onSaleBadge = document.querySelector(`.on-sale-badge[data-lecture-id="\${currentLectureId}"]`);
            const currentOnSale = onSaleBadge.classList.contains('badge-success');

            if (statusBadge) {
                // 기존 클래스 제거
                statusBadge.classList.remove('badge-pending', 'badge-registered', 'badge-rejected');

                // 상태에 따라 클래스와 텍스트 변경
                if (selectedStatus === 'REGISTERED') {
                    statusBadge.classList.add('badge-registered');
                    statusBadge.textContent = STATUS_MAP['REGISTERED'];
                    statusBadge.setAttribute('onclick', `openStatusModal(\${currentLectureId}, 'REGISTERED')`);
                    onSaleBadge.setAttribute('onclick', `toggleSaleStatus(\${currentLectureId}, \${currentOnSale}, 'REGISTERED')`);

                } else if (selectedStatus === 'REJECTED') {
                    statusBadge.classList.add('badge-rejected');
                    statusBadge.textContent = STATUS_MAP['REJECTED'];
                    statusBadge.setAttribute('onclick', `openStatusModal(\${currentLectureId}, 'REJECTED')`);
                    onSaleBadge.setAttribute('onclick', `toggleSaleStatus(\${currentLectureId}, \${currentOnSale}, 'REJECTED')`);

                    // 반려 시 판매 상태도 미판매로 변경
                    if (onSaleBadge) {
                        onSaleBadge.classList.remove('badge-success');
                        onSaleBadge.classList.add('badge-danger');
                        onSaleBadge.textContent = ON_SALE_MAP[false];
                        onSaleBadge.setAttribute('onclick', `toggleSaleStatus(\${currentLectureId}, false)`);
                    }
                }
            }

            // 모달 닫기
            closeStatusModal();

        } catch (error) {
            console.error("상태 변경 실패:", error);
            alert("상태 변경 중 오류가 발생했습니다.");
        }
    }

    // 모달 배경 클릭 시 닫기
    document.addEventListener('DOMContentLoaded', () => {
        const modal = document.getElementById('statusChangeModal');
        if (modal) {
            modal.addEventListener('click', (e) => {
                if (e.target === modal) {
                    closeStatusModal();
                }
            });
        }
    });

    // 모달 닫기
    function closeStatusModal() {
        // 모든 버튼에서 selected 클래스 제거
        document.querySelectorAll('.status-option-btn').forEach(btn => {
            btn.classList.remove('selected');
        });

        document.getElementById('statusChangeModal').classList.remove('active');
        currentLectureId = null;
        selectedStatus = null;
    }

</script>
