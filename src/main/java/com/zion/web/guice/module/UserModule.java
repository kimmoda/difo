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
import com.zion.follower.service.FollowerService;
import com.zion.follower.service.FollowerServiceImpl;
import com.zion.mail.service.ApproveStylistEmailContentFactory;
import com.zion.mail.service.BecomeStylistEmailContentFactory;
import com.zion.mail.service.FirstLookEmailContentFactory;
import com.zion.mongo.db.repository.FollowerRepository;
import com.zion.mongo.db.repository.FollowerRepositoryImpl;
import com.zion.mongo.db.repository.UserFeedRepository;
import com.zion.mongo.db.repository.UserFeedRepositoryImpl;
import com.zion.mongo.db.repository.UserRepository;
import com.zion.mongo.db.repository.UserRepositoryImpl;
import com.zion.registration.service.StylistRegistrationService;
import com.zion.registration.service.StylistRegistrationServiceImpl;
import com.zion.user.service.UserFeedService;
import com.zion.user.service.UserFeedServiceImpl;
import com.zion.user.service.UserService;
import com.zion.user.service.UserServiceImpl;

public class UserModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(UserFeedRepository.class).to(UserFeedRepositoryImpl.class);
        bind(UserFeedService.class).to(UserFeedServiceImpl.class);
        bind(StylistRegistrationService.class).to(StylistRegistrationServiceImpl.class);
        bind(BecomeStylistEmailContentFactory.class);
        bind(ApproveStylistEmailContentFactory.class);
        bind(FirstLookEmailContentFactory.class);
        bind(FollowerRepository.class).to(FollowerRepositoryImpl.class);
        bind(FollowerService.class).to(FollowerServiceImpl.class);
    }
}
