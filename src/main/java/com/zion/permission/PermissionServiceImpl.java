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

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.auth.InvalidJwtToken;
import com.zion.auth.JwtClaim;
import com.zion.auth.JwtService;
import com.zion.comment.Comment;
import com.zion.comment.CommentDto;
import com.zion.comment.service.CommentService;
import com.zion.common.RegistrationStatus;
import com.zion.common.UserRole;
import com.zion.rate.Rate;
import com.zion.rate.RateDto;
import com.zion.rate.service.RateService;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;
import com.zion.user.service.UserService;

public class PermissionServiceImpl implements PermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);

    private UserService userService;
    private JwtService jwtService;
    private RateService rateService;
    private CommentService commentService;

    @Inject
    public PermissionServiceImpl(UserService userService, JwtService jwtService, 
            RateService rateService, CommentService commentService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.rateService = rateService;
        this.commentService = commentService;
    }

    @Override
    public boolean hasAnyRole(String jwtToken, String... roles) throws UserNotFoundException {
        try {
            User user = this.getUser(jwtToken);
            if (user == null) {
                throw new InvalidJwtToken("Cannot find user from jwt token.");
            }

            return this.containAnyRole(user, roles);
        } catch (InvalidJwtToken e) {
            LOGGER.debug("the jwtToken is invalid.");
        }
        return false;
    }

    @Override
    public boolean containAnyRole(User user, String... roles) {
        List<String> evaluatedRoles = Arrays.asList(roles);
        return user.getUserRoles().stream().anyMatch(r -> evaluatedRoles.contains(r));
    }

    @Override
    public boolean isValidJwtToken(String jwtToken) {
        return this.jwtService.isValidToken(jwtToken);
    }

    @Override
    public User getUser(String jwtToken) throws UserNotFoundException, InvalidJwtToken {
            JwtClaim claim = this.jwtService.getJwtClaim(jwtToken);
            String username = claim.getSub();
            User user =  userService.getUserByUsername(username);
            if(user == null) {
                throw new UserNotFoundException("Cannot find the user as the jwt token is invalid");
            }else {
                return user;
            }
    }

    @Override
    public boolean canCreateUser(String jwt) throws InvalidJwtToken {
        try {
            User loggedInUser = this.getUser(jwt);
            return this.containAnyRole(loggedInUser, UserRole.ADMIN.name());
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean canUpdateUser(String jwt, String updatingUserId) throws InvalidJwtToken {
        try {
            User loggedInUser = this.getUser(jwt);
            return this.containAnyRole(loggedInUser, UserRole.ADMIN.name()) || loggedInUser.getId().equals(updatingUserId);
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean canUpdateUserRole(String jwt) throws InvalidJwtToken {
        try {
            User loggedInUser = this.getUser(jwt);
            return this.containAnyRole(loggedInUser, UserRole.ADMIN.name());
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean canUpdateUserStatus(String jwt, String updatingUserId) throws InvalidJwtToken {
        try {
            User loggedInUser = this.getUser(jwt);
            return this.containAnyRole(loggedInUser, UserRole.ADMIN.name()) && !loggedInUser.getId().equals(updatingUserId);
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean isValidUser(String userId) {
        try {
            User user = this.userService.getUserById(userId);
            return user != null;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean canApplyStylist(String jwt) throws InvalidJwtToken {
        try {
            User loggedInUser = this.getUser(jwt);
            return !this.containAnyRole(loggedInUser, UserRole.STYLIST.name(), UserRole.DESIGNER.name(), UserRole.INFLUENCER.name());
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean canApproveStylist(String jwt, String stylistId) throws InvalidJwtToken {
        try {
            User loggedInUser = this.getUser(jwt);
            boolean isLoggedInUserAdmin = this.containAnyRole(loggedInUser, UserRole.ADMIN.name());
            User user = this.userService.getUserById(stylistId);
            boolean isStylist = this.containAnyRole(user, UserRole.STYLIST.name(), UserRole.DESIGNER.name(), UserRole.INFLUENCER.name());
            boolean isPending = user.getRegistrationStatus().equals(RegistrationStatus.PENDING.name());
            return isLoggedInUserAdmin && isStylist && isPending;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean canRejectStylist(String jwt, String rejectedUserId) throws InvalidJwtToken {
        try {
            User loggedInUser = this.getUser(jwt);
            boolean isLoggedInUserAdmin = this.containAnyRole(loggedInUser, UserRole.ADMIN.name());
            User user = this.userService.getUserById(rejectedUserId);
            boolean isUserCanBeReject = this.containAnyRole(user, UserRole.STYLIST.name(), UserRole.DESIGNER.name(), UserRole.INFLUENCER.name());
            return isLoggedInUserAdmin && isUserCanBeReject;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean canRateUser(String loggedInUser, Rate rate) {
        try {
            User user;
            if (StringUtils.isNotBlank(rate.getId())) {
                RateDto dto = this.rateService.get(rate.getId());
                if (dto == null) {
                    LOGGER.warn(String.format("cannot update rate as the rate 's' is not existing", rate.getId()));
                    return false;
                }
                if (!dto.getUserId().equals(loggedInUser)) {
                    LOGGER.warn(
                            String.format("cannot update rate as the rate '%s' does not belong to user '%s'", rate.getId(), loggedInUser));
                    return false;
                }
                user = this.userService.getUserById(dto.getDestId());
            } else {
                user = this.userService.getUserById(rate.getDestId());
            }
            boolean isStylist = this.containAnyRole(user, UserRole.STYLIST.name(), UserRole.INFLUENCER.name(), UserRole.DESIGNER.name());
            boolean notRateYourSelf = !loggedInUser.equals(user.getId());
            return isStylist && notRateYourSelf;
        } catch (UserNotFoundException e) {
            LOGGER.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean canComment(String loggedInUserId, Comment comment) {
        if (StringUtils.isNotBlank(comment.getId())) {
            CommentDto dto = this.commentService.get(comment.getId());
            if (dto == null) {
                LOGGER.warn(String.format("cannot update comment as the comment 's' is not existing", comment.getId()));
                return false;
            }
            if (!dto.getUserId().equals(loggedInUserId)) {
                LOGGER.warn(
                        String.format("cannot update comment as the comment '%s' does not belong to user '%s'", comment.getId(),
                                loggedInUserId));
                return false;
            }

        }
        return true;
    }
}
