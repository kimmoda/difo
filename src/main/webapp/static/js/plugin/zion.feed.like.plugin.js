/**
 * Zion feed like plugin
 * @param {object} data - store feed id, feed like count, feed like status and config param fireToggleFeedLikeStatus.
 *        fireToggleFeedLikeStatus determine when to fire zionFeedLikeStatus event.
 *        Which case need fire zionFeedLikeStatus event: in feed modal view change feed like status at the same time need change responsive feed card like status.
 *        No need fire zionFeedLikeStatus event: directly change feed like status in feed card list view.
 * @example data = {
            id: feed.id,
            likeCount: feed.likeCount,
            like: feed.like,
            fireToggleFeedLikeStatus: false
 * }
 *
 * @event zionFeedLikeStatus - fire event when change feed like status and pass feed id and feed like status to listen event.
 *
 * @example jQuery('.element').on('zionFeedLikeStatus', function (e, feedInfo) {
 *      ......
 * });

    @param {object} feedInfo - store feed id and feed like status

    @example feedInfo: {
        feedId: config.id,
        feedLikeStatus: false
    }
 */

(function ($) {
    $.fn.zionFeedLikePlugin = function (data) {
        var config = {
                fireToggleFeedLikeStatus: true
            },
            likeContainer = $(this),
            likeIconHtml;
        if (!data) {
            throw new Error("can not create feed like plugin, passing data is invalid");
        }
        if (data) {
            jQuery.extend(config, data);
        }
        likeIconHtml =  $('<i>', {
            'class': 'zion-feed-like-icon fa',
            'data-like-feed-id': config.id
        });

        likeIconHtml.addClass(config.like ? 'fa-heart like' : 'fa-heart-o dislike');
        likeIconHtml.append($('<span>', {
            'class': 'zion-feed-like-count',
            'text': config.likeCount
        }));
        likeContainer.append(likeIconHtml);

        //click event for user click like icon
        likeContainer.on('click', '.zion-feed-like-icon', $.debounce(1000, true, function () {
            var logStatus = commonService.getJwtToken()? 'signedin' : 'notsignedin',
                feedLikeStatus = $(this).hasClass('like');
            zionGAEventService('feed', 'like', logStatus).track();
            likeContainer.zionSocialLogin({
                isCallbackTriggerAfterLogin: false,
                loginSuccessCallback: function () {
                    zionWebService.post(webConfig.restUrl + '/userfeed/v1/like', {
                        'feedId': config.id,
                        'status': 'LIKE'
                    }, function (resp) {
                       //TODO add GAE tracking.

                    }, function (resp) {
                        //TODO add GAE tracking.
                    });
                    zionFeedLikePluginUtils.toggleLikeUIStatus(likeContainer, {
                        feedId: config.id
                    });
                    if (config.fireToggleFeedLikeStatus) {
                        //to avoid network delay, using UI class check feed like status instead of ajax response value;
                        //after call toggleLikeUIStatus function feed like status has been changed to opposite status;
                        //when pass feedLikeStatus to fire event need to change status either.
                        likeContainer.trigger('zionFeedLikeStatus', {
                            feedId: config.id,
                            feedLikeStatus: !feedLikeStatus
                        });
                    }
                }
            }).show();
        }));
    }
}(jQuery));
