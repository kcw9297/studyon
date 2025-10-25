document.addEventListener("DOMContentLoaded", () => {

    // [1] 총 회원 수 가져오기
    fetch(`/admin/api/home/countAllMembers`)
        .then(res => res.json())
        .then(json => {
            //  RestUtils.ok() 구조 고려 필요
            if (!json.success) {
                console.error("회원 수 조회 실패:", json?.message);
                return;
            }
            const count = json.data || 0; // data 없으면 json 자체를 숫자로 처리
            renderStatusValue(".status-card","totalMember", `${count.toLocaleString()}명`);
        })
        .catch(err => console.error("전체 회원 수 조회 실패:", err));

    // [2] 총 강의 수 가져오기
    fetch(`/admin/api/home/countAllLectures`)
        .then(res => res.json())
        .then(json => {
            if (!json.success) {
                console.error("총 강의 수 조회 실패:", json?.message);
                // 500 오류 시 json이 null일 수 있으므로 오류 방지를 위해 ?. 연산자 추가
                return;
            }
            const count = json?.data ?? 0; // data 없으면 json 자체를 숫자로 처리
            renderStatusValue(".status-card", "lecture", `${count.toLocaleString()}개`);
        })
        .catch(err => console.error("전체 강의 수 조회 실패:", err));

    // [3] 오늘 신규 회원 수 가져오기
    fetch(`/admin/api/home/countNewMembers`)
        .then(res => res.json())
        .then(json => {
            if (!json.success) {
                console.error("오늘 신규 회원 수 조회 실패:", json?.message);
                return;
            }
            const count = json?.data ?? 0; // data 없으면 json 자체를 숫자로 처리
            renderCardNumber(".dashboard-card", "newMember", `${count.toLocaleString()}명`);
        })
        .catch(err => console.error("오늘 신규 회원 수 조회 실패:", err));

    // [4] 활성 사용자 수 가져오기
    fetch(`/admin/api/home/activeMembers`)
        .then(res => res.json())
        .then(json => {
            const count = json?.data ?? 0; // data 없으면 json 자체를 숫자로 처리
            renderStatusValue(".status-card","activeMember", `${count.toLocaleString()}명`);
        })
        .catch(err => console.error("활성 사용자 수 조회 실패:", err));


    // [5] 이번 달 매출 가져오기
    fetch(`/admin/api/home/totalSales`)
        .then(res => res.json())
        .then(json => {
            if (!json.success) {
                console.error("이번달 총 매출액 수 조회 실패:", json?.message);
                // 500 오류 시 json이 null일 수 있으므로 오류 방지를 위해 ?. 연산자 추가
                return;
            }
            const count = Number(json?.data ?? 0);
            renderCardNumber(".dashboard-card.sales", "totalSales", `₩${count.toLocaleString()}`);
            // ".dashboard-card.sales" == <div class="dashboard-card sales" ...>
        })
        .catch(err => console.error("이번달 총 매출액 수 조회 실패:", err));


    // [6] TOP 선생님
    fetch(`/admin/api/home/topTeacher`)
        .then(res => res.json())
        .then(json => {
            if (!json.success) {
                console.error("이번달 MVP 강사 조회 실패:", json?.message);
                return;
            }
            
            // 넘어오는 JSON 문자열 -> 실제 객체 전환
            const teacher = JSON.parse(json.data);
            // 데이터 없을 경우 예외 처리
            if (!teacher) {
                renderCardNumber(".dashboard-card", "topTeacher", "데이터 없음");
                return;
            }

            const name = teacher.nickname ?? "(이름 없음)";
            const sales = teacher.totalSales ?? 0;
            const month = new Date().getMonth() + 1;

            // [1] 이름 출력
            renderCardNumber(".dashboard-card", "topTeacher", `${name} 강사`);

            // [2] 매출 정보는 card-sub 수동 갱신
            const card = document.querySelector('.dashboard-card[data-type="topTeacher"]');
            const sub = card.querySelector(".card-sub");
            if (sub) {
                sub.textContent = `매출 ₩${Number(sales).toLocaleString()} (${month}월 기준)`;
            }
        })
        .catch(err => console.error("이번달 MVP 강사 조회 실패:", err));



    /** [★] 렌더링 함수 정의 (data-type으로 선택)
     */
    
    // [★-1] 렌더링 함수 - status-value용 (data-type으로 선택)
    // param className : CSS 해당되는 태그 이름
    // param type : HTML 요소 데이터 타입(data-type)
    // param text : HTML에 표시될 요소 텍스트
    function renderStatusValue(className, type, text) {
        const card = document.querySelector(`${className}[data-type="${type}"]`);
        if (!card) {
            console.error(`${type} StatusValue 카드 요소를 찾을 수 없습니다.`);
            return;
        }

        const valueElement = card.querySelector(".status-value");
        if (!valueElement) {
            console.error(`${type}의 .status-value 요소를 찾을 수 없습니다.`);
            return;
        }

        valueElement.textContent = text;
    }

    // [★-2] 렌더링 함수 - dashboard-card-number 용 (data-type으로 선택)
    // param className : CSS 해당되는 태그 이름
    // param type : HTML 요소 데이터 타입(data-type)
    // param text : HTML에 표시될 요소 텍스트

    function renderCardNumber(className, type, text) {
        const card = document.querySelector(`${className}[data-type="${type}"]`);
        if (!card) {
            console.error(`${type} CardNumber 카드 요소를 찾을 수 없습니다.`);
            return;
        }
        const valueElement = card.querySelector(".card-number");
        if (!valueElement) {
            console.error(`${type}의 .card-number 요소를 찾을 수 없습니다.`);
            return;
        }
        valueElement.textContent = text;
    }
});
