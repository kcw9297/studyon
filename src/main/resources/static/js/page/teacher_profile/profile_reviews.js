document.addEventListener("DOMContentLoaded", () => {
    const teacherId = document.getElementById("teacherComment").dataset.teacherId;

    const params = new URLSearchParams();
    params.append("teacherId", teacherId);

    fetch("/api/teachers/reviews/{teacherId}" , {
        method: "POST",
        headers: { "Content-Type" : "application/x-www-form-urlencoded" },
        body: params
    })
        .then(res => res.json())
        .then(json => {
            console.log("서버 응답: " , json);
            renderComments(json.data);
        })
        .catch(err => console.error("수강평 불러오기 실패 : ", err));

    function renderComments(comments) {
        const container = document.querySelector(".lecture-comment-box");
        container.innerHTML = "";

        if (!comments || comments.length === 0) {
            container.innerHTML = "<p>아직 등록된 수강평이 없습니다.</p>";
            return;
        }

        comments.forEach(comment => {
            const item = document.createElement("div");
            item.classList.add("lecture-comment-box-item")
            item.innerHTML = `
                <div class="lecture-comment-username">${comment.nickname}</div>
                <div class="lecture-comment-comment">${comment.content}</div>
            `;
            container.appendChild(item);
        });
    }
});

