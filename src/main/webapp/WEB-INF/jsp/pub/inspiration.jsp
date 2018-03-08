<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" bg="white-bg"
                       pageTitle="Best street style looks | Fashion Bloggers | Designers collections">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.stylehub.css</src>
            <src>/static/css/zion.common.feed.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="header"></stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="banner">
            <h3>GET INSPIRED BY THE LATEST POSTS</h3>
            <h4>Explore, Love and comment on posts. Click on them to buy the items</h4>
        </div>
        <div class="filter-panel-container row">
            <div class="col-sm-2 col-xs-6 filter-panel-cell filter-toggle-btn" id="filter-toggle-button">
                <span>FILTER <span class="filter-num"></span></span>
                <i class="fa fa-angle-down" aria-hidden="true"></i>
            </div>
            <%--<div class="col-sm-2 col-xs-4 filter-panel-cell filter-display">--%>
                <%--<span class="order-container" id="order-container">--%>
                    <%--<span class="order-display" id="order-display"></span>--%>
                <%--</span>--%>
            <%--</div>--%>
            <div class="col-sm-1 col-xs-6 filter-panel-cell filter-display">
                <a href="javascript:;" class="reset-filter-button" id="reset-filter-button">
                    Reset
                </a>
            </div>
            <div class="col-sm-9 hidden-xs filter-panel-cell filter-display">
                <div class="tag-container">
                    <a class="tag-scroll-btn zion-hide" href="javascript:;" id="left-button">
                        <i class="fa fa-lg fa-angle-left" aria-hidden="true"></i>
                    </a>
                    <div class="tag-area-wrap" id="tag-area-wrap">
                        <ul class="selected-tags" id="tags-area">
                        </ul>
                    </div>
                    <a class="tag-scroll-btn zion-hide" href="javascript:;" id="right-button">
                        <i class="fa fa-lg fa-angle-right" aria-hidden="true"></i>
                    </a>
                </div>
            </div>
        </div>
        <div id="tag-filter-container" class="tag-filter-container zion-closed filter-hide">
        </div>
        <div id="feed-list" class="grid">
            <div class="grid-col-sizer"></div>
            <div class="grid-gutter-sizer"></div>
        </div>
        <div class="empty-message">
            <span>Sorry, no results found. Please try another search.</span>
        </div>
        <div class="zion-loading">
        </div>
        <div id="myModal" class="modal fade zion-feed-modal zion-modal" tabindex="-1" role="dialog"
             aria-labelledby="myModalLabel"></div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script type="text/javascript">
            var listFeedsUrl = "<c:url value="/restws/feed/v1/list"/>",
                getFeedUrl = "<c:url value="/restws/feed/v1/view"/>",
                stylistUrl = "<c:url value="/action/stylist/view"/>";
        </script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/components/pub/inspiration.js</src>
            <src>/static/js/template/feedcard.js</src>
            <src>/static/js/template/inspirationFilter.js</src>
            <src>/static/js/template/annotation.js</src>
            <src>/static/js/template/comments.js</src>
        </pack:script>
    </stripes:layout-component>
</stripes:layout-render>
