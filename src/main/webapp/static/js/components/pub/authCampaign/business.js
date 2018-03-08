var authCampaignService = function () {
    var nextPageToken = null,
        fetchBalance = function (callback) {
            zionWebService.get(zionUrls.rest_balance, null, function (resp) {
                var account = resp.data;
                if (callback && jQuery.isFunction(callback)) {
                    callback(account);
                }
            });
        },
        fetchHistory = function (callback) {
            var userInfo = commonService.getUserInfo(),
                params = {
                    participantid: userInfo.id,
                    resultSize: 10,
                    nextPageToken: nextPageToken
                };
            zionWebService.get(zionUrls.rest_fetchFinishedCampaign, params, function (resp) {
                nextPageToken = resp.nextPageToken;
                if (callback && jQuery.isFunction(callback)) {
                    callback(resp.data);
                }
            })
        };

    return {
        balance: fetchBalance,
        history: fetchHistory
    }
};