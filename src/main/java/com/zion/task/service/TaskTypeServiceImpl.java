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

import com.zion.converter.TaskTypeConverter;
import com.zion.mongo.db.repository.TaskTypeRepository;
import com.zion.morphia.entity.TaskTypeEntity;
import com.zion.task.TaskType;

public class TaskTypeServiceImpl implements TaskTypeService {
    
    private TaskTypeRepository taskTypeRepo;
    
    @Inject
    public TaskTypeServiceImpl(TaskTypeRepository taskTypeRepo) {
        this.taskTypeRepo = taskTypeRepo;
    }

    @Override
    public TaskType getById(String id) {
        TaskTypeEntity taskTypeEntity = this.taskTypeRepo.getById(id);
        return TaskTypeConverter.convertTo(taskTypeEntity);
    }

    @Override
    public List<TaskType> listAllEnabled() {
        return this.taskTypeRepo.listAllEnabled().stream().map(e -> TaskTypeConverter.convertTo(e)).collect(Collectors.toList());
    }

    @Override
    public TaskType getByName(String name) {
        TaskTypeEntity taskTypeEntity = this.taskTypeRepo.getByName(name);
        return TaskTypeConverter.convertTo(taskTypeEntity);
    }

}

