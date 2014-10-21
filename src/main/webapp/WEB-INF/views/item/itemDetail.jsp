<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>Setlla</title>

<%@ include file="/WEB-INF/views/include/css.jsp"%>

</head>

<body>
	<header>
		<h1>상품 상세 화면</h1>
	</header>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>
	<section>
		<form id="frm" method="POST">
			<%@ include file="/WEB-INF/views/include/hidden.jsp"%>
			<article>
				<ol>
					<li>
						<img id="itemImage"/>
					</li>
					<li><label for="itemId">ID</label>
						<input type="text" id="itemId" name="itemId" />
					</li>
					<li><label for="itemName">상품명</label>
						<input type="text" id="itemName" name="itemName" />
					</li>
					<li><label for="price">가격</label>
						<input type="text" id="price" name="price" />
					</li>
					<li><label for="description">설명</label>
						<input type="text" id="description" name="description" />
					</li>
				</ol>
			</article>
		</form>
	</section>

<%@ include file="/WEB-INF/views/include/script.jsp"%>

<script>
	$("document").ready(function() {
		console.log(<%=request.getParameter("param")%>); // 전달받은 param 값 확인(객체가 아니고 문자열임)

		action(2);
	});

	function action(actionType) {
		switch (actionType) {
		case 1: // 목록
			break;
		case 2: // 상세
			$.ajax({
				type : "POST",
				url : "select/item.getItem.do",
				dataType : "json",
				data : {
					"param" : JSON.stringify(<%=request.getParameter("param")%>)
				},
				beforeSend : function(xhr) {
					// 전송 전 Code
				},
				success : function(resultList) {
					$.each(resultList, function() {
						console.log(this.itemid + " | " + this.itemname + " | "
								+ this.price + " | " + this.pictureurl);
						//TODO 이미지, 첨부파일 등이 한글명일 경우 처리 방법 필요
						//var encodedUrl = encodeURIComponent(this.pictureurl);
						var encodedUrl = this.pictureurl;
						$("#itemImage").attr("src", "resources/images/" + encodedUrl);
						$("#itemId").val(this.itemid);
						$("#itemName").val(this.itemname);
						$("#price").val(this.price);
					});
				},
				error : function(error) {
					alert("Error 발생");
				}
			});

			break;
		}
	}
</script>
	
</body>
</html>