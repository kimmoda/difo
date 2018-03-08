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
package com.zion.socialmedia;

public enum SocialSource {
    FACEBOOK("Facebook", 1, "fa fa-facebook"),
    GOOGLE("Google", 2, "fa fa-google-plus"),
    PINTEREST("Pinterest", 3, "fa fa-pinterest-p"),
    TWITTER("Twitter", 4, "fa fa-twitter"),
    INSTAGRAM("Instagram", 5, ""),
    LINKEDIN("Linkedin", 6, ""),
    SHORTLINK("Shortlink", 7, ""),
    OTHER("Other", 8, "");
    
    private String displayName;
    
    private int order;
    
    private String fontIcon;
    
    private SocialSource(String displayName, int order, String fontIcon) {
        this.displayName = displayName;
        this.order = order;
        this.fontIcon = fontIcon;
    }

    public int getOrder() {
        return order;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFontIcon() {
        return fontIcon;
    }
}

