document.addEventListener("DOMContentLoaded", () => {
    const count = 4;
    const subjectFromJSP = document.getElementById("lecturePage").dataset.subject;

    const params = new URLSearchParams();
    params.append("subject", subjectFromJSP);
    params.append("count", count.toString());

    fetch("/api/lecture/recent", {
        method: "GET"
    })
        .then(res => {
            if (!res.ok) throw new Error("HTTP " + res.status);
            return res.json();
        })
        .then(json => {
            console.log("✅ 최근 강의 데이터:", json.data);
            renderRecentLectures(json.data);
        })
        .catch(err => console.error("최근 강의 데이터 요청 실패:", err));

    // ✅ DOM 렌더링 함수
    function renderRecentLectures(lectures) {
        const titles = document.querySelectorAll(".recomment-lecture-title");
        let container = null;

        titles.forEach(title => {
            if (title.textContent.trim().includes("요새 뜨는 강의")) {
                // "요새 뜨는 강의" 다음에 오는 div.recent-lecture-container 선택
                container = title.nextElementSibling;
            }
        });

        if (!container) {
            console.error("최근 강의 조회 중 문제 발생");
            return;
        }

        container.innerHTML = ""; // 기존 내용 초기화
        
        if (!lectures || lectures.length === 0) {
            container.innerHTML = `<p class="no-lecture">최근 등록된 강의가 없습니다.</p>`;
            return;
        }

        // 비동기 데이터 렌더링 단계
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
            ⭐ ${recentLecture.averageRate ?? "0.0"}
            🧸 ${recentLecture.totalStudents >= 10 ? "10+" : recentLecture.totalStudents}
          </p>
        </div>
      `;
            container.appendChild(item);
        });
    }
});
