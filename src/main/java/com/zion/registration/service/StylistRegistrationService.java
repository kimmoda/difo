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
package com.zion.registration.service;

import com.zion.common.RejectRegistrationDto;
import com.zion.registration.IllegalUserStatusChangeException;
import com.zion.registration.UserRegistrationDto;
import com.zion.user.ApproveUserDto;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;

public interface StylistRegistrationService {
    void sendWelcomeEmail(User user);
    
    void sendEmailAfterFirstLook(User user);

    User register(UserRegistrationDto userRegistration) throws UserNotFoundException;

    User approve(ApproveUserDto approveUserDto) throws UserNotFoundException, IllegalUserStatusChangeException;

    User reject (RejectRegistrationDto dto) throws  UserNotFoundException;
}
