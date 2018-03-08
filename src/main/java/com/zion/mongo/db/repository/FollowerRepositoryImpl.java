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

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.common.FollowerDto;
import com.zion.common.QueryCriteria;
import com.zion.common.UserRole;
import com.zion.morphia.entity.FollowerEntity;
import com.zion.morphia.entity.UserEntity;

public class FollowerRepositoryImpl extends AbstractRepository implements FollowerRepository {

    private UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(FollowerRepositoryImpl.class);

    @Inject
    public FollowerRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FollowerEntity createOrDelete(FollowerDto dto) {
        FollowerEntity persisted = this.get(dto);
        return (persisted != null) ? this.delete(persisted.getId()) : this.create(dto);
    }

    private FollowerEntity create(FollowerDto dto) {
        if (StringUtils.isBlank(dto.getFanId())) {
            throw new IllegalArgumentException("Fan's id cannot be blank.");
        }
        if (StringUtils.isBlank(dto.getFollowedUserId())) {
            throw new IllegalArgumentException("Followed user id cannot be blank.");
        }
        if (dto.getFanId().equals(dto.getFollowedUserId())) {
            throw new IllegalArgumentException("cannot follow yourself.");
        }
        FollowerEntity newFollower = new FollowerEntity();
        UserEntity follower = userRepository.getUser(dto.getFollowedUserId());
        if (follower == null) {
            throw new IllegalArgumentException(String.format("Followed user '%s' cannot be found.", dto.getFollowedUserId()));
        }
        if (follower.getUserRoles() != null && follower.getUserRoles().contains(UserRole.ADMIN.name())) {
            throw new IllegalArgumentException(String.format("user '%s' cannot be followed.", dto.getFollowedUserId()));
        }
        newFollower.setFanId(dto.getFanId());
        newFollower.setFollowedUser(follower);
        newFollower = super.persist(newFollower);
        long fansCount = follower.getFansCount() + 1;
        follower.setFansCount(fansCount);
        try {
            userRepository.updateUser(follower);
        } catch (Exception e) {
            // may fail as concurrent issue.
            LOGGER.warn(e.getMessage());
        }
        return newFollower;
    }

    private FollowerEntity delete(String id) {
        FollowerEntity entity = this.get(id);
        if (entity == null) {
            throw new IllegalArgumentException(String.format("cannot delete follower entity '%s' as it cannot be found.", id));
        }
        UserEntity user = entity.getFollowedUser();
        FollowerEntity deleted = super.delete(entity);
        long count = user.getFansCount();
        if (count >= 1) {
            count = count - 1;
        }
        user.setFansCount(count);
        try {
            userRepository.updateUser(user);
        } catch (Exception e) {
            //May fail as concurrent issue.
            LOGGER.warn(e.getMessage());
        }
        deleted.setFollowed(false);
        return deleted;
    }

    @Override
    public FollowerEntity get(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("follower entity id is blank.");
        }
        return super.get(FollowerEntity.class, new ObjectId(id));
    }

    @Override
    public FollowerEntity get(FollowerDto dto) {
        if (StringUtils.isBlank(dto.getFanId())) {
            throw new IllegalArgumentException("Fan's id cannot be blank.");
        }
        if (StringUtils.isBlank(dto.getFollowedUserId())) {
            throw new IllegalArgumentException("Followed user id cannot be blank.");
        }
        UserEntity follower = userRepository.getUser(dto.getFollowedUserId());
        if (follower == null) {
            throw new IllegalArgumentException(String.format("Followed user '%s' cannot be found.", dto.getFollowedUserId()));
        }
        return this.getMongoDatastore().find(FollowerEntity.class)
                .field("fanId").equal(dto.getFanId())
                .field("followedUser").equal(follower).get();
    }

    @Override
    public EntityListWrapper<FollowerEntity> listFollowedUser(QueryCriteria<FollowerEntity> followerEntity) {
        return super.search(followerEntity);
    }

}
