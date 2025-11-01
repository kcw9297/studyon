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
                    if (lectures.length === 0) {
                        container.innerHTML += `
                            <div style="text-align: center;">검색된 강의가 없습니다.</div>
                        `
                    }

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

    });

</script>