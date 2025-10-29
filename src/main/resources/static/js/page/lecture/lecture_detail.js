// 영어 → 한국어 매핑
document.addEventListener("DOMContentLoaded", () => {
    const translations = {
        "KOREAN": "국어",
        "ENGLISH": "영어",
        "MATH": "수학",
        "SCIENCE": "과학탐구",
        "SOCIAL": "사회탐구",
        "BASIC": "기초",
        "STANDARD": "핵심",
        "ADVANCED": "심화",
        "EXPERT": "응용",
        "MASTER": "최상급"
    };

    document.querySelectorAll("[data-en]").forEach(el => {
        const enText = el.getAttribute("data-en");
        if(translations[enText]) {
            el.innerHTML = translations[enText].replace(/\n/g, "<br/>");
        }
    });
});



// 좋아요 버튼
document.addEventListener("DOMContentLoaded", function () {
    const likeButton = document.querySelector(".summary-like");
    if (!likeButton) return;

    const lectureId = likeButton.dataset.lectureId;
    const memberId = 93; // 로그인 유저 ID
    const likeImg = likeButton.querySelector("img");
    const likeCountElem = likeButton.querySelector(".like-count");

    let liked = false; // 클릭 시 토글용
    let likeCount = parseInt(likeCountElem.textContent); // 초기 숫자

    // 1️⃣ 초기 상태 fetch
    fetch(`/lecture-like/${lectureId}/status?memberId=${memberId}`)
        .then(res => res.json())
        .then(data => {
            liked = data.liked;
            likeImg.src = liked ? "/img/png/like2.png" : "/img/png/like1.png";
            if (data.likeCount !== undefined) {
                likeCount = data.likeCount;
                likeCountElem.textContent = likeCount;
            }
        })
        .catch(err => console.error("좋아요 상태 불러오기 실패:", err));

    // 2️⃣ 버튼 클릭 시 토글
    likeButton.addEventListener("click", () => {
        const url = liked
            ? `/lecture-like/${lectureId}/remove`
            : `/lecture-like/${lectureId}/add`;

        const dto = { memberId: memberId, lectureId: parseInt(lectureId) };

        fetch(url, {
            method: liked ? "DELETE" : "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dto)
        })
            .then(res => res.json())
            .then(data => {
                liked = data.liked;
                likeImg.src = liked ? "/img/png/like2.png" : "/img/png/like1.png";

                likeCountElem.textContent = data.likeCount;
            })
            .catch(err => console.error("좋아요 토글 오류:", err));
    });
});


// 네비게이션
document.addEventListener("DOMContentLoaded", () => {
    const sections = document.querySelectorAll(".introduce-content");
    const navLinks = document.querySelectorAll(".navigation a");

    // 유저 스크롤 감지
    window.addEventListener("scroll", () => {
        let current = ""; // 섹션id 저장소
        sections.forEach(section => { // 영역 확인
            const sectionTop = section.offsetTop - 100;
            const sectionHeight = section.offsetHeight;
            // 스크롤이 섹션 시작~끝에 있으면 해당 위치
            if (window.scrollY >= sectionTop && window.scrollY < sectionTop + sectionHeight) {
                current = section.getAttribute("id");
            }
        });

        navLinks.forEach(link => { // 네비게이션 바 활성화
            link.classList.remove("active");
            // 링크=스크롤 위치 일치하면 active
            if (link.getAttribute("href") === `#${current}`) {
                link.classList.add("active");
            }
        });
    });
});


// 리뷰 더보기 버튼
document.addEventListener("DOMContentLoaded", () => {
    const reviewsList = document.querySelector(".reviews-list");
    if (!reviewsList) return; // 리뷰 목록 없으면 종료

    const reviews = Array.from(reviewsList.querySelectorAll(".reviews-comment"));
    const moreBtn = document.querySelector(".reviews-more");
    const perPage = 3;

    // 리뷰가 더 없으면 버튼 숨기고 종료
    if (reviews.length === 0) {
        if (moreBtn) moreBtn.style.display = "none";
        return;
    }

    // 초기 상태: 3개만
    function init() {
        reviews.forEach((r, i) => {
            if (i < perPage) {
                r.classList.remove("reviews-hidden");
            } else {
                r.classList.add("reviews-hidden");
            }
        });
        currentIndex = Math.min(perPage, reviews.length);
        updateButton();
    }

    // 더보기: 3개씩 주기
    function showMore() {
        const next = Math.min(currentIndex + perPage, reviews.length);
        for (let i = currentIndex; i < next; i++) {
            reviews[i].classList.remove("reviews-hidden");
        }
        currentIndex = next;
        updateButton();
    }

    // 버튼 표시/비표시
    function updateButton() {
        if (!moreBtn) return;
        if (currentIndex >= reviews.length) {
            moreBtn.style.display = "none";
        } else {
            moreBtn.style.display = "block";
        }
    }

    // 안전 장치: 더보기 버튼 누를 때 예외 처리를 위해 이벤트 리스너 등록
    let currentIndex = 0;
    if (moreBtn) {
        moreBtn.addEventListener("click", () => {
            try {
                showMore();
            } catch (e) {
                console.error("showMore 에러:", e);
            }
        });
    }

    // 초기 실행
    init();

    // (디버깅용) 콘솔에 상태 출력 — 문제 있으면 개발자도구에서 확인하세요
    console.log("reviews 총개수:", reviews.length, "초기 표시 개수:", currentIndex);
});


// 알고리즘 슬라이드
document.addEventListener("DOMContentLoaded", () => {
    const list = document.querySelector(".algorithm-list");
    const items = document.querySelectorAll(".algorithm-item");
    const prevBtn = document.querySelector(".algorithm-no");
    const nextBtn = document.querySelector(".algorithm-yes");

    const itemsPerPage = 4; // 한 페이지에 4개
    const totalItems = items.length;
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    let currentPage = 0;

    // 슬라이드 업데이트 함수
    function updateSlide() {
        const offset = -(320 + 40) * itemsPerPage * currentPage;
        // (아이템 width 320 + gap 40)
        list.style.transform = `translateX(${offset}px)`;
        list.style.transition = "transform 0.5s ease";

        // 버튼 활성/비활성 처리
        if (currentPage === 0) {
            prevBtn.classList.remove("algorithm-yes");
            prevBtn.classList.add("algorithm-no");
        } else {
            prevBtn.classList.remove("algorithm-no");
            prevBtn.classList.add("algorithm-yes");
        }

        if (currentPage >= totalPages - 1) {
            nextBtn.classList.remove("algorithm-yes");
            nextBtn.classList.add("algorithm-no");
        } else {
            nextBtn.classList.remove("algorithm-no");
            nextBtn.classList.add("algorithm-yes");
        }
    }

    // 다음 버튼
    nextBtn.addEventListener("click", () => {
        if (currentPage < totalPages - 1) {
            currentPage++;
            updateSlide();
        }
    });

    // 이전 버튼
    prevBtn.addEventListener("click", () => {
        if (currentPage > 0) {
            currentPage--;
            updateSlide();
        }
    });

    // 초기 상태
    updateSlide();
});
