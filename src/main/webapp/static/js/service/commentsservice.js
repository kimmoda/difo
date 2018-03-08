var zionCommentsService = function (commentsType) {
    var type = commentsType ? commentsType : 'FEED';
    var getComment = function (id, nextPageToken, callback, count) {
        var defaultCount = count ? count : 6;
            var data = {
                destId: id,
                resultSize: defaultCount,
                commentDestination: type
            };
            if (nextPageToken) {
                data.nextPageToken = nextPageToken;
            }
            zionWebService.getPub(zionUrls.rest_getComments, data, function (response) {
                if (response && response.nextPageToken) {
                    nextPageToken = response.nextPageToken
                }
                if (callback && jQuery.isFunction(callback)) {
                    callback(response);
                }
            })
        },
        postComment = function (id, content, callback) {
            if (commonService.isEmpty(content)) {
                return;
            }
            var data = {
                destId: id,
                comment: content,
                commentDestination: type
            };
            zionWebService.post(zionUrls.rest_postComments, data, function (resp) {
                if (callback && jQuery.isFunction(callback)) {
                    callback(resp.data);
                }
            })

        };


    return {
        get: getComment,
        post: postComment
    }
};