<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="content-wrapper">
    <div class="lecture-info-box">
        <div class="lecture-thumbnail">
            <img src="<c:url value="${fileDomain}/${data.thumbnailImagePath}" />" class="lecture-thumbnail-img"alt="강의이미지">
        </div>
        <div class="lecture-info">
            <div class="lecture-title">${data.lectureTitle}</div>
            <div class="lecture-subtitle">${data.lectureTeacherNickname}</div>
        </div>
        <div class="lecture-price"><fmt:formatNumber value="${data.price}" pattern="₩#,###" /></div>
    </div>

    <div class="member-info-box">
        <label class="divide-title-box">구매자 정보</label>
        <form id="payment-info">
            <div class="member-info-item">
                <label class="member-info-item-description" for="name">성함</label>
                <input class="member-name-input" id="buyerName" placeholder="이름을 입력하세요">
            </div>
            <div id="buyerNameError"></div>
            <div class="member-info-item">
                <label class="member-info-item-description" for="phone">연락처</label>
                <input class="member-phone-input" id="buyerPhoneNumber" placeholder="예: 01012345678">
            </div>
            <div id="buyerPhoneNumberError"></div>

        </form>
    </div>
    <button id="pay-btn" class="pay-button">결제하기</button>
</div>




<style>

    #content{
        height:auto;
    }

    /* 구매자 정보 */
    .member-info-box {
        width: 800px;
        margin: 40px;
        padding: 30px 25px;
        background: #fff;
        border-radius: 16px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        display: flex;
        flex-direction: column;
        gap: 20px;
        font-family: 'Noto Sans KR', sans-serif;
    }

    .member-info-item {
        display: flex;
        flex-direction: column;
        gap: 8px;
    }

    .member-info-item-description {
        font-weight: 600;
        color: #333;
        font-size: 15px;
    }

    .member-name-input,
    .member-phone-input {
        padding: 12px 15px;
        border: 1px solid #ddd;
        border-radius: 10px;
        font-size: 14px;
        outline: none;
        transition: all 0.3s ease;
    }

    .member-name-input:focus,
    .member-phone-input:focus {
        border-color: #4a90e2;
        box-shadow: 0 0 0 3px rgba(74,144,226,0.2);
    }






    .header-div{
        display:flex;
        width:100%;
        height:58px;
        background-color: rgb(131, 187, 41);
        justify-content: center;
        align-items: center;
        font-size:30px;
        font-weight: bold;
    }

    .lecture-info-box{
        display: flex;
        justify-content: center;
        height:150px;
        align-items: center;
        margin-top: 50px;
        flex-direction: row;
        width: 800px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
    }

    .lecture-thumbnail{
        width: 25%;
        height:100%;
        background-color: rgb(255, 255, 255);
        padding:15px;
    }

    .lecture-info{
        display:flex;
        flex-direction: column;
        justify-content: center;
        width: 50%;
        height:100%;
        background-color: rgb(255, 255, 255);

    }
    .lecture-price{
        width:25%;
        height:100%;
        font-size: 40px;
        font-weight: bold;
        text-align: center;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .content-wrapper{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        gap: 5px;
        width: 100%;
    }

    .lecture-thumbnail-img{
        width:100%;
        height:100%;
        border-radius: 10px;;
    }

    .lecture-title{
        font-size: 24px;
        font-weight: bold;
        margin-left:10px;
    }

    .lecture-subtitle{
        font-size: 18px;
        margin-left:10px;
    }


    .divide-title-box{
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        height:100%;
        align-items: center;
        flex-direction: row;
        width: 800px;
        font-size:20px;
        font-weight: bold;
    }

    .coupon-box{
        display: flex;
        flex-direction: column;
        justify-content:center;
        height:100%;
        align-items: center;
        width: 800px;
        border:2px solid black;
        padding:20px 0;

    }

    .payment-method-box{
        display: flex;
        flex-direction: row;
        justify-content:center;
        height:100%;
        align-items: center;
        flex-direction: row;
        width: 800px;
        border:2px solid black;
        padding:20px 0;

    }

    .payment-summary-box{
        display: flex;
        flex-direction: column;
        justify-content:center;
        height:100%;
        align-items: center;
        width: 800px;
        border:2px solid black;
        padding:20px 0;
    }

    .coupon-button{
        width:200px;
        height:40px;
        border-radius: 5px;
        background-color: rgb(131, 187, 41);
        color: white;
        font-size: 16px;
        font-weight: bold;
        margin-left:20px;
        cursor: pointer;
    }

    .pay-button{
        width:800px;
        height:50px;
        border-radius: 5px;
        background-color: rgb(53, 64, 129);
        color: white;
        font-size: 20px;
        font-weight: bold;
        margin-top:20px;
        cursor: pointer;
    }

    .payment-logo{
        width:80px;
        height:40px;
    }

    .summary-item{
        font-size:24px;
        font-weight: bold;
    }

    .summary-price{
        align-items:center;
        text-align:center;
    }
</style>

<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script>
    document.getElementById("pay-btn").addEventListener("click", async () => {

        try {

            // 입력받은 사용자 정보
            const buyerName = document.getElementById("buyerName")?.value.trim();
            const buyerPhoneNumber = document.getElementById("buyerPhoneNumber")?.value.trim();

            // 전송을 위한 form 데이터
            const formData = new FormData();
            formData.append("token", "${data.token}")
            formData.append("lectureId", "${data.lectureId}")
            formData.append("buyerName", buyerName)
            formData.append("buyerPhoneNumber", buyerPhoneNumber)

            // REST API 요청 (1단계 : 검증)
            const res = await fetch("/api/payments/verify", {
                headers: {'X-Requested-From': window.location.pathname + window.location.search},
                method: "POST",
                body: formData
            });


            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {

                // 로그인이 필요한 경우
                if (rp.statusCode === 401) {

                    // 로그인 필요 안내 전달
                    if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                        window.location.href = rp.redirect || "/login";
                    }

                    // 로직 중단
                    return;
                }

                // 권한이 부족한 경우
                if (rp.statusCode === 403) {
                    alert(rp.message || "접근 권한이 없습니다.");
                    return;
                }

                // 유효성 검사에 실패한 경우
                if (rp.inputErrors) {

                    document.querySelectorAll("[id$='Error']").forEach(elem => {
                        elem.textContent = "";
                    });

                    Object.entries(rp.inputErrors).forEach(([field, message]) => {
                        const errorElem = document.getElementById(`\${field}Error`);
                        if (errorElem) {
                            errorElem.textContent = message;
                            errorElem.className = "asynchronous-message-wrong";
                        }
                    });
                    return;
                }

                // 기타 예기치 않은 오류가 발생한 경우
                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            // 성공 시 결제 창 띄우기
            document.querySelectorAll("[id$='Error']").forEach(elem => {
                elem.textContent = "";
            });

            //doPayment({
            //    "buyerName" : buyerName,
            //    "buyerPhoneNumber" : buyerPhoneNumber
            //});

            await execute();


        } catch (error) {
            console.error('결제 검증 실패 오류:', error);
        }
    });


    async function doPayment(data = {}) {

        // 가맹점 식별코드
        IMP.init("${idCode}");

        // 결제 요청을 위한 파라미터
        const pg = 'html5_inicis.INIpayTest';//"tosspayments"; // PgProvider
        const pay_method = 'card'; // 결제수단
        const impMerchantUid = 'PAYMENT_' + new Date().getTime(); // 가맹점 결제번호
        const impAmount = '100'; // 결제 금액
        const impBuyerName = data.buyerName;
        const impBuyerTel = data.buyerPhoneNumber;
        const impName = "${data.lectureTitle}".length > 20 ? "${data.lectureTitle}".substring(0, 20) + " ..." : "${data.lectureTitle}";

        // 2단계 : Iamport(Portone) 결제 API 요청
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
                execute(result)

                // 결제 실패
            } else {
                console.log(JSON.stringify(result));
                alert(result.error_msg || "결제 요청에 실패했습니다. 다시 시도해 주세요.");
            }

        });
    }

    async function execute(result) {

        try {

            // 전송을 위한 form 데이터
            const formData = new FormData();
            //formData.append("paymentUid", result.imp_uid);
            //formData.append("paidAmount", result.paid_amount);
            //formData.append("paymentApiResult", JSON.stringify(result));
            formData.append("paymentUid", "IMP000000");
            formData.append("paidAmount", "${data.price}");
            formData.append("paymentApiResult", JSON.stringify(""));
            formData.append("lectureId", "${data.lectureId}")
            formData.append("token", "${data.token}")

            // REST API 요청 (3단계 : 클라이언트 결제 후 검증)
            const res = await fetch("/api/payments", {
                method: "POST",
                body: formData
            });

            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {

                // 결제 처리에 실패한 경우, 안내문 발송
                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            // 성공 시 성공 결제 페이지로 이동
            window.location.href = rp.redirect || "/";


        } catch (error) {
            console.error('결제 검증 실패 오류:', error);
        }
    }


</script>
