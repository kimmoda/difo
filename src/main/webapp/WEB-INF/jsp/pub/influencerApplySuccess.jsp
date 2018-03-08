<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Apply Trendsetters">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.stylistApply.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div id="thankyou-container" class="zion-stylist-complete-container">
            <p class="title">
                THANK YOU
            </p>
            <p class="content">
                We will review your application and in less than 48 hours we will approve you as a verified influencer.
            </p>
            <p class="content">
                While you wait, why do not you explore some influencers' campaigns?
            </p>
            <p class="content">
                <a href="<c:url value="/campaign" />">
                    >> EXPLORE <<
                </a>
            </p>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <zion:uiComp name="influencerApply"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>
