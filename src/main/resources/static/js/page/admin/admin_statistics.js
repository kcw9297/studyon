document.addEventListener("DOMContentLoaded", () => {

    // [1] 총 회원 수 가져오기
    fetch("/api/admin/home/countAllMembers")
        .then(res => res.json())
        .then(json => {
            //  RestUtils.ok() 구조 고려 필요
            if (!json.success) {
                console.error("회원 수 조회 실패:", json.message);
                return;
            }
            const count = json.data; // <-- 핵심 수정
            renderStatusValue(".status-card","totalMember", `${count.toLocaleString()}명`);
        })
        .catch(err => console.error("전체 회원 수 조회 실패:", err));

    // [2] 총 강의 수 가져오기
    fetch("/api/admin/home/countAllLectures")
        .then(res => res.json())
        .then(json => {
            if (!json.success) {
                console.error("총 강의 수 조회 실패:", json.message);
                return;
            }
            const count = json.data;
            renderStatusValue(".status-card", "lecture", `${count.toLocaleString()}개`);
        })
        .catch(err => console.error("전체 강의 수 조회 실패:", err));

    // [3] 오늘 신규 회원 수 가져오기
    fetch("/api/admin/home/countNewMembers")
        .then(res => res.json())
        .then(json => {
            if (!json.success) {
                console.error("오늘 신규 회원 수 조회 실패:", json.message);
                return;
            }
            const count = json.data;
            renderCardNumber(".card-number", "newMember", `${count.toLocaleString()}명`);
        })
        .catch(err => console.error("오늘 신규 회원 수 조회 실패:", err));

    // [4] 활성 사용자 수 가져오기
    fetch("/api/admin/home/activeMembers")
        .then(res => res.json())
        .then(json => {
            const count = json.data;
            renderStatusValue(".status-card","activeMember", `${count.toLocaleString()}명`);
        })
        .catch(err => console.error("활성 사용자 수 조회 실패:", err));


    // [5] 이번 달 매출 가져오기
    fetch("/api/admin/home/totalSales")
        .then(res => res.json())
        .then(json => {
            if (!json.success) {
                console.error("이번달 총 매출액 수 조회 실패:", json.message);
                return;
            }
            const count = json.data;
            renderCardNumber(".card-number", "totalSales", `₩${count.toLocaleString()}`);
        })
        .catch(err => console.error("이번달 총 매출액 수 조회 실패:", err));



    /** [★] 렌더링 함수 정의 (data-type으로 선택)
     */
    
    // [★-1] 렌더링 함수 - status-value용 (data-type으로 선택)
    // param className : CSS 해당되는 태그 이름
    // param type : HTML 요소 데이터 타입(data-type)
    // param text : HTML에 표시될 요소 텍스트
    function renderStatusValue(className, type, text) {
        const card = document.querySelector(`${className}[data-type="${type}"]`);
        if (!card) {
            console.error(`${type} 카드 요소를 찾을 수 없습니다.`);
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
            console.error(`${type} 카드 요소를 찾을 수 없습니다.`);
            return;
        }

        const valueElement = card.querySelector(".card-number");
        if (!valueElement) {
            console.error(`${type}의 .status-value 요소를 찾을 수 없습니다.`);
            return;
        }

        valueElement.textContent = text;
    }
});
