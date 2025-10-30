document.addEventListener("DOMContentLoaded", () => {
    const subjectFromJSP = document.getElementById("lecturePage").dataset.subject;
    const count = 4;

    fetch(`/api/lectures/best?subject=${subjectFromJSP}&count=${count}`, {
        method: "GET",
        headers: { 'X-Requested-From': window.location.pathname + window.location.search }
    })
        .then(res => res.json())
        .then(json => {
            // ⚠️ 문자열을 실제 배열로 변환
            const parsedData = json.data;
            console.log("✅ 최근 인기 강의 데이터:", parsedData);
            renderBestLectures(parsedData);
        })
        .catch(err => console.error("강의 추천 페이지 주간 인기 강의 조회 실패 : " , err));

    function renderBestLectures(lectures) {
        const titles = document.querySelectorAll(".recomment-lecture-title");
        let container = null;
        
        titles.forEach(title => {
            if (title.textContent.trim().includes("주간 인기/추천 강의")) {
                container = title.nextElementSibling;  // 현재 요소의 바로 다음 형제 요소를 반환
            }
        });

        if (!container) {
            console.error("강의 추천 페이지 인기 강의 컨테이너 조회 실패")
            return;
        }

        container.innerHTML = "";

        if (!lectures || lectures.length === 0) {
            container.innerHTML = `<p>인기 강의가 없습니다.</p>`
            return;
        }

        lectures.forEach(bestLecture => {
            const item = document.createElement("div");

            const detailUrl = `/lecture/detail/${bestLecture.lectureId}`;

            item.classList.add("recent-lecture-item");
            item.innerHTML = `
            <a href="${detailUrl}">
                <img src="/img/png/sample1.png" alt="강의이미지" class="recent-lecture-thumbnail">
                <div class="lecture-info">
                    <p class="lecture-title">${bestLecture.title}</p>
                    <p class="lecture-info-text">${bestLecture.nickname}</p>
                    <p class="lecture-info-text">₩${Number(bestLecture.price).toLocaleString()}</p>
                    <!--⭐<c:out value="${bestLecture.averageRate}" />-->
                    <p class="lecture-info-text">🧸 ${bestLecture.totalStudents >= 10 ? "10+" : bestLecture.totalStudents}</p>
                </div>
            </a>`

            container.appendChild(item);
        })
    }
})