<!DOCTYPE html>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>파일 리스팅</title>
</head>
<body>
<center>
    <h3>< 파일 리스트 ></h3>
    <a href="<c:url value="/file/upload.do"/>">파일폼</a><br/>

    <h4>이미지 출력</h4>
    <c:if test="${empty files}">
        <tr>
            <td align="center" colspan="3">파일이 하나도 없음</td>
        </tr>
    </c:if>

    <c:forEach items="${files}" var="file">
        <tr>
            <td align="center">
                <c:choose>
                    <c:when test="${fn:endsWith(file.storeName, '.mp4')}">
                        <video width="480" controls>
                            <source src="${fileDomain}/${file.entity.value}/${file.storeName}" type="video/mp4">
                            브라우저가 video 태그를 지원하지 않습니다.
                        </video>
                    </c:when>
                    <c:otherwise>
                        <a href=<c:url value="/file/download/${file.fileId}"/>>
                            <img src="${fileDomain}/${file.entity.value}/${file.storeName}" alt="${file.originalName}" width="200" />
                        </a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>

</center>
</body>
</html>