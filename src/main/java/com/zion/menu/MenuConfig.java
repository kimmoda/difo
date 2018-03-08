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

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuConfig {

    private MenuDesktop desktop = new MenuDesktop();
    private MenuMobile mobile = new MenuMobile();


    public MenuDesktop getDesktop() {
        return desktop;
    }

    public void setDesktop(MenuDesktop desktop) {
        this.desktop = desktop;
    }

    public MenuMobile getMobile() {
        return mobile;
    }

    public void setMobile(MenuMobile menuMobile) {
        this.mobile = menuMobile;
    }
}
