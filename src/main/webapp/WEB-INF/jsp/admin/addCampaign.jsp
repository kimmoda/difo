<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/admin/layout.jsp">
    <stripes:layout-component name="contents">
        <div class="row">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Create or Update Campaign
                    <a href="#" data-action-bean-url="<c:url value="/action/superadmin/campaign/add"/>" class="btn btn-success btn-xs zion-admin-action"><i class="glyphicon glyphicon-plus"></i></a>
                </div>
                <div class="panel-body">
                    <div class="col-md-6">
                        <div id="error-banner" class="alert alert-danger zion-hide" role="alert"></div>
                        <input type="hidden" id="taskId"/>
                        <div class="form-group">
                            <label>Title</label>
                            <label class="required">*</label>
                            <input id="title" class="form-control" placeholder="Title"/>
                        </div>
                        <div class="form-group">
                            <label>Author</label>
                            <label class="required">*</label>
                            <input id="authorname" class="form-control" placeholder="username"/>
                        </div>
                        <div class="form-group">
                            <label>URL</label>
                            <label class="required">*</label>
                            <input id="post-url" class="form-control" placeholder="url"/>
                        </div>
                        <div class="form-group">
                            <label>Campaign Objective</label>
                            <label class="required">*</label>
                            <select id="campaign-objective-select" class="form-control">
                                <option></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Description</label>
                            <label class="required">*</label>
                            <textarea id="content" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <button id="save-task-button" class="btn btn-default">Save</button>
                <i id="action-throbber" class="fa fa-spinner fa-spin zion-hide" aria-hidden="true"></i>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script src="<c:url value="/static/js/addCampaign.js" />"></script>
        <script type="text/javascript">
            var createOrUpdateTaskUrl = "<c:url value="/restws/task/promotion/admin/update"/>",
                updatedTaskId = "${actionBean.id}",
                getTaskTypeUrl = "<c:url value="/restws/task/type/v1/createcampaign"/>",
                getTaskUrl = "<c:url value="/restws/task/promotion/v1/view"/>";
        </script>
    </stripes:layout-component>
</stripes:layout-render>

