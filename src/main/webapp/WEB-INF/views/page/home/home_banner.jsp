<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="main-banner-container">
    <img src="<c:url value='/img/png/banner1.png'/>" class="main-banner fade active" alt="배너1">
    <img src="<c:url value='/img/png/banner2.png'/>" class="main-banner fade" alt="배너2">
</div>


<script>

    document.addEventListener("DOMContentLoaded", () => {
        loadBanner();
    });

    async function loadBanner() {

        try {
            const res = await fetch('/api/banners', {
                method: 'GET'
            });

            const rp = await res.json();
            console.log("배너 응답:", rp);

            if (!res.ok || !rp.success) {
                console.error('배너 로드 실패:', rp.message);
                return;
            }

            const banners = rp.data;

            // 활성화된 배너가 있는 경우에만 처리
            if (banners && banners.length > 0) {
                const container = document.querySelector('.main-banner-container');
                container.innerHTML = ''; // 기존 하드코딩된 내용 제거

                // 배너 생성 및 추가
                banners.forEach((banner, index) => {
                    const img = document.createElement('img');
                    img.src = `${fileDomain}/\${banner.bannerImage.filePath}`;
                    img.className = index === 0 ? 'main-banner fade active' : 'main-banner fade';
                    img.alt = banner.title || `배너\${index + 1}`;
                    container.appendChild(img);
                });
            }

            // 배너 전환 시작
            startBannerRotation();

        } catch (error) {
            console.error('배너 로드 오류:', error);
        }
    }

    // 배너 로테이션 시작
    function startBannerRotation() {
        const banners = document.querySelectorAll(".main-banner");
        let current = 0;

        setInterval(() => {
            banners[current].classList.remove("active");
            current = (current + 1) % banners.length;
            banners[current].classList.add("active");
        }, 4000); // 4초마다 전환
    }
</script>
