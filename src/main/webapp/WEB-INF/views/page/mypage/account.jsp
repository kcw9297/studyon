<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<section class="account-profile">
    <div class="mypage-title">프로필</div>
    <div class="mypage-info-container">
        <div class="mypage-info-profileimg">
            <div class="mypage-profile-wrapper">
                <img src="<c:url value='/img/png/menhera.png'/>" class="mypage-profile" alt="프로필 사진">
                <div class="mypage-profile-overlay">
                    <span>프로필 사진 변경</span>
                </div>
            </div>

            <!-- 파일 선택용 숨김 input -->
            <input type="file" id="editProfileImage" accept="image/*" style="display:none;">
        </div>

        <div class="mypage-info-nickname-box">
            <div class="mypage-info-nickname">지후</div>
            <button class="mypage-nickname-edit-button">✏️</button>
        </div>
    </div>
</section>




<section class="account-report">
    <div class="title">기본 정보</div>
    <div>
        <div class="account-chapter">
            <div class="account-item">이메일</div>
            <div>
                <div class="account-text">
                    <p>java@gmail.com</p>
                </div>
                <%--<button class="account-button">수정</button>--%>
            </div>
        </div>
        <div class="account-chapter">
            <div class="account-item">비밀번호</div>
            <div>
                <div class="account-text">
                    <p>••••••••</p>
                </div>
                <c:if test="${sessionScope.profile.provider eq 'NORMAL'}">
                    <button onclick="openPasswordeditModal()" class="password-edit-modal-button">수정</button>
                </c:if>
            </div>
        </div>
    </div>
</section>

<%--nickname & password modal--%>
<jsp:include page="/WEB-INF/views/page/mypage/nickname_edit_modal.jsp" />

<%--password edit modal --%>
<jsp:include page="/WEB-INF/views/page/auth/account_password_edit_modal.jsp" />



<style>

    <%-- 추가된 부분 이후에 우리 스타일에 맞게 변경 요구 --%>


    <%-- 추가된 부분 이후에 우리 스타일에 맞게 변경 요구 --%>

    .password-edit-modal-button{
        border : 2px solid green;
        padding : 5px;
        border-radius:15px;
        font-weight: bold;
    }

    .mypage-info-container {
        width: 100%;
        padding: 10px;
        height: auto;
        text-align: center;
    }

    .mypage-profile-wrapper {
        position: relative;
        display: inline-block;
        width: 300px;
        height: 300px;
    }

    .mypage-profile {
        width: 100%;
        height: 100%;
        border: 5px solid pink;
        border-radius: 180px;
        object-fit: cover;
        cursor: pointer;
        transition: 0.3s ease;
    }

    /* hover 시 반투명 어둡게 + 확대 */
    .mypage-profile:hover {
        transform: scale(1.03);
        filter: brightness(80%);
    }

    /* overlay 기본 숨김 */
    .mypage-profile-overlay {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        border-radius: 180px;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 18px;
        font-weight: bold;
        opacity: 0;
        transition: opacity 0.3s ease;
        cursor: pointer;
    }

    /* hover 시 overlay 등장 */
    .mypage-profile-wrapper:hover .mypage-profile-overlay {
        opacity: 1;
    }

    /* 닉네임 */
    .mypage-info-nickname-box {
        width: 100%;
        text-align: center;
        margin-top: 10px;
    }

    .mypage-info-nickname {
        font-weight: bold;
        font-size: 20px;
        color: mediumseagreen;
    }

    .mypage-nickname-edit-button {
        opacity: 60%;
        font-size: 14px;
        border: none;
        background: none;
        cursor: pointer;
    }
</style>



