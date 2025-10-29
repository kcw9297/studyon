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


        <div class="member-info-box">
            <label class="divide-title-box">구매자 정보</label>
            <form id="payment-info">
            <div class="member-info-item">
                <label class="member-info-item-description" for="name">성함</label>
                <input class="member-name-input" id="name" placeholder="이름을 입력하세요">
            </div>
            <div class="member-info-item">
                <label class="member-info-item-description" for="phone">연락처</label>
                <input class="member-phone-input" id="phone" placeholder="예: 010-1234-5678">
            </div>

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
