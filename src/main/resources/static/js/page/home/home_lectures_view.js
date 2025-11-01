document.addEventListener("DOMContentLoaded", () => {
    const count = 5;

    const params = new URLSearchParams();
    // 변수 바인딩 추가
    params.append("count", count.toString());


    // ✅ [1] 최근 등록된 강의 조회
    fetch(`/api/home/recent?count=${count}`, {
        method: "GET",
        headers: {'X-Requested-From': window.location.pathname + window.location.search}
    })
        .then(res => res.json())
        .then(json => {
            // ⚠️ 문자열을 실제 배열로 변환
            const parsedData = json.data;
            renderRecentLectures(parsedData);
        })
        .catch(err => console.error("홈화면 최근 강의 조회 실패 : ", err));

    // ✅ [2] 인기 강의 조회
    fetch(`/api/home/best?count=${count}`, {
        method: "GET",
        headers: { 'X-Requested-From': window.location.pathname + window.location.search }
    })
        .then(res => res.json())
        .then(json => {
            // ⚠️ 문자열을 실제 배열로 변환
            const parsedData = json.data;
            renderBestLectures(parsedData);
        })
        .then(e => console.log(e))

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
        console.log("best lecture =" , lectures);

        lectures.forEach(bestLecture => {
            const item = document.createElement("div");

            const detailUrl = `/lecture/detail/${bestLecture.lectureId}`;
            const fileDomain = "http://localhost:8080/upload";

            // ✅ 썸네일 경로 처리
            const thumbnailSrc = bestLecture.thumbnailImagePath
                ? `${fileDomain}/${bestLecture.thumbnailImagePath}`
                : "/img/png/default_member_profile_image.png";

            item.classList.add("recent-lecture-item");
            item.innerHTML = `
            <a href="${detailUrl}">
                <img src="${thumbnailSrc}" alt="강의이미지" class="recent-lecture-thumbnail"
                 onerror="this.onerror=null; this.src='/img/png/default_member_profile_image.png';">
                <div class="lecture-info">
                    <p class="lecture-title">${bestLecture.title}</p>
                    <p class="lecture-info-text">${bestLecture.teacherNickname}</p>
                    <p class="lecture-info-text">₩${Number(bestLecture.price).toLocaleString()}</p>
                    <p class="lecture-info-text">
                         <!--⭐${bestLecture.averageRate}-->
                         🧸 ${bestLecture.totalStudents >= 10 ? "10+" : bestLecture.totalStudents}
                    </p>
                </div>
            </a>
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

        console.log("recent lecture =" , lectures);



        lectures.forEach(recentLecture => {
            const item = document.createElement("div");

            const detailUrl = `/lecture/detail/${recentLecture.lectureId}`;
            const fileDomain = "http://localhost:8080/upload";
            const thumbnailSrc = recentLecture.thumbnailImagePath
                ? `${fileDomain}/${recentLecture.thumbnailImagePath}`
                : "/img/png/default_member_profile_image.png";


            item.classList.add("recent-lecture-item");
            item.innerHTML = `
                <a href="${detailUrl}">
                    <img src="${thumbnailSrc}" alt="강의이미지" class="recent-lecture-thumbnail"
                 onerror="this.onerror=null; this.src='/img/png/default_member_profile_image.png';">
                        <div class="lecture-info">
                            <p class="lecture-title">${recentLecture.title}</p>
                            <p class="lecture-info-text">${recentLecture.teacherNickname}</p>
                            <p class="lecture-info-text">₩${Number(recentLecture.price).toLocaleString()}</p>
                            <p class="lecture-info-text">
                                 <!--⭐${recentLecture.averageRate}-->
                                 🧸 ${recentLecture.totalStudents >= 10 ? "10+" : recentLecture.totalStudents}
                            </p>
                        </div>
                    </a>
            `;
            container.appendChild(item);
        });
    }
});
