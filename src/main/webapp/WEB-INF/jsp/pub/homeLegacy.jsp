<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" bg="white-bg" pageTitle="Home">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.home.legacy.css</src>
            <src>/static/css/zion.pub.stylehub.css</src>
            <src>/static/css/zion.common.feed.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="zion-home-container row">
            <section class="header">
                <div class="header-container">
                    <div id="zion-header-carousel" class="carousel slide" data-ride="carousel" data-interval="4000">

                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#zion-header-carousel" data-slide-to="0" class="active"></li>
                            <li data-target="#zion-header-carousel" data-slide-to="1"></li>
                            <li data-target="#zion-header-carousel" data-slide-to="2"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <img class="img-lg" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/homebanner/hompage-top-slider-1.jpg"
                                     alt="header image">
                                <img class="img-sm" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/homebanner/hompage-top-slider-mobile-1.jpg"
                                     alt="header image">
                                <div class="carousel-caption">
                                    <h2 class="sub-title-header">A COMMUNITY</h2>
                                    <h1 class="title">FOR EVERYONE</h1>
                                    <h3 class="sub-title-footer">WHO LOVES FASHION</h3>
                                    <a class="btn zion-btn-white btn-get-inspired" href="<c:url value="/inspiration"/>">GET INSPIRED</a>
                                    <a class="btn zion-btn-black btn-become-trendsetter" href="<c:url value="/apply"/>">BECOME A TRENDSETTER
                                    </a>
                                </div>
                            </div>
                            <div class="item">
                                <img class="img-lg" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/homebanner/hompage-top-slider-2.jpg"
                                     alt="header image">
                                <img class="img-sm" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/homebanner/hompage-top-slider-mobile-2.jpg"
                                     alt="header image">
                                <div class="carousel-caption content-lg">
                                    <h2 class="sub-title-header">DISCOVER</h2>
                                    <h1 class="title">NEW STYLES</h1>
                                    <h3 class="sub-title-footer title-short">BROWSE THROUGH LOTS OF FASHIONABLE LOOKS</h3>
                                    <a class="btn zion-btn-black" href="<c:url value="/inspiration"/>">GET INSPIRED</a>
                                </div>
                            </div>
                            <div class="item">
                                <img class="img-lg" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/homebanner/hompage-top-slider-3.jpg"
                                     alt="header image">
                                <img class="img-sm" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/homebanner/hompage-top-slider-mobile-3.jpg"
                                     alt="header image">
                                <div class="carousel-caption">
                                    <h2 class="sub-title-header">FOLLOW</h2>
                                    <h1 class="title">TRENDSETTERS</h1>
                                    <h3 class="sub-title-footer">FIND DESIGNERS & FASHION INFLUENCERS</h3>
                                    <a class="btn zion-btn-black" href="<c:url value="/trendsetter/list"/>">START EXPLORING</a>
                                </div>
                            </div>
                        </div>

                        <!-- Controls -->
                        <a class="left carousel-control" href="#zion-header-carousel" role="button" data-slide="prev">
                            <i class="fa fa-2x fa-angle-left" aria-hidden="true"></i>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#zion-header-carousel" role="button" data-slide="next">
                            <i class="fa fa-2x fa-angle-right" aria-hidden="true"></i>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </section>
            <section class="trendsetter container">
                <section class="trendsetter-header col-md-12">
                    <h2 class="header-title">
                        Hot trendsetters of the week
                    </h2>
                </section>
                <div class="trendsetter-body col-md-12">
                    <div id="zion-trendsetter-carousel" class="carousel slide" data-ride="carousel" data-interval="false">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#zion-trendsetter-carousel" data-slide-to="0" class="active"></li>
                            <li data-target="#zion-trendsetter-carousel" data-slide-to="1"></li>
                            <li data-target="#zion-trendsetter-carousel" data-slide-to="2"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" role="listbox" id="popular-trendsetter">
                        </div>

                        <!-- Controls -->
                        <a class="left carousel-control" href="#zion-trendsetter-carousel" role="button"
                           data-slide="prev">
                            <i class="fa fa-angle-left" aria-hidden="true"></i>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#zion-trendsetter-carousel" role="button"
                           data-slide="next">
                            <i class="fa fa-angle-right" aria-hidden="true"></i>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </section>

            <section class="looks container">
                <h2 class="title col-sm-12">
                    Looks
                </h2>
                <div id="latestLooks">
                </div>
                <section class="see-more-btn col-xs-12">
                    <a href="<c:url value="/inspiration" />" class="btn zion-btn-white">SEE MORE</a>
                </section>
            </section>
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
            <src>/static/js/template/feedcard.js</src>
            <src>/static/js/template/homeLatestLooks.js</src>
            <src>/static/js/template/homePopularTrendsetters.js</src>
            <src>/static/js/template/annotation.js</src>
            <src>/static/js/template/comments.js</src>
        </pack:script>
        <zion:uiComp name="homeLegacy"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>