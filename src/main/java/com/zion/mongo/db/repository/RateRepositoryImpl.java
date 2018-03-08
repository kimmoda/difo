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
import com.zion.converter.RateConverter;
import com.zion.morphia.entity.RateEntity;
import com.zion.rate.RateAlreadyExistException;

public class RateRepositoryImpl extends AbstractRepository implements RateRepository {

    @Override
    public RateEntity saveOrUpdate(RateEntity entity) throws RateAlreadyExistException {
        if (StringUtils.isBlank(entity.getDestId())) {
            throw new IllegalArgumentException("Cannot save or update rate as rate destination id is blank");
        }
        if (StringUtils.isBlank(entity.getUserId())) {
            throw new IllegalArgumentException("Cannot save or update rate as user id is blank");
        }
        RateEntity existingEntity;
        if (StringUtils.isBlank(entity.getId())) {
            existingEntity  = this.get(entity.getUserId(), entity.getDestId());
            if(existingEntity != null) {
                throw new RateAlreadyExistException(String.format("Cannot update the '%s' '%s' as user '%s' already rate it", existingEntity.getRateDestination(), 
                        existingEntity.getDestId(), existingEntity.getUserId()));
            }
            return super.persist(entity);
        } else {
            existingEntity = this.get(entity.getId());
            if (existingEntity != null) {
                RateConverter.update(entity, existingEntity);
                return super.update(existingEntity);
            } else {
                throw new IllegalArgumentException(String.format("Cannot update rate '%s' as rate does not exist.", entity.getId()));
            }
        }
    }

    @Override
    public EntityListWrapper<RateEntity> listRates(QueryCriteria<RateEntity> criteria) {
        return super.search(criteria);
    }

    @Override
    public RateEntity get(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Cannot get rate as id is blank.");
        }
        return super.get(RateEntity.class, new ObjectId(id));
    }

    @Override
    public RateEntity get(String userId, String destId) {
        if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(destId)) {
            return this.getMongoDatastore().find(RateEntity.class)
            .field("userId").equal(userId)
            .field("destId").equal(destId).get();
        }else {
            return null;
        }
    }

    @Override
    public RateEntity delete(String id) {
        RateEntity entity = this.get(id);
        return (entity != null) ? super.delete(entity) : null;
    }

}
