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
package com.zion.follower.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.zion.common.DtoListWrapper;
import com.zion.common.FollowerDto;
import com.zion.common.QueryCriteria;
import com.zion.converter.FollowerConverter;
import com.zion.follower.Follower;
import com.zion.mongo.db.repository.EntityListWrapper;
import com.zion.mongo.db.repository.FollowerRepository;
import com.zion.morphia.entity.FollowerEntity;
import com.zion.user.User;

public class FollowerServiceImpl implements FollowerService {

    private FollowerRepository followerRepo;

    @Inject
    public FollowerServiceImpl(FollowerRepository followerRepo) {
        this.followerRepo = followerRepo;
    }

    @Override
    public Follower createOrDelete(FollowerDto dto) {
        validateFollowerDto(dto);
        return FollowerConverter.convertTo(followerRepo.createOrDelete(dto));
    }
    
    @Override
    public Follower get(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("cannot get follower as follower id is blank");
        }
        return FollowerConverter.convertTo(followerRepo.get(id));
    }
    
    @Override
    public Follower get(FollowerDto dto) {
        validateFollowerDto(dto);
        return FollowerConverter.convertTo(followerRepo.get(dto));
    }

    @Override
    public DtoListWrapper<User> listFollowedUserByFanId(QueryCriteria<FollowerEntity> followerEntity) {
        EntityListWrapper<FollowerEntity> entityListWrapper = this.followerRepo.listFollowedUser(followerEntity);
        List<FollowerEntity> entities = entityListWrapper.getEntities();
        List<User> dtos = entities.stream().map(entity -> FollowerConverter.convertTo(entity).getFollowedUser()).filter(new Predicate<User>() {
            @Override
            public boolean test(User t) {
                return t.isEnabled();
            }
        }).collect(Collectors.toList());
        DtoListWrapper<User> dtoWrapper = new DtoListWrapper<>();
        dtoWrapper.setDtos(dtos);
        dtoWrapper.setNextPageToken(entityListWrapper.getNextPageToken());
        return dtoWrapper;
    }

    private void validateFollowerDto(FollowerDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("cannot create follower as dto is null");
        }
        if (StringUtils.isBlank(dto.getFanId())) {
            throw new IllegalArgumentException("cannot create follower as fan's id is blank");
        }
        if (StringUtils.isBlank(dto.getFollowedUserId())) {
            throw new IllegalArgumentException("cannot create follower as follower's id is blank");
        }
    }

}
