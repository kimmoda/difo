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
import com.zion.mongo.db.repository.SharedFeedRepository;
import com.zion.mongo.db.repository.SharedFeedRepositoryImpl;
import com.zion.socialmedia.FacebookUserService;
import com.zion.socialmedia.FacebookUserServiceImpl;
import com.zion.socialmedia.GoogleUserService;
import com.zion.socialmedia.GoogleUserServiceImpl;
import com.zion.socialmedia.shareurl.ShareUrlGeneraterSerivceImpl;
import com.zion.socialmedia.shareurl.ShareUrlGeneraterService;

public class SocialMediaModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FacebookUserService.class).to(FacebookUserServiceImpl.class);
        bind(GoogleUserService.class).to(GoogleUserServiceImpl.class);
        bind(SharedFeedRepository.class).to(SharedFeedRepositoryImpl.class);
        bind(ShareUrlGeneraterService.class).to(ShareUrlGeneraterSerivceImpl.class);
    }
}

