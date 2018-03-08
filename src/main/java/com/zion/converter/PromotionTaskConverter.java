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
package com.zion.converter;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.zion.morphia.entity.PromotionTaskEntity;
import com.zion.morphia.entity.TaskTypeEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.task.PromotionTask;
import com.zion.task.PromotionTaskDto;
import com.zion.task.TaskType;

public class PromotionTaskConverter {
    private static UserConverter userConverter = new UserConverter();
    
    public static PromotionTaskEntity convertTo(PromotionTask src) {
        PromotionTaskEntity dest = new PromotionTaskEntity();
        if (StringUtils.isNotBlank(src.getId())) {
            dest.setEntityId(new ObjectId(src.getId().trim()));
        }
        UserEntity creator = new UserEntity();
        creator.setEntityId(new ObjectId(src.getCreator().getId()));
        dest.setAuthor(creator);
        dest.setPostUrl(src.getPostUrl());
        dest.setTitle(src.getTitle());
        dest.setShortUrl(src.getShortUrl());
        TaskTypeEntity taskTypeEntity = new TaskTypeEntity();
        taskTypeEntity.setEntityId(new ObjectId(src.getTaskType().getId()));
        dest.setTaskType(taskTypeEntity);
        dest.setDescription(src.getDescription());
        return dest;
    }
    
    public static PromotionTask convertTo(PromotionTaskEntity src) {
        PromotionTask dest = new PromotionTask();
        dest.setCreationDate(src.getCreationDate());
        dest.setCreator(userConverter.convertToModel(src.getAuthor()));
        dest.setEnabled(src.isEnabled());
        dest.setPostUrl(src.getPostUrl());
        if(src.getPostUrlPreview() != null) {
            dest.setPostUrlPreview(src.getPostUrlPreview());
        }
        dest.setTitle(src.getTitle());
        dest.setShortUrl(src.getShortUrl());
        dest.setId(src.getId());
        dest.setTaskType(TaskTypeConverter.convertTo(src.getTaskType()));
        dest.setExpiredDate(src.getExpiredDate());
        dest.setDescription(src.getDescription());
        return dest;
    }
    
    public static PromotionTask convertTo(PromotionTaskDto src) {
        PromotionTask dest = new PromotionTask();
        dest.setPostUrl(src.getPostUrl());
        dest.setTitle(src.getTitle());
        dest.setDescription(src.getDescription());
        dest.setId(src.getId());
        TaskType taskType = new TaskType();
        taskType.setId(src.getTaskTypeId());
        dest.setTaskType(taskType);
        return dest;
    }

    public static PromotionTaskEntity update(PromotionTaskEntity src, PromotionTaskEntity dest) {
        dest.setPostUrl(src.getPostUrl());
        dest.setPostUrlPreview(src.getPostUrlPreview());
        dest.setTitle(src.getTitle());
        dest.setDescription(src.getDescription());
        dest.setTaskType(src.getTaskType());
        return dest;
    }
}