<script src="<c:url value='/js/page/mypage/account.js'/>"></script>
<script>
    document.addEventListener("DOMContentLoaded", async () => {

        /* 프로필 정보 출략 */

        //  data 필드(문자열)를 다시 파싱

        // 닉네임
        const nicknameElem = document.querySelector(".mypage-info-nickname");
        if (nicknameElem) nicknameElem.textContent = "${sessionScope.profile.nickname}" || "닉네임 없음";

        // 프로필 이미지
        const imgElem = document.querySelector(".mypage-profile");
        if (imgElem) {
            imgElem.src = ${not empty sessionScope.profile.profileImage} ?
                `${fileDomain}/${sessionScope.profile.profileImage.filePath}` : "<c:url value='/img/png/default_member_profile_image.png'/>";
        }

        // 이메일 (첫 번째 account-chapter 안의 p)
        const emailElem = document.querySelector(".account-report .account-chapter:nth-child(1) .account-text p");
        if (emailElem) emailElem.textContent = "${sessionScope.profile.email}" || "이메일 없음";

        // 비밀번호 (보안상 실제 비밀번호는 안 주지만, 마스킹)
        const passwordElem = document.querySelector(".account-report .account-chapter:nth-child(2) .account-text p");
        if (passwordElem) passwordElem.textContent = "••••••••";




        /* 프로필 이미지 수정 */

        // 프로필 이미지 변경
        const profileWrapper = document.querySelector(".mypage-profile-wrapper");
        const profileOverlay = document.querySelector(".mypage-profile-overlay");
        const inputFile = document.getElementById("editProfileImage");
        const allowedExt = ["png", "jpg", "jpeg", "webp"];

        // 오버레이 클릭 시 파일 선택 창 열기
        if (profileWrapper && inputFile) {
            profileWrapper.addEventListener("click", () => {
                inputFile.click();
            });

            // 파일 선택 시 이벤트
            inputFile.addEventListener("change", (e) => {

                const file = e.target.files[0];
                if (!file) return;

                // meme 타입 검사
                if (!file.type.startsWith("image/")) {
                    alert("이미지 파일만 선택 가능합니다.");
                    return;
                }

                // 파일 확장자 검사
                const fileExt = file.name.split(".").pop().toLowerCase();
                if (!allowedExt.includes(fileExt)) {
                    alert("PNG, JPG, JPEG 형식의 정적 이미지 파일만 업로드 가능합니다.");
                    inputFile.value = ""; // 선택 초기화
                    return;
                }

                // 파일 크기 검사
                const maxSize = 5 * 1024 * 1024;
                if (file.size > maxSize) {
                    alert("파일 크기는 5MB 이하여야 합니다.");
                    return;
                }

                editProfileImage(file);
            });
        }


        const nicknameEditBtn = document.querySelector(".mypage-nickname-edit-button");
        const closeBtn = document.getElementById("closeNicknameBtn");
        if (nicknameEditBtn) {
            nicknameEditBtn.addEventListener("click", () => {
                // 닉네임 수정 모달 열기
                openNicknameModal();
            });
        }
        if (closeBtn) {
            closeBtn.addEventListener("click", closeNicknameModal);
        }

        // ✅ 비밀번호 수정 버튼 클릭 → 모달 열기
        const passwordEditBtn = document.querySelector(".password-edit-button");
        const closePasswordBtn = document.getElementById("closePasswordModalBtn");
        const sendResetEmailBtn = document.getElementById("sendResetEmailBtn");
        const closeMailBtn = document.getElementById("closeMailSuccessBtn");

        if (passwordEditBtn) {
            passwordEditBtn.addEventListener("click", () => {
                openPasswordModal();
            });
        }

        if (closePasswordBtn) {
            closePasswordBtn.addEventListener("click", () => {
                closePasswordModal();
            });
        }

        if (sendResetEmailBtn) {
            sendResetEmailBtn.addEventListener("click", async () => {
                // 실제 이메일 발송 로직 (예: fetch("/api/auth/password-reset", {...}))
                // 여기선 성공했다고 가정
                openMailSendSuccessModal();
            });
        }

        if (closeMailBtn) {
            closeMailBtn.addEventListener("click", closeMailSendSuccessModal);
        }
    });



    async function editProfileImage(file) {

        try {

            // REST API 요청
            const form = new FormData();
            form.append("profileImage", file);

            const res = await fetch("/api/members/profile_image", {
                method: "PATCH",
                body: form
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

                // 유효성 검사에 실패한 경우
                if (rp.inputErrors) {
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

            // 프로필 이미지 변겅 처리
            const imgElem = document.querySelector(".mypage-profile");
            if (imgElem) {
                imgElem.src =
                    URL.createObjectURL(file) || "<c:url value='/img/png/default_member_profile_image.png'/>";
            }


        } catch (error) {
            console.error("프로필 로드 실패:", error);
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

    function openPasswordModal() {
        const modal = document.getElementById("passwordResetModal");
        if (modal) modal.style.display = "flex";
    }

    function closePasswordModal() {
        const modal = document.getElementById("passwordResetModal");
        if (modal) modal.style.display = "none";
    }

    function openMailSendSuccessModal() {
        const modal = document.getElementById("mailSendSuccessModal");
        if (modal) modal.style.display = "flex";
    }

    function closeMailSendSuccessModal() {
        const modal = document.getElementById("mailSendSuccessModal");
        if (modal) modal.style.display = "none";
    }

    function openPasswordeditModal() {
        document.querySelector('.password-modal').style.display = 'flex';
    }

    function closePasswordeditModal() {
        document.querySelector('.password-modal').style.display = 'none';
    }



</script>