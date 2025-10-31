<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div id="titleModal" class="title-modal">
    <div class="title-modal-content">
        <h2>공지사항 제목 수정</h2>
        <input type="text" id="titleInput" name="title" placeholder="변경할 제목을 입력하세요">
        <div class="text-error" id="titleError"></div>
        <div class="modal-buttons">
            <button id="saveTitleBtn" onclick="saveNoticeTitle()">저장</button>
            <button id="closeEditTitleBtn" onclick="closeTitleModal()">취소</button>
        </div>
    </div>
</div>

<style>
    /* 제목 수정 모달 */
    .title-modal {
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

    .title-modal-content {
        background: white;
        border-radius: 12px;
        padding: 30px 40px;
        text-align: center;
        width: 400px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        animation: fadeIn 0.3s ease;
    }

    .title-modal-content h2 {
        margin-bottom: 20px;
        font-size: 22px;
        font-weight: 600;
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

    #saveTitleBtn {
        background-color: #28a745;
        color: white;
    }

    #saveTitleBtn:hover {
        background-color: #218838;
    }

    #closeEditTitleBtn {
        background-color: #ccc;
    }

    #closeEditTitleBtn:hover {
        background-color: #bbb;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: scale(0.95); }
        to { opacity: 1; transform: scale(1); }
    }

    #titleInput {
        width: 100%;
        padding: 12px 15px;
        margin-top: 10px;
        border: 2px solid #e0e0e0;
        border-radius: 10px;
        font-size: 16px;
        outline: none;
        transition: all 0.2s ease;
        background-color: #fafafa;
        box-sizing: border-box;
    }

    #titleInput:focus {
        border-color: #28a745;
        background-color: #fff;
        box-shadow: 0 0 5px rgba(40, 167, 69, 0.3);
    }

    #titleInput::placeholder {
        color: #aaa;
        font-size: 14px;
    }

</style>

<script>
    // 현재 편집 중인 카드 인덱스 저장 (1~6)
    let currentEditingCardIndex = null;

    // 공지사항 제목 수정 함수 - 모달 열기
    function editBannerTitle(cardIndex) {
        currentEditingCardIndex = cardIndex;
        const card = document.querySelector(`.banner-card[data-card-idx="\${cardIndex}"]`);

        // 모달 입력창에 현재 제목 설정
        document.getElementById('titleInput').value = '';
        document.getElementById('titleError').textContent = '';

        // 모달 열기
        openTitleModal();
    }

    // 제목 모달 열기
    function openTitleModal() {
        const modal = document.getElementById("titleModal");
        if (modal) {
            modal.style.display = "flex";
        }
    }

    // 제목 모달 닫기
    function closeTitleModal() {
        const modal = document.getElementById("titleModal");
        if (modal) {
            modal.style.display = "none";
        }
        currentEditingCardIndex = null;
    }

    // 제목 저장 함수
    async function saveNoticeTitle() {
        if (!currentEditingCardIndex) {
            alert('오류: 카드 정보를 찾을 수 없습니다.');
            return;
        }

        const newTitle = document.getElementById('titleInput').value.trim();

        // 유효성 검사
        if (!newTitle) {
            document.getElementById('titleError').textContent = '제목을 입력해주세요.';
            return;
        }

        if (newTitle.length < 2) {
            document.getElementById('titleError').textContent = '제목은 최소 2자 이상이어야 합니다.';
            return;
        }

        if (newTitle.length > 50) {
            document.getElementById('titleError').textContent = '제목은 최대 50자까지 입력 가능합니다.';
            return;
        }

        try {
            const formData = new FormData();
            formData.append('title', newTitle);

            const res = await fetch(`/admin/api/banners/\${currentEditingCardIndex}/title`, {
                method: 'PATCH',
                body: formData
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

                // 로그인을 제외한 실패 응답
                document.getElementById('titleError').textContent =
                    rp.inputErrors.title || rp.message || '서버 오류가 발생했습니다. 다시 시도해 주세요.';

                return;
            }

            const card = document.querySelector(`.banner-card[data-card-idx="\${currentEditingCardIndex}"]`);
            const titleElement = card.querySelector('.banner-title');
            titleElement.textContent = newTitle;
            alert('제목이 성공적으로 변경되었습니다.');
            closeTitleModal();


        } catch (error) {
            console.error('제목 변경 오류:', error);
            document.getElementById('titleError').textContent = '제목 변경 중 오류가 발생했습니다.';
        }
    }
</script>
