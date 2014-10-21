<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SmartEditor2 테스트</title>
<link href="resources/se/css/smart_editor2_in.css" rel="stylesheet" type="text/css">
</head>
<body class="smartOutput se2_inputarea">
	<p>
		<b><u>에디터 내용:</u></b>
	</p>
	
	<hr>
	<div style="width:736px;">
	<%=request.getParameter("ir1") %>
	</div>
	<hr>

	<p>
		<b>테스트 수행 파일(setest.jsp)에서 편집한 글이 정상적으로 나오나요?</b>
	</p>

<%@ include file="/WEB-INF/views/include/script.jsp" %>

</body>
</html>