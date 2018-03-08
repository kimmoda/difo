<%@ include file="/WEB-INF/jsp/include/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>zion</title>
<%@ include file="/WEB-INF/jsp/include/style.jsp"%>
</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Please Sign In</h3>
				</div>
				<div class="panel-body">
					<form role="form">
						<fieldset>
							<div class="form-group">
								<input id="username" class="form-control" placeholder="E-mail" name="email" type="email" autofocus>
							</div>
							<div class="form-group">
								<input id="password" class="form-control" placeholder="Password" name="password" type="password" value="">
							</div>
							<div class="checkbox">
								<label>
									<input name="remember" type="checkbox" value="Remember Me">Remember Me
								</label>
							</div>
							<!-- Change this to a button or input when using this as a form -->
							<a id="loginBt" href="#" class="btn btn-lg btn-success btn-block">Login</a>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/include/commonScript.jsp"%>
<%@ include file="/WEB-INF/jsp/include/adminScript.jsp"%>
<script type="text/javascript">
	var homePagePath = "<c:url value="/action/superadmin/home" />",
        loginPath = "<c:url value="/restws/auth/login" />"; 
</script>
<script src="<c:url value="/static/js/login.js" />"></script>
</body>
</html>