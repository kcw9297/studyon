<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/admin.css'/>">

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="dashboard"/>
</jsp:include>

<div class="admin-content-container">

    <%-- status-value, card-number 숫자는 자동으로 업데이트되게 해놓음 --%>

    <!-- 신규 회원 수 카드 -->
    <div class="dashboard-card" data-type="newMember">
        <div class="card-icon">👤</div>
        <div class="card-info">
            <div class="card-title">신규 회원 수</div>
            <div class="card-number">-명</div>
            <div class="card-sub">오늘 가입</div>
        </div>
    </div>

    <!-- 이번달 매출 카드 -->
    <div class="dashboard-card sales" data-type="totalSales">
        <div class="card-icon">💰</div>
        <div class="card-info">
            <div class="card-title">이번달 매출</div>
            <div class="card-number">₩0</div>
            <div class="card-sub">10월 기준</div>
        </div>
    </div>

    <div class="dashboard-card mvp" data-type="topTeacher">
        <div class="card-icon">🏆</div>
        <div class="card-info">
            <div class="card-title">이번달 MVP 강사</div>
            <div class="card-number">최예나 강사</div>
            <div class="card-sub">매출 ₩3,280,000 (10월 기준)</div>
        </div>
    </div>
    <!--
        <div class="dashboard-card loser">
            <div class="card-icon">💀</div>
            <div class="card-info">
                <div class="card-title">이번달 꼴등 강사</div>
                <div class="card-number">박패배 강사</div>
                <div class="card-sub">매출 ₩0 (방출 후보)</div>
            </div>
        </div>
    -->
    <div class="dashboard-card loser">
        <div class="card-icon">💀</div>
        <div class="card-info">
            <div class="card-title">이번달 꼴등 강사</div>
            <div class="card-number">박패배 강사</div>
            <div class="card-sub">매출 ₩0 (방출 후보)</div>
        </div>
    </div>

    <div class="status-card" data-type="totalMember">
        <div class="status-icon">👥</div>
        <div class="status-info">
            <div class="status-title">총 회원 수</div>
            <div class="status-value">0명</div>
        </div>
    </div>

    <div class="status-card" data-type="lecture">
        <div class="status-icon">🎓</div>
        <div class="status-info">
            <div class="status-title">전체 강의 수</div>
            <div class="status-value">0개</div>
        </div>
    </div>

    <div class="status-card" data-type="activeMember">
        <div class="status-icon">💡</div>
        <div class="status-info">
            <div class="status-title">활성 사용자 수</div>
            <div class="status-value">-명</div>
            <div class="status-sub">(최근 7일 로그인)</div>
        </div>
    </div>

    <div class="status-card system">
        <div class="status-icon">🖥️</div>
        <div class="status-info">
            <div class="status-title">시스템 상태</div>
            <div class="status-value">✅ 정상 작동</div>
        </div>
    </div>
</div>

<!-- admin_statistics.js -->
<script>
    document.addEventListener("DOMContentLoaded", function () {

        // [1] 총 회원 수 가져오기
        fetch("/admin/api/home/countAllMembers")
            .then(function (res) {
                return res.json();
            })
            .then(function (json) {
                if (!json.success) {
                    console.error("회원 수 조회 실패:", json ? json.message : "");
                    return;
                }
                const count = json.data || 0;
                renderStatusValue(".status-card", "totalMember", count.toLocaleString() + "명");
            })
            .catch(function (err) {
                console.error("전체 회원 수 조회 실패:", err);
            });

        // [2] 총 강의 수 가져오기
        fetch("/admin/api/home/countAllLectures")
            .then(function (res) {
                return res.json();
            })
            .then(function (json) {
                if (!json.success) {
                    console.error("총 강의 수 조회 실패:", json ? json.message : "");
                    return;
                }
                const count = json.data || 0;
                renderStatusValue(".status-card", "lecture", count.toLocaleString() + "개");
            })
            .catch(function (err) {
                console.error("전체 강의 수 조회 실패:", err);
            });

        // [3] 오늘 신규 회원 수 가져오기
        fetch("/admin/api/home/countNewMembers")
            .then(function (res) {
                return res.json();
            })
            .then(function (json) {
                if (!json.success) {
                    console.error("오늘 신규 회원 수 조회 실패:", json ? json.message : "");
                    return;
                }
                const count = json.data || 0;
                renderCardNumber(".dashboard-card", "newMember", count.toLocaleString() + "명");
            })
            .catch(function (err) {
                console.error("오늘 신규 회원 수 조회 실패:", err);
            });

        // [4] 활성 사용자 수 가져오기
        fetch("/admin/api/home/activeMembers")
            .then(function (res) {
                return res.json();
            })
            .then(function (json) {
                const count = json.data || 0;
                renderStatusValue(".status-card", "activeMember", count.toLocaleString() + "명");
            })
            .catch(function (err) {
                console.error("활성 사용자 수 조회 실패:", err);
            });

        // [5] 이번 달 매출 가져오기
        fetch("/admin/api/home/totalSales")
            .then(function (res) {
                return res.json();
            })
            .then(function (json) {
                if (!json.success) {
                    console.error("이번달 총 매출액 수 조회 실패:", json ? json.message : "");
                    return;
                }
                const count = Number(json.data || 0);
                renderCardNumber(".dashboard-card.sales", "totalSales", "₩" + count.toLocaleString());
            })
            .catch(function (err) {
                console.error("이번달 총 매출액 수 조회 실패:", err);
            });

        // [6] TOP 선생님
        fetch("/admin/api/home/topTeacher")
            .then(function (res) {
                return res.json();
            })
            .then(function (json) {
                if (!json.success) {
                    console.error("이번달 MVP 강사 조회 실패:", json ? json.message : "");
                    return;
                }

                const teacher = JSON.parse(json.data);
                if (!teacher) {
                    renderCardNumber(".dashboard-card", "topTeacher", "데이터 없음");
                    return;
                }

                const name = teacher.nickname || "(이름 없음)";
                const sales = teacher.totalSales || 0;
                const month = new Date().getMonth() + 1;

                renderCardNumber(".dashboard-card", "topTeacher", name + " 강사");

                const card = document.querySelector('.dashboard-card[data-type="topTeacher"]');
                const sub = card ? card.querySelector(".card-sub") : null;
                if (sub) {
                    sub.textContent = "매출 ₩" + Number(sales).toLocaleString() + " (" + month + "월 기준)";
                }
            })
            .catch(function (err) {
                console.error("이번달 MVP 강사 조회 실패:", err);
            });

        /** [★] 렌더링 함수 정의 (data-type으로 선택)
         */

        // [★-1] status-value용
        function renderStatusValue(className, type, text) {
            const card = document.querySelector(className + '[data-type="' + type + '"]');
            if (!card) {
                console.error(type + " StatusValue 카드 요소를 찾을 수 없습니다.");
                return;
            }

            const valueElement = card.querySelector(".status-value");
            if (!valueElement) {
                console.error(type + "의 .status-value 요소를 찾을 수 없습니다.");
                return;
            }

            valueElement.textContent = text;
        }

        // [★-2] dashboard-card-number용
        function renderCardNumber(className, type, text) {
            const card = document.querySelector(className + '[data-type="' + type + '"]');
            if (!card) {
                console.error(type + " CardNumber 카드 요소를 찾을 수 없습니다.");
                return;
            }

            const valueElement = card.querySelector(".card-number");
            if (!valueElement) {
                console.error(type + "의 .card-number 요소를 찾을 수 없습니다.");
                return;
            }

            valueElement.textContent = text;
        }
    });
</script>
