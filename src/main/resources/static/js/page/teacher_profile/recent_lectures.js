document.addEventListener("DOMContentLoaded", () => {
    const teacherId = document.getElementById("teacherRecentLectures").dataset.teacherId;

    const params = new URLSearchParams();
    params.append("teacherId", teacherId);

    fetch(`/api/teachers/profile/recentLecture`, {
        method: "GET",
        headers: { 'X-Requested-From': window.location.pathname + window.location.search } ,
        body: params
    })
        .then(res => res.json())
        .then(json => {
            console.log("서버 응답: " , json);
            renderRecentLectures(json.data);
        })
        .catch(err => console.error("수강평 불러오기 실패 : ", err));

    function renderRecentLectures(recentLectures) {
        const teacherRecentSection = document.getElementById("teacherRecentLectures");
        const container = teacherRecentSection.querySelector(".recent-lecture-container");
        container.innerHTML = "";

        if (!recentLectures || recentLectures.length === 0) {
            container.innerHTML = "<p>강의 정보가 조회되지 않습니다.</p>";
            return;
        }

        recentLectures.forEach((recentLecture) => {
            const item = document.createElement("div");
            item.classList.add("recent-lecture-item")

            // 🧩 변수로 미리 포맷팅
            const price = recentLecture.price.toLocaleString("ko-KR");  // 12,000 형식
            const students = recentLecture.totalStudents >= 10 ? "10+" : recentLecture.totalStudents;
            const rating = recentLecture.averageRate ?? "N/A";

            // div 클릭 시 들어갈 링크
            const detailUrl = `/lecture/detail/${recentLecture.lectureId}`;
            
            item.innerHTML = `
                <a href="${detailUrl}">
                    <img src="/img/png/sample1.png" alt="강의이미지" class="recent-lecture-thumbnail">
                    <div class="lecture-info">
                        <p class="lecture-title">${recentLecture.title}</p>
                        <p class="lecture-info-text">${recentLecture.description}</p>
                        <p class="lecture-info-text">₩${price}</p>
                        <p class="lecture-info-text"><!--⭐${rating}--></p>
                        <p class="lecture-info-text">&#x1F9F8; ${students}</p>
                    </div>
                </a>
            `;
            container.appendChild(item);
        });
    }
});