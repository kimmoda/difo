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
package com.zion.socialmedia.shareurl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.zion.common.UrlPreviewField;
import com.zion.mongo.db.repository.PromotionTaskRepository;
import com.zion.task.PromotionTask;
import com.zion.task.service.PromotionTaskService;
import org.apache.commons.lang3.StringUtils;

import com.zion.feed.Feed;
import com.zion.feed.FeedService;
import com.zion.media.Media;
import com.zion.socialmedia.SocialSource;
import com.zion.socialmedia.shareurl.model.SocialShare;
import com.zion.user.Person;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;
import com.zion.user.service.UserService;

public class ShareUrlGeneraterSerivceImpl implements ShareUrlGeneraterService {

    private static Map<SocialSource, AbstractShareUrlGenerater> generaters = new HashMap<>();

    static {
        generaters.put(SocialSource.FACEBOOK, new FacebookShareUrlGenerater());
        generaters.put(SocialSource.GOOGLE, new GoogleShareUrlGenerater());
        generaters.put(SocialSource.PINTEREST, new PinterestShareUrlGenerater());
        generaters.put(SocialSource.TWITTER, new TwitterShareUrlGenerater());
        generaters.put(SocialSource.SHORTLINK, new ShortUrlGenerater());
    }

    private FeedService feedService;
    private UserService userService;
    private PromotionTaskService promotionTaskService;
    private PromotionTaskRepository promotionTaskRepository;

    @Inject
    public ShareUrlGeneraterSerivceImpl(FeedService feedService, UserService userService, PromotionTaskService promotionTaskService, PromotionTaskRepository promotionTaskRepository) {
        this.feedService = feedService;
        this.userService = userService;
        this.promotionTaskService = promotionTaskService;
        this.promotionTaskRepository = promotionTaskRepository;
    }

    @Override
    public List<SocialShare> generateFeedShareUrls(String feedId) {

        List<SocialShare> socialShares = new ArrayList<>();
        Feed feed = feedService.getById(feedId);

        if (feed != null && feed.isEnabled()) {
            for (Map.Entry<SocialSource, AbstractShareUrlGenerater> entry : generaters.entrySet()) {
                if (entry.getValue().isEnbaled()) {
                    Media coverImage = feed.getCoverImage();
                    String url = coverImage != null ? coverImage.getUrl() : null;
                    String sharedUrl = entry.getValue().getShareUrl(generateFeedTitle(feed), getShortDescription(feed.getContent()),
                            feed.getShortUrl(), url);
                    socialShares.add(new SocialShare(entry.getKey().name(), entry.getKey().getDisplayName(), entry.getKey().getFontIcon(),
                            sharedUrl, entry.getKey().getOrder()));
                }
            }
        }
        return socialShares;
    }

    @Override
    public List<SocialShare> generateUserShareUrls(String userId) {
        List<SocialShare> socialShares = new ArrayList<>();
        User user;
        try {
            user = this.userService.getUserById(userId);
        } catch (UserNotFoundException e) {
            throw new IllegalArgumentException(String.format("cannot found user '%s'", userId));
        }

        if (user != null && user.isEnabled()) {
            for (Map.Entry<SocialSource, AbstractShareUrlGenerater> entry : generaters.entrySet()) {
                if (entry.getValue().isEnbaled()) {
                    Person person = user.getPerson();
                    String bio = "";
                    String avatar = "";
                    if (person != null) {
                        avatar = StringUtils.isNotBlank(person.getAvatar()) ? person.getAvatar() : "";
                        bio = StringUtils.isNotBlank(person.getIntroduce()) ? person.getIntroduce() : "";
                    }
                    String sharedUrl = entry.getValue().getShareUrl(user.getDisplayName(), bio,
                            user.getShortUrl(), avatar);
                    socialShares.add(new SocialShare(entry.getKey().name(), entry.getKey().getDisplayName(), entry.getKey().getFontIcon(),
                            sharedUrl, entry.getKey().getOrder()));
                }
            }
        }
        return socialShares;
    }

    @Override
    public List<SocialShare> generateCampaignShareUrls(String campaignId) {
        List<SocialShare> socialShares = new ArrayList<>();
        PromotionTask promotionTask = this.promotionTaskService.getById(campaignId, null);
        if (promotionTask != null && promotionTask.isEnabled()) {
            for (Map.Entry<SocialSource, AbstractShareUrlGenerater> entry : generaters.entrySet()) {
                if (entry.getValue().isEnbaled()) {
                    Map<String, String> postPreview = promotionTask.getPostUrlPreview();
                    String imageUrl = StringUtils.isNotBlank(postPreview.get(UrlPreviewField.IMAGE.getFieldName())) ? postPreview.get(UrlPreviewField.IMAGE.getFieldName()) : postPreview.get(UrlPreviewField.FAVICON.getFieldName());
                    String sharedUrl;
                    if (entry.getKey().equals(SocialSource.SHORTLINK)) {
                        sharedUrl = entry.getValue().getShareUrl(promotionTask.getTitle(), promotionTask.getDescription(), promotionTask.getShortUrl(), imageUrl);
                    } else {
                        sharedUrl = entry.getValue().getShareUrl(promotionTask.getTitle(), promotionTask.getDescription(), promotionTaskRepository.generateOriUrl(promotionTask.getId()), imageUrl);
                    }
                    socialShares.add(new SocialShare(entry.getKey().name(), entry.getKey().getDisplayName(), entry.getKey().getFontIcon(), sharedUrl, entry.getKey().getOrder()));
                }
            }
        }
        return socialShares;
    }

    private String generateFeedTitle(Feed feed) {
        if (StringUtils.isBlank(feed.getTitle())) {
            return feed.getAuthor().getDisplayName();
        }
        return feed.getTitle();
    }

    private String getShortDescription(String description) {
        String normalizedDescription = "";
        if (StringUtils.isNotBlank(description)) {
            normalizedDescription = description.trim();
        }
        if (normalizedDescription.length() > 110) {
            return normalizedDescription.substring(0, 110) + "...";
        }
        return normalizedDescription;
    }
}

