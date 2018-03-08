<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/admin/layout.jsp">
    <stripes:layout-component name="contents">
        <h1 class="page-header">Feeds</h1>
        <div class="row">
            <div class="col-md-6">
                <a href="#" data-action-bean-url="<c:url value="/action/superadmin/feed/add"/>" class="btn btn-success btn-xs zion-admin-action zion-hide"><i class="glyphicon glyphicon-plus"></i></a>
            </div>
            <div class="col-md-6">
                <select id="author-select" class="form-control">
                    <option>All</option>
                </select>
            </div>
        </div>
        <div class="row">
            <ul id="feed-list" class="list-group">
            </ul>
            <button id="show-more" type="button" class="btn btn-success">Show More</button>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
		<script src="<c:url value="/static/js/feeds.js" />"></script>
		<script type="text/javascript">
		    var listFeedsUrl = "<c:url value="/restws/feed/v1/list"/>",
                listUsersUrl = "<c:url value="/restws/user/list"/>",
		        deleteUrl = "<c:url value="/restws/feed/del"/>",
		        updateSysTags = "<c:url value="/restws/feed/admin/update/systags"/>",
		        getFeedUrl = "<c:url value="/action/superadmin/feed/add"/>";
                fileClouldName = "${actionBean.fileClouldName}";
		</script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/util/imageUtils.js</src>
            <src>/static/js/template/superAdminFeedList.js</src>
        </pack:script>
    </stripes:layout-component>
</stripes:layout-render>
