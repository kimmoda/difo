<%@ include file="/WEB-INF/jsp/include/tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>zion</title>
    <%@ include file="/WEB-INF/jsp/include/style.jsp"%>
</head>
<body>
<div id="loginTable" class="row">
    <div class="col-xs-6 col-md-4"></div>
    <div class="col-xs-6 col-md-4">
        <h1>Change Password</h1>
        <div id="warning-message" class="alert alert-danger zion-hide" role="alert"></div>
        <form class="form-horizontal">
            <div class="form-group">
                <div class="col-md-12">
                    <input id="old_password" class="form-control" type="password" placeholder="Old Password"></input>
                </div>
                <div class="col-md-12">
                    <input id="new_password" class="form-control" type="password" placeholder="New Password"></input>
                </div>
                <div class="col-md-12">
                    <input id="new_password2" class="form-control" type="password" placeholder="Confirm Password"></input>
                </div>
            </div>
            <button id="changePwdBtn" type="button" class="btn btn-primary">Change Password</button>
        </form>
    </div>
    <div class="col-xs-6 col-md-4"></div>
</div>
<%@ include file="/WEB-INF/jsp/include/commonScript.jsp"%>
<script type="text/javascript">
    var homePagePath = "<c:url value="/action/superadmin/home" />", changePwdPath = "<c:url value="/restws/auth/update/password" />";
</script>
<script src="<c:url value="/static/js/changePassword.js" />"></script>
</body>
</html>