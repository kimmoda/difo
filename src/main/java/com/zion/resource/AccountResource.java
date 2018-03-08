/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2018 Forward Thinking Ltd
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
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.zion.account.AccountBalance;
import com.zion.account.AccountBalanceDto;
import com.zion.account.service.AccountBalanceService;
import com.zion.auth.InvalidJwtToken;
import com.zion.common.Payload;
import com.zion.common.UserRole;
import com.zion.converter.AccountBalanceConverter;
import com.zion.permission.PermissionService;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;
import com.zion.user.service.UserService;

@Path("/account")
public class AccountResource extends BaseResource {

    private AccountBalanceService accBlService;
    private PermissionService permissionService;
    private UserService userService;

    @Inject
    public AccountResource(AccountBalanceService accBlService, PermissionService permissionService, UserService userService) {
        this.accBlService = accBlService;
        this.permissionService = permissionService;
        this.userService = userService;
    }

    @POST
    @Path("/admin/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<AccountBalance> update(@HeaderParam(PARAM_JWT_TOKEN) String token, AccountBalanceDto dto) {
        Payload<AccountBalance> payload = new Payload<>();
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot create or update account as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot create or update account as jwt token is not valid.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot create or update account as jwt token is not valid.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        if (!this.permissionService.containAnyRole(loggedInUser, UserRole.ADMIN.name())) {
            payload.setMsg("Cannot create or update account as user does not have permission.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }

        if (StringUtils.isBlank(dto.getUsername())) {
            payload.setMsg("Cannot create or update account as missing account username");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        try {
            User accountUser = this.userService.getUserByUsername(dto.getUsername());
            AccountBalance accountBalance = AccountBalanceConverter.convertTo(dto, accountUser);
            AccountBalance data = this.accBlService.createOrUpdate(accountBalance);
            payload.setData(data);
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setMsg("ok");
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot create or update account as missing account user");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
    }
    
    @GET
    @Path("/v1/mybalance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<AccountBalance> myBalance(@HeaderParam(PARAM_JWT_TOKEN) String token) {
        Payload<AccountBalance> payload = new Payload<>();
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot create or update account as jwt token is not valid.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot create or update account as jwt token is not valid.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
        AccountBalance data = this.accBlService.getByUserId(loggedInUser.getId());
        if (data == null) {
            data = new AccountBalance();
            data.setUser(loggedInUser);
        }
        payload.setData(data);
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setMsg("ok");
        return payload;
    }
}
