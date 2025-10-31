<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- ✅ 비밀번호 변경 모달 -->
<div id="passwordEditModal" class="password-modal">
    <div class="password-modal-content">
        <h2>비밀번호 변경</h2>
        <p class="password-edit-desc">새로운 비밀번호를 입력해주세요.</p>

        <form id="passwordEditForm">
            <input type="hidden" id="editToken" name="token" value="${param.token}">
            <input type="hidden" id="email" name="email" value="${param.email}">

            <div class="form-group">
                <label for="password">새 비밀번호</label>
                <input type="password" id="password" name="password" placeholder="새 비밀번호 입력" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">비밀번호 확인</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호 재입력" required>
            </div>

            <div class="text-error" id="passwordEditError"></div>
            <div class="modal-buttons">
                <button type="submit" class="edit-btn">변경하기</button>
                <button type="button" class="close-btn" onclick="closeEditPasswordModal()">닫기</button>
            </div>
        </form>
    </div>
</div>

<!-- ✅ 스타일 -->
<style>
    .password-modal {
        display: none;
        position: fixed;
        z-index: 1000;
        inset: 0;
        background: rgba(0, 0, 0, 0.5);
        justify-content: center;
        align-items: center;
        backdrop-filter: blur(4px);
        animation: modalFadeIn 0.3s ease;
    }

    .password-modal-content {
        background: #fff;
        border-radius: 14px;
        padding: 40px 45px;
        text-align: center;
        width: 380px;
        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
        animation: popIn 0.35s ease;
    }

    .password-modal-content h2 {
        margin-bottom: 8px;
        font-size: 24px;
        font-weight: 700;
        color: #222;
    }

    .password-edit-desc {
        font-size: 14px;
        color: #666;
        margin-bottom: 25px;
    }

    .form-group {
        text-align: left;
        margin-bottom: 18px;
    }

    .form-group label {
        display: block;
        font-size: 14px;
        font-weight: 600;
        margin-bottom: 6px;
        color: #333;
    }

    .form-group input {
        width: 100%;
        padding: 12px 14px;
        border: 1px solid #d1d1d1;
        border-radius: 8px;
        font-size: 15px;
        outline: none;
        transition: 0.2s;
    }

    .form-group input:focus {
        border-color: #007bff;
        box-shadow: 0 0 4px rgba(0, 123, 255, 0.2);
    }

    .modal-buttons {
        display: flex;
        justify-content: center;
        gap: 12px;
        margin-top: 25px;
    }

    .modal-buttons button {
        padding: 12px 0;
        width: 48%;
        border-radius: 8px;
        font-size: 15px;
        font-weight: 600;
        cursor: pointer;
        border: none;
        transition: all 0.25s ease;
    }

    .edit-btn {
        background: linear-gradient(135deg, #007bff, #3399ff);
        color: white;
    }

    .edit-btn:hover {
        background: linear-gradient(135deg, #0056d6, #1a83ff);
    }

    .close-btn {
        background: #f0f0f0;
        color: #333;
    }

    .close-btn:hover {
        background: #e0e0e0;
    }

    /* ✅ 애니메이션 */
    @keyframes modalFadeIn {
        from { opacity: 0; }
        to { opacity: 1; }
    }

    @keyframes popIn {
        from { opacity: 0; transform: scale(0.9); }
        to { opacity: 1; transform: scale(1); }
    }
</style>


<script>

    const form = document.getElementById("passwordEditForm");
    const errorElem = document.getElementById("passwordEditError");

    form.addEventListener("submit", async(e) => {

        try {
            e.preventDefault();

            // 요소 값 추출
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;

            // 클라이언트 유효성 검사
            if (password !== confirmPassword) {
                passwordEditError.textContent = "비밀번호 확인과 일치하지 않습니다.";
                return;
            }

            // REST API 요청
            const res = await fetch("/api/members/password", {
                headers: {'X-Requested-From': window.location.pathname + window.location.search},
                method: "PATCH",
                body: new FormData(form)
            });

            // 서버 JSON 응답 문자열 파싱
            const rp = await res.json();
            console.log("서버 응답:", rp);

            // 요청 실패 처리
            if (!res.ok || !rp.success) {

                // 로그인이 필요한 경우
                if (rp.statusCode === 401) {

                    // 로그인 필요 안내 전달
                    if (confirm(rp.message || "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?")) {
                        window.location.href = rp.redirect || "/login";
                    }

                    // 로직 중단
                    return;
                }

                // 권한이 부족한 경우
                if (rp.statusCode === 403) {
                    errorElem.textContent = (rp.message || "접근 권한이 없습니다.");
                    return;
                }

                // 그 외 경우 처리
                errorElem.textContent = rp.inputErrors.password || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.";
                return;
            }

            // 요청 성공 처리
            alert(rp.message || "비밀번호를 변경했습니다.");
            closeEditPasswordModal();


        } catch (error) {
            console.error("비밀번호 변경 실패:", error);
        }

    });


    function openEditPasswordModal() {
        const modal = document.getElementById("passwordEditModal");
        if (modal) modal.style.display = "flex";
    }

    function closeEditPasswordModal() {
        const modal = document.getElementById("passwordEditModal");
        modal.querySelectorAll('input').forEach(input => input.value = '');
        modal.querySelectorAll('textarea').forEach(textarea => textarea.value = '');
        modal.querySelectorAll('.text-error').forEach(textError => textError.textContent = '');
        if (modal) modal.style.display = "none";
    }

</script>