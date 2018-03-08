var zionFeedTagUtils = {
    listFeedsByTag : function (tag) {
        var code = tag.data('tag-code'),
            name = tag.data('tag-name'),
            tags = [{
                code: code,
                name: name
            }];
        if (code) {
            commonService.setFeedFilterTags(tags);
            window.location.replace(zionUrls.action_inspiration);
        }
    }
};
