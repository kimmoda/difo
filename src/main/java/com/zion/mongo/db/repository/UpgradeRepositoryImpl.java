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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;

import com.zion.common.*;
import com.zion.morphia.entity.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.feed.FeedType;
import com.zion.media.Media;
import com.zion.media.MediaType;
import com.zion.media.Photo;
import com.zion.morphia.entity.embed.FeedSystemStat;
import com.zion.morphia.entity.embed.PersonEntity;
import com.zion.morphia.entity.embed.UserSystemStat;
import com.zion.socialmedia.SocialSource;

public class UpgradeRepositoryImpl extends AbstractRepository implements UpgradeRepository {

    private String appVersion = AppConfig.getInstance().getAppVersion();
    private EnvType env = AppConfig.getInstance().getAppEnv();
    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeRepositoryImpl.class);

    private TagRepository tagRepository;
    private UserRepository userRepository;
    private FeedRepository feedRepository;
    private UserFeedRepository userFeedRepository;
    private PromotionTaskRepository promotionTaskRepository;

    @Inject
    public UpgradeRepositoryImpl(TagRepository tagRepository, UserRepository userRepository,
                                 FeedRepository feedRepository, UserFeedRepository userFeedRepository,
                                 PromotionTaskRepository promotionTaskRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.feedRepository = feedRepository;
        this.userFeedRepository = userFeedRepository;
        this.promotionTaskRepository = promotionTaskRepository;
    }

    @Override
    public void dbupgrade() {
        upgrade200DbScript();
        upgrade201DbScript();
        upgrade210DbScript();
        try {
            upgrade211DbScript();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        upgrade220DbScript();
        this.upgrade230DbScript();
        this.upgrade240DbScript();
        this.upgrade242DbScript();
        this.upgrade300DbScript();
        this.upgrade301DbScript();
        this.upgrade400DbScript();
        this.upgrade420DBScript();
    }

    private void upgrade200DbScript() {
        if (appVersion.equals("2.0.0") || EnvType.TEST == env) {
            upgradePredefinedStyleTags();
        }
    }

    private void upgradePredefinedStyleTags() {
        for (PreferredStyleType preferredStyleType : PreferredStyleType.values()) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setCode(preferredStyleType.getKey());
            tagEntity.setLabel(preferredStyleType.getDisplayName());
            tagEntity.setCodeSystem(TagCodeSystem.PREFERRED_STYLE.getCode());
            tagEntity.setSource(TagSourceType.STYLEHUB_PREDEFINED.getCode());
            this.tagRepository.createTag(tagEntity);
        }
    }

    private void upgradeFeedOrderTags() {
        LOGGER.info("Updated feed order tags");
        for (LookOrderTag lookOrderTag : LookOrderTag.values()) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setCode(lookOrderTag.getKey());
            tagEntity.setLabel(lookOrderTag.getDisplayName());
            tagEntity.setCodeSystem(TagCodeSystem.LOOK_ORDER.getCode());
            tagEntity.setSource(TagSourceType.STYLEHUB_SYSTEM.getCode());
            this.tagRepository.createTag(tagEntity);
        }
        LOGGER.info("End of updateding feed order tags");
    }

    private void upgrade201DbScript() {
        if (appVersion.equals("2.0.1") || EnvType.TEST == env) {
            UserEntity userEntity = this.userRepository.getUserByUsername("admin");
            Set<String> roles = userEntity.getUserRoles();
            roles.add(UserRole.SYSADMIN.name());
            this.userRepository.updateUser(userEntity);

            List<FeedEntity> feedList = this.feedRepository.listAllFeeds();
            for (FeedEntity entity : feedList) {
                long currentCount = entity.getLikeCount();
                Random random = new Random();
                int count = random.nextInt(15) + 5;
                entity.setLikeCount(currentCount + count);
                this.feedRepository.update(entity);
            }
        }
    }

    private void upgrade210DbScript() {
        if (appVersion.equals("2.1.0") || EnvType.TEST == env) {
            // 1. Create product category predefined tags.
            LOGGER.info("start upgrade product category");
            for (ProductCategoryType type : ProductCategoryType.values()) {
                TagEntity tagEntity = new TagEntity();
                tagEntity.setCode(type.getKey());
                tagEntity.setLabel(type.getDisplayName());
                tagEntity.setCodeSystem(TagCodeSystem.PRODUCT_CATEGORY.getCode());
                tagEntity.setSource(TagSourceType.STYLEHUB_PREDEFINED.getCode());
                this.tagRepository.createTag(tagEntity);
            }
            LOGGER.info("End of upgrade product category");
            List<FeedEntity> entities = this.feedRepository.listAllFeeds();
            LOGGER.info("start upgrade feed entities");
            for (FeedEntity entity : entities) {
                // 2. update feed status
                if (StringUtils.isBlank(entity.getStatus())) {
                    entity.setStatus(FeedStatus.FINAL.name());
                }

                // 3. update author roles
                UserEntity userEntity = entity.getAuthor();
                if (userEntity != null && userEntity.getUserRoles() != null && !userEntity.getUserRoles().isEmpty()) {
                    entity.setAuthorRoles(userEntity.getUserRoles());
                }

                FeedEntity updated = this.feedRepository.update(entity);

                // 4. update the short url
                this.feedRepository.generateShortUrl(updated);
            }
            LOGGER.info(String.format("'%s' records has been updated", entities.size()));
            LOGGER.info("End of upgrade feed entites");
            // 5. update the UserFeed entity
            List<UserFeedEntity> userFeedEntities = this.userFeedRepository.listAll();
            LOGGER.info("start upgrade user feed entities");
            for (UserFeedEntity userFeedEntity : userFeedEntities) {
                if (StringUtils.isBlank(userFeedEntity.getFeedAuthorId())) {
                    String feedId = userFeedEntity.getFeedId();
                    FeedEntity feedEntity = this.feedRepository.getById(feedId);
                    userFeedEntity.setFeedAuthorId(feedEntity.getAuthor().getId());
                }
                userFeedEntity.setLikeStatus("");
                this.userFeedRepository.update(userFeedEntity);
            }
            LOGGER.info(String.format("'%s' records has been updated", userFeedEntities.size()));
            LOGGER.info("end of upgrade user feed entities");
            // 6. update the feed stat count
            List<FeedEntity> feedList = this.feedRepository.listAllFeeds();
            LOGGER.info("start upgrade feed stats count");
            for (FeedEntity entity : feedList) {
                entity.setLikeCount(0);
                entity.setDislikeCount(0);
                entity.setShortUrlClickCount(0);
                entity.setSharedCount(0);
                entity.setViewedCount(0);
                entity.setFeedSystemStat(new FeedSystemStat());
                this.feedRepository.update(entity);
            }
            LOGGER.info(String.format("'%s' records has been updated", feedList.size()));
            LOGGER.info("end of upgrading like count");
            // 7. update total user's feed list count
            List<UserEntity> userList = this.userRepository.listAllUsers();
            LOGGER.info("start upgrade user feed stats count");
            for (UserEntity user : userList) {
                user.setFeedLikeCount(0);
                user.setFeedSharedCount(0);
                user.setShortUrlClickCount(0);
                user.setFeedViewedCount(0);
                user.setUserSystemStat(new UserSystemStat());
                long finalFeedCount = this.feedRepository.getMyFeedCount(user.getId(), FeedStatus.FINAL.name());
                user.setWorkCount(finalFeedCount);
                this.userRepository.updateUser(user);
            }
            LOGGER.info(String.format("'%s' records has been updated", userList.size()));
            LOGGER.info("end of upgrade user feed total like count");
        }
    }

    private void upgrade211DbScript() throws IOException {
        if (appVersion.equals("2.1.1.final") || appVersion.equals("2.1.1.m2") || EnvType.TEST == env) {
            LOGGER.info("start upgrade 2.1.1.final");

            // 1. update the UserFeed entity
            List<UserFeedEntity> userFeedEntities = this.userFeedRepository.listAll();
            LOGGER.info("start upgrade user feed entities");
            for (UserFeedEntity userFeedEntity : userFeedEntities) {
                userFeedEntity.setLikeStatus("");
                this.userFeedRepository.update(userFeedEntity);
            }
            LOGGER.info(String.format("'%s' records has been updated", userFeedEntities.size()));
            LOGGER.info("end of upgrade user feed entities");
            // 2. update the feed stat count
            List<FeedEntity> feedList = this.feedRepository.listAllFeeds();
            LOGGER.info("start upgrade feed stats count");
            for (FeedEntity entity : feedList) {
                entity.setLikeCount(0);
                entity.setDislikeCount(0);
                entity.setShortUrlClickCount(0);
                entity.setSharedCount(0);
                entity.setViewedCount(0);
                entity.setFeedSystemStat(new FeedSystemStat());
                try {
                    this.feedRepository.update(entity);
                } catch (Exception e) {
                    // Ignore it may fail as the concurrent issue.
                    LOGGER.info(String.format("fail to update feed '%s' ", entity.getId()));
                }
            }
            LOGGER.info(String.format("'%s' records has been updated", feedList.size()));
            LOGGER.info("end of upgrade user feed entities");

            // 3. update total user's feed list count
            List<UserEntity> userList = this.userRepository.listAllUsers();
            LOGGER.info("start upgrade user feed stats count");
            for (UserEntity user : userList) {
                user.setFeedLikeCount(0);
                user.setFeedSharedCount(0);
                user.setShortUrlClickCount(0);
                user.setFeedViewedCount(0);
                user.setUserSystemStat(new UserSystemStat());
                long finalFeedCount = this.feedRepository.getMyFeedCount(user.getId(), FeedStatus.FINAL.name());
                user.setWorkCount(finalFeedCount);
                try {
                    UserEntity updated = this.userRepository.updateUser(user);
                    this.userRepository.generateShortUrl(updated);
                } catch (Exception e) {
                    // Ignore it may fail as the concurrent issue.
                    LOGGER.info(String.format("fail to update user '%s' ", user.getId()));
                }
            }
            LOGGER.info(String.format("'%s' records has been updated", userList.size()));
            LOGGER.info("end of upgrade user feed total like count");
            LOGGER.info("end of upgrade 2.1.1.final");
        }
    }

    private void upgrade220DbScript() {
        if (appVersion.equals("2.2.0.m1") || appVersion.equals("2.2.0.final") || EnvType.TEST == env) {
            LOGGER.info("upgrading for 2.2.0");
            List<UserEntity> userList = this.userRepository.listAllUsers();
            LOGGER.info("start upgrade user's short url.");
            int shortUrlCount = 0;
            for (UserEntity user : userList) {
                try {
                    if (StringUtils.isBlank(user.getShortUrl())) {
                        this.userRepository.generateShortUrl(user);
                        shortUrlCount++;
                    }
                } catch (Exception e) {
                    // Ignore it may fail as the concurrent issue.
                    LOGGER.info(String.format("fail to update user '%s' ", user.getId()));
                }
            }
            LOGGER.info(String.format("'%d' records has been updated", shortUrlCount));
        }
    }

    private void upgrade230DbScript() {
        if (appVersion.equals("2.3.0.m2") || EnvType.TEST == env) {
            LOGGER.info("upgrading for 2.3.0.m2");
            for (LookOrderTag lookOrderTag : LookOrderTag.values()) {
                TagEntity tagEntity = new TagEntity();
                tagEntity.setCode(lookOrderTag.getKey());
                tagEntity.setLabel(lookOrderTag.getDisplayName());
                tagEntity.setCodeSystem(TagCodeSystem.LOOK_ORDER.getCode());
                tagEntity.setSource(TagSourceType.STYLEHUB_SYSTEM.getCode());
                this.tagRepository.createTag(tagEntity);
            }
            for (TrendsetterOrderTag trendsetterOrderTag : TrendsetterOrderTag.values()) {
                TagEntity tagEntity = new TagEntity();
                tagEntity.setCode(trendsetterOrderTag.getKey());
                tagEntity.setLabel(trendsetterOrderTag.getDisplayName());
                tagEntity.setCodeSystem(TagCodeSystem.TRENDSETTER_ORDER.getCode());
                tagEntity.setSource(TagSourceType.STYLEHUB_SYSTEM.getCode());
                this.tagRepository.createTag(tagEntity);
            }
            LOGGER.info("finish upgrade 2.3.0.m2");
        }
    }

    private void upgrade240DbScript() {
        if (appVersion.equals("2.4.0") || EnvType.TEST == env) {
            LOGGER.info("============Updating 2.4.0=============");
            upgradePredefinedStyleTags();
            upgradeFeedOrderTags();
            TagEntity defaultTag = this.tagRepository.getDefaultFeedSysTag();
            if (defaultTag != null) {
                LOGGER.info("update feed default sys tag");
                int i = 0;
                for (FeedEntity feed : this.feedRepository.listAllFeeds()) {
                    if (feed.getSysTags() == null || feed.getSysTags().isEmpty()) {
                        Set<TagEntity> tags = new HashSet<>();
                        tags.add(defaultTag);
                        feed.setSysTags(tags);
                        this.feedRepository.update(feed);
                        i++;
                    }
                }
                LOGGER.info(String.format("Update '%d' feed", i));
            }
            LOGGER.info("==========Completed Update 2.4.0============");
        }
    }

    private void upgrade242DbScript() {
        if (appVersion.equals("2.4.2") || EnvType.TEST == env) {
            LOGGER.info("============Updating 2.4.2=============");
            int i = 0;
            LOGGER.info("==========update feed photo none secure url to https ===============");
            for (FeedEntity feed : this.feedRepository.listAllFeeds()) {
                if (feed.getPhoto() != null && StringUtils.isNotBlank(feed.getPhoto().getUrl())) {
                    String url = feed.getPhoto().getUrl().trim();
                    if (url.startsWith("http://")) {
                        String secureUrl = StringUtils.replace(url, "http://", "https://");
                        Photo photo = feed.getPhoto();
                        photo.setUrl(secureUrl);
                        feed.setPhoto(photo);
                        this.feedRepository.update(feed);
                        i++;
                    }
                }
            }
            LOGGER.info(String.format("Update '%d' feed", i));
            LOGGER.info("==========Completed Update 2.4.2============");
        }
    }

    private void upgrade300DbScript() {
        if (appVersion.equals("3.0.0") || EnvType.TEST == env) {
            LOGGER.info("============Updating 3.0.0=============");
            int i = 0;
            LOGGER.info("============Updating feed cover image ===========");
            for (FeedEntity feed : this.feedRepository.listAllFeeds()) {
                Photo photo = feed.getPhoto();
                if (photo == null) {
                    continue;
                }
                Media coverImage = new Media();
                coverImage.setFormat(photo.getFormat());
                coverImage.setMediaType(MediaType.IMAGE.name());
                coverImage.setPublicId(photo.getPublicId());
                coverImage.setSource(photo.getSource());
                coverImage.setUrl(photo.getUrl());
                coverImage.setVersion(photo.getVersion());
                coverImage.setDescription("");
                coverImage.setCoverImage(true);

                String annotationData = photo.getAnnotationData();
                String updatedAnnotationData = StringUtils.replace(annotationData, "pin-image", photo.getPublicId());
                feed.setAnnotationData(updatedAnnotationData);
                feed.setCoverImage(coverImage);
                feed.setMediaContent(new ArrayList<>());
                feed.setType(FeedType.SINGLE_IMAGE.name());
                this.feedRepository.update(feed);
                i++;
            }
            LOGGER.info(String.format("Update '%d' feed", i));
            LOGGER.info("============End Updating 3.0.0=============");
        }
    }

    private void upgrade301DbScript() {
        String fbAvatarPattern = "https://res.cloudinary.com/dozzyprzt/image/facebook/%s.jpg";
        if (appVersion.equals("3.0.2") || EnvType.TEST == env) {
            LOGGER.info("============Updating 3.0.2=============");
            LOGGER.info("============Updating user===========");
            int i = 0;
            List<UserEntity> userList = this.userRepository.listAllUsers();
            for (UserEntity entity : userList) {
                if (entity.getPerson() != null && StringUtils.isNotBlank(entity.getPerson().getAvatar())) {
                    boolean isFBAvatarUrl = entity.getPerson().getAvatar().contains("fbcdn");
                    if (isFBAvatarUrl && StringUtils.isNotBlank(entity.getExternalUserId())) {
                        String url = String.format(fbAvatarPattern, entity.getExternalUserId());
                        entity.getPerson().setAvatar(url);
                        entity.setSource(SocialSource.FACEBOOK.name());
                        this.userRepository.updateUser(entity);
                        i++;
                    }
                }
            }
            LOGGER.info(String.format("============Updating '%d' user===========", i));
            LOGGER.info("============End of Updating user===========");
        }

    }

    private void upgrade400DbScript() {
        if (appVersion.equals("4.0.0") || EnvType.TEST == env) {
            LOGGER.info("============Updating 4.0.0=============");
            String firstname = "System";
            String username = AppConfig.getInstance().getSystemUsername();
            UserEntity existing = this.userRepository.getUserByUsername(username);
            if (existing == null) {
                LOGGER.info("============Create system user===========");
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(username);
                PersonEntity person = userEntity.getPerson();
                person.setFirstName(firstname);
                userEntity.getUserRoles().add(UserRole.DEFAULT.name());
                userEntity.setRegistrationStatus(RegistrationStatus.APPROVED.name());
                userEntity.setSource(UserSource.ZION.name());
                this.userRepository.createUser(userEntity);
                LOGGER.info("============End of creating system user===========");
            }
            LOGGER.info("============End of updating 4.0.0=============");
        }
    }

    private void upgrade420DBScript() {
        if (appVersion.equals("4.2.0") || EnvType.TEST == env) {
            LOGGER.info("============Updating 4.2.0=============");
            List<PromotionTaskEntity> taskList = this.promotionTaskRepository.listAllTasks();
            for (PromotionTaskEntity promotionTaskEntity : taskList) {
                Validate.notNull(promotionTaskEntity);
                Validate.notEmpty(promotionTaskEntity.getId());
                if (StringUtils.isBlank(promotionTaskEntity.getShortUrl())) {
                    this.promotionTaskRepository.generateShortUrl(promotionTaskEntity);
                }
            }
            LOGGER.info("============End of updating 4.2.0=============");
        }
    }
}
