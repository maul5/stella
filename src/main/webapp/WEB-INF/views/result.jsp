<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="ko">
<meta charset="utf-8">
<title>Stella</title>

<%@include file="/WEB-INF/views/include/script.jsp" %>
<%@ include file="/WEB-INF/views/include/css.jsp"%>

<script>
	$("document").read(function() {
		if (frm.result.value == "SUCCESS") {
			alert(frm.message.value);

			$("#frm").attr({
				action : frm.action.value,
				target : "_self",
				method : "POST"
			});

			$("#frm").submit();
		}
	});
</script>
</head>
<body>
	<form id="frm" name="frm">

		<input type="hidden" name="result" value="${result}" />
		<input type="hidden" name="path" value="${path}" />
		<input type="hidden" name="action" value="${action}" />
		<input type="hidden" name="message" value="${message}" />
		<input type="hidden" name="param" value="" />

		<%-- <p>result  = ${result}</p> --%>
		<%-- <p>path    = ${path}</p> --%>
		<%-- <p>action  = ${action}</p> --%>
		<%-- <p>message = ${message}</p> --%>

	</form>
</body>
</html>