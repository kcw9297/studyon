<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/lecture/lecture_detail.css'/>">
<div id="content">
    <div id="lecture">
        <section class="summary">
            <div class="summary-top">
                <div class="summary-text">
                    <div class="summary-lecture">
                        <div class="summary-tag">
                            <a href="">고등학교(3)</a>
                            &nbsp;&nbsp;/&nbsp;&nbsp;
                            <a href="">미적분</a>
                        </div>
                        <div class="summary-name">2025 수능특강 : 미적분</div>
                        <div class="summary-explain">
                            <p>“개념은 탄탄하게, 문제풀이는 날카롭게”</p>
                            <p>단순 암기가 아닌 이해 중심 학습으로 수학적 사고력을 키워드립니다.</p>
                        </div>
                    </div>
                    <div class="summary-report">
                        <div class="summary-review">
                            <div class="summary-star">★&nbsp;</div>
                            <div class="summary-score">(4.8)&nbsp;</div>
                            <div class="summary-count">수강평 1,853개</div>
                        </div>
                        <div class="summary-student">
                            <div class="summary-member">
                                <img src="images/student.png">
                            </div>
                            <div class="summary-total">&nbsp;수강생 12,340명</div>
                        </div>
                    </div>
                </div>
                <div class="summary-thumbnail">
                    <img src="images/thumbnail1.png">
                </div>
            </div>
            <div class="summary-bottom">
                <div class="summary-introduce">
                    <div class="summary-category">
                        <p>강사 :</p>
                        <p>커리큘럼 :</p>
                        <p>강의 시간 :</p>
                        <p>난이도 :</p>
                    </div>
                    <div class="summary-item">
                        <p>전성홍</p>
                        <p>30강</p>
                        <p>35시간</p>
                        <p>고급</p>
                    </div>
                </div>
                <div class="summary-pay">
                    <div class="summary-count">
                        <div>
                            <p>최대</p>
                            <em class="summary-red">11%</em>
                            <p>할인</p>
                        </div>
                        <div>
                            <s class="summary-lean">90,000원</s>
                            <strong class="summary-big">80,000원</strong>
                        </div>
                    </div>
                    <div class="summary-coupon">
                        <div>
                            <p>쿠폰 할인</p>
                        </div>
                        <div>
                            <p>최대</p>
                            <em class="summary-red">10,000원</em>
                        </div>
                    </div>
                    <div class="summary-buttons">
                        <button class="summary-like">
                            <img src="images/like1.png">1250
                        </button>
                        <button class="summary-purchase">바로 구매하기</button>
                    </div>
                </div>
            </div>
        </section>
        <nav class="navigation">
            <div class="navigation-category">
                <button class="navigation-item">
                    <a href="">강의소개</a>
                </button>
                <button class="navigation-item">
                    <a href="">커리큘럼</a>
                </button>
                <button class="navigation-item">
                    <a href="">강사이력</a>
                </button>
                <button class="navigation-item">
                    <a href="">수강평</a>
                </button>
            </div>
        </nav>
        <section class="introduce">
            <div class="intro">
                <div class="intro-text">
                    강의소개 설명
                </div>
                <div class="intro-recommend">
                    <div class="intro-chapter">
                        <img src="images/recommend.png">
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
                        <img src="images/study.png">
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
            <div class="introduce-content">
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
                        <th>단계</th>
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
            <div class="introduce-content">
                <div class="introduce-title">강사이력</div>
                <div class="instructor">
                    <div class="instructor-teacher">
                        <img src="images/teacher1.png">
                        <div>
                            <strong>전성홍</strong>
                            <p>강사</p>
                        </div>
                    </div>
                    <div class="instructor-data">
                        <div class="instructor-category">
                            <div class="instructor-item">
                                <p class="instructor-name">강의</p>
                                <p>8개</p>
                            </div>
                            <div class="instructor-item">
                                <p class="instructor-name">수강생</p>
                                <p>10,000명</p>
                            </div>
                            <div class="instructor-item">
                                <p class="instructor-name">평점</p>
                                <p>4.8점</p>
                            </div>
                            <div class="instructor-item">
                                <p class="instructor-name">수강평</p>
                                <p>10,000개</p>
                            </div>
                        </div>
                        <div class="instructor-word">
                            <p>안녕하세요, 전성홍 강사입니다.</p>
                            <p>고등학교 3학년 수학을 담당하고 있습니다.</p>
                            <p>수학이 가장 쉬운 과목이 되도록 노력하겠습니다.</p>
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
            <div class="introduce-content">
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
                            <strong>4.8</strong>
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
                                <img src="images/user.png">푸들
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
                                    <p>따라가기만 하면 어케든 됨ㅇㅇ</p>
                                </div>
                            </div>
                        </li>
                        <li class="reviews-comment">
                            <div class="reviews-user">
                                <img src="images/user.png">치와와
                            </div>
                            <div class="reviews-content">
                                <div class="reviews-top">
                                    <div class="reviews-god">★</div>
                                    <div class="reviews-god">★</div>
                                    <div class="reviews-bad">★</div>
                                    <div class="reviews-bad">★</div>
                                    <div class="reviews-bad">★</div>
                                    <div class="reviews-day">2025.10.20.</div>
                                </div>
                                <div class="reviews-bottom">
                                    <p>주말이 없다</p>
                                </div>
                            </div>
                        </li>
                    </ul>
                    <button class="reviews-more">더보기</button>
                </div>
            </div>
        </section>
    </div>
    <section class="algorithm">
        <div class="algorithm-title">
            추천 강의
            <div class="algorithm-over">
                <button class="algorithm-no">
                    <img src="images/over1.png">
                </button>
                <button class="algorithm-yes">
                    <img src="images/over2.png">
                </button>
            </div>
        </div>
        <div class="algorithm-list">
            <a class="algorithm-item" href="">
                <div class="algorithm-thumbnail">
                    <img src="images/thumbnail1.png">
                </div>
                <div class="algorithm-lecture">2025 수능특강 : 미적분</div>
                <div class="algorithm-teacher">전성홍 강사</div>
                <div class="algorithm-price">
                    <div class="algorithm-present">90,000원</div>
                    <div class="algorithm-discount">11%</div>
                    <div class="algorithm-special">80,000원</div>
                </div>
                <div class="algorithm-report">
                    <div class="algorithm-review">
                        <div class="algorithm-star">★</div>
                        <div class="algorithm-score">4.8</div>
                        <div class="algorithm-count">(350)</div>
                    </div>
                    <div class="algorithm-student">
                        <div class="algorithm-member">
                            <img src="images/student.png">
                        </div>
                        <div class="algorithm-total">1,200</div>
                    </div>
                </div>
            </a>
            <a class="algorithm-item" href="">
                <div class="algorithm-thumbnail">
                    <img src="images/thumbnail1.png">
                </div>
                <div class="algorithm-lecture">2025 수능특강 : 미적분</div>
                <div class="algorithm-teacher">전성홍 강사</div>
                <div class="algorithm-price">
                    <div class="algorithm-present">90,000원</div>
                    <div class="algorithm-discount">11%</div>
                    <div class="algorithm-special">80,000원</div>
                </div>
                <div class="algorithm-report">
                    <div class="algorithm-review">
                        <div class="algorithm-star">★</div>
                        <div class="algorithm-score">4.8</div>
                        <div class="algorithm-count">(350)</div>
                    </div>
                    <div class="algorithm-student">
                        <div class="algorithm-member">
                            <img src="images/student.png">
                        </div>
                        <div class="algorithm-total">1,200</div>
                    </div>
                </div>
            </a>
            <a class="algorithm-item" href="">
                <div class="algorithm-thumbnail">
                    <img src="images/thumbnail1.png">
                </div>
                <div class="algorithm-lecture">2025 수능특강 : 미적분</div>
                <div class="algorithm-teacher">전성홍 강사</div>
                <div class="algorithm-price">
                    <div class="algorithm-present">90,000원</div>
                    <div class="algorithm-discount">11%</div>
                    <div class="algorithm-special">80,000원</div>
                </div>
                <div class="algorithm-report">
                    <div class="algorithm-review">
                        <div class="algorithm-star">★</div>
                        <div class="algorithm-score">4.8</div>
                        <div class="algorithm-count">(350)</div>
                    </div>
                    <div class="algorithm-student">
                        <div class="algorithm-member">
                            <img src="images/student.png">
                        </div>
                        <div class="algorithm-total">1,200</div>
                    </div>
                </div>
            </a>
            <a class="algorithm-item" href="">
                <div class="algorithm-thumbnail">
                    <img src="images/thumbnail2.png">
                </div>
                <div class="algorithm-lecture">2025 수능특강 : 미적분</div>
                <div class="algorithm-teacher">전성홍 강사</div>
                <div class="algorithm-price">
                    <div class="algorithm-present">90,000원</div>
                    <div class="algorithm-discount">11%</div>
                    <div class="algorithm-special">80,000원</div>
                </div>
                <div class="algorithm-report">
                    <div class="algorithm-review">
                        <div class="algorithm-star">★</div>
                        <div class="algorithm-score">4.8</div>
                        <div class="algorithm-count">(350)</div>
                    </div>
                    <div class="algorithm-student">
                        <div class="algorithm-member">
                            <img src="images/student.png">
                        </div>
                        <div class="algorithm-total">1,200</div>
                    </div>
                </div>
            </a>
            <a class="algorithm-item" href="">
                <div class="algorithm-thumbnail">
                    <img src="images/thumbnail3.png">
                </div>
                <div class="algorithm-lecture">2025 수능특강 : 미적분</div>
                <div class="algorithm-teacher">전성홍 강사</div>
                <div class="algorithm-price">
                    <div class="algorithm-present">90,000원</div>
                    <div class="algorithm-discount">11%</div>
                    <div class="algorithm-special">80,000원</div>
                </div>
                <div class="algorithm-report">
                    <div class="algorithm-review">
                        <div class="algorithm-star">★</div>
                        <div class="algorithm-score">4.8</div>
                        <div class="algorithm-count">(350)</div>
                    </div>
                    <div class="algorithm-student">
                        <div class="algorithm-member">
                            <img src="images/student.png">
                        </div>
                        <div class="algorithm-total">1,200</div>
                    </div>
                </div>
            </a>
            <a class="algorithm-item" href="">
                <div class="algorithm-thumbnail">
                    <img src="images/thumbnail4.png">
                </div>
                <div class="algorithm-lecture">2025 수능특강 : 미적분</div>
                <div class="algorithm-teacher">전성홍 강사</div>
                <div class="algorithm-price">
                    <div class="algorithm-present">90,000원</div>
                    <div class="algorithm-discount">11%</div>
                    <div class="algorithm-special">80,000원</div>
                </div>
                <div class="algorithm-report">
                    <div class="algorithm-review">
                        <div class="algorithm-star">★</div>
                        <div class="algorithm-score">4.8</div>
                        <div class="algorithm-count">(350)</div>
                    </div>
                    <div class="algorithm-student">
                        <div class="algorithm-member">
                            <img src="images/student.png">
                        </div>
                        <div class="algorithm-total">1,200</div>
                    </div>
                </div>
            </a>
        </div>
    </section>
</div>

<script src="<c:url value='/js/page/lecture/lecture_detail.js'/>"></script>