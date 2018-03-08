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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.account.NotEnoughBalanceException;
import com.zion.appswitch.AppSwitchProvider;
import com.zion.auth.InvalidJwtToken;
import com.zion.common.DtoListWrapper;
import com.zion.common.LinkPreviewException;
import com.zion.common.Payload;
import com.zion.common.QueryCriteria;
import com.zion.common.UserRole;
import com.zion.converter.PromotionTaskConverter;
import com.zion.mongo.db.repository.CommonConfigRepository;
import com.zion.morphia.entity.CommonConfigEntity;
import com.zion.morphia.entity.PromotionTaskEntity;
import com.zion.permission.PermissionService;
import com.zion.task.PromotionTask;
import com.zion.task.PromotionTaskDto;
import com.zion.task.TaskStatus;
import com.zion.task.TaskType;
import com.zion.task.TaskTypeName;
import com.zion.task.service.PromotionTaskService;
import com.zion.task.service.TaskTypeService;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;
import com.zion.user.service.UserService;

@Path("/task")
@AppSwitchProvider
public class TaskResource extends BaseResource {

    private PromotionTaskService promotionTaskService;
    private TaskTypeService taskTypeService;
    private PermissionService permissionService;
    private CommonConfigRepository configRepo;
    private UserService userService;
    private static final String PARAM_AUTHOR_ID = "authorId";
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskResource.class);

    @Inject
    public TaskResource(PromotionTaskService promotionTaskService, PermissionService permissionService,
            TaskTypeService taskTypeService, UserService userService, CommonConfigRepository configRepo) {
        this.promotionTaskService = promotionTaskService;
        this.taskTypeService = taskTypeService;
        this.permissionService = permissionService;
        this.userService = userService;
        this.configRepo = configRepo;
    }

    @GET
    @Path("/type/v1/createcampaign")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<TaskType> getCreatCampaignType() {
        Payload<TaskType> payload = new Payload<>();
        List<TaskType> types = this.taskTypeService.listAllEnabled();
        TaskType createCampaignType = this.taskTypeService.getByName(TaskTypeName.CREATE_CAMPAIGN.name());
        CommonConfigEntity lifeSpanConfig = this.configRepo.getEnabledConfigByKey("promotion.task.lifespan");
        if (lifeSpanConfig != null) {
            createCampaignType.setLifespan(Integer.valueOf(lifeSpanConfig.getValue()));
        }else {
            payload.setMsg("Cannot find the campaign life span please contact with support admin");
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return payload;
        }
        for (TaskType type : types) {
            if (!type.getName().equals(TaskTypeName.CREATE_CAMPAIGN.name())) {
                createCampaignType.getSubTypes().add(type);
            }
        }
        payload.setData(createCampaignType);
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }

    @GET
    @Path("/promotion/v1/view")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<PromotionTask> getById(@HeaderParam(PARAM_JWT_TOKEN) String token, @QueryParam(PARAM_ID) String id) {
        Payload<PromotionTask> payload = new Payload<>();
        if (StringUtils.isBlank(id)) {
            payload.setMsg("Cannot view the task as missing id");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        String loggedInUserId = null;
        if (StringUtils.isNotBlank(token)) {
            try {
                User user = this.permissionService.getUser(token);
                loggedInUserId = user.getId();
            } catch (UserNotFoundException e) {
                LOGGER.warn(String.format("User cannot be found when list tasks with jwt token: '%s'", token));
            } catch (InvalidJwtToken e) {
                LOGGER.warn(String.format("invalid jwt token: '%s' when list tasks.", token));
            }
        }
        PromotionTask promotionTask = this.promotionTaskService.getById(id, loggedInUserId);

        if (promotionTask == null) {
            payload.setMsg(String.format("Cannot view the task '%s' as task cannot be found.", id));
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } else {
            payload.setData(promotionTask);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        }
    }

    @GET
    @Path("/promotion/v1/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<PromotionTask>> listTasks(@HeaderParam(PARAM_JWT_TOKEN) String token,
            @QueryParam(PARAM_RESULT_SIZE) @DefaultValue("20") int resultSize,
            @QueryParam(PARAM_AUTHOR_ID) String authorId, @QueryParam(PARAM_STATUS) TaskStatus status,
            @QueryParam(PARAM_NEXT_PAGE_TOKEN) String nextPageToken, @QueryParam(PARAM_SHOWDELETED) @DefaultValue("false") Boolean showDeleted) {
        Payload<List<PromotionTask>> payload = new Payload<>();
        QueryCriteria<PromotionTaskEntity> criteria = new QueryCriteria<>();
        criteria.setClazz(PromotionTaskEntity.class);
        criteria.setResultSize(resultSize);
        criteria.setIncludeDisable(showDeleted);
        if (status != null) {
            criteria.setTaskStatus(status);
        }
        if (StringUtils.isNotBlank(authorId)) {
            criteria.setAuthorId(authorId);
        }
        if (StringUtils.isNotBlank(nextPageToken)) {
            criteria.setNextPageToken(nextPageToken);
        }
        if (StringUtils.isNotBlank(token)) {
            try {
                User user = this.permissionService.getUser(token);
                criteria.setLoggedInUserId(user.getId());
            } catch (UserNotFoundException e) {
                LOGGER.warn(String.format("User cannot be found when list tasks with jwt token: '%s'", token));
            } catch (InvalidJwtToken e) {
                LOGGER.warn(String.format("invalid jwt token: '%s' when list tasks.", token));
            }
        }
        criteria.setSortBy("creationDate");
        DtoListWrapper<PromotionTask> taskWrapper = this.promotionTaskService.list(criteria);
        payload.setData(taskWrapper.getDtos());
        payload.setNextPageToken(taskWrapper.getNextPageToken());
        payload.setMsg("ok");
        payload.setStatus(Response.Status.OK.getStatusCode());
        return payload;
    }

    @GET
    @Path("/promotion/v1/active/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<List<PromotionTask>> listMineTasks(@HeaderParam(PARAM_JWT_TOKEN) String token) {
        Payload<List<PromotionTask>> payload = new Payload<>();
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot list mine task as jwt is missing.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        try {
            User user = this.permissionService.getUser(token);
            List<PromotionTask> data = this.promotionTaskService.getActiveTasks(user.getId(), user.getId());
            payload.setData(data);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot list mine task as login user cannot be found");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot list mine task as jwt is not valid");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
    }

    /**
     * 402 require payment as the account balance is not enough to create a task.
     * 404 post url link not found
     * @param token
     * @param dto
     * @return
     */
    @POST
    @Path("/promotion/v1/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<PromotionTask> create(@HeaderParam(PARAM_JWT_TOKEN) String token, PromotionTaskDto dto) {
        return this.createOrUpdate(token, dto, false);
    }

    @POST
    @Path("/promotion/v1/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<PromotionTask> update(@HeaderParam(PARAM_JWT_TOKEN) String token, PromotionTaskDto dto) {
        return this.createOrUpdate(token, dto, true);
    }

    @POST
    @Path("/promotion/admin/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<PromotionTask> adminCreateOrUpdate(@HeaderParam(PARAM_JWT_TOKEN) String token, PromotionTaskDto dto) {
        Payload<PromotionTask> payload = new Payload<>();
        if (dto == null) {
            payload.setMsg("Cannot update task as post body is null.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(dto.getPostUrl())) {
            payload.setMsg("Cannot update task as missing post url");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(dto.getTaskTypeId())) {
            payload.setMsg("Cannot update task as missing task type id");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(dto.getTitle())) {
            payload.setMsg("Cannot update task as missing task title");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot create or update task as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(dto.getAuthorname())) {
            payload.setMsg("Cannot update task as missing author id");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot create or update task as jwto is not valid.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot create or update task as jwt token is not valid.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        if (!this.permissionService.containAnyRole(loggedInUser, UserRole.ADMIN.name())) {
            payload.setMsg("Cannot create or update task as user does not have admin permission.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }

        User campaignAuthor;
        try {
            campaignAuthor = this.userService.getUserByUsername(dto.getAuthorname());
        } catch (UserNotFoundException e1) {
            payload.setMsg(String.format("Cannot create or update task as user id '%s' is not valid.", dto.getAuthorname()));
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        PromotionTask task = PromotionTaskConverter.convertTo(dto);
        task.setCreator(campaignAuthor);

        PromotionTask data;
        try {
            data = this.promotionTaskService.createOrUpdate(task);
            payload.setMsg("ok");
            payload.setData(data);
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        } catch (NotEnoughBalanceException e) {
            payload.setMsg("Not enough balance to create task.");
            payload.setStatus(Response.Status.PAYMENT_REQUIRED.getStatusCode());
            return payload;
        } catch (LinkPreviewException e) {
            payload.setMsg("Cannot create the task as the post url link cannot be found.");
            payload.setStatus(Response.Status.FOUND.getStatusCode());
            return payload;
        }
    }

    @POST
    @Path("/promotion/admin/del")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<PromotionTask> deleteFeed(@HeaderParam(PARAM_JWT_TOKEN) String token, String taskId) {
        Payload<PromotionTask> payload = new Payload<>();
        try {
            User user = this.permissionService.getUser(token);
            if (!this.permissionService.containAnyRole(user, UserRole.ADMIN.name())) {
                payload.setMsg("Cannot delete feed as logged in user does not have permission");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot delete feed as jwt is invalid");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot delete feed as logged in user cannot be found");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(taskId)) {
            payload.setMsg("cannot update feed as id is blank");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        } else {
            PromotionTask deleted = this.promotionTaskService.disable(taskId);
            payload.setMsg("ok");
            payload.setStatus(Response.Status.OK.getStatusCode());
            payload.setData(deleted);
            return payload;
        }
    }

    private Payload<PromotionTask> createOrUpdate(String token, PromotionTaskDto dto, boolean update) {
        Payload<PromotionTask> payload = new Payload<>();
        if (update) {
            if (StringUtils.isBlank(dto.getId())) {
                payload.setMsg("Cannot update task as it missing id.");
                payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                return payload;
            }
        }
        if (dto == null) {
            payload.setMsg("Cannot update task as post body is null.");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(dto.getPostUrl())) {
            payload.setMsg("Cannot update task as missing post url");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(dto.getTaskTypeId())) {
            payload.setMsg("Cannot update task as missing task type id");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(dto.getTitle())) {
            payload.setMsg("Cannot update task as missing task title");
            payload.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            return payload;
        }
        if (StringUtils.isBlank(token)) {
            payload.setMsg("Cannot create or update task as missing jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }
        User loggedInUser;
        try {
            loggedInUser = this.permissionService.getUser(token);
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot create or update task as jwt token is not valid.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot create or update task as jwt token is not valid.");
            payload.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            return payload;
        }

        if (!this.permissionService.containAnyRole(loggedInUser,
                UserRole.DESIGNER.name(),
                UserRole.ADMIN.name(),
                UserRole.INFLUENCER.name(),
                UserRole.STYLIST.name())) {
            payload.setMsg("Cannot create or update task as user does not have permission.");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        }

        PromotionTask task = PromotionTaskConverter.convertTo(dto);
        task.setCreator(loggedInUser);

        PromotionTask data;
        try {
            data = this.promotionTaskService.createOrUpdate(task);
            payload.setMsg("ok");
            payload.setData(data);
            payload.setStatus(Response.Status.OK.getStatusCode());
            return payload;
        } catch (NotEnoughBalanceException e) {
            payload.setMsg("Not enough balance to create task.");
            payload.setStatus(Response.Status.PAYMENT_REQUIRED.getStatusCode());
            return payload;
        } catch (LinkPreviewException e) {
            payload.setMsg("Cannot create the campaign as the link cannot be found");
            payload.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            return payload;
        }
    }

}
