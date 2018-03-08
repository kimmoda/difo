<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/admin/layout.jsp">
    <stripes:layout-component name="headMeta">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/zion.admin.home.css" />">
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">User Management</h1>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-md-3">
                <label class="checkbox-inline zion-header-check">
                    <input id="pending" name="roles" type="checkbox" value="pending">Pending
                </label>
                <label>
                    <button id="export-user-csv">Download all user as csv</button>
                    <button id="export-trendsetter-user-csv">Download trendsetter user as csv</button>
                </label>
            </div>
            <div class="form-group col-md-9">
                <label class="checkbox-inline zion-header-check">
                    <input id="trendsetter" name="rolesType" type="checkbox" value="TRENDSETTER">Trendsetter
                </label>
                <label class="checkbox-inline zion-header-check">
                    <input id="stylist" name="rolesType" type="checkbox" value="STYLIST">Stylist
                </label>
                <label class="checkbox-inline zion-header-check">
                    <input id="designer" name="rolesType" type="checkbox" value="DESIGNER">Designer
                </label>
                <label class="checkbox-inline zion-header-check">
                    <input id="influencer" name="rolesType" type="checkbox" value="INFLUENCER">Influencer
                </label>
                <label class="checkbox-inline zion-header-check">
                    <input id="puppet" name="rolesType" type="checkbox" value="PUPPET">Puppet
                </label>
            </div>

        </div>
        <div id="add-new-user" class="btn btn-default">
            <i class="fa fa-plus"></i>
        </div>
        <table id="user-list-table" class="display data-table" width="100%"></table>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
        <script src="<c:url value="/static/js/home.js" />"></script>
        <script type="text/javascript">
            var loginPath = "<c:url value="/action/superadmin/login"/>",
                listUsersUrl = "<c:url value="/restws/user/list"/>";
            var updatePath = "<c:url value="/action/superadmin/user/add"/>",
                pendingStylistUrl = "<c:url value="/restws/user/registration/list?status=PENDING"/>";
        </script>
    </stripes:layout-component>
</stripes:layout-render>
