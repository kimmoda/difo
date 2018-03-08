<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isHideHeaderMenu="true" isStylehubPreference="false">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.stylehub.css</src>
            <src>/static/css/zion.common.feed.css</src>
            <src>/static/css/zion.pub.singleLook.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="single-feed-container" id="single-feed">
        </div>
        <div class="single-feed-footer">
            <div class="container">
                <a href="<c:url value="/"/>">Powered by <c:out value="${appBrandName}"/></a>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/singleLook.js</src>
        </pack:script>
        <zion:uiComp name="singleLook"></zion:uiComp>
        <script type="text/javascript">
            var feedId = "${actionBean.feedId}",
                stylistUrl = "<c:url value="/action/stylist/view"/>";
        </script>
    </stripes:layout-component>
</stripes:layout-render>