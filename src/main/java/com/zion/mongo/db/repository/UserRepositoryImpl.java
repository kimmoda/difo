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

import com.mongodb.BasicDBObject;
import com.zion.action.pub.StylistViewActionBean;
import com.zion.common.AppConfig;
import com.zion.common.QueryCriteria;
import com.zion.common.RegistrationStatus;
import com.zion.common.UserRole;
import com.zion.common.UserSource;
import com.zion.morphia.entity.UserEntity;
import com.zion.shorturl.ShortUrlService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class UserRepositoryImpl extends AbstractRepository implements UserRepository{
    private static final String USER_VIEW_URL_FORMAT = "%s" + StylistViewActionBean.URL + "?userId=%s";
    
    private ShortUrlService shortUrlService;
    
    @Inject
    public UserRepositoryImpl(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

	@Override
	public UserEntity createUser(UserEntity user) {
		UserEntity entity = super.persist(user);
		return entity != null ? this.generateShortUrl(entity) : null;
	}

	@Override
	public UserEntity getUser(String id) {
		return super.get(UserEntity.class, new ObjectId(id));
	}

	@Override
	public List<UserEntity> getNonAdminUsers(Boolean includeDisabled) {
		Query<UserEntity> query = super.getMongoDatastore().find(UserEntity.class).field("userRoles").hasNoneOf(Arrays.asList(UserRole.ADMIN.name()));
		if(includeDisabled != null && !includeDisabled) {
			query.field("enabled").equal(true);
		}
		return query.asList();
	}
	
	@Override
    public List<UserEntity> listAllUsers() {
        return super.getMongoDatastore().find(UserEntity.class).asList();
    }

	@Override
	public UserEntity updateUser(UserEntity user) {
		return super.update(user);
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		return getMongoDatastore().find(UserEntity.class).field("username").equalIgnoreCase(username).get();
	}

	@Override
	public List<UserEntity> findUsers(String username) {
		return getMongoDatastore().find(UserEntity.class)
				.field("username")
				.containsIgnoreCase(username)
				.asList();
	}
	
	@Override
	public List<UserEntity> getUsers(List<String> ids) {
		List<ObjectId> objectIds = new ArrayList<>();
		for(String id: ids) {
			objectIds.add(new ObjectId(id));
		}
		return getMongoDatastore().get(UserEntity.class, objectIds).asList();
	}

	@Override
	public UserEntity increaseWorkCount(String id) {
		UserEntity entity = this.getUser(id);
		long workCount = entity.getWorkCount() + 1;
		entity.setWorkCount(workCount);
		return this.updateUser(entity);
	}

	@Override
	public UserEntity decreaseWorkCount(String id) {
		UserEntity entity = this.getUser(id);
		long count = entity.getWorkCount();
		if(count >= 1) {
			long workCount = count - 1;
			entity.setWorkCount(workCount);
			return this.updateUser(entity);
		}else {
			return entity;
		}
	}

	@Override
	public EntityListWrapper<UserEntity> list(QueryCriteria<UserEntity> criteria) {
		return search(criteria);
	}

    @Override
    public UserEntity agreeUploadPhotoCondition(String id) {
        Validate.notBlank(id);
        UserEntity entity = this.getUser(id);
        if (entity != null) {
            entity.setArgeeUploadPhotoCondition(true);
            return super.update(entity);
        }else {
            return null;
        }
    }

    @Override
    public List<UserEntity> listFakeUsers() {
        return getMongoDatastore().find(UserEntity.class).field("source").equal(UserSource.ZION.name()).asList();
    }

    @Override
    public UserEntity generateShortUrl(UserEntity entity) {
        Validate.notNull(entity);
        Validate.notEmpty(entity.getId());
        if (StringUtils.isBlank(entity.getShortUrl())) {
            // generate user public profile page url
            String host = AppConfig.getInstance().getAppHost();
            String viewUrl = String.format(USER_VIEW_URL_FORMAT, host, entity.getId());
            String shortUrl = shortUrlService.getShortUrl(viewUrl);
            entity.setShortUrl(shortUrl);
            return super.update(entity);
        }
       return entity;
    }

    @Override
    public UserEntity setFirstLookStatus(String id) {
        Validate.notBlank(id);
        UserEntity entity = this.getUser(id);
        if (entity != null) {
            entity.setUploadLooks(true);
            return super.update(entity);
        }else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getUserCountries() {
        BasicDBObject query = new BasicDBObject();
        query.put("userRoles", new BasicDBObject("$in", new String[] {UserRole.DESIGNER.name(), UserRole.STYLIST.name(), UserRole.INFLUENCER.name()}));
        query.put("registrationStatus", new BasicDBObject("$eq", RegistrationStatus.APPROVED.name()));
        return getMongoDatastore().getCollection(UserEntity.class).distinct("contact.country", query);
    }

}

