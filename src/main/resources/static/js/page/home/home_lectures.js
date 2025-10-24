document.addEventListener("DOMContentLoaded", () => {
    const count = 4;

    const params = new URLSearchParams();
    // 변수 바인딩 추가
    params.append("count", count.toString());


    // ✅ [1] 최근 등록된 강의 조회
    fetch("/api/home/recent", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: params
    })
        .then(res => res.json())
        .then(json => renderRecentLectures(json.data))
        .catch(err => console.error("홈화면 인기 강의 조회 실패 : ", err));

    // ✅ [2] 인기 강의 조회
    fetch("/api/home/best", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: params
    })
        .then(res => res.json())
        .then(json => renderBestLectures(json.data))
        .catch(err => console.error("홈화면 인기 강의 조회 실패:", err));

    /* -- 렌더 함수 -- */

    function renderBestLectures(lectures) {
        // ✅ 단일 요소 선택
        const container = document.querySelector("#popularLectureContainer");

        if (!container) {
            console.error("홈화면 인기 강의 컨테이너 조회 실패");
            return;
        }

        container.innerHTML = "";

        if (!lectures || lectures.length === 0) {
            container.innerHTML = `<p>최근 인기 강의가 없습니다.</p>`;
            return;
        }

        lectures.forEach(bestLecture => {
            const item = document.createElement("div");
            item.classList.add("recent-lecture-item");
            item.innerHTML = `
                <img src="/img/png/sample1.png" alt="강의이미지">
                <div class="lecture-info">
                    <p class="lecture-title">${bestLecture.title}</p>
                    <p class="lecture-info-text">${bestLecture.nickname}</p>
                    <p class="lecture-info-text">₩${Number(bestLecture.price).toLocaleString()}</p>
                    <p class="lecture-info-text">
                         <!--⭐${bestLecture.averageRate}-->
                         🧸 ${bestLecture.totalStudents >= 10 ? "10+" : bestLecture.totalStudents}
                    </p>
                </div>
            `;
            container.appendChild(item);
        });
    }
    function renderRecentLectures(lectures) {
        // ✅ 단일 요소 선택
        const container = document.querySelector("#recentLectureContainer");

        if (!container) {
            console.error("홈화면 최신 강의 컨테이너 조회 실패");
            return;
        }

        container.innerHTML = "";

        if (!lectures || lectures.length === 0) {
            container.innerHTML = `<p>최신 강의가 없습니다.</p>`;
            return;
        }

        lectures.forEach(recentLecture => {
            const item = document.createElement("div");
            item.classList.add("recent-lecture-item");
            item.innerHTML = `
                <img src="/img/png/sample1.png" alt="강의이미지">
                <div class="lecture-info">
                    <p class="lecture-title">${recentLecture.title}</p>
                    <p class="lecture-info-text">${recentLecture.nickname}</p>
                    <p class="lecture-info-text">₩${Number(recentLecture.price).toLocaleString()}</p>
                    <p class="lecture-info-text">
                         <!--⭐${recentLecture.averageRate}-->
                         🧸 ${recentLecture.totalStudents >= 10 ? "10+" : recentLecture.totalStudents}
                    </p>
                </div>
            `;
            container.appendChild(item);
        });
    }
});
