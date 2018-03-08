package com.zion.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuDesktop {
    private List<MenuItemConfig> header = new ArrayList<>();

    private List<MenuItemConfig> adminHeader = new ArrayList<>();

    private List<MenuItemConfig> adminSide = new ArrayList<>();
    private List<MenuItemConfig> footer = new ArrayList<>();

    public List<MenuItemConfig> getHeader() {
        return header;
    }

    public void setHeader(List<MenuItemConfig> header) {
        this.header = header;
    }

    public List<MenuItemConfig> getAdminHeader() {
        return adminHeader;
    }

    public void setAdminHeader(List<MenuItemConfig> adminHeader) {
        this.adminHeader = adminHeader;
    }

    public List<MenuItemConfig> getAdminSide() {
        return adminSide;
    }

    public void setAdminSide(List<MenuItemConfig> adminSide) {
        this.adminSide = adminSide;
    }

    public List<MenuItemConfig> getFooter() {
        return footer;
    }

    public void setFooter(List<MenuItemConfig> footer) {
        this.footer = footer;
    }
}
