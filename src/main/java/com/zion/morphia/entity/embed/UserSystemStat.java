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
package com.zion.morphia.entity.embed;

public class UserSystemStat {

    private long shortUrlClickCount;

    private long feedLikeCount;

    private long feedSharedCount;

    private long feedViewedCount;
    
    private long profileShortUrlClickCount;
    
    private long fansCount;

    public UserSystemStat() {
    }

    public long getShortUrlClickCount() {
        return shortUrlClickCount;
    }

    public void setShortUrlClickCount(long shortUrlClickCount) {
        this.shortUrlClickCount = shortUrlClickCount;
    }

    public long getFeedLikeCount() {
        return feedLikeCount;
    }

    public void setFeedLikeCount(long feedLikeCount) {
        this.feedLikeCount = feedLikeCount;
    }

    public long getFeedSharedCount() {
        return feedSharedCount;
    }

    public void setFeedSharedCount(long feedSharedCount) {
        this.feedSharedCount = feedSharedCount;
    }

    public long getFeedViewedCount() {
        return feedViewedCount;
    }

    public void setFeedViewedCount(long feedViewedCount) {
        this.feedViewedCount = feedViewedCount;
    }

    public long getProfileShortUrlClickCount() {
        return profileShortUrlClickCount;
    }

    public void setProfileShortUrlClickCount(long profileShortUrlClickCount) {
        this.profileShortUrlClickCount = profileShortUrlClickCount;
    }

    public long getFansCount() {
        return fansCount;
    }

    public void setFansCount(long fansCount) {
        this.fansCount = fansCount;
    }
}
