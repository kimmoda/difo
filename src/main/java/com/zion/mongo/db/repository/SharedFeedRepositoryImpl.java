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

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.morphia.entity.FeedEntity;
import com.zion.morphia.entity.SharedFeedEntity;
import com.zion.morphia.entity.UserEntity;

public class SharedFeedRepositoryImpl extends AbstractRepository implements SharedFeedRepository {

    private UserRepository userRepository;
    private FeedRepository feedRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SharedFeedRepositoryImpl.class);

    @Inject
    public SharedFeedRepositoryImpl(UserRepository userRepository, FeedRepository feedRepository) {
        this.userRepository = userRepository;
        this.feedRepository = feedRepository;
    }

    @Override
    public SharedFeedEntity create(SharedFeedEntity sharedFeedEntity) {
        Validate.notNull(sharedFeedEntity);
        String feedAuthorId = sharedFeedEntity.getFeedAuthorId();
        Validate.notBlank(feedAuthorId);
        Validate.notBlank(sharedFeedEntity.getFeedId());
        Validate.notBlank(sharedFeedEntity.getSocialSource());

        UserEntity userEntity = this.userRepository.getUser(feedAuthorId);
        Validate.notNull(userEntity);

        FeedEntity feedEntity = this.feedRepository.getById(sharedFeedEntity.getFeedId());
        Validate.notNull(feedEntity);

        try {
            long feedSharedCount = feedEntity.getSharedCount() + 1;
            feedEntity.setSharedCount(feedSharedCount);
            this.feedRepository.update(feedEntity);

            long count = userEntity.getFeedSharedCount() + 1;
            userEntity.setFeedSharedCount(count);
            this.userRepository.updateUser(userEntity);
        } catch (Exception e) {
            //It may fail as concurrent user shares same feed
            LOGGER.warn(e.getMessage());
        }
        return super.persist(sharedFeedEntity);
    }

}
