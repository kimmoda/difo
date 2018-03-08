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
package com.zion.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zion.user.User;

@JsonInclude(Include.NON_NULL)
public class AccountBalance {

    private User user;
    private long expense;
    private long income;
    private long balance;

    public User getUser() {
        return user;
    }
    public long getExpense() {
        return expense;
    }
    public long getIncome() {
        return income;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setExpense(long expense) {
        this.expense = expense;
    }
    public void setIncome(long income) {
        this.income = income;
    }
    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }
}

