document.addEventListener("DOMContentLoaded", () => {
    const count = 4;
    const subjectFromJSP = document.getElementById("lecturePage").dataset.subject;

    const params = new URLSearchParams();
    params.append("subject", subjectFromJSP);
    params.append("count", count.toString());

    fetch(`/api/lecture/reviews/recent/${subjectFromJSP}?count=${count}`, {
        method: "GET"
    })
            .then(res => {
                if (!res.ok) throw new Error("HTTP " + res.status);
                return res.json();
            })
            .then(json => {
                renderRecentReviews(json.data);
            })
            .catch(err => console.error("최근 수강평 조회 실패 : ", err));

    // DOM 렌더링 함수
    function renderRecentReviews(reviews) {
        const titles = document.querySelectorAll(".recomment-lecture-title");
        let container = null;

        titles.forEach(title => {
            if (title.textContent.trim().includes("최근 수강평")) {
                // "최근 수강평" 다음에 오는 div.recent-lecture-container 선택
                container = title.nextElementSibling;
            }
        })
        if (!container) {
            console.error("최근 수강평 조회 중 문제 발생");
            return;
        }

        container.innerHTML = ""; // 기존 내용 초기화

        if (!reviews || reviews.length === 0) {
            container.innerHTML = `<p class="no-lecture">최근 등록된 리뷰가 없습니다.</p>`;
            return;
        }

        reviews.forEach((review) => {
            const item = document.createElement("div");
            item.classList.add("recent-comment-box-item");

            item.innerHTML = `
                <div class="lecture-comment-username">${review.nickname}</div>
                <div class="lecture-comment-comment">${review.content}</div>
            `;
            container.appendChild(item);
        });
    }
})