var createCampaignService = function () {

    var $ = jQuery,
        options = {
            showParticipants: false
        },
        nextPageToken,
        canCreate,
        initData = {
            systemPaidMaxCoinAmount: 300,
            digitalCurrencySign: webConfig.digitalCurrencyShortname,
            appConfig: webConfig.appConfig
        },
        title,
        linkUrl,
        canPreview = false,
        methodSelected = false,
        campaignId,
        validationConfig = [
            {
                'target': '#campaign-title',
                'error': '#title-error',
                'minLength': 5,
                'maxLength': 200
            }],
        validationService = zionValidationService(),
        fetchBalance = function () {
            var dtd = $.Deferred();
            zionWebService.get(zionUrls.rest_balance, null, function (resp) {
                initData.balance = resp.data.balance;
                dtd.resolve();
            });
            return dtd;
        },
        fetchMethodTypeList = function () {
            var dtd = $.Deferred();
            zionWebService.getPub(zionUrls.rest_fetchCampaignMethodList, null, function (resp) {
                initData.subTasks = resp.data.subTypes;
                initData.cost = resp.data.cost;
                initData.lifespan = resp.data.lifespan;
                dtd.resolve();
            });
            return dtd;
        },
        fetchCampaignContent = function () {
            var dtd = $.Deferred();
            if (campaignId) {
                zionWebService.getPub(zionUrls.rest_fetchCampaign, {
                    id: campaignId
                }, function (resp) {
                    var data = resp.data;
                    initData.campaignName = data.title;
                    initData.campaignUrl = data.postUrl;
                    initData.campaignDesc = data.description;
                    initData.previewUrl = data.postUrlPreview.image;
                    initData.previewIcon = data.postUrlPreview.favicon;
                    initData.previewTitle = data.postUrlPreview.title;
                    initData.previewDesc = data.postUrlPreview.description;
                    initData.taskType = data.taskType.id;
                    canPreview = true;
                    dtd.resolve();
                });
            } else {
                dtd.resolve();
            }
            return dtd;
        },
        showPrev = function (url) {
            $('.preview-image').addClass('zion-hide');
            $('.preview-title').addClass('zion-hide');
            $('.preview-description').addClass('zion-hide');
            $('.preview-icon').addClass('zion-hide');
            $('.preview').removeClass('zion-hide');
            $('#link-error').addClass('zion-hide');
            $('#basic-url').removeClass('zion-input-error-indicator');
            zionLoadingService().show('.preview');
            getMetaData(url, function (resp) {
                    zionLoadingService().stop('.preview');
                    $('.preview-title').html(resp.title).removeClass('zion-hide');
                    if (resp.description) {
                        $('.preview-description').html(resp.description).removeClass('zion-hide');
                        $('.preview-title').removeClass('margin-bottom-0')
                    } else {
                        $('.preview-description').addClass('zion-hide');
                        $('.preview-title').addClass('margin-bottom-0')
                    }
                    $('.preview-icon').attr("src", resp.favicon).removeClass('zion-hide');
                    if (resp.image) {
                        $('.preview-image').attr("src", resp.image).removeClass('zion-hide');
                    } else {
                        $('.preview-image').addClass('zion-hide');
                    }
                    canPreview = true;
                },
                function () {
                    $('.preview').addClass('zion-hide');
                    $('#link-error').removeClass('zion-hide');
                    $('#basic-url').addClass('zion-input-error-indicator');
                    zionLoadingService().stop('.preview');
                    canPreview = false;
                })
        },
        hidePrev = function () {
            $('.preview').addClass('zion-hide');
            $('#link-error').removeClass('zion-hide');
            $('#basic-url').addClass('zion-input-error-indicator');
            canPreview = false;
        },
        addListener = function () {
            $('#campaign-title').focusout(function () {
                $(this).val($.trim($(this).val()));
                title = $(this).val();
            });

            $('.url-input').focusout(function () {
                var url = $.trim($(this).val());
                if (linkUrl === url) {
                    return;
                }
                if (url.length > 0) {
                    if (!zionStringUtils.isUrl(url)) {
                        url = "http://" + url;
                    }
                    $(this).val(url);
                    linkUrl = url;
                    showPrev(url);
                } else {
                    linkUrl = "";
                    hidePrev();
                }
            })
                .on('input', function () {
                    canPreview = false;
                });

            $('.cancel').click(function () {
                window.location.href = zionUrls.action_campaign;

            });

            $('.submit').click(function () {
                if (!canCreate) {
                    return;
                }
                if (!isVerified()) {
                    return;
                }
                var taskInfo = {
                    title: $.trim($('#campaign-title').val()),
                    postUrl: $.trim($('.url-input').val()),
                    taskTypeId: $(".promotion-methods input[type='radio']:checked").val(),
                    description: $.trim($('#description').val())
                };
                if (campaignId) {
                    taskInfo.id = campaignId;
                    update(taskInfo, function (resp) {
                        finishedJob(resp);
                    });
                } else {
                    create(taskInfo, function (resp) {
                        finishedJob(resp)
                    })
                }

            });

            $('.not-enough-btn').click(function () {
                window.location.replace(zionUrls.action_campaign);
            });

            $('.create-campaign').on('click', '#participants-load-more-button', function () {
                loadParticipants();
            })
        },
        finishedJob = function (resp) {
            switch (resp.status) {
                case 200: {
                    $('#create_campaign').modal('hide');
                    // messageService.show("Congratulations", "success", function () {
                    //     messageService.hide();
                    window.location.reload();
                    // })
                    break;
                }
                case 404: {
                    $('.url-input').addClass('zion-input-error-indicator');
                    break;
                }
                default: {
                    $('.url-input').addClass('zion-input-error-indicator');
                    break;
                }
            }

        },
        create = function (taskInfo, callback) {
            if (taskInfo) {
                zionLoadingService().show('.create-campaign');
                zionWebService.post(zionUrls.rest_createCampaign, taskInfo, function (resp) {
                    zionLoadingService().stop('.create-campaign');
                    if (callback && jQuery.isFunction(callback)) {
                        callback(resp);
                    }
                });
            }
        },
        update = function (taskInfo, callback) {
            if (taskInfo) {
                zionLoadingService().show('.create-campaign');
                zionWebService.post(zionUrls.rest_updateCampaign, taskInfo, function (resp) {
                    zionLoadingService().stop('.create-campaign');
                    if (callback && jQuery.isFunction(callback)) {
                        callback(resp);
                    }
                });
            }
        },
        init = function (opts) {
            options = $.extend({}, options, opts);
            if (opts && opts.id) {
                campaignId = opts.id;
            }
            $(this).zionSocialLogin({
                loginSuccessCallback: function (resp) {
                    if (!isInfluencer()) {
                        window.location.replace(commonService.getZionHomeUrl() + '/apply');
                        return;
                    }
                    $.when(fetchBalance(), fetchMethodTypeList(), fetchCampaignContent()).done(function () {
                        var html = Handlebars.templates.createCampaign(initData);
                        $('#create_campaign').empty().append(html).modal('show');
                        addListener();
                        validationService.registerFieldEvents(validationConfig);
                        if (campaignId) {
                            canCreate = true;
                            $('.preview').removeClass('zion-hide');
                            $(".promotion-methods input[type='radio']").val([initData.taskType]);
                            if (initData.previewUrl) {
                                $('.preview-image').removeClass('zion-hide');
                            }
                            $('.submit').text('Update Campaign');
                            $('.edit-title').text('Edit Campaign');
                            //todo Insert campaign participants
                            if (options.showParticipants) {
                                var html = "<div class=\"campaign-history-container\">\n" +
                                    "                    <h4 class=\"zion-campaign-subtitle\">Campaign participants</h4>\n" +
                                    "                    <div id=\"campaign-participants\"></div>\n" +
                                    "                    <div class=\"load-more-button-container zion-hide\" id=\"participants-load-more-button-container\">\n" +
                                    "                        <span class=\"load-more-button\" id=\"participants-load-more-button\">View all participants</span>\n" +
                                    "                    </div>\n" +
                                    "                </div>";
                                $('.create-campaign').append(html);
                                loadParticipants();
                            }
                        } else {
                            if (initData.balance < initData.cost) {
                                canCreate = false;
                                $('.canDisable').attr("readonly", "readonly");
                                $('.create-campaign-container').addClass('zion-disabled');
                                $('.not-enough').removeClass('zion-hide');
                            } else {
                                canCreate = true;
                            }
                        }
                        autosize($('#description'));
                    })
                }
            }).show();
        },
        loadParticipants = function () {
            zionLoadingService().show('#campaign-participants');
            zionWebService.getPub(zionUrls.rest_fetchCampaignParticipantList, {
                resultSize: 10,
                nextPageToken: nextPageToken,
                taskId: campaignId
            }, function (response) {
                zionLoadingService().stop('#campaign-participants');
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
                jQuery('.zion-campaign-card-container').empty().append(Handlebars.templates.errorMessage());
            });
        },
        isVerified = function () {

            if (!validationService.validFields(validationConfig)) {
                return;
            }

            if (!canPreview) {
                $('.url-input').addClass('zion-input-error-indicator');
                $('#link-error').removeClass('zion-hide');
                return false;
            } else {
                $('.url-input').removeClass('zion-input-error-indicator');
            }
            methodSelected = $(".promotion-methods input[type='radio']:checked").val();
            if (methodSelected === undefined) {

                //todo prompt the user
                return false;
            }
            return true;
        },
        isInfluencer = function () {
            var me = commonService.getUserInfo(),
                userStatus = me.registrationStatus;
            return userStatus === 'APPROVED';
        },
        getMetaData = function (urlStr, callback, failed) {
            var urlEncoded = encodeURIComponent(urlStr);
            zionWebService.get(zionUrls.rest_preview, {url: urlEncoded}, function (resp) {
                switch (resp.status) {
                    case 200: {
                        if (resp.data) {
                            if (callback && jQuery.isFunction(callback)) {
                                callback(resp.data);
                            }
                        }
                        break;
                    }
                    case 404: {
                        if (failed && jQuery.isFunction(failed)) {
                            failed();
                        }
                        break;
                    }
                    default: {
                        if (failed && jQuery.isFunction(failed)) {
                            failed();
                        }
                        break;
                    }
                }
            })

        };

    return {
        init: init
    }
};