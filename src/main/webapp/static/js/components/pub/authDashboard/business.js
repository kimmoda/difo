var dashboardService = (function () {
    var getUserInformation = function (callback) {
            zionWebService.get(zionUrls.rest_me, callback, function (response) {
                if (callback && jQuery.isFunction(callback)) {
                    callback(response.data);
                }
            });
        },
        getLatestFeed = function (status, callback) {
            var data = {'resultSize': 11};
            if (status) {
                data['status'] = status;
            }
            zionWebService.getPub(zionUrls.rest_getMyFeedsList, data, function (response) {
                if (response.data) {
                    var feeds = response.data;
                    var newData = [];
                    for (var i = 0; i < feeds.length; i++) {
                        var feed = feeds[i];
                        if (feed && feed.coverImage && feed.coverImage.url) {
                            feed.coverImage.url = zionImageUtils().squareImage(feed.coverImage.url);
                        }
                        feed.isShowAuthor = false;
                        feed.isShowEditBtn = true;
                        feed.likeIcon = feed.like ? 'fa-heart like' : 'fa-heart-o dislike';
                        newData.push(feed);
                    }
                    if (callback && jQuery.isFunction(callback)) {
                        callback(newData);
                    }
                }

            });
        },

        getDraftCount = function (callback) {
            zionWebService.getPub(zionUrls.rest_getMyFeedsCount, null, function (response) {
                var data = response.data;
                if (callback && jQuery.isFunction(callback)) {
                    callback(data);
                }
            });
        },

        agreeTerms = function (callback) {
            zionWebService.post(zionUrls.rest_term_conditions, JSON.stringify("UPLOAD_PHOTO"), function (response) {
                var data = response.data;
                if (callback && jQuery.isFunction(callback)) {
                    callback(data);
                }
            });
        };

    return {
        getInfo: getUserInformation,
        getFeed: getLatestFeed,
        getDrafeCount: getDraftCount,
        agree: agreeTerms
    }
})();
