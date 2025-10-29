<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<section class="payments">
    <div class="mypage-title">구매 내역</div>
    <div>
        <form method="get" action="/mypage/payments">
            <div class="parameters-top">
                <div class="payments-date">
                    <div class="parameters-start">
                        <input id="since" name="since" type="date" value="${sinceDate}">
                    </div>
                    <div class="parameters-and">~</div>
                    <div class="parameters-last">
                        <input id="until" name="until" type="date" value="${untilDate}">
                    </div>
                </div>
                <button type="submit">조회</button>
            </div>
        </form>
        <div class="parameters-bottom">
            <table class="parameters-list">
                <colgroup>
                    <col width="5%">
                    <col width="15%">
                    <col width="10%">
                    <col width="35%">
                    <col width="10%">
                    <col width="10%">
                    <col width="10%">
                </colgroup>
                <tbody>
                <tr class="parameters-category">
                    <th>No</th>
                    <th>날짜</th>
                    <th>과목</th>
                    <th>품명</th>
                    <th>결제수단</th>
                    <th>금액(원)</th>
                    <th>구분</th>
                </tr>
                <c:choose>
                    <c:when test="${not empty paymentList}">
                        <c:forEach var="payment" items="${paymentList}" varStatus="status">
                            <tr class="parameters-item">
                                <td>${fn:length(paymentList)-status.index}</td>
                                <td><c:out value="${fn:substring(payment.cdate, 0, 10)}"/></td>
                                <td data-en="<c:out value='${payment.lecture.subject}'/>"><c:out value="${payment.lecture.subject}"/></td>
                                <td><c:out value="${payment.lecture.title}"/></td>
                                <td><c:out value="${payment.paymentApiResult}" default="-" /></td>
                                <td><fmt:formatNumber value="${payment.paidAmount}" pattern="#,###" /></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${payment.isRefunded}">
                                            <span style="color:red;">환불완료</span>
                                        </c:when>
                                        <c:otherwise>
                                            결제완료
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="7" style="text-align:center; padding:20px;">
                                구매 내역이 없습니다.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</section>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const translations = {
            "KOREAN": "국어",
            "ENGLISH": "영어",
            "MATH": "수학",
            "SCIENCE": "과학탐구",
            "SOCIAL": "사회탐구"
        };

        document.querySelectorAll("[data-en]").forEach(el => {
            const enText = el.getAttribute("data-en");
            if(translations[enText]) {
                el.innerHTML = translations[enText].replace(/\n/g, "<br/>");
            }
        });
    });
</script>