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
package com.zion.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserFeed {
    private String id;
    private String userId;
    private String feedId;
    private String feedAuthorId;
    @JsonInclude(Include.NON_EMPTY)
    private String likeStatus;

    public UserFeed() {
    }
    public UserFeed(String userId, String feedId, String feedAuthorId, String likeStatus) {
        this.userId = userId;
        this.feedId = feedId;
        this.likeStatus = likeStatus;
        this.feedAuthorId = feedAuthorId;
    }
    public String getUserId() {
        return userId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
}

