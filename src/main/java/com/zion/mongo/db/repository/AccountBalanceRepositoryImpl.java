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
package com.zion.mongo.db.repository;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import com.zion.converter.AccountBalanceConverter;
import com.zion.morphia.entity.AccountBalanceEntity;
import com.zion.morphia.entity.UserEntity;

public class AccountBalanceRepositoryImpl extends AbstractRepository implements AccountBalanceRepository {
    private UserRepository userRep;
    
    @Inject
    public AccountBalanceRepositoryImpl(UserRepository userRep) {
        this.userRep = userRep;
    }

    @Override
    public AccountBalanceEntity createOrUpdate(AccountBalanceEntity accountBalance) {
        if (accountBalance == null) {
            throw new IllegalArgumentException("cannot create account balance as account balance is null");
        }
        if (accountBalance.getUser() == null) {
            throw new IllegalArgumentException("cannot create account balance as account user is null");
        }
        String userId = accountBalance.getUser().getId();
        UserEntity existingUser = this.userRep.getUser(userId);
        if (existingUser == null) {
            throw new IllegalArgumentException("cannot create account balance as account user does not exist.");
        }
        accountBalance.setUser(existingUser);
        AccountBalanceEntity accBalance = this.getByUserId(userId);
        if (accBalance != null) {
            if (!accBalance.getUser().getId().equals(existingUser.getId())) {
                throw new IllegalArgumentException("cannot update account balance as account user does not match existing account user.");
            }
            AccountBalanceConverter.update(accountBalance, accBalance);
            return super.update(accBalance);
        }else {
            return super.persist(accountBalance);
        }
        
    }

    @Override
    public AccountBalanceEntity getByUserId(String userId) {
        Query<AccountBalanceEntity> query = this.getMongoDatastore().find(AccountBalanceEntity.class);
        query.and(query.criteria("user")
                .equal(new Key<UserEntity>(UserEntity.class, "zion_user", new ObjectId(userId))));
        return query.get();
    }

}
