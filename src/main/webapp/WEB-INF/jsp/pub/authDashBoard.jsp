<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isUserAdmin="true">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.auth.dashboard.css</src>
            <src>/static/css/zion.common.feed.css</src>
            <src>/static/css/zion.pub.auth.emptyLooks.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="adminContents">
        <div class="row">
            <div id="feed-social-share-container" class="col-sm-12">
                <div class="col-sm-4">
                    <img class="look-feed-icon" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/admin/lookfeed.jpg" alt="STYLEHUB looks feed">
                </div>
                <div id="social-page-share-container">
                    <div class="col-sm-8">
                        <span class="title">Share your look feed</span>
                        <p>Increase sales, commissions, and engagement rate. Copy the TinyURL to share your look feed page on your Instagram bio and social media channels.</p>
                        <p><a href="<c:url value="/shopmyfeed" />" title="stylehub - shop my feed"> >> Learn more << </a></p>
                        <button class="btn btn-primary look-feed-bt">View Look Feed</button>
                        <div id="share-look-feed-plugin-container" class="zion-social-share-plugin-container"></div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="overview-container">
            <div class="row overview-data-container">
                <div class="col-md-3 col-xs-6">
                    <div class="row">
                        <div class="col-xs-3 overview-icon">
                            <i class="fa fa-heart" aria-hidden="true"></i>
                        </div>
                        <div class="col-xs-9 overview-text">
                            <span id="like-count" class="overview-count"></span>
                            <span class="overview-label">Likes</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-xs-6 border-left">
                    <div class="row">
                        <div class="col-xs-3 overview-icon">
                            <i class="fa fa-share-alt" aria-hidden="true"></i>
                        </div>
                        <div class="col-xs-9 overview-text">
                            <span id="shared-count" class="overview-count"></span>
                            <span class="overview-label">Shared</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-xs-6 border-left">
                    <div class="row">
                        <div class="col-xs-3 overview-icon">
                            <i class="fa fa-eye" aria-hidden="true"></i>
                        </div>
                        <div class="col-xs-9 overview-text">
                            <span id="viewed-count" class="overview-count"></span>
                            <span class="overview-label">Viewed</span>
                        </div>
                    </div>

                </div>
                <div class="col-md-3 col-xs-6 border-left">
                    <div class="row">
                        <div class="col-xs-3 overview-icon">
                            <i class="fa fa-hand-pointer-o" aria-hidden="true"></i>
                        </div>
                        <div class="col-xs-9 overview-text">
                            <span id="clicked-count" class="overview-count"></span>
                            <span class="overview-label">Social media visits</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="my-last-look-container zion-looks-status-filter">
            <div class="row">
                <div class="col-sm-6">
                    <span class="title" id="my-latest-looks">
                        My latest looks
                    </span>
                </div>
                <div class="col-sm-6 right-content">

                </div>
            </div>
            <div id="empty-looks-container">

            </div>
            <div id="feed-list" class="grid">
                <div class="grid-col-sizer"></div>
                <div class="grid-gutter-sizer"></div>
            </div>
            <div class="page-load-status">
                <div class="spinner">
                    <div class="bounce1"></div>
                    <div class="bounce2"></div>
                    <div class="bounce3"></div>
                </div>
            </div>
            <div id="myModal" class="modal fade zion-feed-modal zion-modal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel"></div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script src="//widget.cloudinary.com/global/all.js" type="text/javascript"></script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/feedcard.js</src>
            <src>/static/js/template/annotation.js</src>
            <src>/static/js/template/comments.js</src>
            <src>/static/js/template/emptyLooksMessage.js</src>
            <src>/static/js/template/userTermsConditions.js</src>
        </pack:script>
        <zion:uiComp name="authDashboard"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>