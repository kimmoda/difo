package com.zion.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuMobile {
    private List<MenuItemConfig> tabBar = new ArrayList<>();

    private List<MenuItemConfig> adminHeader = new ArrayList<>();

    private List<MenuItemConfig> adminMore = new ArrayList<>();
    private List<MenuItemConfig> dropdown = new ArrayList<>();

    public List<MenuItemConfig> getTabBar() {
        return tabBar;
    }

    public void setTabBar(List<MenuItemConfig> tabBar) {
        this.tabBar = tabBar;
    }

    public List<MenuItemConfig> getAdminHeader() {
        return adminHeader;
    }

    public void setAdminHeader(List<MenuItemConfig> adminHeader) {
        this.adminHeader = adminHeader;
    }

    public List<MenuItemConfig> getAdminMore() {
        return adminMore;
    }

    public void setAdminMore(List<MenuItemConfig> adminMore) {
        this.adminMore = adminMore;
    }

    public List<MenuItemConfig> getDropdown() {
        return dropdown;
    }

    public void setDropdown(List<MenuItemConfig> dropdown) {
        this.dropdown = dropdown;
    }
}
