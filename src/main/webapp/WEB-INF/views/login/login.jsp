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
	function action(actionType) {
		switch (actionType) {
		case 1: // 목록
			break;
		case 2: // 상세, 인증
			var objParam = {};
			objParam.userid   = frmLogin.userid.value;
			objParam.password = frmLogin.password.value;
			
			$.ajax({
				type : "POST",
				url : "login/login.getLogin.do",
				dataType : "json",
				data : {
					"param" : JSON.stringify(objParam)
				},
				beforeSend : function(xhr) {
					// 전송 전 Code
				},
				success : function(result) {
					if (result.result == 'SUCCESS') {
						console.log('로그인 성공');
					} else {
						console.log('로그인 실패');
					}
				},
				error : function(error) {
					alert("Error 발생:" + error);
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

                <form id="frmLogin" method="post" action="javascript:action(2)" class="form-horizontal">
                <fieldset>
                    <legend>(스텔라) 로그인을 해야 하는 순간이예요. ^^</legend>
                    <div class="form-group">
                        <label for="userid" class="col-lg-2 control-label">회원&nbsp;&nbsp;ID</label>
                        <div class="col-lg-10">
                            <input type="text" name="userid" required autofocus placeholder="4~10자리" class="form-control"><br>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-lg-2 control-label">비밀번호</label>
                        <div class="col-lg-10">
                            <input type="password" name="password" required placeholder="영문,숫자 사용. 6~10자리" class="form-control"><br>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-10 col-lg-offset-2">
                            <input type="submit" value="로그인" class="btn btn-primary">
                            <input type="reset" class="btn btn-default" value="초기화">
                        </div>
                    </div>
                </fieldset>
                </form>

            </div>

        </div>

    </div>
    <!-- /.container -->
    
    <%@ include file="/WEB-INF/views/include/footer.jsp"%>
    
</body>
</html>