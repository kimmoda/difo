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
package com.zion.task.service;

import java.util.List;

import com.zion.account.NotEnoughBalanceException;
import com.zion.common.DtoListWrapper;
import com.zion.common.LinkPreviewException;
import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.PromotionTaskEntity;
import com.zion.task.PromotionTask;

public interface PromotionTaskService {

    PromotionTask createOrUpdate(PromotionTask task) throws NotEnoughBalanceException, LinkPreviewException;

    PromotionTask getById(String id, String loggedInUserId);

    PromotionTask disable(String id);
    
    List<PromotionTask> getActiveTasks(String userId, String loggedInUserId);
    
    DtoListWrapper<PromotionTask> list(QueryCriteria<PromotionTaskEntity> criteria);
}

