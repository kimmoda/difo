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
package com.zion.task;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zion.user.User;

@JsonInclude(Include.NON_NULL)
public class PromotionTaskTx {

    private PromotionTask task;
    
    private User txFrom;

    private User txTo;

    private long amount;
    
    private Date creationDate;
    
    public PromotionTaskTx() {
    }

    public PromotionTask getTask() {
        return task;
    }

    public User getTxFrom() {
        return txFrom;
    }

    public User getTxTo() {
        return txTo;
    }

    public long getAmount() {
        return amount;
    }

    public void setTask(PromotionTask task) {
        this.task = task;
    }

    public void setTxFrom(User from) {
        this.txFrom = from;
    }

    public void setTxTo(User to) {
        this.txTo = to;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

