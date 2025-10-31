<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/lecture_statistics.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="statistics"/>
</jsp:include>

<div class="admin-content-container">
    <h1 class="admin-page-title">강의 통계</h1>

    <div class="summary-card-container">
        <div class="summary-card">총 강의수 <span id="totalLecture">0</span></div>
        <div class="summary-card">등록완료 <span id="registeredLecture">0</span></div>
        <div class="summary-card">검수대기 <span id="pendingLecture">0</span></div>
    </div>

    <div class="chart-card">
        <canvas id="lectureChart"></canvas>
    </div>
    <div class="chart-card">
        <canvas id="difficultyChart"></canvas>
    </div>
    <div class="chart-card">
        <canvas id="statusChart"></canvas>
    </div>

    <div class="chart-card">
        <h2 class="chart-title">강의 평점 상위 TOP5</h2>
        <canvas id="topRatedChart"></canvas>
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
        padding: 10px 20px;
        margin-bottom: 20px;
        font-size: 40px;
        font-weight: 600;
        color: #333;
    }

    .chart-card {
        flex: 1 1 45%;
        max-width: 600px;
        min-width: 400px;
        background: white;
        border: 1px solid #e0e0e0;
        border-radius: 16px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        padding: 20px 30px 40px;
        text-align: center;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .chart-card:hover {
        transform: translateY(-4px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }
    .chart-title {
        margin-bottom: 20px;
        font-size: 24px;
        font-weight: 600;
        color: #000000;
    }

    .summary-card-container {
        display: flex;
        justify-content: center;
        text-align: center;
        gap: 20px;
        margin: 30px 30px;
    }

    .summary-card {
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 3px 6px rgba(0,0,0,0.1);
        padding: 16px 28px;
        font-size: 18px;
        font-weight: bold;
    }

    .summary-card span {
        display: block;
        font-size: 28px;
        color: #0074D9;
    }

    #lectureChart {
        width: 100%;
        max-width: 700px;
        margin: 0 auto;
    }

    #difficultyChart {
        width: 100%;
        max-width: 700px;
        height: 350px;
        margin: 0 auto;
    }
</style>

<script src="<c:url value='/js/page/admin/lecture_statistics.js'/>"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
<script>
    fetch("/admin/api/lectures/subjectCount")
        .then(res => res.json())
        .then(json => {
            console.log("[Data Raw]", json);

            const data = json.data; //실제 통계 데이터 추출
            if (!data) {
                console.error("데이터 없음:", json);
                return;
            }
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
                            color: '#000000',
                            font: {weight: 'bold', size: 18}
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
        .then(json => {
            console.log("[Data Raw]", json);

            const data = json.data; //실제 통계 데이터 추출
            if (!data) {
                console.error("데이터 없음:", json);
                return;
            }
            const labels = Object.keys(data);    // ["기초", "핵심", "응용", "심화", "최상위"]
            const values = Object.values(data);

            const ctx = document.getElementById("difficultyChart").getContext("2d");
            new Chart(ctx, {
                type: "doughnut",
                data: {
                    labels: labels,
                    datasets: [{
                        data: values,
                        backgroundColor: [  // ✅ 철자 수정
                            "#ff6384", "#36a2eb", "#ffcd56", "#4bc0c0", "#9966ff"
                        ],
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        title: {
                            display: true,
                            text: "난이도별 강의 분포",
                            font: { size: 24 }
                        },
                        legend: {
                            display: true,
                            position: "bottom",
                            labels: {
                                color: "#333",
                                font: { size: 18 }
                            }
                        },
                        tooltip: {  // ✅ plugins 안쪽에 위치 (Chart.js v3 기준)
                            displayColors: false,
                            callbacks: {
                                label: (context) => context.label + ": " + context.parsed + "개"
                            }
                        }
                    }
                }
            });
        })
        .catch(err => {
            console.error("[ERROR] 난이도별 강의 수 로드 실패:", err);
            alert("⚠️ 난이도별 강의 데이터를 불러오지 못했습니다.");
        });
</script>
<script>
    fetch("/admin/api/lectures/statusCount")
        .then(res => res.json())
        .then(data => {
            console.log("[Data] 등록 상태별 강의 수:", data)

            const labels = Object.keys(data);    // ["기초", "핵심", "응용", "심화", "최상위"]
            const values = Object.values(data);

            const ctx = document.getElementById("statusChart").getContext("2d");

            new Chart(ctx, {
                type: "pie",
                data: {
                    labels: labels,
                    datasets: [{
                        data: values,
                        backgroundColor: [
                            "#4bc0c0", "#ffcd56", "#ff6384", "#9966ff"
                        ],
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        title: {
                            display: true,
                            text: "등록 상태별 강의 현황",
                            font: { size: 18 }
                        },
                        legend: {
                            display: true,
                            position: "bottom"
                        },
                        tooltip: {
                            displayColors: false,
                            callbacks: {
                                label: (context) => context.label + ": " + context.parsed + "개"
                            }
                        }
                    }
                }
            });
        })
        .catch(err => {
            console.error("[ERROR] 등록 상태별 강의 수 로드 실패:", err);
            alert("⚠️ 등록 상태별 강의 데이터를 불러오지 못했습니다.");
        });
</script>
<script>
    fetch("/admin/api/lectures/topRated")
        .then(res => res.json())
        .then(data => {
            console.log("[Data] 평점 TOP5:", data);

            const labels = Object.keys(data);
            const values = Object.values(data);

            const ctx = document.getElementById("topRatedChart").getContext("2d");

            new Chart(ctx, {
                type: "bar",
                data: {
                    labels: labels,
                    datasets: [{
                        label: "평균 평점",
                        data: values,
                        backgroundColor: [
                            "#4bc0c0", "#36a2eb", "#9966ff", "#ffcd56", "#ff6384"
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    indexAxis: "y",  // ✅ 수평 막대
                    responsive: true,
                    scales: {
                        x: {
                            beginAtZero: true,
                            max: 5,
                            ticks: { stepSize: 1 },
                            title: {
                                display: true,
                                text: "평점 (0~5)",
                                color: "#000000",
                                font: { size: 14, weight: "bold" }
                            }
                        },
                        y: {
                            ticks: {
                                color: "#333",
                                font: { size: 14, weight: "bold" }
                            }
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: "강의 평점 상위 5개 강의",
                            font: { size: 18 }
                        },
                        legend: { display: false },
                        tooltip: {
                            displayColors: false,
                            callbacks: {
                                label: (ctx) => `평점 ${ctx.parsed.x.toFixed(1)}점`
                            }
                        },
                        datalabels: {
                            anchor: "end",
                            align: "right",
                            color: "#222",
                            font: { weight: "bold", size: 14 },
                            formatter: (v) => v.toFixed(1)
                        }
                    }
                },
                plugins: [ChartDataLabels]
            });
        })
        .catch(err => {
            console.error("[ERROR] 평점 TOP5 로드 실패:", err);
            alert("⚠️ 평점 TOP5 데이터를 불러오지 못했습니다.");
        });
</script>

