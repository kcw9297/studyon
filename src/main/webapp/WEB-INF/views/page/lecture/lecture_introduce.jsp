<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="lecture_detail.css">
</head>
<body>
<section class="introduce">
    <div id="intro" class="introduce-content">
        <div class="intro-text">
            강의소개 설명
        </div>
        <div class="intro-recommend">
            <div class="intro-chapter">
                <img src="<c:url value='/img/png/recommend.png'/>">
                <p>이런 분께 추천해요</p>
            </div>
            <ul class="intro-list">
                <li>수능에서 미적분 선택자 중 고득점을 목표로 하는 학생</li>
                <li>개념은 알지만 실전 적용이 부족한 학생</li>
                <li>기출을 완벽히 정리하고 싶거나, 최신 출제 트렌드에 맞춰 대비하고 싶은 학생</li>
                <li>제한된 시간에 효율적으로 전 범위를 학습하고 싶은 학생</li>
            </ul>
        </div>
        <div class="intro-study">
            <div class="intro-chapter">
                <img src="<c:url value='/img/png/study.png'/>">
                <p>이런 걸 배울 수 있어요</p>
            </div>
            <ul class="intro-list">
                <li>수능에서 미적분 선택자 중 고득점을 목표로 하는 학생</li>
                <li>개념은 알지만 실전 적용이 부족한 학생</li>
                <li>기출을 완벽히 정리하고 싶거나, 최신 출제 트렌드에 맞춰 대비하고 싶은 학생</li>
                <li>제한된 시간에 효율적으로 전 범위를 학습하고 싶은 학생</li>
            </ul>
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
            <tbody>
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
                <img src="<c:url value='/img/png/teacher1.png'/>">
                <div>
                    <strong>${teacher.member.nickname}</strong>
                    <p>강사</p>
                </div>
            </div>
            <div class="instructor-data">
                <div class="instructor-category">
                    <div class="instructor-item">
                        <p class="instructor-name">과목</p>
                        <p data-en="<c:out value='${teacher.subject}'/>"><c:out value="${teacher.subject}" /></p>
                    </div>
                    <div class="instructor-item">
                        <p class="instructor-name">수강생</p>
                        <p><fmt:formatNumber value="${teacher.totalStudents}" type="number"/>명</p>
                    </div>
                    <div class="instructor-item">
                        <p class="instructor-name">평점</p>
                        <p>${teacher.averageRating}점</p>
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
        <div class="reviews">
            <div class="reviews-sort">
                <select id="reviews-sort" name="reviews-sort">
                    <option value="recommend" selected>추천순</option>
                    <option value="latest">최신순</option>
                    <option value="high-rating">높은 평점순</option>
                    <option value="low-rating">낮은 평점순</option>
                </select>
            </div>
            <div class="reviews-situation">
                <div class="reviews-total">
                    <strong>${lecture.averageRate}</strong>
                    <p>853개의 수강평</p>
                </div>
                <div class="reviews-statistics">
                    <div class="reviews-item">
                        <div class="reviews-star">★★★★★</div>
                        <div class="reviews-percent">000%</div>
                    </div>
                    <div class="reviews-item">
                        <div class="reviews-star">★★★★</div>
                        <div class="reviews-percent">000%</div>
                    </div>
                    <div class="reviews-item">
                        <div class="reviews-star">★★★</div>
                        <div class="reviews-percent">000%</div>
                    </div>
                    <div class="reviews-item">
                        <div class="reviews-star">★★</div>
                        <div class="reviews-percent">000%</div>
                    </div>
                    <div class="reviews-item">
                        <div class="reviews-star">★</div>
                        <div class="reviews-percent">000%</div>
                    </div>
                </div>
            </div>
            <ul class="reviews-list">
                <li class="reviews-comment">
                    <div class="reviews-user">
                        <img src="<c:url value='/img/png/user.png'/>">푸들
                    </div>
                    <div class="reviews-content">
                        <div class="reviews-top">
                            <div class="reviews-god">★</div>
                            <div class="reviews-god">★</div>
                            <div class="reviews-god">★</div>
                            <div class="reviews-god">★</div>
                            <div class="reviews-god">★</div>
                            <div class="reviews-day">2025.10.20.</div>
                        </div>
                        <div class="reviews-bottom">
                            <p>쌤 덕분에 10모 1등급 받았어요</p>
                        </div>
                    </div>
                </li>
            </ul>
            <button class="reviews-more">더보기</button>
        </div>
    </div>
</section>
</body>
</html>
