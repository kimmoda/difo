/**
 * Zion campaign list service

 @param {object} config - store pass in config property
 eg:
 config = {
            resultSize: 10,
            loadingMethod: "AUTO",
            status: "ACTIVE"|"COMPLETED"|"EXPIRED"
        }

 loadingMethod value:
 "AUTO": allowed scroll window to load more campaign;
 "MANUAL": have load more button at bottom for click to load more campaign;
 "NONE": not allowed load more campaign;
 */


var campaignListService = function ($) {
    var $grid = zionImageReveal.init(),
        loading = $('.zion-loading').zionLoading(),
        isLoading = false,
        nextPageToken = null,
        url = zionUrls.rest_fetchCampaignList,
        config = {
            resultSize: 10,
            loadingMethod: "AUTO",
            isAdminPage: false,
            showDeleted: false
        },
        getData = function () {
            isLoading = true;
            loading.show();
            var options = {
                resultSize: config.resultSize ? config.resultSize : 20,
                nextPageToken: nextPageToken,
                showDeleted: config.showDeleted
            };
            if (config.authorId) {
                options.authorId = config.authorId;
            }
            if (config.status) {
                options.status = config.status;
            }
            zionWebService.getPub(url, options, function (resp) {
                var data = resp.data;
                jQuery.each(data, function (index, value) {
                    if (value.taskStatus === "ACTIVE") {
                        value.active = true;
                    }
                    if (!value.mine) {
                        if (value.taskStatus === "COMPLETED") {
                            value.completed = true;
                        } else if (value.taskStatus === "EXPIRED") {
                            value.expired = true;
                        }
                    } else {
                        if (config.isAdminPage) {
                            value.isMine = false;
                        } else {
                            value.isMine = value.taskStatus === "ACTIVE";
                        }
                    }
                });
                loading.hide();
                isLoading = false;
                nextPageToken = resp.nextPageToken;
                if (!nextPageToken) {
                    $('.load-more-container').hide();
                } else {
                    $('.load-more-container').show();
                }
                var itemsHTML = Handlebars.templates.campaignItem({looks: resp.data});
                $grid.masonryImagesReveal($(itemsHTML));
            });
        },
        addLoadMoreButton = function () {
            var html = '<div class="load-more-container" style="display: none">\n' +
                '                <div class="btn zion-btn-black load-more-button">\n' +
                '                    More\n' +
                '                </div>\n' +
                '            </div>';
            $('#campaign-list').parent().append(html);
        },
        loadMore = function () {
            if (!isLoading) {
                if (nextPageToken === null || nextPageToken) {
                    getData(nextPageToken);
                }
            }
        },
        init = function (opts) {
            config = $.extend({}, config, opts);
            getData();

            switch (config.loadingMethod) {
                case 'NONE':
                    break;
                case 'MANUAL':
                    addLoadMoreButton();
                    $('body').on('click', '.load-more-button', function () {
                        loadMore();
                    });
                    break;
                case 'AUTO':
                default:
                    commonService.winScroll(function () {
                        loadMore();
                    });
            }

        };

    return {
        init: init
    }
}(jQuery);
