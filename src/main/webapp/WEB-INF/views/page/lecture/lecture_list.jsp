<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_recommend.css'/>">

<div id="content">
    <div class="main-container">
        <div class="sidebar-container">
            <div class="recommend-nav">
                <a href="<c:url value='/lecture/list'/>" class="nav-item">전체</a>
                <c:forEach var="subject" items="${subjects}">
                    <a href="<c:url value='/lecture/recommend/${subject}'/>" class="nav-item">${subject.value}</a>
                </c:forEach>
            </div>
        </div>

        <!--메인 컨텐츠 -->
        <div class="main-content-container">
            <div class="search-box">
                <form id="lecture-search-form" method="get">
                    <!-- 검색 구분 -->
                    <div class="filter-row">
                        <label>검색 구분:</label>
                        <select name="filter">
                            <option value="">전체</option>
                            <c:forEach var="filter" items="${filters}">
                                <option value="${filter}" <c:if test="${param.filter eq filter}">selected</c:if>>
                                        ${filter.value}
                                </option>
                            </c:forEach>
                        </select>

                        <label>검색어:</label>
                        <input type="text" name="keyword" placeholder="검색어를 입력하세요" value="${param.keyword}">
                    </div>

                    <!-- 카테고리 -->
                    <div class="filter-row">
                        <label>과목:</label>
                        <c:forEach var="subject" items="${subjects}">
                            <label>
                                <input type="checkbox" name="subjects" value="${subject}"
                                       <c:if test="${fn:contains(selectedSubjects, subject.name())}">checked</c:if>>
                                    ${subject.value}
                            </label>
                        </c:forEach>
                    </div>

                    <!-- 2차 카테고리 자동 변경 영역 -->
                    <div class="filter-row">
                        <label>세부:</label>
                        <c:forEach var="subjectDetail" items="${subjectDetails}">
                            <label>
                                <input type="checkbox" name="subjectDetails" value="${subjectDetail}"
                                       <c:if test="${fn:contains(selectedSubjectDetails, subjectDetail.name())}">checked</c:if>>
                                    ${subjectDetail.name}
                            </label>
                        </c:forEach>
                    </div>

                    <!-- 가격 -->
                    <div class="filter-row">
                        <label>가격 범위:</label>
                        <input type="number" name="minPrice" placeholder="최소 금액" min="0" value="${param.minPrice}">
                        ~
                        <input type="number" name="maxPrice" placeholder="최대 금액" min="0" value="${param.maxPrice}">
                    </div>

                    <!-- 난이도 -->
                    <div class="filter-row">
                        <label>난이도:</label>
                        <c:forEach var="difficulty" items="${difficulties}">
                            <label>
                                <input type="checkbox" name="difficulties" value="${difficulty}"
                                       <c:if test="${fn:contains(selectedDifficulties, difficulty.name())}">checked</c:if>>
                                    ${difficulty.value}
                            </label>
                        </c:forEach>
                    </div>

                    <!-- 정렬 -->
                    <div class="filter-row">
                        <label>정렬 기준:</label>
                        <c:forEach var="sort" items="${sorts}">
                            <label><input type="radio" name="sort" value="${sort}" <c:if test="${param.sort eq sort}">checked</c:if>>
                                    ${sort.value}
                            </label>
                        </c:forEach>
                    </div>

                    <!-- 검색 버튼 -->
                    <div class="filter-row" style="text-align:right;">
                        <button type="submit" id="search-btn" class="search-btn">검색</button>
                    </div>
                </form>
            </div>

            <div class ="search-lecture-container">

            </div>

        </div>
    </div>
</div>

<style>
    .main-content-container {
        margin-top: 20px;
        border: 2px solid black;
        width: 100%;
        height: auto;
        padding-right:20px;
    }
    .search-box {
        border: 1px solid #ccc;
        border-radius: 8px;
        padding: 20px;
        background: #fafafa;
        margin-bottom: 20px;
        margin-top:20px;
        margin-right:5px;
        width: 100%;
    }
    .filter-row {
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        gap: 10px;
        margin-bottom: 12px;
    }
    .filter-row label {
        font-weight: 600;
        margin-right: 8px;
    }
    .filter-row input[type="text"],
    .filter-row input[type="number"],
    .filter-row select {
        padding: 6px 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        min-width: 120px;
    }
    .search-btn {
        background-color: #007bff;
        color: white;
        font-weight: bold;
        padding: 8px 18px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: 0.2s ease;
    }
    .search-btn:hover {
        background-color: #0056b3;
    }
    .search-lecture-item {
        width: 260px;
        height: auto;
        box-shadow: inset 0 0 0 2px rgba(0, 0, 0, 0.2); /* ✅ 내부 border처럼 */
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        box-sizing: border-box;

    }
    .search-lecture-item:hover {
        transform: translateY(-5px);
        cursor: pointer;
    }

    .search-lecture-container {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
        gap: 20px;
        width: 100%;
        height: auto;
        box-sizing: border-box;
        background-color: white;
        margin-bottom:10px;
    }
    .search-lecture-info{
        padding:5px;

    }
    .search-lecture-title{
        font-weight:bold;
        color:#333333;
    }
    .search-lecture-teacher{
        color: black;
    }
