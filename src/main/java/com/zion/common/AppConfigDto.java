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
package com.zion.common;

public class AppConfigDto {

    private String appName;
    private String brandName;
    private String logoUrl;
    private String supportEmail;
    
    public AppConfigDto() {
    }
    
    public AppConfigDto(String appName, String brandName, String logoUrl, String supportEmail) {
        this.appName = appName;
        this.brandName = brandName;
        this.logoUrl = logoUrl;
        this.supportEmail = supportEmail;
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
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }
}

