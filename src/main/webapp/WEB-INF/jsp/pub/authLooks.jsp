<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isUserAdmin="true" pageTitle="Add new looks">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.auth.looks.css</src>
            <src>/static/css/zion.pub.stylistLooks.css</src>
            <src>/static/css/zion.common.feed.css</src>
            <src>/static/css/zion.pub.auth.emptyLooks.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="adminContents">
        <div id="looks-status-filter" class="zion-looks-status-filter"></div>
        <section class="zion-stylist-content" id="stylist-content"></section>
        <div id="empty-looks-container">

        </div>
        <div id="termsModal" class="modal fade zion-modal" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel"></div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/myLooksFilter.js</src>
            <src>/static/js/template/feedcard.js</src>
            <src>/static/js/template/annotation.js</src>
            <src>/static/js/template/comments.js</src>
            <src>/static/js/template/looks.js</src>
            <src>/static/js/template/emptyLooksMessage.js</src>
            <src>/static/js/template/userTermsConditions.js</src>
        </pack:script>
        <script src="//widget.cloudinary.com/global/all.js" type="text/javascript"></script>
        <zion:uiComp name="authlooks"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>