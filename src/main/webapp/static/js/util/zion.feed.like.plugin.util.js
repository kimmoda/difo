/**
 * Zion feed like plugin util
 *
 *@method toggleLikeUIStatus - change feed like status and feed like count number;
 *        When to use: change feed card like status;
 *                     change feed modal like status;
 *                     change feed modal like status fire zionFeedLikeStatus event and addChangeFeedLikeStatusEvent listen this event then call toggleLikeUIStatus.
 *      @param {object} targetNode - use to target feed like element
 *      @example targetNode = $('fa-heart-o');
 *      @param {object} feedInfo - store feed id
 *              feedInfo: {feedId: feed.id}
 *
 *@method addChangeFeedLikeStatusEvent - listen zionFeedLikeStatus event which fired by feed like plugin, get feed id and feed like status with feed like node param pass to toggleLikeUIStatus method.
 *      @param {object} targetNode - use to target feed like element
 *      @example targetNode = $('fa-heart');
 */

var zionFeedLikePluginUtils = {
    //change feed like status and like count number
    toggleLikeUIStatus: function (targetNode, feedInfo) {
        var like = targetNode.find("[data-like-feed-id='" + feedInfo.feedId + "']"),
            likeNumElement = like.find('.zion-feed-like-count'),
            likeNumber = Number(likeNumElement.text());
        like.toggleClass('fa-heart fa-heart-o').toggleClass('like dislike');
        if (jQuery.isNumeric(likeNumber)) {
            if (like.hasClass('dislike')) {
                likeNumElement.text(likeNumber - 1);
            } else {
                likeNumElement.text(likeNumber + 1);
            }
        } else {
            //some case the number field cannot be parsed as integer eg: 1.5K
            if (console) {
                console.log('cannot increase the like number field as the field :' + likeNumberTxt + ' is not a integer number.')
            }
        }
    },
    addChangeFeedLikeStatusEvent: function (targetNode) {
        //listen zionFeedLikeStatus event which fired by feed like plugin
        jQuery('.zion-feed-modal').on('zionFeedLikeStatus', '.zion-modal-feed-like-container', function (e, feedInfo) {
            zionFeedLikePluginUtils.toggleLikeUIStatus(targetNode, feedInfo);
        });
    }
};