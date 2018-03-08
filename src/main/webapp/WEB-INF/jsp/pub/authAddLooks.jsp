<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isUserAdmin="true">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.auth.addlooks.css</src>
            <src>/static/css/lib/selectize.css</src>
            <src>/static/css/zion.common.feed.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="adminContents">
        <div class="create-looks-title-container">
            <h2 class="create-looks-title">Add a look</h2>
            <p class="create-looks-subtitle">Follow these simple steps to publish your look.</p>
        </div>
        <div class="progress-bar-container">
            <div id="progressBar"></div>
        </div>
        <div class="row crater-container">
        <div class="text-center step-1">
            <div class="image-container hidden">
                <img id="photo-viewer" src="#" class="img-responsive center-block hidden pin"
                     easypin-id="pin-image"/>
            </div>
            <div class="change-photo-button-container hidden upload-button-container">

                <div id="change-photo-button" class="change-btn col-md-3 no-padding">
                    <a href="javascript:" class="choose-photo-button change-photo">
                        <i class="fa fa-camera" aria-hidden="true"></i>
                        <span>Change photo</span>
                    </a>
                </div>
                <div class="delete-btn col-md-3 col-md-offset-3">
                    <a href="javascript:" class="btn zion-btn-white" id="delete-button">Delete</a>
                </div>
                <div class="next-btn col-md-3">
                    <div class="btn zion-btn-black" id="next-button">Next step &#62;</div>
                </div>
            </div>
        </div>
        <div class="step-2 zion-hide absolute">
            <div class="thumbnail-container">
                <img id="look-thumbnail" src="" alt="look image preview">
            </div>
            <div class="feed-info-container zion-form">
                <div class="form-group">
                    <label for="feed-title" class="title">Give your look a name <span
                            class="zion-form-compulsory">*</span></label>
                    <input type="text" class="form-control feed-title" id="feed-title" placeholder="Title">
                    <div id="title-error-indicator" class="zion-input-error-text zion-hide"></div>
                </div>
                <div class="form-group">
                    <label for="feed-description" class="title">Tell us about your look <span
                            class="zion-form-compulsory">*</span></label>
                    <textarea class="form-control feed-description" id="feed-description"
                              placeholder="Describe your look, what are you wearing, what inspired you to create the look? On what occasion would you wear this look?"></textarea>
                    <div id="desc-error-indicator" class="zion-input-error-text zion-hide"></div>
                </div>
                <div class="form-group">
                    <label class="title">Select some hashtags <span
                            class="zion-form-compulsory">*</span></label>
                    <div class="tag-container">
                        <div id="tag-container"></div>
                        <hr class="tag-separate">
                        <ul id="custom-tag-container"></ul>
                    </div>
                    <div class="tag-no-select-error zion-input-error-text zion-hide">You must have at least one tag.</div>
                    <div class="input-group custom-tag-input">
                        <input type="text" class="form-control tag-input" id="tag-input"
                               placeholder="Or add a tag.  Only numbers and letters are supported">
                        <span class="input-group-btn">
                            <button class="add-tag-button btn zion-btn-black" id="add-tag-button"
                                    type="button">ADD</button>
                        </span>
                    </div>
                    <div class="tag-error-indicator zion-input-error-text zion-hide"></div>
                </div>
                <div class="save-feed-container row">
                    <div id="server-side-error" class="zion-input-error-text zion-hide">
                        internal server error occur, please try again
                    </div>
                    <div class="delete-btn col-md-4">
                        <button class="btn zion-btn-white" id="final-step-delete-btn">
                            <span>Delete</span>
                        </button>
                    </div>
                    <div class="cancel-btn col-md-4">
                        <button class="btn zion-btn-black" id="previous-button">
                            <span>Prev step</span>
                        </button>
                    </div>
                    <div class="save-btn col-md-4">
                        <button class="btn btn-primary" id="save-button">
                            <i class="success-check zion-hide fa fa-lg fa-check" aria-hidden="true"></i>
                            <span>Publish &#62;</span>
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
                    <input name="title" type="text" class="form-control" id="tag_title" placeholder="Brand name">
                    <div class="zion-input-error-text zion-hide" id="empty-easypin-title-error">Please fill name</div>
                </div>
            </div>
            <div class="form-group">
                <div>
                    <input name="url" type="text" class="form-control" id="tag_url" placeholder="Link to buy">
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
                <div class="marker2" style="height: 20px; width: 20px">
                </div>
            </marker>
        </div>

        <!-- popover -->
        <div style="display:none;" width="200" shadow="true" popover>
            <div style="width:100%;text-align:center;">{[title]}</div>
        </div>
        <div id="myModal" class="modal fade zion-feed-modal zion-modal" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel"></div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script src="//widget.cloudinary.com/global/all.js" type="text/javascript"></script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/lib/selectize.js</src>
            <src>/static/js/lib/jquery.auto-complete.js</src>
            <src>/static/js/template/annotation.js</src>
            <src>/static/js/template/comments.js</src>
            <src>/static/js/template/tagFilter.js</src>
        </pack:script>
        <zion:uiComp name="authaddlooks"></zion:uiComp>
        <script type="text/javascript">
            var feedId = "${actionBean.feedId}",
                markIconURL = "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/icon/movebutton.png",
                markEditURL = "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/easypin-edit.png",
                markRemoveURL = "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/easypin-close.png";
        </script>
    </stripes:layout-component>
</stripes:layout-render>