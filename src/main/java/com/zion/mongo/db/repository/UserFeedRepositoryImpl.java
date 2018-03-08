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

import org.apache.commons.lang3.StringUtils;

import com.zion.common.QueryCriteria;
import com.zion.converter.UserFeedConverter;
import com.zion.morphia.entity.UserFeedEntity;

public class UserFeedRepositoryImpl extends AbstractRepository implements UserFeedRepository {

    @Override
    public UserFeedEntity getUserFeed(String userId, String feedId) {
        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("Cannot get user feed as user id is blank");
        }
        if (StringUtils.isBlank(feedId)) {
            throw new IllegalArgumentException("cannot get user feed as feed id is blank");
        }
        return this.getMongoDatastore().find(UserFeedEntity.class)
                .field("userId").equal(userId)
                .field("feedId").equal(feedId).get();
    }

    @Override
    public List<UserFeedEntity> getUserFeeds(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("Cannot get user feed as user id is blank");
        }
        return this.getMongoDatastore().find(UserFeedEntity.class)
                .field("userId").equal(userId).asList();
    }

    @Override
    public UserFeedEntity saveOrUpdate(UserFeedEntity src) {
        if (src == null) {
            throw new IllegalArgumentException("Cannot save user feed as user feed entity is null");
        }
        UserFeedEntity dest = this.getUserFeed(src.getUserId(), src.getFeedId());
        if (dest != null) {
            UserFeedConverter.update(src, dest);
            return this.update(dest);
        } else {
            return this.persist(src);
        }
    }

    @Override
    public EntityListWrapper<UserFeedEntity> getLikedFeeds(QueryCriteria<UserFeedEntity> query) {
        return super.search(query);
    }

    @Override
    public List<UserFeedEntity> listAll() {
        return this.getMongoDatastore().find(UserFeedEntity.class).asList();
    }

    @Override
    public UserFeedEntity update(UserFeedEntity entity) {
        return super.update(entity);
    }

}
