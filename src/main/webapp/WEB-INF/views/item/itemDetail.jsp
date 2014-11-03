<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <%@ include file="/WEB-INF/views/include/script.jsp" %>
    <%@ include file="/WEB-INF/views/include/css.jsp"%>

    <!-- Custom CSS -->
    <link href="" rel="stylesheet">

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
					//resultList is array

					//TODO 이미지, 첨부파일 등이 한글명일 경우 처리 방법 필요
					//var encodedUrl = encodeURIComponent(jsonObejct.pictureurl);
					var itemTpl = $("#itemTpl").html();
					var html = Mustache.render(itemTpl, resultList[0]);
					$("#item").html(html);
					<%--
					$.each(resultList, function() {
						console.log(this.itemid + " | " + this.itemname + " | "
								+ this.price + " | " + this.pictureurl);
						var encodedUrl = this.pictureurl;
						$("#itemImage").attr("src", "resources/images/" + encodedUrl);
						$("#itemId").val(this.itemid);
						$("#itemName").val(this.itemname);
						$("#price").val(this.price);
					});
					--%>
				},
				error : function(error) {
					alert("Error 발생");
				}
			});

			break;
		}
	}
	</script>
</head>

<body>
	<!-- 상단 Navigation -->
	<%@ include file="/WEB-INF/views/include/top.jsp"%>
	
    <!-- Page Content -->
    <div class="container">
        <div class="row">

            <%@include file="/WEB-INF/views/include/left.jsp" %>

            <div class="col-md-9">

                <div id="item" class="thumbnail">
                    <script id="itemTpl" type="text/template">
                    <img class="img-responsive" src="resources/images/{{pictureurl}}" alt="{{itemname}}">
                    <div class="caption-full">
                        <h4 class="pull-right">{{price}}</h4>
                        <h4><a href="#">{{itemname}}</a></h4>
                        <p>{{description}}</p>
                    </div>
                    <div class="ratings">
                        <p class="pull-right">3 reviews</p>
                        <p>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            4.0 stars
                        </p>
                    </div>
                    </script>
                </div>

                <div class="well">

                    <div class="text-right">
                        <a class="btn btn-success">Leave a Review</a>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-md-12">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            Anonymous
                            <span class="pull-right">10 days ago</span>
                            <p>This product was great in terms of quality. I would definitely buy another!</p>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-md-12">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            Anonymous
                            <span class="pull-right">12 days ago</span>
                            <p>I've alredy ordered another one!</p>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-md-12">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            Anonymous
                            <span class="pull-right">15 days ago</span>
                            <p>I've seen some better than this, but not at this price. I definitely recommend this item.</p>
                        </div>
                    </div>

                </div>

            </div>

        </div>

    </div>
    <!-- /.container -->
    
    <%@ include file="/WEB-INF/views/include/footer.jsp"%>
    
</body>
</html>