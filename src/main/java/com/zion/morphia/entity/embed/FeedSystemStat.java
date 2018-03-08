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

public class FeedSystemStat {
    
    private long likeCount;

    private long shortUrlClickCount;
    
    private long sharedCount;

    private long viewedCount;
    
    public FeedSystemStat() {
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getShortUrlClickCount() {
        return shortUrlClickCount;
    }

    public void setShortUrlClickCount(long shortUrlClickCount) {
        this.shortUrlClickCount = shortUrlClickCount;
    }

    public long getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(long sharedCount) {
        this.sharedCount = sharedCount;
    }

    public long getViewedCount() {
        return viewedCount;
    }

    public void setViewedCount(long viewedCount) {
        this.viewedCount = viewedCount;
    }
}

