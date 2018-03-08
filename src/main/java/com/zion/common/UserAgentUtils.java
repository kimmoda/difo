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

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;

public class UserAgentUtils {

    private static final String USER_AGENT = "User-Agent";

    private static Browser[] SUPPORTED_BROWSERS = new Browser[] {
            /*
             * refer to comment to see each browser's name. or refer to
             * https://github.com/HaraldWalker/user-agent-utils/blob/trunk/src/main/java/eu/bitwalker/useragentutils/Browser.java
             */
            Browser.IE, /* Internet Explorer */
            Browser.EDGE, /* Microsoft Edge */
            Browser.CHROME, /* Chrome */
            Browser.FIREFOX, /* Firefox */
            Browser.SAFARI /* Safari */
    };

    /**
     * refer to : https://github.com/HaraldWalker/user-agent-utils/blob/trunk/src/main/java/eu/bitwalker/useragentutils/Browser.java
     * 
     * @param request
     * @return
     */
    public static String getBrowserName(final HttpServletRequest request) {
        Browser currentBrowser = Browser.parseUserAgentString(request.getHeader(USER_AGENT), Arrays.asList(SUPPORTED_BROWSERS));
        String name = currentBrowser.getGroup().getName();
        return UserAgent.getUserAgent(name).getKey();
    }
}
