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

            <div class="video-container">
                <iframe src="https://www.youtube.com/embed/ocpAK6fLAOI?control=0&rel=0&showinfo=0"
                        allowfullscreen></iframe>
            </div>
            <p class="title col-md-12">
                JOIN <c:out value="${appBrandName}"/> TODAY
            </p>
            <div class="content col-md-12">
                <div class="apply-button zion-hide" id="login-container">
                    <p class="notice">Apply now for FREE to become a fashion-forward influencer, designer or stylist in
                        our community. You will have the opportunity to showcase your best looks, styles, and
                        collections, and connect with style seekers!</p>
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
                <div class="zion-form zion-hide" id="apply-form-container">
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
                            <label for="social-media-text">Social Media
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
                Your request is pending. We'll review your application in 48 hours. If you're approved you'll receive an
                email with instructions and a link to create your profile.
            </p>
        </div>
        <div id="approved-container" class="zion-stylist-complete-container zion-hide">
            <p class="title">
                CONGRATULATIONS!
            </p>
            <p class="content">
                We have reviewed your application and have determined that you will be
                an excellent <c:out value="${appBrandName}"/> influencer! We are delighted to have you join our community!
            </p>
            <p class="content">To begin creating your looks, click on the link below:
            </p>
            <p class="content">
                <a href=# class="btn btn-link btn-lg create-looks-link">Create Looks</a>
            </p>
            <p class="content">If you have any questions, please do not hesitate to reach out to our friendly support
                team at <a href="mailto:<c:out value="${appSupportEmail}"/>" style="color: #484848"><c:out value="${appSupportEmail}"/></a></p>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <zion:uiComp name="stylistApply"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>
