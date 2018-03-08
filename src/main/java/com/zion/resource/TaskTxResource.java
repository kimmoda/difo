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
import com.zion.common.Payload;
import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.PromotionTaskTxEntity;
import com.zion.permission.PermissionService;
import com.zion.task.PromoteOwnTaskException;
import com.zion.task.PromotionTask;
import com.zion.task.PromotionTaskTx;
import com.zion.task.PromotionTaskTxDto;
import com.zion.task.TaskAlreadyCompleteException;
import com.zion.task.TaskExpiredException;
import com.zion.task.service.PromotionTaskTxService;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;

@Path("/tx")
@AppSwitchProvider
public class TaskTxResource extends BaseResource {

    private PromotionTaskTxService promotionTaskTxService;
    private PermissionService permissionService;
    private final static String PARAM_TASK_ID = "taskId";
    private final static String PARAM_PARTICIPANT_ID = "participantid";

    @Inject
    public TaskTxResource(PromotionTaskTxService promoteTaskTxService, PermissionService permissionService) {
        this.promotionTaskTxService = promoteTaskTxService;
        this.permissionService = permissionService;
    }

    /**
     * 403 User is not valid or jwt is not valid
     * 410 task expired
     * @param token
     * @param dto
     * @return
     */
    @POST
    @Path("/promotiontask/v1/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<PromotionTaskTx> create(@HeaderParam(PARAM_JWT_TOKEN) String token, PromotionTaskTxDto dto) {
        Payload<PromotionTaskTx> payload = new Payload<>();

        if (StringUtils.isBlank(dto.getTaskId())) {
            payload.setMsg("missing task id");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("cannot find user from jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("invalid jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        PromotionTaskTx taskTx = new PromotionTaskTx();

        PromotionTask task = new PromotionTask();
        task.setId(dto.getTaskId());

        taskTx.setTask(task);
        taskTx.setTxTo(loggedInUser);

        PromotionTaskTx data;
        try {
            data = this.promotionTaskTxService.create(taskTx);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(data);
            return payload;
        } catch (TaskExpiredException e) {
            payload.setMsg("Campaign expired");
            payload.setStatus(Response.Status.GONE.getStatusCode());
            return payload;
        } catch (PromoteOwnTaskException e) {
            payload.setMsg("Cannot promote your own campaign.");
            payload.setStatus(Response.Status.CONFLICT.getStatusCode());
            return payload;
        } catch (TaskAlreadyCompleteException e) {
            payload.setMsg("User already done the campaign");
            payload.setStatus(Response.Status.NOT_MODIFIED.getStatusCode());
            return payload;
        }

    }

    @GET
    @Path("/task/v1/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<PromotionTaskTx>> listTxByPromotionTaskId(@QueryParam(PARAM_TASK_ID) String taskId,
            @QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
            @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken) {
        Payload<List<PromotionTaskTx>> payload = new Payload<>();
        QueryCriteria<PromotionTaskTxEntity> criteria = new QueryCriteria<>();
        if (StringUtils.isBlank(taskId)) {
            payload.setMsg("missing task id");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } else {
            criteria.setTaskId(taskId.trim());
        }
        criteria.setClazz(PromotionTaskTxEntity.class);
        criteria.setResultSize(resultSize);
        criteria.setIncludeDisable(false);
        if (StringUtils.isNotBlank(nextPageToken)) {
            criteria.setNextPageToken(nextPageToken);
        }
        criteria.setSortBy("creationDate");
        DtoListWrapper<PromotionTaskTx> txWrapper = this.promotionTaskTxService.searchTx(criteria);
        payload.setData(txWrapper.getDtos());
        payload.setNextPageToken(txWrapper.getNextPageToken());
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }

    @GET
    @Path("/participant/v1/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<PromotionTaskTx>> listTxByParticipantId(@QueryParam(PARAM_PARTICIPANT_ID) String participantId,
            @QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
            @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken) {
        Payload<List<PromotionTaskTx>> payload = new Payload<>();
        QueryCriteria<PromotionTaskTxEntity> criteria = new QueryCriteria<>();
        if (StringUtils.isBlank(participantId)) {
            payload.setMsg("missing participant id");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } else {
            criteria.setTxToId(participantId.trim());
        }
        criteria.setClazz(PromotionTaskTxEntity.class);
        criteria.setResultSize(resultSize);
        criteria.setIncludeDisable(false);
        if (StringUtils.isNotBlank(nextPageToken)) {
            criteria.setNextPageToken(nextPageToken);
        }
        criteria.setSortBy("creationDate");
        DtoListWrapper<PromotionTaskTx> txWrapper = this.promotionTaskTxService.searchTx(criteria);
        payload.setData(txWrapper.getDtos());
        payload.setNextPageToken(txWrapper.getNextPageToken());
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }
}
