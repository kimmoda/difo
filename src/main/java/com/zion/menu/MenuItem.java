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

import java.util.Set;

import com.zion.menu.MenuItemConfig.CATEGORY;

public class MenuItem {

    private final String id;

    private final String label;

    private final String action;

    private final String icon;

    private final Boolean actionPath;

    private final Boolean externalLink;

    private final Boolean highlight;

    private final Boolean disableHyperlink;

    private final Boolean requireAuth;

    private final Set<CATEGORY> categories;

    public MenuItem(String id, String label, String action, String icon, Boolean actionPath, Boolean externalLink, Boolean highlight, Boolean disableHyperlink,
            Boolean requireAuth, Set<CATEGORY> categories) {
        this.id = id;
        this.label = label;
        this.action = action;
        this.icon = icon;
        this.actionPath = actionPath;
        this.externalLink = externalLink;
        this.highlight = highlight;
        this.disableHyperlink = disableHyperlink;
        this.requireAuth = requireAuth;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public Boolean getExternalLink() {
        return externalLink;
    }

    public String getLabel() {
        return label;
    }

    public String getAction() {
        return action;
    }

    public String getIcon() {
        return icon;
    }

    public Boolean getHighlight() {
        return highlight;
    }

    public Boolean getDisableHyperlink() {
        return disableHyperlink;
    }

    public Boolean getRequireAuth() {
        return requireAuth;
    }

    public Boolean getActionPath() {
        return actionPath;
    }

    public Set<CATEGORY> getCategories() {
        return categories;
    }

}