</style>

<script>
    let currentPage = 1;
    let isLoading = false;
    let isLastPage = false;

    document.addEventListener("DOMContentLoaded", () => {

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

        loadLectures();

        // 무한 스크롤 이벤트
        window.addEventListener('scroll', () => {
            if (isLoading || isLastPage) return;

            const scrollTop = window.scrollY || document.documentElement.scrollTop;
            const windowHeight = window.innerHeight;
            const documentHeight = document.documentElement.scrollHeight;

            // 스크롤이 하단에 도달하면 다음 페이지 로드
            if (scrollTop + windowHeight >= documentHeight - 100) {
                currentPage++;
                loadLectures();
            }
        });

        // 검색 버튼 클릭
        document.getElementById("search-btn").addEventListener("click", async (e) => {
            e.preventDefault();

            const form = document.getElementById('lecture-search-form');
            const formData = new FormData(form);
            const params = new URLSearchParams();

            for (let [key, value] of formData.entries())
                if (value) params.append(key, value);

            const queryString = params.toString();

            // 검색 시 페이지 초기화
            currentPage = 1;
            isLastPage = false;
            window.location.href = window.location.pathname + "?" + queryString;
        });




        async function loadLectures() {
            if (isLoading) return;
            isLoading = true;

            try {
                const pathname = "/api/lectures";
                const urlParams = new URLSearchParams(window.location.search);
                urlParams.set('page', currentPage);
                const url = pathname + "?" + urlParams.toString();

                const res = await fetch(url, {
                    method: "GET"
                });

                const rp = await res.json();
                console.log("서버 응답:", rp);

                if (res.ok && rp.success) {
                    const lectures = rp.data.data;
                    const container = document.querySelector('.search-lecture-container');

                    // 첫 페이지면 기존 내용 제거
                    if (currentPage === 1) {
                        container.innerHTML = '';
                    }

                    // 강의 목록 렌더링
                    lectures.forEach(lecture => {
                        const lectureItem = createLectureItem(lecture);
                        container.appendChild(lectureItem);
                    });

                    // 마지막 페이지 확인
                    isLastPage = rp.data.endPage;

                }

            } catch (error) {
                console.error('검색 실패 오류:', error);
            } finally {
                isLoading = false;
            }
        }

        function createLectureItem(lecture) {
            const item = document.createElement('div');
            item.className = 'search-lecture-item';

            const thumbnailSrc = lecture.thumbnailImagePath
                ? `${fileDomain}/\${lecture.thumbnailImagePath}`
                : `<c:url value='/img/png/default_member_profile_image.png'/>`;

            item.innerHTML = `
            <img src="\${thumbnailSrc}" class="lecture-thumbnail" />
            <div class="search-lecture-info">
                <label class="search-lecture-title">\${lecture.title}</label>
                <label class="search-lecture-title">\${SUBJECT_MAP[lecture.subject]}</label>
                <label class="search-lecture-title">\${SUBJECT_DETAIL_MAP[lecture.subjectDetail]}</label>
                <label class="search-lecture-title">\${DIFFICULTY_MAP[lecture.difficulty]}</label>
                <label class="search-lecture-teacher">\${lecture.teacherNickname}</label>
                <label class="search-lecture-teacher">\${lecture.price}</label>
                <label class="search-lecture-teacher">\${lecture.totalStudents}</label>
                <label class="search-lecture-teacher">\${lecture.totalDuration}</label>
                <label class="search-lecture-teacher">\${lecture.averageRate}</label>
                <label class="search-lecture-teacher">\${lecture.likeCount}</label>
            </div>
        `;

            // 클릭 이벤트 (상세 페이지로 이동)
            item.addEventListener('click', () => {
                window.location.href = `/lecture/\${lecture.lectureId}`;
            });

            return item;
        }







    });


</script>