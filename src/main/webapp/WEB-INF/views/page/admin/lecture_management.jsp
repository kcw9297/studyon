<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="lecture"/>
</jsp:include>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/member_management_paging.css'/>">

<div class="admin-content-container">

    <div class="admin-header-bar">
        <h2 class="admin-page-title">강의 관리</h2>
    </div>

    <jsp:include page="/WEB-INF/views/page/admin/lecture_search_bar.jsp" />
    <jsp:include page="/WEB-INF/views/page/admin/lecture_list.jsp" />
</div>


<style>

    .admin-content-container {
        display: flex;
        flex-direction: column;
        border: 2px solid black;
        min-height: 600px;
        height: auto;
        width: 100%;
        padding-bottom: 50px;
    }
</style>

<script>

    document.addEventListener("DOMContentLoaded", () => {

        // 검색 파라미터
        let searchParams = {
            filter: '',
            keyword: '',
            subjects: [],
            subjectDetails: [],
            minPrice: 0,
            maxPrice: 0,
            onSales: [],
            statuses: [],
            targets: [],
            difficulties: [],
            sort: 'LATEST',
            page: 1,
            size: 5
        };

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


        // 최초 로드 시 1회 검색
        search();

        // 검색 버튼 클릭
        document.getElementById("lectureSearchBtn").addEventListener("click", () => {
            extractSearchParams();
            searchParams.page = 1; // 검색 시 첫 페이지로
            search();
        });


        pagination.addEventListener('click', (e) => {

            if (e.target.classList.contains('page-btn')) {
                const btnText = e.target.textContent.trim();

                if (e.target.classList.contains('disabled')) {
                    return;
                }

                if (btnText === '◀' && searchParams.page > 1) {
                    searchParams.page--;
                    search();
                } else if (btnText === '▶') {
                    searchParams.page++;
                    search();
                } else if (!isNaN(btnText)) {
                    searchParams.page = parseInt(btnText);
                    search();
                }
            }
        });

        // 검색 실행 함수
        function search() {

            // 검색에 필요한 url 수집
            const url = buildSearchUrl();

            // 검색 url 출력
            console.log("검색 URL : ", url);

            // 검색 요청
            sendSearchRequest(url);
        }





        // 검색 조건 수집 함수
        function extractSearchParams() {
            // 검색 구분
            searchParams.filter = document.getElementById('searchType').value;

            // 검색어
            searchParams.keyword = document.getElementById('keyword').value.trim();

            // 과목
            searchParams.subjects = [];
            document.querySelectorAll('input[name="subjects"]:checked').forEach(checkbox => {
                searchParams.subjects.push(checkbox.value);
            });

            // 세부과목
            searchParams.subjectDetails = [];
            document.querySelectorAll('input[name="subjectDetails"]:checked').forEach(checkbox => {
                searchParams.subjectDetails.push(checkbox.value);
            });

            // 가격 범위
            const minPrice = document.querySelector('input[name="minPrice"]').value;
            const maxPrice = document.querySelector('input[name="maxPrice"]').value;
            searchParams.minPrice = minPrice ? parseInt(minPrice) : null;
            searchParams.maxPrice = maxPrice ? parseInt(maxPrice) : null;

            // 판매 상태
            searchParams.onSales = [];
            document.querySelectorAll('input[name="onSales"]:checked').forEach(checkbox => {
                searchParams.onSales.push(checkbox.value);
            });

            // 등록 상태
            searchParams.statuses = [];
            document.querySelectorAll('input[name="statuses"]:checked').forEach(checkbox => {
                searchParams.statuses.push(checkbox.value);
            });

            // 학년(대상)
            searchParams.targets = [];
            document.querySelectorAll('input[name="targets"]:checked').forEach(checkbox => {
                searchParams.targets.push(checkbox.value);
            });

            // 난이도
            searchParams.difficulties = [];
            document.querySelectorAll('input[name="difficulties"]:checked').forEach(checkbox => {
                searchParams.difficulties.push(checkbox.value);
            });

            // 정렬
            searchParams.sort = document.querySelector('input[name="sort"]:checked')?.value;
        }

        // URL 생성 함수
        function buildSearchUrl() {
            const baseUrl = '/admin/api/lectures';
            const params = new URLSearchParams();

            // 페이지 정보
            params.append('page', searchParams.page);
            params.append('size', searchParams.size);

            // 검색 구분 & 키워드
            if (searchParams.filter) {
                params.append('filter', searchParams.filter);
            }
            if (searchParams.keyword) {
                params.append('keyword', searchParams.keyword);
            }

            // 과목 (배열)
            searchParams.subjects.forEach(subject => {
                params.append('subjects', subject);
            });

            // 세부과목 (배열)
            searchParams.subjectDetails.forEach(detail => {
                params.append('subjectDetails', detail);
            });

            // 가격 범위
            if (searchParams.minPrice !== null) {
                params.append('minPrice', searchParams.minPrice);
            }
            if (searchParams.maxPrice !== null) {
                params.append('maxPrice', searchParams.maxPrice);
            }

            // 판매 상태 (배열)
            searchParams.onSales.forEach(onSale => {
                params.append('onSales', onSale);
            });

            // 강의 상태 (배열)
            searchParams.statuses.forEach(status => {
                params.append('statuses', status);
            });

            // 학년(대상)
            searchParams.targets.forEach(target => {
                params.append('targets', target);
            });

            // 난이도
            searchParams.difficulties.forEach(difficulty => {
                params.append('difficulties', difficulty);
            });

            // 정렬
            if (searchParams.sort) {
                params.append('sort', searchParams.sort);
            }

            return `\${baseUrl}?\${params.toString()}`;
        }

        // 추후 비동기 요청 함수
        async function sendSearchRequest(url) {

            try {

                const res = await fetch(url, {
                    method: "GET"
                });

                const rp = await res.json();
                console.log("서버 응답:", rp);

                if (res.ok && rp.success) {
                    const page = rp.data;
                    const lectures = page.data;
                    const container = document.querySelector('.lecture-list');

                    // 기존 내용 제거
                    container.innerHTML = '';

                    // 강의 목록 렌더링
                    lectures.forEach(lecture => {
                        container.innerHTML += createLectureItem(lecture);
                    });

                    // 페이징 렌더링
                    renderPagination(page);
                }



            } catch (error) {
                console.error('검색 실패 오류:', error);
            }
        }


        // 강의 목록 렌더링 (추후 구현)
        function createLectureItem(lecture) {
            console.log('강의 목록 렌더링:', lecture);
            const imgSrc = lecture.thumbnailImagePath
                ? `${fileDomain}/\${lecture.thumbnailImagePath}`
                : `<c:url value='/img/png/default_member_profile_image.png'/>`;

            return `
                <div class="lecture-item">
                    <a href="#" class="lecture-thumbnail-link">
                        <img src="\${imgSrc}" alt="썸네일" class="lecture-thumbnail">
                    </a>
                    <div class="lecture-info">
                        <div class="lecture-title-row">
                            <span class="lecture-target">🎓 \${TARGET_MAP[lecture.lectureTarget]}</span>
                            <a href="#" class="lecture-title">\${lecture.title}</a>
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
                        <span class="badge badge-success">\${ON_SALE_MAP[lecture.onSale]}</span>
                        <span class="badge badge-approved">\${STATUS_MAP[lecture.lectureRegisterStatus]}</span>
                    </div>
                    <div class="lecture-actions">
                        <button class="btn-manage">관리</button>
                    </div>
                </div>
            `;
        }

    });

</script>