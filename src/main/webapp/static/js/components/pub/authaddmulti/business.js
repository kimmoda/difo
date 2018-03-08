var multiImageFeedService = {
    getFeedInfoById: function (feedId, callback) {
        if (callback && jQuery.isFunction(callback)) {
            if (feedId) {
                zionWebService.getPub(
                    zionUrls.rest_getFeed,
                    {
                        id: feedId
                    },
                    function (resp) {
                        if (callback && jQuery.isFunction(callback)) {
                            var feedInfo = resp.data;
                            callback(feedInfo);
                        }
                    })
            }
        }
    },
    loadTags: function (callback) {
        zionWebService.getPub(zionUrls.rest_getTags, null, function (resp) {
            if (callback && jQuery.isFunction(callback)) {
                callback(resp)
            }
        })
    },

    saveFeed: function (feed, callback) {
        if (!feed) {
            return;
        }
        zionWebService.post(zionUrls.rest_saveFeed_v2, feed, function (resp) {
            if (callback && jQuery.isFunction(callback)) {
                callback(resp);
            }
        })

    },
    deleteFeed: function (feedId, callback) {
        if (feedId) {
            zionWebService.post(zionUrls.rest_deleteFeed, feedId, function (resp) {
                if (callback && jQuery.isFunction(callback)) {
                    callback(resp);
                }
            })
        }
    }
};