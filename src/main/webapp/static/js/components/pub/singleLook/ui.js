jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var url = zionUrls.rest_getFeed,
            feedInfo = {
                id: feedId
            };
        SingleLookService.getLook(url, feedInfo);
        $('.zion-content').on('click', '#share-feed-button', function () {
            $('.feed-share-container').toggleClass('show-feed-share-container');
        });
    });
})(jQuery);