jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        $('#start-share-btn').on('click', function () {
            $(this).zionSocialLogin({
                loginSuccessCallback:function () {
                    window.location.replace(zionUrls.action_my_looks)
                }
            }).show();
        });
    });
})(jQuery);