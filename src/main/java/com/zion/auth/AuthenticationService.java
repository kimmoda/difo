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
package com.zion.auth;

import com.zion.common.EmptyEmailException;
import com.zion.socialmedia.SocialMediaAuthException;
import com.zion.user.User;
import com.zion.user.service.DuplicatedUserException;

public interface AuthenticationService {
    User socialMediaLogin(AuthToken token) throws InvalidAuthToken, SocialMediaAuthException, DuplicatedUserException, EmptyEmailException;
}

