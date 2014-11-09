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
                <ul>
                    <li>GET, POST만</li>
                    <li>Robots.txt 제어</li>
                    <li>회원정보 변경 전 비밀번호 요청</li>
                    <li>주요 정보 암호화</li> <!-- SHA1(128bit) -->
                    <li>에러페이지 Redirection</li>
                    <li>아이핀 연결</li> <!-- 아이디 확인, 비밀번호 변경 -->
                    <li>휴대폰 인증 연결</li>
                    <li>CAPTCHA</li> <!-- 회원가입, 게시판 글 입력 -->
                    <li>신고 후 전자결제 연결</li>
                </ul>
            </div>