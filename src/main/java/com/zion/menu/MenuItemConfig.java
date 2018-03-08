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

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zion.common.UserRole;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItemConfig {

    private String id;

    private String label;

    private String action;

    private String icon;

    private Set<UserRole> userRoles;

    private Set<CATEGORY> categories =  new HashSet<>();

    private Boolean externalLink = false;

    private Boolean highlight = false;

    private Boolean disableHyperlink = false;

    private Boolean actionPath = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<CATEGORY> getCategories() {
        return categories;
    }

    public void setCategories(Set<CATEGORY> categories) {
        this.categories = categories;
    }

    public Boolean getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(Boolean externalLink) {
        this.externalLink = externalLink;
    }

    public Boolean getHighlight() {
        return highlight;
    }

    public void setHighlight(Boolean highlight) {
        this.highlight = highlight;
    }

    public Boolean getActionPath() {
        return actionPath;
    }

    public void setActionPath(Boolean actionPath) {
        this.actionPath = actionPath;
    }

    public Boolean getDisableHyperlink() {
        return disableHyperlink;
    }

    public void setDisableHyperlink(Boolean disableHyperlink) {
        this.disableHyperlink = disableHyperlink;
    }

    public enum CATEGORY {
        AUTH,
        PUB;
    }
}
