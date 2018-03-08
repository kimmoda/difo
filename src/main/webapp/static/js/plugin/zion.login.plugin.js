(function ($) {
    var options,
        _init = function () {
            options = {
                containerId: '#loginModal',
                googleLoginBtId: '#google-login-button-id',
                facebookLoginBtId: '#facebook-login-button-id',
                googleAuthClientId: webConfig.googleAuthId,
                loginRestUrl: zionUrls.rest_login,
                loginSuccessCallback: null,
                loginFailedCallback: null,
                isCallbackTriggerAfterLogin: true
            }
        },
        _successCallback = function (requestData) {
            zionWebService.postPub(options.loginRestUrl, requestData, function (response) {
                _hide();
                commonService.setJwtToken(response.jwtToken);
                commonService.saveUserInfo(response.data);
                if (menuBusiness) {
                    menuBusiness.initMenus(function (menus) {
                        $('#main-menu-container').empty().append(Handlebars.templates.pubHeaderMenus(menus));
                    });
                }
                if (options.loginSuccessCallback && jQuery.isFunction(options.loginSuccessCallback) && options.isCallbackTriggerAfterLogin) {
                    options.loginSuccessCallback(response.data);
                }
            });
        },
        _initGoogleLogin = function () {
            var buttonNode = jQuery(options.googleLoginBtId)[0];
            gapi.load('auth2', function () {
                var auth2 = gapi.auth2.init({
                    client_id: options.googleAuthClientId,
                    cookiepolicy: 'single_host_origin',

                });
                auth2.attachClickHandler(buttonNode, {},
                    jQuery.debounce(1000, true, function (loggedInUser) {
                        zionGAEventService('signin', 'google', 'success').track();
                        var requestData = {
                            'authToken': loggedInUser.getAuthResponse().id_token,
                            'appClientId': options.googleAuthClientId,
                            'source': "GOOGLE"
                        };
                        _successCallback(requestData);
                    }),
                    jQuery.debounce(1000, true, function (error) {
                        zionGAEventService('signin', 'google', 'error').track();
                        if (options.loginFailedCallback && jQuery.isFunction(options.loginFailedCallback)) {
                            options.loginFailedCallback();
                        }
                    }));
            });
        },
        _openFBLoginDialog = function () {
            FB.login(function (response) {
                    if (response.authResponse && response.authResponse.accessToken) {
                        var requestData = {
                            'authToken': response.authResponse.accessToken,
                            'source': "FACEBOOK"
                        };
                        _successCallback(requestData);
                    }
                }, {scope: 'email,public_profile'}
            );
        },
        _initFacebookLogin = function () {
            var buttonNode = jQuery(options.facebookLoginBtId);
            buttonNode.on('click', jQuery.debounce(1000, true, function () {
                FB.getLoginStatus(function (response) {
                    if (response.error) {
                        zionGAEventService('signin', 'facebook', 'error').track();
                    }
                    if (response.status) {
                        zionGAEventService('signin', 'facebook', 'status-' + response.status).track();
                    }
                    if (response && !response.error && response.status && response.status === 'connected') {
                        //user may already log into facebook from other application. So we only need to generate our JWT token form them
                        // without going to facebook login dialog.
                        if (response.authResponse && response.authResponse.accessToken) {
                            var requestData = {
                                'authToken': response.authResponse.accessToken,
                                'source': "FACEBOOK"
                            };
                            zionGAEventService('signin', 'facebook', 'success').track();
                            _successCallback(requestData);
                        } else {
                            _openFBLoginDialog();
                        }
                    } else {
                        _openFBLoginDialog();
                    }
                }, false);
            }));
        },
        _show = function () {
            if (commonService.getJwtToken()) {
                if (options.loginSuccessCallback && jQuery.isFunction(options.loginSuccessCallback)) {
                    options.loginSuccessCallback(commonService.getUserInfo());
                }
            } else {
                jQuery(options.containerId).modal({
                    keyboard: true,
                    show: true
                })
            }
        },
        _hide = function () {
            jQuery(options.containerId).modal('hide');
        };
    $.fn.zionSocialLogin = function (config) {
        _init();
        options = $.extend({}, options, config);
        _initGoogleLogin();
        _initFacebookLogin();
        return {
            show: _show,
            hide: _hide
        }
    }
}(jQuery));
