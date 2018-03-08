<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isHideHeaderMenu="true">
    <stripes:layout-component name="contents">
        <div class="row margin-top-40">
            <div class="col-md-2"></div>
            <div class="col-md-8 text-center">
                <a href="<c:url value="/" />" title="STYLEHUB">
                    <img src="<c:out value="${appLogoUrl}"/>" height="80" />
                </a>
            </div>
            <div class="col-md-2"></div>
        </div>
        <div class="row margin-top-20">
            <div class="col-md-2"></div>
            <div class="col-md-8 text-center">
                <div class="facebook-login-container margin-bottom-20">
                    <button id="facebook-login-button-id" class="btn btn-lg center-block" type="button">
                        <i class="fa fa-facebook" aria-hidden="true"></i>
                        <span>Sign in with Facebook</span>
                    </button>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8 text-center">
                <div class="google-login-container margin-bottom-20">
                    <button id="google-login-button-id" class="btn btn-lg center-block" type="button">
                        <i class="fa fa-google" aria-hidden="true"></i>
                        <span>Sign in with Google</span>
                    </button>
                </div>
            </div>
            <div class="col-md-2"></div>
        </div>
        <div class="row condition-container">
            <div class="col-md-2"></div>
	        <div class="col-md-8 text-center">
	            <span class="zion-indicator">
	                By clicking Sign in, I agree to<br />
	                STYLEHUB <a href="<c:url value="/termsandcondition" />" title="terms of use" target="_blank">terms of use</a> 
	                and <a href="<c:url value="/privacy" />" title="privacy" target="_blank">privacy policy</a>.
	            </span>
	        </div>
	        <div class="col-md-2"></div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
       <script src="https://apis.google.com/js/platform.js"></script>
       <script src="<c:url value="/static/js/util/facebookinit.js" />"></script>
       <script src="<c:url value="/static/js/components/pub/signin/ui.js" />"></script>
    </stripes:layout-component>
</stripes:layout-render>
