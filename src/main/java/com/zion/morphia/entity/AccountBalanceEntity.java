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

@Entity(value = "zion_account_balance", noClassnameStored = true)
@Indexes({ @Index(fields = {@Field("user")}, options = @IndexOptions(name = "idx_acc_user", unique = true)) })
public class AccountBalanceEntity extends BaseEntity {
    private static final long serialVersionUID = 6810855470373567061L;
    @Reference
    private UserEntity user;
    private long expense;
    private long income;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public long getExpense() {
        return expense;
    }

    public long getIncome() {
        return income;
    }

    public void setExpense(long expense) {
        this.expense = expense;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return this.getId();
    }

}

