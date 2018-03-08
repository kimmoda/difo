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
package com.zion.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.common.DtoListWrapper;
import com.zion.common.LikeStatus;
import com.zion.common.QueryCriteria;
import com.zion.converter.FeedConverter;
import com.zion.converter.UserFeedConverter;
import com.zion.feed.Feed;
import com.zion.mongo.db.repository.EntityListWrapper;
import com.zion.mongo.db.repository.FeedRepository;
import com.zion.mongo.db.repository.UserFeedRepository;
import com.zion.mongo.db.repository.UserRepository;
import com.zion.morphia.entity.FeedEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.morphia.entity.UserFeedEntity;
import com.zion.user.UserFeed;

public class UserFeedServiceImpl implements UserFeedService{
    private UserFeedRepository userFeedRepo;
    private FeedRepository feedRepo;
    private UserRepository userRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFeedServiceImpl.class);
    
    @Inject
    public UserFeedServiceImpl(UserFeedRepository userFeedRepo, FeedRepository feedRepo, UserRepository userRepo) {
        this.userFeedRepo = userFeedRepo;
        this.feedRepo = feedRepo;
        this.userRepo = userRepo;
    }

    @Override
    public UserFeed getUserFeed(String userId, String feedId) {
        UserFeedEntity entity = this.userFeedRepo.getUserFeed(userId, feedId);
        return entity != null ? UserFeedConverter.convertTo(entity) : null;
    }
    @Override
    public List<UserFeed> getUserFeeds(String userId) {
        List<UserFeedEntity> entities = this.userFeedRepo.getUserFeeds(userId);
        return entities != null && !entities.isEmpty() ? entities.stream().map(e -> UserFeedConverter.convertTo(e)).collect(Collectors.toList())
                : new ArrayList<>();
    }
    
    @Override
    public DtoListWrapper<Feed> getLikedFeeds(QueryCriteria<UserFeedEntity> queryCriteria) {
        EntityListWrapper<UserFeedEntity> userFeedWrapper = this.userFeedRepo.getLikedFeeds(queryCriteria);
        List<UserFeedEntity> entities = userFeedWrapper.getEntities();
        DtoListWrapper<Feed> dtoList = new DtoListWrapper<>();
        if (entities != null && !entities.isEmpty()) {
            List<String> feedIds = entities.stream().map(e -> e.getFeedId()).collect(Collectors.toList());
            List<FeedEntity> feedEntities = this.feedRepo.listFeeds(feedIds);
            List<Feed> feeds = feedEntities.stream().map(e -> {
                Feed feed = FeedConverter.convertTo(e); 
                feed.setLike(true);
                return  feed;}
            ).filter(new Predicate<Feed>() {
                @Override
                public boolean test(Feed t) {
                    return t.isEnabled();
                }
            }).collect(Collectors.toList());
            dtoList.setDtos(feeds);
            dtoList.setNextPageToken(userFeedWrapper.getNextPageToken());
        }
        return dtoList;
    }

    @Override
    public UserFeed saveOrUpdate(UserFeed src) {
        if(src == null) {
            throw new IllegalArgumentException("Cannot save or update user feed as user feed is null");
        }
        UserFeedEntity entity = this.userFeedRepo.saveOrUpdate(UserFeedConverter.convertTo(src));
        return entity != null ? UserFeedConverter.convertTo(entity) : null;
    }

    /** column header is the saved like status in userFeedEntity. Row header is that client user dislike or like action.
     *                  | dislike                                               | like                                                  |   empty String                            |
     *      |dislike    | dislikeCount -1, likeStatus = ""                      | likeCount -1, dislikeCount +1, likeStatus = "DISLIKE" | dislikeCount + 1, likeStatus = "DISLIKE"  |
     *      |like       | dislikeCount -1, likeCount +1, likeStatus = "LIKE"    | likeCount -1, likeStatus = ""                         | likeCount + 1,  likeStatus = "LIKE"       |                   |
     */
    @Override
    public UserFeed updateLikeStatus(UserFeed src) {
        String feedId = src.getFeedId();
        String userId = src.getUserId();
        UserFeed saved = this.getUserFeed(userId, feedId);
        String action = src.getLikeStatus();
        if (saved != null) {
            String savedStatus = saved.getLikeStatus();
            if(LikeStatus.DISLIKE.name().equals(action) && LikeStatus.DISLIKE.name().equals(savedStatus)) {
                this.updateDisLikeCount(feedId, false);
                saved.setLikeStatus("");
                return this.saveOrUpdate(saved);
            }else if(LikeStatus.DISLIKE.name().equals(action) && LikeStatus.LIKE.name().equals(savedStatus)) {
                this.updateLikeCount(feedId, false);
                this.updateDisLikeCount(feedId, true);
                saved.setLikeStatus(action);
                return this.saveOrUpdate(saved);
            }else if(LikeStatus.LIKE.name().equals(action) && LikeStatus.DISLIKE.name().equals(savedStatus)){
                this.updateDisLikeCount(feedId, false);
                this.updateLikeCount(feedId, true);
                saved.setLikeStatus(action);
                return this.saveOrUpdate(saved);
            }else if(LikeStatus.LIKE.name().equals(action) && LikeStatus.LIKE.name().equals(savedStatus)) {
                this.updateLikeCount(feedId, false);
                saved.setLikeStatus("");
                return this.saveOrUpdate(saved);
            }else{
                saved.setLikeStatus(action);
                return saveUserFeed(saved);
            }
        }else {
            return saveUserFeed(src);
        }
    }
    
    private UserFeed saveUserFeed(UserFeed src) {
        String feedId = src.getFeedId();
        if(LikeStatus.LIKE.name().equals(src.getLikeStatus())) {
            this.updateLikeCount(feedId, true);
        }else if(LikeStatus.DISLIKE.name().equals(src.getLikeStatus())) {
            this.updateDisLikeCount(feedId, true);
        }else {
            throw new IllegalArgumentException(String.format("Cannot update like status as status '%s' does not match LIKE or DISLIKE", src.getLikeStatus()));
        }
        return this.saveOrUpdate(src);
    }
    
    /**
     * increase or decrease like count in feed
     * @param feedId
     * @param increase
     */
    private void updateLikeCount(String feedId, boolean increase) {
        this.updateCount(feedId, increase, true);
    }
    
    /**
     * increase or decrease dislike count in feed
     * @param feedId
     * @param increase
     */
    private void updateDisLikeCount(String feedId, boolean increase) {
        this.updateCount(feedId, increase, false);
    }
    
    private void updateCount(String feedId, boolean increase, boolean isLikeCount) {
        FeedEntity feed = this.feedRepo.getById(feedId);
        if(feed != null) {
            UserEntity feedAuthor = feed.getAuthor();
            long count = isLikeCount? feed.getLikeCount(): feed.getDislikeCount();
            long totalLikeCount = feedAuthor.getFeedLikeCount();
            if(increase) {
                count = count + 1;
                totalLikeCount = totalLikeCount + 1;
            }else {
                count = count >= 1 ? count -1 : count;
                totalLikeCount = totalLikeCount >= 1 ? totalLikeCount -1 : totalLikeCount;
            }
            if(isLikeCount) {
                feed.setLikeCount(count);
            }else {
                feed.setDislikeCount(count);
            }
            feedAuthor.setFeedLikeCount(totalLikeCount);
            try {
                this.userRepo.updateUser(feedAuthor);
                this.feedRepo.update(feed);
            }catch(Exception e) {
                //It may fail as concurrent users updating feed or user.
                LOGGER.warn(e.getMessage());
            }
        }else {
            throw new IllegalArgumentException(String.format("cannot update feed '%s' like or dislike count as feed cannot be found.", feedId));
        }
    }

}

