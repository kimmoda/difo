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
package com.zion.socialmedia.shareurl;

import java.net.URLEncoder;

import com.zion.common.AppConfig;
import com.zion.socialmedia.SocialSource;


public abstract class AbstractShareUrlGenerater {
    
    protected AppConfig appConfig = AppConfig.getInstance();
	
	protected abstract String getShareUrl(String title, String description, String url, String image);
	
	protected abstract String getUrlFormat();
	
	protected abstract SocialSource getSocialSource();
	
	protected boolean isEnbaled() {
	    return appConfig.isSocialShareEnabled(getSocialSource());
	}
	
	protected String encode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}
}

