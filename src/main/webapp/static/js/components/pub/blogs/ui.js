jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        $('#posts-list').zionblogs({
            afterClicked:function (articleId) {
                window.location.href = commonService.getZionActionUrl('/article') + "?articleId=" + articleId;
            }
        });
    });
})(jQuery);
