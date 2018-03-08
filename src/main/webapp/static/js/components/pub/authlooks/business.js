var layoutFeed = function () {
    var hasData = false,
        isLoading = false,
        nextPageToken = null,
        feedService = zionFeedService(),
        isFirstLoading = true,
        $grid = zionImageReveal.init(),
        statusFilterContainer = jQuery('#looks-status-filter'),
        getFeedsCount = function () {
            zionWebService.getPub(zionUrls.rest_getMyFeedsCount, null, function (response) {
                var data = response.data,
                    html;
                if (data) {
                    html = Handlebars.templates.myLooksFilter({data: data});
                    statusFilterContainer.html(html);
                }
            })
        },
        uploadPhoto = function () {
            window.location.href = commonService.getZionActionUrl('/auth/looks/add/multi')
        },
        getFeedsList = function (pageToken, status) {
            isLoading = true;
            jQuery('.page-load-status').removeClass('hidden');
            var data = {'resultSize': 20};
            if (pageToken) {
                data['nextPageToken'] = nextPageToken;
            }
            if (status) {
                data['status'] = status;
            }
            zionWebService.getPub(zionUrls.rest_getMyFeedsList, data,
                function (response) {
                    var dataList = response.data;
                    if (dataList && dataList.length === 0 && nextPageToken === null) {
                        jQuery('.zion-stylist-content').hide();
                        jQuery('#looks-status-filter').hide();
                        var emptyHTML = Handlebars.templates.emptyLooksMessage();
                        jQuery('#empty-looks-container')
                            .empty()
                            .append(emptyHTML)
                            .on("click", "#add-looks-button", function () {
                                var userInfo = commonService.getUserInfo();
                                if (userInfo.argeeUploadPhotoCondition) {
                                    uploadPhoto();
                                } else {
                                    var modalHtml = Handlebars.templates.userTermsConditions();
                                    jQuery('#termsModal')
                                        .empty()
                                        .append(modalHtml)
                                        .modal({})
                                        .on("click", "#agree-conditions-button", function () {
                                            agree(function () {
                                                uploadPhoto();
                                            })
                                        });
                                }
                            });

                    } else {
                        nextPageToken = response['nextPageToken'];
                        if (response.data) {
                            if (response.data.length === 0) {
                                jQuery('.page-load-status').addClass('hidden');
                                return;
                            }
                            hasData = true;
                            var feeds = response.data;
                            var newData = [];
                            for (var i = 0; i < feeds.length; i++) {
                                var feed = feeds[i];
                                if (feed && feed.coverImage && feed.coverImage.url) {
                                    feed.coverImage.url = zionImageUtils().squareImage(feed.coverImage.url);
                                }
                                feed.isShowAuthor = false;
                                feed.isShowEditBtn = true;
                                feed.likeIcon = feed.like ? 'fa-heart' : 'fa-heart-o';
                                newData.push(feed);
                            }
                            if (isFirstLoading) {
                                newData.splice(0, 0, {
                                    isAddButton: true
                                });
                                isFirstLoading = false;
                            }
                            var itemsHTML = Handlebars.templates.feedcard({data: newData});
                            var $items = jQuery(itemsHTML);
                            $grid.masonryImagesReveal($items);
                        }
                        isLoading = false;
                        jQuery('.page-load-status').addClass('hidden');
                    }
                },
                function (data) {
                    jQuery('.page-load-status').addClass('hidden');
                    isLoading = false;
                    if (console && console.log) {
                        console.log(data);
                    }
                });
        },
        filteringHandler = function (status) {
            var listItems = jQuery('#feed-list .grid-item');
            $grid.masonry('remove', listItems);
            $grid.masonry('layout');
            isFirstLoading = true;
            getFeedsList(null, status);
        },
        agree = function (callback) {
            zionWebService.post(zionUrls.rest_term_conditions, JSON.stringify("UPLOAD_PHOTO"), function (response) {
                var data = response.data;
                if (callback && jQuery.isFunction(callback)) {
                    callback(data);
                }
            });
        };

    getFeedsCount();
    getFeedsList(null);

    commonService.winScroll(function () {
        if (!isLoading) {
            if (nextPageToken === null || nextPageToken) {
                getFeedsList(nextPageToken);
            }
        }
    });

    $grid.on('click', '.edit-button-container', function () {
        var feedId = jQuery(this).data('feedid');
        window.location.href = commonService.getZionActionUrl('/auth/looks/add/multi') + '?feedId=' + feedId;
    });

    $grid.on('click', '.feed-image', jQuery.debounce(1000, true, function () {
        var url = zionUrls.rest_getFeed + "?id=" + jQuery(this).attr("id");
        feedService.getfeedById(url);
    }));
    $grid.on('click', '.add-post-button', function () {
        uploadPhoto();
    });

    statusFilterContainer.on('click', '.look-final-link', function () {
        filteringHandler("FINAL");
    });

    statusFilterContainer.on('click', '.look-draft-link', function () {
        filteringHandler("DRAFT");
    });
}