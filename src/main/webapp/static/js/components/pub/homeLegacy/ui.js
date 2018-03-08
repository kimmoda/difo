jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var feedService = zionFeedService();
        homepageService.getLatestLooks(function (resp) {
            var latestLooksHTML = Handlebars.templates.homeLatestLooks({looks: resp}),
                $items = $(latestLooksHTML);
            $items.find('.zion-feed-like-plugin-container').each(function (index) {
                $(this).zionFeedLikePlugin({
                    id: $(this).data('feed-like-id'),
                    likeCount: $(this).data('feed-like-count'),
                    like: $(this).data('feed-like'),
                    fireToggleFeedLikeStatus: false
                });
            });
            $('#latestLooks')
                .empty()
                .append($items)
                .on('click', '.look-image', $.debounce(1000, true, function (e) {
                    var feedId = $(this).data("id");
                    var url = zionUrls.rest_getFeed + "?id=" + feedId;
                    feedService.getfeedById(url);
                }));

            if (isIE()) {
                $('.author-name').addClass('ie');
            }
        });

        homepageService.getPopularUser(function (resp) {
            var row1 = [],
                row2 = [],
                row3 = [],
                user = [row1, row2, row3];

            for (var i = 0; i < resp.length; i++) {
                resp[i].stylistUrl = stylistUrl + '?userId=' + resp[i].id;
                var containerIndex = parseInt((i / 4), 10);
                if (containerIndex < 4) {
                    user[containerIndex].push(resp[i]);
                }
            }

            var popularUserHTML = Handlebars.templates.homePopularTrendsetters({users: user});
            $('#popular-trendsetter').append(popularUserHTML);
            $('#popular-trendsetter .item').first().addClass('active');
            $('#zion-trendsetter-carousel').carousel();
        });

        zionFeedLikePluginUtils.addChangeFeedLikeStatusEvent($('#latestLooks'));

        $('#latestLooks').on('click', '.author-link', function () {
            var userId = $(this).data('user-id');
            if (userId) {
                window.location.href = zionUrls.action_stylist_view + '?userId=' + userId;
            }
        });

        $('#get-start-btn').on('click', function () {
            if (commonService.getJwtToken()) {
                window.location.href = commonService.getZionActionUrl('/auth/userprofile')
            } else {
                $(this).zionSocialLogin({
                    loginSuccessCallback: function () {
                        window.location.href = commonService.getZionActionUrl('/auth/userprofile')
                    }
                }).show();
            }
        })

    });
})(jQuery);