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
package com.zion.account.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.zion.account.AccountBalance;
import com.zion.converter.AccountBalanceConverter;
import com.zion.mongo.db.repository.AccountBalanceRepository;
import com.zion.morphia.entity.AccountBalanceEntity;

public class AccountBalanceServiceImpl implements AccountBalanceService {
    
    private AccountBalanceRepository accountBlRepo;
    
    @Inject
    public AccountBalanceServiceImpl(AccountBalanceRepository accountBlRepo) {
        this.accountBlRepo = accountBlRepo;
    }

    @Override
    public AccountBalance createOrUpdate(AccountBalance accountBalance) {
        if(accountBalance == null) {
            return null;
        }else {
            AccountBalanceEntity entity = this.accountBlRepo.createOrUpdate(AccountBalanceConverter.convertTo(accountBalance));
            return AccountBalanceConverter.convertTo(entity);
        }
    }

    @Override
    public AccountBalance getByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }else {
            AccountBalanceEntity entity = this.accountBlRepo.getByUserId(userId);
            return AccountBalanceConverter.convertTo(entity);
        }
    }

}

