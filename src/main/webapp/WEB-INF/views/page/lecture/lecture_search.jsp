<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_recommend.css'/>">

<div id="content">
    <div class="main-container">
        <div class="sidebar-container">
            <div class="recommend-nav">
                <a href="<c:url value='/lecture/recommend/SEARCH'/>" class="nav-item">전체검색</a>
                <a href="<c:url value='/lecture/recommend/MATH'/>" class="nav-item">수학</a>
                <a href="<c:url value='/lecture/recommend/ENGLISH'/>" class="nav-item">영어</a>
                <a href="<c:url value='/lecture/recommend/KOREAN'/>" class="nav-item">국어</a>
                <a href="<c:url value='/lecture/recommend/SCIENCE'/>" class="nav-item">과학탐구</a>
                <a href="<c:url value='/lecture/recommend/SOCIAL'/>" class="nav-item">사회탐구</a>
            </div>
        </div>

        <!-- ✅ 메인 컨텐츠 -->
        <div class="main-content-container">
            <div class="search-box">
                <form id="lecture-search-form" method="get" action="<c:url value='/lecture/recommend/search'/>">
                    <!-- 검색 구분 -->
                    <div class="filter-row">
                        <label>검색 구분:</label>
                        <select name="searchType">
                            <option value="ALL">전체</option>
                            <option value="TITLE">강의명</option>
                            <option value="CONTENT">내용</option>
                            <option value="TEACHER">선생님</option>
                        </select>

                        <label>검색어:</label>
                        <input type="text" name="keyword" placeholder="검색어를 입력하세요">
                    </div>

                    <!-- 카테고리 -->
                    <div class="filter-row">
                        <label>카테고리 1차:</label>
                        <label><input type="radio" name="category1" value="MATH"> 수학</label>
                        <label><input type="radio" name="category1" value="ENGLISH"> 영어</label>
                        <label><input type="radio" name="category1" value="KOREAN"> 국어</label>
                        <label><input type="radio" name="category1" value="SCIENCE"> 과학탐구</label>
                        <label><input type="radio" name="category1" value="SOCIAL"> 사회탐구</label>
                    </div>

                    <!-- ✅ 2차 카테고리 자동 변경 영역 -->
                    <div class="filter-row">
                        <label>카테고리 2차:</label>
                        <select name="category2" id="category2">
                            <option value="">-- 선택 --</option>
                        </select>
                    </div>

                    <!-- 가격 -->
                    <div class="filter-row">
                        <label>가격 범위:</label>
                        <input type="number" name="minPrice" placeholder="최소 금액" min="0">
                        ~
                        <input type="number" name="maxPrice" placeholder="최대 금액" min="0">
                    </div>

                    <!-- 난이도 -->
                    <div class="filter-row">
                        <label>난이도:</label>
                        <label><input type="radio" name="difficulty" value="BASIC"> 초급</label>
                        <label><input type="radio" name="difficulty" value="STANDARD"> 중급</label>
                        <label><input type="radio" name="difficulty" value="ADVANCED"> 고급</label>
                    </div>

                    <!-- 정렬 -->
                    <div class="filter-row">
                        <label>정렬 기준:</label>
                        <label><input type="radio" name="sort" value="LATEST" checked> 최신순</label>
                        <label><input type="radio" name="sort" value="RATING"> 평점순</label>
                        <label><input type="radio" name="sort" value="POPULAR"> 수강생순</label>
                    </div>

                    <!-- 검색 버튼 -->
                    <div class="filter-row" style="text-align:right;">
                        <button type="submit" class="search-btn">검색</button>
                    </div>
                </form>
            </div>

            <div class ="search-lecture-container">
                <div class="search-lecture-item">
                    <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
                    <div class="search-lecture-info">
                        <label class="search-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</label>
                        <label class="search-lecture-teacher">고창우</label>
                    </div>
                </div>
                <div class="search-lecture-item">
                    <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
                    <div class="search-lecture-info">
                        <label class="search-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</label>
                        <label class="search-lecture-teacher">고창우</label>
                    </div>
                </div>
                <div class="search-lecture-item">
                    <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
                    <div class="search-lecture-info">
                        <label class="search-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</label>
                        <label class="search-lecture-teacher">고창우</label>
                    </div>
                </div>
                <div class="search-lecture-item">
                    <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
                    <div class="search-lecture-info">
                        <label class="search-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</label>
                        <label class="search-lecture-teacher">고창우</label>
                    </div>
                </div>
                <div class="search-lecture-item">
                    <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
                    <div class="search-lecture-info">
                        <label class="search-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</label>
                        <label class="search-lecture-teacher">고창우</label>
                    </div>
                </div>
                <div class="search-lecture-item">
                    <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
                    <div class="search-lecture-info">
                        <label class="search-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</label>
                        <label class="search-lecture-teacher">고창우</label>
                    </div>
                </div>
                <div class="search-lecture-item">
                    <img src="<c:url value='/img/png/thumbnail.png'/>" class="lecture-thumbnail">
                    <div class="search-lecture-info">
                        <label class="search-lecture-title">일 잘하는 사람은 '이렇게' 말합니다</label>
                        <label class="search-lecture-teacher">고창우</label>
                    </div>
                </div>

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
    document.addEventListener("DOMContentLoaded", () => {
        const subCategories = {
            "MATH": ["수학 I", "수학 II", "확률과 통계"],
            "ENGLISH": ["문법", "독해", "듣기", "회화"],
            "KOREAN": ["문학", "비문학", "문법", "작문"],
            "SCIENCE": ["물리 I", "화학 I", "생명과학 I", "지구과학 I"],
            "SOCIAL": ["생활과 윤리", "윤리와 사상", "사회·문화", "한국지리"]
        };

        const categoryRadios = document.querySelectorAll("input[name='category1']");
        const category2Select = document.getElementById("category2");

        categoryRadios.forEach(radio => {
            radio.addEventListener("change", () => {
                const selected = radio.value;
                category2Select.innerHTML = '<option value="">-- 선택 --</option>';
                if (subCategories[selected]) {
                    subCategories[selected].forEach(sub => {
                        const option = document.createElement("option");
                        option.value = sub;
                        option.textContent = sub;
                        category2Select.appendChild(option);
                    });
                }
            });
        });
    });
</script>
