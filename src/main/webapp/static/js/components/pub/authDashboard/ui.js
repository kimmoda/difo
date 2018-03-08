jQuery.noConflict();
(function ($) {
    var $grid = zionImageReveal.init(),
        currentStatus = null,
        feedService = zionFeedService(),
        initSocailShareSection = function () {
            dashboardService.getInfo(function (resp) {
                var likeCount = resp.feedLikeCount,
                    sharedCount = resp.feedSharedCount,
                    viewedCount = resp.feedViewedCount,
                    clickedCount = resp.shortUrlClickCount,
                    userSocialSharedUrl = zionUrls.rest_user_social_share_urls.replace("{userId}", resp.id);
                //setup the feed looks share plugin
                zionWebService.getPub(userSocialSharedUrl, {}, function (response) {
                    jQuery('#share-look-feed-plugin-container').socialshare({shareUrlsConfig: response.data}, undefined);
                });
                $('.look-feed-icon, .look-feed-bt').on('click', function () {
                    var url = zionUrls.action_stylist_view + '?userId=' + resp.id + '&source=tinyurl',
                        win = window.open(url, '_blank');
                    //need to check. some browser setting may block the js open new tab.
                    if (win) {
                        win.focus();
                    } else {
                        window.location.replace(url);
                    }
                });
                $('#like-count').text(likeCount ? likeCount : '0');
                $('#shared-count').text(sharedCount ? sharedCount : '0');
                $('#viewed-count').text(viewedCount ? viewedCount : '0');
                $('#clicked-count').text(clickedCount ? clickedCount : '0');
            });
        },
        setDraftCount = function () {
            dashboardService.getDrafeCount(function (resp) {
                var draftCount = resp.draftCount,
                    publishedCount = resp.finalCount;
                $('#draft-count').text(draftCount ? draftCount : '0');
                $('#published-count').text(publishedCount ? publishedCount : '0');
            })
        },
        setLooksData = function () {
            $('.page-load-status').removeClass('hidden');
            dashboardService.getFeed(currentStatus, function (resp) {
                if (resp.length > 0) {
                    resp.splice(0, 0, {
                        isAddButton: true
                    });
                    var itemsHTML = Handlebars.templates.feedcard({data: resp});
                    var $items = $(itemsHTML);
                    $grid.masonryImagesReveal($items);
                } else {
                    var emptyHTML = Handlebars.templates.emptyLooksMessage();
                    $('#empty-looks-container')
                        .empty()
                        .append(emptyHTML)
                        .on("click", "#add-looks-button", function () {
                            zionGAEventService('feed', 'add', 'dashboard').track();
                            var userInfo = commonService.getUserInfo();
                            if (userInfo.argeeUploadPhotoCondition) {
                                uploadPhoto();
                            } else {
                                var modal = Handlebars.templates.userTermsConditions();
                                $('#myModal')
                                    .empty()
                                    .append(modal)
                                    .modal({})
                                    .on("click", "#agree-conditions-button", function () {
                                        zionGAEventService('feed', 'agreement', 'dashboard').track();
                                        dashboardService.agree(function () {
                                            uploadPhoto();
                                        })
                                    });
                            }
                        });
                    $('#my-latest-looks').hide();
                }

                $('.page-load-status').addClass('hidden');
            });
        },
        uploadPhoto = function () {
            window.location.href = commonService.getZionActionUrl('/auth/looks/add/multi');

        },
        initPage = function () {
            initSocailShareSection();
            setDraftCount();
            setLooksData(currentStatus);
        };

    $('#my-latest-looks').click(function () {
        if (currentStatus === null) {
            return;
        }
        var listItems = jQuery('#feed-list .grid-item');
        $grid.masonry('remove', listItems);
        $grid.masonry('layout');
        currentStatus = null;
        setLooksData();
    });

    $('#draft-button').click(function () {
        if (currentStatus === "DRAFT") {
            return;
        }
        var listItems = jQuery('#feed-list .grid-item');
        $grid.masonry('remove', listItems);
        $grid.masonry('layout');
        currentStatus = "DRAFT";
        setLooksData();
    });

    $('#published-button').click(function () {
        if (currentStatus === "FINAL") {
            return;
        }
        var listItems = jQuery('#feed-list .grid-item');
        $grid.masonry('remove', listItems);
        $grid.masonry('layout');
        currentStatus = "FINAL";
        setLooksData();
    });

    $grid.on('click', '.edit-button-container', function (e) {
        e.stopPropagation();
        var feedId = jQuery(this).data('feedid');
        window.location.href = commonService.getZionActionUrl('/auth/looks/add/multi') + '?feedId=' + feedId;
    });

    $grid.on('click', '.feed-image', $.debounce(1000, true, function () {
        var url = zionUrls.rest_getFeed + "?id=" + $(this).attr("id");
        feedService.getfeedById(url);
    }));

    $grid.on('click', '.add-post-button', function () {
        uploadPhoto();
    });

    initPage();


})(jQuery);