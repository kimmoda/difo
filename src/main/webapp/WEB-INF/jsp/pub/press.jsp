<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Blog">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.press.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="header">
            <div class="header-title">
                <h1>BLOG</h1>
            </div>
            <div class="header-subtitle">
                <h4>Curated articles from the most fashionable trendsetters online</h4>
            </div>
        </div>
        <div class="content-container">
            <div id="posts-list"></div>
        </div>
        <div style="display:none;" zion-blogs-item-template>
            <div class="col-sm-6 article-card" data-id="{[id]}">
                <div class="item-container">
                    <div class="cover-image">
                        <img src="#" alt="" id="cover-{[id]}">
                    </div>
                    <div class="title">
                        <span>{[title]}</span>
                    </div>
                    <div class="author-info-container">
                        <span class="author">{[author]}</span>&nbsp;&nbsp;|&nbsp;
                        <span class="publish-time">{[date]}</span>
                    </div>
                    <div class="content">
                        {[content]}
                    </div>
                </div>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/postslistitem.js</src>
        </pack:script>
        <zion:uiComp name="blogs"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>
