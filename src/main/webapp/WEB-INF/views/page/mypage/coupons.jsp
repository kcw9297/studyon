<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<section class="coupons">
    <div class="mypage-title">쿠폰</div>
    <div>
        <div class="coupons-issue">
            <div class="coupons-issue">
                <div class="coupons-input">
                    <input id="coupon-cord" type="text" placeholder="쿠폰 코드를 입력하세요.">
                </div>
                <div class="coupons-button">
                    <button type="submit" disabled>쿠폰 발급</button>
                </div>
            </div>
        </div>
        <ul class="coupons-list">
            <li class="coupons-item">
                <button>
                    <div class="coupons-top">
                        <div class="coupons-book">
                            <div class="coupons-count">50,000원</div>
                            <div class="coupons-countdown">D-7</div>
                        </div>
                        <div class="coupons-big">[웰컴혜택] 신규회원 5만 원 할인 쿠폰</div>
                        <div class="coupons-middle">2025.10.31 까지</div>
                    </div>
                    <div class="coupons-bottom">
                        <div class="coupons-open">
                            <div class="coupons-middle">유의사항</div>
                            <img src="/static/mypage/images/demonstration.png" alt="유의사항">
                        </div>
                        <div class="coupons-small">
                            <p>- 본 웰컴쿠폰은 [30만 원] 이상 강의 결제 시 사용 가능합니다.</p>
                            <p>- 웰컴쿠폰은 발급일(회원가입일)로부터 7일 이내 사용 가능합니다. 기간 내 미사용 시 자동 소멸하며, 재발급은 불가합니다.</p>
                            <p>- 웰컴쿠폰을 사용하여 결제한 클래스 환불 시 사용하신 쿠폰의 재발급은 불가합니다.</p>
                            <p>- 웰컴쿠폰 적용 대상 클래스는 아래 이벤트 페이지에서 확인해 주세요.</p>
                            <p>(https://coloso.co.kr/event/welcomebenefit)</p>
                            <p>- 웰컴쿠폰 1장당 1개의 강의에 적용 가능합니다.</p>
                            <p>- 웰컴쿠폰은 타 쿠폰과 중복 사용이 불가합니다.</p>
                            <p>- 웰컴쿠폰을 사용하여 결제한 강의 환불 시 사용하신 웰컴쿠폰의 재발급은 불가합니다.</p>
                        </div>
                    </div>
                </button>
            </li>

            <!-- 이하 다른 쿠폰 항목들 그대로 유지 -->
            <li class="coupons-item">
                <button>
                    <div class="coupons-top">
                        <div class="coupons-book">
                            <div class="coupons-count">50,000원</div>
                            <div class="coupons-countdown">D-7</div>
                        </div>
                        <div class="coupons-big">[웰컴혜택] 신규회원 5만 원 할인 쿠폰</div>
                        <div class="coupons-middle">2025.10.31 까지</div>
                    </div>
                    <div class="coupons-bottom">
                        <div class="coupons-close">
                            <div class="coupons-middle">유의사항</div>
                            <img src="/static/mypage/images/demonstration.png" alt="유의사항">
                        </div>
                    </div>
                </button>
            </li>
        </ul>
    </div>
</section>
