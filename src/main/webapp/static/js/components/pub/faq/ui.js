jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var createCampaignCost = 200,
            maxCoinEarn = 300;
        $('.create-campaign-cost').text(createCampaignCost);
        $('.max-coin-earn').text(maxCoinEarn);

        $('.style-seeker-button').on('click', function () {
            $('.trendsetter').hide();
            $('.style-seeker').show();
            $('.trendsetter-button').removeClass('active');
            $('.style-seeker-button').addClass('active');
        });

        $('.trendsetter-button').on('click', function () {
            $('.trendsetter').show();
            $('.style-seeker').hide();
            $('.trendsetter-button').addClass('active');
            $('.style-seeker-button').removeClass('active');
        });

        $('#faq-pub-add-campaign').on('click', function (e) {
            e.preventDefault();
            createCampaignService().init();
        });
    });
})(jQuery);
