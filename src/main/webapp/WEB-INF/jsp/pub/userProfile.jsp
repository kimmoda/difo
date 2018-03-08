<%@ include file="/WEB-INF/jsp/include/tags.jsp" %>

<stripes:layout-render name="/WEB-INF/jsp/layout/pub/layout.jsp" isUserAdmin="true">
    <stripes:layout-component name="header">
        <pack:style enabled="${actionBean.webConfig.packTagEnabled}">
            <src>/static/css/zion.pub.userprofile.css</src>
        </pack:style>
    </stripes:layout-component>
    <stripes:layout-component name="adminContents">
        <form class="form-horizontal zion-form">
            <div class="panel zion-panel">
                <div class="panel-heading">About</div>
                <div class="panel-body">
                    <div class="form-group">
                        <div class="col-sm-2">
                        </div>
                        <div class="col-sm-10">
                            <div class="btn btn-primary" id="change-avatar-button">Change Avatar</div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-firstName" class="col-sm-2 control-label">Ethereum Wallet Address</label>
                        <div class="col-sm-10">
                            <input id="zion-wallet-address" class="form-control" name="walletAddress" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-title" class="col-sm-2 control-label">Title</label>
                        <div class="col-sm-10">
                            <select class="form-control" name="title" id="zion-title">
                                <option value=""></option>
                                <option value="MR">Mr</option>
                                <option value="MRS">Mrs</option>
                                <option value="MS">Ms</option>
                                <option value="MISS">Miss</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-firstName" class="col-sm-2 control-label">First Name</label>
                        <div class="col-sm-10">
                            <input id="zion-firstName" class="form-control" name="firstName" type="text">
                            <div id="firstName-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 50 characters.
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-lastName" class="col-sm-2 control-label">Last Name</label>
                        <div class="col-sm-10">
                            <input id="zion-lastName" class="form-control" name="lastName" type="text" value="">
                            <div id="lastName-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 50 characters.
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-gender" class="col-sm-2 control-label">Gender</label>
                        <div class="col-sm-10">
                            <div class="checkbox" id="zion-gender">
                                <input type="radio" name="gender" id="zion-male" value="male"> <span>Male&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <input type="radio" name="gender" id="zion-female" value="female"> <span>Female</span>
                                <input type="hidden" name="genderCode" id="zion-gender-code" value="">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-dob" class="col-sm-2 control-label">Birthday</label>
                        <div class="col-sm-10">
                            <input id="zion-dob" class="form-control" placeholder="Date of Birth" name="dob" type="text"
                                   value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-introduce" class="col-sm-2 control-label">Describe Yourself</label>
                        <div class="col-sm-10">
                            <textarea id="zion-introduce" class="form-control" name="introduce"></textarea>
                            <div id="introduce-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 500 characters
                            </div>
                        </div>
                    </div>
                    <input id="zion-avatar" type="hidden" name="avatar"/>
                </div>
            </div>
            <div class="panel zion-panel">
                <div class="panel-heading">Contact</div>
                <div class="panel-body">
                    <div class="form-group">
                        <label for="zion-email" class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-10">
                            <input id="zion-email" class="form-control" name="email" type="email">
                            <div id="email-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 50 characters.
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-address1" class="col-sm-2 control-label">Location</label>
                        <div class="col-sm-10">
                            <input id="zion-address1" class="zion-google-location-input form-control"
                                   placeholder="e.g. Auckland, New Zealand / 123 Main Street, Aukcland" name="location"
                                   type="text" value="">
                            <div id="address1-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 200 characters.
                            </div>
                            <input id="zion-city" class="form-control" placeholder="City" name="city" type="hidden"
                                   value="">
                            <input id="zion-country" class="form-control" placeholder="Country" name="country"
                                   type="hidden" value="">
                            <input id="zion-country-code" class="form-control" placeholder="Country" name="countryCode"
                                   type="hidden" value="">
                            <input id="zion-suburb" class="form-control" placeholder="Country" name="country"
                                   type="hidden" value="">
                            <input id="zion-state" class="form-control" placeholder="State" name="state" type="hidden"
                                   value="">
                            <input id="zion-postcode" class="form-control" placeholder="Postcode" name="postcode"
                                   type="hidden" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-phone1" class="col-sm-2 control-label">Phone</label>
                        <div class="col-sm-10">
                            <input id="zion-phone1" class="form-control" name="phone" type="text">
                            <div id="phone1-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 50 characters.
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-facebook" class="col-sm-2 control-label">Facebook</label>
                        <div class="col-sm-10">
                            <input id="zion-facebook" class="form-control" name="facebook" type="text"
                                   placeholder="your facebook link">
                            <div id="facebook-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 200 characters.
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-instagram" class="col-sm-2 control-label">Instagram</label>
                        <div class="col-sm-10">
                            <input id="zion-instagram" class="form-control" name="instagram" type="text"
                                   placeholder="your instagram link">
                            <div id="instagram-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 200 characters.
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-youtube" class="col-sm-2 control-label">Youtube</label>
                        <div class="col-sm-10">
                            <input id="zion-youtube" class="form-control" name="youtube" type="text"
                                   placeholder="your youtube page link">
                            <div id="youtube-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 200 characters.
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="zion-website" class="col-sm-2 control-label">Website</label>
                        <div class="col-sm-10">
                            <input id="zion-website" class="form-control" name="website" type="text"
                                   placeholder="your website page link">
                            <div id="website-error-indicator" class="zion-input-error-text zion-hide">
                                Please enter the text less than 200 characters.
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="row user-profile-update margin-bottom-20">
            <div class="col-md-12">
                <button id="zion-update-user-profile" class="btn btn-primary">Update</button>
                <span id="submit-fail-message" class="zion-submit-error-message zion-hide">Update fail, please check above error fields.</span>
                <div class="success-check text-success zion-hide">
                    <i class="fa fa-lg fa-check" aria-hidden="true"></i>
                    <span>Your info has been successfully updated.</span>
                </div>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="footer">
        <script src="//widget.cloudinary.com/global/all.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBxeKW0Bw2FccEE3wWJdbUvMWUIeKQ_PJw&libraries=places&callback=zionGoogleLocationService.init&language=en"></script>
        <zion:uiComp name="userProfile"></zion:uiComp>
    </stripes:layout-component>
</stripes:layout-render>