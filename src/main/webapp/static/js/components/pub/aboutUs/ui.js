jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var fields = [
                {
                    'target': '#contact-message',
                    'error': '#email-error-indicator',
                    'minLength': 5,
                    'maxLength': 10000
                }
            ],
            validationService = zionValidationService(),
            recaptcha = recaptchaService();
        recaptcha.success(function () {
            var data = $.trim($('.contact-message').val());
            zionWebService.post(zionUrls.rest_postEmailFeedback, data, function (resp) {
                if (resp.status === 200) {
                    $('.contact-message').val('');
                    $('#message-btn-text').addClass('zion-hide');
                    $('#success-icon').removeClass('zion-hide');
                    setTimeout(function () {
                        $('#message-btn-text').removeClass('zion-hide');
                        $('#success-icon').addClass('zion-hide');
                    }, 2000);
                }
            });
        });

        $('#join-us-btn').on('click', $.debounce(1000, true, function (e) {
            $(this).zionSocialLogin({
                loginSuccessCallback:function () {
                    window.location.href = zionUrls.action_dashboard
                }
            }).show();
        }));

        validationService.registerFieldEvents(fields);

        $('#contact-message-btn').on('click', $.debounce(1000, true, function (e) {
            if (validationService.validFields(fields)) {
                if (commonService.getJwtToken()) {
                    recaptcha.fire();
                } else {
                    $(this).zionSocialLogin({
                        loginSuccessCallback: function () {
                            recaptcha.fire();
                        }
                    }).show()
                }
            }
        }));
    });
})(jQuery);
