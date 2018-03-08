var homepageService = (function () {
    var getLatestLooks = function (callback) {
            var data = {'resultSize': 12, 'systags': 'top'};
            zionWebService.getPub(zionUrls.rest_getFeedsList, data, function (resp) {
                if (callback && jQuery.isFunction(callback)) {
                    // resp.data.likeIcon = resp.data.like ? 'fa-heart' : 'fa-heart-o';
                    for (var i = 0; i < resp.data.length; i++) {
                        resp.data[i].likeIcon = resp.data[i].like ? 'fa-heart like' : 'fa-heart-o dislike';
                    }
                    callback(resp.data);
                }
            })
        },
        getPopularTrendsetters = function (callback) {
            var data = {'resultSize': 12, 'tags': 'top'};
            zionWebService.getPub(zionUrls.rest_userList,data,function (resp) {
                if (callback && jQuery.isFunction(callback)) {
                    callback(resp.data);
                }
            })
        };

    return {
        getLatestLooks: getLatestLooks,
        getPopularUser:getPopularTrendsetters
    }

})();