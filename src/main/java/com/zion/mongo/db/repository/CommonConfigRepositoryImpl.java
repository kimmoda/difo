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
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zion.httpclient.WebHttpClientService;
import com.zion.httpclient.exception.HttpClientConnectionException;
import com.zion.morphia.entity.CommonConfigEntity;

public class CommonConfigRepositoryImpl extends AbstractRepository implements CommonConfigRepository {
    private WebHttpClientService httpClient;

    @Inject
    public CommonConfigRepositoryImpl(WebHttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<CommonConfigEntity> createOrUpdate()
            throws HttpClientConnectionException, JsonParseException, JsonMappingException, IOException {
        String json = httpClient.get("https://s3-ap-southeast-2.amazonaws.com/zion-ui/client/config/CommonConfig.json", null, null)
                .getContent();
        ObjectMapper mapper = new ObjectMapper();
        List<CommonConfigEntity> configEntities = mapper.readValue(json, new TypeReference<List<CommonConfigEntity>>() {
        });

        for (CommonConfigEntity configEntity : configEntities) {
            CommonConfigEntity existingConfigEntity = this.getEnabledConfigByKey(configEntity.getKey().trim().toLowerCase());
            if (existingConfigEntity != null) {
                existingConfigEntity.setValue(configEntity.getValue());
                existingConfigEntity.setEnabled(configEntity.isEnabled());
                this.update(existingConfigEntity);
            } else {
                CommonConfigEntity config = new CommonConfigEntity();
                config.setKey(configEntity.getKey().trim().toLowerCase());
                config.setValue(configEntity.getValue().trim());
                this.persist(config);
            }
        }
        return this.listAll(CommonConfigEntity.class);
    }

    @Override
    public CommonConfigEntity getEnabledConfigByKey(String key) {
        return this.getMongoDatastore().find(CommonConfigEntity.class)
                .field("key").equal(key)
                .field("enabled").equal(true).get();
    }

}
