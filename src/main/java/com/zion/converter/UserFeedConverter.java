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

import com.zion.morphia.entity.UserFeedEntity;
import com.zion.user.UserFeed;

public class UserFeedConverter {
    public static UserFeed convertTo(UserFeedEntity src) {
        UserFeed dest = new UserFeed();
        dest.setId(src.getId());
        dest.setFeedId(src.getFeedId());
        dest.setLikeStatus(src.getLikeStatus());
        dest.setFeedAuthorId(src.getFeedAuthorId());
        dest.setUserId(src.getUserId());
        return dest;
        
    }
    
    public static UserFeedEntity convertTo(UserFeed src) {
        UserFeedEntity dest = new UserFeedEntity();
        dest.setFeedId(src.getFeedId());
        dest.setLikeStatus(src.getLikeStatus());
        dest.setUserId(src.getUserId());
        dest.setFeedAuthorId(src.getFeedAuthorId());
        return dest;
        
    }
    
    public static void update(UserFeedEntity src, UserFeedEntity dest) {
        dest.setLikeStatus(src.getLikeStatus());
    }
}

