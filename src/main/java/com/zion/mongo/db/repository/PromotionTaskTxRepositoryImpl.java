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

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import com.zion.common.AppConfig;
import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.AccountBalanceEntity;
import com.zion.morphia.entity.PromotionTaskEntity;
import com.zion.morphia.entity.PromotionTaskTxEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.task.PromoteOwnTaskException;
import com.zion.task.TaskAlreadyCompleteException;

public class PromotionTaskTxRepositoryImpl extends AbstractRepository implements PromotionTaskTxRepository {

    private UserRepository userRep;
    private PromotionTaskRepository taskRep;
    private AccountBalanceRepository accRepo;
    private static final String systemUserName = AppConfig.getInstance().getSystemUsername();

    @Inject
    public PromotionTaskTxRepositoryImpl(UserRepository userRep, PromotionTaskRepository taskRep, AccountBalanceRepository accRepo) {
        this.userRep = userRep;
        this.taskRep = taskRep;
        this.accRepo = accRepo;
    }

    @Override
    public PromotionTaskTxEntity create(PromotionTaskTxEntity entity) throws PromoteOwnTaskException, TaskAlreadyCompleteException {
        UserEntity to = entity.getTxTo();
        PromotionTaskEntity taskEntity = entity.getTask();
        if (to == null) {
            throw new IllegalArgumentException("cannot create a new promotion task tx as 'to user' is null");
        }
        if (taskEntity == null) {
            throw new IllegalArgumentException("cannot create a new promotion task tx as 'task' is null");
        }

        PromotionTaskTxEntity existingTx = this.getTxByReceiver(taskEntity.getId(), to.getId());
        if (existingTx != null) {
            throw new TaskAlreadyCompleteException(
                    String.format("cannot create a new promotion task tx as this user '%s' already done this task", to.getId()));
        }

        UserEntity existingFrom = userRep.getUserByUsername(systemUserName);
        UserEntity existingTo = userRep.getUser(to.getId());
        PromotionTaskEntity existingTaskEntity = taskRep.getById(taskEntity.getId());

        if (existingFrom == null) {
            throw new IllegalArgumentException(
                    String.format("cannot create a new promotion task tx as system user '%s' is null", systemUserName));
        }
        if (existingTo == null) {
            throw new IllegalArgumentException(String.format("cannot create a new promotion task tx  as to user '%s' is null", to.getId()));
        }
        if (existingTaskEntity == null) {
            throw new IllegalArgumentException(
                    String.format("cannot create a new promotion task tx as promotion task '%s' is null", taskEntity.getId()));
        }

        if (existingTaskEntity.getAuthor().getId().equals(existingTo.getId())) {
            throw new PromoteOwnTaskException(String.format(
                    "cannot create a new promtion task tx as user '%s' cannot promote his or her own task '%s'", to.getId(), taskEntity.getId()));
        }
        entity.setTxFrom(existingFrom);
        entity.setTxTo(existingTo);
        entity.setTask(existingTaskEntity);
        int defaultReward = existingTaskEntity.getTaskType().getReward();
        //Make the game more interesting, user will get a random reward based on the default reward.
        long randomReward = this.getRandomReward(0.5, 4, defaultReward);
        entity.setAmount(randomReward);
        PromotionTaskTxEntity persisted = super.persist(entity);
        this.updateAccount(persisted);
        return persisted;
    }
    
    //when random rate between 0.8 to 4. eg: if a default reward is 20, the random reward will be between 10 and 80
    private long getRandomReward(double min, double max, long defaultReward) {
        Random r = new Random();
        int bound = (int) ((max- min)*10 +1);
        double randomRate = (r.nextInt(bound) + min*10)/10.0;
        return (long) (randomRate * defaultReward);
    }

    private void updateAccount(PromotionTaskTxEntity entity) {
        //Do not decrease "From" account as it is a system account. It can supply coin as much as it like.
        //Increase the "To" account's income.
        AccountBalanceEntity accToEntity = accRepo.getByUserId(entity.getTxTo().getId());
        if (accToEntity == null) {
            AccountBalanceEntity defaultToAcc = new AccountBalanceEntity();
            defaultToAcc.setUser(entity.getTxTo());
            accToEntity = accRepo.createOrUpdate(defaultToAcc);
        }
        long income = accToEntity.getIncome() + entity.getAmount();
        accToEntity.setIncome(income);
        this.accRepo.createOrUpdate(accToEntity);
    }

    @Override
    public EntityListWrapper<PromotionTaskTxEntity> searchTx(QueryCriteria<PromotionTaskTxEntity> criteria) {
        return super.search(criteria);
    }

    @Override
    public List<PromotionTaskTxEntity> getTxByTaskId(String taskId) {
        Query<PromotionTaskTxEntity> query = this.getMongoDatastore().find(PromotionTaskTxEntity.class);
        query.and(query.criteria("task")
                .equal(new Key<PromotionTaskEntity>(PromotionTaskEntity.class, "zion_promotion_task", new ObjectId(taskId))));
        return query.asList();
    }

    @Override
    public PromotionTaskTxEntity getTxByReceiver(String taskId, String receiverId) {
        Query<PromotionTaskTxEntity> query = this.getMongoDatastore().find(PromotionTaskTxEntity.class);
        query.and(query.criteria("task")
                .equal(new Key<PromotionTaskEntity>(PromotionTaskEntity.class, "zion_promotion_task", new ObjectId(taskId))));
        query.and(query.criteria("txTo")
                .equal(new Key<UserEntity>(UserEntity.class, "zion_user", new ObjectId(receiverId))));
        return query.get();
    }

}
