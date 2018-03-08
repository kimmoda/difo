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
                We will review your application to become a verified trendsetter in 48 hours and contact you
                accordingly.
            </p>
            <p class="content">
                While you wait for this confirmation email, you can begin uploading looks and editing your profile.
            </p>
            <div class="text-center margin-top-40">
                <a class="btn btn-primary"
                   href="<c:url value="/action/auth/dashboard" />">
                    My Dashboard
                </a>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <zion:uiComp name="stylistApply"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>
