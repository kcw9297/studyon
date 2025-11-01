<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/lecture_statistics.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="statistics"/>
</jsp:include>


<div class="admin-content-container">
    <header class="admin-header-statistics">
        <h1 class="admin-page-title">강의 통계</h1>
        <div class="summary-card-container">
            <div class="summary-card">총 강의수 <span id="totalLecture">0</span></div>
            <div class="summary-card">등록완료 <span id="registeredLecture">0</span></div>
            <div class="summary-card">등록대기 <span id="pendingLecture">0</span></div>
            <div class="summary-card">미등록 <span id="unregisteredLecture">0</span></div>
        </div>
    </header>
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
        <canvas id="topRatedChart"></canvas>
    </div>

    <div class="chart-card">
        <canvas id="targetChart"></canvas>
    </div>
    <div class="chart-card">
        <canvas id="salesBySubjectChart"></canvas>
    </div>
</div>

<style>
    .admin-content-container {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(550px, auto)); /*  반응형 2~4열 */
        gap: 40px;
        justify-items: center;
        flex-wrap: wrap;
        justify-content: center;
        border: 2px solid black;
        min-height: 600px;
        height: auto;
        flex-direction: column;
        background-color: #fafafa;
        padding-bottom: 60px;
    }

    .admin-header-statistics {
        grid-column: 1 / -1; /* 전체 폭 차지 */
        display: flex;
        flex-direction: column; /* 제목, 부제목 세로 정렬*/
        justify-content: center;
        text-align: center;
        margin-bottom: 20px;
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
        display: flex;                 /* 내부를 플렉스 컨테이너로 */
        align-items: center;           /* 수직 중앙 */
        justify-content: center;       /* 수평 중앙 */
        flex-direction: column;        /* 제목(h2)과 차트를 세로로 배치 */
        position: relative; /* 내부 캔버스 절대 위치 기준 */
        width: 100%;
        flex: 1 1 45%;
        max-width: 500px;
        aspect-ratio: 1 / 1;  /* 정사각형 비율 유지 */
        background: white;
        border: 1px solid #e0e0e0;
        border-radius: 16px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        padding: 40px 30px 40px 30px;
        text-align: center;
        transition: transform 0.2s ease, box-shadow 0.2s ease;
        margin-bottom: 20px;
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
        flex-wrap: wrap;
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
    /* ✅ 모든 canvas 크기 통일 */
    .chart-card canvas {
        position: static;
        width: 90%;
        height: 90%;
        display: block;
        margin: 0 auto;
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
    document.addEventListener("DOMContentLoaded", async () => {
        try {
            // 등록상태별 강의 수 불러오기
            const res = await fetch("/admin/api/lectures/statusCount");
            const json = await res.json();

            const data = json.data || json; // RestUtils.ok 구조 고려
            let total = 0;

            for (const key in data) total += data[key];
            document.getElementById("totalLecture").textContent = total;

            document.getElementById("registeredLecture").textContent =
                data["등록완료"] || 0;
            document.getElementById("pendingLecture").textContent =
                data["등록대기중"] || 0;
            document.getElementById("unregisteredLecture").textContent =
                data["미등록"] || 0;
        } catch (err) {
            console.error("[ERROR] 통계 데이터 불러오기 실패:", err);
        }
    });
</script>

<script>
    // ✅ 모든 차트에서 공통으로 쓸 툴팁 설정
    const commonTooltip = {
        displayColors: false,
        callbacks: {
            label: (context) => {
                // [0] raw 값 직접 가져오기
                let value = context?.raw;

                // [1] 객체라면 내부 숫자값 하나 찾기
                if (value && typeof value === "object") {
                    const firstNumeric = Object.values(value)
                        .find(v => typeof v === "number" && !isNaN(v));
                    value = firstNumeric ?? null;
                }

                // [2] 문자열 숫자면 변환 (JSP 대응)
                if (typeof value === "string") {
                    // JSP EL 오인 방지용 Number() → window["Number"]()
                    const numeric = window["Number"](value.replace(/,/g, ""));
                    if (!isNaN(numeric)) value = numeric;
                }

                // [3] 숫자면 단위 붙여서 반환
                if (typeof value === "number" && !isNaN(value)) {
                    const title = context.chart?.options?.plugins?.title?.text || "";
                    const dsLabel = context.dataset?.label || "";

                    const isRating =
                        value <= 5 &&
                        (title.includes("평점") || dsLabel.includes("평균 평점"));
                    const isMoney =
                        value >= 1000 &&
                        (title.includes("매출") ||
                            title.includes("금액") ||
                            dsLabel.includes("원"));

                    if (isRating) return `평점: \${value.toFixed(1)}점`;
                    if (isMoney) return `\${value.toLocaleString()}원`;
                    return `\${value.toLocaleString()}개`;
                }

                // [4] fallback - formattedValue 시도
                const fv = context.formattedValue;
                if (fv && !isNaN(window["Number"](fv))) {
                    return `\${window["Number"](fv).toLocaleString()}개`;
                }

                // [5] 완전 예외 fallback
                return `\${context.label || "데이터"}: \${String(value ?? "N/A")}`;
            }
        }
    };


</script>

<script>
    fetch("/admin/api/lectures/subjectCount")
        .then(res => res.json())
        .then(json => {
            console.log("[Data Raw]", json);

            const data = json.data;
            if (!data) {
                console.error("데이터 없음:", json);
                return;
            }

            const subjectMap = {
                ENGLISH: "영어",
                KOREAN: "국어",
                MATH: "수학",
                SCIENCE: "과학",
                SOCIAL: "사회"
            };

            // [2] 모든 과목을 미리 정의 (순서 고정)
            const allSubjects = ["KOREAN", "ENGLISH", "MATH", "SCIENCE", "SOCIAL"];

            // [3] 누락된 과목은 0으로 채우기
            const completeData = {};
            allSubjects.forEach(sub => {
                completeData[sub] = data[sub] || 0;
            });

            const labels = allSubjects.map(sub => subjectMap[sub]);
            const values = Object.values(data);
            const maxValue = Math.max(...values);

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
                    maintainAspectRatio: false,
                    scales: {
                        x: {
                            ticks: {
                                color: "#333",
                                font: { size: 14 }
                            },
                            title: {
                                display: true,
                                text: "과목명",
                                color: "#555",
                                font: { size: 16, weight: "bold" }
                            }
                        },
                        y: {
                            beginAtZero: true,
                            max: maxValue + 2,
                            ticks: { stepSize: 1 },
                            title: {
                                display: true,
                                text: "강의 개수",
                                color: "#555",
                                font: { size: 16, weight: "bold" }
                            }
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: "과목별 등록 강의 수 현황",
                            font: { size: 25 }
                        },
                        legend: { display: false },
                        tooltip: commonTooltip,   // 쉼표로 구분 후 아래 datalabels 추가
                        datalabels: {
                            anchor: 'end',
                            align: 'top',
                            color: '#000000',
                            font: { weight: 'bold', size: 18 }
                        }
                    }
                },
                plugins: [ChartDataLabels]
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

            const data = json.data || json; //실제 통계 데이터 추출
            if (!data) {
                console.error("데이터 없음:", json);
                return;
            }

            // [2] 모든 과목을 미리 정의 (순서 고정)
            const allDifficulties = ["기초", "핵심", "응용", "심화", "최상위"];

            // [3] 누락된 과목은 0으로 채우기
            const completeData = {};
            allDifficulties.forEach(level => {
                completeData[level] = data[level] || 0;
            });

            const labels = allDifficulties;
            const values = Object.values(completeData);

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
                        borderWidth: 3
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false, // 세로 길이 비율 고정 해제
                    plugins: {
                        title: {
                            display: true,
                            text: "난이도별 강의 분포",
                            font: { size: 25 }
                        },
                        legend: {
                            display: true,
                            position: "bottom",
                            labels: {
                                color: "#000222",
                                font: { size: 18 }
                            }
                        },
                        tooltip: commonTooltip
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
        .then(json => {
            const data = json.data || json;
            console.log("[Data] 등록 상태별 강의 수:", data)

            // 모든 가능한 상태 정의
            const statusMap = {
                REGISTERED: "등록완료",
                PENDING: "등록대기중",
                REJECTED: "반려",
                UNREGISTERED: "미등록",
            };

            const allStatusLabels = Object.values(statusMap);


            // 영문 key를 한글로 변환한 새 객체 만들기
            const localizedData = {};
            for (const [key, value] of Object.entries(data)) {
                const label = statusMap[key] || key;
                localizedData[label] = value;
            }

            // 없는 상태는 0으로 채워서 새 객체 생성
            const completeData = {};
            allStatusLabels.forEach(label => {
                completeData[label] = localizedData[label] || 0;
            });

            const labels = Object.keys(completeData);
            const values = Object.values(completeData);

            const ctx = document.getElementById("statusChart").getContext("2d");

            const colors = [
                "#4bc0c0", "#fae84f", "#ff6384", "#9966ff",
                "#36a2eb", "#ff9f40", "#a3e635", "#e879f9"
            ];

            // 데이터 길이에 맞게 slice (부족하면 랜덤)
            const backgroundColors = colors.slice(0, labels.length);


            console.log("📊 최종 labels:", labels);
            console.log("📊 최종 values:", values);
            console.log("📊 completeData:", completeData);
            new Chart(ctx, {
                type: "pie",
                data: {
                    labels: labels,
                    datasets: [{
                        data: values,
                        backgroundColor: backgroundColors,
                        borderWidth: 2
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false, // 세로 길이 비율 고정 해제
                    plugins: {
                        title: {
                            display: true,
                            text: "등록 상태별 강의 현황",
                            font: { size: 25 }
                        },
                        legend: {
                            display: true,
                            position: "bottom",
                            labels: {
                                color: "#000222",
                                font: {
                                    size: 16
                                },
                                padding: 20
                            }
                        },
                        tooltip: commonTooltip
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
        .then(json => {

            const data = json.data || json;
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
                    maintainAspectRatio: false, // 세로 길이 비율 고정 해제
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
                            font: { size: 25 }
                        },
                        legend: { display: false },
                        tooltip: commonTooltip,
                        datalabels: {
                            anchor: "end",
                            align: "right",
                            color: "#222",
                            font: { weight: "bold", size: 14 },
                            formatter: (v, ctx) => {
                                const rating = ctx.parsed?.x ?? ctx.parsed;
                                return typeof rating === "number" ? rating.toFixed(1) : "-";
                            }
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
<script>
    fetch("/admin/api/lectures/targetCount")
        .then(res => res.json())
        .then(json => {
            console.log("[Data] 학년별 강의 수:", json);
            const data = json.data || json;  // RestUtils 래핑 대응

            // 가능한 모든 학년 미리 정의
            const allGrades = ["고1", "고2", "고3"];

            // 데이터 누락 시 0으로 채우기
            const completeData = {};
            allGrades.forEach(grade => {
                completeData[grade] = data[grade] || 0;
            });

            const labels = Object.keys(completeData)
                .map(key => key.replace("HIGH", "고")); // 🔹 enum → 보기 좋은 한글로 변환;
            const values = Object.values(completeData);
            const maxValue = Math.max(...values, 1); // 전부 0이면 max=1로 방어

            const ctx = document.getElementById("targetChart").getContext("2d");
            new Chart(ctx, {
                type: "bar",
                data: {
                    labels: labels,
                    datasets: [{
                        label: "대상 학년별 강의 수",
                        data: values,
                        borderWidth: 1,
                        backgroundColor: [
                            "rgba(255, 99, 132, 0.6)",  // 고1
                            "rgba(54, 162, 235, 0.6)",  // 고2
                            "rgba(255, 206, 86, 0.6)"   // 고3
                        ]
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false, // 세로 길이 비율 고정 해제
                    scales: {
                        y: {
                            beginAtZero: true,
                            max: maxValue + 2,
                            ticks: { stepSize: 1 },
                            title: {
                                display: true,
                                text: "강의 개수",
                                color: "#000000",
                                font: { size: 16, weight: "bold" }
                            }
                        },
                        x: {
                            title: {
                                display: true,
                                text: "대상 학년",
                                color: "#555",
                                font: {size: 16, weight: "bold"}
                            }
                        }
                    },
                    plugins: {
                        title: {
                            display: true,
                            text: "대상 학년별 강의 수 분포",
                            font: {size: 25}
                        },
                        legend: {display: false},
                        tooltip: commonTooltip

                    }
                }
            });
        })
        .catch(err => {
            console.error("[ERROR] 대상 학년별 강의 수 분포 통계 로드 실패:", err);
    });
</script>
<script>
    fetch("/admin/api/lectures/salesBySubject")
        .then(res => res.json())
        .then(json => {
            const data = json.data || json;
            const subjectMap = {
                ENGLISH: "영어",
                KOREAN: "국어",
                MATH: "수학",
                SCIENCE: "과학",
                SOCIAL: "사회"
            };

            const labels = Object.keys(data).map(k => subjectMap[k] || k);
            const values = Object.values(data);

            const ctx = document.getElementById("salesBySubjectChart").getContext("2d");

            new Chart(ctx, {
                type: "bar",
                data: {
                    labels: labels,
                    datasets: [{
                        label: "과목별 총 매출 (원)",
                        data: values,
                        backgroundColor: [
                            "#4bc0c0", "#36a2eb", "#9966ff", "#ffcd56", "#ff6384"
                        ]
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        title: {
                            display: true,
                            text: "과목별 강의 매출 통계",
                            font: { size: 24 }
                        },
                        tooltip: commonTooltip,
                        legend: { display: false }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: value => value.toLocaleString() + "원"
                            }
                        }
                    }
                }
            });
        })
        .catch(err => {
            console.error("[ERROR] 매출 통계 로드 실패:", err);
        });
</script>
