<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isUserAdmin="true">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.multiimagefeed.css</src>
            <src>/static/css/zion.common.feed.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="adminContents">
        <div class="multi-image-feed-container zion-form">
            <div class="page-title">Add a new post</div>
            <div class="form-group post-title">
                <input type="text" class="form-control feed-title" id="feed-title" placeholder="Title">
                <div id="title-error-indicator" class="zion-input-error-text zion-hide"></div>
            </div>
            <div class="form-group">
                <textarea class="form-control feed-description" id="feed-content"
                          placeholder="Describe your post, what are you wearing, what inspired you to create the post? On what occasion would you wear this look?"></textarea>
                <div id="desc-error-indicator" class="zion-input-error-text zion-hide"></div>
            </div>
            <div class="form-group tag-area">
                <label class="tag-area-title">Select some hashtags</label>
                <div class="tag-container">
                    <div id="tag-container"></div>
                    <hr class="tag-separate">
                    <ul id="custom-tag-container"></ul>
                    <div class="input-group custom-tag-input">
                        <input type="text" class="form-control tag-input" id="tag-input"
                               placeholder="Or add a tag.  Only numbers and letters are supported">
                        <span class="input-group-btn">
                            <button class="add-tag-button btn zion-btn-black" id="add-tag-button"
                                    type="button">ADD</button>
                        </span>
                    </div>
                </div>
                <div class="tag-no-select-error zion-input-error-text zion-hide">You must have at least one tag.</div>

                <div class="tag-error-indicator zion-input-error-text zion-hide"></div>
            </div>
            <div class="feed-image-container">

            </div>
            <div class="overcount-error-indicator zion-input-error-text zion-hide">
                The total number of images and videos cannot be more than 5.
            </div>
            <div class="float-menu">

                <div class="plus-button">
                    <i class="fa fa-plus" aria-hidden="true"></i>
                </div>
                <div class="action-buttons">
                    <div class="btn zion-btn-white add-text tool-btn">
                        <i class="fa fa-paragraph" aria-hidden="true"></i>
                    </div>
                    <div class="btn zion-btn-white add-photos tool-btn">
                        <i class="fa fa-picture-o" aria-hidden="true"></i>
                    </div>
                    <div class="btn zion-btn-white add-video tool-btn">
                        <i class="fa fa-youtube" aria-hidden="true"></i>
                    </div>
                </div>
            </div>
            <div class="preview-container">
                <div class="preview-error-indicator zion-input-error-text zion-hide">
                    You need to upload at least one image, before you preview.
                </div>
                <div class="preview-button">
                    Preview &#62;
                </div>
            </div>

            <div class="save-feed-container">
                <div id="server-side-error" class="zion-input-error-text zion-hide">
                </div>
                <%--<div class="delete-btn">--%>
                    <%--<a class="btn btn-link" id="previous-button" href="<c:url value="/action/auth/looks" />">--%>
                        <%--<span>Cancel</span>--%>
                    <%--</a>--%>
                <%--</div>--%>
                <button class="btn btn-danger" id="delete-btn">
                    <span>Delete</span>
                </button>
                <div class="save-btn">
                    <button class="btn btn-primary" id="save-button">
                        <i class="success-check zion-hide fa fa-lg fa-check" aria-hidden="true"></i>
                        <span>Publish</span>
                    </button>
                </div>
            </div>
        </div>

        <%-- add video url modal--%>

        <div class="modal fade" id="insert-video" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Insert youtube video</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal zion-form">
                            <div class="form-group">
                                <label for="video-url" class="col-sm-2 control-label">Youtube Video Url</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="video-url"
                                           placeholder="https://www.youtube.com/xxxxxxxxxx">
                                </div>
                                <div id="youtube-url-error" class="zion-input-error-text zion-hide">
                                    Invalid Youtube video url, please use the correct url format.
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary video-confirmed">
                            Insert
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <%-- Insert Url Modal--%>
        <div class="modal fade" id="insert-link" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="insert-link-title">Insert new Link</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label for="link-url" class="col-sm-2 control-label">Url</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="link-url" placeholder="http://">
                                </div>
                            </div>
                            <div class="form-group link-desc-container">
                                <label for="link-desc" class="col-sm-2 control-label">Title</label>
                                <div class="col-sm-10">
                                    <textarea type="text" class="form-control" id="link-desc">
                                    </textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary link-confirmed" data-dismiss="modal">
                            Insert
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- dialog window -->
        <div class="easy-modal zion-form" style="display:none;" modal-position="free">
            <div class="title">Add item</div>
            <div class="form-group">
                <div>
                    <input name="title" type="text" class="form-control" id="tag_title" placeholder="Tag">
                    <div class="zion-input-error-text zion-hide" id="empty-easypin-title-error">Please fill name</div>
                </div>
            </div>
            <div class="form-group">
                <div>
                    <input name="url" type="text" class="form-control" id="tag_url" placeholder="Url Link">
                    <div class="zion-input-error-text zion-hide" id="empty-easypin-url-error">Please fill link</div>
                    <div class="zion-input-error-text zion-hide" id="invalid-easypin-url-error">Please provide valid
                        link
                    </div>
                </div>
            </div>
            <div class="easypin-modal-btn">
                <a href="javascript:;" class="easypin-modal-cancel easy-cancel-dialog">Cancel</a>
                <input type="button" value="Add" class="easy-submit btn zion-btn-grey">
            </div>

        </div>

        <div style="display:none;" easypin-tpl>
            <popover>
                <div class="exPopoverContainer" shadow="true">
                    <div class="popBg">
                        <a href={[url]} target="_blank">
                            <p class="annotation-title">{[title]}</p>
                        </a>
                    </div>
                </div>
            </popover>
            <marker>
                <div class="marker2" style="height: 15px; width: 15px">
                </div>
            </marker>
        </div>
        <div id="myModal" class="modal fade zion-feed-modal zion-modal" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel"></div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script src="//widget.cloudinary.com/global/all.js" type="text/javascript"></script>
        <script type="text/javascript">
            var feedId = "${actionBean.feedId}",
                markIconURL = "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/icon/movebutton.png",
                markEditURL = "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/easypin-edit.png",
                markRemoveURL = "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/easypin-close.png";
        </script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/annotation.js</src>
            <src>/static/js/template/comments.js</src>
            <src>/static/js/lib/selectize.js</src>
            <src>/static/js/lib/jquery.auto-complete.js</src>
            <src>/static/js/lib/autosize.js</src>
            <src>/static/js/template/tagFilter.js</src>
            <src>/static/js/template/multiimagefeedcomponent.js</src>

        </pack:script>
        <zion:uiComp name="authaddmulti"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>