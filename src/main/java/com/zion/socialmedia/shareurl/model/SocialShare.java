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
package com.zion.socialmedia.shareurl.model;

public class SocialShare {

    private String type;

    private String displayName;

    private String fontIcon;
    
    private String url;

    private int order;

    public SocialShare(String type, String displayName, String fontIcon, String url, int order) {
        this.type = type;
        this.displayName = displayName;
        this.fontIcon = fontIcon;
        this.url = url;
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFontIcon() {
        return fontIcon;
    }

    public String getUrl() {
        return url;
    }

    public int getOrder() {
        return order;
    }
}
