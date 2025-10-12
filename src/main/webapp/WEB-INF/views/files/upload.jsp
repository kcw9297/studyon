<!DOCTYPE html>
<%@ page contentType ="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Fileupload Form</title>
		<style>
			a{text-decoration:none}
		</style>
	</head>
	<body style="text-align:center">

		<h1>Fileupload Form</h1>
		<form action="<c:url value="/file/upload.do"/>"  method="post" enctype="multipart/form-data">
				파일: <input type='file' name='file'><br/>
			<input type='submit' value="전송">
		</form> 
		
	</body>
</html>