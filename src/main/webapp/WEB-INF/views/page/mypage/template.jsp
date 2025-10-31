<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<style>
</style>

<div>
    <div id="mypage-container">
        <aside id="aside">
            <nav class="sidebar">
                <p class="sidebar-title">Home</p>
                <ui>
                    <a href="/mypage/account">
                        <li class="sidebar-text">
                            <input type="radio" name="mypage" value="계정 정보" id="account" checked>
                            <span>계정 정보</span>
                        </li>
                    </a>
                </ui>
                <p class="sidebar-title">학습 관리</p>
                <ui>
                    <a href="lecture_management">
                        <li class="sidebar-text">
                            <input type="radio" name="mypage" value="강의 관리" id="courses">
                            <span>강의 관리</span>
                        </li>
                    </a>
                </ui>
                <p class="sidebar-title">장바구니</p>
                <ui>
                    <a href="likes">
                        <li class="sidebar-text">
                            <input type="radio" name="mypage" value="관심 목록" id="likes">
                            <span>관심 목록</span>
                        </li>
                    </a>
                    <a href="payments">
                        <li class="sidebar-text">
                            <input type="radio" name="mypage" value="구매 내역" id="payments">
                            <span>구매 내역</span>
                        </li>
                    </a>
                    <a href="../usersupport/startchat">
                        <li class="sidebar-text">
                            <input type="radio" name="mypage" value="구매 내역" id="support">
                            <span>고객 상담</span>
                        </li>
                    </a>
                </ui>
            </nav>
        </aside>
        <section class="mypage-section">
            <jsp:include page="${bodyPage}" />
        </section>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const path = window.location.pathname;

        // 현재 경로를 input id와 매핑
        const map = {
            "/mypage/account": "account",
            "/mypage/lecture_management": "courses",
            "/mypage/likes": "likes",
            "/mypage/payments": "payments"
        };

        // 현재 경로와 일치하는 input에 checked 적용
        const activeId = map[path];
        if (activeId) {
            const input = document.getElementById(activeId);
            if (input) input.checked = true;
        }
    });
</script>

<style>
    .mypage-section{
        width:100%;
    }
</style>