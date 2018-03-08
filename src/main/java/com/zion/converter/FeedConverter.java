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
package com.zion.converter;

import com.zion.feed.Feed;
import com.zion.morphia.entity.FeedEntity;
import com.zion.morphia.entity.TagEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.morphia.entity.embed.FeedSystemStat;
import com.zion.tag.Tag;
import com.zion.user.Author;
import com.zion.user.User;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FeedConverter {

    private static final ShopTagConverter shopTagConverter = new ShopTagConverter();
    private static final UserConverter userConverter = new UserConverter();

    public static FeedEntity convertTo(Feed src) {
        FeedEntity dest = new FeedEntity();
        if (StringUtils.isNotBlank(src.getId())) {
            dest.setEntityId(new ObjectId(src.getId()));
        }

        Author author = src.getAuthor();
        UserEntity userEntity = new UserEntity();
        userEntity.setEntityId(new ObjectId(author.getUserId()));
        dest.setAuthor(userEntity);
        dest.setAuthorRoles(src.getAuthorRoles());
        if (StringUtils.isNotBlank(src.getContent())) {
            dest.setContent(src.getContent().trim());
        }
        dest.setPublishAt(src.getPublishAt());
        if (StringUtils.isNotBlank(src.getTitle())) {
            dest.setTitle(src.getTitle().trim());
        }
        dest.setType(src.getType());
        dest.setCoverImage(src.getCoverImage());
        if (src.getMediaContent() != null) {
            dest.setMediaContent(src.getMediaContent());
        } else {
            dest.setMediaContent(new ArrayList<>());
        }
        dest.setAnnotationData(src.getAnnotationData());
        dest.setCategory(src.getCategory());
        dest.setStatus(src.getStatus());
        dest.setShopTags(shopTagConverter.convertToEntity(src.getShopTags()));
        dest.setDislikeCount(src.getDislikeCount());
        if (src.getFeedTags() != null) {
            dest.setFeedTags(src.getFeedTags());
        }
        dest.setLikeCount(src.getLikeCount());
        return dest;
    }

    public static Feed convertTo(FeedEntity src) {
        Feed dest = new Feed();
        dest.setId(src.getId());
        User user = userConverter.convertToModel(src.getAuthor());
        dest.setAuthor(userConverter.convertToAuthor(user));
        dest.setContent(src.getContent());
        dest.setEnabled(src.isEnabled());
        dest.setCoverImage(src.getCoverImage());
        dest.setMediaContent(src.getMediaContent());
        dest.setAnnotationData(src.getAnnotationData());
        dest.setPublishAt(src.getPublishAt());
        dest.setTitle(src.getTitle());
        dest.setType(src.getType());
        dest.setCategory(src.getCategory());
        dest.setStatus(src.getStatus());
        dest.setShopTags(shopTagConverter.convertToModel(src.getShopTags()));
        dest.setDislikeCount(src.getDislikeCount());

        FeedSystemStat sysStat = src.getFeedSystemStat();
        long sysLikeCount = sysStat != null ? sysStat.getLikeCount() : 0;
        dest.setLikeCount(src.getLikeCount() + sysLikeCount);

        long sysShortUtilCount = sysStat != null ? sysStat.getShortUrlClickCount() : 0;
        dest.setShortUrlClickCount(src.getShortUrlClickCount() + sysShortUtilCount);

        long sysViewCount = sysStat != null ? sysStat.getViewedCount() : 0;
        dest.setViewedCount(src.getViewedCount() + sysViewCount);

        long sysShareCount = sysStat != null ? sysStat.getSharedCount() : 0;
        dest.setSharedCount(src.getSharedCount() + sysShareCount);

        dest.setAuthorRoles(src.getAuthorRoles());
        if (src.getFeedTags() != null) {
            dest.setFeedTags(src.getFeedTags());
        }
        dest.setShortUrl(src.getShortUrl());

        Set<TagEntity> tagEntities = src.getSysTags();
        Set<Tag> sysTags = new HashSet<>();
        for (TagEntity entity : tagEntities) {
            if (entity != null) {
                sysTags.add(new Tag(entity.getLabel(), entity.getCode(), entity.getCodeSystem(), entity.getSource()));
            }
        }
        dest.setSysTags(sysTags);
        dest.setCreationDate(src.getCreationDate());
        return dest;
    }

    public static void updateFeedEntity(FeedEntity src, FeedEntity dest) {
        if (StringUtils.isNotBlank(src.getContent())) {
            dest.setContent(src.getContent().trim());
        }
        dest.setPublishAt(src.getPublishAt());
        if (StringUtils.isNotBlank(src.getTitle())) {
            dest.setTitle(src.getTitle().trim());
        }
        dest.setAuthorRoles(src.getAuthorRoles());
        dest.setCoverImage(src.getCoverImage());
        if (src.getMediaContent() != null) {
            dest.setMediaContent(src.getMediaContent());
        } else {
            dest.setMediaContent(new ArrayList<>());
        }
        dest.setAnnotationData(src.getAnnotationData());
        dest.setType(src.getType());
        dest.setCategory(src.getCategory());
        dest.setShopTags(src.getShopTags());
        if (src.getFeedTags() != null) {
            dest.setFeedTags(src.getFeedTags());
        }
    }
}
