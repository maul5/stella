<%@ page pageEncoding="UTF-8"%>
            <div class="col-md-3">
                <!-- <p class="lead">Shop Name</p> -->
                <div class="list-group">
                    <a href="javascript:reqDirectPage('frmMenu', 'item', 'itemList', '')" class="list-group-item on_color1">천연비누</a>
                    <a href="javascript:reqDirectPage('frmMenu', 'item', 'itemList', '')" class="list-group-item on_color2">천연화장품</a>
                    <a href="javascript:reqDirectPage('frmMenu', 'item', 'itemList', '')" class="list-group-item on_color3">머리핀</a>
                </div>
                <form id="frmMenu" name="frmNav" method="POST">
                    <%@include file="/WEB-INF/views/include/hidden.jsp"%>
                </form>
            </div>