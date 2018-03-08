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
package com.zion.mongo.db.repository;

import java.util.List;

import com.zion.common.QueryCriteria;
import com.zion.feed.FeedCount;
import com.zion.morphia.entity.FeedEntity;

public interface FeedRepository {

	FeedEntity saveOrUpdate(FeedEntity entity);
	
	FeedEntity update(FeedEntity entity);
	
	void updateFeedAuthorRoles(String userId);
	
	FeedEntity getById(String id);
	
	FeedEntity disable(String id);
	
	FeedEntity enable(String id);
	
	List<FeedEntity> listFeeds(List<String> ids);

	List<FeedEntity> listAllFeeds();

	EntityListWrapper<FeedEntity> list(QueryCriteria<FeedEntity> criteria);
	
	FeedEntity generateShortUrl(FeedEntity feedEntity);
	
	FeedCount getMyFeedCount(String authorId);

    long getMyFeedCount(String authorId, String status);
}

