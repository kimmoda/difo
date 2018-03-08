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

import com.zion.account.AccountBalance;
import com.zion.account.AccountBalanceDto;
import com.zion.morphia.entity.AccountBalanceEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.user.User;

public class AccountBalanceConverter {
    private static UserConverter userConverter = new UserConverter();

    public static AccountBalance convertTo(AccountBalanceEntity src) {
        if (src == null) {
            return null;
        }
        AccountBalance dest = new AccountBalance();
        dest.setExpense(src.getExpense());
        dest.setIncome(src.getIncome());
        dest.setUser(userConverter.convertToModel(src.getUser()));
        long bl = src.getIncome() - src.getExpense();
        dest.setBalance(bl);
        return dest;
    }
    
    public static AccountBalance convertTo(AccountBalanceDto src, User user) {
        AccountBalance dest = new AccountBalance();
        dest.setExpense(src.getExpense());
        dest.setIncome(src.getIncome());
        dest.setUser(user);
        long bl = src.getIncome() - src.getExpense();
        dest.setBalance(bl);
        return dest;
    }
    
    public static AccountBalanceEntity convertTo(AccountBalance src) {
        if (src == null) {
            return null;
        }
        AccountBalanceEntity dest = new AccountBalanceEntity();
        dest.setExpense(src.getExpense());
        dest.setIncome(src.getIncome());
        UserEntity user = new UserEntity();
        user.setEntityId(new ObjectId(src.getUser().getId()));
        dest.setUser(user);
        return dest;
    }
    
    public static AccountBalanceEntity update(AccountBalanceEntity src, AccountBalanceEntity dest) {
        dest.setExpense(src.getExpense());
        dest.setIncome(src.getIncome());
        return dest;
    }
}

