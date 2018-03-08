<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" pageTitle="Apply Trendsetters">
    <stripes:layout-component name="headMeta">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.stylistApply.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="contents">
        <div class="zion-stylist-apply-container row zion-hide">
                <%--<img class="top-image col-md-12"--%>
                <%--src="https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/become_stylist_top_banner.jpg"--%>
                <%--alt="become stylist top banner">--%>

            <div class="content col-md-12">
                <div class="apply-button zion-hide" id="login-container">
                    <h1 class="title col-md-12">
                        Become a verified influencer
                    </h1>
                    <p class="notice">
                        Apply NOW for FREE and become a verified influencer.
                        <br>
                        As a verified influencer on our platform you can promote your post on our community and increase
                        engagement, likes and fans while helping others to be seen online as well.
                    </p>
                    <P class="notice">To apply, sign in with your social media account.</P>
                    <div class="facebook-login-container">
                        <button id="apply-facebook-login-btn" class="btn btn-lg btn-facebook center-block"
                                type="button">
                            <i class="fa fa-facebook" aria-hidden="true"></i>
                            <span>Sign in with Facebook</span>
                        </button>
                    </div>
                    <span class="button-content">&nbsp;or&nbsp;</span>
                    <div class="google-login-container">
                        <button id="apply-google-login-btn" class="btn btn-lg btn-google center-block" type="button">
                            <i class="fa fa-google" aria-hidden="true"></i>
                            <span> Sign in with Google</span>
                        </button>
                    </div>
                </div>
                <div class="content col-md-12 zion-hide" id="login-content">
                    <h1 class="title col-md-12">
                        Promote your posts
                    </h1>
                    <p class="notice">To promote your posts and add a campaign you need to become a verified
                        influencer. To verify you as an influencer we need to check your online activity first. It won't
                        take more than 48 hours to be approved.</p>
                    <p class="notice">
                        Please enter your social media or blog URL down below:
                    </p>
                </div>
                <div class="zion-form col-md-12 zion-hide" id="apply-form-container">
                    <form>
                        <div class="form-group">
                            <label for="email">Email
                                <span class="zion-form-compulsory">*</span>
                            </label>
                            <input type="email" class="form-control" id="email" placeholder="Email"/>
                            <div id="email-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the words between 5 and 50 characters.
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="social-media-text">Social media links
                                <span class="zion-form-compulsory">*</span>
                            </label>
                            <textarea class="media-link form-control" id="social-media-text"
                                      placeholder="Enter any of your blog/website/instagram/youtube/facebook url"></textarea>
                            <div id="media-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the words between 10 and 300 characters.
                            </div>
                        </div>
                        <div id="send-apply-button" class="btn btn-lg btn-default">SUBMIT</div>
                    </form>
                </div>
            </div>
        </div>
        <div id="applied-container" class="zion-stylist-complete-container zion-hide">
            <p class="title">
                THANK YOU
            </p>
            <p class="content">
                We will review your application and in less than 48 hours we will approve you as a verified influencer.
            </p>
            <p class="content">
                While you wait, why do not you explore some influencers' campaigns?
            </p>
            <p class="content">
                <a href="<c:url value="/campaign" />">
                    >> EXPLORE <<
                </a>
            </p>
        </div>
        <div id="approved-container" class="zion-stylist-complete-container zion-hide">
            <p class="title">
                CONGRATULATIONS!
            </p>
            <p class="content">
                We have reviewed your application and have determined you will be an excellent influencer for our
                community.
            </p>
            <p class="content">
                We are delighted to have you join our exclusive community.
            </p>
            <p class="content">
                To begin, first start promoting, commenting or liking others influencers posts to earn coins. Click on
                the link below.
            </p>
            <p class="content">
                <a href="<c:url value="/campaign" />">
                    >> Promote Influencers <<
                </a>
            </p>
            <p class="content">
                Once you have helped other influencers, you will collect coins that would help you pay for your own
                promotional campaigns.
            </p>
            <p class="content">
                <a href="<c:url value="/home" />">
                    >> Learn more <<
                </a>
            </p>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <zion:uiComp name="influencerApply"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>
