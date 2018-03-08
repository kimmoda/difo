/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2018 Forward Thinking Ltd
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
package com.zion.appswitch;

public enum AppSwitch {
    CAMPAIGN("CAMPAIGN", "Boot Campaign", "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/stylehub_logo.png"),
    FEED("FEED", "STYLEHUB", "https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/stylehub_logo.png");

    private String appName;
    private String brandName;
    private String logoUrl;

    private AppSwitch(String appName, String brandName, String logoUrl) {
        this.appName = appName;
        this.brandName = brandName;
        this.logoUrl = logoUrl;
    }

    public String getAppName() {
        return appName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

}
