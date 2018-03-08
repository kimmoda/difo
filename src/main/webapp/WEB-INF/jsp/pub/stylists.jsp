<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Bloggers | Fashion Designers | Fashion Stylists Directory">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.stylistList.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="banner">
            <h3>DISCOVER FASHION INFLUENCERS</h3>
        </div>

        <div class="filter-panel-container row">
            <div class="col-sm-2 col-xs-6 filter-panel-cell filter-toggle-btn" id="filter-toggle-button">
                <span>FILTER <span class="filter-num"></span></span>
                <i class="fa fa-angle-down" aria-hidden="true"></i>
            </div>
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
        <div id="tag-filter-container" class="tag-filter-container zion-closed filter-hide"></div>
        <div class="row">
            <div id="stylist-list" class="col-sm-12"></div>
        </div>
        <div class="empty-message">
            <span>Sorry, no results found. Please try another search.</span>
        </div>
        <div class="zion-loading">
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script type="text/javascript">
		    var listUsersUrl = "<c:url value="/restws/user/v1/list"/>",
		        stylistUrl = "<c:url value="/action/stylist/view"/>";
		</script>
		<pack:script enabled="${actionBean.webConfig.packTagEnabled}">
		    <src>/static/js/components/pub/stylist.list.js</src>
            <src>/static/js/components/pub/stylist/ui.js</src>
		    <src>/static/js/template/stylistcard.js</src>
		    <src>/static/js/template/trendsetterFilter.js</src>
		</pack:script>
    </stripes:layout-component>
</stripes:layout-render>
