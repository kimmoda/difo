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
package com.zion.feed;

import com.zion.socialmedia.SocialSource;

public class SharedFeedDto {
    private String feedId;
    /**
     * where the feed is shared. eg: facebook.
     */
    private SocialSource source;
    
    public SharedFeedDto() {
    }
    
    public String getFeedId() {
        return feedId;
    }
    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }
    public SocialSource getSource() {
        return source;
    }
    public void setSource(SocialSource source) {
        this.source = source;
    }
}

