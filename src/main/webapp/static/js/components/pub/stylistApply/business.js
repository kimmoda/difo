var stylistApplyBusiness = {
    getUserInfo : function (callback) {
        zionWebService.get(zionUrls.rest_me, null, function (response) {
        	callback(response.data);
        })
    }

};