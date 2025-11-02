<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

    // 에디터 정보
    let editorId;

    // ✏️ 수정 버튼 추가 함수
    function addEditButtons() {

        const editableFields = [
            { id: 'lecture-title', label: '강의 제목', type: 'text', field: 'title', url: `/teacher/api/lectures/${lectureId}/title` },
            { id: 'lecture-summary', label: '강의 소개', type: 'textarea', field: 'summary', url: `/teacher/api/lectures/${lectureId}/summary` },
            { id: 'lecture-description', label: '강의 상세 소개', type: 'editor', field: 'description', url: `/teacher/api/lectures/${lectureId}/description` },
            { id: 'lecture-lectureTarget', label: '강의 대상', type: 'select', field: 'lectureTarget', options: TARGET_MAP, url: `/teacher/api/lectures/${lectureId}/lecture-target` },
            { id: 'lecture-subjectDetail', label: '강의 상세 과목', type: 'select', field: 'subjectDetail', options: SUBJECT_DETAIL_MAP, url: `/teacher/api/lectures/${lectureId}/subject-detail` },
            { id: 'lecture-difficulty', label: '난이도', type: 'select', field: 'difficulty', options: DIFFICULTY_MAP, url: `/teacher/api/lectures/${lectureId}/difficulty` },
            { id: 'lecture-price', label: '판매 가격', type: 'number', field: 'price', url: `/teacher/api/lectures/${lectureId}/price` }
        ];

        editableFields.forEach(fieldInfo => {
            const section = document.getElementById(fieldInfo.id).closest('.view-section');
            const label = section.querySelector('.view-label');

            // 수정 버튼 생성
            const editBtn = document.createElement('button');
            editBtn.innerHTML = '✏️';
            editBtn.classList.add('edit-icon-btn');
            editBtn.style.marginLeft = '10px';
            editBtn.style.border = 'none';
            editBtn.style.background = 'transparent';
            editBtn.style.cursor = 'pointer';
            editBtn.style.fontSize = '18px';

            editBtn.addEventListener('click', () => openEditModal(fieldInfo));
            label.appendChild(editBtn);
        });
    }

    // 모달 생성 및 열기
    async function openEditModal(fieldInfo) {
        // 기존 모달이 있으면 제거
        const existingModal = document.querySelector('.edit-modal-overlay');
        if (existingModal) existingModal.remove();

        // 현재 값 가져오기
        const currentElement = document.getElementById(fieldInfo.id);
        let currentValue = '';

        // 수정하는 값 타입에 따라 구분
        if (fieldInfo.type === 'editor') {
            currentValue = currentElement.innerHTML;
        } else if (fieldInfo.type === 'select') {
            currentValue = currentElement.innerText;
        } else if (fieldInfo.type === 'number') {
            currentValue = currentElement.innerText.replace('₩', '').replace(/,/g, '').trim();
        } else {
            currentValue = currentElement.innerText;
        }

        // 에디터의 경우, 서버에서 아이디 값을 얻음 (캐시 생성)
        if (fieldInfo.type === 'editor') {
            try {
                const res = await fetch('/teacher/api/lectures/cache', {
                    headers: {'X-Requested-From': window.location.pathname + window.location.search},
                    method: "POST",
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
                        alert(rp.message || "접근 권한이 없습니다.");
                        return;
                    }

                    // 기타 예기치 않은 오류가 발생한 경우
                    alert(rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.");
                    return;
                }
                // 성공 시, 캐시 아이디 얻어옴
                editorId = rp.data;

                // 기존 textarea 내용 초기화 (이전 캐시 내용 제거)
                document.getElementById("content").value = "";

            } catch (err) {
                console.error('에디터 세션 생성 실패:', err);
                alert('에디터를 불러올 수 없습니다.');
                return;
            }
        }




        // 모달 HTML 생성
        const modalHTML = `
            <div class="edit-modal-overlay">
                <div class="edit-modal-content \${fieldInfo.type === 'editor' ? 'editor-modal' : ''}">
                    <div class="edit-modal-header">
                        <h3>\${fieldInfo.label} 수정</h3>
                        <button class="modal-close-btn">✕</button>
                    </div>
                    <div class="edit-modal-body">
                        \${generateInputField(fieldInfo, currentValue)}
                        <div class="text-error-center" id="\${fieldInfo}Error"></div>
                    </div>
                    <div class="edit-modal-footer">
                        <button class="modal-cancel-btn">취소</button>
                        <button class="modal-save-btn">저장</button>
                    </div>
                </div>
            </div>
        `;

        document.body.insertAdjacentHTML('beforeend', modalHTML);

        const modal = document.querySelector('.edit-modal-overlay');
        const errorText = modal.querySelector('.text-error-center');

        // 닫기 이벤트
        modal.querySelector('.modal-close-btn').addEventListener('click', () => closeModal(modal));
        modal.querySelector('.modal-cancel-btn').addEventListener('click', () => closeModal(modal));
        modal.addEventListener('click', (e) => {
            if (e.target === modal) closeModal(modal);
        });

        // 저장 이벤트
        modal.querySelector('.modal-save-btn').addEventListener('click', async () => {

            // 폼 데이터 생성
            const formData = new FormData();
            const target = `\${fieldInfo.field}`;
            const value = getInputValue(fieldInfo, modal);
            formData.append(target, value);
            if (editorId) formData.append("editorId", editorId);

            try {
                // 서버 요청 (실제 API 엔드포인트에 맞게 수정 필요)
                const res = await fetch(fieldInfo.url, {
                    headers: {"X-Requested-From": window.location.pathname + window.location.search},
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

                    // 권한이 부족한 경우
                    if (rp.statusCode === 403) {
                        alert(rp.message || "접근 권한이 없습니다.");
                        return;
                    }

                    // 유효성검사, 혹은 기타 오류 발생 시 출력
                    errorText.textContent = rp.inputErrors[target] || rp.message || "서버 오류가 발생했습니다. 잠시 후에 시도해 주세요.";
                    return;
                }

                // 성공 시, 현재 모달을 닫고 내용 변경
                alert(rp.message || "정보를 수정했습니다.");
                editValue(fieldInfo, value);
                closeModal(modal);
                editorId = null;

            } catch (err) {
                console.error('수정 요청 실패:', err);
            }
        });
    }

    // 입력 필드 생성
    function generateInputField(fieldInfo, currentValue) {
        if (fieldInfo.type === 'text') {
            return `<input type="text" id="\${fieldInfo.field}" name="\${fieldInfo.field}" class="modal-input" value="\${currentValue}" />`;
        } else if (fieldInfo.type === 'textarea') {
            return `<textarea class="modal-textarea" id="\${fieldInfo.field}" name="\${fieldInfo.field}" rows="5">\${currentValue}</textarea>`;
        } else if (fieldInfo.type === 'editor') {
            return `
                    <textarea class="modal-editor" id="content-\${editorId}" name="\${fieldInfo.field}" rows="10" style="display:none;">\${currentValue}</textarea>
                    <iframe style="width: 100%; height: 853px;" src="<c:url value="/editor?width=1000&height=800&editorId=\${editorId}&fileUploadUrl=/teacher/api/lectures/cache/description-image"/>"></iframe>
                `;
        } else if (fieldInfo.type === 'number') {
            return `<input type="number" id="\${fieldInfo.field}" name="\${fieldInfo.field}" class="modal-input" value="\${currentValue}" />`;
        } else if (fieldInfo.type === 'select') {
            console.log("fieldInfo.options = {}", fieldInfo.options);
            let options = '';
            for (let key in fieldInfo.options) {
                const selected = fieldInfo.options[key] === currentValue ? 'selected' : '';
                options += `<option value="\${key}" \${selected}>\${fieldInfo.options[key]}</option>`;
            }
            return `<select class="modal-select">\${options}</select>`;
        }
        return '';
    }

    // 입력값 가져오기
    function getInputValue(fieldInfo, modal) {
        if (fieldInfo.type === 'select') {
            return modal.querySelector('.modal-select').value;
        } else if (fieldInfo.type === 'number') {
            return modal.querySelector('.modal-input').value;
        } else if (fieldInfo.type === 'textarea') {
            return modal.querySelector('.modal-textarea').value;
        } else if (fieldInfo.type === 'editor') {
            return modal.querySelector('.modal-editor').value;
        } else {
            return modal.querySelector('.modal-input').value;
        }
    }

    // 수정된 값 반영
    function editValue(fieldInfo, value) {
        if (fieldInfo.type === 'editor') {
            document.getElementById(fieldInfo.id).innerHTML = value;
        } else if (fieldInfo.type === 'select') {
            let afterText;
            if (fieldInfo.field === 'lectureTarget') afterText = TARGET_MAP[value];
            else if (fieldInfo.field === 'subjectDetail') afterText = SUBJECT_DETAIL_MAP[value];
            else if (fieldInfo.field === 'difficulty') afterText = DIFFICULTY_MAP[value];
            else afterText = value;
            document.getElementById(fieldInfo.id).textContent = afterText;
        } else if (fieldInfo.type === 'textarea') {
            document.getElementById(fieldInfo.id).innerHTML = value;
        } else if (fieldInfo.type === 'number') {
            document.getElementById(fieldInfo.id).textContent = "₩" + Number(value).toLocaleString();
        } else {
            document.getElementById(fieldInfo.id).textContent = value;
        }
    }

    // 모달 닫기
    function closeModal(modal) {
        modal.remove();
    }

    // 에디터 내용 변동 시 처리 함수
    function onEditorContentChange(content) {
        document.getElementById(`content-\${editorId}`).value = content;
    }
</script>

<style>
    /* 모달 스타일 */
    .edit-modal-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 9999;
    }

    .edit-modal-content {
        background: white;
        border-radius: 12px;
        width: 90%;
        max-width: 600px;
        max-height: 80vh;
        overflow-y: auto;
        box-shadow: 0 4px 20px rgba(0,0,0,0.3);
    }

    .edit-modal-content.editor-modal {
        max-width: 1035px;  /* 더 넓게 */
        max-height: 90vh;   /* 더 높게 */
        width: 100%;
    }

    .edit-modal-content.editor-modal .edit-modal-body {
        padding: 15px;  /* 에디터 여백 줄임 */
    }

    .edit-modal-content.editor-modal iframe {
        width: 100%;
        height: 853px;  /* iframe 높이 조정 */
    }

    .edit-modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px 25px;
        border-bottom: 1px solid #eee;
    }

    .edit-modal-header h3 {
        margin: 0;
        font-size: 20px;
        font-weight: 600;
    }

    .modal-close-btn {
        background: none;
        border: none;
        font-size: 24px;
        cursor: pointer;
        color: #999;
    }

    .edit-modal-body {
        padding: 25px;
    }

    .modal-input, .modal-textarea, .modal-editor, .modal-select {
        width: 100%;
        padding: 12px;
        border: 1px solid #ddd;
        border-radius: 6px;
        font-size: 15px;
        font-family: inherit;
    }

    .modal-textarea, .modal-editor {
        resize: vertical;
    }

    .edit-modal-footer {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        padding: 15px 25px;
        border-top: 1px solid #eee;
    }

    .modal-cancel-btn, .modal-save-btn {
        padding: 10px 20px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        font-weight: 600;
        transition: 0.2s;
    }

    .modal-cancel-btn {
        background: #f1f1f1;
        color: #666;
    }

    .modal-cancel-btn:hover {
        background: #e0e0e0;
    }

    .modal-save-btn {
        background: #4e73df;
        color: white;
    }

    .modal-save-btn:hover {
        background: #3b5cc3;
    }

    .text-error-center {
        text-align: center;
        font-size: 14px;
    }

</style>