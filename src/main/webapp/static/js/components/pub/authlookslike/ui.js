jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var looksHtml = Handlebars.templates.looks(),
            config = {
                isShowAuthor:true,
                pub:false,
                loadStatus: '.page-load-status',
                noData: '.no-data',
                deleteLookCard: true,
                url: zionUrls.rest_getLikedFeeds,
                afterModal:function ($grid, feedInfo) {
                    var element = $("#feed-" + feedInfo.id);
                    if (!feedInfo.like){
                        $grid.masonry( 'remove', element ).masonry('layout');
                    }
                    var items = $grid.masonry('getItemElements');
                    if (items && items.length === 0){
                        $('.zion-stylist-content').addClass('zion-hide');
                        $('.zion-stylist-complete-container').removeClass('zion-hide');
                    }
                }
            };
        $('#stylist-content').append(looksHtml);
        layoutFeed(config,function () {
            $('.zion-stylist-complete-container').removeClass('zion-hide');
        });
        
        $('.find-fashion-button').click(function () {
            window.location.href = zionUrls.action_inspiration;
        });

    });
})(jQuery);
