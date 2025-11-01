<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_detail.css'/>">
</head>
<body>
<section class="introduce">
    <div id="intro" class="introduce-content">
        <div class="intro-text">
            강의소개 설명
        </div>
    </div>
    <div id="curriculum" class="introduce-content" >
        <div class="introduce-title">커리큘럼</div>
        <table class="curriculum">
            <colgroup>
                <col width="35%">
                <col width="10%">
                <col width="45%">
                <col width="10%">
            </colgroup>
            <tbody id="curriculum-body">
            <tr class="curriculum-category">
                <th>챕터</th>
                <th>차수</th>
                <th>제목</th>
                <th>시간</th>
            </tr>
            <tr class="curriculum-item">
                <td>수열의 극한</td>
                <td>1강</td>
                <td>수열의 수렴과 발산</td>
                <td>27분</td>
            </tr>
            <tr class="curriculum-item">
                <td></td>
                <td>2강</td>
                <td>수열의 극한에 대한 기본 성질 및 진위판정</td>
                <td>30분</td>
            </tr>
            <tr class="curriculum-item">
                <td></td>
                <td>3강</td>
                <td>극한값의 계산 및 대소관계</td>
                <td>32분</td>
            <tr class="curriculum-item">
                <td>여러 가지 함수의 미분</td>
                <td>4강</td>
                <td>지수함수 로그함수의 극한</td>
                <td>32분</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div id="instructor" class="introduce-content">
        <div class="introduce-title">강사이력</div>
        <div class="instructor">
            <div class="instructor-teacher">
                <c:choose>
                    <c:when test="${not empty teacher.member.profileImage and teacher.member.profileImage.fileId ne 0}">
                    </c:when>
                    <c:otherwise>
                        <img src="<c:url value='/img/png/default_member_profile_image.png'/>" alt="기본 이미지">
                    </c:otherwise>
                </c:choose>
                <div>
                    <strong>${teacher.member.nickname}</strong>
                    <p>강사</p>
                </div>
            </div>
            <div class="instructor-data">
                <div class="instructor-category">
                    <div class="instructor-item">
                        <p class="instructor-name">과목</p>
                        <p data-en="<c:out value='${teacher.subject}'/>"><c:out value="${teacher.subject}"/></p>
                    </div>
                    <div class="instructor-item">
                        <p class="instructor-name">수강생</p>
                        <p><fmt:formatNumber value="${teacher.totalStudents}" type="number"/>명</p>
                    </div>
                    <div class="instructor-item">
                        <p class="instructor-name">평점</p>
                        <p><fmt:formatNumber value="${teacher.averageRating}" pattern="#0.0"/>점</p>
                    </div>
                    <div class="instructor-item">
                        <p class="instructor-name">수강평</p>
                        <p><fmt:formatNumber value="${teacher.totalReview}" type="number"/>개</p>
                    </div>
                </div>
                <div class="instructor-word">
                    <p>${teacher.description}</p>
                </div>
            </div>
            <div class="instructor-history">
                <p>이력</p>
                <ul>
                    <li>국내 최상위권 학생 다수 배출, 누적 수강생 10만+</li>
                    <li>○○대학교 수학과 졸업, 교육학 석사</li>
                    <li>前 ○○학원/○○교육 수학 대표 강사</li>
                    <li>『수능 수학 필수 개념집』 저자</li>
                    <li>EBS 연계 교재 집필 참여</li>
                </ul>
            </div>
        </div>
    </div>
    <div id="reviews" class="introduce-content">
        <div class="introduce-title">수강평</div>
        <%--효상 작성--%>

        <div class="review-input-box">
            <form>
                <textarea id="review-content" placeholder="수강 후기를 작성해주세요." maxlength="300"></textarea>
                <div>
                    <select id="review-rate">
                        <option value="5">★★★★★ (5점)</option>
                        <option value="4">★★★★☆ (4점)</option>
                        <option value="3">★★★☆☆ (3점)</option>
                        <option value="2">★★☆☆☆ (2점)</option>
                        <option value="1">★☆☆☆☆ (1점)</option>
                    </select>
                    <button id="review-submit-Btn" class="submit">등록</button>
                </div>
            </form>
        </div>

        <%--효상 작성--%>
        <div class="reviews">
            <div class="reviews-situation">
                <div class="reviews-total">
                    <strong><fmt:formatNumber value="${lecture.averageRate}" pattern="#0.0"/></strong>
                    <p><fmt:formatNumber value="${reviewCount}" type="number"/>개의 수강평</p>
                </div>
                <div class="reviews-statistics">

                <c:forEach var="i" begin="1" end="5">
                    <div class="reviews-item">
                        <div class="reviews-star">
                            <c:forEach var="j" begin="1" end="${i}">★</c:forEach>
                        </div>
                        <div class="reviews-percent">
                            <fmt:formatNumber value="${ratingPercent[i] != null ? ratingPercent[i] : 0}" pattern="0"/>%
                        </div>
                    </div>
                </c:forEach>

                </div>
            </div>
            <ul class="reviews-list">

                <c:if test="${not empty reviews}">
                    <c:forEach var="review" items="${reviews}">
                        <li class="reviews-comment">
                            <div class="reviews-user">
                                <c:choose>
                                    <c:when test="${not empty review.member.profileImage and review.member.profileImage.fileId ne 0}">
                                        <img src="${fileDomain}/${review.member.profileImage.filePath}" alt="프로필 이미지">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="<c:url value='/img/png/default_member_profile_image.png'/>" alt="기본 이미지">
                                    </c:otherwise>
                                </c:choose>
                                    ${review.member.nickname}
                            </div>
                            <div class="reviews-content">
                                <div class="reviews-top">
                                    <div class="reviews-god">
                                        <c:forEach begin="1" end="${review.rating}">
                                            <span class="reviews-god filled">★</span>
                                        </c:forEach>
                                        <c:forEach begin="1" end="${5 - review.rating}">
                                            <span class="reviews-god empty">★</span>
                                        </c:forEach>
                                    </div>
                                    <div class="reviews-day">
                                        <c:out value="${fn:substring(review.createdAt, 0, 10)}"/>
                                    </div>
                                </div>
                                <div class="reviews-bottom">
                                    <p>${review.content}</p>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </c:if>


            </ul>
            <button class="reviews-more">더보기</button>
        </div>
    </div>
