<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Stylist">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.stylistLooks.css</src>
            <src>/static/css/zion.pub.stylistView.css</src>
            <src>/static/css/zion.common.feed.css</src>
            <src>/static/css/zion.trendsetter.comment.plugin.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="zion-stylist-container">
            <section class="zion-stylist-header" id="user-header"></section>
            <section class="zion-stylist-content container">
                <div class="col-sm-4 col-md-3 user-summary" id="user-summary"></div>
                <div class="col-sm-8 col-md-9 stylist-content">
                    <div class="looks-count-container zion-hide" id="looks-count">

                    </div>
                    <div class="looks" id="stylist-content">

                    </div>
                    <div class="show-more-look">
                        <button class="btn zion-btn-white show-more-look-btn zion-hide" id="show-more-look-btn">SHOW MORE</button>
                    </div>
                    <div class="comment" id="comments">

                    </div>
                </div>
            </section>

                <%--<div class="modal fade" id="zion-review-stylist-dialog" tabindex="-1" role="dialog" aria-hidden="true">--%>
                <%--<div class="modal-dialog" role="document">--%>
                <%--<div class="modal-content">--%>
                <%--<div class="modal-header">--%>
                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
                <%--<span aria-hidden="true">&times;</span>--%>
                <%--</button>--%>
                <%--</div>--%>
                <%--<div class="modal-body">--%>
                <%--<h1>Review Stylist</h1>--%>
                <%--<form class="zion-form">--%>
                <%--<div id="rating-error-banner" class="zion-form-error-summary hide">--%>
                <%--Please give rating stars--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                <%--<input id="stylist-rating" name="stylist-rating" type="number" class="rating" min=0--%>
                <%--max=5 step=1 data-size="sm" data-rtl="false">--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                <%--<label for="stylist-review-comment">Comment</label>--%>
                <%--<textarea type="text" class="form-control" id="stylist-review-comment"--%>
                <%--placeholder="comment"></textarea>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                <%--<button id="rating-btn" class="btn btn-primary">Primary</button>--%>
                <%--</div>--%>
                <%--</form>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>

        </div>

    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script type="text/javascript">
            var userID = "${actionBean.userId}",
                getUserUrl = "<c:url value="/restws/user/v1/view?id=${actionBean.userId}"/>",
                listFeedsUrl = "<c:url value="/restws/feed/v1/list"/>",
                getFeedUrl = "<c:url value="/restws/feed/v1/view"/>";
        </script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/plugin/zion.trendsetter.comment.plugin.js</src>
            <src>/static/js/components/pub/stylist.looks.js</src>
            <src>/static/js/components/pub/stylist.js</src>
            <src>/static/js/template/userheader.js</src>
            <src>/static/js/template/feedcard.js</src>
            <src>/static/js/template/annotation.js</src>
            <src>/static/js/template/looks.js</src>
            <src>/static/js/template/usersummary.js</src>
            <src>/static/js/template/personalinfo.js</src>
            <src>/static/js/template/services.js</src>
            <src>/static/js/template/reviews.js</src>
            <src>/static/js/template/contact.js</src>
            <src>/static/js/template/comments.js</src>
            <src>/static/js/template/commentsProfile.js</src>
        </pack:script>
        <script src="https://www.google.com/recaptcha/api.js?onload=recaptchaInit&render=explicit" async defer></script>
    </stripes:layout-component>
</stripes:layout-render>
