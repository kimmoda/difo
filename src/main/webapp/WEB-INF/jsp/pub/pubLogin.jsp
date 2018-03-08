<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<div class="modal fade zion-modal" id="loginModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog signin-container" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <!-- <h5 class="modal-title zion-login-modal-title">Sign in</h5> -->
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- <div class="row title-container">
                    <div class="col-md-12">
                        <h2>JOIN STYLEHUB</h2>
                        <div class="row">
                            <div class="col-md-2"></div>
                            <div class="col-md-8">
                                <p class="campaign">Discover Fashion Influencers that Match Your Taste</p>
                            </div>
                            <div class="col-md-2"></div>
                        </div>
                    </div>
                </div> -->
                <div class="row signin-btn-container">
                    <div class="col-md-12">
                        <div class="facebook-login-container margin-bottom-20">
		                    <button id="facebook-login-button-id" class="btn btn-lg center-block" type="button">
		                        <i class="fa fa-facebook" aria-hidden="true"></i>
		                        <span>Sign in with Facebook</span>
		                    </button>
		                </div>
                    </div>
                    <div class="col-md-12">
                        <div class="google-login-container">
		                    <button id="google-login-button-id" class="btn btn-lg center-block" type="button">
		                        <i class="fa fa-google" aria-hidden="true"></i>
		                        <span>Sign in with Google</span>
		                    </button>
		                </div>
                    </div>
                </div>
                <div class="row condition-container">
                    <div class="col-md-12">
                        <span class="zion-indicator">
                            By clicking Sign in, I agree to<br />
                            <c:out value="${appBrandName}"/> <a href="<c:url value="/termsandcondition" />" title="terms of use" target="_blank">terms of use</a>
                            and <a href="<c:url value="/privacy" />" title="privacy" target="_blank">privacy policy</a>.
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://apis.google.com/js/platform.js"></script>
<script src="<c:url value="/static/js/util/facebookinit.js" />"></script>