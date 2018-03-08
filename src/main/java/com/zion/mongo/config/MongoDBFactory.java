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
package com.zion.mongo.config;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.zion.common.AppConfig;
import com.zion.common.EnvType;
import com.zion.morphia.entity.BaseEntity;

public class MongoDBFactory {
    
    private static final MongoDBFactory INSTANCE = new MongoDBFactory();

    private Datastore datastore;

    public static MongoDBFactory instance() {
        return INSTANCE;
    }

    public Datastore getDatastore() {
        try {
            if (datastore != null) {
                return datastore;
            }
            MongoClient mongoClient;
            if (EnvType.PROD_TEST == AppConfig.getInstance().getAppEnv()) {
                MongoClientURI uri = new MongoClientURI("");
                mongoClient = new MongoClient(uri);
            } else if (EnvType.PROD == AppConfig.getInstance().getAppEnv()) {
                MongoClientURI uri = new MongoClientURI("");
                mongoClient = new MongoClient(uri);
            } else {
                mongoClient = new MongoClient();
            }
            datastore = new Morphia().mapPackage(BaseEntity.class.getPackage().getName()).createDatastore(mongoClient, "");
            datastore.ensureIndexes();
            return datastore;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing MongoDB", e);
        }
    }
}
