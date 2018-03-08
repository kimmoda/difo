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

import com.zion.common.FollowerDto;
import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.FollowerEntity;

public interface FollowerRepository {

    FollowerEntity createOrDelete(FollowerDto dto);
    
    FollowerEntity get(String id);
    
    FollowerEntity get(FollowerDto dto);

    EntityListWrapper<FollowerEntity> listFollowedUser(QueryCriteria<FollowerEntity> followerEntity);
}
