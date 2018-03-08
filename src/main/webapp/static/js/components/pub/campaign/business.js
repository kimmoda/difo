var campaignListUIService = function () {
    var getExpiredCampaignList = function (options, callback) {
        var opts = jQuery.extend({}, {
            resultSize: 10,
            status: "EXPIRED"
        }, options);
        zionWebService.getPub(zionUrls.rest_fetchCampaignList, opts, function (resp) {
            if (callback && jQuery.isFunction(callback)) {
                var data = resp.data,
                    dtoListWrapper = {
                        data: []
                    },
                    currentCampaign;
                for(var i=0; i < data.length; i++) {
                    currentCampaign = data[i];
                    dtoListWrapper.data.push({
                        participant: {
                            avatar: currentCampaign.creator.person.avatar,
                            displayName: currentCampaign.creator.displayName,
                            profileUrl: zionUrls.action_stylist_view + '?userId=' + currentCampaign.creator.id
                        },
                        orignialPostUrl: currentCampaign.postUrl,
                        title: currentCampaign.title,
                        description: currentCampaign.description,
                        taskType: currentCampaign.taskType.description,
                        creationDate: currentCampaign.creationDate,
                    });
                }
                dtoListWrapper.nextPageToken = resp.nextPageToken;
                callback(dtoListWrapper);
            }
        })
    };

    return {
        getExpiredCampaignList: getExpiredCampaignList
    }
}();