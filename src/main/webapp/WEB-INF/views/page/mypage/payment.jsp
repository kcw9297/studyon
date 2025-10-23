<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<section class="payments">
    <div class="mypage-title">구매 내역</div>
    <div>
        <div class="parameters-top">
            <div class="payments-date">
                <div class="parameters-start">
                    <input id="since" type="date" value="2025-10-01">
                </div>
                <div class="parameters-and">~</div>
                <div class="parameters-last">
                    <input id="until" type="date" value="2025-10-31">
                </div>
            </div>
            <button>조회</button>
        </div>
        <div class="parameters-bottom">
            <table class="parameters-list">
                <colgroup>
                    <col width="5%">
                    <col width="10%">
                    <col width="10%">
                    <col width="35%">
                    <col width="15%">
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
                <tr class="parameters-item">
                    <td>3</td>
                    <td>2025.10.30</td>
                    <td>과학탐구</td>
                    <td>수능기본 : 화학II</td>
                    <td>신용카드</td>
                    <td>130,000</td>
                    <td>결제완료</td>
                </tr>
                <tr class="parameters-item">
                    <td>2</td>
                    <td>2025.10.24</td>
                    <td>수학</td>
                    <td>2025 수능 특강 : 미적분</td>
                    <td>신용카드</td>
                    <td>80,000</td>
                    <td>결제완료</td>
                </tr>
                <tr class="parameters-item">
                    <td>1</td>
                    <td>2025.10.20</td>
                    <td>영어</td>
                    <td>[수능발전] 수능 QnQ 특강 영어 : 어법</td>
                    <td>무통장</td>
                    <td>100,000</td>
                    <td>환불완료</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</section>