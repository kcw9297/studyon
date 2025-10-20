document.addEventListener("DOMContentLoaded", function () {
    const couponButtons = document.querySelectorAll(".coupons-item button");

    couponButtons.forEach((btn, index) => {
        btn.addEventListener("click", () => {
            // 버튼 내부 텍스트 추출 (예: "[웰컴혜택] 신규회원 5만 원 할인 쿠폰")
            const title = btn.querySelector(".coupons-big")?.innerText || `쿠폰 ${index + 1}`;
            alert(`쿠폰 클릭됨: ${title}`);
        });
    });
});