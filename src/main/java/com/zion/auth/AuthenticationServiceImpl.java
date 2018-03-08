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

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.zion.common.EmptyEmailException;
import com.zion.converter.UserConverter;
import com.zion.registration.service.StylistRegistrationService;
import com.zion.socialmedia.SocialMediaAuthException;
import com.zion.socialmedia.SocialMediaUserProfile;
import com.zion.socialmedia.SocialSource;
import com.zion.user.User;
import com.zion.user.service.DuplicatedUserException;
import com.zion.user.service.UserNotFoundException;
import com.zion.user.service.UserService;

public class AuthenticationServiceImpl implements AuthenticationService{
    private UserService userService;
    private JwtService jwtService;
    private StylistRegistrationService regService;
    private UserConverter userConverter = new UserConverter();
    
    @Inject
    public AuthenticationServiceImpl(UserService userService, JwtService jwtService, StylistRegistrationService regService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.regService = regService;
    }

    @Override
    public User socialMediaLogin(AuthToken authToken) throws InvalidAuthToken, SocialMediaAuthException, DuplicatedUserException, EmptyEmailException {
        if(authToken == null) {
            throw new IllegalArgumentException("cannot perform social media login as auth token is null");
        }
        String source = authToken.getSource();
        if(StringUtils.isNotBlank(source)) {
            try {
                SocialSource.valueOf(source.trim());
            }catch(Exception e) {
                throw new IllegalArgumentException(String.format("'%s' source is not supported ",  source));
            }
        }else {
            throw new IllegalArgumentException("user source field is empty. Please contact admin");
        }
        if(StringUtils.isBlank(authToken.getAuthToken())) {
            throw new IllegalArgumentException("auth token field is empty. Please contact admin");
        }
        
        SocialMediaUserProfile userProfile = this.userService.getSocialMediaUserProfile(authToken);
        String userName = userConverter.convertToUserName(userProfile);
        User user;
        try {
            user = userService.getUserByUsername(userName);
        } catch (UserNotFoundException e) {
            user = userService.createUserWithoutPassword(new UserConverter().convertToModel(userProfile));
            //send email to the new user
            if (user != null) {
                this.regService.sendWelcomeEmail(user);
            }
            
        }
        JwtClaim newClaim = jwtService.constructDefaultJwtClaim(user.getUsername().trim());
        String jwtToken = jwtService.issueToken(newClaim);
        user.setJwt(jwtToken);
        return user;
   
    }

}

