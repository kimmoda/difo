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

import com.zion.appswitch.AppSwitchProvider;
import com.zion.auth.InvalidJwtToken;
import com.zion.common.DtoListWrapper;
import com.zion.common.LikeStatus;
import com.zion.common.Payload;
import com.zion.common.QueryCriteria;
import com.zion.feed.Feed;
import com.zion.feed.FeedLike;
import com.zion.feed.FeedService;
import com.zion.morphia.entity.UserFeedEntity;
import com.zion.permission.PermissionService;
import com.zion.user.User;
import com.zion.user.UserFeed;
import com.zion.user.service.UserFeedService;
import com.zion.user.service.UserNotFoundException;

@Path("/userfeed")
@AppSwitchProvider
public class UserFeedResource extends BaseResource {

    private PermissionService permissionService;
    private UserFeedService userFeedService;
    private FeedService feedService;

    @Inject
    public UserFeedResource(PermissionService permissionService, UserFeedService userFeedService, FeedService feedService) {
        this.permissionService = permissionService;
        this.userFeedService = userFeedService;
        this.feedService = feedService;
    }

    @POST
    @Path("/v1/like")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<UserFeed> updateLikeStatus(@HeaderParam(PARAM_JWT_TOKEN) String jwt, FeedLike feedLike) {
        Payload<UserFeed> payload = new Payload<>();
        try {
            LikeStatus likeStatus = feedLike.getStatus();
            String feedId = feedLike.getFeedId();
            if (likeStatus == null) {
                payload.setMsg("Missing likestatus. Likestatus can only be LIKE or DISLIKE");
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }

            if (StringUtils.isBlank(feedId)) {
                payload.setMsg("Missing feed id");
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }

            Feed feed = this.feedService.getById(feedId);

            if (feed == null) {
                payload.setMsg(String.format("Feed '%s' does not exist, please contact admin user.", feedId));
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }

            User user = permissionService.getUser(jwt);
            UserFeed userFeed = new UserFeed(user.getId(), feedId, feed.getAuthor().getUserId(), likeStatus.name());
            payload.setData(this.userFeedService.updateLikeStatus(userFeed));
            payload.setMsg("Ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot update like status as the user cannot be found with jwt token : " + jwt);
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot update like status as invalid jwt");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
    }

    @GET
    @Path("/v1/like/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<Feed>> listLikedFeeds(@HeaderParam(PARAM_JWT_TOKEN) String jwt,
            @QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
            @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken) {
        Payload<List<Feed>> payload = new Payload<>();
        if (StringUtils.isBlank(jwt)) {
            payload.setMsg("Missing jwt token when list liked feeds");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        try {
            User user = permissionService.getUser(jwt);
            QueryCriteria<UserFeedEntity> criteria = new QueryCriteria<>();
            criteria.setClazz(UserFeedEntity.class);
            criteria.setResultSize(resultSize);
            criteria.setIncludeDisable(false);
            if (StringUtils.isNotBlank(nextPageToken)) {
                criteria.setNextPageToken(nextPageToken);
            }
            criteria.setAscOrder(true);
            criteria.setSortBy("creationDate");
            criteria.setUserId(user.getId());
            criteria.setLikeStatus(LikeStatus.LIKE.name());
            DtoListWrapper<Feed> feedsWrapper = this.userFeedService.getLikedFeeds(criteria);
            List<Feed> dtos = feedsWrapper.getDtos();
            if (dtos != null && !dtos.isEmpty()) {
                payload.setData(dtos);
            }
            payload.setNextPageToken(feedsWrapper.getNextPageToken());
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setMsg("ok");
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot find logged in user when list liked feeds");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Invalid jwt token when list liked feeds");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
    }
}
