var menuBusiness = {
    parseMenuItem: function (item) {
        var url,
            subPath,
            //activeAction is from action bean attribute. It is defined as global object in layout.jsp
            active = webConfig.activeAction === item.action;
        if (item.externalLink) {
            url = item.action;
        } else {
            subPath = item.action ? '/' + item.action : '/';
            url = item.actionPath ? commonService.getZionActionUrl(subPath) : commonService.getZionHomeUrl() + subPath;
        }

        return {
            "id": item.id,
            "label": item.label,
            "url": url,
            "icon": item.icon,
            "active": active,
            "disableHyperlink": item.disableHyperlink,
            "highlight": item.highlight,
            "externalLink": item.externalLink,
            "isMobile": item.isMobile,
            "isDesktop": item.isDesktop
        };
    },
    initMenus: function (callback) {
        var self = this;
        zionWebService.getPub(zionUrls.rest_menu, null, function (response) {
            var menus = {
                pubHeaderMenus: [],
                mobileHeaderMoreMenus: [],
                pubAuthHeaderMenus: [],
                pubTabBarMenus: [],
                authSideMenus: [],
                pubFooterMenus: [],
                user: null
            };
            if (response && response.data && response.data.menuItems) {
                var menusData = response.data.menuItems,
                    mobileMenu = menusData.mobile,
                    desktopMenu = menusData.desktop;
                menus.user = response.data.user;
                desktopMenu.header.forEach(function (item) {
                    item.isDesktop = true;
                    menus.pubHeaderMenus.push(self.parseMenuItem(item));
                });
                mobileMenu.dropdown.forEach(function (item) {
                    item.isMobile = true;
                    menus.pubHeaderMenus.push(self.parseMenuItem(item));
                });
                desktopMenu.admin_header.forEach(function (item) {
                    menus.pubAuthHeaderMenus.push(self.parseMenuItem(item));
                });

                desktopMenu.admin_side.forEach(function (item) {
                    menus.authSideMenus.push(self.parseMenuItem(item));
                });

                //for tabBar menu
                mobileMenu.tab_bar.forEach(function (item) {
                    menus.pubTabBarMenus.push(self.parseMenuItem(item));
                });

                desktopMenu.footer.forEach(function (item) {
                    menus.pubFooterMenus.push(self.parseMenuItem(item));
                });

                mobileMenu.admin_more.forEach(function (item) {
                    menus.mobileHeaderMoreMenus.push(self.parseMenuItem(item));
                })
            }
            callback(menus);
        });
    },
    getUserInfo: function (callback) {
        zionWebService.get(zionUrls.rest_me, null, function (resp) {
            commonService.saveUserInfo(resp.data);
            if (callback && jQuery.isFunction(callback)) {
                callback();
            }
        })
    },
    trendsetterCheck: function (callback) {
        if (commonService.getJwtToken()) {
            zionWebService.getPub(zionUrls.rest_me, null, function (response) {
                var approvedTrendsetter = false;
                approvedTrendsetter = response.data.approvedTrendsetter;
                callback(approvedTrendsetter);
            });
        }
    }
};