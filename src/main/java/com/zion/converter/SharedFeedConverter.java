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
package com.zion.converter;

import com.zion.feed.SharedFeed;
import com.zion.morphia.entity.SharedFeedEntity;

public class SharedFeedConverter {
    
    public static SharedFeedEntity convertTo(SharedFeed sharedFeed) {
        SharedFeedEntity entity = new SharedFeedEntity();
        entity.setFeedAuthorId(sharedFeed.getFeedAuthorId());
        entity.setFeedId(sharedFeed.getFeedId());
        entity.setUserId(sharedFeed.getUserId());
        entity.setSocialSource(sharedFeed.getSocialSource());
        return entity;
    }
    
    public static SharedFeed convertTo(SharedFeedEntity entity) {
        SharedFeed dest = new SharedFeed();
        dest.setFeedAuthorId(entity.getFeedAuthorId());
        dest.setFeedId(entity.getFeedId());
        dest.setUserId(entity.getUserId());
        dest.setSocialSource(entity.getSocialSource());
        dest.setId(entity.getId());
        return dest;
    }

}

