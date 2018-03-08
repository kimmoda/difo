/**
 * @param config
 *
 * config = config ? config : {
            isShowAuthor: false,
            pub: true,
            loadStatus: '.page-load-status',
            noData: '.no-data',
            userId: optional,
            url: listFeedsUrl
        }
 * @param emptyCallback
 */
var layoutFeed = function (conf, emptyCallback) {
    var hasData = false,
        isLoading = false,
        nextPageToken = null,
        $grid = zionImageReveal.init(),
        feedServiceConfig = {
            showViewButton: false,
            modalId: '#myModal',
            feedListId: '#feed-list',
            commentInputId: '#comments-input',
            commentsContainerId: '#comment-container',
            commentContainerId: '#comments',
            sendCommentBtnId: '#comment-send-button',
            loadMoreBtnId: '#load-more-button',
            likeBtnId: '#like-it-button',
            shareContainer: '.share-container',
            commentSendContainer: '.comment-send-container',
            signInPrompt: '.signin-prompt',
            deleteLookCard: false,
            leftImage: '.annotation-container',
            description: '.description',
            descMore: '#description-more-button'
        },
        feedService = zionFeedService(feedServiceConfig),
        config = {
            isShowAuthor: false,
            pub: true,
            loadStatus: '.page-load-status',
            noData: '.no-data',
            url: listFeedsUrl,
            displayFeedShowMoreBtn: false
        },
        getFeedsList = function (pageToken) {
            isLoading = true;
            jQuery(config.loadStatus).removeClass('hidden');
            var data = {'resultSize': 20};
            if (pageToken) {
                data['nextPageToken'] = nextPageToken;
            }
            if (config.userId) {
                data['authorId'] = userID;
            }
            data['authorRoles'] = "DESIGNER,STYLIST,INFLUENCER,DEFAULT";
            if (config.pub) {
                zionWebService.getPub(config.url, data,
                    function (response) {
                        processData(response);
                    },
                    function (data) {
                        jQuery(config.loadStatus).addClass('hidden');
                        isLoading = false;
                        if (console && console.log) {
                            console.log(data);
                        }
                    });
            } else {
                zionWebService.get(config.url, data,
                    function (response) {
                        processData(response);
                    },
                    function (data) {
                        jQuery(config.loadStatus).addClass('hidden');
                        isLoading = false;
                        if (console && console.log) {
                            console.log(data);
                        }
                    });
            }
        },
        processData = function (response) {
            if (!response.data && nextPageToken === null) {
                if (emptyCallback && jQuery.isFunction(emptyCallback)) {
                    jQuery(config.loadStatus).addClass('hidden');
                    emptyCallback();
                    return;
                }
            }
            if (response.data) {
                jQuery('#looks-count').removeClass('zion-hide');
            } else {
                jQuery('#looks-count').addClass('zion-hide');
            }
            if (response['nextPageToken']) {
                nextPageToken = response['nextPageToken'];
                if (config.displayFeedShowMoreBtn) {
                    jQuery('#show-more-look-btn').removeClass('zion-hide');
                }
            } else {
                nextPageToken = null;
                if (config.displayFeedShowMoreBtn) {
                    jQuery('#show-more-look-btn').addClass('zion-hide');
                }
            }
            if (response.data) {
                if (response.data.length === 0) {
                    jQuery(config.loadStatus).addClass('hidden');
                    if (!hasData) {
                        jQuery(config.noData).removeClass('hidden');
                    }
                    return;
                }
                hasData = true;
                var feeds = response.data;
                var newData = [];
                for (var i = 0; i < feeds.length; i++) {
                    var feed = feeds[i];
                    if (feed && feed.coverImage && feed.coverImage.url) {
                        if (config.squareImage) {
                            feed.coverImage.url = zionImageUtils().squareImage(feed.coverImage.url);
                        } else {
                            feed.coverImage.url = feedService.getImageUrl(feed.coverImage.url);
                        }
                    }
                    feed.isShowAuthor = config.isShowAuthor;
                    feed.likeIcon = feed.like ? 'fa-heart like' : 'fa-heart-o dislike';
                    newData.push(feed);
                }
                var itemsHTML = Handlebars.templates.feedcard({data: newData});
                var $items = jQuery(itemsHTML);
                $items.find('.zion-feed-like-plugin-container').each(function (index) {
                    jQuery(this).zionFeedLikePlugin({
                        id: jQuery(this).data('feed-like-id'),
                        likeCount: jQuery(this).data('feed-like-count'),
                        like: jQuery(this).data('feed-like'),
                        fireToggleFeedLikeStatus: true
                    });
                });
                $grid.masonryImagesReveal($items);
            }
            isLoading = false;
            jQuery(config.loadStatus).addClass('hidden');
        },
        hasFeedItems = function () {
            return jQuery('#feed-list').find('.grid-item').not('.zion-hide').length > 0;
        };
    if (conf) {
        jQuery.extend(config, conf);
    }

    getFeedsList(null);

    if (config.displayFeedShowMoreBtn) {
        jQuery('#show-more-look-btn').on('click', jQuery.debounce(1000, true, function () {
            if (nextPageToken) {
                getFeedsList(nextPageToken);
            }
        }));
    } else {
        commonService.winScroll(function () {
            if (!isLoading && nextPageToken) {
                getFeedsList(nextPageToken);
            }
        });
    }

    jQuery('.zion-wrapper').on('zionFeedLikeStatus', '.zion-modal-feed-like-container, .zion-feed-like-plugin-container', function (e, feedInfo) {
        if (config.deleteLookCard) {
            var element = jQuery(".grid-item[data-feed-id='" + feedInfo.feedId + "']");
            if (feedInfo.feedLikeStatus === true) {
                element.removeClass('zion-hide');
            } else {
                element.addClass('zion-hide');
            }
            $grid.masonry('layout');

            //Show and hide the no feed item message.
            if (hasFeedItems()) {
                jQuery('.zion-stylist-complete-container').addClass('zion-hide');
            } else {
                jQuery('.zion-stylist-complete-container').removeClass('zion-hide');
            }
        }
    });

    zionFeedLikePluginUtils.addChangeFeedLikeStatusEvent(jQuery('#feed-list'));

    $grid.on('click', '.feed-image', jQuery.debounce(1000, true, function () {
        var url = getFeedUrl + "?id=" + jQuery(this).attr("id"),
            feed = this;
        feedService.getfeedById(url);
    }));


    $grid.on('click', '.author-info-container', function () {
        var userId = jQuery(this).data('user-id');
        if (userId) {
            window.location.href = stylistUrl + '?userId=' + userId;
        }
    });
}