<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />

<div class="teacher-statistics-container">
    <h1 class="page-title">📊 강의 통계 (Teacher Ver.)</h1>

    <!-- 요약 카드 -->
    <div class="summary-card-container">
        <div class="summary-card">총 강의수 <span id="totalLectureCount">-</span></div>
        <div class="summary-card">등록완료 <span id="registeredCount">-</span></div>
        <div class="summary-card">등록대기 <span id="pendingCount">-</span></div>
        <div class="summary-card">미등록 <span id="unregisteredCount">-</span></div>
        <div class="summary-card">총 수강생수 <span id="totalStudents">-</span></div>
        <div class="summary-card">총 매출액 <span id="totalSales">-</span></div>
    </div>

    <div class="chart-grid">
        <div class="chart-card">
            <h2>과목별 매출 비율</h2>
            <canvas id="salesChart"></canvas>
        </div>

        <div class="chart-card">
            <h2>난이도별 강의 분포</h2>
            <canvas id="difficultyChart"></canvas>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", async () => {
        try {
            const res = await fetch("/api/teachers/management/statistics");
            const result = await res.json();
            const data = result.data;

            console.log("📊 Dashboard Data:", data);

            const totalLecture = data.lectureStats.totalLectureCount ?? 0;
            const pending = data.lectureStats.byStatus?.PENDING ?? 0;
            const unregistered = data.lectureStats.byStatus?.UNREGISTERED ?? 0;

            // ✅ 등록 완료 강의 수 계산
            const registeredCount = totalLecture - (pending + unregistered);

            // ✅ 카드 데이터 표시
            document.getElementById("totalLectureCount").textContent = totalLecture;
            document.getElementById("pendingCount").textContent = pending;
            document.getElementById("unregisteredCount").textContent = unregistered;
            document.getElementById("registeredCount").textContent = registeredCount; // ✅ 추가
            document.getElementById("totalStudents").textContent = data.teacherStats.totalStudents ?? 0;
            document.getElementById("totalSales").textContent =
                (data.salesStats.totalSales ?? 0).toLocaleString("ko-KR") + "원";

            // ✅ 난이도별 그래프
            const diffData = data.lectureStats.byDifficulty || {};
            new Chart(document.getElementById("difficultyChart"), {
                type: "doughnut",
                data: {
                    labels: Object.keys(diffData),
                    datasets: [{
                        data: Object.values(diffData),
                        backgroundColor: ["#e67e22", "#3498db", "#9b59b6", "#2ecc71", "#f39c12"]
                    }]
                },
                options: {
                    plugins: { legend: { position: "bottom" } }
                }
            });

            // ✅ 과목별 매출 그래프
            const sales = data.salesStats.salesBySubject || {};
            new Chart(document.getElementById("salesChart"), {
                type: "bar",
                data: {
                    labels: Object.keys(sales),
                    datasets: [{
                        label: "매출액 (원)",
                        data: Object.values(sales),
                        backgroundColor: "#2ecc71"
                    }]
                },
                options: {
                    plugins: { legend: { display: false } },
                    scales: { y: { beginAtZero: true } }
                }
            });

        } catch (err) {
            console.error("📛 통계 데이터 불러오기 실패:", err);
        }
    });
</script>

<style>
    .teacher-statistics-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 20px 20px 20px;
        background: #fff;
        justify-content: center;
    }

    .page-title {
        font-size: 28px;
        font-weight: 700;
        margin-bottom: 40px;
        color: #333;
    }

    /* ✅ 카드 스타일 */
    .summary-card-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 24px;
        margin-bottom: 60px;
    }

    .summary-card {
        background: #fff;
        border-radius: 16px;
        box-shadow: 0 3px 8px rgba(0,0,0,0.08);
        padding: 22px 36px;
        min-width: 180px;
        text-align: center;
        font-size: 17px;
        font-weight: 600;
        color: #333;
    }

    .summary-card span {
        display: block;
        font-size: 28px;
        color: #27ae60;
        margin-top: 8px;
    }

    /* ✅ 차트 영역 균형 */
    .chart-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(420px, 1fr));
        gap: 40px;
        width: 100%;
        max-width: 1100px;
    }

    .chart-card {
        background: #fff;
        border-radius: 18px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.08);
        padding: 30px 20px 40px;
        text-align: center;
        transition: 0.2s;
    }

    .chart-card:hover {
        transform: translateY(-4px);
        box-shadow: 0 6px 14px rgba(0,0,0,0.12);
    }

    .chart-card h2 {
        font-size: 18px;
        font-weight: 600;
        margin-bottom: 20px;
        color: #333;
    }

    /* ✅ 그래프 크기 통일 */
    .chart-card canvas {
        width: 100% !important;
        height: 360px !important;
        max-height: 380px;
    }
</style>

</style>
