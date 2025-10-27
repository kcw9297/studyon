document.addEventListener("DOMContentLoaded", () => {
    const teacherJSP = document.getElementById("teacher").dataset.subject;

    const params = new URLSearchParams();
    params.append("subject", teacherJSP);

    fetch(`/api/teachers/subject/${teacherJSP}`, {
        method: "GET"
    })
        .then(res => res.json())
        .then(json => {
            console.log("📦 서버 응답:", json);
            console.log("👩‍🏫 선생님 수:", json.data?.length);
            renderSubjectTeachers(json.data);
        })
        .catch(err => console.log("관련 선생님 정보 불러오기 실패 : ", err));

    function renderSubjectTeachers(teachers) {
        const titles = document.querySelector(".teacher-list-title");
        let container = titles?.nextElementSibling;

        if (!container) {
            console.error("과목별 선생님 조회 중 문제 발생");
            return;
        }

        container.innerHTML = ""; // 기존 내용 초기화

        if (!teachers || teachers.length === 0) {
            container.innerHTML = `<p class="no-lecture">해당 과목의 선생님이 존재하지 않습니다.</p>`;
            return;
        }

        teachers.forEach((teacher) => {
            const item = document.createElement("div");

            // div 클릭 시 들어갈 링크
            const detailUrl = `/teacher/profile/${teacher.teacherId}`;

            item.classList.add("recent-lecture-item");

            item.innerHTML = `
                <img src="/img/png/sample1.png" alt="강의이미지" class="recent-lecture-thumbnail">
                <div class="lecture-info">
                  <p class="lecture-title">${teacher.nickname}</p>
                  <p class="lecture-info-text">${teacher.description || "소개가 없습니다."}</p>
                  <!-- <p class="lecture-info-text">⭐${teacher.averageRating ?? "0.0"}</p> -->
                </div>
            `;
            container.appendChild(item);
        })
    }
})