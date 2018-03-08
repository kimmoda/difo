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
package com.zion.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.zion.auth.InvalidJwtToken;
import com.zion.common.Payload;
import com.zion.common.RejectRegistrationDto;
import com.zion.mail.EmailUtils;
import com.zion.permission.PermissionService;
import com.zion.registration.IllegalUserStatusChangeException;
import com.zion.registration.UserRegistrationDto;
import com.zion.registration.UserRegistration;
import com.zion.registration.service.StylistRegistrationService;
import com.zion.user.ApproveUserDto;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;

@Path("/stylist")
public class StylistRegistrationResource extends BaseResource {
    private StylistRegistrationService registerService;
    private PermissionService permissionService;

    @Inject
    public StylistRegistrationResource(StylistRegistrationService registerService, PermissionService permissionService) {
        this.registerService = registerService;
        this.permissionService = permissionService;
    }

    @POST
    @Path("/v1/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<User> register(@HeaderParam(PARAM_JWT_TOKEN) String jwt, UserRegistration userRegistrationDto) {
        Payload<User> payload = new Payload<>();
        UserRegistrationDto userRegistration = new UserRegistrationDto(userRegistrationDto);
        try {
            if (StringUtils.isBlank(userRegistrationDto.getContactEmail()) || !EmailUtils.isValid(userRegistrationDto.getContactEmail())) {
                payload.setMsg("Invalid email");
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }
            if (StringUtils.isBlank(userRegistrationDto.getSocialMediaUrls())) {
                payload.setMsg("Should contains none empty social media link when applying stylist");
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }
            User user = this.permissionService.getUser(jwt);
            if (!this.permissionService.canApplyStylist(jwt)) {
                payload.setMsg(String.format("The user '%s' cannot apply to be stylist. please check user's role", user.getId()));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
            userRegistration.setUserId(user.getId());
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot found logged in user when register stylist.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Invalid JWT token");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
        try {
            User updatedUser = this.registerService.register(userRegistration);
            payload.setData(updatedUser);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
        } catch (UserNotFoundException e1) {
            payload.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            payload.setMsg(String.format("Cannot register user as user '%s' cannot be found.", userRegistration.getUserId()));
        }
        return payload;
    }

    @POST
    @Path("/v1/approve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<User> approve(@HeaderParam(PARAM_JWT_TOKEN) String jwt, ApproveUserDto approveUserDto) {
        Payload<User> payload = new Payload<>();
        String userId = approveUserDto.getUserId();
        if (StringUtils.isBlank(userId)) {
            payload.setMsg("User id is blank.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(approveUserDto.getRedirectUrl())) {
            payload.setMsg("Redirect url is blank.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        try {
            if (!this.permissionService.canApproveStylist(jwt, userId)) {
                payload.setMsg(String.format("The user '%s' cannot apply to be stylist. please check user's role", userId));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg(String.format("The user '%s' cannot apply to be stylist as jwt is invalid.", userId));
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        try {
            User updatedUser = this.registerService.approve(approveUserDto);
            payload.setData(updatedUser);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
        } catch (UserNotFoundException e1) {
            payload.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            payload.setMsg(String.format("Cannot register user as user '%s' cannot be found.", userId));
        } catch (IllegalUserStatusChangeException e) {
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            payload.setMsg(String.format("Cannot change the user '%s' status, please check the user status.", userId));
        }
        return payload;
    }

    @POST
    @Path("/v1/reject")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Payload<User> reject(@HeaderParam(PARAM_JWT_TOKEN) String jwt, RejectRegistrationDto dto) {
        Payload<User> payload = new Payload<>();
        String rejectedUserId = dto.getRejectedUserId();
        if (StringUtils.isBlank(rejectedUserId)) {
            payload.setMsg("User id is blank.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        try {
            if (!this.permissionService.canRejectStylist(jwt, rejectedUserId)) {
                payload.setMsg(String.format("The user '%s' cannot be rejected. please check user's role", rejectedUserId));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg(String.format("The user '%s' cannot be rejected as jwt is invalid.", rejectedUserId));
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        try {
            User rejectUser = this.registerService.reject(dto);
            payload.setData(rejectUser);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        } catch (UserNotFoundException e) {
            payload.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            payload.setMsg(String.format("Cannot reject user as user '%s' cannot be found.", rejectedUserId));
            return payload;
        }
    }
}
