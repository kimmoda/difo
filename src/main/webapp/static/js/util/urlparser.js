var SocialLinkUrlParser = function () {

    var supportType = {
            youtube: 'youtube',
            facebook: 'facebook',
            instagram: 'instagram',
            other: 'other'
        },
        parse = function (urlStr) {
            var id = null,
                socialMedia = url('.2', urlStr).toLowerCase();
            switch (socialMedia) {
                case supportType.youtube: {
                    id = commonService.getYoutubeIdFromUrl(urlStr);
                    break;
                }
                case supportType.facebook: {
                    var o = urlStr.match(/[^](fbid=[0-9]{9})\d+/);
                    if (null !== o)
                        id = (id = o[0].replace("?fbid=", "")).replace("_fbid=", "");
                    else {
                        var n = urlStr.match(/[^\/|\.!=][0-9]{7,}(?!.*[0-9]{7,})\d+/);
                        null !== n && (id = n[0])
                    }
                    break;
                }
                case supportType.instagram: {
                    var items = urlStr.split('/p/');
                    if (items.length >= 2) {
                        var temp = items[1];
                        id = temp.substr(0, 11);
                    }
                    break;
                }
                default: {
                    socialMedia = supportType.other;
                }
            }
            return {
                id: id,
                url: urlStr,
                source: socialMedia
            }
        };
    return {
        type: supportType,
        parser: parse
    }
}();