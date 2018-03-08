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
package com.zion.mongo.db.repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zion.httpclient.WebHttpClientService;
import com.zion.httpclient.exception.HttpClientConnectionException;
import com.zion.morphia.entity.TaskTypeEntity;

public class TaskTypeRepositoryImpl extends AbstractRepository implements TaskTypeRepository {

    private WebHttpClientService httpClient;

    @Inject
    public TaskTypeRepositoryImpl(WebHttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<TaskTypeEntity> createOrUpdate()
            throws HttpClientConnectionException, JsonParseException, JsonMappingException, IOException {
        String json = httpClient.get("https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/config/PromotionTaskConfig.json", null, null)
                .getContent();
        ObjectMapper mapper = new ObjectMapper();
        List<TaskTypeEntity> types = mapper.readValue(json, new TypeReference<List<TaskTypeEntity>>() {
        });
        Map<String, TaskTypeEntity> taskMap = new HashMap<>();

        List<TaskTypeEntity> existingTaskTypes = this.listAll(TaskTypeEntity.class);
        for (TaskTypeEntity entity : existingTaskTypes) {
            taskMap.put(entity.getName().toLowerCase(), entity);
        }

        for (TaskTypeEntity type : types) {
            TaskTypeEntity existingType = taskMap.get(type.getName().toLowerCase());
            if (existingType != null) {
                existingType.setCost(type.getCost());
                existingType.setReward(type.getReward());
                existingType.setDescription(type.getDescription());
                existingType.setEnabled(type.isEnabled());
                this.update(existingType);
            } else {
                this.persist(type);
            }
        }
        return this.listAll(TaskTypeEntity.class);
    }

    @Override
    public TaskTypeEntity getById(String id) {
        return super.get(TaskTypeEntity.class, new ObjectId(id));
    }
    
    @Override
    public List<TaskTypeEntity> listAllEnabled() {
        return super.listAllEnabled(TaskTypeEntity.class);
    }

    @Override
    public TaskTypeEntity getByName(String name) {
        return this.getMongoDatastore().find(TaskTypeEntity.class).field("name").equal(name).get();
    }

}
