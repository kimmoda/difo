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

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.zion.common.QueryCriteria;
import com.zion.converter.CommentConverter;
import com.zion.morphia.entity.CommentEntity;

public class CommentRepositoryImpl extends AbstractRepository implements CommentRepository {

    @Override
    public CommentEntity saveOrUpdate(CommentEntity entity) {
        if (StringUtils.isBlank(entity.getDestId())) {
            throw new IllegalArgumentException("Cannot save or update comment as comment destination id is blank");
        }
        if (StringUtils.isBlank(entity.getUserId())) {
            throw new IllegalArgumentException("Cannot save or update comment as user id is blank");
        }
        if (StringUtils.isBlank(entity.getId())) {
           return super.persist(entity);
        } else {
            CommentEntity existingEntity = this.get(entity.getId());
            if (existingEntity != null) {
               CommentConverter.update(entity, existingEntity);
               return super.update(existingEntity);
            } else {
                throw new IllegalArgumentException(String.format("Cannot update comment '%s' as comment does not exist.", entity.getId()));
            }
        }
    }

    @Override
    public EntityListWrapper<CommentEntity> list(QueryCriteria<CommentEntity> criteria) {
        return super.search(criteria);
    }

    @Override
    public CommentEntity get(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Cannot get comment as id is blank.");
        }
        return super.get(CommentEntity.class, new ObjectId(id));
    }

}
