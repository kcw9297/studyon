document.addEventListener("DOMContentLoaded", () => {
    const teacherId = document.getElementById("teacherBestLectures").dataset.teacherId;

    const params = new URLSearchParams();
    params.append("teacherId", teacherId);

    fetch(`/api/teachers/profile/bestLecture`, {
        method: "POST",
        headers: { 'X-Requested-From': window.location.pathname + window.location.search },
        body: params
    })
        .then(res => res.json())
        .then(json => {
            console.log("서버 응답: " , json);
            renderBestLectures(json.data);
        })
        .catch(err => console.error("수강평 불러오기 실패 : ", err));

    function renderBestLectures(bestLectures) {
        const teacherBestSection = document.getElementById("teacherBestLectures");
        const container = teacherBestSection.querySelector(".recent-lecture-container");
        container.innerHTML = "";

        if (!bestLectures || bestLectures.length === 0) {
            container.innerHTML = "<p>강의 정보가 조회되지 않습니다.</p>";
            return;
        }

        bestLectures.forEach((bestLecture) => {
            const item = document.createElement("div");
            item.classList.add("recent-lecture-item")

            // 🧩 변수로 미리 포맷팅
            const price = bestLecture.price.toLocaleString("ko-KR");  // 12,000 형식
            const students = bestLecture.totalStudents >= 10 ? "10+" : bestLecture.totalStudents;
            const rating = bestLecture.averageRate ?? "N/A";
            const description = bestLecture.description || "강의 소개가 없습니다.";

            // div 클릭 시 들어갈 링크
            const detailUrl = `/lecture/detail/${bestLecture.lectureId}`;
            // ✅ JSP 구조 그대로 템플릿화
            item.innerHTML = `
            <a href="${detailUrl}"
                <img src="/img/png/sample1.png" alt="강의이미지" class="recent-lecture-thumbnail">
                <div class="lecture-info">
                    <p class="lecture-title">${bestLecture.title}</p>
                    <p class="lecture-info-text">${description}</p>
                    <p class="lecture-info-text">₩${price}</p>
                    <p class="lecture-info-text"><!--⭐ ${rating}--></p>
                    <p class="lecture-info-text">
                        &#x1F9F8; ${students}
                    </p>
                </div>
            </a>
            `;
            container.appendChild(item);
        });
    }
});