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

import com.zion.socialmedia.SocialSource;

public class PinterestShareUrlGenerater extends AbstractShareUrlGenerater {

	@Override
	public String getShareUrl(String title, String description, String url, String image) {
		return String.format(getUrlFormat(), encode(url), encode(description), encode(image));
	}

	@Override
	public String getUrlFormat() {
		return "http://pinterest.com/pin/create/link/?url=%s&description=%s&media=%s";
	}

	@Override
	public SocialSource getSocialSource() {
		return SocialSource.PINTEREST;
	}
}
