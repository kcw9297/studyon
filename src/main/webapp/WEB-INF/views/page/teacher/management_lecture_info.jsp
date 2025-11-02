<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/WEB-INF/views/page/teacher/navbar.jsp" />
<div class="lecture-view-wrapper">
    <div class="view-content">
        <div class="view-title">강의 상세 보기</div>
        <div class="lecture-status-container">
            <label class="lecture-status-onsale">판매중</label>
            <label class="lecture-status-status">승인대기중</label>
        </div>

        <div class="view-section">
            <label class="view-label">강의 제목</label>
            <div id="lecture-title" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">강의 소개</label>
            <div id="lecture-summary" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">강의 상세 소개</label>
            <div id="lecture-description" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">강의 대상</label>
            <div id="lecture-lectureTarget" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">강의 상세 과목</label>
            <div id="lecture-subjectDetail" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">난이도</label>
            <div id="lecture-difficulty" class="view-value"></div>
        </div>

        <div class="view-section">
            <label class="view-label">판매 가격</label>
            <div id="lecture-price" class="view-value"></div>
        </div>

        <div class="view-section">

            <label class="view-label">썸네일 이미지</label>

            <div id="lecture-thumbnail" class="lecture-thumbnail">
                썸네일을 등록해주세요 📷
            </div>
            <input type="file" id="thumbnail-upload" accept="image/*" style="display:none;">
        </div>


        <div class="view-section">
            <label class="view-label">강의 목차</label>
            <div id="lecture-list-box"></div>
        </div>
    </div>
    <div class="lecture-register-button">
        강의승인요청
    </div>
</div>
<%--
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/teacher/management_lecture_view.css'/>">
--%>


