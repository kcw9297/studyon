<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="main-content">
    <canvas id="confetti"></canvas>
    <div class="payment-center-box">
        <img src="<c:url value='/img/png/present.png'/>" class="payment-icon">
        <div class="payment-title">결제가 완료되었습니다 🎉</div>
        <div class="payment-subtitle">이제 바로 수강해보세요!</div>
        <div class="payment-subtitle">결제번호 : ${data.paymentId}</div>
        <div class="payment-subtitle">결제강의 : ${data.lectureTitle}</div>
        <div class="payment-subtitle">결제액수 : <fmt:formatNumber value="${data.paidAmount}" pattern="₩#,###" /></div>
        <button class="payment-btn" onclick="location.href='/mypage/lecture_management'">내 강의로 이동</button>
    </div>
</div>


<style>
    /* 전체 레이아웃 */
    .main-content {
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        min-height: 800px;
        background: linear-gradient(135deg, #f9fafb 0%, #ffffff 100%);
        overflow: hidden;
    }

    /* 폭죽 캔버스 */
    #confetti {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        pointer-events: none;
    }

    /* 카드 박스 */
    .payment-center-box {
        position: relative;
        z-index: 2;
        width: 480px;
        padding: 60px 40px;
        background: #fff;
        border-radius: 24px;
        box-shadow: 0 10px 25px rgba(0,0,0,0.08);
        text-align: center;
        font-family: 'Noto Sans KR', sans-serif;
        animation: fadeInUp 0.8s ease-out;
    }

    /* 이미지 */
    .payment-icon {
        width: 200px;
        height: 200px;
        margin-bottom: 20px;
        animation: bounce 1.5s infinite;
    }

    /* 타이틀 */
    .payment-title {
        font-size: 26px;
        font-weight: 700;
        color: #2c3e50;
        margin-bottom: 12px;
    }

    /* 서브 텍스트 */
    .payment-subtitle {
        font-size: 16px;
        color: #666;
        margin-bottom: 40px;
    }

    /* 버튼 */
    .payment-btn {
        background: linear-gradient(135deg, #00c6ff 0%, #0072ff 100%);
        border: none;
        color: white;
        font-size: 16px;
        padding: 14px 40px;
        border-radius: 10px;
        cursor: pointer;
        transition: all 0.3s ease;
    }
    .payment-btn:hover {
        transform: translateY(-2px);
        box-shadow: 0 6px 14px rgba(0,114,255,0.3);
    }

    /* 등장 애니메이션 */
    @keyframes fadeInUp {
        from { opacity: 0; transform: translateY(40px); }
        to { opacity: 1; transform: translateY(0); }
    }

    /* 아이콘 바운스 */
    @keyframes bounce {
        0%, 100% { transform: translateY(0); }
        50% { transform: translateY(-8px); }
    }
</style>


<script>
    // 🎆 폭죽 애니메이션 (Canvas)
    const canvas = document.getElementById('confetti');
    const ctx = canvas.getContext('2d');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    const confettiCount = 200;
    const confetti = [];

    for (let i = 0; i < confettiCount; i++) {
        confetti.push({
            x: Math.random() * canvas.width,
            y: Math.random() * canvas.height - canvas.height,
            r: Math.random() * 6 + 4,
            d: Math.random() * confettiCount,
            color: "#333",
            tilt: Math.floor(Math.random() * 10) - 10,
        });
    }

    function drawConfetti() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        confetti.forEach(c => {
            ctx.beginPath();
            ctx.fillStyle = c.color;
            ctx.ellipse(c.x, c.y, c.r, c.r / 2, Math.PI / 4, 0, 2 * Math.PI);
            ctx.fill();
        });
        updateConfetti();
    }

    function updateConfetti() {
        confetti.forEach(c => {
            c.y += Math.cos(c.d) + 2;
            c.x += Math.sin(c.d) * 2;
            c.d += 0.01;
            if (c.y > canvas.height) {
                c.y = -10;
                c.x = Math.random() * canvas.width;
            }
        });
    }

    function animate() {
        drawConfetti();
        requestAnimationFrame(animate);
    }
    animate();

    // 반응형 처리
    window.addEventListener('resize', () => {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    });
</script>