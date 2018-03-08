jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var looksHtml = Handlebars.templates.looks();
        $('#stylist-content').append(looksHtml);
        layoutFeed();

    });
})(jQuery);
