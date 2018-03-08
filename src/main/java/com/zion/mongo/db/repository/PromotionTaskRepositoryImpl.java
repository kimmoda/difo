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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.zion.action.pub.CampaignActionBean;
import com.zion.common.AppConfig;
import com.zion.shorturl.ShortUrlService;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import com.zion.account.NotEnoughBalanceException;
import com.zion.common.LinkPreviewException;
import com.zion.common.QueryCriteria;
import com.zion.common.service.LinkPreviewService;
import com.zion.converter.PromotionTaskConverter;
import com.zion.morphia.entity.AccountBalanceEntity;
import com.zion.morphia.entity.CommonConfigEntity;
import com.zion.morphia.entity.PromotionTaskEntity;
import com.zion.morphia.entity.TaskTypeEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.task.TaskTypeName;

public class PromotionTaskRepositoryImpl extends AbstractRepository implements PromotionTaskRepository {
    private static final String CAMPAIGN_VIEW_URL_FORMAT = "%s" + CampaignActionBean.URL + "?campaignId=%s";
    private UserRepository userRepo;
    private CommonConfigRepository configRepo;
    private TaskTypeRepository taskTypeRepo;
    private AccountBalanceRepository accountRepo;
    private LinkPreviewService linkPreviewService;
    private ShortUrlService shortUrlService;

