var taskFinishedListService = function ($) {
    var loading = $('.zion-loading').zionLoading(),
        isLoading = false,
        nextPageToken = null,
        url = zionUrls.rest_fetchFinishedCampaign,
        userInfo = commonService.getUserInfo(),
        options = {
            resultSize: 10,
            target: '.finished-list',
            participantid: userInfo.id
        },
        getData = function () {
            var params = {
                participantid: userInfo.id,
                resultSize: options.resultSize,
                nextPageToken: nextPageToken
            };
            options.nextPageToken = nextPageToken;
            isLoading = true;
            loading.show();
            zionWebService.getPub(url, params, function (resp) {
                var data = resp.data,
                    listTaskDto = [];
                loading.hide();
                // Added Title
                if (nextPageToken === null && data.length > 0) {
                    $('.my-finished-task-container').show();
                }
                isLoading = false;
                nextPageToken = resp.nextPageToken;
                if (!nextPageToken) {
                    $('.history-load-more-container').hide();
                }

                for (var i = 0; i < resp.data.length; i++) {
                    var currentTask = resp.data[i];
                    listTaskDto.push({
                        task: {
                            id: currentTask.task.id,
                            favicon: currentTask.task.postUrlPreview.favicon,
                            title: currentTask.task.title
                        },
                        amount: currentTask.amount,
                        currency: webConfig.digitalCurrencyShortname
                    });
                }
                var itemsHTML = Handlebars.templates.finishedCampaignItem({task: listTaskDto});
                $(options.target).append(itemsHTML);
            });
        },
        addLoadMoreButton = function () {
            var html = '<div class="history-load-more-container">\n' +
                '                <div class="btn zion-btn-black history-load-more-button">\n' +
                '                    More\n' +
                '                </div>\n' +
                '            </div>';
            $(options.target).parent().append(html);
        },
        loadMore = function () {
            if (!isLoading) {
                if (nextPageToken === null || nextPageToken) {
                    getData(nextPageToken);
                }
            }
        },
        init = function (opts) {
            options = $.extend({}, options, opts);
            getData();
            if (options.manualLoadMore) {
                addLoadMoreButton();
                $('body').on('click', '.history-load-more-button', function () {
                    loadMore();
                })
            } else {
                commonService.winScroll(function () {
                    loadMore();
                });
            }
        };
    return {
        init: init
    }
}(jQuery);
