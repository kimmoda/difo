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
import com.zion.comment.Comment;
import com.zion.comment.CommentDestinationType;
import com.zion.comment.CommentDto;
import com.zion.comment.OverLimitedLengthException;
import com.zion.comment.service.CommentService;
import com.zion.common.DtoListWrapper;
import com.zion.common.Payload;
import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.CommentEntity;
import com.zion.permission.PermissionService;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;

@Path("/comment")
public class CommentResource extends BaseResource {
    public static final String PARAM_COMMENT_DESTINATION_ID = "destId";
    public static final String PARAM_COMMENT_DESTINATION = "commentDestination";
    public static final int PARAM_MAX_COMMENT = 300;

    private CommentService commentService;
    private PermissionService permissionService;

    @Inject
    public CommentResource(CommentService commentService, PermissionService permissionService) {
        this.commentService = commentService;
        this.permissionService = permissionService;
    }

    @POST
    @Path("/v1/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<CommentDto> createOrUpdate(@HeaderParam(PARAM_JWT_TOKEN) String token, Comment comment) {
        Payload<CommentDto> payload = new Payload<>();
        CommentDto dto = new CommentDto();
        try {
            User user = this.permissionService.getUser(token);
            if(!this.permissionService.canComment(user.getId(), comment)) {
                payload.setMsg(String.format("cannot update comment as the comment does not belong to the user '%s' ", user.getId()));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
            dto.setUserId(user.getId());
        } catch (UserNotFoundException e) {
            payload.setMsg("cannot save comment as jwt token is invalid");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("cannot save comment as jwt token is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        if (StringUtils.isBlank(comment.getDestId())) {
            payload.setMsg("cannot save comment as comment destination id is blank ");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } else {
            dto.setDestId(comment.getDestId());
        }

        if (StringUtils.isBlank(comment.getComment())) {
            payload.setMsg("cannot save comment as comment is blank ");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } 
        if (comment.getComment().trim().length() > PARAM_MAX_COMMENT ) {
            payload.setMsg("cannot save comment as comment is too long. ");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        dto.setComment(comment.getComment().trim());
        if (StringUtils.isBlank(comment.getCommentDestination())) {
            payload.setMsg("cannot save comment as comment destination is blank ");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        dto.setId(comment.getId());
        try {
            CommentDestinationType.valueOf(comment.getCommentDestination());
            dto.setCommentDestination(comment.getCommentDestination());
        } catch (Exception e) {
            payload.setMsg(
                    String.format("cannot save comment as comment destination '%s' is not support ", comment.getCommentDestination()));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }

        try {
            CommentDto saved = this.commentService.saveOrUpdate(dto);
            payload.setData(saved);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        } catch (OverLimitedLengthException e) {
            payload.setMsg("cannot save comment as comment length is over limited ");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
    }

    @GET
    @Path("/v1/pub/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<CommentDto>> list(@QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
            @QueryParam(PARAM_COMMENT_DESTINATION_ID) String destId, @QueryParam(PARAM_COMMENT_DESTINATION) String destType,
            @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken) {
        Payload<List<CommentDto>> payload = new Payload<>();
        QueryCriteria<CommentEntity> queryCriteria = new QueryCriteria<>();
        if (StringUtils.isBlank(destId)) {
            payload.setMsg("Cannot list comment as comments destination id is missing.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(destType)) {
            try {
                CommentDestinationType.valueOf(destType);
            } catch (Exception e) {
                payload.setMsg(String.format("Cannot list comment as comments destination type '%s' is not support", destType));
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }
            payload.setMsg("Cannot list comment as comments destination type is missing.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        queryCriteria.setDestId(destId);
        queryCriteria.setResultSize(resultSize);
        
        queryCriteria.setCommentDestType(destType);
        queryCriteria.setSortBy("creationDate");
        queryCriteria.setClazz(CommentEntity.class);
        if (StringUtils.isNotBlank(nextPageToken)) {
            queryCriteria.setNextPageToken(nextPageToken);
        }
        DtoListWrapper<CommentDto> wrapper = this.commentService.list(queryCriteria);
        payload.setData(wrapper.getDtos());
        payload.setNextPageToken(wrapper.getNextPageToken());
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }
}
