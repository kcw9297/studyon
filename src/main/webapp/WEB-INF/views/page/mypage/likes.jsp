<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<section class="likes">
    <div class="mypage-title">관심 목록</div>
    <div>
        <jsp:include page="likes_navbar.jsp"/>
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
        </div>
    </div>
</section>