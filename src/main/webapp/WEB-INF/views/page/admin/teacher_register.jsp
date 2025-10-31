<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/teacher_register.css'/>">


<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="teacher"/>
</jsp:include>

<div class="admin-content-container">
    <form action="<c:url value='/admin/api/members/teacher/register'/>" method="post" class="teacher-create-form">
        <div class="form-section">
            <h3>강사 기본 정보</h3>

            <div class="form-row">
                <label for="email">이메일</label>
                <input type="email" id="email" name="email" required placeholder="example@studyon.com">
                <small class="hint">30자 이내 이메일 형식 입력</small>
            </div>

            <div class="form-row">
                <label for="nickname">이름 (닉네임)</label>
                <input type="text" id="nickname" name="nickname" required placeholder="강사 이름 입력">
                <small class="hint">닉네임 또는 이름 입력</small>
            </div>

            <div class="form-row">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" required placeholder="등록될 비밀번호">
                <small class="hint">6자 이상 20자 이하 영문/특수문자/숫자 입력</small>
            </div>
            <div class="form-row">
                <label for="password">비밀번호 확인</label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" required placeholder="비밀번호 확인">
                <small class="hint">비밀번호 다시 입력</small>
            </div>
            <div class="form-row">
                <label for="subject">담당 과목</label>
                <select id="subject" name="subject" required>
                    <option value="">선택</option>
                    <option value="KOREAN">국어</option>
                    <option value="ENGLISH">영어</option>
                    <option value="MATH">수학</option>
                    <option value="SOCIAL">사회탐구</option>
                    <option value="SCIENCE">과학탐구</option>
                </select>
            </div>

            <!--
            <div class="form-row">
                <label for="description">강사 소개</label>
                <textarea id="description" name="description" rows="5" placeholder="강의 철학, 커리큘럼 등을 간단히 작성하세요."></textarea>
            </div>
            -->
        </div>

        <div class="form-buttons">
            <button type="button" id="createTeacherBtn" class="create-teacher-btn">등록하기</button>
            <button type="reset" class="btn-reset" onclick="history.back()">뒤로가기</button>
        </div>
    </form>
</div>

<!-- teacher_register.js -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        console.log("[INIT] 강사 등록 페이지 로드 완료");

        const form = document.querySelector(".teacher-create-form");
        const btn = document.getElementById("createTeacherBtn");
        if (!form || !btn) return;

        btn.addEventListener("click", async function(e) {
            e.preventDefault();

            const email = form.email.value.trim();
            const nickname = form.nickname.value.trim();
            const password = form.password.value.trim();
            const passwordConfirm = form.passwordConfirm.value.trim();
            const subject = form.subject.value;

            if (!passwordConfirm || passwordConfirm.length === 0) {
                alert("⚠️ 비밀번호를 한번 더 입력해주세요.");
                return;
            }

            if (password !== passwordConfirm) {
                alert("⚠️ 비밀번호가 일치하지 않습니다.");
                return;
            }

            // ✅ [1] 유효성 검사
            if (!email || !nickname || !password || !subject) {
                alert("⚠️ 필수 항목을 모두 입력해주세요.");
                return;
            }

            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                alert("⚠️ 올바른 이메일 형식이 아닙니다.");
                return;
            }

            if (password.length < 4) {
                alert("⚠️ 비밀번호는 4자리 이상이어야 합니다.");
                return;
            }

            // ✅ [2] 이메일 중복검사
            try {
                const duplicateCheck = await fetch("/admin/api/members/check-email?email=" + encodeURIComponent(email));
                const jsonCheck = await duplicateCheck.json();

                if (jsonCheck.exists) {
                    alert("⚠️ 이미 등록된 이메일입니다.");
                    return;
                }
            } catch (err) {
                console.error("[ERROR] 이메일 중복검사 실패:", err);
                alert("⚠️ 이메일 중복검사 중 오류가 발생했습니다.");
                return;
            }

            // ✅ [3] 서버 전송
            const formData = new FormData(form);

            try {
                const res = await fetch("/admin/api/members/teacher/register", {
                    method: "POST",
                    body: formData
                });

                const json = await res.json();
                console.log("[응답 JSON]", json);

                if (json.success) {
                    alert("✅ 강사 등록이 완료되었습니다!");
                    window.location.href = "/admin/teacher_management";
                } else {
                    alert("❌ 등록 실패: " + (json.message || "서버 오류"));
                }
            } catch (err) {
                console.error("[등록 실패]", err);
                alert("⚠️ 등록 중 오류가 발생했습니다:\n" + err.message);
            }
        });
    });
</script>
