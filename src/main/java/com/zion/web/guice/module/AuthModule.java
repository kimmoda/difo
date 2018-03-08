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
package com.zion.web.guice.module;

import com.google.inject.AbstractModule;
import com.zion.auth.AuthenticationService;
import com.zion.auth.AuthenticationServiceImpl;
import com.zion.auth.EncryptService;
import com.zion.auth.EncryptServiceImpl;
import com.zion.auth.JwtService;
import com.zion.auth.JwtServiceImpl;
import com.zion.menu.MenuService;
import com.zion.menu.MenuServiceImpl;
import com.zion.permission.PermissionService;
import com.zion.permission.PermissionServiceImpl;

public class AuthModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EncryptService.class).to(EncryptServiceImpl.class);
        bind(JwtService.class).to(JwtServiceImpl.class);
        bind(PermissionService.class).to(PermissionServiceImpl.class);
        bind(AuthenticationService.class).to(AuthenticationServiceImpl.class);
        bind(MenuService.class).to(MenuServiceImpl.class);
    }
}

