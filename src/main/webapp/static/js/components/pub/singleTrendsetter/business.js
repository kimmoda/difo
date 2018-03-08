var SingleTrendsetterService = (function () {
    var getTrendsetter = function (url) {
            zionWebService.getPub(url, null, function (response) {
                var user = response['data'],
                    userSocialSharedUrl,
                    userSummaryHtml;

                userSummaryHtml = Handlebars.templates.singleTrendsetterSummary({
                    'avatar': user.person ? user.person.avatar : undefined,
                    'displayName': user.displayName,
                    'location': user.location,
                    'feedCount': user.workCount,
                    'fansCount': user.fansCount,
                    'introduce': user.person.introduce,
                    'notMine': !user.mine,
                    'userRoles': user.userRoles,
                    'phone': user.contact && user.contact.phone ? user.contact.phone : undefined,
                    'email': user.contact && user.contact.email ? user.contact.email : undefined,
                    'facebook': user.contact && user.contact.facebook ? user.contact.facebook : undefined,
                    'instagram': user.contact && user.contact.instagram ? user.contact.instagram : undefined,
                    'youtube': user.contact && user.contact.youtube ? user.contact.youtube : undefined,
                    'website': user.contact && user.contact.website ? user.contact.website : undefined
                });
                jQuery('#single-trendsetter-summary').append(userSummaryHtml);

                userSocialSharedUrl = zionUrls.rest_user_social_share_urls.replace("{userId}", user.id);
                zionWebService.getPub(userSocialSharedUrl, {}, function (response) {
                    jQuery('#zion-user-social-share-container').socialshare({shareUrlsConfig: response.data}, undefined);
                });

                if (user.fansCount > 1) {
                    jQuery('.like-content').text('Followers');
                } else {
                    jQuery('.like-content').text('Follower');
                }
            });
        };
    return {
        getLook: getTrendsetter
    }
})();