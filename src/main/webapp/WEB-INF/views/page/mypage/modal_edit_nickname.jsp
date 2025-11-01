<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="nicknameModal" class="nickname-modal">
    <div class="nickname-modal-content">
        <h2>닉네임 수정</h2>
        <input type="text" id="nicknameInput" name="nickname" placeholder="변경할 닉네임을 입력하세요">
        <div class="text-error" id="nicknameError"></div>
        <div class="modal-buttons">
            <button id="saveNicknameBtn" onclick="editNickname()">저장</button>
            <button id="closeEditNicknameBtn" onclick="closeNicknameModal()">취소</button>
        </div>
    </div>
</div>

<style>
    .nickname-modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.4);
        justify-content: center;
        align-items: center;
    }

    .nickname-modal-content {
        background: white;
        border-radius: 12px;
        padding: 30px 40px;
        text-align: center;
        width: 400px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        animation: fadeIn 0.3s ease;
    }


    .modal-content h2 {
        margin-bottom: 20px;
        font-size: 22px;
        font-weight: 600;
    }

    .modal-content input {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
        font-size: 16px;
    }

    .modal-buttons {
        margin-top: 25px;
        display: flex;
        justify-content: center;
        gap: 10px;
    }

    .modal-buttons button {
        padding: 10px 20px;
        border: none;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
        transition: background 0.2s ease;
    }

    #saveNicknameBtn {
        background-color: #28a745;
        color: white;
    }

    #closeEditNicknameBtn {
        background-color: #ccc;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: scale(0.95); }
        to { opacity: 1; transform: scale(1); }
    }

    #nicknameInput {
        width: 100%;
        padding: 12px 15px;
        margin-top: 10px;
        border: 2px solid #e0e0e0;
        border-radius: 10px;
        font-size: 16px;
        outline: none;
        transition: all 0.2s ease;
        background-color: #fafafa;
    }

    /* 포커스 시 효과 */
    #nicknameInput:focus {
        border-color: #28a745; /* 초록 포인트 */
        background-color: #fff;
        box-shadow: 0 0 5px rgba(40, 167, 69, 0.3);
    }

    /* placeholder 색상 */
    #nicknameInput::placeholder {
        color: #aaa;
        font-size: 14px;
    }


</style>

<script>

    // 닉네임 수정 함수
    async function editNickname() {

        try {

            // REST API 요청
            const nickname = document.getElementById("nicknameInput").value;
            const form = new FormData();
            form.append("nickname", nickname);

            const res = await fetch("/api/members/nickname", {
                headers: {'X-Requested-From': window.location.pathname + window.location.search},
                method: "PATCH",
                body: form
            });

            // JSON 데이터 파싱
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
                    alert(rp.message || "접근 권한이 없습니다.");
                    return;
                }

                // 유효성 검사에 실패한 경우
                if (rp.inputErrors) {
                    document.querySelectorAll(".text-error").forEach((el) => {el.textContent = "";});
                    Object.entries(rp.inputErrors).forEach(([field, message]) => {
                        const errorElem = document.getElementById(`\${field}Error`);
                        if (errorElem) errorElem.textContent = message;
                    });
                    return;
                }

                // 기타 예기치 않은 오류가 발생한 경우
                alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                return;
            }

            // 닉네임 변경
            alert(rp.message || "닉네임을 변경했습니다.")
            const nicknameElem = document.querySelector(".mypage-info-nickname");
            if (nicknameElem) nicknameElem.textContent = nickname || "닉네임";
            closeNicknameModal();

        } catch (error) {
            console.error("닉네임 변경 실패:", error);
        }
    }



    function openNicknameModal() {
        const modal = document.getElementById("nicknameModal");
        if (modal) {
            modal.style.display = "flex"; // 모달 보이기
        }
    }

    function closeNicknameModal() {
        const modal = document.getElementById("nicknameModal");
        if (modal) modal.style.display = "none";
    }


</script>