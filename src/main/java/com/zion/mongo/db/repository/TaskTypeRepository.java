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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zion.httpclient.exception.HttpClientConnectionException;
import com.zion.morphia.entity.TaskTypeEntity;

public interface TaskTypeRepository {

    List<TaskTypeEntity> createOrUpdate() throws HttpClientConnectionException, JsonParseException, JsonMappingException, IOException;

    TaskTypeEntity getById(String id);
    
    TaskTypeEntity getByName(String name);

    List<TaskTypeEntity> listAllEnabled();
}
