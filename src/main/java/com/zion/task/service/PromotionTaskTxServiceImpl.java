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
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.zion.common.DtoListWrapper;
import com.zion.common.QueryCriteria;
import com.zion.converter.PromotionTaskTxConverter;
import com.zion.mongo.db.repository.EntityListWrapper;
import com.zion.mongo.db.repository.PromotionTaskRepository;
import com.zion.mongo.db.repository.PromotionTaskTxRepository;
import com.zion.morphia.entity.PromotionTaskTxEntity;
import com.zion.task.PromoteOwnTaskException;
import com.zion.task.PromotionTaskTx;
import com.zion.task.TaskAlreadyCompleteException;
import com.zion.task.TaskExpiredException;

public class PromotionTaskTxServiceImpl implements PromotionTaskTxService {
    private PromotionTaskTxRepository taskTxRepo;
    private PromotionTaskRepository taskRepo;
    
    @Inject
    public PromotionTaskTxServiceImpl(PromotionTaskTxRepository taskTxRepo, PromotionTaskRepository taskRepo) {
        this.taskTxRepo = taskTxRepo;
        this.taskRepo = taskRepo;
    }

    @Override
    public PromotionTaskTx create(PromotionTaskTx taskTx) throws TaskExpiredException, PromoteOwnTaskException, TaskAlreadyCompleteException {
        if (taskTx == null) {
            return null;
        }else {
            if (this.taskRepo.isTaskExpired(taskTx.getTask().getId())) {
                throw new TaskExpiredException("Task expired");
            }
            PromotionTaskTxEntity entity = this.taskTxRepo.create(PromotionTaskTxConverter.convertTo(taskTx));
            return PromotionTaskTxConverter.convertTo(entity);
        }
    }

    @Override
    public DtoListWrapper<PromotionTaskTx> searchTx(QueryCriteria<PromotionTaskTxEntity> criteria) {
        EntityListWrapper<PromotionTaskTxEntity> wrapper = this.taskTxRepo.searchTx(criteria);
        List<PromotionTaskTx> txList = wrapper.getEntities().stream().map(e -> PromotionTaskTxConverter.convertTo(e)).collect(Collectors.toList());
        DtoListWrapper<PromotionTaskTx> dtoList = new DtoListWrapper<>();
        dtoList.setDtos(txList);
        dtoList.setNextPageToken(wrapper.getNextPageToken());
        return dtoList;
    }

}

