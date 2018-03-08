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
package com.zion.common;

import org.apache.commons.lang3.StringUtils;

public enum UserAgent {

    IE("Internet Explorer", "IE"),
    FF("Firefox", "FF"),
    EDGE("Microsoft Edge", "EDGE"),
    CHROME("Chrome", "CHROME"),
    SAFARI("Safari", "SAFARI"),
    MOBILE_SAFARI("Mobile Safari", "MOBILE_SAFARI"),
    UNKOWN("Unkown", "UNKOWN");
    
    private String name;
    private String key;
    
    private UserAgent(String name, String key) {
        this.name = name;
        this.key = key;
    }
    
    public String getName() {
        return name;
    }
    
    public String getKey() {
        return key;
    }
    
    public static UserAgent getUserAgent(String browserName) {
        if(StringUtils.isBlank(browserName)) {
            return UNKOWN;
        }
        for(UserAgent ua: UserAgent.values()) {
            if (ua.getName().equalsIgnoreCase(browserName.trim())) {
                return ua;
            }
        }
        return UNKOWN;
    }
    
}

