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

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.zion.auth.InvalidJwtToken;
import com.zion.common.DtoListWrapper;
import com.zion.common.FollowerDto;
import com.zion.common.Payload;
import com.zion.common.QueryCriteria;
import com.zion.follower.Follower;
import com.zion.follower.service.FollowerService;
import com.zion.morphia.entity.FollowerEntity;
import com.zion.permission.PermissionService;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;

@Path("/follower")
public class FollowerResource extends BaseResource {

    private FollowerService followerService;
    private PermissionService permissionService;

    @Inject
    public FollowerResource(FollowerService followerService, PermissionService permissionService) {
        this.followerService = followerService;
        this.permissionService = permissionService;
    }

    @POST
    @Path("/v1/follow")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<Follower> followOrUnfollow(@HeaderParam(PARAM_JWT_TOKEN) String token, String followedUserId) {
        Payload<Follower> payload = new Payload<>();
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot create followed user as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(followedUserId)) {
            payload.setMsg("Cannot create followed user as missing follower user id.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot list followed users as logged in user cannot be found");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot list followed users as invalid jwt token.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        Follower follower = this.followerService.createOrDelete(new FollowerDto(loggedInUser.getId(), followedUserId));
        payload.setData(follower);
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }

    @GET
    @Path("/v1/followed/list")
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<User>> listFollowedUsers(@HeaderParam(PARAM_JWT_TOKEN) String token,
            @QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
            @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken) {
        Payload<List<User>> payload = new Payload<>();
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot list followed users as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot list followed users as logged in user cannot be found");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot list followed users as invalid jwt token.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        QueryCriteria<FollowerEntity> criteria = new QueryCriteria<>();
        criteria.setClazz(FollowerEntity.class);
        criteria.setResultSize(resultSize);
        criteria.setIncludeDisable(false);
        if (StringUtils.isNotBlank(nextPageToken)) {
            criteria.setNextPageToken(nextPageToken);
        }
        criteria.setAscOrder(true);
        criteria.setSortBy("creationDate");
        criteria.setFanId(loggedInUser.getId());
        DtoListWrapper<User> users = this.followerService.listFollowedUserByFanId(criteria);
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        payload.setData(users.getDtos());
        payload.setNextPageToken(users.getNextPageToken());
        return payload;
    }
}
