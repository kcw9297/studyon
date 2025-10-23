document.addEventListener("DOMContentLoaded", () => {
    const subject = document.getElementById("lecturePage").dataset.subject;
    const count = 4;

    fetch("/api/lecture/best", {
        method: "POST",
        body: (() => {
            const formData = new FormData();
            formData.append("subject", subject);
            formData.append("count", count.toString());
            return formData;
            }) ()
    })
        .then(res => res.json())
        .then(data => renderBestLectures(data))
        .catch(err => console.error("인기 강의 로딩 실패 ㅜㅜ : " , err));

    function renderBestLectures(lectures) {
        const titles = document.querySelectorAll(".recomment-lecture-title");
        let container = null;
        
        titles.forEach(title => {
            if (title.textContent.trim().includes("주간 인기/추천 강의")) {
                container = title.nextElementSibling;  // 현재 요소의 바로 다음 형제 요소를 반환
            }
        });

        if (!container) {
            console.error("인기 강의 컨테이너 조회 실패")
            return;
        }

        container.innerHTML = "";

        if (!lectures || lectures.length === 0) {
            container.innerHTML = `<p>인기 강의가 없습니다.</p>`
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
              <!--⭐<c:out value="${bestLecture.averageRate}" />-->
              <p class="lecture-info-text">🧸 ${bestLecture.totalStudents >= 10 ? "10+" : bestLecture.totalStudents}</p>
            </div>`;
            container.appendChild(item);
        })
    }
})