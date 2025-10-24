<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<section class="likes">
    <div class="mypage-title">관심 목록</div>
    <div>
        <div class="category">
            <div class="subject">
                <input type="checkbox" name="subject" value="국어" id="korean" checked>
                <label>국어</label>
            </div>
            <div class="subject">
                <input type="checkbox" name="subject" value="수학" id="mathematics">
                <label>수학</label>
            </div>
            <div class="subject">
                <input type="checkbox" name="subject" value="영어" id="english">
                <label>영어</label>
            </div>
            <div class="subject">
                <input type="checkbox" name="subject" value="사회탐구" id="social">
                <label>사회탐구</label>
            </div>
            <div class="subject">
                <input type="checkbox" name="subject" value="과학탐구" id="science">
                <label>과학탐구</label>
            </div>
        </div>
        <div class="likes-list">
            <a class="likes-item" href="">
                <div class="likes-thumbnail">
                    <img src="/static/mypage/images/thumbnail1.png">
                    <div class="likes-deleteIcon">
                        <img src="/static/mypage/images/delete.png">
                    </div>
                </div>
                <div class="likes-lecture">2025 수능특강 : 미적분</div>
                <div class="likes-teacher">전성홍 강사</div>
                <div class="likes-price">
                    <div class="likes-present">90,000원</div>
                    <div class="likes-discount">11%</div>
                    <div class="likes-special">80,000원</div>
                </div>
                <div class="likes-report">
                    <div class="likes-review">
                        <div class="likes-star">★</div>
                        <div class="likes-score">4.8</div>
                        <div class="likes-count">(350)</div>
                    </div>
                    <div class="likes-student">
                        <div class="likes-member">
                            <img src="/static/mypage/images/student.png">
                        </div>
                        <div class="likes-total">1,200</div>
                    </div>
                </div>
            </a>
            <a class="likes-item" href="">
                <div class="likes-thumbnail">
                    <img src="/static/mypage/images/thumbnail2.png">
                    <div class="likes-deleteIcon">
                        <img src="/static/mypage/images/delete.png">
                    </div>
                </div>
                <div class="likes-lecture">기출문제 : 25년도 9월 모의고사</div>
                <div class="likes-teacher">이나래 강사</div>
                <div class="likes-price">
                    <div class="likes-present">90,000원</div>
                    <div class="likes-discount">11%</div>
                    <div class="likes-special">80,000원</div>
                </div>
                <div class="likes-report">
                    <div class="likes-review">
                        <div class="likes-star">★</div>
                        <div class="likes-score">4.8</div>
                        <div class="likes-count">(350)</div>
                    </div>
                    <div class="likes-student">
                        <div class="likes-member">
                            <img src="/static/mypage/images/student.png">
                        </div>
                        <div class="likes-total">1,200</div>
                    </div>
                </div>
            </a>
            <a class="likes-item" href="">
                <div class="likes-thumbnail">
                    <img src="/static/mypage/images/thumbnail3.png">
                    <div class="likes-deleteIcon">
                        <img src="/static/mypage/images/delete.png">
                    </div>
                </div>
                <div class="likes-lecture">전교1등의 노트필기법</div>
                <div class="likes-teacher">최수아 강사</div>
                <div class="likes-price">
                    <div class="likes-present">90,000원</div>
                    <div class="likes-discount">11%</div>
                    <div class="likes-special">80,000원</div>
                </div>
                <div class="likes-report">
                    <div class="likes-review">
                        <div class="likes-star">★</div>
                        <div class="likes-score">4.8</div>
                        <div class="likes-count">(350)</div>
                    </div>
                    <div class="likes-student">
                        <div class="likes-member">
                            <img src="/static/mypage/images/student.png">
                        </div>
                        <div class="likes-total">1,200</div>
                    </div>
                </div>
            </a>
            <a class="likes-item" href="">
                <div class="likes-thumbnail">
                    <img src="/static/mypage/images/thumbnail4.png">
                    <div class="likes-deleteIcon">
                        <img src="/static/mypage/images/delete.png">
                    </div>
                </div>
                <div class="likes-lecture">수능기본 : 화학II</div>
                <div class="likes-teacher">김성하 강사</div>
                <div class="likes-price">
                    <div class="likes-present">90,000원</div>
                    <div class="likes-discount">11%</div>
                    <div class="likes-special">80,000원</div>
                </div>
                <div class="likes-report">
                    <div class="likes-review">
                        <div class="likes-star">★</div>
                        <div class="likes-score">4.8</div>
                        <div class="likes-count">(350)</div>
                    </div>
                    <div class="likes-student">
                        <div class="likes-member">
                            <img src="/static/mypage/images/student.png">
                        </div>
                        <div class="likes-total">1,200</div>
                    </div>
                </div>
            </a>
        </div>
    </div>
</section>