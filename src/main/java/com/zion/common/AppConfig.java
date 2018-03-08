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

import com.cloudinary.utils.ObjectUtils;
import com.zion.appswitch.AppSwitch;
import com.zion.mail.SesConfiguration;
import com.zion.socialmedia.SocialSource;

import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class AppConfig {

    private static final String CONFIG_FILE = "app.properties";

    private static Configuration config;

    private static AppConfig instance = new AppConfig();

    static {
        try {
            config = new PropertiesConfiguration(CONFIG_FILE);
        } catch (ConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static AppConfig getInstance() {
        return instance;
    }

    public String getAuthEncryptKey() {
        return config.getString("auth.encrypt.key");
    }

    public String getJwtSecreteKey() {
        return config.getString("auth.jwt.secrete.key");
    }

    public String getJwtAud() {
        return config.getString("auth.jwt.aud");
    }

    public String getJwtIss() {
        return config.getString("auth.jwt.iss");
    }

    public EnvType getAppEnv() {
        return EnvType.getEnvType(config.getString("app.env"));
    }

    public String getAppHost() {
        EnvType env = this.getAppEnv();
        return config.getString(env.getId() + ".app.host");
    }

    public String getAppClientKey() {
        return config.getString("app.client.key");
    }

    public String getAppVersion() {
        return config.getString("app.version");
    }

    public String getDefaultPassword() {
        return config.getString("app.default.password");
    }

    public String getGoogleApiKey() {
        return config.getString("google.api.key");
    }

    public String getGoogleAuthId() {
        EnvType env = this.getAppEnv();
        return config.getString(env.getId() + ".google.oauth.id");
    }

    public String getFacebookApiKey() {
        EnvType env = this.getAppEnv();
        return config.getString(env.getId() + ".facebook.api.key");
    }

    public boolean isPackTagToggleEnabled() {
        return config.getBoolean("packtag.enabled");
    }

    public boolean isPackTagForceEnabled() {
        return config.getBoolean("packtag.force.enabled");
    }

    public boolean isPackTagEnabled() {
        if (isPackTagForceEnabled()) {
            return true;
        }

        if (!isPackTagToggleEnabled()) {
            return false;
        }

        EnvType env = this.getAppEnv();
        if (EnvType.TEST == env) {
            return false;
        }

        return true;
    }

    public SesConfiguration getSesConfiguration() {
        return new SesConfiguration(config.getString("mail.smtp.from.admin"),
                config.getString("mail.smtp.from"),
                config.getString("mail.smtp.support"),
                config.getString("mail.smtp.port"),
                config.getString("mail.smtp.server"),
                config.getString("mail.smtp.username"),
                config.getString("mail.smtp.password"));
    }

    public String getTinyUrlApiEndpoint() {
        return config.getString("shorturl.api.endpoint.tinurl");
    }

    public int getTinyUrlApiTimeout() {
        return config.getInt("shorturl.api.tinurl.timeout");
    }

    public String getAvatarRelativePath() {
        return config.getString("cloudinary.avatar.path");
    }

    public String getCouldinaryPresetId() {
        EnvType env = this.getAppEnv();
        return config.getString(env.getId() + ".cloudinary.preset");
    }
    
    public String getCouldinaryThumbnailPresetId() {
        EnvType env = this.getAppEnv();
        return config.getString(env.getId() + ".cloudinary.preset.thumbnail");
    }

    public String getSupportImageFormats() {
        return "jpe,jpg,jpeg,gif,png,bmp";
    }

    public boolean isSocialShareEnabled(SocialSource socialSource) {
        return config.getBoolean("social.share." + socialSource.name().toLowerCase() + ".enabled");
    }

    public ReCaptcha getReCaptchaConfig() {
        ReCaptcha reCaptcha = new ReCaptcha();
        reCaptcha.setSiteSecret(config.getString("SITE_SECRET"));
        reCaptcha.setRecaptchaResponse(config.getString("G_RECAPTCHA_RESPONSE"));
        reCaptcha.setResponseParam(config.getString("RESPONSE_PARAM"));
        reCaptcha.setSiteVerifyUrl(config.getString("SITE_VERIFY_URL"));
        reCaptcha.setSecretParam(config.getString("SECRET_PARAM"));
        return reCaptcha;
    }

    @SuppressWarnings("rawtypes")
    public Map getCloudinaryConfig() {
        if (getAppEnv().equals(EnvType.PROD)) {
            return ObjectUtils.asMap(
                    "cloud_name", "",
                    "api_key", "",
                    "api_secret", "");
        } else {
            return ObjectUtils.asMap(
                    "cloud_name", "",
                    "api_key", "",
                    "api_secret", "");
        }
    }
    
    public String getOpenGraphApiKey() {
        return config.getString("open.graph.io.api.key");
    }
    
    public String getSystemUsername() {
        return config.getString("app.default.system.username");
    }
    
    public String getDigitalCurrencyShortname() {
        return config.getString("app.default.digital.currency.shortname");
    }
    
    public AppSwitch getEnabledApp() {
        String appName =  config.getString("app.name");
        return AppSwitch.valueOf(appName);
    }

    public SocialMediaDto getSocialMedia(){
        SocialMediaDto socialMedia = new SocialMediaDto(
                config.getString("social.facebook"),
                config.getString("social.instagram"),
                config.getString("social.youtube"),
                config.getString("social.linkedin")
        );
        return socialMedia;
    }
}
