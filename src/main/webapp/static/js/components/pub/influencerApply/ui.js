 jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var userId,
            stylistApplyPage = {
                finishedContainerId: '#applyFinishModal',
                sendContainer: '#send-container',
                sendButtonContainer: '#send-button-container',
                loginContainer: '#login-container',
                googleLoginBtId: '#apply-google-login-btn',
                facebookLoginBtId: '#apply-facebook-login-btn',
                googleAuthClientId: webConfig.googleAuthId,
                loginRestUrl: zionUrls.rest_login
            },
            fields = [{'target': '#email', 'error': '#email-error-indicator', 'minLength': 5, 'maxLength': 50},
                {'target': '#social-media-text', 'error': '#media-error-indicator', 'minLength': 10, 'maxLength': 300},
            ],
            initUser = function (user) {
                if (user) {
                    userId = user.id;
                    if (applied(user)) {
                        return;
                    }
                    if (user.contact && user.contact.email) {
                        $('#email').val(user.contact.email);
                    }
                }
            },
            validationService = zionValidationService(),
            init = function () {
                if (commonService.getJwtToken()) {
                    $('#login-container').addClass('zion-hide');
                    $('#login-content').removeClass('zion-hide');
                    $('#apply-form-container').removeClass('zion-hide');
                    influencerApplyBusiness.getUserInfo(function (user) {
                        initUser(user);
                    });
                } else {
                    $('#login-container').removeClass('zion-hide');
                    $('#login-content').addClass('zion-hide');
                    $('#apply-form-container').addClass('zion-hide');
                    $('.zion-stylist-apply-container').removeClass('zion-hide');
                    $('#login-container').zionSocialLogin({
                        googleLoginBtId: '#apply-google-login-btn',
                        facebookLoginBtId: '#apply-facebook-login-btn',
                        loginSuccessCallback: function () {
                            window.location.replace(commonService.getZionHomeUrl() + '/apply');
                        }
                    });
                }
                validationService.registerFieldEvents(fields);
            },
            applied = function (userInfo) {
                if (userInfo.registrationStatus === 'APPROVED') {
                    $('.zion-stylist-apply-container').addClass('zion-hide');
                    $('#login-container').addClass('zion-hide');
                    $('#login-content').addClass('zion-hide');
                    $('#apply-form-container').addClass('zion-hide');
                    $('#approved-container').removeClass('zion-hide');
                    return true;
                }
                if (userInfo.registrationStatus === 'PENDING') {
                    $('.zion-stylist-apply-container').addClass('zion-hide');
                    $('#login-container').addClass('zion-hide');
                    $('#login-content').addClass('zion-hide');
                    $('#apply-form-container').addClass('zion-hide');
                    $('#applied-container').removeClass('zion-hide');
                    return true;
                }
                $('.zion-stylist-apply-container').removeClass('zion-hide');
                return false;
            };

        init();

        $('#send-apply-button').on('click', $.debounce(1000, true, function () {
            if (!userId) {
                zionNotifacationService.error(zionMessage.error_server_msg.content, zionMessage.error_server_msg.title);
                return;
            }
            var email = $.trim($('#email').val());
            if (!validationService.email({
                    email: email,
                    id: "#email"
                })) {
                $('#email-error-indicator').text("Invalid email.").removeClass('zion-hide');
                zionGAEventService('applyTrendsetter', 'emailvalidate', "fail-" + email + " user-" + userId).track();
                return;
            }

            var params = {
                socialMediaUrls: $('#social-media-text').val(),
                contactEmail: $('#email').val(),
            };
            if (validationService.validFields(fields)) {
                zionLoadingService().show();
                zionWebService.post(zionUrls.rest_stylistApply, params, function () {
                    //TODO: error handling
                });
                zionGAEventService('applyTrendsetter', 'apply', "success-" + userId).track();
                window.location.replace(commonService.getZionHomeUrl() + '/apply/success');
            }
        }));

    });
})(jQuery);
