/*******************************************************************************
 *  Forward Thinking CONFIDENTIAL
 *   __________________
 *
 *  2013 - 2017 Forward Thinking Ltd
 *  All Rights Reserved.
 *
 *  NOTICE:  All information contained herein is, and remains
 *  the property of Forward Thinking Ltd and its suppliers,
 *  if any.  The intellectual and technical concepts contained
 *  herein are proprietary to Forward Thinking Ltd
 *  and its suppliers and may be covered by New Zealand and Foreign Patents,
 *  patents in process, and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from Forward Thinking Ltd.
 */

package com.zion.action.base;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.zion.action.pub.ErrorActionBean;
import com.zion.auth.InvalidJwtToken;
import com.zion.common.UserRole;
import com.zion.permission.PermissionService;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class AdminBaseActionBean extends BaseActionBean {
    private String token;
    
    @Inject
    private PermissionService permissionService;

    @DefaultHandler
    public Resolution view() {
        if (canAccess()) {
            return new ForwardResolution(getAdminView(this.getClass()));
        }else {
            return new ForwardResolution(getPubView(ErrorActionBean.class));
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    protected boolean canAccess() {
        if (StringUtils.isBlank(this.token)) {
            return false;
        }
        try {
            User user = this.permissionService.getUser(this.token);
            return user.getUserRoles()!= null && this.permissionService.containAnyRole(user, UserRole.ADMIN.name());
        } catch (UserNotFoundException | InvalidJwtToken e) {
            return false;
        }
    }
}
