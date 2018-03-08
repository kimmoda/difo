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
package com.zion.user.service;


import com.zion.auth.AuthToken;
import com.zion.auth.InvalidAuthToken;
import com.zion.common.AuthorUpdatable;
import com.zion.common.CodeSet;
import com.zion.common.DtoListWrapper;
import com.zion.common.EmptyEmailException;
import com.zion.common.QueryCriteria;
import com.zion.common.RegistrationStatus;
import com.zion.common.RejectRegistrationDto;
import com.zion.common.UsernameChangeDto;
import com.zion.morphia.entity.UserEntity;
import com.zion.registration.UserRegistrationDto;
import com.zion.socialmedia.SocialMediaAuthException;
import com.zion.socialmedia.SocialMediaUserProfile;
import com.zion.user.User;
import com.zion.user.UserAuthDto;
import com.zion.user.UserTagDto;

import java.util.List;
import java.util.Set;

public interface UserService {

	List<User> listNoAdminUsers(Boolean includeDisabled);
	
	List<User> listAllUsers();
	
	DtoListWrapper<User> list(QueryCriteria<UserEntity> criteria);
	
	User createUser(User user) throws DuplicatedUserException;
	
	User updateUser(User user) throws UserNotFoundException;
	
	User updateRegistrationStatus(String userId, RegistrationStatus status) throws UserNotFoundException;
	
	User rejectToBeTrendsetter(RejectRegistrationDto dto) throws UserNotFoundException;
	
	User registerTrendsetter(UserRegistrationDto reg);
	
	User disableUser(String username) throws UserNotFoundException;
	
	User enableUser(String username) throws UserNotFoundException;
	
	User getUserByUsername(String username) throws UserNotFoundException;
	
	User getUserById(String id) throws UserNotFoundException;
	
	SocialMediaUserProfile getSocialMediaUserProfile(AuthToken authToken) throws InvalidAuthToken, SocialMediaAuthException, EmptyEmailException;

	User updateUserPassword(UserAuthDto useAuthDto, String newPassword) throws UserNotFoundException, InvalidPasswordException;

	User getValidUser(UserAuthDto dto);

	User createUserWithoutPassword(User user) throws DuplicatedUserException;

	User updateUserRoles(String username, Set<String> roles) throws UserNotFoundException;
	
	User increaseWorkCount(String id);
	
	User decreaseWorkCount(String id);
	
	User increaseFeedViewedCount(String id);
	
	User updateUserName(UsernameChangeDto userNameChangeDto);
	
	/**
	 * Dynamically update author For example, after users change the avatar, we need to dynamically update their comment's avatar
	 * @param authorUpdatable
	 */
	<T extends AuthorUpdatable>void dynaUpdateAuthor(List<T> authorUpdatableList);

    void setDefaultPassword(String username) throws UserNotFoundException;
    
    User agreeUploadPhotoCondition(String id);

    User increaseTrackingCount(String userId);
    
    User updateUserTags(UserTagDto dto);
    
    User setFirstLookStatus(String id);
    
    List<CodeSet> getUserCountries();

}

