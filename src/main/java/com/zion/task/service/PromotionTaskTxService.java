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

import com.zion.common.DtoListWrapper;
import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.PromotionTaskTxEntity;
import com.zion.task.PromoteOwnTaskException;
import com.zion.task.PromotionTaskTx;
import com.zion.task.TaskAlreadyCompleteException;
import com.zion.task.TaskExpiredException;

public interface PromotionTaskTxService {

    PromotionTaskTx create(PromotionTaskTx entity) throws TaskExpiredException, PromoteOwnTaskException, TaskAlreadyCompleteException;

    DtoListWrapper<PromotionTaskTx> searchTx(QueryCriteria<PromotionTaskTxEntity> criteria);
}

