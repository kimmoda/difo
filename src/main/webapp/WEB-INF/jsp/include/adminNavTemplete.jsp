<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="<c:url value="/action/superadmin/home"/>">ZION Admin</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">
        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-messages">
                <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                </li>
                <li><a class="zion-admin-action" href="#" data-action-bean-url="<c:url value="/action/superadmin/changepassword"/>"><i class="fa fa-gear fa-fw"></i> Change Password</a>
                </li>
                <li class="divider"></li>
                <li><a href="#" id="logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                </li>
            </ul>
        </li>
    </ul>
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li class="sidebar-search">
                    <div class="input-group custom-search-form">
                        <input type="text" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">
                                <i class="fa fa-search"></i>
                            </button>
                        </span>
                    </div>
                </li>
                <li>
                    <a class="zion-admin-action" href="#" data-action-bean-url="<c:url value="/action/superadmin/home"/>"><i class="fa fa-edit fa-fw"></i> User</a>
                </li>
                <li>
                    <a class="zion-admin-action" href="#" data-action-bean-url="<c:url value="/action/superadmin/feed"/>"><i class="fa fa-edit fa-fw"></i> Feeds</a>
                </li>
                <li>
                    <a class="zion-admin-action" href="#" data-action-bean-url="<c:url value="/action/superadmin/campaign"/>"><i class="fa fa-edit fa-fw"></i> Campaign</a>
                </li>
                <li>
                    <a class="zion-admin-action" href="#" data-action-bean-url="<c:url value="/action/superadmin/upgrade"/>"><i class="fa fa-edit fa-fw"></i> Upgrade</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type="application/javascript">
    jQuery.noConflict();
    var loginPath = "<c:url value="/action/superadmin/login"/>";
    (function ($) {
        $(document).ready(function () {
            $('#logout').on('click', function (e) {
                e.preventDefault();
                localStorage.removeItem('zion-Jwt-Token');
                window.location.href = loginPath;
            });
        });
    })(jQuery);
</script>