jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        SingleTrendsetterService.getLook(getUserUrl);

        var looksHtml = Handlebars.templates.looks();
        $('#single-trendsetter-look').append(looksHtml);
        layoutFeed({
            userId: userID,
            squareImage:true
        });

        $('#single-trendsetter-summary').on('click', '#share-user-button', function () {
            $('.user-share-container').toggleClass('show-share-container');
            $('.zion-stylist-content').toggleClass('lower-stylist-content');
        });
    });
})(jQuery);