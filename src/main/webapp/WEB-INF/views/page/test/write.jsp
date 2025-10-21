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

        // CSRF 토큰 쿠키 추출 함수
        function getCSRFToken() {
            // 현재 페이지의 meta 태그에서 읽기
            const token = $('meta[name="_csrf"]').attr('content');
            console.log('🔑 Meta 태그 토큰:', token);
            return token;
        }

        // 방향성 - 업로드 시에만 업로드 파일 반영
        // 최종적으로 업로드된 파일의 <img> 태그 추출해서 고아파일 색출 후 필요 없는파일은 삭제
        // 수정은, 기존의 본문을 불러와서 반영하고, 마찬가지로 파일의 변동에 따라 새롭게 추가된 파일 / 사라진 파일을 판별 (파일 정보로 판별)
        // 파일 테이블에 파일 유형도 첨부하여 구분할 예정, EDITOR, THUMBNAIL, 등...
        $(document).ready(function() {


            $('#writeForm').on('submit', function(e) {
                e.preventDefault();
                console.log('폼 제출 감지됨 form = {}', $(this).serialize());

                /*
                const formData = new FormData();
                const token = getCSRFToken();
                console.log('🔑 CSRF 토큰:', token);
                console.log('🍪 전체 쿠키:', document.cookie);
                formData.append("file", file);
                formData.append("entity", "LECTURE_QUESTION");
                formData.append("_csrf", token); // 인증 토큰 추가

                $.ajax({
                    url: '/test/editor/upload',
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (url) {
                        //$('#summernote').summernote('insertImage', url);
                        alert("ok!");
                        syncToParent();
                    },
                    error: function () {
                        alert(`업로드 실패`);
                    }
                });
                 */
            });

            // [6] 브라우저 창 닫기 / 새로고침 시 연결 종료
            window.addEventListener("beforeunload", (e) => {
                navigator.sendBeacon("/test/editor/write/exit");
            });

        });

    </script>
</head>
<body>

<main class="main">
    <h2>게시글 작성</h2>

    <form id="writeForm" method="post" action="<c:url value="/test/editor/write"/>">
        <!-- 글 제목 -->
        <div class="field">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" required>
        </div>

        <!-- 숨겨진 content -->
        <input type="hidden" id="content" name="content">

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