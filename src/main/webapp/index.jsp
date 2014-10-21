<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta charset="utf-8">
<title>Stella</title>
</head>

<body>
	<header>
		<h1>메인 화면</h1>
	</header>
	<%@include file="/WEB-INF/views/include/nav.jsp" %>
	<section>
		<article>
			<h2>공지사항</h2>
			<ul>
				<li>1. 오픈 했어요</li>
				<li>2. 무조건 주문 해 주세요</li>
				<li>3. 계속 구매 해셔야 합니다. ^^</li>
			</ul>
		</article>
	</section>
	<%@include file="/WEB-INF/views/include/footer.jsp" %>

<%@ include file="/WEB-INF/views/include/script.jsp" %>
<%@ include file="/WEB-INF/views/include/css.jsp"%>

</body>
</html>