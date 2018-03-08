<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isUserAdmin="true">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.auth.looks.css</src>
            <src>/static/css/zion.pub.stylistLooks.css</src>
            <src>/static/css/zion.common.feed.css</src>
            <src>/static/css/zion.pub.auth.userLikes.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="adminContents">
        <div class="zion-stylist-complete-container zion-hide">
            <p class="title">
                LIKE SOME LOOKS
            </p>
            <p class="content">
                There are lots of looks on STYLEHUB. Start browsing our inspiration feed and start liking looks that inspire you. We'll save all your looks for you in this space, so you can come back to them at anytime.
            </p>
            <div class="empty-url">
                <div class="btn btn-link find-fashion-button">
                    <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                    Get Inspired Now
                    <i class="fa fa-angle-double-left" aria-hidden="true"></i>
                </div>
            </div>
        </div>
        <section class="zion-stylist-content" id="stylist-content"></section>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script type="text/javascript">
            var listFeedsUrl = "<c:url value="/restws/feed/v1/list"/>",
                getFeedUrl = "<c:url value="/restws/feed/v1/view"/>",
                stylistUrl = "<c:url value="/action/stylist/view"/>";
        </script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/components/pub/stylist.looks.js</src>
            <src>/static/js/components/pub/authlookslike/ui.js</src>
            <src>/static/js/template/feedcard.js</src>
            <src>/static/js/template/looks.js</src>
            <src>/static/js/template/annotation.js</src>
            <src>/static/js/template/comments.js</src>
        </pack:script>
    </stripes:layout-component>
</stripes:layout-render>