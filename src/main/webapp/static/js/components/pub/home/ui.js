jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        campaignListService.init({
            resultSize: 20,
            status: "ACTIVE",
            loadingMethod: 'NONE'
        });

        $('#campaign-list').on('click', '.zion-campaign-content', $.debounce(1000, true, function () {
            var campaignId = $(this).find('.campaign-image').data('id');
            zionPromoteCampaignService({
                campaignId: campaignId,
                modalContainerId: '#promote-campaign-modal'
            }).showCampaign();
        }));

        //listener for campaign promote completed execute. Change campaign item status from normal to completed
        $('#promote-campaign-modal').on('zionCompleteCampaignEvent', function (e, campaignInfo) {
            var campaignStatusHTML = $('<div>', {
                    'class': 'zion-campaign-item-status zion-campaign-item-COMPLETED'
                }),
                campaignItem = $('#campaign-list').find("[data-id='" + campaignInfo.campaignId + "']");
            campaignStatusHTML.append($('<span>', {
                'text': 'COMPLETED'
            }));

            campaignItem.append($('<div>', {
                'class': 'zion-campaign-item-cover zion-campaign-item-completed-cover'
            }));
            campaignItem.append(campaignStatusHTML);
        });

    });
})(jQuery);