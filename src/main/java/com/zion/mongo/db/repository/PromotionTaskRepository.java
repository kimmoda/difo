/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2018 Forward Thinking Ltd
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

import com.zion.account.NotEnoughBalanceException;
import com.zion.common.LinkPreviewException;
import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.PromotionTaskEntity;

public interface PromotionTaskRepository {

    PromotionTaskEntity createOrUpdate(PromotionTaskEntity entity) throws NotEnoughBalanceException, LinkPreviewException;
    
    PromotionTaskEntity getById(String id);
    
    PromotionTaskEntity disable(String id);

    PromotionTaskEntity generateShortUrl(PromotionTaskEntity promotionTaskEntity);
    
    EntityListWrapper<PromotionTaskEntity> list(QueryCriteria<PromotionTaskEntity> criteria);

    boolean isTaskExpired(String taskId);
    
    List<PromotionTaskEntity> getActiveTasks(String userId);

    List<PromotionTaskEntity> listAllTasks();

    String generateOriUrl(String campaignId);
}