<script>

    const SUBJECT_DETAIL_MAP = {
        <c:forEach var="subjectDetail" items="${subjectDetails}" varStatus="status">
        "${subjectDetail}": "${subjectDetail.name}"${!status.last ? ',' : ''}
        </c:forEach>
    };

    const DIFFICULTY_MAP = {
        <c:forEach var="difficulty" items="${difficulties}" varStatus="status">
        "${difficulty}": "${difficulty.value}"${!status.last ? ',' : ''}
        </c:forEach>
    };

    const TARGET_MAP = {
        <c:forEach var="target" items="${targets}" varStatus="status">
        "${target}": "${target.value}"${!status.last ? ',' : ''}
        </c:forEach>
    };

    const ON_SALE_MAP = {
        true: "${onSales[0].value}",   // ON_SALE의 value
        false: "${onSales[1].value}"   // NOT_SALE의 value
    };

    const STATUS_MAP = {
        <c:forEach var="st" items="${statuses}" varStatus="status">
        "${st}": "${st.value}"${!status.last ? ',' : ''}
        </c:forEach>
    };


    document.addEventListener("DOMContentLoaded", async () => {

        const path = window.location.pathname;
        const lectureId = path.split("/").pop();
        const thumbImg = document.getElementById("lecture-thumbnail");
        const fileInput = document.getElementById("thumbnail-upload");
        const thumbBox = document.getElementById("lecture-thumbnail");
        const listBox = document.getElementById("lecture-list-box");
        console.log(lectureId);

        // 썸네일 변경 이벤트 감지
        thumbImg.addEventListener("click", () => {
            fileInput.click();
        });

        //썸네일 파일 변경 이벤트 감지
        fileInput.addEventListener("change", async (e) => {
            const file = e.target.files[0];
            if (!file) return;

            const form = new FormData();
            form.append("file", file);

            try {
                const res = await fetch("/api/teachers/management/lecture/"+lectureId+"/thumbnail", {
                    method: "PATCH",
                    body: form
                });
                const json = await res.json();
                if (json.status === "OK") {
                    thumbImg.src = URL.createObjectURL(file);
                } else {
                    alert(json.message || "업로드 실패");
                }
                thumbBox.innerHTML =
                    '<img src="/api/teachers/management/lecture/' + lectureId + '/thumbnail/view?ts=' + Date.now() + '"' +
                    ' alt="강의 썸네일"' +
                    ' style="width:100%; height:100%; border-radius:10px; object-fit:cover;">';


            } catch (err) {
                console.error("썸네일 업로드 실패:", err);
            }
        });

        //등록된 강좌를 브라우저에 렌더링
        try {
            const response = await fetch("/api/teachers/management/lectureinfo/" + lectureId);
            const json = await response.json();
            const lecture = json.data;

            //렌더링시 승인 요청/등록된 강좌는 버튼 X
            if (lecture.lectureRegisterStatus === "PENDING" || lecture.lectureRegisterStatus === "REGISTERED") {
                const registerBtn = document.querySelector(".lecture-register-button");
                if (registerBtn) {
                    registerBtn.style.display = "none"; // 버튼 숨기기
                }
            }

            console.log("lecture 정보 = ", lecture);

            // 강의 기본 정보 렌더링
            document.getElementById("lecture-title").innerText = lecture.title;
            document.getElementById("lecture-summary").innerText = lecture.summary;
            document.getElementById("lecture-description").innerHTML = lecture.description;
            document.getElementById("lecture-lectureTarget").innerText = TARGET_MAP[lecture.target];
            document.getElementById("lecture-subjectDetail").innerText = SUBJECT_DETAIL_MAP[lecture.subjectDetail];
            document.getElementById("lecture-difficulty").innerText = DIFFICULTY_MAP[lecture.difficulty];
            document.getElementById("lecture-price").innerText = "₩" + lecture.price.toLocaleString();

            // 강의상태 TAG
            // 상태 라벨 엘리먼트
            const saleLabel = document.querySelector(".lecture-status-onsale");
            const statusLabel = document.querySelector(".lecture-status-status");

            // 판매 상태
            if (lecture.onSales === true) {
                saleLabel.textContent = "판매중";
                saleLabel.style.backgroundColor = "#4CAF50"; // 초록색
                saleLabel.style.color = "#fff";
            } else {
                saleLabel.textContent = "미판매중";
                saleLabel.style.backgroundColor = "#9E9E9E"; // 회색
                saleLabel.style.color = "#fff";
            }

            // 등록 상태
            switch (lecture.lectureRegisterStatus) {
                case "UNREGISTERED":
                    statusLabel.textContent = "미등록";
                    statusLabel.style.backgroundColor = "#9E9E9E";
                    break;
                case "PENDING":
                    statusLabel.textContent = "승인대기중";
                    statusLabel.style.backgroundColor = "#FFC107"; // 노랑
                    break;
                case "REGISTERED":
                    statusLabel.textContent = "등록";
                    statusLabel.style.backgroundColor = "#4CAF50"; // 초록
                    break;
                default:
                    statusLabel.textContent = "알 수 없음";
                    statusLabel.style.backgroundColor = "#BDBDBD";
                    break;
            }
            // 썸네일 이미지
            const imageElement = document.getElementById("lecture-thumbnail");
            if (lecture.thumbnailImagePath)
                imageElement.innerHTML = `<img src="${fileDomain}/\${lecture.thumbnailImagePath}" alt="강의 썸네일" style="width:100%; height:100%; border-radius:10px; object-fit:cover;">`;
            else imageElement.textContent = "썸네일을 등록해주세요 📷";


            // 강의 목차 불러오기
            const res = await fetch("/api/teachers/management/lectureindex/" + lectureId);
            const jsondata = await res.json();
            const indexList = jsondata.data || [];

            listBox.innerHTML = "";

            console.log("indexList = ", indexList);

            if (indexList.length === 0) {
                listBox.innerHTML = "<div>등록된 목차가 없습니다.</div>";
            } else {
                indexList.forEach(function(item, idx) {
                    const div = document.createElement("div");
                    div.classList.add("lecture-item");
                    div.setAttribute("draggable", "true");
                    div.dataset.id = item.lectureIndexId;
                    const videoFileName = item.videoFileName || "강의 영상이 없습니다";


                    div.innerHTML =
                        '<div class="lecture-index">' + (item.indexNumber || (idx + 1)) + '강</div>' +
                        '<div class="lecture-info">' +
                        '<div class="lecture-title">' + item.indexTitle + '</div>' +
                        '</div>' +
                        '<div class="lecture-video-title">' + videoFileName + '</div>' +
                        '<div class="lecture-actions">' +
                        '   <button class="upload-btn">📹 업로드</button>' +
                        '   <button class="delete-btn">✕</button>' +
                        '</div>' +
                        // 영상 리스트 컨테이너 추가
                        '<div id="video-list-' + item.lectureIndexId + '" class="video-list-container"></div>';

                    listBox.appendChild(div);
                });
            }

            /* [1] X버튼 삭제 이벤트 */
            listBox.addEventListener("click", async function(e) {
                if (e.target.classList.contains("delete-btn")) {
                    const item = e.target.closest(".lecture-item");
                    const indexId = item.dataset.id;

                    if (confirm("이 목차를 삭제하시겠습니까?")) {
                        try {
                            const res = await fetch("/api/teachers/management/lectureIndex/" + indexId, {
                                method: "DELETE"
                            });
                            const json = await res.json();

                            if (json) {
                                item.remove();

                                const reordered = Array.from(listBox.querySelectorAll(".lecture-item")).map((item, i) => {
                                    const title = item.querySelector(".lecture-title").innerText;
                                    return {
                                        lectureIndexId: Number(item.dataset.id),
                                        indexNumber: i + 1,
                                        indexTitle: title
                                    };
                                });

                                console.log("📡 삭제 후 자동 재정렬:", reordered);

                                const res2 = await fetch("/api/teachers/management/lectureindex/" + lectureId, {
                                    method: "PUT",
                                    headers: { "Content-Type": "application/json" },
                                    body: JSON.stringify(reordered)
                                });

                                const json2 = await res2.json();
                                console.log("update로직 실행됨", json2);

                                if (json2.success === true || json2.statusCode === 200) {
                                    console.log("삭제 후 순서 자동 업데이트 완료");
                                    //location.reload();
                                } else {
                                    console.warn("⚠ 삭제 후 순서 업데이트 실패:", json2.message);
                                }

                            } else {
                                alert(json.message || "삭제 실패");
                                console.warn("⚠ DELETE 응답 실패:", json);
                            }
                        } catch (err) {
                            console.error("🚨 삭제 실패:", err);
                        }
                    }
                }


                if (e.target.classList.contains("upload-btn")) {
                    const item = e.target.closest(".lecture-item");
                    const indexId = item.dataset.id;

                    const fileInput = document.createElement("input");
                    fileInput.type = "file";
                    fileInput.accept = "video/*";
                    fileInput.click();

                    fileInput.addEventListener("change", async (ev) => {
                        const file = ev.target.files[0];
                        if (!file) return;

                        const form = new FormData();
                        form.append("file", file);

                        try {
                            const res = await fetch("/api/teachers/management/lectureindex/" + indexId + "/video", {
                                method: "POST",
                                body: form
                            });
                            const json = await res.json();

                            if (json.success === true || json.statusCode === 200 || json.status === "OK") {
                                alert("🎬 동영상 업로드 완료!");
                                location.reload();
                            } else {
                                alert(json.message || "업로드 실패");
                            }
                        } catch (err) {
                            console.error("🚨 동영상 업로드 실패:", err);
                        }
                    });
                }
            });
            /*  [2] 드래그 앤 드롭 */
            let draggedItem = null;

            listBox.addEventListener("dragstart", function(e) {
                if (e.target.classList.contains("lecture-item")) {
                    draggedItem = e.target;
                    e.target.style.opacity = "0.5";
                }
            });

            listBox.addEventListener("dragend", function(e) {
                e.target.style.opacity = "1";
            });

            listBox.addEventListener("dragover", function(e) {
                e.preventDefault();
                const afterElement = getDragAfterElement(listBox, e.clientY);
                if (afterElement == null) {
                    listBox.appendChild(draggedItem);
                } else {
                    listBox.insertBefore(draggedItem, afterElement);
                }
            });

            function getDragAfterElement(container, y) {
                const draggableElements = [].slice.call(container.querySelectorAll(".lecture-item:not(.dragging)"));
                return draggableElements.reduce(function(closest, child) {
                    const box = child.getBoundingClientRect();
                    const offset = y - box.top - box.height / 2;
                    if (offset < 0 && offset > closest.offset) {
                        return { offset: offset, element: child };
                    } else {
                        return closest;
                    }
                }, { offset: Number.NEGATIVE_INFINITY }).element;
            }

            /* [3] 순서 저장 버튼 */
            const saveOrderBtn = document.createElement("button");
            saveOrderBtn.textContent = "목차 순서 저장";
            saveOrderBtn.classList.add("list-change-btn");
            document.querySelector(".view-content").appendChild(saveOrderBtn);

            saveOrderBtn.addEventListener("click", async function() {
                const reordered = Array.from(listBox.querySelectorAll(".lecture-item")).map((item, i) => ({
                    lectureIndexId: Number(item.dataset.id),
                    indexNumber: Number(i + 1),
                    indexTitle: item.querySelector(".lecture-title").innerText
                }));

                try {
                    const res = await fetch("/api/teachers/management/lectureindex/" + lectureId, {
                        method: "PUT",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(reordered)
                    });
                    const json = await res.json();
                    if (json.success) {
                        location.reload();
                    } else {
                        alert(json.message || "저장 실패");
                    }
                } catch (err) {
                    console.error("🚨 순서 저장 실패:", err);
                }
            });

            // 수정 버튼 렌더링
            if (lecture.lectureRegisterStatus === "UNREGISTERED" || lecture.lectureRegisterStatus === "REJECTED") addEditButtons();

        } catch (err) {
            console.error("🚨 강의정보/목차 로드 실패:", err);
        }


        /* [4] 새 목차 추가 버튼 */
        const addBtn = document.createElement("button");
        addBtn.textContent = "새 목차 추가";
        addBtn.classList.add("list-change-btn");
        document.querySelector(".view-content").appendChild(addBtn);

        addBtn.addEventListener("click", async () => {
            const title = prompt("새 목차 제목을 입력하세요:");
            if (!title) return;

            // 현재 마지막 번호 계산
            const items = listBox.querySelectorAll(".lecture-item");
            const newIndexNumber = items.length + 1;

            try {
                const res = await fetch("/api/teachers/management/lectureindex/" + lectureId, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        indexTitle: title,
                        indexNumber: newIndexNumber
                    })
                });

                const json = await res.json();
                if (json.status === "OK") {
                    alert("새 목차가 추가되었습니다!");
                    location.reload(); // 새로고침으로 반영
                } else {
                    alert(json.message || "추가 실패");
                }
            } catch (err) {
                console.error(" 목차 추가 실패:", err);
            }
            location.reload();
        });

        //강의 승인 요청 버튼 로직

        const registerBtn = document.querySelector(".lecture-register-button");

        registerBtn.addEventListener("click", async () => {
            try {
                // URL에서 lectureId 추출
                const pathParts = window.location.pathname.split("/");
                const lectureId = pathParts[pathParts.length - 1];
                console.log("📘 lectureId =", lectureId);

                // FormData 생성
                const formData = new FormData();
                formData.append("lectureId", lectureId);

                // 서버 요청
                const res = await fetch(`/api/teachers/management/lectureinfo/registerPending`, {
                    method: "POST",
                    body: formData
                });

                // 응답 처리
                if (res.ok) {
                    const data = await res.json();
                    console.log("등록 성공:", data);
                    alert("강의 등록 요청이 완료되었습니다.");
                    location.reload();
                } else {
                    console.error("서버 응답 오류:", res.status);
                    alert("강의 등록 요청에 실패했습니다.");
                }

            } catch (err) {
                console.error(" 강의 등록 중 오류:", err);
                alert("강의 등록 중 문제가 발생했습니다.");
            }
        });
        //DOMContentLoad End

    });

