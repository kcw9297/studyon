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
                <button class="account-button">수정</button>
            </div>
        </div>
        <div class="account-chapter">
            <div class="account-item">비밀번호</div>
            <div>
                <div class="account-text">
                    <p>••••••••</p>
                </div>
                <button class="account-button">수정</button>
            </div>
        </div>
    </div>
</section>


<style>
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



<script src="<c:url value='/js/mypage/account.js'/>"></script>
<script>
    document.addEventListener("DOMContentLoaded", async () => {
        try {
            console.log("✅ account.js 로드됨");

            // ✅ REST API 요청
            const res = await fetch("/api/members/profile");

            if (!res.ok) {
                if (res.status === 401) {
                    alert("로그인이 필요합니다.");
                    window.location.href = "/login";
                    return;
                }
                throw new Error("서버 오류가 발생했습니다.");
            }

            // ✅ JSON 데이터 파싱
            const profile = await res.json();
            console.log("📥 사용자 프로필:", profile);

            // ✅ 닉네임
            const nicknameElem = document.querySelector(".mypage-info-nickname");
            if (nicknameElem) nicknameElem.textContent = profile.nickname || "닉네임 없음";

            // ✅ 프로필 이미지
            const imgElem = document.querySelector(".mypage-profile");
            if (imgElem) {
                imgElem.src = profile.imageUrl || "<c:url value='/img/png/menhera.png'/>";
            }

            // ✅ 이메일 (첫 번째 account-chapter 안의 p)
            const emailElem = document.querySelector(".account-report .account-chapter:nth-child(1) .account-text p");
            if (emailElem) emailElem.textContent = profile.email || "이메일 없음";

            // ✅ 비밀번호 (보안상 실제 비밀번호는 안 주지만, 마스킹)
            const passwordElem = document.querySelector(".account-report .account-chapter:nth-child(2) .account-text p");
            if (passwordElem) passwordElem.textContent = "••••••••";

        } catch (error) {
            console.error("❌ 프로필 로드 실패:", error);
        }
    });
</script>