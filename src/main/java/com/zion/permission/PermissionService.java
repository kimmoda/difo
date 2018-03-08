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
package com.zion.permission;

import com.zion.auth.InvalidJwtToken;
import com.zion.comment.Comment;
import com.zion.rate.Rate;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;

public interface PermissionService {
	boolean hasAnyRole(String jwtToken, String... roles) throws UserNotFoundException;

	boolean containAnyRole(User user, String... roles);
	
	boolean isValidUser(String userId);
	
	boolean isValidJwtToken(String jwtToken);

	User getUser(String jwtToken) throws UserNotFoundException, InvalidJwtToken;
	
	boolean canCreateUser(String jwt) throws InvalidJwtToken;
	
	boolean canUpdateUserRole(String jwt) throws InvalidJwtToken;
	
	boolean canUpdateUserStatus(String jwt, String updatingUserId) throws InvalidJwtToken;
	
	boolean canUpdateUser(String jwt, String updatingUserId) throws InvalidJwtToken;
	
	boolean canApplyStylist(String jwt) throws InvalidJwtToken;
	
	boolean canApproveStylist(String jwt, String stylistId) throws InvalidJwtToken;

	boolean canRejectStylist(String jwt, String stylistId) throws InvalidJwtToken;
	
	boolean canRateUser(String loggedInUserId, Rate rate);

	boolean canComment(String loggedInUserId, Comment comment);
}

