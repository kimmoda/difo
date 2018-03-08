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

@Entity(value = "zion_sharedfeed", noClassnameStored = true)
public class SharedFeedEntity extends BaseEntity{

    private static final long serialVersionUID = -7288622404910782448L;

    /**
     * Who shares this feed. this may be null.
     */
    private String userId;
    private String feedId;
    private String feedAuthorId;
    /**
     * where this feed is shared to
     */
    private String socialSource;
    
    public SharedFeedEntity() {}

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

    public String getFeedAuthorId() {
        return feedAuthorId;
    }

    public void setFeedAuthorId(String feedAuthorId) {
        this.feedAuthorId = feedAuthorId;
    }

    public String getSocialSource() {
        return socialSource;
    }

    public void setSocialSource(String socialSource) {
        this.socialSource = socialSource;
    }

    @Override
    public String toString() {
        return this.getId();
    }

}