</script>

<style>
    .lecture-thumbnail {
        width: 100%;
        height: auto;
        border-radius: 10px;
        background-color: #f5f5f5;
        border: 1px dashed #bbb;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #777;
        font-size: 14px;
        font-weight: 500;
        text-align: center;
        cursor: pointer;
        transition: all 0.2s ease-in-out;
    }

    .lecture-thumbnail:hover {
        background-color: #e8f5e9;
        border-color: #7cb342;
        color: #558b2f;
    }

    .thumbnail-register-button {
        display: flex;
        justify-content: center;
        align-items: center;
        background: linear-gradient(135deg, #8fbc8f, #7fbf7f);
        color: white;
        font-weight: bold;
        padding: 8px 18px;
        border-radius: 25px;
        height: 36px;
        border: none;
        cursor: pointer;
        box-shadow: 0 2px 5px rgba(0,0,0,0.15);
        transition: all 0.2s ease-in-out;
    }

    .thumbnail-register-button:hover {
        background: linear-gradient(135deg, #7fbf7f, #6fae6f);
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0,0,0,0.2);
    }

    .lecture-view-wrapper {
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        background: #fff;
        padding-bottom: 80px;
    }

    .view-content {
        width: 80%;
        max-width: 1000px;
        border-radius: 12px;
        padding: 30px 40px;
        margin-top: 30px;
    }

    .view-title {
        font-size: 30px;
        font-weight: bold;
        text-align: center;
        color: #333;
        margin-bottom: 10px;
    }

    .view-section {
        margin-bottom: 20px;
    }

    .view-label {
        display: block;
        font-weight: 600;
        font-size: 18px;
        margin-bottom: 8px;
        color: #444;
    }

    .view-value {
        font-size: 17px;
        background: #f9f9f9;
        padding: 12px 15px;
        border-radius: 8px;
        border: 1px solid #ddd;
        white-space: pre-wrap;
        word-wrap: break-word;
    }

    .thumbnail-box {
        display: flex;
        justify-content: flex-start;
        background: #fff;
        border: 1px dashed #ccc;
        border-radius: 10px;
        padding: 15px;
    }

    .thumbnail-preview {
        width: 50%;
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    }

    #lecture-list-box {
        margin-top: 10px;
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        background: #fff;
    }


    .button-box {
        display: flex;
        justify-content: center;
        gap: 10px;
        margin-top: 40px;
    }

    .list-change-btn, .edit-btn, .delete-btn {
        border: none;
        background: #f1f1f1;
        border-radius: 6px;
        padding: 10px 16px;
        cursor: pointer;
        font-weight: 500;
        transition: 0.2s;
        margin-left:10px;
    }

    .edit-btn:hover { background: #d5f5e3; }
    .delete-btn:hover { background: #f5b7b1; }

    /*
    LIST PART CSS
    */

    .lecture-item{
        display:grid;
        grid-template-columns: 80px 1fr 280px auto; /* 번호 / 제목 / 파일명 / 버튼 */
        column-gap:16px;
        align-items:center;
        padding:12px 16px;
        margin-bottom:10px;
        border:1px solid #ddd;
        border-radius:8px;
        background:#fff;
    }

    .lecture-item.dragging {
        opacity: 0.6;
        background: #f1f8e9;
    }

    .delete-btn {
        background: #ffebee;
        color: #d32f2f;
        border: none;
        border-radius: 6px;
        padding: 6px 10px;
        cursor: pointer;
        font-weight: bold;
        transition: 0.2s;
    }

    .delete-btn:hover {
        background: #ffcdd2;
    }

    .list-change-btn {
        margin-top: 15px;
        background: #81c784;
        border: none;
        color: white;
        font-weight: 600;
        border-radius: 6px;
        padding: 8px 14px;
        cursor: pointer;
        transition: 0.2s;
    }

    .list-change-btn:hover {
        background: #66bb6a;
    }

    /*
    video button css
    */
    .lecture-actions {
        display: flex;
        gap: 8px;
        align-items: center;
        justify-content: flex-end;
    }

    .upload-btn {
        background: #e3f2fd;
        color: #1565c0;
        border: none;
        border-radius: 6px;
        padding: 6px 10px;
        cursor: pointer;
        font-weight: 600;
        transition: 0.2s;
    }

    .upload-btn:hover {
        background: #bbdefb;
    }

    .video-list-container {
        margin-top: 20px;
    }

    .video-item {
        margin-bottom: 15px;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 8px;
        background: #fafafa;
    }


    .lecture-index {
        font-weight: 600;
        text-align: center;
        color: #333;
    }

    .lecture-info {
        display: flex;
        flex-direction: column;
    }

    .lecture-video-title {
        font-size: 14px;
        color: #1565c0;
        font-weight: 500;
        text-align: center;
    }

    .lecture-actions {
        display: flex;
        justify-content: center;
        gap: 8px;
    }

    /*강의등록 버튼*/

    .lecture-register-button {
        display: inline-block;
        background: #4e73df; /* 기본 파랑 */
        color: #fff;
        font-weight: 600;
        font-size: 15px;
        padding: 10px 22px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: all 0.2s ease;
        box-shadow: 0 3px 6px rgba(0,0,0,0.15);
    }

    .lecture-register-button:hover {
        background: #3b5cc3; /* hover 시 약간 진하게 */
        transform: translateY(-1px);
    }

    .lecture-register-button:active {
        background: #2e4ca8;
        transform: translateY(0);
    }

    .lecture-register-button:disabled {
        background: #ccc;
        color: #666;
        cursor: not-allowed;
        box-shadow: none;
    }
    /* 판매태그 */
    .lecture-status-container {
        display: flex;
        gap: 10px;
        margin-bottom: 15px;
    }

    .lecture-status-container label {
        padding: 6px 12px;
        border-radius: 6px;
        font-weight: bold;
        font-size: 20px;
        color: white;
    }

    .lecture-status-container{
        display: flex;
        text-align: center;
        justify-content: center;
    }
</style>

<jsp:include page="/WEB-INF/views/page/teacher/management_lecture_info_edit_modal.jsp" />