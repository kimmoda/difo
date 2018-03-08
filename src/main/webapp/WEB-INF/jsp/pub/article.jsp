<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Article">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.article.css</src>
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
            <div class="row flex">
                <div class="article-area col-sm-8">
                    <div class="cover">
                        <img src="#" alt="" class="cover-image">
                    </div>
                    <div class="title">

                    </div>
                    <div class="author-container">
                        <span class="author"></span> | <span class="date"></span>
                    </div>
                    <div class="article-content">

                    </div>
                </div>
                <div class="recommend-area col-sm-4">
                    <div class="headline">
                        What's trending
                    </div>
                </div>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script type="text/javascript">
            var articleId = "${actionBean.articleId}";
        </script>
        <pack:script enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/js/template/postslistitem.js</src>
        </pack:script>
        <zion:uiComp name="article"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>
