<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/lecture_statistics.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="statistics"/>
</jsp:include>

<div class="admin-content-container">
    <div class="chart-card">
        <h2 class="admin-page-title">과목별 강의 분포</h2>
        <canvas id="lectureChart"></canvas>
    </div>
    <div>
        <h2 class="admin-page-title">난이도별 강의 분포</h2>
        <canvas id="difficultyChart"></canvas>
    </div>
</div>

<style>
    .admin-content-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        border: 2px solid black;
        min-height: 600px;
        height: auto;
        flex-direction: column;
    }

    .admin-page-title {
        text-align: center;
        align-content: center;
    }

    .chart-card {
        flex: 1 1 45%;
    }

    #lectureChart {
        width: 100%;
        max-width: 700px;
        margin: 0 auto;
    }

    #difficultyChart {
        width: 100%;
        max-width: 700px;
        margin: 0 auto;
    }
</style>

<script src="<c:url value='/js/page/admin/lecture_statistics.js'/>"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
<script>
    fetch("/admin/api/lectures/subjectCount")
        .then(res => res.json())
        .then(data => {
            console.log("[Data] 과목별 강의 수: " + data);

            // [1] 영어 → 한글 맵핑 테이블
            const subjectMap = {
                ENGLISH: "영어",
                KOREAN: "국어",
                MATH: "수학",
                SCIENCE: "과학",
                SOCIAL: "사회"
            };

            // [2] 데이터 분리
            const labels = Object.keys(data).map(key => subjectMap[key] || key);
            const values = Object.values(data);
            const maxValue = Math.max(...values);

            // [3] Chart.js 차트 생성
            const ctx = document.getElementById("lectureChart").getContext("2d");
            new Chart(ctx, {
                type: "bar",
                data: {
                    labels: labels,
                    datasets: [{
                        label: "과목별 등록 강의 수",
                        data: values,
                        borderWidth: 1,
                        backgroundColor: [
                            "rgba(255, 99, 132, 0.6)",
                            "rgba(54, 162, 235, 0.6)",
                            "rgba(255, 206, 86, 0.6)",
                            "rgba(75, 192, 192, 0.6)",
                            "rgba(153, 102, 255, 0.6)"
                        ]
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            ticks: {
                                color: "#333",
                                font: {size: 14},
                            },
                            title: {
                                display: true,
                                text: "과목명",
                                color: "#555",
                                font: {size: 16, weight: "bold"}
                            }
                        },
                        y: {
                            beginAtZero: true,
                            max: maxValue + 2,
                            ticks: {stepSize: 1},
                            title: {
                                display: true,
                                text: "강의 개수",  // ✅ Y축 이름
                                color: "#555",
                                font: {size: 16, weight: "bold"}
                            }
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: "과목별 등록 강의 수 현황",
                            font: {size: 18}
                        },
                        legend: {display: false},
                        tooltip: {
                            displayColors: false,
                            callbacks: {
                                title: (context) => context[0].label,
                                label: (context) => context.parsed.y + "개",
                            }
                        },
                        datalabels: { // ✅ 숫자 표시 설정
                            anchor: 'end',     // 막대 상단 위치
                            align: 'top',
                            color: '#333',
                            font: {weight: 'bold', size: 14}
                        }
                    }
                },
                plugins: [ChartDataLabels] // ✅ 플러그인 등록
            });
        })
        .catch(err => {
            console.error("[ERROR] 통계 데이터 로드 실패:", err);
            alert("⚠️ 과목별 강의 수 데이터를 불러오지 못했습니다.");
        });
</script>
<script>
    fetch("/admin/api/lectures/difficultyCount")
        .then(res => res.json())
        .then(data => {
            console.log("[Data] 난이도별 강의 수:", data);

            const labels = Object.keys(data);    // ["기초", "핵심", "응용", "심화", "최상위"]
            const values = Object.values(data);

            const ctx = document.getElementById("difficultyChart").getContext("2d");
            new chart(ctx, {
                type: "doughnut",
                data: {
                    labels: labels,
                    datasets: [{
                        data: values,
                        backgroudColor: [
                            "#ff6384", "#36a2eb", "#ffcd56", "#4bc0c0", "#9966ff"
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: "난이도별 강의 분포",
                            font: {size: 18}
                        },
                        legend: {position: "bottom"}
                        tooltip: {
                            displayColors: false,
                            callbacks: {
                                label: (context) => context.label + ": " + context.parsed + "개"
                            }
                        }
                    }
                }
            })
        })
</script>
