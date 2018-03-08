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
package com.zion.user.service;

import java.util.List;

import com.zion.common.DtoListWrapper;
import com.zion.common.QueryCriteria;
import com.zion.feed.Feed;
import com.zion.morphia.entity.UserFeedEntity;
import com.zion.user.UserFeed;

public interface UserFeedService {

    UserFeed getUserFeed(String userId, String feedId);

    List<UserFeed> getUserFeeds(String userId);
    
    /**
     * get the feeds that the user liked.
     * queryCriteria should contains user id and Like status.
     * @param userId
     * @return
     */
    DtoListWrapper<Feed> getLikedFeeds(QueryCriteria<UserFeedEntity> query);
    
    UserFeed saveOrUpdate(UserFeed src);
    
    UserFeed updateLikeStatus(UserFeed src);
}

