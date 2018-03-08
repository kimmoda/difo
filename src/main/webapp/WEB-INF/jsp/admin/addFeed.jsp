<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/admin/layout.jsp">
    <stripes:layout-component name="headMeta">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/zion.admin.feedmanager.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/lib/selectize.css" />">
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/lib/jquery.auto-complete.css" />">
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Feeds Management</h1>
            </div>
        </div>
        <div class="row">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Feed
                    <a href="#" data-action-bean-url="<c:url value="/action/superadmin/feed/add"/>"
                       class="btn btn-success btn-xs zion-admin-action"><i
                            class="glyphicon glyphicon-plus"></i></a>
                </div>
                <div class="panel-body">
                    <div class="col-md-6">
                        <input type="hidden" id="feedId"/>
                        <div id="error-banner" class="alert alert-danger zion-hide" role="alert"></div>
                        <div class="form-group">
                            <label>Title</label>
                            <label class="required">*</label>
                            <input id="title" class="form-control" placeholder="Title"/>
                        </div>
                        <div class="form-group">
                            <label>Author</label>
                            <label class="required">*</label>
                            <select id="author-select" class="form-control">
                                <option></option>
                            </select>
                        </div>
                        <div id="photo-container" class="form-group">
                            <label>Photo</label>
                            <label class="required">*</label>
                            <input id="photo-uploader" type="file" placeholder="Photo"/>
                        </div>
                        <div id="photo-viewer" class="form-group"></div>
                            <%--<div class="change-photo-container zion-hide">--%>
                            <%--<a href="javascript:;" class="file btn btn-default btn-block">CHANGE PHOTO--%>
                            <%--<input type="file" name="" id="changePhoto" accept="image/*">--%>
                            <%--</a>--%>
                            <%--</div>--%>

                        <div class="form-group">
                            <label>Content</label>
                            <textarea id="content" class="form-control"></textarea>
                        </div>
                        <div class="form-group">
                            <label>Tags</label>
                            <input type="text" id="feed-tags">
                        </div>
                        <div class="form-group">
                            <label>Custom tags</label>
                            <div class="tag-container">
                                <ul id="custom-tag-container"></ul>
                            </div>
                            <div class="tag-no-select-error zion-input-error-text zion-hide">You must have at least one tag.</div>
                            <div class="input-group custom-tag-input">
                                <input type="text" class="form-control tag-input" id="tag-input" placeholder="Add a tag.  Only numbers and letters are supported">
                                <span class="input-group-btn">
                                    <button class="add-tag-button btn zion-btn-black" id="add-tag-button" type="button">ADD</button>
                                </span>
                            </div>
                            <div class="tag-error-indicator zion-input-error-text zion-hide"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <button id="save-feed-button" class="btn btn-default">Save</button>
                <i id="action-throbber" class="fa fa-spinner fa-spin zion-hide" aria-hidden="true"></i>
            </div>
        </div>
        <!-- dialog window -->
        <div class="easy-modal zion-form" style="display:none;" modal-position="free">
            <div class="form-group">
                <label class="control-label">Title</label>
                <div>
                    <input name="title" type="text" class="form-control" id="tag_title" placeholder="Title">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label">Url</label>
                <div>
                    <input name="url" type="text" class="form-control" id="tag_url" placeholder="Url">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label">Shop tag ID</label>
                <div>
                    <input name="shoptagID" type="text" class="form-control" id="tag_shoptagID" placeholder="Shop Tag">
                </div>
            </div>
            <input type="button" value="Save" class="easy-submit">
        </div>

        <!-- popover -->
        <div style="display:none;" width="200" shadow="true" popover>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
        <script src="<c:url value="/static/js/lib/jquery.easypin.js" />"></script>
        <script src="<c:url value="/static/js/lib/autosize.js" />"></script>
        <script src="<c:url value="/static/js/lib/selectize.js" />"></script>
        <script src="<c:url value="/static/js/lib/jquery.auto-complete.js" />"></script>
        <script src="<c:url value="/static/js/feed.js" />"></script>
        <script src="<c:url value="/static/js/util/zionStringUtil.js" />"></script>
        <script src="<c:url value="/static/js/service/validationService.js" />"></script>
        <script type="text/javascript">
            var createFeedUrl = "<c:url value="/restws/feed/admin/create"/>",
                uploadPhotoUrl = "<c:url value="/restws/feed/v1/photo/upload"/>",
                feedId = "${actionBean.id}",
                updateFeedUrl = "<c:url value="/restws/feed/admin/update"/>",
                getFeedUrl = "<c:url value="/restws/feed/v1/view"/>",
                getTagsUrl = "<c:url value="/restws/tag/v1/pstyle/shpre/search"/>",
                getCustomTagsUrl = "<c:url value="/restws/tag/v1/pstyle/shuser/search"/>",
                listUsersUrl = "<c:url value="/restws/user/list"/>",
                markIconURL = "<c:url value="/static/img/marker/marker2.png"/>",
                markEditURL = "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/easypin-edit.png",
                markRemoveURL = "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/easypin-close.png";
        </script>
    </stripes:layout-component>
</stripes:layout-render>
