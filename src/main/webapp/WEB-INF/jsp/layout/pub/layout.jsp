<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<stripes:layout-definition>
    <!DOCTYPE html>
    <html>
    <head>
        <c:set var="appBrandName" scope="request" value="${actionBean.appBrandName}"/>
        <c:set var="appLogoUrl" scope="request" value="${actionBean.appLogoUrl}"/>
        <c:set var="appSupportEmail" scope="request" value="${actionBean.appSupportEmail}"/>
        <!--  copyright THREE LEO LTD  -->
        <c:if test="${isStylehubPreference == null || isStylehubPreference}">
            <title>
                <c:choose>
                    <c:when test="${empty pageTitle }"><c:out value="${appBrandName}"/></c:when>
                    <c:otherwise>${pageTitle} | <c:out value="${appBrandName}"/></c:otherwise>
                </c:choose>
            </title>
            <link rel="icon" href="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/favicon.png">
        </c:if>
            <%-- Google Site Verification --%>
        <meta name="google-site-verification" content="NWN64mB_puFwXdLfleLyNYELTaxiNBr_H0DtSZ90kPI"/>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description"
              content="Help promote and get promoted by fellow fashion &amp; lifestyle influencers. Increase social engagement, followers, and visibility online while earning rewards.">
        <meta name="keywords"
              content="fashion bloggers, fashion designers, fashion stylists, personal stylists, women clothes, street style, buy ready-to-shop looks, fashion directory, fashion trendsetters, fashion style, fashion looks, discover fashion influencers">
        <meta name="author" content="THREE LEO Ltd">
        <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1">
        <link rel="alternate" href="${actionBean.webConfig.hostDomain}${actionBean.webConfig.contextPath}" hreflang="en-nz"/>

        <c:choose>
            <c:when test="${isStylehubPreference == null || isStylehubPreference}">
                <zion:shareMeta isStylehubSpecific="false"/>
            </c:when>
            <c:otherwise>
                <zion:shareMeta isStylehubSpecific="true"/>
            </c:otherwise>
        </c:choose>

            <%-- google web fonts --%>
        <link rel='stylesheet'
              href='https://fonts.googleapis.com/css?family=Lato%3A300%7CMontserrat%3A400%2C700%2C300%7CPlayfair+Display%3A400italic%7CPermanent+Marker%3A400&#038;subset=latin&#038;ver=4.5.9'
              type='text/css' media='all'/>
        <link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet'>
        <link href="https://fonts.googleapis.com/css?family=Playfair+Display" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans"/>
            <%-- font awesome --%>
        <script src="https://use.fontawesome.com/0db7afb289.js"></script>

        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/lib/bootstrap.css</src>
            <src>/static/css/lib/bootstrap-datepicker.css</src>
            <src>/static/css/lib/star-rating.css</src>
            <src>/static/css/lib/toastr.css</src>
            <src>/static/css/lib/animate.css</src>
            <src>/static/css/lib/jquery.auto-complete.css</src>
            <src>/static/css/zion.common.css</src>
            <src>/static/css/zion.common.button.css</src>
            <src>/static/css/zion.common.form.css</src>
            <src>/static/css/zion.common.modal.css</src>
            <src>/static/css/zion.common.panel.css</src>
            <src>/static/css/zion.common.general.page.css</src>
            <src>/static/css/zion.common.createCampaign.css</src>
            <src>/static/css/zion.pub.admin.layout.css</src>
            <src>/static/css/zion.color.css</src>
            <src>/static/css/zion.pub.head.menu.css</src>
            <src>/static/css/zion.pub.footer.css</src>
            <src>/static/css/lib/waitMe.css</src>
            <src>/static/css/zion.responsive.css</src>
            <src>/static/css/zion.pub.login.css</src>
            <src>/static/css/zion.social.share.plugin.css</src>
            <src>/static/css/zion.loading.plugin.css</src>
        </pack:style>

        <stripes:layout-component name="headMeta"/>

        <!-- Google Tag Manager -->
        <script>(function (w, d, s, l, i) {
            w[l] = w[l] || [];
            w[l].push({'gtm.start': new Date().getTime(), event: 'gtm.js'});
            var f = d.getElementsByTagName(s)[0], j = d.createElement(s), dl = l != 'dataLayer' ? '&l=' + l : '';
            j.async = true;
            j.src = 'https://www.googletagmanager.com/gtm.js?id=' + i + dl;
            f.parentNode.insertBefore(j, f);
        })(window, document, 'script', 'dataLayer', 'GTM-5SHCZ56');</script>
        <!-- End Google Tag Manager -->
    </head>
    <body class="${bg == 'white-bg' || isUserAdmin ? 'white-bg' : 'grey-extra-lightest'}">
    <div id="grecaptcha-id" class="zion-grecaptcha"></div>
    <div class="zion-wrapper">
        <c:if test="${!isHideHeaderMenu}">
            <%@ include file="/WEB-INF/jsp/pub/pubLogin.jsp" %>
            <%@ include file="/WEB-INF/jsp/layout/pub/menu.jsp" %>
        </c:if>
        <stripes:layout-component name="header"/>
        <c:if test="${isUserAdmin}">
            <div class="admin-header-background">
                <div class="patch">

                </div>
            </div>
        </c:if>
        <div class="zion-content container">
            <stripes:layout-component name="contents"/>
            <c:if test="${isUserAdmin}">
                <div class="row zion-admin-container">
                    <div class="col-sm-3 no-padding">
                        <div id="admin-side-menu-container" class="zion-desktop-visible"></div>
                    </div>
                    <div class="col-sm-9">
                        <div id="zion-admin-header-container" class="header-menu zion-desktop-visible zion-mobile-visible">
                        </div>
                        <div class="dashboard-show-more-container">
                            <ul class="dashboard-show-more-list">

                            </ul>
                        </div>
                        <stripes:layout-component name="adminContents"/>
                    </div>
                </div>
            </c:if>
        </div>

        <%@ include file="/WEB-INF/jsp/layout/pub/footer.jsp" %>
    </div>
    <div id="create_campaign" class="modal fade zion-feed-modal zion-modal" tabindex="-1" role="dialog"></div>

    <div id="message_modal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title message_title"></h4>
                </div>
                <div class="modal-body">
                    <p class="message_content"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary button_ok">OK</button>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        <%-- DO NOT REMOVE webConfig GLOBAL CONFIG --%>
        var webConfig = <jsp:include page="/action/webconfig"></jsp:include>;
        webConfig.activeAction = "${actionBean.action}";
    </script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

    <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
        <src>/static/js/lib/jquery-3.2.1.js</src>
        <src>/static/js/lib/handlebars.js</src>
        <src>/static/js/lib/xss.js</src>
        <src>/static/js/lib/bootstrap.js</src>
        <src>/static/js/lib/url.js</src>
        <src>/static/js/lib/toastr.js</src>
        <src>/static/js/lib/addel.jquery.js</src>
        <src>/static/js/lib/autosize.js</src>
        <src>/static/js/lib/jquery.easing.js</src>
        <src>/static/js/lib/jquery.easypin.js</src>
        <src>/static/js/lib/imageload.js</src>
        <src>/static/js/lib/masonry-grid-layout.js</src>
        <src>/static/js/lib/bootstrap-datepicker.js</src>
        <src>/static/js/lib/star-rating.js</src>
        <src>/static/js/lib/waitMe.js</src>
        <src>/static/js/lib/jquery.ba-throttle-debounce.js</src>
        <src>/static/js/lib/raphael.js</src>
        <src>/static/js/lib/progressStep.js</src>
        <%-- zion js --%>
        <src>/static/js/plugin/zion.feed.annotation.plugin.js</src>
        <src>/static/js/util/zion.feed.like.plugin.util.js</src>
        <src>/static/js/plugin/zion.feed.like.plugin.js</src>
        <src>/static/js/service/loadingservice.js</src>
        <src>/static/js/service/commonservice.js</src>
        <src>/static/js/service/commentsservice.js</src>
        <src>/static/js/service/notificationservice.js</src>
        <src>/static/js/service/webservice.js</src>
        <src>/static/js/service/message.js</src>
        <src>/static/js/service/googlelocationservice.js</src>
        <src>/static/js/service/urls.js</src>
        <src>/static/js/service/validationService.js</src>
        <src>/static/js/service/feedservice.js</src>
        <src>/static/js/service/gaeventtrackingservice.js</src>
        <src>/static/js/service/recaptchaservice.js</src>
        <src>/static/js/service/uploadphotoservice.js</src>
        <src>/static/js/service/createCampaign.js</src>
        <src>/static/js/service/messageService.js</src>
        <src>/static/js/util/zionStringUtil.js</src>
        <src>/static/js/util/authcheck.js</src>
        <src>/static/js/util/imagereveal.js</src>
        <src>/static/js/util/handlebarHelper.js</src>
        <src>/static/js/util/userAgentCheck.js</src>
        <src>/static/js/util/imageUtils.js</src>
        <src>/static/js/util/feedTagUtil.js</src>
        <src>/static/js/util/urlparser.js</src>
        <src>/static/js/plugin/zion.social.share.plugin.js</src>
        <src>/static/js/plugin/zion.blogs.plugin.js</src>
        <src>/static/js/plugin/zion.login.plugin.js</src>
        <src>/static/js/plugin/zion.loading.plugin.js</src>
        <src>/static/js/components/pub/menu/business.js</src>
        <src>/static/js/components/pub/menu/ui.js</src>
        <src>/static/js/template/adminMenus.js</src>
        <src>/static/js/template/pubHeaderMenus.js</src>
        <src>/static/js/template/pubTabBarMenus.js</src>
        <src>/static/js/template/pubAdminHeaderMenus.js</src>
        <src>/static/js/template/pubFooterMenus.js</src>
        <src>/static/js/template/pubAdminHeaderMoreMenus.js</src>
        <src>/static/js/template/createCampaign.js</src>
        <src>/static/js/template/socialMediaList.js</src>
    </pack:script>

    <stripes:layout-component name="footer"/>
        <%-- Google Tag Manager (noscript) --%>
    <noscript>
        <iframe src="https://www.googletagmanager.com/ns.html?id=GTM-5SHCZ56" height="0" width="0"
                style="display:none;visibility:hidden"></iframe>
    </noscript>
        <%-- End Google Tag Manager (noscript) --%>
    </body>
    </html>
</stripes:layout-definition>