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
package com.zion.web.guice.module;

import com.google.inject.AbstractModule;
import com.zion.mongo.db.repository.PromotionTaskRepository;
import com.zion.mongo.db.repository.PromotionTaskRepositoryImpl;
import com.zion.mongo.db.repository.PromotionTaskTxRepository;
import com.zion.mongo.db.repository.PromotionTaskTxRepositoryImpl;
import com.zion.mongo.db.repository.TaskTypeRepository;
import com.zion.mongo.db.repository.TaskTypeRepositoryImpl;
import com.zion.task.service.PromotionTaskService;
import com.zion.task.service.PromotionTaskServiceImpl;
import com.zion.task.service.PromotionTaskTxService;
import com.zion.task.service.PromotionTaskTxServiceImpl;
import com.zion.task.service.TaskTypeService;
import com.zion.task.service.TaskTypeServiceImpl;

public class TaskModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PromotionTaskRepository.class).to(PromotionTaskRepositoryImpl.class);
        bind(PromotionTaskService.class).to(PromotionTaskServiceImpl.class);
        bind(PromotionTaskTxRepository.class).to(PromotionTaskTxRepositoryImpl.class);
        bind(PromotionTaskTxService.class).to(PromotionTaskTxServiceImpl.class);
        bind(TaskTypeRepository.class).to(TaskTypeRepositoryImpl.class);
        bind(TaskTypeService.class).to(TaskTypeServiceImpl.class);
    }
}

