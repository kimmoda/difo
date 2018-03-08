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

import org.bson.types.ObjectId;

import com.zion.morphia.entity.PromotionTaskEntity;
import com.zion.morphia.entity.PromotionTaskTxEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.task.PromotionTask;
import com.zion.task.PromotionTaskTx;
import com.zion.user.User;

public class PromotionTaskTxConverter {

    private static UserConverter userConverter = new UserConverter();

    public static PromotionTaskTxEntity convertTo(PromotionTaskTx src) {
        PromotionTaskTxEntity dest = new PromotionTaskTxEntity();

        User userTo = src.getTxTo();
        UserEntity to = new UserEntity();
        to.setEntityId(new ObjectId(userTo.getId()));
        dest.setTxTo(to);

        PromotionTask task = src.getTask();
        PromotionTaskEntity taskEntity = new PromotionTaskEntity();
        taskEntity.setEntityId(new ObjectId(task.getId()));
        dest.setTask(taskEntity);

        return dest;
    }

    public static PromotionTaskTx convertTo(PromotionTaskTxEntity src) {
        PromotionTaskTx dest = new PromotionTaskTx();
        dest.setTxFrom(userConverter.convertToModel(src.getTxFrom()));
        dest.setTxTo(userConverter.convertToModel(src.getTxTo()));
        dest.setAmount(src.getAmount());
        dest.setTask(PromotionTaskConverter.convertTo(src.getTask()));
        dest.setCreationDate(src.getCreationDate());
        return dest;
    }
}
