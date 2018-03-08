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
package com.zion.action.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zion.action.model.WebConfig;
import com.zion.appswitch.AppSwitch;
import com.zion.common.AppConfig;
import com.zion.common.AppConfigDto;
import com.zion.common.SocialMediaDto;
import com.zion.common.UserAgentUtils;

public class WebConfigBuilder {

    private AppConfig appConfig = AppConfig.getInstance();

    private static final String ACTION_URL_WITH_CONTEXT_FORMAT = "%s%s/action";

    private static final String ACTION_URL_WITHOUT_CONTEXT_FORMAT = "%s/action";

    private static final String REST_URL_WITH_CONTEXT_FORMAT = "%s%s/restws";

    private static final String REST_URL_WITHOUT_CONTEXT_FORMAT = "%s/restws";

    public WebConfig build(HttpServletRequest request) {
        Map cloudinaryConfig = appConfig.getCloudinaryConfig();
        String cloudinaryApiKey = cloudinaryConfig.get("api_key").toString();
        String cloudinaryHostName = cloudinaryConfig.get("cloud_name").toString();
        String ua = UserAgentUtils.getBrowserName(request);
        AppSwitch enabledApp = appConfig.getEnabledApp();
        AppConfigDto appConfigDto = new AppConfigDto(enabledApp.getAppName(), enabledApp.getBrandName(), enabledApp.getLogoUrl(), appConfig.getSesConfiguration().getSupport());
        SocialMediaDto socialMedia = appConfig.getSocialMedia();
        WebConfig webConfig = new WebConfig(socialMedia, appConfigDto, ua, getHostDomain(request), getContextPath(request), getActionUrl(request), getRestUrl(request),
                appConfig.getFacebookApiKey(), appConfig.getGoogleApiKey(), cloudinaryApiKey, cloudinaryHostName, appConfig.getCouldinaryPresetId(),
                appConfig.getCouldinaryThumbnailPresetId(),
                appConfig.getGoogleAuthId(), appConfig.getAppVersion(),
                appConfig.isPackTagEnabled(), appConfig.getDigitalCurrencyShortname());
        return webConfig;
    }

    private String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    private String getHostDomain(HttpServletRequest request) {
        String protocol = request.getScheme();
        int serverPort = request.getServerPort();
        String serverName = request.getServerName();
        StringBuilder url = new StringBuilder();
        url.append(protocol).append("://").append(serverName);
        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }
        return url.toString();
    }

    private String getActionUrl(HttpServletRequest request) {
        if (isRootContextPath(request)) {
            return String.format(ACTION_URL_WITHOUT_CONTEXT_FORMAT, getHostDomain(request));
        } else {
            return String.format(ACTION_URL_WITH_CONTEXT_FORMAT, getHostDomain(request), request.getContextPath());
        }
    }

    private String getRestUrl(HttpServletRequest request) {
        if (isRootContextPath(request)) {
            return String.format(REST_URL_WITHOUT_CONTEXT_FORMAT, getHostDomain(request));
        } else {
            return String.format(REST_URL_WITH_CONTEXT_FORMAT, getHostDomain(request), request.getContextPath());
        }
    }

    private boolean isRootContextPath(HttpServletRequest request) {
        String contextPath = getContextPath(request);
        return contextPath.length() <= 1;
    }

}