</section>
</body>
</html>


<style>
    /*효상 작성*/
    /* ✅ 리뷰 입력 박스 전체 */
    .review-input-box {
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        margin-bottom: 30px;
        background: #fafafa;
    }

    /* ✅ 텍스트 입력창 */
    .review-input-box textarea {
        width: 100%;
        min-height: 80px;
        border: 1px solid #ccc;
        border-radius: 6px;
        padding: 10px;
        font-size: 15px;
        resize: vertical;
        outline: none;
        box-sizing: border-box;
        margin-bottom: 10px;
    }

    /* ✅ 하단 select + 버튼 정렬 */
    .review-input-box div {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        gap: 10px;
    }

    /* ✅ 별점 선택창 */
    .review-input-box select {
        padding: 6px 10px;
        font-size: 14px;
        border-radius: 6px;
        border: 1px solid;
    }

    /* CURRICULUM TABLE DESIGN By KHS */
    .curriculum {
        width: 100%;
        border-collapse: collapse;
        margin-top: 15px;
        font-family: 'Noto Sans KR', sans-serif;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 2px 8px rgba(0,0,0,0.05);
    }

    /* 상단 헤더 (챕터, 차수, 제목, 시간) */
    .curriculum-category th {
        background: linear-gradient(90deg, #f7f8fa 0%, #eef2f7 100%);
        color: #2c3e50;
        font-weight: 700;
        padding: 12px 10px;
        border-bottom: 2px solid #dcdfe6;
        font-size: 15px;
    }

    /* 일반 셀 스타일 */
    .curriculum-item td {
        padding: 12px 10px;
        border-bottom: 1px solid #eee;
        font-size: 14px;
        color: #333;
    }

    /* 홀짝 줄 구분 */
    .curriculum-item:nth-child(even) td {
        background-color: #fafafa;
    }

    /* 챕터(첫 번째 컬럼) 강조 */
    .curriculum-item td:first-child {
        font-weight: 600;
        color: #34495e;
    }

    /* hover 효과 */
    .curriculum-item:hover td {
        background-color: #f2f6fc;
        transition: background-color 0.2s ease-in-out;
    }

    /* 반응형 - 모바일에서 줄여도 보기 좋게 */
    @media screen and (max-width: 768px) {
        .curriculum-category th,
        .curriculum-item td {
            font-size: 13px;
            padding: 10px 6px;
        }
    }


</style>

<script>
    //효상작성

    //수강평 등록
    const pathParts = window.location.pathname.split("/");
    const lectureId = pathParts[pathParts.length - 1];
    const submitBtn = document.getElementById("review-submit-Btn");

    submitBtn.addEventListener("click", async (e) => {
        e.preventDefault(); // 폼 새로고침 방지

        const pathParts = window.location.pathname.split("/");
        const lectureId = pathParts[pathParts.length - 1];
        const submitBtn = document.getElementById("review-submit-Btn");
        console.log("📘 lectureId =", lectureId);

        try {
            const reviewRate = document.getElementById("review-rate");
            const reviewContent = document.getElementById("review-content");

            // ✅ 입력 유효성 검사
            if (!reviewContent.value.trim()) {
                alert("내용을 입력해주세요!");
                return;
            }

            // ✅ 전송 데이터 구성
            const formData = new FormData();
            formData.append("lectureId", lectureId);
            formData.append("rating", reviewRate.value);
            formData.append("content", reviewContent.value);

            // ✅ POST 요청
            const res = await fetch("/api/lectures/reviews/create", {
                method: "POST",
                body: formData
            });

            // ✅ 응답 처리
            if (res.ok) {
                const data = await res.json();
                console.log("📩 등록 성공:", data);
                alert("수강평이 등록되었습니다!");
                location.reload(); // 새로고침으로 즉시 반영
            } else {
                console.error("⚠️ 서버 응답 오류:", res.status);
                alert("수강평 등록 요청에 실패했습니다.");
            }

        } catch (err) {
            console.error("❌ 수강평 등록 중 오류:", err);
            alert("수강평 등록 중 문제가 발생했습니다.");
        }
    });

    //강의 index 불러오기
        console.log("🎯 lectureId =", lectureId);

    // ✅ 강의 index 불러오기
    (async () => {
        console.log("🎯 lectureId =", lectureId);

        // ✅ 커리큘럼 불러오기 API 호출
        try {
            const response = await fetch("/lecture/detail/curriculum/" + lectureId);
            if (!response.ok) throw new Error("서버 응답 오류 " + response.status);

            const json = await response.json();
            const curriculumList = json.data;
            console.log("📚 커리큘럼 리스트:", curriculumList);

            const tbody = document.getElementById("curriculum-body");
            tbody.innerHTML = ""; // 기존 내용 제거

            curriculumList.forEach((item, idx) => {
                const tr = document.createElement("tr");
                tr.classList.add("curriculum-item");

                tr.innerHTML = `
            <td>\${item.lectureTitle || "-"}</td>
            <td>\${item.indexNumber ? item.indexNumber + "강" : "-"}</td>
            <td>\${item.indexTitle || "제목 없음"}</td>
            <td>\${item.videoFileName || "-"}</td>
        `;

                tbody.appendChild(tr);
            });

        } catch (err) {
            console.error("❌ 커리큘럼 불러오기 실패:", err);
        }

    })();







</script>