<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Stella</title>

<%@ include file="/WEB-INF/views/include/css.jsp"%>

</head>

<body>
	<header>
		<h1>상품 리스트 화면</h1>
	</header>
	<%@ include file="/WEB-INF/views/include/nav.jsp" %>
	<section>
		<form id="frm01" method="POST">
			<%@ include file="/WEB-INF/views/include/hidden.jsp"%>
			<article>
				<h2>상품 리스트 화면</h2>
				<table id="list" border="1">
					<tr class="header">
						<th align="center" width="100">상품 ID</th>
						<th align="center" width="320">상품 명</th>
						<th align="center" width="100">가격(원)</th>
					</tr>
				</table>
			</article>
		</form>
	</section>
	<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<%@ include file="/WEB-INF/views/include/script.jsp"%>

<script>
	$("document").ready(function() {
		test();
		
		action(1);
	});

	function action(actionType) {
		switch (actionType) {
		case 1: // 목록
			$.ajax({ type : "POST",
				url : "select/item.getListItem.do",
				dataType : "json",
				data : {"param" : ''},
				beforeSend : function(xhr) {
					// 전송 전 Code
				},
				success : function(resultList) {
					$.each(resultList, function() {
						console.log(this.itemid + " | " + this.itemname + " | " + this.price);
						/*
						<tr>
						<td></td>
						<td></td>
						<td></td>
						</tr>
						*/
						var td1 = document.createElement("td"); //상품 ID
						td1.innerHTML = this.itemid;
						var td2 = document.createElement("td"); //상품명
						var path = "item";
						var file = "itemDetail";
						var param = "{itemid:\'" + this.itemid + "\'}";
						td2.innerHTML = '<a href="javascript:direct_view(\'' + path + '\', \'' + file + '\', ' + param + ')">' + this.itemname + '</a>';

						var td3 = document.createElement("td"); //가격(원)
						td3.innerHTML = this.price;
						
						var tr = document.createElement("tr");
						tr.appendChild(td1);
						tr.appendChild(td2);
						tr.appendChild(td3);
						
						$("#list").append(tr);
					});
				},
				error : function(error) {
				 	alert("Error 발생");
				}
			});

			break;
		}
	}
	
	function test() {
		var arr =["a", "b", "c"];
		
		var str = JSON.stringify(arr);
		console.log(str);

		var newArr = JSON.parse(str);

		while (newArr.length > 0) {
			console.log(newArr.pop());
		}
		
		var employees = [
		                 {"firstName":"John", "lastName":"Doe"}, 
		                 {"firstName":"Anna", "lastName":"Smith"}, 
		                 {"firstName":"Peter", "lastName": "Jones"}
		             ];
		console.log(employees[0].firstName + " " + employees[0].lastName);
		
		
	}
</script>

</body>
</html>