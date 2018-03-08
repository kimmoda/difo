<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/admin/layout.jsp">
    <stripes:layout-component name="contents">
        <h1 class="page-header">Campaigns</h1>
        <div class="row">
            <div class="col-md-6">
                <a href="#" data-action-bean-url="<c:url value="/action/superadmin/campaign/add"/>" class="btn btn-success btn-xs zion-admin-action"><i class="glyphicon glyphicon-plus"></i></a>
            </div>
        </div>
        <div class="row">
            <ul id="Campaign-list" class="list-group">
            </ul>
            <button id="show-more" type="button" class="btn btn-success">Show More</button>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script src="<c:url value="/static/js/superAdminCampaign.js" />"></script>
        <script type="text/javascript">
            var listCampaignUrl = "<c:url value="/restws/task/promotion/v1/list"/>",
                deleteUrl = "<c:url value="/restws/task/promotion/admin/del"/>",
                createOrUpdateCampaignUrl = "<c:url value="/action/superadmin/campaign/add"/>";
        </script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/superAdminCampaignList.js</src>
        </pack:script>
    </stripes:layout-component>
</stripes:layout-render>
