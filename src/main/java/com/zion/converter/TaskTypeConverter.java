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

import com.zion.morphia.entity.TaskTypeEntity;
import com.zion.task.TaskType;

public class TaskTypeConverter {

    public static TaskType convertTo(TaskTypeEntity src) {
        TaskType dest = new TaskType();
        dest.setCost(src.getCost());
        dest.setDescription(src.getDescription());
        dest.setId(src.getId());
        dest.setName(src.getName());
        dest.setReward(src.getReward());
        return dest;
    }
}

