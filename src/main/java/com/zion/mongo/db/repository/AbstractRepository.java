package com.zion.mongo.db.repository;

import com.zion.common.QueryCriteria;
import com.zion.mongo.config.MongoDBConnectionService;
import com.zion.morphia.entity.BaseEntity;
import com.zion.morphia.entity.PromotionTaskEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.task.TaskStatus;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public abstract class AbstractRepository implements GenericRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepository.class);

    @Inject
    private MongoDBConnectionService mongoDBConnectionService;

    protected Datastore getMongoDatastore() {
        return mongoDBConnectionService.getDatastore();
    }

    public void setMongoDatastore(MongoDBConnectionService mongoDBConnectionService) {
        this.mongoDBConnectionService = mongoDBConnectionService;
    }

    public String getCurrentMongoDBName() {
        return getMongoDatastore().getDB().getName();
    }

    @Override
    public <E extends BaseEntity> E persist(E entity) {
        Validate.notNull(entity);
        Key<E> key = getMongoDatastore().save(entity);
        entity.setEntityId(new ObjectId(key.getId().toString()));
        return entity;
    }

    @Override
    public <E extends BaseEntity> E update(E entity) {
        Validate.notNull(entity);
        Validate.notNull(entity.getId());

        Key<E> key = getMongoDatastore().save(entity);
        entity.setEntityId(new ObjectId(key.getId().toString()));
        return entity;
    }

    @Override
    public <E extends BaseEntity> E delete(E entity) {
        Validate.notNull(entity);
        Validate.notNull(entity.getId());

        getMongoDatastore().delete(entity);
        return entity;
    }

    @Override
    public <E extends BaseEntity> long count(Class<E> clazz) {
        if (clazz == null) {
            return 0;
        }
        return getMongoDatastore().find(clazz).count();
    }

    public <E extends BaseEntity> List<E> listAll(Class<E> clazz) {
        if (clazz == null) {
            return null;
        }
        return getMongoDatastore().find(clazz).asList();
    }

    public <E extends BaseEntity> List<E> listAllEnabled(Class<E> clazz) {
        if (clazz == null) {
            return null;
        }
        return getMongoDatastore().find(clazz).field("enabled").equal(true).asList();
    }

    @Override
    public <E extends BaseEntity> E get(Class<E> clazz, final ObjectId id) {
        if ((clazz == null) || (id == null)) {
            return null;
        }
        return getMongoDatastore().find(clazz).field("entityId").equal(id)
                .get();
    }

    @Override
    public <E extends BaseEntity> List<E> get(Class<E> clazz, List<ObjectId> ids) {
        if (clazz == null || ids == null || ids.isEmpty()) {
            return null;
        }
        return getMongoDatastore().get(clazz, ids).asList();
    }

    @Override
    public <E extends BaseEntity> E enableEntity(Class<E> clazz, final ObjectId id) {
        E e = this.get(clazz, id);
        if (e == null) {
            throw new IllegalStateException(String.format("cannot find entity with id '%s' when enable it. ", id));
        }
        e.setEnabled(true);
        return this.update(e);
    }

    @Override
    public <E extends BaseEntity> E disableEntity(Class<E> clazz, final ObjectId id) {
        E e = this.get(clazz, id);
        if (e == null) {
            throw new IllegalStateException(String.format("cannot find entity with id '%s' when disable it. ", id));
        }
        e.setEnabled(false);
        return this.update(e);
    }

    @Override
    public <E extends BaseEntity> E getEnabled(Class<E> clazz, ObjectId id) {
        if ((clazz == null) || (id == null)) {
            return null;
        }

        Query<E> query = getMongoDatastore().find(clazz);
        query.field("entityId").equal(id);
        query.field("enabled").equal(true);
        return query.get();
    }

    @Override
    public <E extends BaseEntity> EntityListWrapper<E> search(QueryCriteria<E> criteria) {
        String sortBy = criteria.getSortBy();
        List<E> entities = this.searchByCriteria(criteria);
        Long nextToken = null;
        if (entities != null && entities.size() == criteria.getResultSize() + 1) {
            if (StringUtils.isNotBlank(sortBy)) {
                if (sortBy.equals("publishAt")) {
                    nextToken = entities.get(entities.size() - 1).getPublishAt().getTime();
                }
                if (sortBy.equals("creationDate")) {
                    nextToken = entities.get(entities.size() - 1).getCreationDate().getTime();
                } else {
                    nextToken = null;
                    LOGGER.info(String.format("Do not support next token when sort by '%s' now.", sortBy));
                }
            }
            entities = entities.subList(0, criteria.getResultSize());
        }
        EntityListWrapper<E> wrapper = new EntityListWrapper<>();
        if (nextToken != null) {
            wrapper.setNextPageToken(nextToken);
        }
        if (entities != null && !entities.isEmpty()) {
            wrapper.setEntities(entities);
        } else {
            wrapper.setEntities(new ArrayList<>());
        }
        return wrapper;
    }

    private <E extends BaseEntity> List<E> searchByCriteria(QueryCriteria<E> criteria) {
        Query<E> query = getMongoDatastore().find(criteria.getClazz());
        String nextPageToken = criteria.getNextPageToken();
        String sortBy = criteria.getSortBy();
        if (StringUtils.isNotBlank(nextPageToken)) {
            try {
                Long milliseconds = Long.valueOf(nextPageToken.trim());
                Date date = new Date(milliseconds);
                if (StringUtils.isNotBlank(sortBy)) {
                    if (criteria.getAscOrder() != null && criteria.getAscOrder()) {
                        query.field(sortBy).greaterThanOrEq(date);
                    } else {
                        query.field(sortBy).lessThanOrEq(date);
                    }
                }
            } catch (Exception e) {
                LOGGER.info(String.format("Cannot convert the nextPageToken '%s' to last update date.", nextPageToken));
            }
        }
        if (criteria.getIncludeDisable() != null && !criteria.getIncludeDisable()) {
            query.and(query.criteria("enabled").equal(true));
        }

        if (criteria.getWorkCountBaseLine() != null) {
            query.and(query.criteria("workCount").greaterThanOrEq(criteria.getWorkCountBaseLine()));
        }
        if (StringUtils.isNotBlank(criteria.getUserCountryCode())) {
            query.and(query.criteria("contact.country").equal(criteria.getUserCountryCode().trim()));
        }
        if (StringUtils.isNotBlank(criteria.getStatus())) {
            query.and(query.criteria("status").equal(criteria.getStatus()));
        }
        if (StringUtils.isNotBlank(criteria.getLikeStatus())) {
            query.and(query.criteria("likeStatus").equal(criteria.getLikeStatus()));
        }
        if (criteria.getTaskStatus() != null) {
            Date currentDate = new Date();
            if (criteria.getTaskStatus().equals(TaskStatus.ACTIVE)) {
                query.and(query.criteria("expiredDate").greaterThan(currentDate));
            }
            if (criteria.getTaskStatus().equals(TaskStatus.EXPIRED)) {
                query.and(query.criteria("expiredDate").lessThan(currentDate));
            }
        }
        if (StringUtils.isNotBlank(criteria.getCategory())) {
            query.and(query.criteria("category").equal(criteria.getCategory()));
        }
        if (StringUtils.isNotBlank(criteria.getRegistrationStatus())) {
            query.and(query.criteria("registrationStatus").equal(criteria.getRegistrationStatus()));
        }
        if (criteria.getContainUserRoles() != null && !criteria.getContainUserRoles().isEmpty()) {
            query.and(query.criteria("userRoles").hasAnyOf(criteria.getContainUserRoles()));
        }
        if (criteria.getContainAuthorRoles() != null && !criteria.getContainAuthorRoles().isEmpty()) {
            query.and(query.criteria("authorRoles").hasAnyOf(criteria.getContainAuthorRoles()));
        }
        if (StringUtils.isNotBlank(criteria.getUserId())) {
            query.and(query.criteria("userId").equal(criteria.getUserId()));
        }
        if (StringUtils.isNotBlank(criteria.getAuthorId())) {
            query.and(query.criteria("author")
                    .equal(new Key<UserEntity>(UserEntity.class, "zion_user", new ObjectId(criteria.getAuthorId()))));
        }
        if (StringUtils.isNotBlank(criteria.getTaskId())) {
            query.and(query.criteria("task").equal(
                    new Key<PromotionTaskEntity>(PromotionTaskEntity.class, "zion_promotion_task", new ObjectId(criteria.getTaskId()))));
        }
        if (StringUtils.isNotBlank(criteria.getTxToId())) {
            query.and(query.criteria("txTo")
                    .equal(new Key<UserEntity>(UserEntity.class, "zion_user", new ObjectId(criteria.getTxToId()))));
        }
        if (StringUtils.isNotBlank(criteria.getDestId())) {
            query.and(query.criteria("destId").equal(criteria.getDestId()));
        }
        if (StringUtils.isNotBlank(criteria.getCommentDestType())) {
            query.and(query.criteria("commentDest").equal(criteria.getCommentDestType()));
        }
        if (StringUtils.isNotBlank(criteria.getFanId())) {
            query.and(query.criteria("fanId").equal(criteria.getFanId()));
        }
        if (criteria.getTags() != null && !criteria.getTags().isEmpty()) {
            query.and(query.criteria("feedTags.code").in(criteria.getTags()));
        }
        if (criteria.getSysTags() != null && !criteria.getSysTags().isEmpty()) {
            query.and(query.criteria("sysTags.code").in(criteria.getSysTags()));
        }
        if (StringUtils.isNotBlank(sortBy)) {
            String orderSign = criteria.getAscOrder() != null && criteria.getAscOrder() ? "" : "-";
            query.order(orderSign + sortBy);
        }
        return query.asList(new FindOptions().limit(criteria.getResultSize() + 1));
    }

}
