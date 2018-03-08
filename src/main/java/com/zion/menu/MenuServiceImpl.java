/*************************************************************************
 *
 * Forward Thinking CONFIDENTIAL
 * __________________
 *
 *  2013 - 2017 Forward Thinking Ltd
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Forward Thinking Ltd and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Forward Thinking Ltd
 * and its suppliers and may be covered by New Zealand and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Forward Thinking Ltd.
 */
package com.zion.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zion.appswitch.AppSwitch;
import com.zion.common.AppConfig;
import com.zion.common.RegistrationStatus;
import com.zion.common.UserRole;
import com.zion.converter.UserConverter;
import com.zion.menu.MenuItemConfig.CATEGORY;
import com.zion.permission.PermissionService;
import com.zion.user.Author;
import com.zion.user.User;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuServiceImpl implements MenuService {

    private PermissionService permissionService;

    private static MenuConfig menuConfig = new MenuConfig();

    private static final String HEADER_STYLIST_APPROVE_ID = "menu-pub-header-stylist-apply";

    private static final String HEADER_SIGN_IN_ID = "menu-pub-header-signin";

    @Inject
    public MenuServiceImpl(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    static {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = null;
        if (AppConfig.getInstance().getEnabledApp().equals(AppSwitch.CAMPAIGN)) {
            is = MenuService.class.getResourceAsStream("/Menu/Campaign.json");
        } else if (AppConfig.getInstance().getEnabledApp().equals(AppSwitch.FEED)) {
            is = MenuService.class.getResourceAsStream("/Menu/Feeds.json");
        }
        try {
            menuConfig = mapper.readValue(is, MenuConfig.class);
        } catch (IOException e) {
            // ignore errors.
        }
    }

    @Override
    public Menu generateMenu(final String jwtToken) {
        Map<String, Map> menuGroup = new HashMap<>();
        User user = getUser(jwtToken);
        menuGroup.put("desktop", parseDesktopMenuItem(user));
        menuGroup.put("mobile", parseMobileMenuItem(user));
        Author author = generateAuthor(user);
        return new Menu(menuGroup, author);
    }

    private Map<String, List<MenuItem>> parseDesktopMenuItem(User user) {
        Map<String, List<MenuItem>> menuGroup = new HashMap<>();
        List<MenuItemConfig> headerItems = menuConfig.getDesktop().getHeader();
        List<MenuItemConfig> adminHeaderItems = menuConfig.getDesktop().getAdminHeader();
        List<MenuItemConfig> adminSideItems = menuConfig.getDesktop().getAdminSide();
        List<MenuItemConfig> footerItems = menuConfig.getDesktop().getFooter();

        menuGroup.put("header", parseCommonMenus(headerItems, user));
        menuGroup.put("admin_header", parseCommonMenus(adminHeaderItems, user));
        menuGroup.put("admin_side", parseMenusWithUserRole(adminSideItems, user));
        menuGroup.put("footer", parseCommonMenus(footerItems, user));
        return menuGroup;
    }


    private Map<String, List<MenuItem>> parseMobileMenuItem(User user) {
        Map<String, List<MenuItem>> menuGroup = new HashMap<>();
        List<MenuItemConfig> tabBarItems = menuConfig.getMobile().getTabBar();
        List<MenuItemConfig> adminHeaderItems = menuConfig.getMobile().getAdminHeader();
        List<MenuItemConfig> adminMoreItems = menuConfig.getMobile().getAdminMore();
        List<MenuItemConfig> dropdownItems = menuConfig.getMobile().getDropdown();

        menuGroup.put("tab_bar", parseCommonMenus(tabBarItems, user));
        menuGroup.put("admin_header", parseCommonMenus(adminHeaderItems, user));
        menuGroup.put("admin_more", parseMenusWithUserRole(adminMoreItems, user));
        menuGroup.put("dropdown", parseCommonMenus(dropdownItems, user));
        return menuGroup;
    }


    private List<MenuItem> parseMenusWithUserRole(List<MenuItemConfig> items, final User user) {
        List<MenuItem> siderMenus = new ArrayList<>();

        if (!hasLoggedIn(user)) {
            return siderMenus;
        }

        items.forEach(mig -> {
            MenuItem menuItem = initMenu(mig);
            if (mig.getUserRoles() != null && !mig.getUserRoles().isEmpty()) {
                if (isApproved(user)) {
                    siderMenus.add(menuItem);
                }
            } else {
                siderMenus.add(menuItem);
            }
        });

        return siderMenus;
    }

    private List<MenuItem> parseCommonMenus(List<MenuItemConfig> items, final User user) {
        List<MenuItem> menus = new ArrayList<>();
        items.forEach(mig -> {
            MenuItem menuItem = initMenu(mig);
            if (mig.getCategories().contains(MenuItemConfig.CATEGORY.AUTH)) {
                if (hasLoggedIn(user)) {
                    menus.add(menuItem);
                }
            } else if (HEADER_SIGN_IN_ID.equals(mig.getId())) {
                if (!hasLoggedIn(user)) {
                    menus.add(menuItem);
                }
            } else if (HEADER_STYLIST_APPROVE_ID.equals(mig.getId())) {
                if (!isApproved(user)) {
                    menus.add(menuItem);
                }
            } else {
                menus.add(menuItem);
            }
        });

        return menus;
    }

    private boolean hasLoggedIn(final User user) {
        return user != null;
    }

    private boolean isApproved(final User user) {
        if (!hasLoggedIn(user)) {
            return false;
        }
        if (user.getUserRoles().contains(UserRole.INFLUENCER.name())
                || user.getUserRoles().contains(UserRole.DESIGNER.name())
                || user.getUserRoles().contains(UserRole.STYLIST.name())) {
            return RegistrationStatus.APPROVED.name().equals(user.getRegistrationStatus());
        }
        return false;
    }

    private MenuItem initMenu(MenuItemConfig mig) {
        Boolean requireAuth = mig.getCategories().contains(CATEGORY.AUTH);
        return new MenuItem(mig.getId(), mig.getLabel(), mig.getAction(), mig.getIcon(), mig.getActionPath(), mig.getExternalLink(), mig.getHighlight(), mig.getDisableHyperlink(), requireAuth, mig.getCategories());
    }

    private Author generateAuthor(final User user) {
        if (user != null) {
            UserConverter converter = new UserConverter();
            return converter.convertToAuthor(user);
        }
        return null;
    }

    private User getUser(final String jwtToken) {
        try {
            User user = this.permissionService.getUser(jwtToken);
            return user;
        } catch (Exception e) {
            return null;
        }
    }


}
