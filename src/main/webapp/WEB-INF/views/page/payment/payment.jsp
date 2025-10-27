<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="content-wrapper">
  <div class="lecture-info-box">
    <div class="lecture-thumbnail">
      <img src="resources/sample1.png" class="lecture-thumbnail-img"alt="강의이미지">
    </div>
    <div class="lecture-info">
      <div class="lecture-title">일 잘하는 사람은 '이렇게' 말합니다</div>
      <div class="lecture-subtitle">인프런</div>
    </div>
    <div class="lecture-price">₩35,000</div>
  </div>
  <div class="divide-title-box">할인정보</div>
  <div class="coupon-box">
    <button class="coupon-button">쿠폰선택</button>
    <div class="coupon-info">사용가능한 쿠폰이 없습니다.</div>
  </div>
  <div class="divide-title-box">결제수단</div>
  <div class="payment-method-box">
    <input type="radio" id="card" name="payment" value="card" checked>
    <label>토스 페이먼츠</label>
    <img src="resources/Toss_Logo.png" class="payment-logo" alt="토스페이먼츠로고">
  </div>
  <div class="divide-title-box">총 결제액</div>
  <div class="payment-summary-box">
    <div class="summary-item">
      <div class="summary-title">총 결제금액</div>
      <div class="summary-price">₩35,000</div>
    </div>
  </div>
    <button id="pay-btn" class="pay-button">결제하기</button>
</div>

<style>
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
        border:2px solid black;
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


<script>
    document.getElementById("pay-btn").addEventListener("click", async () => {
        const lectureId = ${lecture.lectureId};

        try {
            const res = await fetch("/api/payment/complete", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ lectureId })
            });

            // ✅ 로그인 안 된 경우 → 서버가 302로 /login 보냄
            if (res.redirected) {
                console.warn("비로그인 상태: 로그인 페이지로 이동");
                window.location.href = res.url;
                return;
            }

            // ✅ 응답 JSON 확인
            const result = await res.json();
            console.log("결제 응답:", result);

            alert(result.data?.content ?? "결제 완료되었습니다!");

            // ✅ 결제 완료 후 마이페이지로 이동
            window.location.href = "/mypage/lecture_management";



        } catch (err) {
            console.error("결제 처리 중 오류:", err);
            alert("❌ 결제 중 오류가 발생했습니다.");
        }
    });
</script>
