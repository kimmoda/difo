var zionImageUtils = function () {
    var
        _getCustomImageUrl = function (imageUrl, options) {
            var photo_url = "",
                url_split;
            if (imageUrl) {
                url_split = imageUrl.split("upload/");
                photo_url += url_split[0] + "upload/";
                photo_url += options + "/";
                photo_url += url_split[1];
            }
            return photo_url;
        },
        getThumbUrl = function (oriUrl) {
            if (!oriUrl) {
                return "";
            }
            return _getCustomImageUrl(oriUrl, "w_300,h_300,c_fill,g_faces");
        },
        getAvatar = function (oriUrl) {
            if (!oriUrl) {
                return "";
            }
            return _getCustomImageUrl(oriUrl, "w_200,h_200");
        },
        smallSquareImage = function (oriUrl) {
            if (!oriUrl) {
                return "";
            }
            return _getCustomImageUrl(oriUrl, "w_100,h_100,c_fill,g_faces");
        };
    return {
        smallSquareImage: smallSquareImage,
        squareImage: getThumbUrl,
        avatarUrl: getAvatar
    }
};