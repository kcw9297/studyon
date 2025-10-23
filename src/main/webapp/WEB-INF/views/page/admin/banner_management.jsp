<%-- 운영자 페이지 기본 Template 수정해서 사용하면됨 --%>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/admin/banner_management.css'/>">
<div id="empty-box"></div>

<jsp:include page="/WEB-INF/views/page/admin/navbar.jsp">
    <jsp:param name="active" value="banner"/>
</jsp:include>

<div class="admin-content-container">
    <%--
    <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="메인비주얼이미지" class="main-banner">
    --%>
    <div class="banner-admin-container">
        <h2 class="admin-page-title">배너 관리</h2>

        <!-- 현재 등록된 배너 -->
        <div class="banner-grid">
            <c:forEach var="banner" items="${bannerList}" varStatus="loop">
                <div class="banner-card">
                    <div class="banner-preview">
                        <img src="${banner.imageUrl}" alt="배너 ${loop.index + 1}">
                    </div>
                    <form class="banner-form" method="post" enctype="multipart/form-data" action="/admin/banner/upload">
                        <input type="hidden" name="bannerId" value="${banner.bannerId}">
                        <label class="upload-label">
                            새 이미지 선택
                            <input type="file" name="bannerFile" accept="image/*" required>
                        </label>
                        <div class="banner-actions">
                            <button type="button" class="btn-danger" data-id="${banner.bannerId}">삭제</button>
                        </div>
                    </form>
                </div>
            </c:forEach>

            <!-- 배너 3개 미만일 때만 추가 가능 -->
            <c:if test="${fn:length(bannerList) < 3}">
                <div class="banner-card add-card">
                    <form id="newBannerForm" method="post" enctype="multipart/form-data" action="/admin/banner/upload">
                        <label class="upload-label">
                            새 배너 추가
                            <input type="file" name="bannerFile" accept="image/*" required>
                        </label>
                        <button type="submit" class="btn-primary">추가</button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>

        <div class="banner-upload-area">
            <!-- 더미 배너 1 -->
            <div class="banner-slot">
                <div class="banner-preview">
                    <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="메인비주얼이미지" class="main-banner">
                </div>
                <form class="banner-form" method="post" enctype="multipart/form-data" action="/admin/banner/upload">
                    <button type="button" class="btn-delete" data-id="1">삭제</button>
                </form>
            </div>

            <!-- 더미 배너 2 -->
            <div class="banner-slot">
                <div class="banner-preview">
                    <img src="<c:url value='/img/png/teacher_profile_img.png'/>" alt="메인비주얼이미지" class="main-banner">
                </div>
                <form class="banner-form" method="post" enctype="multipart/form-data" action="/admin/banner/upload">
                    <button type="button" class="btn-delete" data-id="1">삭제</button>
                </form>
            </div>
        </div>

</div>

<style>
    .admin-content-container {
        border:2px solid black;
        min-height: 600px;
        height:auto;
        display:flex;
        flex-direction:column;
        padding:10px;
    }

    .main-banner{
        width:600px;
        height:auto;
    }

    .description{
        font-size:30px;
        font-weight:bold;
    }

    /*배너 css*/
    .banner-admin-container {
        width: 95%;
        margin: 30px auto;
        background: #fff;
        border-radius: 10px;
        box-shadow: 0 3px 10px rgba(0,0,0,0.1);
        padding: 25px 30px;
    }

    .admin-page-title {
        font-size: 22px;
        font-weight: 700;
        color: #333;
        margin-bottom: 25px;
    }

    /* 배너 카드 그리드 */
    .banner-grid {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
    }

    .banner-form{
        text-align: center;
        font-weight: bold;
        margin-bottom:15px;
    }

    .banner-card {
        flex: 1 1 30%;
        background: #f9f9f9;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        padding: 15px;
        text-align: center;
        transition: all 0.2s ease;
    }
    .banner-card:hover {
        transform: translateY(-3px);
        box-shadow: 0 4px 10px rgba(0,0,0,0.15);
    }

    /* 이미지 미리보기 */
    .banner-preview {
        width: 100%;
        height: auto;
        background: white;
        border-radius: 6px;
        overflow: hidden;
        text-align:center;
    }
    .banner-preview img {
        width: 800px;
        height: auto;
        object-fit: cover;
    }

    /* 파일 업로드 라벨 */
    .upload-label {
        display: inline-block;
        background: #ececec;
        color: #333;
        padding: 6px 12px;
        border-radius: 6px;
        font-size: 14px;
        cursor: pointer;
        transition: 0.2s;
    }
    .upload-label:hover {
        background: #ddd;
    }
    .upload-label input[type="file"] {
        display: none;
    }

    /* 버튼 */
    .banner-actions {
        display: flex;
        justify-content: center;
        gap: 10px;
        margin-top: 10px;
    }
    .btn-primary, .btn-danger {
        padding: 6px 12px;
        border: none;
        border-radius: 6px;
        font-size: 14px;
        color: #fff;
        cursor: pointer;
        transition: 0.2s;
    }
    .btn-primary {
        background: #4a90e2;
    }
    .btn-primary:hover {
        background: #357ac8;
    }
    .btn-danger {
        background: #e74c3c;
    }
    .btn-danger:hover {
        background: #c0392b;
    }

    /* 추가 카드 스타일 */
    .add-card {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background: #fafafa;
        border: 2px dashed #bbb;
    }
    .add-card:hover {
        background: #f0f0f0;
    }

    .banner-slot img {
        border: 2px solid #eee;
        border-radius: 6px;
    }
    .banner-slot:hover img {
        border-color: #4a90e2;
    }
</style>

<script src="<c:url value='/js/page/admin/banner_management.js'/>"></script>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        // 🔹 파일 선택 시 미리보기
        document.querySelectorAll('input[type="file"]').forEach(input => {
            input.addEventListener("change", (e) => {
                const file = e.target.files[0];
                const preview = e.target.closest(".banner-card")?.querySelector("img");
                if (file && preview) {
                    const reader = new FileReader();
                    reader.onload = (ev) => preview.src = ev.target.result;
                    reader.readAsDataURL(file);
                }
            });
        });

        // 🔹 삭제
        document.querySelectorAll(".btn-danger").forEach(btn => {
            btn.addEventListener("click", () => {
                const id = btn.dataset.id;
                if (confirm("이 배너를 삭제하시겠습니까?")) {
                    fetch(`/admin/banner/delete/${id}`, { method: "DELETE" })
                        .then(res => res.ok ? location.reload() : alert("삭제 실패"));
                }
            });
        });
    });
</script>