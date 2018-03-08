jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var promoteCampaignService = zionPromoteCampaignService({
            campaignId: campaignId,
            pageContainerId: '#shared-campaign',
            modalContainerId: '#shared-campaign-processing-modal'
        });
        promoteCampaignService.showCampaign();

        //when claim reward and close modal, page will reload to update campaign status
        $('#shared-campaign-processing-modal').on('zionCompleteCampaignEvent', function (e, campaignInfo) {
            var sharedCampaignContainer = $('#shared-campaign'),
                campaignStatus = promoteCampaignService.getCampaignStatus("COMPLETED", false);

            promoteCampaignService.initParticipantList();
            sharedCampaignContainer.find('.label').addClass(campaignStatus.cssClass).text(campaignStatus.value);
            sharedCampaignContainer.find('.promote-action-section').addClass('zion-disabled');
            sharedCampaignContainer.find('.zion-campaign-promote-btn').prop('disabled', true);
        });

        $('#shared-campaign').on('click', ".homepage-btn", function () {
            window.location.replace(commonService.getZionHomeUrl());
        })
    });
})(jQuery);