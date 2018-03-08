var zionWebService = (function () {

    var buildHeader = function (addJWT) {
            var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            };
            if (commonService.getJwtToken() && addJWT) {
                headers['Zion-Jwt-Token'] = commonService.getJwtToken();
            }
            return headers;
        },
        getFromPublicServer = function (url, parameters, callback, error) {
            get(url, parameters, callback, error);
        },

        postToPublicServer = function (url, parameters, callback, error) {
            post(url, parameters, callback, error);
        },

        getFromServer = function (url, parameters, callback, error) {
            if (!commonService.getJwtToken()) {
                var loginModal = jQuery(this).zionSocialLogin({
                    loginSuccessCallback: function () {
                        get(url, parameters, callback, error);
                    }
                }).show();
                return;
            }
            get(url, parameters, callback, error);

        },

        postToServer = function (url, parameters, callback, error) {
            if (!commonService.getJwtToken()) {
                var loginModal = jQuery(this).zionSocialLogin({
                    loginSuccessCallback: function () {
                        post(url, parameters, callback, error);
                    }
                }).show();
                return;
            }
            post(url, parameters, callback, error);
        },

        get = function (url, parameters, success, error) {
            jQuery.ajax({
                    dataType: "json",
                    headers: buildHeader(true),
                    data: parameters ? parameters : "",
                    type: "GET",
                    url: url,
                    cache: false,
                    statusCode: {
                        503: function () {
                            window.location.href = commonService.getZionActionUrl('error');
                        }
                    },
                    success: function (response) {
                        if (success && typeof success === 'function') {
                            success(response);
                        }

                    },
                    error: function (request, status, error) {
                        // zionNotifacationService.error(data);
                        if (error && jQuery.isFunction(error)) {
                            error(status)
                        }
                    }
                }
            );
        },

        post = function (url, parameters, success, error) {
            var data;
            if (commonService.isString(parameters)) {
                data = parameters;
            } else {
                data = JSON.stringify(parameters);
            }
            jQuery.ajax({
                url: url,
                method: "POST",
                dataType: "json",
                headers: buildHeader(true),
                data: data,
                statusCode: {
                    503: function () {
                        window.location.href = commonService.getZionActionUrl('error');
                    }
                },
                success: function (response) {
                    switch (response.status) {
                        case 200: {
                            if (success && typeof success === 'function') {
                                success(response);
                            }
                            break;
                        }
                        case 304:
                        case 409:
                        case 410: {
                            if (error && typeof error === 'function') {
                                error(response)
                            }
                            break;
                        }
                        default: {
                            zionNotifacationService.info(response.msg);
                        }
                    }
                },
                error: function (data) {
                    // zionNotifacationService.error(data);
                    if (error && jQuery.isFunction(error)) {
                        error(data)
                    }
                }
            })
        },
        upload = function (url, fileData, success, error) {
            var jwtToken = commonService.getJwtToken();
            if (!jwtToken) {
                return;
            }
            jQuery.ajax({
                headers: {
                    'Zion-Jwt-Token': commonService.getJwtToken()
                },
                data: fileData,
                type: "POST",
                cache: false,
                processData: false,
                contentType: false,
                url: url,
                success: function (response) {
                    if (success && jQuery.isFunction(success)) {
                        success(response);
                    }
                },
                error: function (response) {
                    if (error && jQuery.isFunction(error)) {
                        error(response);
                    }
                }
            });
        },
        getThird = function (url, parameters, success, error) {
            jQuery.ajax({
                dataType: "json",
                headers: buildHeader(false),
                data: parameters ? parameters : "",
                type: "GET",
                url: url,
                cache: true,
                success: function (response) {
                    if (success && typeof success == 'function') {
                        success(response);
                    }
                },
                error: function (data) {
                    // zionNotifacationService.error(data);
                    if (error && jQuery.isFunction(error)) {
                        error(data)
                    }
                }
            });
        };

    return {
        getPub: getFromPublicServer,
        postPub: postToPublicServer,
        get: getFromServer,
        post: postToServer,
        upload: upload,
        getThird: getThird
    }
})
();