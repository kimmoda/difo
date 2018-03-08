jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var config = {
            pub: false,
            container: '#stylist-content',
            showMore: '#show-more',
            showSummary: false,
            showRating: false,
            url: zionUrls.rest_getFollowedUsers,
            customCss: 'small-size',
            grid: 'col-md-4'
        };

        layoutStylist(config, function () {
            $('.zion-stylist-complete-container').removeClass('zion-hide');
        });

        $('.find-fashion-button').click(function () {
                window.location.href = zionUrls.action_trendsettersList;
            }
        );

    });
})(jQuery);
