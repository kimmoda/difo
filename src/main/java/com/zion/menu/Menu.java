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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zion.user.Author;

public class Menu {

    private Map<String, Map> menuItems = new HashMap();

    private Author user;

    public Menu(Map<String, Map> menuItems, Author user) {
        super();
        this.menuItems = menuItems;
        this.user = user;
    }

    public Map<String, Map> getMenuItems() {
        return menuItems;
    }

    public Author getUser() {
        return user;
    }

}
