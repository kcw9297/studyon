<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<div id="empty-box"></div>
<div id="empty-box"></div>

<div>
    <div id="mypage-container">
        <aside id="aside">
            <nav class="sidebar">
                <p class="sidebar-title">Home</p>
                <ui>
                    <li class="sidebar-text">
                        <a href="">
                            <input type="radio" name="mypage" value="계정 정보" id="account" checked>
                            <span>계정 정보</span>
                        </a>
                    </li>
                </ui>
                <p class="sidebar-title">학습 관리</p>
                <ui>
                    <li class="sidebar-text">
                        <a href="">
                            <input type="radio" name="mypage" value="강의 관리" id="courses">
                            <span>강의 관리</span>
                        </a>
                    </li>
                </ui>
                <p class="sidebar-title">장바구니</p>
                <ui>
                    <li class="sidebar-text">
                        <a href="">
                            <input type="radio" name="mypage" value="쿠폰" id="coupons">
                            <span>쿠폰</span>
                        </a>
                    </li>
                    <li class="sidebar-text">
                        <a href="">
                            <input type="radio" name="mypage" value="관심 목록" id="likes">
                            <span>관심 3목록</span>
                        </a>
                    </li>
                    <li class="sidebar-text">
                        <a href="">
                            <input type="radio" name="mypage" value="구매 내역" id="payments">
                            <span>구매 내역</span>
                        </a>
                    </li>
                </ui>
            </nav>
        </aside>
        <div id="content">


            <section class="mypage-section">
                <jsp:include page="${bodyPage}" />
<%--                <div class="mypage-title">쿠폰</div>--%>
<%--                <div>--%>
<%--                    <div class="coupons-issue">--%>
<%--                        <div class="coupons-issue">--%>
<%--                            <div class="coupons-input">--%>
<%--                                <input id="coupon-cord" type="text" placeholder="쿠폰 코드를 입력하세요.">--%>
<%--                            </div>--%>
<%--                            <div class="coupons-button">--%>
<%--                                <button type="submit" disabled>쿠폰 발급</button>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <ul class="coupons-list">--%>
<%--                        <li class="coupons-item">--%>
<%--                            <button>--%>
<%--                                <div class="coupons-top">--%>
<%--                                    <div class="coupons-book">--%>
<%--                                        <div class="coupons-count">50,000원</div>--%>
<%--                                        <div class="coupons-countdown">D-7</div>--%>
<%--                                    </div>--%>
<%--                                    <div class="coupons-big">[웰컴혜택] 신규회원 5만 원 할인 쿠폰</div>--%>
<%--                                    <div class="coupons-middle">2025.10.31 까지</div>--%>
<%--                                </div>--%>
<%--                                <div class="coupons-bottom">--%>
<%--                                    <div class="coupons-open">--%>
<%--                                        <div class="coupons-middle">유의사항</div>--%>
<%--                                        <img src="/static/mypage/images/demonstration.png">--%>
<%--                                    </div>--%>
<%--                                    <div class="coupons-small">--%>
<%--                                        <p>- 본 웰컴쿠폰은 [30만 원] 이상 강의 결제 시 사용 가능합니다.</p>--%>
<%--                                        <p>- 웰컴쿠폰은 발급일(회원가입일)로부터 7일 이내 사용 가능합니다. 기간 내 미사용 시 자동 소멸하며, 재발급은 불가합니다.</p>--%>
<%--                                        <p>- 웰컴쿠폰을 사용하여 결제한 클래스 환불 시 사용하신 쿠폰의 재발급은 불가합니다.</p>--%>
<%--                                        <p>- 웰컴쿠폰 적용 대상 클래스는 아래 이벤트 페이지에서 확인해 주세요.</p>--%>
<%--                                        <p>(https://coloso.co.kr/event/welcomebenefit)</p>--%>
<%--                                        <p>- 웰컴쿠폰 1장당 1개의 강의에 적용 가능합니다.</p>--%>
<%--                                        <p>- 웰컴쿠폰은 타 쿠폰과 중복 사용이 불가합니다.</p>--%>
<%--                                        <p>- 웰컴쿠폰을 사용하여 결제한 강의 환불 시 사용하신 웰컴쿠폰의 재발급은 불가합니다.</p>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </button>--%>
<%--                        </li>--%>
<%--                        <li class="coupons-item">--%>
<%--                            <button>--%>
<%--                                <div class="coupons-top">--%>
<%--                                    <div class="coupons-book">--%>
<%--                                        <div class="coupons-count">50,000원</div>--%>
<%--                                        <div class="coupons-countdown">D-7</div>--%>
<%--                                    </div>--%>
<%--                                    <div class="coupons-big">[웰컴혜택] 신규회원 5만 원 할인 쿠폰</div>--%>
<%--                                    <div class="coupons-middle">2025.10.31 까지</div>--%>
<%--                                </div>--%>
<%--                                <div class="coupons-bottom">--%>
<%--                                    <div class="coupons-close">--%>
<%--                                        <div class="coupons-middle">유의사항</div>--%>
<%--                                        <img src="/static/mypage/images/demonstration.png">--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </button>--%>
<%--                        </li>--%>
<%--                        <li class="coupons-item">--%>
<%--                            <button>--%>
<%--                                <div class="coupons-top">--%>
<%--                                    <div class="coupons-book">--%>
<%--                                        <div class="coupons-count">50,000원</div>--%>
<%--                                        <div class="coupons-countdown">D-7</div>--%>
<%--                                    </div>--%>
<%--                                    <div class="coupons-big">[웰컴혜택] 신규회원 5만 원 할인 쿠폰</div>--%>
<%--                                    <div class="coupons-middle">2025.10.31 까지</div>--%>
<%--                                </div>--%>
<%--                                <div class="coupons-bottom">--%>
<%--                                    <div class="coupons-close">--%>
<%--                                        <div class="coupons-middle">유의사항</div>--%>
<%--                                        <img src="/static/mypage/images/demonstration.png">--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </button>--%>
<%--                        </li>--%>
<%--                        <li class="coupons-item">--%>
<%--                            <button>--%>
<%--                                <div class="coupons-top">--%>
<%--                                    <div class="coupons-book">--%>
<%--                                        <div class="coupons-count">50,000원</div>--%>
<%--                                        <div class="coupons-countdown">D-7</div>--%>
<%--                                    </div>--%>
<%--                                    <div class="coupons-big">[웰컴혜택] 신규회원 5만 원 할인 쿠폰</div>--%>
<%--                                    <div class="coupons-middle">2025.10.31 까지</div>--%>
<%--                                </div>--%>
<%--                                <div class="coupons-bottom">--%>
<%--                                    <div class="coupons-close">--%>
<%--                                        <div class="coupons-middle">유의사항</div>--%>
<%--                                        <img src="/static/mypage/images/demonstration.png">--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </button>--%>
<%--                        </li>--%>
<%--                    </ul>--%>
<%--                </div>--%>
            </section>
        </div>
    </div>
</div>