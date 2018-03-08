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
package com.zion.morphia.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "zion_promotion_task_tx", noClassnameStored = true)
@Indexes({ @Index(fields = {@Field("task"), @Field("txTo")}, options = @IndexOptions(name = "idx_task_tx_to", unique = true)) })
public class PromotionTaskTxEntity extends BaseEntity {
    private static final long serialVersionUID = 3377355695700370346L;
    
    @Reference
    private PromotionTaskEntity task;
    
    @Reference
    private UserEntity txFrom;

    @Reference
    private UserEntity txTo;

    private long amount;

    public PromotionTaskEntity getTask() {
        return task;
    }

    public void setTask(PromotionTaskEntity task) {
        this.task = task;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public UserEntity getTxFrom() {
        return txFrom;
    }

    public UserEntity getTxTo() {
        return txTo;
    }

    public long getAmount() {
        return amount;
    }

    public void setTxFrom(UserEntity from) {
        this.txFrom = from;
    }

    public void setTxTo(UserEntity to) {
        this.txTo = to;
    }

    @Override
    public String toString() {
        return this.getId() + ": from " + this.txFrom + " to " + this.txTo + " amount " + amount;
    }
}
