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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.common.DtoListWrapper;
import com.zion.common.FeedTagDto;
import com.zion.common.LikeStatus;
import com.zion.common.QueryCriteria;
import com.zion.common.TagCodeSystem;
import com.zion.common.TagSourceType;
import com.zion.converter.FeedConverter;
import com.zion.converter.SharedFeedConverter;
import com.zion.mongo.db.repository.EntityListWrapper;
import com.zion.mongo.db.repository.FeedRepository;
import com.zion.mongo.db.repository.SharedFeedRepository;
import com.zion.mongo.db.repository.TagRepository;
import com.zion.mongo.db.repository.UserFeedRepository;
import com.zion.mongo.db.repository.UserRepository;
import com.zion.morphia.entity.FeedEntity;
import com.zion.morphia.entity.SharedFeedEntity;
import com.zion.morphia.entity.TagEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.morphia.entity.UserFeedEntity;
import com.zion.tag.Tag;

public class FeedServiceImpl implements FeedService {

    private FeedRepository feedRepo;

    private UserFeedRepository userFeedRepo;

    private UserRepository userRepo;

    private SharedFeedRepository sharedFeedRepo;

    private TagRepository tagRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedServiceImpl.class);

    @Inject
    public FeedServiceImpl(FeedRepository feedRepo, TagRepository tagRepo, UserFeedRepository userFeedRepo, UserRepository userRepo,
            SharedFeedRepository sharedFeedRepo) {
        this.feedRepo = feedRepo;
        this.tagRepo = tagRepo;
        this.userFeedRepo = userFeedRepo;
        this.userRepo = userRepo;
        this.sharedFeedRepo = sharedFeedRepo;
    }

    @Override
    public Feed saveOrUpdate(Feed feed) {
        if (feed == null) {
            throw new IllegalArgumentException("cannot save or update feed as it is null.");
        }
        FeedEntity entity = FeedConverter.convertTo(feed);
        Set<Tag> filteredTags = this.tagRepo.filterDirtyUserDefinedTag(entity.getFeedTags());
        entity.setFeedTags(filteredTags);

        FeedEntity src = this.feedRepo.saveOrUpdate(entity);

        Set<Tag> userDefinedTags = filteredTags.stream().filter(tag -> {
            return (tag.getSource().equals(TagSourceType.STYLEHUB_USERINPUT.getCode())) ? true : false;
        }).collect(Collectors.toSet());
        this.tagRepo.createUserDefinedTag(userDefinedTags);
        return src != null ? FeedConverter.convertTo(src) : null;
    }

    @Override
    public Feed getById(QueryCriteria<FeedEntity> criteria) {
        if (StringUtils.isBlank(criteria.getFeedId())) {
            throw new IllegalArgumentException("cannot get feed as its id is blank.");
        }
        FeedEntity src = this.feedRepo.getById(criteria.getFeedId());
        if (src != null) {
            Feed feed = FeedConverter.convertTo(src);
            if (StringUtils.isNotBlank(criteria.getLoggedInUserId())) {
                dynaUpdateFeedLikeStatus(Arrays.asList(feed), criteria.getLoggedInUserId());
            }
            return feed;
        } else {
            return null;
        }
    }

    @Override
    public Feed disable(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("cannot disable feed as its id is blank.");
        }
        FeedEntity src = this.feedRepo.disable(id);
        return src != null ? FeedConverter.convertTo(src) : null;
    }

    @Override
    public Feed enable(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("cannot enable feed as its id is blank.");
        }
        FeedEntity src = this.feedRepo.enable(id);
        return src != null ? FeedConverter.convertTo(src) : null;
    }

    @Override
    public DtoListWrapper<Feed> list(QueryCriteria<FeedEntity> criteria) {
        EntityListWrapper<FeedEntity> enWrapper = this.feedRepo.list(criteria);
        DtoListWrapper<Feed> feedWrapper = new DtoListWrapper<>();
        List<FeedEntity> feedEntities = enWrapper.getEntities();
        List<Feed> feeds = feedEntities.stream().filter(new Predicate<FeedEntity>() {
            @Override
            public boolean test(FeedEntity t) {
                return t.getAuthor() != null && t.getAuthor().isEnabled();
            }
        }).map(f -> FeedConverter.convertTo(f)).collect(Collectors.toList());
        if (StringUtils.isNotBlank(criteria.getLoggedInUserId())) {
            dynaUpdateFeedLikeStatus(feeds, criteria.getLoggedInUserId());
        }
        feedWrapper.setDtos(feeds);
        feedWrapper.setNextPageToken(enWrapper.getNextPageToken());
        return feedWrapper;
    }

    private void dynaUpdateFeedLikeStatus(List<Feed> feeds, String loggedInUserId) {
        List<UserFeedEntity> entities = this.userFeedRepo.getUserFeeds(loggedInUserId);
        Map<String, UserFeedEntity> userFeedMap = new HashMap<>();
        for (UserFeedEntity entity : entities) {
            userFeedMap.put(entity.getFeedId(), entity);
        }
        for (Feed feed : feeds) {
            String feedId = feed.getId();
            UserFeedEntity userFeed = userFeedMap.get(feedId);
            if (userFeed != null && LikeStatus.LIKE.name().equals(userFeed.getLikeStatus())) {
                feed.setLike(true);
            }
        }
    }

    @Override
    public boolean isMyFeed(String loggedInUserId, String feedId) throws FeedNotFoundException {
        if (StringUtils.isNotBlank(feedId)) {
            FeedEntity feedEntity = this.feedRepo.getById(feedId);
            if (feedEntity == null) {
                throw new FeedNotFoundException(String.format("Cannot found the feed '%s'", feedId));
            }
            return feedEntity.getAuthor().getId().equals(loggedInUserId);
        }
        return false;
    }

    @Override
    public Feed increaseTrackingCount(String id) {
        FeedEntity feedEntity = this.feedRepo.getById(id);
        if (feedEntity != null) {
            long feedClickincreased = feedEntity.getShortUrlClickCount() + 1;
            feedEntity.setShortUrlClickCount(feedClickincreased);
            try {
                feedEntity = this.feedRepo.update(feedEntity);
            } catch (Exception e) {
                // It may fail as concurrent users updating feed
                LOGGER.warn(e.getMessage());
            }
            UserEntity author = feedEntity.getAuthor();
            long totalIncreased = author.getShortUrlClickCount() + 1;
            author.setShortUrlClickCount(totalIncreased);
            try {
                this.userRepo.updateUser(author);
            } catch (Exception e) {
                // It may fail as concurrent users updating feed
                LOGGER.warn(e.getMessage());
            }
            return FeedConverter.convertTo(feedEntity);
        } else {
            return null;
        }
    }

    @Override
    public FeedCount getMyFeedCount(String authorId) {
        return this.feedRepo.getMyFeedCount(authorId);
    }

    @Override
    public Feed updateStatus(String id, String status) {
        Validate.notBlank(id);
        Validate.notBlank(status);
        FeedEntity entity = this.feedRepo.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            return FeedConverter.convertTo(this.feedRepo.update(entity));
        } else {
            return null;
        }
    }

    @Override
    public Feed getById(String id) {
        Validate.notBlank(id);
        FeedEntity entity = this.feedRepo.getById(id);
        return entity != null ? FeedConverter.convertTo(entity) : null;
    }

    @Override
    public SharedFeed shareFeed(SharedFeed sharedFeed) {
        SharedFeedEntity entity = this.sharedFeedRepo.create(SharedFeedConverter.convertTo(sharedFeed));
        return entity != null ? SharedFeedConverter.convertTo(entity) : null;
    }

    @Override
    public Feed increaseViewCount(String id) {
        Validate.notBlank(id);
        FeedEntity entity = this.feedRepo.getById(id);
        if (entity == null) {
            return null;
        } else {
            long count = entity.getViewedCount() + 1;
            entity.setViewedCount(count);
            FeedEntity updated;
            try {
                updated = this.feedRepo.update(entity);
                return updated != null ? FeedConverter.convertTo(updated) : null;
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
                return null;
            }
        }
    }

    @Override
    public Feed updateSysTags(FeedTagDto dto) {
        if (StringUtils.isBlank(dto.getFeedId())) {
            return null;
        }
        FeedEntity feedEntity = this.feedRepo.getById(dto.getFeedId());
        if (feedEntity != null) {
            Set<TagEntity> tagEntities = new HashSet<>();
            for (Tag tag : dto.getTags()) {
                TagCodeSystem codeSystem = TagCodeSystem.findTagCodeSystemByCode(tag.getCodeSystem());
                TagSourceType source = TagSourceType.findTagSourceTypeByCode(tag.getSource());
                if (StringUtils.isNotBlank(tag.getCode()) && codeSystem != null && source != null) {
                    TagEntity entity = this.tagRepo.findTag(tag.getCode(), codeSystem, source);
                    if (entity != null) {
                        tagEntities.add(entity);
                    }
                }
            }
            feedEntity.setSysTags(tagEntities);
            feedEntity = this.feedRepo.update(feedEntity);
            return FeedConverter.convertTo(feedEntity);
        } else {
            return null;
        }
    }

}
