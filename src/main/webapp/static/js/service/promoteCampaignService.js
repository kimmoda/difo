/**
 *
 * @param {object} options - store campaignId, modalContainerId and pageContainerId. Only display campaign in page will have pageContainerId.
 * @example  {
         *      campaignId: campaignId,
                modalContainerId: '#shared-campaign-processing-modal',
                pageContainerId: '#shared-campaign'
         * }
 */
var zionPromoteCampaignService = function (options) {
    var nextPageToken = null,
        //constant variable should not be updated.
        CAMPAIGN_ID = options.campaignId,
        INPAGE = options.pageContainerId ? true : false,
        MODAL_CONTAINER = jQuery(options.modalContainerId),
        CAMPAIGN_CONTAINER = INPAGE ? jQuery(options.pageContainerId) : MODAL_CONTAINER,
        _getCampaignStatus = function (status, isMine) {
            if (status === 'EXPIRED') {
                return {
                    value: 'Expired',
                    cssClass: 'zion-label-warning'
                }
            }
            if (status === 'COMPLETED') {
                return {
                    value: 'Completed',
                    cssClass: 'zion-label-success'
                }
            }
            if (status === 'ACTIVE' && isMine === true) {
                return {
                    value: 'Mine',
                    cssClass: 'zion-label-success'
                };
            }
        },
        _initCampaign = function (data) {
            return {
                id: data.id,
                title: data.title,
                creationDate: data.creationDate,
                creator: {
                    displayName: data.creator.displayName,
                    avatar: data.creator.person.avatar,
                    profileUrl: zionUrls.action_stylist_view + '?userId=' + data.creator.id
                },
                postUrlPreview: {
                    image: data.postUrlPreview.image,
                    favicon: data.postUrlPreview.favicon,
                    title: data.postUrlPreview.title,
                    description: data.postUrlPreview.description,
                    url: data.postUrlPreview.url
                },
                description: data.description,
                taskType: {
                    name: data.taskType.name,
                    description: data.taskType.description,
                    isShare: data.taskType.name === "SHARE"
                },
                campaignStatus: _getCampaignStatus(data.taskStatus, data.mine),
                coinName: webConfig.digitalCurrencyShortname,
                disabled: data.taskStatus !== 'ACTIVE' || data.mine === true,
                showOriUrl: data.taskStatus !== 'ACTIVE' || data.mine === true || data.taskType.name === "SHARE",
                isMine: data.mine,
                isDeleted: !data.enabled,
                appConfig: webConfig.appConfig
            };
        },
        _openModal = function () {
            if (!MODAL_CONTAINER.hasClass('in')) {
                MODAL_CONTAINER.modal({
                    backdrop: 'static',
                    keyboard: false,
                    show: true
                });
                MODAL_CONTAINER.on('hidden.bs.modal', function (e) {
                    //remove all direct click or delegate click events but not other events.
                    //Because we an event like zionCompleteCampaignEvent is registered, which should not be removed.
                    //If you register any event or delegate events from this modal, please remove it before init it.
                    MODAL_CONTAINER.off('click').empty();
                });
            }
        },
        _showModalHeaderActions = function () {
            var topBar = MODAL_CONTAINER.find('.top-bar'),
                closeBtn = MODAL_CONTAINER.find('.modal-close-btn');
            topBar.removeClass('zion-hide');
            closeBtn.removeClass('zion-hide');
        },
        _hideModalHeaderActions = function () {
            var topBar = MODAL_CONTAINER.find('.top-bar'),
                closeBtn = MODAL_CONTAINER.find('.modal-close-btn');
            topBar.addClass('zion-hide');
            closeBtn.addClass('zion-hide');
        },
        _redirectToClaimReward = function () {
            var modalContentContainerNode = MODAL_CONTAINER.find('.zion-campaign-card-container'),
                randomMills = Math.random() * (15000 - 5000) + 5000;
            _openModal();
            _hideModalHeaderActions();
            modalContentContainerNode.empty().append(Handlebars.templates.campaignPromoteProcessLoading());
            window.setTimeout(function () {
                modalContentContainerNode.empty().append(Handlebars.templates.campaignPromoteProcessClaimReward());
            }, randomMills);
        },
        _redirectToErrorMessage = function (msg) {
            _openModal();
            _showModalHeaderActions();
            MODAL_CONTAINER.find('.zion-campaign-card-container').empty().append(Handlebars.templates.errorMessage({message: msg}));
        },
        _getParticipantsByCampaignId = function () {
            var loadingErrorNode = CAMPAIGN_CONTAINER.find('.loading-participants-error'),
                participantListContainer = CAMPAIGN_CONTAINER.find('.campaign-participants'),
                loadMoreBtn = CAMPAIGN_CONTAINER.find(".participants-load-more-button-container"),
                loadingErrorMsg = "Sorry! System may be too busy, Please refresh your page and try again",
                loading = CAMPAIGN_CONTAINER.find('.zion-loading').zionLoading();
            loading.show();
            loadingErrorNode.addClass('zion-hide');
            zionWebService.getPub(zionUrls.rest_fetchCampaignParticipantList, {
                resultSize: 20,
                nextPageToken: nextPageToken,
                taskId: CAMPAIGN_ID
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
                    participantListContainer.append(participantsHtml);
                    if (response.nextPageToken) {
                        nextPageToken = response.nextPageToken;
                        loadMoreBtn.removeClass('zion-hide');
                    } else {
                        loadMoreBtn.addClass('zion-hide');
                    }
                } else {
                    loadingErrorNode.removeClass('zion-hide');
                    loadingErrorNode.empty().text(loadingErrorMsg);
                }
            }, function () {
                loading.hide();
                loadingErrorNode.removeClass('zion-hide');
                loadingErrorNode.empty().text(loadingErrorMsg);
            });
        },
        _initSharePlugin = function () {
            var socialSharedUrl = zionUrls.rest_campaign_social_share_urls.replace("{campaignId}", CAMPAIGN_ID);
            zionWebService.getPub(socialSharedUrl, {}, function (response) {
                var pluginNode = CAMPAIGN_CONTAINER.find('.zion-campaign-social-share-container');
                pluginNode.socialshare({shareUrlsConfig: response.data}, undefined);
                CAMPAIGN_CONTAINER.find('.campaign-share-btn').click(function () {
                    pluginNode.slideToggle(400);
                })
            });
        },
        _initParticipantList = function () {
            CAMPAIGN_CONTAINER.find(".campaign-participants").empty().off('click');
            _getParticipantsByCampaignId();
            CAMPAIGN_CONTAINER.on('click', '.participants-load-more-button', jQuery.debounce(1000, true, function () {
                _getParticipantsByCampaignId();
            }));
        },
        _getCampaign = function () {

            //remove all direct click or delegate click events but not other events.
            //Because we an event like zionCompleteCampaignEvent is registered, which should not be removed.
            //If you register any event or delegate events from this modal, please remove it before init it.
            CAMPAIGN_CONTAINER.off('click').empty();
            zionLoadingService().show('body');
            zionWebService.getPub(zionUrls.rest_fetchCampaign, {
                id: CAMPAIGN_ID
            }, function (response) {
                zionLoadingService().stop('body');
                if (response.status === 200 && response.data) {
                    var disabled = response.data.taskStatus !== 'ACTIVE' || response.data.mine === true,
                        isShare = response.data.taskType.name === "SHARE",
                        campaign = _initCampaign(response.data),
                        campaignHtml = INPAGE ? Handlebars.templates.sharedCampaign(campaign) : Handlebars.templates.campaignCard(campaign),
                        campaignUrl = response.data.postUrl;
                    CAMPAIGN_CONTAINER.empty().append(campaignHtml);
                    if (INPAGE !== true) {
                        _openModal();
                    }
                    _initParticipantList();
                    _initSharePlugin();
                    if (!disabled) {
                        CAMPAIGN_CONTAINER.on('click', '.zion-campaign-promote-btn', function () {
                            if (isShare) {
                                FB.ui({method: 'share', href: campaignUrl}, function (response) {
                                    if (response && !response.error_code) {
                                        _redirectToClaimReward();
                                    }
                                });
                            } else {
                                _redirectToClaimReward();
                            }

                        });
                    }
                } else {
                    _redirectToErrorMessage();
                }
            }, function () {
                zionLoadingService().stop('body');
                _redirectToErrorMessage();
            });
        },
        _registerEvents = function () {
            MODAL_CONTAINER.find('.promote-campaign-claim-btn').prop('disabled', false);
            MODAL_CONTAINER.on('click', '.promote-campaign-claim-btn', jQuery.debounce(1000, true, function () {
                _showModalHeaderActions();
                if (commonService.isLoggedIn()) {
                    jQuery('.promote-campaign-claim-btn').prop('disabled', true);
                }
                zionWebService.post(zionUrls.rest_createCampaignTransaction, {
                    taskId: CAMPAIGN_ID
                }, function (response) {
                    if (response.status === 200 && response.data) {
                        //campaign promote completed need to change campaign item status outside of campaign detail page;
                        //zionCompleteCampaignEvent will be fired with campaign id for listener to identify which campaign item element need to change status
                        //campaign home page and campaign list page's campaign items need to listen zionCompleteCampaignEvent and change campaign status to completed
                        var data = response.data;
                        MODAL_CONTAINER.trigger('zionCompleteCampaignEvent', {campaignId: CAMPAIGN_ID});
                        MODAL_CONTAINER.find('.zion-campaign-card-container').empty().append(Handlebars.templates.campaignPromoteProcessFinal({
                            earnedCoinAmount: data.amount,
                            coinName: webConfig.digitalCurrencyShortname
                        }));
                        window.setTimeout(function () {
                            jQuery('.flipper').find('.front').addClass('flip-front');
                            jQuery('.flipper').find('.back').addClass('flip-back');
                        }, 1000);
                    } else {
                        _redirectToErrorMessage();
                    }
                }, function (response) {
                    if (response.status === 410) {
                        _redirectToErrorMessage("This campaign has expired.");
                    } else if (response.status === 409) {
                        _redirectToErrorMessage("You cannot promote your own campaign for rewards. Please promote another influencers' campaigns.");
                    } else if (response.status === 304) {
                        _redirectToErrorMessage("You already participated in this campaign and claimed your reward. You cannot promote campaigns more than once.");
                    } else {
                        _redirectToErrorMessage();
                    }
                });
            }));
        },
        _showCampaignDetail = function () {
            if (!CAMPAIGN_ID) {
                return;
            }
            _getCampaign();
            _registerEvents();
        };

    return {
        showCampaign: _showCampaignDetail,
        initParticipantList: _initParticipantList,
        getCampaignStatus: _getCampaignStatus
    }
};

