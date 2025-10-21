<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>글 작성</title>

    <style>
        iframe {
            display: block;
            border: none;
            width: 100%;
            height: 560px; /* 에디터 높이 + 60px*/
        }
    </style>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>

        // 스크립트 상수
        let isWaiting = false;
        let pending = false;
        let isSubmitted = false; // 작성 완료 여부 플래그

        // 에디터 본문이 편집된 경우 감지
        function onEditorContentChange(contents) {
            if (isSubmitted) return; // 제출이 완료된 경우 건너뜀
            console.log('부모 content 변경 감지:', contents);
            $('#content').val(contents);
            scheduleUpdate();
        }

        // 변경 감지 후 갱신 (변경된 내용을 캐시에 반영)
        function scheduleUpdate() {

            // [1] 대기 상태가 아니라면 갱신 수행
            if (!isWaiting) {
                // 대기 중이 아니면 즉시 전송
                sendUpdate();
                isWaiting = true;

                // 5초 뒤에 대기 해제
                setTimeout(() => {
                    isWaiting = false;

                    // 대기 중 변경이 쌓였으면 마지막 데이터로 한 번 더 전송
                    if (pending) {
                        sendUpdate();
                        pending = false;
                    }
                }, 1000);

            } else {

                // [2] 대기 중일 때는 데이터만 저장
                pending = true;
                console.log('⌛ 전송 예약됨 (5초 후)');
            }
        }

        // 변경 업데이트
        function sendUpdate() {

            // 현재 작성된 데이터
            const formData = new FormData();
            const token = $('meta[name="_csrf"]').attr('content');
            formData.append("content", $("#content").val());
            formData.append("_csrf", token); // 인증 토큰 추가

            // 데이터 전송 (캐시 동기화)
            $.ajax({
                url: '/testboard/change',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function () {},
                error: function (xhr) {
                    const rp = JSON.parse(xhr.responseText);
                    const content = rp.message.content || "세션이 만료되었습니다.\n다시 작성해 주세요";
                    const redirect = rp.redirect || "/";
                    alert(content);
                    window.location.href = redirect;
                }
            });
        }



        // 방향성 - 업로드 시에만 업로드 파일 반영
        // 최종적으로 업로드된 파일의 <img> 태그 추출해서 고아파일 색출 후 필요 없는파일은 삭제
        // 수정은, 기존의 본문을 불러와서 반영하고, 마찬가지로 파일의 변동에 따라 새롭게 추가된 파일 / 사라진 파일을 판별 (파일 정보로 판별)
        // 파일 테이블에 파일 유형도 첨부하여 구분할 예정, EDITOR, THUMBNAIL, 등...
        $(document).ready(function() {

            /*
            $(document).on('input change', '.write input, .write select', function() {
                console.log('변경 감지:', this.name, $(this).val());
                scheduleUpdate()
            });

             */


            $('#writeForm').on('submit', function(e) {
                e.preventDefault();
                console.log('폼 제출 감지됨 form = {}', $(this).serialize());

                const formData = new FormData(this);
                const token = $('meta[name="_csrf"]').attr('content');
                console.log('🔑 CSRF 토큰:', token);
                console.log('🍪 전체 쿠키:', document.cookie);
                formData.append("_csrf", token); // 인증 토큰 추가

                $.ajax({
                    url: '/testboard/write',
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,

                    success: function (rp) {
                        const message = rp.message || "저장에 성공했습니다!";
                        const redirect = rp.redirect || "/";
                        alert(message.content);
                        window.location.href = redirect;
                    },
                    error: function (xhr) {
                        const rp = JSON.parse(xhr.responseText);
                        const content = rp.message.content || "오류가 발생했습니다\n잠시 후에 다시 시도해 주세요";
                        alert(content);
                    }
                });

            });

        });

    </script>
</head>
<body>

<main class="main">
    <h2>게시글 작성</h2>

    <form id="writeForm" method="post" action="<c:url value="/testboard/write"/>">

        <!-- 숨겨진 content -->
        <textarea id="content" name="content" class="write" hidden>${not empty cache.content ? cache.content : ''}</textarea>

        <!-- iframe 에디터 -->
        <iframe src="<c:url value="/editor?width=1000&height=500"/>"></iframe>

        <!-- 하단 버튼 -->
        <div style="margin-top: 20px;">
            <button type="submit">작성</button>
            <button type="button" onclick="location.href='/'">뒤로</button>
        </div>
    </form>
</main>

</body>
</html>