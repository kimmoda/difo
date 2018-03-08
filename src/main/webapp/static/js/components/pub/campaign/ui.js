jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var nextPageToken;
        campaignListService.init({
            resultSize: 20,
            status: "ACTIVE",
            showDelete:false
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
        
        $('#active-campaign-link').on('click', function() {
            window.location.replace(zionUrls.action_campaign);
        });
        
        $('#expired-campaign-link').on('click', $.debounce(1000, true, function(e) {
            e.preventDefault();
            $('#expired-campaign-list-modal').empty();
            $('#expired-campaign-list-modal').modal({
                backdrop: 'static',
                keyboard: false,
                show: true
            });
            zionLoadingService().show('body');
            campaignListUIService.getExpiredCampaignList({resultSize: 10}, function(campaignListWrapper) {
                zionLoadingService().stop('body');
                nextPageToken = campaignListWrapper.nextPageToken;
                var modalDataHtml = Handlebars.templates.expiredCampaignList({data: campaignListWrapper.data}),
                    modalHtml = Handlebars.templates.expiredCampaignModal({nextPageToken: nextPageToken});
                $('#expired-campaign-list-modal').append(modalHtml);
                $('#expired-campaign-list-modal .zion-expired-campaigns-container').append(modalDataHtml);
            });
            $('#expired-campaign-list-modal').on('click', '.show-more-expired-campaign-bt', function(e) {
                e.preventDefault();
                $('.show-more-expired-campaign-bt').prop('disabled', true);
                campaignListUIService.getExpiredCampaignList({nextPageToken: nextPageToken}, function(campaignListWrapper) {
                    $('.show-more-expired-campaign-bt').prop('disabled', false);
                    var modalDataHtml = Handlebars.templates.expiredCampaignList({data: campaignListWrapper.data});
                    nextPageToken = campaignListWrapper.nextPageToken;
                    if (!nextPageToken) {
                        $(".show-more-expired-campaign-bt").addClass('zion-hide');
                    }
                    $('#expired-campaign-list-modal .zion-expired-campaigns-container').append(modalDataHtml);
                });
            });
        }));
        
        $('#expired-campaign-list-modal').on('hidden.bs.modal', function (e) {
            $('#expired-campaign-list-modal').off('click').empty();
        });
    });
})(jQuery);
