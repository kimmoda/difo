jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        $(this).zionSocialLogin({
            loginSuccessCallback:function () {
                window.location.replace(zionUrls.action_userProfile);
            }
        }).show();
    });
})(jQuery);