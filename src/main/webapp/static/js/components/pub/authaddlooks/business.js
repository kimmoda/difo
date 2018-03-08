var addFeedService = (function () {
    var loadTags = function (callback) {
            zionWebService.getPub(zionUrls.rest_getTags, null, function (resp) {
                if (callback && jQuery.isFunction(callback)) {
                    callback(resp)
                }
            })
        },
        saveFeed = function (feed, callback) {
            if (!feed) {
                return;
            }
            zionWebService.post(zionUrls.rest_saveFeed, feed, function (resp) {
                if (callback && jQuery.isFunction(callback)) {
                    callback(resp);
                }
            })

        },
        deleteFeed = function (feedId, callback) {
            if (feedId) {
                zionWebService.post(zionUrls.rest_deleteFeed, feedId, function (resp) {
                    if (callback && jQuery.isFunction(callback)) {
                        callback(resp);
                    }
                })
            }
        },
        getFeed = function (feedId, callback) {
            if (feedId) {
                var data = {
                    id: feedId
                };
                zionWebService.getPub(zionUrls.rest_getFeed, data, function (resp) {
                    if (callback && jQuery.isFunction(callback)) {
                        callback(resp);
                    }
                })
            }

        },
        findTagFromList = function (code, list) {
            var tag = null;
            jQuery.each(list, function (index, value) {
                if (value.code.toLowerCase() === code.toLowerCase()) {
                    tag = value;
                }
            });
            return tag;
        },
        removeTagFromList = function (code, list) {
            var tags = [];
            jQuery.each(list, function (index, value) {
                if (value.code.toLowerCase() !== code.toLowerCase()) {
                    tags.push(value);
                }
            });
            return tags;
        }
    ;

    return {
        loadTags: loadTags,
        saveFeed: saveFeed,
        getFeed: getFeed,
        deleteFeed: deleteFeed,
        findTag: findTagFromList,
        deleteTag: removeTagFromList
    }
})();