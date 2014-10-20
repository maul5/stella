<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta charset="utf-8">
<title>Stella</title>

<%@ include file="/WEB-INF/views/include/script.jsp" %>
<%@ include file="/WEB-INF/views/include/css.jsp"%>

</head>
<body>
	<header>
		<h1>테스트 화면</h1>
	</header>
	<%@include file="/WEB-INF/views/include/nav.jsp" %>
	<nav>
		<ul>
			<li><a href="requestTest.do">requestTest.do</a></li>
			<li><a href="queryTest.do">queryTest.do</a></li>
		</ul>
		<form id="frmNav" name="frmNav" method="POST">
			<input type="hidden" name="param" />
			<input type="hidden" name="path" />
		</form>
	</nav>
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>