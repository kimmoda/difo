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
package com.zion.mongo.db.repository;

import java.util.List;

import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.UserFeedEntity;

public interface UserFeedRepository {
    
    UserFeedEntity getUserFeed(String userId, String feedId);

    List<UserFeedEntity> getUserFeeds(String userId);
    
    EntityListWrapper<UserFeedEntity> getLikedFeeds(QueryCriteria<UserFeedEntity> queryCriteria);
    
    UserFeedEntity saveOrUpdate(UserFeedEntity entity);
    
    UserFeedEntity update(UserFeedEntity entity);
    
    List<UserFeedEntity> listAll();

}

