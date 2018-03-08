<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/admin/layout.jsp">
    <stripes:layout-component name="headMeta">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="row">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Change user name
                </div>
                <div class="panel-body">
                    <label>Old user name:</label>
                    <input id="oldUserName" name="oldUserName" type="text"/>
                    <label>New user name:</label>
                    <input id="newUserName" name="newUserName" type="text"/>
                    <button id="update-username-button" class="btn btn-default">Update</button>
                    <div id="update-username-message"></div>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    DB upgrading
                </div>
                <div class="panel-body">
                    <button id="upgrade-db-button" class="btn btn-default">Upgrade</button>
                    <div id="upgrade-message"></div>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    User statistic data system upgrading
                </div>
                <div class="panel-body">
                    <button id="upgrade-shared-button" class="btn btn-default">Update Shared count</button>
                    <button id="upgrade-viewed-button" class="btn btn-default">Update viewed count</button>
                    <button id="upgrade-short-url-button" class="btn btn-default">Update short url clicked count</button>
                    <button id="upgrade-like-button" class="btn btn-default">Update liked count</button>
                    <button id="upgrade-all-data-button" class="btn btn-default">Update all (not include fans)</button>
                    <button id="upgrade-fans-count-button" class="btn btn-default">Update fans count</button>
                    <div id="upgrade-data-message"></div>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Generate feed comments
                </div>
                <div class="panel-body">
                    Start day: <input type="text" id="start-day"/> end day: <input type="text" id="end-day"/>
                    <button id="generate-feed-comments-button" class="btn btn-default">Generate</button>
                    <div id="generate-feed-comments"></div>
                </div>
            </div>
            
             <div class="panel panel-primary">
                <div class="panel-heading">
                    Update predefined task type
                </div>
                <div class="panel-body">
                    <button id="update-predefined-task-type" class="btn btn-default">Update</button>
                    <div id="predefined-task-json"></div>
                </div>
            </div>
            
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Update common configuration
                </div>
                <div class="panel-body">
                    <button id="update-common-config" class="btn btn-default">Update</button>
                    <div id="common-config-json"></div>
                </div>
            </div>
            
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Update user account
                </div>
                <div class="panel-body">
                    Username: <input type="text" id="acc-username"/> Income: <input type="text" id="acc-income"/> Expense: <input type="text" id="acc-expense"/>
                    <button id="acc-update-button" class="btn btn-default">update</button>
                    <div id="acc-update-comments"></div>
                </div>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script src="<c:url value="/static/js/upgrade.js" />"></script>
		<script type="text/javascript">
		    var upgradeDbUrl = "<c:url value="/restws/upgrade/db"/>",
		        upgradeSystemDataStatUrl = "<c:url value="/restws/upgrade/system/data/stats"/>",
		        generateFeedCommentsUrl = "<c:url value="/restws/upgrade/generate/feed/comments"/>",
		        updateTaskTypeUrl = "<c:url value="/restws/config/tasktype/update"/>",
		        updateCommonConfigUrl = "<c:url value="/restws/config/common/update"/>",
		        updateUserNameUrl = "<c:url value="/restws/auth/update/username"/>",
		        updateUserAcc = "<c:url value="/restws/account/admin/update"/>";
		</script>
    </stripes:layout-component>
</stripes:layout-render>