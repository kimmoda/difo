var simpleCampaignCardService = function () {
    var nextPageToken = null,
        loading = jQuery('.zion-loading').zionLoading(),
        initWithCampaignId = function (campaignId) {
            if (!campaignId) {
                return;
            }
            jQuery('#simple-campaign-modal').empty().modal({backdrop: 'static', keyboard: false, show: true});
            zionWebService.getPub(zionUrls.rest_fetchCampaign, {
                id: campaignId
            }, function (response) {
                if (response.status === 200 && response.data) {
                    var campaign = {
                            id: response.data.id,
                            title: response.data.title,
                            creationDate: response.data.creationDate,
                            creator: {
                                displayName: response.data.creator.displayName,
                                avatar: response.data.creator.person.avatar,
                                profileUrl: zionUrls.action_stylist_view + '?userId=' + response.data.creator.id
                            },
                            postUrlPreview: {
                                image: response.data.postUrlPreview.image,
                                favicon: response.data.postUrlPreview.favicon,
                                title: response.data.postUrlPreview.title,
                                description: response.data.postUrlPreview.description,
                                url: response.data.postUrlPreview.url
                            },
                            description: response.data.description,
                            taskType: {
                                description: response.data.taskType.description
                            },
                            disabled: response.data.taskStatus !== 'ACTIVE' || response.data.mine === true,
                            campaignStatus: _getCampaignStatus(response.data.taskStatus, response.data.enabled)
                        },
                        modal = Handlebars.templates.simpleCampaignCard(campaign);
                    jQuery('#simple-campaign-modal').append(modal);
                    getParticipantsByCampaignId(campaignId);
                } else {
                    jQuery('.zion-campaign-card-container').empty().append(Handlebars.templates.errorMessage());
                }
            }, function () {
                jQuery('.zion-campaign-card-container').empty().append(Handlebars.templates.errorMessage());
            });

            jQuery('#simple-campaign-modal').on('click', '#participants-load-more-button', jQuery.debounce(1000, true, function () {
                getParticipantsByCampaignId(campaignId);
            }));
        },
        getParticipantsByCampaignId = function (campaignId) {
            loading.show();
            zionWebService.getPub(zionUrls.rest_fetchCampaignParticipantList, {
                resultSize: 20,
                nextPageToken: nextPageToken,
                taskId: campaignId
            }, function (response) {
                loading.hide();
                if (response.status === 200 && response.data) {
                    var participantsHtml,
                        listTemplateDto = [];
                    for (var i = 0; i < response.data.length; i++) {
                        var currentTx = response.data[i];
                        listTemplateDto.push({
                            participant: {
                                avatar: currentTx.txTo.person.avatar,
                                displayName: currentTx.txTo.displayName,
                                profileUrl: zionUrls.action_stylist_view + '?userId=' + currentTx.txTo.id
                            },
                            amount: currentTx.amount + webConfig.digitalCurrencyShortname
                        });
                    }
                    participantsHtml = Handlebars.templates.participants({data: listTemplateDto});
                    jQuery('#campaign-participants').append(participantsHtml);
                    if (response.nextPageToken) {
                        nextPageToken = response.nextPageToken;
                        jQuery("#participants-load-more-button-container").removeClass('zion-hide');
                    } else {
                        jQuery("#participants-load-more-button-container").addClass('zion-hide');
                    }
                } else {
                    jQuery('.zion-campaign-card-container').empty().append(Handlebars.templates.errorMessage());
                }
            }, function () {
                loading.hide();
                jQuery('.zion-campaign-card-container').empty().append(Handlebars.templates.errorMessage());
            });
        },
        _getCampaignStatus = function (status, enabled) {
            if (!enabled){
                return{
                    value: 'Deleted',
                    cssClass: 'zion-label-error'
                }
            }

            if (status === 'EXPIRED') {
                return {
                    value: 'Expired',
                    cssClass: 'zion-label-warning'
                }
            }
        };

    return {
        initWithCampaignId: initWithCampaignId
    }
};

