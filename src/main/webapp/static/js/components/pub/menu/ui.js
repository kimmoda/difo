jQuery.noConflict();
(function ($) {
    $(document).ready(function () {
        var iconButtonUrl,
            loginCheckAndRedirect = function (currentNode, redirectUrl) {
                loginCheckAndCallback(currentNode, function () {
                    window.location.replace(redirectUrl);
                });
            },
            loginCheckAndCallback = function (currentNode, callback) {
                currentNode.zionSocialLogin({
                    loginSuccessCallback: function (resp) {
                        if (callback && $.isFunction(callback)) {
                            callback()
                        }
                    }
                }).show();
            },
            initFooter = function () {
                var socialMediaDictionary = webConfig.socialMedia,
                    html = Handlebars.templates.socialMediaList(socialMediaDictionary);
                jQuery('.social-icons-links').empty().append(html);
            };
        menuBusiness.initMenus(function (menus) {
            var userInfo = commonService.getUserInfo();
            if (!userInfo.approvedTrendsetter) {
                userInfo.userRoles = ["STYLE SEEKER"];
            }
            menus.userInfo = userInfo;
            jQuery('#main-menu-container').append(Handlebars.templates.pubHeaderMenus(menus));
            jQuery('#zion-tab-bar-container').append(Handlebars.templates.pubTabBarMenus(menus));
            jQuery('#footer-menu-container').append(Handlebars.templates.pubFooterMenus(menus));
            jQuery('#zion-admin-header-container').append(Handlebars.templates.pubAdminHeaderMenus(menus));
            jQuery('.dashboard-show-more-list').append(Handlebars.templates.pubAdminHeaderMoreMenus(menus));
            if (commonService.getJwtToken()) {
                menuBusiness.getUserInfo(function () {
                    var userInfo = commonService.getUserInfo();
                    if (userInfo.approvedTrendsetter) {
                        $('#trendsetter-apply-item').addClass('zion-hide');
                    }
                    $('#admin-side-menu-container').empty().append(Handlebars.templates.adminMenus(menus));
                })
            } else {
                $('#menu-auth-header-signout').addClass('zion-hide');
            }
            iconButtonUrl = menus.desktop;
        });


        menuBusiness.trendsetterCheck(function (approvedTrendsetter) {
            if (approvedTrendsetter) {
                $('#for-trendsetter').addClass('zion-hide');
            }
            else {
                $('#for-trendsetter').removeClass('zion-hide');
            }
        });

        $('#admin-side-menu-container')
            .on('click', '.looks-button', function () {
                window.location.href = commonService.getZionActionUrl('/auth/looks');
            })
            .on('click', '.like-button', function () {
                window.location.href = commonService.getZionActionUrl('/auth/look/likes');
            })
            .on('click', '.trendsetter-button', function () {
                window.location.href = commonService.getZionActionUrl('/auth/user/likes');
            })
            .on('click', '.apply-button', function () {
                window.location.href = commonService.getZionHomeUrl() + "/apply";
            })
            .on("click", ".logout-button", function () {
                commonService.logout();
            })
            .on('click', '.side-avatar-container', function () {
                var userId = commonService.getUserInfo().id;
                if (userId) {
                    window.location.href = commonService.getZionActionUrl('/stylist/view') + "?userId=" + userId;
                }
            });

        jQuery('#main-menu-container')
            .on("click touchstart", "#header-menu", function () {
                $('.expend-collapse').removeClass('in')
            })
            .on("click touchstart", "#avatar-menu", function () {
                $('.menu-collapse').removeClass('in')
            })
            .on("click", "#trendsetter-apply-item", function () {
                window.location.href = commonService.getZionHomeUrl() + "/apply";
            })
            .on("click", "#avatar, #menu-pub-mobile-header-signin, #menu-pub-header-signin", function (e) {
                e.preventDefault();
                loginCheckAndRedirect($(this), zionUrls.action_dashboard);
            })
            .on("click", "#menu-desktop-header-center-button", function (e) {
                // hardcode for popup create campaign window
                e.preventDefault();
                if (webConfig.appConfig && webConfig.appConfig.appName.toLowerCase() === "campaign") {
                    createCampaignService().init();
                }else if(webConfig.appConfig && webConfig.appConfig.appName.toLowerCase() === "feed"){
                    loginCheckAndRedirect($(this), zionUrls.action_my_looks);
                }
            });

        //check whether user login. if login redirect to dashboard page.
        jQuery("#zion-tab-bar-container").on("click", ".menu-desktop-header-me", function (e) {
            e.preventDefault();
            loginCheckAndRedirect($(this), zionUrls.action_dashboard);
        })
            .on("click", ".menu-mobile-tab-bar-center-button", function (e) {
                e.preventDefault();
                if (webConfig.appConfig && webConfig.appConfig.appName.toLowerCase() === "campaign") {
                    createCampaignService().init();
                }else if(webConfig.appConfig && webConfig.appConfig.appName.toLowerCase() === "feed"){
                    loginCheckAndRedirect($(this), zionUrls.action_my_looks);
                }
            });

        jQuery("#footer-menu-container").on("click", "#add-look-footer-menu", function (e) {
            e.preventDefault();
            loginCheckAndRedirect($(this), zionUrls.action_my_looks);
        });

        //slide show more dashboard option by click dashboard show more icon
        jQuery("#zion-admin-header-container").on("click", ".dashboard-show-more-button", function () {
            jQuery(".dashboard-show-more-container").slideToggle(500);
        });

        //click "publish profile" option redirect to user trendsetter view page
        jQuery("#my-page").on("click", function () {
            var userId = commonService.getUserInfo().id;
            window.location.href = commonService.getZionActionUrl("/stylist/view") + "?userId=" + userId;
        });

        //click "sign out" option to logout
        jQuery('.dashboard-show-more-container').on("click", '#menu-auth-header-signout', function () {
            commonService.logout();
        });

        initFooter();
    });
})(jQuery);
