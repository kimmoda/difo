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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.zion.account.NotEnoughBalanceException;
import com.zion.common.DtoListWrapper;
import com.zion.common.LinkPreviewException;
import com.zion.common.QueryCriteria;
import com.zion.converter.PromotionTaskConverter;
import com.zion.mongo.db.repository.EntityListWrapper;
import com.zion.mongo.db.repository.PromotionTaskRepository;
import com.zion.mongo.db.repository.PromotionTaskTxRepository;
import com.zion.morphia.entity.PromotionTaskEntity;
import com.zion.task.PromotionTask;
import com.zion.task.TaskStatus;

public class PromotionTaskServiceImpl implements PromotionTaskService {

    private PromotionTaskRepository taskRepo;
    private PromotionTaskTxRepository taskTxRepo;

    @Inject
    public PromotionTaskServiceImpl(PromotionTaskRepository taskRepo, PromotionTaskTxRepository taskTxRepo) {
        this.taskRepo = taskRepo;
        this.taskTxRepo= taskTxRepo;
    }

    @Override
    public PromotionTask createOrUpdate(PromotionTask task) throws NotEnoughBalanceException, LinkPreviewException {
        if (task == null) {
            return null;
        } else {
            PromotionTaskEntity taskEntity = PromotionTaskConverter.convertTo(task);
            PromotionTaskEntity entity = this.taskRepo.createOrUpdate(taskEntity);
            return PromotionTaskConverter.convertTo(entity);
        }

    }

    @Override
    public PromotionTask getById(String id, String loggedInUserId) {
        PromotionTaskEntity entity =  this.taskRepo.getById(id);
        return this.updateTask(entity, loggedInUserId);
    }

    @Override
    public PromotionTask disable(String id) {
        PromotionTaskEntity entity =  this.taskRepo.disable(id);
        return PromotionTaskConverter.convertTo(entity);
    }

    @Override
    public DtoListWrapper<PromotionTask> list(QueryCriteria<PromotionTaskEntity> criteria) {
        DtoListWrapper<PromotionTask> dtoWrapper = new DtoListWrapper<>();
        EntityListWrapper<PromotionTaskEntity> entityWrapper = this.taskRepo.list(criteria);
        List<PromotionTaskEntity> listEntities = entityWrapper.getEntities();
        List<PromotionTask> tasks = listEntities.stream().map(e -> updateTask(e, criteria.getLoggedInUserId())).collect(Collectors.toList());
        dtoWrapper.setDtos(tasks);
        dtoWrapper.setNextPageToken(entityWrapper.getNextPageToken());
        return dtoWrapper;
    }
    
    private PromotionTask updateTask(PromotionTaskEntity taskEntity, String loggedInUserId) {
        PromotionTask task = PromotionTaskConverter.convertTo(taskEntity);
        TaskStatus status = this.getTaskStatus(taskEntity, loggedInUserId);
        task.setTaskStatus(status);
        if (StringUtils.isNotBlank(loggedInUserId) && loggedInUserId.equals(task.getCreator().getId())) {
            task.setMine(true);
        }
        return task;
    }
    
    private TaskStatus getTaskStatus(PromotionTaskEntity task, String loggedInUserId) {
        Date expiredDate = task.getExpiredDate();
        DateTime now = new DateTime();
        if (now.isAfter(expiredDate.getTime())) {
            return TaskStatus.EXPIRED;
        }
        if(StringUtils.isNotBlank(loggedInUserId) && taskTxRepo.getTxByReceiver(task.getId(), loggedInUserId) != null) {
           return TaskStatus.COMPLETED;
        }
        return TaskStatus.ACTIVE;
    }

    @Override
    public List<PromotionTask> getActiveTasks(String userId, String loggedInUserId) {
        return this.taskRepo.getActiveTasks(userId).stream().map(e -> updateTask(e, loggedInUserId)).collect(Collectors.toList());
    }

}
