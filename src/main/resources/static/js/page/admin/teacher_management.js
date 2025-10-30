document.addEventListener("DOMContentLoaded", () => {
    // ✅ 페이지용 변수
    let currentPage = 1;
    const pageSize = 10;
    const tbody = document.getElementById("teachersTableBody");

    const subjectMap = {
        KOREAN: "국어",
        ENGLISH: "영어",
        MATH: "수학",
        SOCIAL: "사회탐구",
        SCIENCE: "과학탐구"
    };

    // 선생님 프로필로 보내는 링크
    const contextPath = document.getElementById("pageRoot").dataset.contextPath || "";


    const subjectFilter = document.getElementById("subjectFilter");
    if (subjectFilter) {
        subjectFilter.addEventListener("change", () => {
            console.log(`[과목 변경] ${subjectFilter.value}`);
            // 기존 검색 상태 유지
            const searchType = document.getElementById("searchType")?.value || "";
            const keyword = document.getElementById("keyword")?.value.trim() || "";

            loadTeachers(1, searchType, keyword);
        });
    }

    // [1] 메인 함수 - 강사 목록 불러오기
    function loadTeachers(page, searchType, keyword) {
        if (!page || isNaN(page) || page < 1) page = 1;

        searchType = searchType ?? (document.getElementById("searchType")?.value || "");
        keyword = keyword ?? (document.getElementById("keyword")?.value.trim() || "");

        const params = new URLSearchParams({
            page,
            size: pageSize
        });


        const subject = document.getElementById("subjectFilter")?.value || "";
        params.append("subject", subject);

        if (searchType) params.append("filter", searchType);
        if (keyword) params.append("keyword", keyword);

        const url = `/api/teachers?${params.toString()}`;
        console.log(`[FETCH] ${url}`);

        fetch(url, {
            method: "GET",
            headers: {
                "X-Requested-From": window.location.pathname + window.location.search,
            },
        })
            .then(async res => {
                if (!res.ok) {
                    throw new Error(`서버 오류 발생 (${res.status})`);
                }
                return await res.json();
            })
            .then(json => {
                // ✅ JSON 구조 안전 확인
                if (!json || typeof json !== "object") {
                    throw new Error("잘못된 서버 응답 구조입니다.");
                }

                if (!json.success) {
                    throw new Error(json.message || "요청 처리 중 오류가 발생했습니다.");
                }

                const raw = json.data || {};
                const teachers = Array.isArray(raw.data) ? raw.data : [];

                // ✅ 0건인 경우 화면 출력
                if (!teachers || teachers.length === 0) {
                    tbody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align:center; color:gray;">
                    '${keyword || subject || "검색"}'에 해당하는 강사 정보가 없습니다.
                </td>
            </tr>`;
                    console.warn("⚠️ 조회 결과 없음 - JS단 안전 처리");
                    renderPagination(1);
                    return;
                }

                // ✅ 정상 데이터 렌더링
                currentPage = raw.currentPage || 1;
                const totalPages = raw.totalPage || 1;
                renderTeachersTable(teachers);
                renderPagination(totalPages);
            })
            .catch(err => {
                console.error("[ERROR] 요청 실패:", err);
                tbody.innerHTML = `
                <tr>
                    <td colspan="7" style="text-align:center;">
                        ❌ 오류가 발생했습니다.<br>
                        ${err.message || "서버와의 통신에 실패했습니다."}
                    </td>
                </tr>`;
                renderPagination(1);

                return;
            });
    }

    // [2] 페이지네이션 렌더링
    function renderPagination(totalPages) {
        pagination.innerHTML = "";

        if (!totalPages || totalPages <= 0) return;

        const maxVisible = 5;
        const currentGroup = Math.ceil(currentPage / maxVisible);
        const start = (currentGroup - 1) * maxVisible + 1;
        const end = Math.min(totalPages, start + maxVisible - 1);

        // 이전 버튼
        const prev = document.createElement("button");
        prev.textContent = "◀";
        prev.className = `page-btn ${currentGroup === 1 ? "disabled" : ""}`;
        prev.onclick = () => {
            if (currentGroup > 1) {
                const prevGroupLastPage = (currentGroup - 2) * maxVisible + 1;
                loadTeachers(prevGroupLastPage);
            }
        };
        pagination.appendChild(prev);

        // 페이지 번호
        for (let i = start; i <= end; i++) {
            const btn = document.createElement("button");
            btn.textContent = i;
            btn.className = `page-btn ${i === currentPage ? "active" : ""}`;
            btn.onclick = () => loadTeachers(i);
            pagination.appendChild(btn);
        }

        // ▶ 다음 그룹 버튼
        const next = document.createElement("button");
        next.textContent = "▶";
        next.className = `page-btn ${end >= totalPages ? "disabled" : ""}`;
        next.onclick = () => {
            if (end < totalPages) {
                const nextGroupFirstPage = end + 1;
                loadTeachers(nextGroupFirstPage);
            }
        };
        pagination.appendChild(next);
    }

    // [3] 강사 테이블 렌더링
    function renderTeachersTable(teachers) {
        if (!tbody) return;

        tbody.innerHTML = ""; // 초기화
        if (!teachers.length) {
            tbody.innerHTML = `<tr><td colspan="8">등록된 강사가 없습니다.</td></tr>`;
            return;
        }

        teachers.forEach((t, idx) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${(currentPage - 1) * pageSize + idx + 1}</td>
                <td>
                    <a href="${contextPath}/teacher/profile/${t.teacherId}">
                    ${t.nickname || "-"}
                    </a>
                </td>
                <td>${t.email || "-"}</td>
                <td>${subjectMap[t.subject] || "-"}</td>
                <td>${t.totalReview ? t.totalReview : 0}</td>
                <td>${t.averageRating ? t.averageRating : "-"}</td>
                <td>${t.totalStudents ? t.totalStudents : 0}</td>
                <td><a href="#" class="management-button" data-teacher-id="${t.memberId}">상세정보</a></td>
            `;
            tbody.appendChild(row);
        });

        attachModalEvents();
    }

    // [4] 모달 이벤트 등록
    function attachModalEvents() {
        document.querySelectorAll(".management-button").forEach(btn => {
            btn.addEventListener("click", (e) => {
                e.preventDefault();
                const row = e.target.closest("tr");
                // 클릭할 때마다 DOM에서 새로 찾기 (스코프 안전)
                const modal = document.getElementById("memberModal");
                const closeBtn = modal.querySelector(".close-btn");
                const closeModalBtn = modal.querySelector("#closeModalBtn");

                const name = row.children[1].innerText;
                const email = row.children[2].innerText;
                const subjectName = row.children[3].innerText;
                const reviews = row.children[4].innerText;
                const rating = row.children[5].innerText;
                const totalStudents = row.children[6].innerText;

                document.getElementById("modalName").innerText = name;
                document.getElementById("modalEmail").innerText = email;
                document.getElementById("modalSubject").innerText = subjectName;
                document.getElementById("modalReviews").innerText = reviews;
                document.getElementById("modalRating").innerText = rating;
                document.getElementById("modalTotalStudents").innerText = totalStudents;

                modal.style.display = "flex";
                // 닫기 버튼
                if (closeBtn) closeBtn.onclick = () => (modal.style.display = "none");
                if (closeModalBtn) closeModalBtn.onclick = () => (modal.style.display = "none");
                window.onclick = (e) => {
                    if (e.target === modal) modal.style.display = "none";
                };
            });
        });
    }

    // [5] 검색 버튼 이벤트
    const searchBtn = document.getElementById("teacherSearchBtn");
    if (searchBtn) {
        searchBtn.addEventListener("click", () => {
            console.log("[검색 클릭] 필터 다시 읽기 후 첫 페이지 로드");
            document.getElementById("keyword").blur();

            tbody.innerHTML = "<tr><td colspan='8' style='text-align:center; color:#777;'>불러오는 중...</td></tr>";
            // 현재 입력값 읽기
            const searchType = document.getElementById("searchType")?.value || "";
            const keyword = document.getElementById("keyword")?.value.trim() || "";

            loadTeachers(1, searchType, keyword);
        });
    }

    // [5-1] 엔터키 검색
    const keywordInput = document.getElementById("keyword");
    if (keywordInput) {
        keywordInput.addEventListener("keypress", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                searchBtn.click();
            }
        });
    }

    // ✅ 초기 실행
    loadTeachers(currentPage);
});
