<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>결제·환불 테스트</title>

    <!-- 포트원 JavaScript SDK -->
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>

    <script>
        function payment() {




            // 가맹점 식별코드
            IMP.init("${idCode}");

            // 결제 요청을 위한 파라미터
            const pg = 'html5_inicis.INIpayTest';//"tosspayments"; // PgProvider
            const pay_method = 'card'; // 결제수단
            const impMerchantUid = 'PAYMENT_' + new Date().getTime(); // 가맹점 결제번호
            const impAmount = '100'; // 결제 금액
            const impBuyerName = '차우차우'
            const impBuyerTel = '010-0000-0000'; // KG이니시스 이용 시 필수사항
            const impBuyerPostcode = '00000';
            const impBuyerAddr = '';
            const impName  = '수학강의';

            // Iamport(Portone) 결제 API 요청
            IMP.request_pay({
                pg              : pg,               // 결제대행사 (현재는 KCP, KAKAOPAY 중 하나)
                pay_method      : pay_method,       // 결제 방법
                merchant_uid    : impMerchantUid,   // 주문 번호
                amount          : impAmount,        // 결제 가격
                name            : impName,          // 상품 이름
                buyer_name      : impBuyerName,     // 구매자 이름
                buyer_tel       : impBuyerTel       // 구매자 연락처 (KG이니시스 이용 시 필수사항)
                //buyer_postcode  : impBuyerPostcode, // 구매자 우편번호
                //buyer_addr      : impBuyerAddr      // 구매자 주소

                // 결제 결과 콜백함수
            }, function(result) {

                // 결제 성공
                if (result.success) {

                    // 결과출력 (임시)
                    console.log(JSON.stringify(result));

                    // 전송 폼
                    const form = new FormData();
                    form.append("lectureId", "1");
                    form.append("memberId", "1");
                    form.append("paidAmount", result.paid_amount);
                    form.append("paymentMethod", result.pay_method);
                    form.append("paymentUid", result.imp_uid);
                    form.append("paymentApiResult", JSON.stringify(result));


                    // 서버에 결과 전달
                    fetch(`/testpayment`, {
                        method: "POST",
                        body: form
                    })
                        // [4] 서버 응답을 JSON 형태로 변환
                        .then(res => res.json())

                        // [5] JSON 변환 완료 후 실제 데이터 처리
                        .then(success => {
                            console.log(success);
                        })

                        // [6] 요청 실패 시 콘솔에 에러 출력
                        .catch(error => console.log("결제 처리 실패 : ", error));


                    // 결제 실패
                } else {
                    console.log(JSON.stringify(result));
                    alert(result.error_msg || "결제 요청에 실패했습니다. 다시 시도해 주세요.");
                }

            });
        }

        function refund() {
            const paymentId = document.getElementById("paymentId").value.trim();
            if (!paymentId) {
                alert("주문번호를 입력하세요.");
                return;
            }

            // 서버에 결과 전달
            fetch(`/testpayment/\${paymentId}/refund`, {
                method: "POST"
            })
                // [4] 서버 응답을 JSON 형태로 변환
                .then(res => res.json())

                // [5] JSON 변환 완료 후 실제 데이터 처리
                .then(success => {
                    console.log(success);
                })

                // [6] 요청 실패 시 콘솔에 에러 출력
                .catch(error => console.log("결제 처리 실패 : ", error));
        }


    </script>
</head>
<body>
    <h2>결제·환불 테스트 페이지</h2>

    <section style="margin-bottom:20px;">
        <button type="button" onclick="payment()">결제</button>
    </section>

    <section>
        <label for="paymentId">환불할 주문번호:</label>
        <input type="text" id="paymentId" name="paymentId" placeholder="주문번호 입력">
        <button type="button" onclick="refund()">환불</button>
    </section>
</body>
</html>