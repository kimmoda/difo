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
package com.zion.morphia.entity;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "zion_user_feed", noClassnameStored = true)
public class UserFeedEntity extends BaseEntity{
    private static final long serialVersionUID = 8666228143995862788L;

    private String userId;
    private String feedId;
    private String feedAuthorId;
    private String likeStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public String getFeedAuthorId() {
        return feedAuthorId;
    }

    public void setFeedAuthorId(String feedAuthorId) {
        this.feedAuthorId = feedAuthorId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("feedId: " + feedId);
        builder.append(" userId: " + userId);
        builder.append(" like status: " + likeStatus);
        return builder.toString();
    }

}

