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

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zion.common.UserRole;
import com.zion.httpclient.exception.HttpClientConnectionException;
import com.zion.mongo.db.repository.CommonConfigRepository;
import com.zion.mongo.db.repository.TaskTypeRepository;
import com.zion.morphia.entity.CommonConfigEntity;
import com.zion.morphia.entity.TaskTypeEntity;
import com.zion.permission.PermissionService;
import com.zion.user.service.UserNotFoundException;

@Path("/config")
public class ConfigurationResource extends BaseResource {

    private TaskTypeRepository taskTypeRepo;
    private CommonConfigRepository commonConfigRepo;
    private PermissionService permissionService;

    @Inject
    public ConfigurationResource(TaskTypeRepository taskTypeRepo, PermissionService permissionService, CommonConfigRepository commonConfigRepo) {
        this.taskTypeRepo = taskTypeRepo;
        this.permissionService = permissionService;
        this.commonConfigRepo = commonConfigRepo;
    }

    @POST
    @Path("/tasktype/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public List<TaskTypeEntity> updateTaskType(@HeaderParam(PARAM_JWT_TOKEN) String token)
            throws HttpClientConnectionException, JsonParseException, JsonMappingException, IOException, IllegalAccessError, UserNotFoundException {
        if (this.permissionService.hasAnyRole(token, UserRole.SYSADMIN.name())) {
            return this.taskTypeRepo.createOrUpdate();
        }else {
            throw new IllegalAccessError();
        }
    }
    
    @POST
    @Path("/common/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public List<CommonConfigEntity> updateCommonConfig(@HeaderParam(PARAM_JWT_TOKEN) String token)
            throws HttpClientConnectionException, JsonParseException, JsonMappingException, IOException, IllegalAccessError, UserNotFoundException {
        if (this.permissionService.hasAnyRole(token, UserRole.SYSADMIN.name())) {
            return this.commonConfigRepo.createOrUpdate();
        }else {
            throw new IllegalAccessError();
        }
    }
}