    @Inject
    public PromotionTaskRepositoryImpl(UserRepository userRepo, CommonConfigRepository configRep,
                                       TaskTypeRepository taskTypeRepo, AccountBalanceRepository accountRepo, LinkPreviewService linkPreviewService, ShortUrlService shortUrlService) {
        this.userRepo = userRepo;
        this.configRepo = configRep;
        this.taskTypeRepo = taskTypeRepo;
        this.accountRepo = accountRepo;
        this.linkPreviewService = linkPreviewService;
        this.shortUrlService = shortUrlService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PromotionTaskEntity createOrUpdate(PromotionTaskEntity entity) throws NotEnoughBalanceException, LinkPreviewException {
        String id = entity.getId();
        UserEntity creator = entity.getAuthor();

        if (creator == null) {
            throw new IllegalArgumentException("cannot update or create promotion task as creator is missing");
        }
        UserEntity existingUser = this.userRepo.getUser(creator.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException(
                    String.format("cannot update or create promotion task as creator '%s' cannot be found", creator.getId()));
        }
        TaskTypeEntity taskTypeEntity = entity.getTaskType();
        if (taskTypeEntity == null) {
            throw new IllegalArgumentException("cannot update or create promotion task as task type is null.");
        }
        String taskTypeId = taskTypeEntity.getId();
        if (StringUtils.isBlank(taskTypeId)) {
            throw new IllegalArgumentException("cannot update or create promotion task as task type id is blank");
        }
        TaskTypeEntity taskType = this.taskTypeRepo.getById(taskTypeId);
        if (taskType == null) {
            throw new IllegalArgumentException("cannot update or create promotion task as task type cannot found");
        }
        entity.setAuthor(existingUser);
        entity.setTaskType(taskType);
        Object linkPreviewJson = null;
        try {
            String encodedUrl = URLEncoder.encode(entity.getPostUrl().trim(), "UTF-8");
            linkPreviewJson = this.linkPreviewService.getHybridGraph(encodedUrl);
        } catch (UnsupportedEncodingException e) {
            throw new LinkPreviewException(e.getMessage());
        }
        if (linkPreviewJson != null && linkPreviewJson instanceof Map) {
            entity.setPostUrlPreview((Map<String, String>) linkPreviewJson);
        } else {
            throw new LinkPreviewException();
        }
        if (id == null) {
            entity.setExpiredDate(this.getExpireDate());
            AccountBalanceEntity accountBalanceEntity = this.getAccountBalance(entity);
            PromotionTaskEntity newTask = super.persist(entity);
            // order is important.Put it after task has been create.
            this.accountRepo.createOrUpdate(accountBalanceEntity);
            newTask = generateShortUrl(newTask);
            return newTask;
        } else {
            PromotionTaskEntity existed = this.getById(id.trim());
            if (!existed.getAuthor().equals(entity.getAuthor())) {
                throw new IllegalArgumentException("cannot update promotion task as user does not match creator");
            }
            PromotionTaskEntity update = PromotionTaskConverter.update(entity, existed);
            return super.update(update);
        }
    }

    @Override
    public boolean isTaskExpired(String taskId) {
        PromotionTaskEntity task = this.getById(taskId);
        if (task == null) {
            return false;
        } else {
            Date expiredDate = task.getExpiredDate();
            DateTime now = new DateTime();
            return now.isAfter(expiredDate.getTime());
        }
    }

    private Date getExpireDate() {
        DateTime now = new DateTime();
        CommonConfigEntity enabledConfigByKey = this.configRepo.getEnabledConfigByKey("promotion.task.lifespan");
        int hours = Integer.valueOf(enabledConfigByKey.getValue());
        return now.plusHours(hours).toDate();
    }

    private AccountBalanceEntity getAccountBalance(PromotionTaskEntity task) throws NotEnoughBalanceException {
        AccountBalanceEntity accEntity = this.accountRepo.getByUserId(task.getAuthor().getId());
        if (accEntity == null) {
            throw new IllegalStateException("cannot create task as account does not exist.");
        }
        long balance = accEntity.getIncome() - accEntity.getExpense();
        int promotionTaskCreationCost = this.taskTypeRepo.getByName(TaskTypeName.CREATE_CAMPAIGN.name()).getCost();
        if (balance < promotionTaskCreationCost) {
            throw new NotEnoughBalanceException("cannot create task as do not have enough balance.");
        }
        accEntity.setExpense(accEntity.getExpense() + promotionTaskCreationCost);
        return accEntity;
    }

    @Override
    public PromotionTaskEntity getById(String id) {
        return super.get(PromotionTaskEntity.class, new ObjectId(id));
    }

    @Override
    public EntityListWrapper<PromotionTaskEntity> list(QueryCriteria<PromotionTaskEntity> criteria) {
        return super.search(criteria);
    }

    @Override
    public PromotionTaskEntity disable(String id) {
        return super.disableEntity(PromotionTaskEntity.class, new ObjectId(id));
    }

    @Override
    public PromotionTaskEntity generateShortUrl(PromotionTaskEntity promotionTaskEntity) {
        Validate.notNull(promotionTaskEntity);
        Validate.notEmpty(promotionTaskEntity.getId());
        if (StringUtils.isBlank(promotionTaskEntity.getShortUrl())) {
            // generate campaign single page url
            String shortUrl = shortUrlService.getShortUrl(generateOriUrl(promotionTaskEntity.getId()));
            promotionTaskEntity.setShortUrl(shortUrl);
            return super.update(promotionTaskEntity);
        }
        return promotionTaskEntity;
    }

    @Override
    public List<PromotionTaskEntity> getActiveTasks(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("cannot get active tasks as creator id is missing");
        }
        Query<PromotionTaskEntity> query = this.getMongoDatastore().find(PromotionTaskEntity.class);
        query.and(query.criteria("author")
                .equal(new Key<UserEntity>(UserEntity.class, "zion_user", new ObjectId(userId))));
        query.and(query.criteria("expiredDate").greaterThan(new Date()));
        query.order("-expiredDate");
        return query.asList();
    }

    @Override
    public List<PromotionTaskEntity> listAllTasks() {
        return super.listAll(PromotionTaskEntity.class);
    }

    @Override
    public String generateOriUrl(String campaignId) {
        String host = AppConfig.getInstance().getAppHost();
        return String.format(CAMPAIGN_VIEW_URL_FORMAT, host, campaignId);
    }
}
