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
import com.zion.comment.OverLimitedLengthException;
import com.zion.common.DtoListWrapper;
import com.zion.common.Payload;
import com.zion.common.QueryCriteria;
import com.zion.common.RateDestinationType;
import com.zion.morphia.entity.RateEntity;
import com.zion.permission.PermissionService;
import com.zion.rate.Rate;
import com.zion.rate.RateAlreadyExistException;
import com.zion.rate.RateDto;
import com.zion.rate.service.RateService;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;

@Path("/rate")
public class RateResource extends BaseResource {
    public static final String PARAM_RATE_DESTINATION_ID = "destId";
    public static final String PARAM_RATE_DESTINATION = "destination";

    private RateService rateService;
    private PermissionService permissionService;

    @Inject
    public RateResource(RateService rateService, PermissionService permissionService) {
        this.rateService = rateService;
        this.permissionService = permissionService;
    }
    
    @POST
    @Path("/v1/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<RateDto> createOrUpdate(@HeaderParam(PARAM_JWT_TOKEN) String token, Rate rate) {
        Payload<RateDto> payload = new Payload<>();
        RateDto dto = new RateDto();
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
            dto.setUserId(loggedInUser.getId());
        } catch (UserNotFoundException e) {
            payload.setMsg("cannot rate as jwt token is invalid");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("cannot rate as jwt token is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(rate.getDestId())) {
            payload.setMsg("cannot rate as rate destination id is blank ");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } else {
            dto.setDestId(rate.getDestId());
        }
        if (rate.getComment() != null) {
            dto.setComment(rate.getComment().trim());
        }
        if (StringUtils.isBlank(rate.getRateDestination())) {
            payload.setMsg("cannot save rate as rate destination is blank ");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        dto.setId(rate.getId());
        if(rate.getRate() < 1 || rate.getRate() > 5) {
            payload.setMsg(String.format("cannot rate with score '%s'", rate.getRate()));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }else {
            dto.setCurrentRate(rate.getRate());
        }
        try {
            RateDestinationType rateDestType = RateDestinationType.valueOf(rate.getRateDestination());
            if(rateDestType.equals(RateDestinationType.USER) && !this.permissionService.canRateUser(loggedInUser.getId(), rate)) {
                payload.setMsg(String.format("cannot rate as user '%s' cannot be rated", rate.getDestId()));
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
            dto.setRateDestination(rate.getRateDestination());
        } catch (Exception e) {
            payload.setMsg(
                    String.format("cannot save rate as rate destination '%s' is not support ", rate.getRateDestination()));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        try {
            RateDto saved = this.rateService.saveOrUpdate(dto);
            payload.setData(saved);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        } catch (OverLimitedLengthException e) {
            payload.setMsg("cannot save rate as comment length is over limited ");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } catch (RateAlreadyExistException e) {
            payload.setMsg("cannot save rate as user already rate");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
    }
    
    @GET
    @Path("/v1/pub/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<RateDto>> list(@QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
            @QueryParam(PARAM_RATE_DESTINATION_ID) String destId,
            @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken) {
        Payload<List<RateDto>> payload = new Payload<>();
        QueryCriteria<RateEntity> queryCriteria = new QueryCriteria<>();
        if (StringUtils.isBlank(destId)) {
            payload.setMsg("Cannot list rate as rate destination id is missing.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        queryCriteria.setDestId(destId);
        queryCriteria.setResultSize(resultSize);
        queryCriteria.setSortBy("creationDate");
        queryCriteria.setClazz(RateEntity.class);
        if (StringUtils.isNotBlank(nextPageToken)) {
            queryCriteria.setNextPageToken(nextPageToken);
        }
        DtoListWrapper<RateDto> wrapper = this.rateService.listRates(queryCriteria);
        payload.setData(wrapper.getDtos());
        payload.setNextPageToken(wrapper.getNextPageToken());
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }
}

