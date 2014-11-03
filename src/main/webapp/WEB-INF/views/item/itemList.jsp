<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <%@ include file="/WEB-INF/views/include/script.jsp" %>
    <%@ include file="/WEB-INF/views/include/css.jsp"%>

    <!-- Custom CSS -->
    <link href="resources/css/shop-homepage.css" rel="stylesheet">

    <script>
    $("document").ready(function() {
        <%-- test(); --%>
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
                    //resultList is array
                    var jsonList = {'jsonList':resultList}
                    //jsonList is JSON
                    var itemListTpl = $("#itemListTpl").html();
                    var html = Mustache.render(itemListTpl, jsonList);
                    $("#itemList").html(html);
                },
                error : function(error) {
                     alert("Error 발생");
                }
            });

            break;
        }
    }

    <%--
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
    --%>
    </script>
</head>

<body>
    <!-- 상단 Navigation -->
    <%@include file="/WEB-INF/views/include/nav.jsp" %>
    
    <!-- Page Content -->
    <div class="container">
        <div class="row">
        
            <%@include file="/WEB-INF/views/include/left.jsp" %>

            <div class="col-md-9">

                <div class="row carousel-holder">

                    <div class="col-md-12">
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img class="slide-image" src="resources/images/main_item/IMG_0010.jpg" alt=""> <!-- 800x300 -->
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="resources/images/main_item/IMG_0028.jpg" alt="">
                                </div>
                                <div class="item">
                                    <img class="slide-image" src="resources/images/main_item/IMG_0279.jpg" alt="">
                                </div>
                            </div>
                            <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </a>
                            <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                            </a>
                        </div>
                    </div>

                </div>

                <form id="form01" method="POST">
                <%@ include file="/WEB-INF/views/include/hidden.jsp"%>
                <div id="itemList" class="row">
                    <script id="itemListTpl" type="text/template">
                    {{#jsonList}}
                    <div class="col-sm-4 col-lg-4 col-md-4">
                        <div class="thumbnail">
                            <img src="resources/images/{{pictureurl}}" alt="{{itemname}}">
                            <div class="caption">
                                <h4 class="pull-right">{{price}}</h4>
                                <h4><a href="javascript:reqDirectPage('form01', 'item', 'itemDetail', { itemid:{{itemid}} })">{{itemname}}</a></h4>
                                <p>{{description}}</p>
                            </div>
                            <div class="ratings">
                                <p class="pull-right">15 reviews</p>
                                <p>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                </p>
                            </div>
                        </div>
                    </div>
                    {{/jsonList}}
                    </script>

                    <div class="col-sm-4 col-lg-4 col-md-4">
                        <h4><a href="#">Like this template?</a>
                        </h4>
                        <p>If you like this template, then check out <a target="_blank" href="http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/">this tutorial</a> on how to build a working review system for your online store!</p>
                        <a class="btn btn-primary" target="_blank" href="http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/">View Tutorial</a>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>
    <!-- /.container -->

    <%@ include file="/WEB-INF/views/include/footer.jsp"%>

</body>
</html>