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
package com.zion.web.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zion.comment.service.CommentService;
import com.zion.comment.service.CommentServiceImpl;
import com.zion.common.service.LinkPreviewService;
import com.zion.common.service.LinkPreviewServiceImpl;
import com.zion.httpclient.WebHttpClientServiceImpl;
import com.zion.httpclient.WebHttpClientService;
import com.zion.mail.service.SesService;
import com.zion.mail.service.SesServiceImpl;
import com.zion.mongo.db.repository.CommentRepository;
import com.zion.mongo.db.repository.CommentRepositoryImpl;
import com.zion.mongo.db.repository.CommonConfigRepository;
import com.zion.mongo.db.repository.CommonConfigRepositoryImpl;
import com.zion.mongo.db.repository.RateRepository;
import com.zion.mongo.db.repository.RateRepositoryImpl;
import com.zion.mongo.db.repository.TagRepository;
import com.zion.mongo.db.repository.TagRepositoryImpl;
import com.zion.rate.service.RateService;
import com.zion.rate.service.RateServiceImpl;
import com.zion.tag.TagService;
import com.zion.tag.TagServiceImpl;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class CommonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SesService.class).to(SesServiceImpl.class);
        bind(CommentRepository.class).to(CommentRepositoryImpl.class);
        bind(CommentService.class).to(CommentServiceImpl.class);
        bind(RateRepository.class).to(RateRepositoryImpl.class);
        bind(RateService.class).to(RateServiceImpl.class);
        bind(TagService.class).to(TagServiceImpl.class);
        bind(TagRepository.class).to(TagRepositoryImpl.class);
        
        // http client
        bind(WebHttpClientService.class).to(WebHttpClientServiceImpl.class);
        bind(CommonConfigRepository.class).to(CommonConfigRepositoryImpl.class);
        bind(LinkPreviewService.class).to(LinkPreviewServiceImpl.class);
    }
    
    @Provides
    @Singleton
    Configuration provideFreemarkerConfiguration() {
        Configuration cfg = new Configuration(new Version(2, 3, 26));
        cfg.setClassForTemplateLoading(this.getClass(), "/email");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }
}

