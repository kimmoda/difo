jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var campaignService = authCampaignService(),
            user = commonService.getUserInfo(),
            coinSign = webConfig.digitalCurrencyShortname,
            options = {
                authorId: user.id,
                loadingMethod: 'MANUAL',
                isAdminPage:true,
                showDeleted: true
            };
        campaignListService.init(options);
        campaignService.balance(function (resp) {
            var account = {
                    balance : resp.balance ? resp.balance : 0,
                    expense : resp.expense ? resp.expense : 0,
                    income : resp.income ? resp.income : 0,
                    currencyName: coinSign
                },
                userCoinAccountHtml;
            userCoinAccountHtml = Handlebars.templates.userCoinAccount(account);
            $('#my-coin-account').empty().append(userCoinAccountHtml);
        });
        taskFinishedListService.init({
            manualLoadMore: true
        });

        $('.my-active-campaign').on('click', '.grid-item', function () {
            var campaignId = $(this).data('campaign-id'),
                status = $(this).data("status"),
                enabled = $(this).data('enabled');
            if (status === "ACTIVE" && enabled) {
                createCampaignService().init({
                    id: campaignId,
                    showParticipants: true
                });
            } else if (status === "EXPIRED" || !enabled) {
                simpleCampaignCardService().initWithCampaignId(campaignId);
            }
        });

        $('.my-finished-task-container').on('click', '.row-container', $.debounce(1200, true, function () {
            var campaignId = $(this).data('campaign-id');
            simpleCampaignCardService().initWithCampaignId(campaignId);
        }));
    });
})(jQuery);
