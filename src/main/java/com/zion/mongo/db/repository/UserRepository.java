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


import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.UserEntity;

import java.util.List;

public interface UserRepository {
	UserEntity createUser(UserEntity user);
	
	UserEntity updateUser(UserEntity user);
	
	UserEntity getUser(String id);
	
	List<UserEntity> getNonAdminUsers(Boolean includeDisabled);
	
	List<UserEntity> listAllUsers();
	
	EntityListWrapper<UserEntity> list(QueryCriteria<UserEntity> criteria);
	
	UserEntity getUserByUsername(String username);
	
	List<UserEntity> findUsers(String username);
	
	List<UserEntity> getUsers(List<String> ids);
	
	UserEntity increaseWorkCount(String id);
	
	UserEntity decreaseWorkCount(String id);
	
	UserEntity agreeUploadPhotoCondition(String id);
	
	List<UserEntity> listFakeUsers();
	
	UserEntity generateShortUrl(UserEntity entity);
	
	UserEntity setFirstLookStatus(String id);
	
	List<String> getUserCountries();
}

