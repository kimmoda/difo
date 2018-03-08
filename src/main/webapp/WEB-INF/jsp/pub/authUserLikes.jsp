<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isUserAdmin="true">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.stylistList.css</src>
            <src>/static/css/zion.pub.auth.userLikes.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="adminContents">
        <div class="zion-stylist-complete-container zion-hide">
            <p class="title">
                YOUR FASHION TRENDSETTERS
            </p>
            <p class="content">
                Browse through our fashion community of trendsetters, fashionistas, professional stylists and fashion experts. Browse through their profiles, read their bios, and peruse their latest collections.
            </p>
            <p class="content">
                Looking for a fashion fix? Find and contact a personal stylist for help.
            </p>
            <p class="content">
                Once you have browsed some of your favorite styles and looks, your "likes" will appear here.
            </p>
            <div class="empty-url">
                <div class="btn btn-link find-fashion-button">
                    <i class="fa fa-angle-double-right" aria-hidden="true"></i>
                    Find Fashion Trendsetters
                    <i class="fa fa-angle-double-left" aria-hidden="true"></i>
                </div>
            </div>
        </div>

        <section class="zion-stylist-content" id="stylist-content"></section>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script type="text/javascript">
            var listUsersUrl = "<c:url value="/restws/user/v1/list"/>",
                stylistUrl = "<c:url value="/action/stylist/view"/>";
        </script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/components/pub/stylist.list.js</src>
            <src>/static/js/components/pub/authuserslike/ui.js</src>
            <src>/static/js/template/stylistcard.js</src>
        </pack:script>
    </stripes:layout-component>
</stripes:layout-render>