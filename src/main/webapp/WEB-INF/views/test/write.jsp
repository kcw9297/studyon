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
            height: 550px; /* 에디터 높이 + 50px*/
        }
    </style>
</head>
<body>

<main class="main">
    <h2>게시글 작성</h2>

    <form id="writeForm" method="post" action="<c:url value="/test/write"/>">
        <!-- 글 제목 -->
        <div class="field">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" required>
        </div>

        <!-- 숨겨진 content -->
        <input type="hidden" id="content" name="content">

        <!-- iframe 에디터 -->
        <iframe src="<c:url value="/summernote?width=1000&height=500"/>"></iframe>

        <!-- 하단 버튼 -->
        <div style="margin-top: 20px;">
            <button type="submit">작성</button>
            <button type="button" onclick="location.href='/'">뒤로</button>
        </div>
    </form>
</main>

</body>
</html>