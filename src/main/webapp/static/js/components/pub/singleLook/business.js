var SingleLookService = (function () {
    var getLook = function (url, feedInfo) {
            zionWebService.getPub(url, feedInfo, function (e) {
                if (e.status === 200) {
                    var feed = e.data;
                    if (feed) {
                        feed.coverImage.url = zionFeedService().getImageUrl(feed.coverImage.url);
                        feed.coverImage.description = filterXSS(feed.coverImage.description);
                        for (var i = 0; i < feed.mediaContent.length; i++) {
                            feed.mediaContent[i].description = filterXSS(feed.mediaContent[i].description);
                        }
                        var itemsHTML = Handlebars.templates.singleLook(feed);
                        jQuery('#single-feed').append(itemsHTML);
                        jQuery('#myModal').zionFeedAnnotation().getAnnotation(feed.annotationData);
                        
                        var feedSocialSharedUrl = zionUrls.rest_feed_social_share_urls.replace("{feedId}", feed.id);
                        zionWebService.getPub(feedSocialSharedUrl, {}, function(response) {
                        	jQuery('#zion-social-share-container').socialshare({shareUrlsConfig: response.data}, function (socialShareId) {
                                zionWebService.postPub(zionUrls.rest_feed_share_stats, {
                                    "feedId": feed.id,
                                    "source": socialShareId
                                });
                            });
                        });
                    }
                }
            });
        };
    return {
        getLook: getLook
    }
})();