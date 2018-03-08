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
package com.zion.feed;

import com.zion.common.DtoListWrapper;
import com.zion.common.FeedTagDto;
import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.FeedEntity;

public interface FeedService {

	Feed saveOrUpdate(Feed feed);
	
	Feed getById(QueryCriteria<FeedEntity> criteria);
	
	Feed getById(String id);
	
	Feed disable(String id);
	
	Feed enable(String id);
	
	Feed updateStatus(String id, String status);
	
	boolean isMyFeed(String loggedInUserId, String feedId) throws FeedNotFoundException;
	
	DtoListWrapper<Feed> list(QueryCriteria<FeedEntity> criteria);
	
	Feed increaseTrackingCount(String id);
	
	Feed increaseViewCount(String id);
	
	FeedCount getMyFeedCount(String authorId);
	
	SharedFeed shareFeed(SharedFeed sharedFeed);
	
	Feed updateSysTags(FeedTagDto dto);
}

