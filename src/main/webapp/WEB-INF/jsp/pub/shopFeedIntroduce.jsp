<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Shop My Feed">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.shopFeedIntroduce.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="zion-shop-introduce-container">
            <section class="header">
                <h4 class="title">
                    HOW TO PROMOTE
                </h4>
                <h4 class="title">
                    YOUR PERSONAL BRAND
                </h4>
                <h5 class="sub-title">
                    WITH STYLEHUB
                </h5>
            </section>
            <section class="main-content container">
                <section class="top-banner row">
                    <div class="col-sm-6 col-xs-12 banner-left-container ">
                        <div class="col-xs-3 col-md-4 banner-icon no-padding">
                            <img class="img-responsive" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/shopmyfeed/top-banner-icon.png" alt="top banner icon">
                        </div>
                        <div class="col-xs-9 col-md-8 banner-content no-padding">
                            <h5 class="title">Sell Your Products Directly from Your Social Media Page</h5>
                            <h6 class="content">With STYLEHUB you can easily share your STYLEHUB profile, looks, collections and portfolio via social media to increase awareness and sales, and all in three simple steps.</h6>
                        </div>
                    </div>
                    <div class="col-sm-6 col-xs-12 banner-right-container no-padding">
                        <img class="banner-process img-responsive" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/shopmyfeed/top-banner-process.png" alt="top banner process">
                    </div>
                </section>
                <section class="step-container row">
                    <div class="col-xs-12 step-title">
                        <div class="step-num">01</div>
                        <div class="title half-width">
                            <p class="title-content">Add look</p>
                            <p class="content">Log into your STYLEHUB account, and click on ADD LOOK to add your looks from your Desktop, Facebook or Instagram.</p>
                        </div>
                    </div>
                    <div class="col-xs-12 step-image">
                        <img class="img-responsive" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/shopmyfeed/step-1.jpg" alt="step-1">
                    </div>
                </section>
                <section class="step-container row">
                    <div class="col-xs-12 col-sm-6 hidden-xs step-image">
                        <img class="img-responsive" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/shopmyfeed/step-2.jpg" alt="step-2">
                    </div>
                    <div class="col-xs-12 col-sm-6 step-title">
                        <div class="step-num">02</div>
                        <div class="title">
                            <p class="title-content">Add a shopping link</p>
                            <p class="content">
                                Embed a link in your images. This way when users click on your looks, they will be directed to where they can purchase your items.
                            </p>
                            <p class="content">
                                Simply click anywhere on your image to add a shopping link.
                            </p>
                            <p class="content">
                                *TIP: Don't forget to test your links after to make sure they are linked correctly!
                            </p>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-6 visible-xs step-image">
                        <img class="img-responsive" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/shopmyfeed/step-2.jpg" alt="step-2">
                    </div>
                </section>
                <section class="step-container row">
                    <div class="col-xs-12 step-title">
                        <div class="step-num">03</div>
                        <div class="title half-width">
                            <p class="title-content">Share your looks</p>
                            <p class="content">Share your looks via social media by clicking the social media icons underneath your looks.</p>
                        </div>
                    </div>
                    <div class="col-xs-12 step-image">
                        <img class="img-responsive" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/shopmyfeed/step-3.jpg" alt="step-3">
                    </div>
                </section>
                <section class="step-container row">
                    <div class="col-xs-12 col-sm-6 col-sm-offset-6 step-title">
                        <div class="step-num">04</div>
                        <div class="title">
                            <p class="title-content">Share your feed link on social media</p>
                            <p class="content">In one click, you can easily share all your looks to get more attention, build awareness, and increase sales.</p>
                        </div>
                    </div>
                    <div class="col-xs-12 step-image">
                        <img class="img-responsive" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/shopmyfeed/step-4.jpg" alt="step-4">
                    </div>
                </section>
                <section class="step-container no-border row">
                    <div class="col-xs-12 step-title margin-bottom-20">
                        <div class="title">
                            <span class="title-content">Your shopping feed</span>
                        </div>
                    </div>
                    <div class="col-xs-12 step-image">
                        <img class="img-responsive" src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/shopmyfeed/shop-feed.jpg" alt="shop-feed">
                    </div>
                    <div class="col-xs-12 final-content">
                        Now you have a shoppable feed!
                    </div>
                </section>
                <section class="start-share">
                    <button class="btn zion-btn-black" id="start-share-btn">Start sharing now</button>
                </section>
            </section>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <zion:uiComp name="shopFeedIntroduce" includeFile="ui"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>
