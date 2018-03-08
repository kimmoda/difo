<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="singleTrendsetter" isHideHeaderMenu="true" isStylehubPreference="false">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.stylistLooks.css</src>
            <src>/static/css/zion.pub.singleTrendsetter.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <section class="single-trendsetter-body">
            <section class="single-trendsetter-container" id="single-trendsetter-summary">
                <button id="share-user-button" class="share-user-btn btn btn-default"><i class="fa fa-share-alt" aria-hidden="true"></i></button>
                <div class="user-share-container">
                    <div id="zion-user-social-share-container" class="zion-social-share-plugin-container" data-share-url="{{shortUrl}}" data-share-title="{{title}}" data-share-description="{{content}}" data-share-image="{{photo.url}}"></div>
                </div>
            </section>
            <section class="single-trendsetter-look" id="single-trendsetter-look"></section>
        </section>
        <section class="single-trendsetter-footer">
            <div class="container">
                <a href="<c:url value="/"/>">Powered by <c:out value="${appBrandName}"/></a>
            </div>
        </section>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script type="text/javascript">
            var userID = "${actionBean.userId}",
                listFeedsUrl = "<c:url value="/restws/feed/v1/list"/>",
                getUserUrl = "<c:url value="/restws/user/v1/view?id=${actionBean.userId}"/>",
                getFeedUrl = "<c:url value="/restws/feed/v1/view"/>"
        </script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/looks.js</src>
            <src>/static/js/template/comments.js</src>
            <src>/static/js/template/feedcard.js</src>
            <src>/static/js/template/annotation.js</src>
            <src>/static/js/template/singleTrendsetterSummary.js</src>
            <src>/static/js/components/pub/stylist.looks.js</src>
        </pack:script>
        <zion:uiComp name="singleTrendsetter"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>