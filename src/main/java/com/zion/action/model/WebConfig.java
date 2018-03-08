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
package com.zion.action.model;

import com.zion.common.AppConfigDto;
import com.zion.common.SocialMediaDto;

public class WebConfig {
    private String ua;
    
    private String contextPath;
    
    private String hostDomain;
    
    private String actionUrl;
    
    private String restUrl;
    
    private String facebookApiKey;
    
    private String googleApiKey;
    
    private String googleAuthId;

    private boolean packTagEnabled;
    
    private String appVersion;
    
    private String cloudinaryApiKey;
    
    private String cloudinaryHostName;
    
    private String cloudinaryPresetId;
    
    private String couldinaryThumbnailPresetId;
    
    private String digitalCurrencyShortname;
    
    private AppConfigDto appConfig;

    private SocialMediaDto socialMedia;
    
    public WebConfig(SocialMediaDto socialMedia, AppConfigDto appConfig, String ua, String hostDomain, String contextPath, String actionUrl, String restUrl,
                     String facebookApiKey, String googleApiKey, String cloudinaryApiKey, String cloudinaryHostName,
                     String cloudinaryPresetId, String couldinaryThumbnailPresetId, String googleAuthId,
                     String appVersion, boolean packTagEnabled, String digitalCurrencyShortname) {
        this.socialMedia = socialMedia;
        this.appConfig = appConfig;
        this.ua = ua;
        this.hostDomain = hostDomain;
        this.actionUrl = actionUrl;
        this.restUrl = restUrl;
        this.contextPath = contextPath;
        this.facebookApiKey = facebookApiKey;
        this.googleApiKey = googleApiKey;
        this.cloudinaryApiKey = cloudinaryApiKey;
        this.cloudinaryHostName = cloudinaryHostName;
        this.cloudinaryPresetId = cloudinaryPresetId;
        this.couldinaryThumbnailPresetId = couldinaryThumbnailPresetId;
        this.googleAuthId = googleAuthId;
        this.packTagEnabled = packTagEnabled;
        this.appVersion = appVersion;
        this.setDigitalCurrencyShortname(digitalCurrencyShortname);
    }
    
    public String getHostDomain() {
        return hostDomain;
    }
    
    public String getActionUrl() {
        return actionUrl;
    }
    
    public String getRestUrl() {
        return restUrl;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getFacebookApiKey() {
        return facebookApiKey;
    }
    
    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public String getGoogleAuthId() {
        return googleAuthId;
    }

    public boolean isPackTagEnabled() {
        return packTagEnabled;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getCloudinaryApiKey() {
        return cloudinaryApiKey;
    }

    public void setCloudinaryApiKey(String cloudinaryApiKey) {
        this.cloudinaryApiKey = cloudinaryApiKey;
    }

    public String getCloudinaryHostName() {
        return cloudinaryHostName;
    }

    public void setCloudinaryHostName(String cloudinaryHostName) {
        this.cloudinaryHostName = cloudinaryHostName;
    }

    public String getCloudinaryPresetId() {
        return cloudinaryPresetId;
    }

    public void setCloudinaryPresetId(String cloudinaryPresetId) {
        this.cloudinaryPresetId = cloudinaryPresetId;
    }

    public String getCouldinaryThumbnailPresetId() {
        return couldinaryThumbnailPresetId;
    }

    public void setCouldinaryThumbnailPresetId(String couldinaryThumbnailPresetId) {
        this.couldinaryThumbnailPresetId = couldinaryThumbnailPresetId;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getDigitalCurrencyShortname() {
        return digitalCurrencyShortname;
    }

    public void setDigitalCurrencyShortname(String digitalCurrencyShortname) {
        this.digitalCurrencyShortname = digitalCurrencyShortname;
    }

    public AppConfigDto getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfigDto appConfig) {
        this.appConfig = appConfig;
    }

    public SocialMediaDto getSocialMedia() {
        return socialMedia;
    }
}
