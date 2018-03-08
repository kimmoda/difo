/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2017 Forward Thinking Ltd
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.action.pub.FeedViewActionBean;
import com.zion.common.AppConfig;
import com.zion.common.FeedStatus;
import com.zion.common.QueryCriteria;
import com.zion.converter.FeedConverter;
import com.zion.feed.FeedCount;
import com.zion.morphia.entity.FeedEntity;
import com.zion.morphia.entity.TagEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.shorturl.ShortUrlService;

public class FeedRepositoryImpl extends AbstractRepository implements FeedRepository {
    private static final String FEED_VIEW_URL_FORMAT = "%s" + FeedViewActionBean.URL + "?feedId=%s";
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedRepositoryImpl.class);


    private UserRepository userRepo;
    private TagRepository tagRepo;
    private ShortUrlService shortUrlService;

    @Inject
    public FeedRepositoryImpl(UserRepository userRepo, ShortUrlService shortUrlService, TagRepository tagRepo) {
        this.userRepo = userRepo;
        this.shortUrlService = shortUrlService;
        this.tagRepo = tagRepo;
    }

    @Override
    public FeedEntity saveOrUpdate(FeedEntity entity) {
        FeedEntity updatedEntity;
        if (StringUtils.isNotBlank(entity.getId())) {
            FeedEntity existingEntity = this.getById(entity.getId());
            if (existingEntity == null) {
                throw new RuntimeException(String.format("Cannot find feed with id '%s'.", entity.getId()));
            }
            FeedConverter.updateFeedEntity(entity, existingEntity);
            updatedEntity = this.update(existingEntity);
        } else {
            UserEntity userEntity = this.userRepo.getUser(entity.getAuthor().getId());
            if (userEntity == null) {
                throw new RuntimeException(String.format("Cannot create feed with author id '%s'.", entity.getAuthor().getId()));
            }
            entity.setAuthor(userEntity);
            TagEntity defaultTag = this.tagRepo.getDefaultFeedSysTag();
            if(defaultTag != null) {
                Set<TagEntity> sysTags = new HashSet<>();
                sysTags.add(defaultTag);
                entity.setSysTags(sysTags);
            }else {
                throw new IllegalStateException("cannot create feed as missing default system default tag. Please upgrad your platform.");
            }
            updatedEntity = this.persist(entity);
        }
        // Require feed id to generate short url. So need to call generateShortUrl after creating feedEntity
        return updatedEntity != null ? this.generateShortUrl(updatedEntity) : null;
    }

    @Override
    public FeedEntity update(FeedEntity entity) {
        return super.update(entity);
    }

    @Override
    public FeedEntity getById(String id) {
        return this.get(FeedEntity.class, new ObjectId(id));
    }

    @Override
    public FeedEntity disable(String id) {
        return this.disableEntity(FeedEntity.class, new ObjectId(id));
    }

    @Override
    public FeedEntity enable(String id) {
        return this.enableEntity(FeedEntity.class, new ObjectId(id));
    }

    @Override
    public EntityListWrapper<FeedEntity> list(QueryCriteria<FeedEntity> criteria) {
        return super.search(criteria);
    }

    @Override
    public List<FeedEntity> listFeeds(List<String> ids) {
        List<ObjectId> objectIds = new ArrayList<>();
        for (String id : ids) {
            objectIds.add(new ObjectId(id));
        }
        return super.get(FeedEntity.class, objectIds);
    }

    @Override
    public List<FeedEntity> listAllFeeds() {
        return super.listAll(FeedEntity.class);
    }

    @Override
    public FeedEntity generateShortUrl(FeedEntity feedEntity) {
        Validate.notNull(feedEntity);
        Validate.notEmpty(feedEntity.getId());
        if (StringUtils.isBlank(feedEntity.getShortUrl())) {
            // generate feed single page url
            String host = AppConfig.getInstance().getAppHost();
            String feedViewUrl = String.format(FEED_VIEW_URL_FORMAT, host, feedEntity.getId());
            String shortUrl = shortUrlService.getShortUrl(feedViewUrl);
            feedEntity.setShortUrl(shortUrl);
            return super.update(feedEntity);
        }
        return feedEntity;
    }

    @Override
    public FeedCount getMyFeedCount(String authorId) {
        Validate.notEmpty(authorId);
        long draftCount = this.getMyFeedCount(authorId, FeedStatus.DRAFT.name());
        long finalCount = this.getMyFeedCount(authorId, FeedStatus.FINAL.name());
        return new FeedCount(draftCount, finalCount);
    }

    @Override
    public long getMyFeedCount(String authorId, String status) {
        Query<FeedEntity> query = this.getMongoDatastore().find(FeedEntity.class);
        query.and(query.criteria("author")
                .equal(new Key<UserEntity>(UserEntity.class, "zion_user", new ObjectId(authorId))));
        query.and(query.criteria("status").equal(status));
        query.and(query.criteria("enabled").equal(true));
        return query.count();
    }
    
    private List<FeedEntity> getFeedEntityByAuthorId(String authorId) {
        Query<FeedEntity> query = this.getMongoDatastore().find(FeedEntity.class);
        query.and(query.criteria("author")
                .equal(new Key<UserEntity>(UserEntity.class, "zion_user", new ObjectId(authorId))));
        query.and(query.criteria("enabled").equal(true));
        return query.asList();
    }

    @Override
    public void updateFeedAuthorRoles(String authorId) {
        List<FeedEntity> feedEntities = this.getFeedEntityByAuthorId(authorId);
        if (feedEntities != null && !feedEntities.isEmpty()) {
            LOGGER.info("start update feed author roles for user " + authorId);
            int i = 0;
            for(FeedEntity entity: feedEntities) {
                UserEntity author = entity.getAuthor();
                Set<String> roles = author.getUserRoles();
                entity.setAuthorRoles(roles);
                super.update(entity);
                i++;
            }
            LOGGER.info(String.format("end of update '%d' feeds' author roles for user %s", i, authorId));
        }else {
            LOGGER.info(String.format("Cannot find feeds for user '%s' when updating feeds' author roles", authorId));
        }
    }

}
