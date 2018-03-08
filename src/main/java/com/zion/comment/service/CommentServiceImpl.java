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
package com.zion.comment.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.zion.comment.CommentDto;
import com.zion.comment.OverLimitedLengthException;
import com.zion.common.DtoListWrapper;
import com.zion.common.QueryCriteria;
import com.zion.converter.CommentConverter;
import com.zion.converter.UserConverter;
import com.zion.mongo.db.repository.CommentRepository;
import com.zion.mongo.db.repository.EntityListWrapper;
import com.zion.mongo.db.repository.UserRepository;
import com.zion.morphia.entity.CommentEntity;
import com.zion.morphia.entity.UserEntity;

public class CommentServiceImpl implements CommentService {
    private CommentRepository repo;
    private UserRepository userRepo;
    private static int MAX_LENGTH = 1000;
    
    @Inject
    public CommentServiceImpl(CommentRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Override
    public CommentDto saveOrUpdate(CommentDto dto) throws OverLimitedLengthException {
        if(StringUtils.isNotBlank(dto.getComment()) && dto.getComment().length() > MAX_LENGTH) {
            throw new OverLimitedLengthException("Cannot save or update comment as its length is over limited.");
        }
        CommentEntity entity = repo.saveOrUpdate(CommentConverter.convertTo(dto));
        return convertToDto(entity);
    }

    

    @Override
    public CommentDto get(String id) {
        if(StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Cannot get comment as id is blank.");
        }
        CommentEntity entity = repo.get(id);
        return convertToDto(entity);
    }

    @Override
    public DtoListWrapper<CommentDto> list(QueryCriteria<CommentEntity> criteria) {
        EntityListWrapper<CommentEntity> entityWrapper = repo.list(criteria);
        DtoListWrapper<CommentDto> dtoWrapper = new DtoListWrapper<>();
        List<CommentDto> dtos = dtoWrapper.getDtos();
        for(CommentEntity entity: entityWrapper.getEntities()) {
            dtos.add(CommentConverter.convertTo(entity));
        }
        if(!dtos.isEmpty()) {
            List<String> userIds = dtos.stream().map(comment -> comment.getUserId()).collect(Collectors.toList());
            List<UserEntity> userEntities = this.userRepo.getUsers(userIds);
            UserConverter userConverter = new UserConverter();
            userConverter.dynaUpdateAuthor(dtos, userEntities);
        }
        dtoWrapper.setNextPageToken(entityWrapper.getNextPageToken());
        return dtoWrapper;
    }
    
    private CommentDto convertToDto(CommentEntity entity) {
        if(entity != null) {
            CommentDto savedDto =  CommentConverter.convertTo(entity);
            UserEntity userEntity = this.userRepo.getUser(savedDto.getUserId());
            UserConverter userConverter = new UserConverter();
            userConverter.dynaUpdateAuthor(Arrays.asList(savedDto), Arrays.asList(userEntity));
            return savedDto;
        }else {
            return null;
        }
    }

}

