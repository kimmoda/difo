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
package com.zion.morphia.entity;

import com.zion.morphia.entity.embed.FeedSystemStat;
import com.zion.morphia.entity.embed.ShopTagEntity;
import com.zion.tag.Tag;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import com.zion.media.Media;
import com.zion.media.Photo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(value = "zion_feed", noClassnameStored = true)
public class FeedEntity extends BaseEntity {

    private static final long serialVersionUID = 2375288034273424083L;

    private String title;

    private String content;

    private Photo photo;
    
    private Media coverImage;
    
    private List<Media> mediaContent = new ArrayList<>();
    
    /**
     * used for media images annotation data. x and y coordinator position and url links
     */
    private String annotationData;

    // image, video, text.
    private String type;

    @Reference
    private UserEntity author;

    private Set<String> authorRoles;

    // final & draft
    private String status;

    private String category;

    private List<ShopTagEntity> shopTags;

    private Set<Tag> feedTags = new HashSet<>();
    
    /**
     * Super admin update these tags which used for system configuration eg: recommend the feed on the top of page
     */
    @Embedded
    private Set<TagEntity> sysTags = new HashSet<>();

    private long likeCount;
    // sum the real number and system fake number.Use for ordering.
    private long sumLikeCount;

    private long dislikeCount;

    private long shortUrlClickCount;
    // sum the real number and system fake number
    private long sumShortUrlClickCount;

    private long sharedCount;
    // sum the real number and system fake number
    private long sumSharedCount;

    private long viewedCount;
    // sum the real number and system fake number
    private long sumViewedCount;

    @Embedded
    private FeedSystemStat feedSystemStat = new FeedSystemStat();
    
    private String shortUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<ShopTagEntity> getShopTags() {
        return shopTags;
    }

    public void setShopTags(List<ShopTagEntity> shopTags) {
        this.shopTags = shopTags;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.setSumLikeCount(this.getFeedSystemStat().getLikeCount() + likeCount);
        this.likeCount = likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Set<Tag> getFeedTags() {
        return feedTags;
    }

    public void setFeedTags(Set<Tag> feedTags) {
        this.feedTags = feedTags;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Set<String> getAuthorRoles() {
        return authorRoles;
    }

    public void setAuthorRoles(Set<String> authorRoles) {
        this.authorRoles = authorRoles;
    }

    public long getShortUrlClickCount() {
        return shortUrlClickCount;
    }

    public void setShortUrlClickCount(long shortUrlClickCount) {
        this.setSumShortUrlClickCount(this.getFeedSystemStat().getShortUrlClickCount() + shortUrlClickCount);
        this.shortUrlClickCount = shortUrlClickCount;
    }

    public FeedSystemStat getFeedSystemStat() {
        return feedSystemStat;
    }

    public void setFeedSystemStat(FeedSystemStat feedSystemStat) {
        this.feedSystemStat = feedSystemStat;
        this.setSumLikeCount(this.feedSystemStat.getLikeCount() + this.getLikeCount());
        this.setSumSharedCount(this.feedSystemStat.getSharedCount() + this.getSharedCount());
        this.setSumShortUrlClickCount(this.feedSystemStat.getShortUrlClickCount() + this.getShortUrlClickCount());
        this.setSumViewedCount(this.feedSystemStat.getViewedCount() + this.getViewedCount());
    }

    public long getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(long sharedCount) {
        this.setSumSharedCount(this.feedSystemStat.getSharedCount() + sharedCount);
        this.sharedCount = sharedCount;
    }

    public long getViewedCount() {
        return viewedCount;
    }

    public void setViewedCount(long viewedCount) {
        this.setSumViewedCount(this.getFeedSystemStat().getViewedCount() + viewedCount);
        this.viewedCount = viewedCount;
    }

    public long getSumLikeCount() {
        return sumLikeCount;
    }

    public void setSumLikeCount(long sumLikeCount) {
        this.sumLikeCount = sumLikeCount;
    }

    public long getSumShortUrlClickCount() {
        return sumShortUrlClickCount;
    }

    public void setSumShortUrlClickCount(long sumShortUrlClickCount) {
        this.sumShortUrlClickCount = sumShortUrlClickCount;
    }

    public long getSumSharedCount() {
        return sumSharedCount;
    }

    public void setSumSharedCount(long sumSharedCount) {
        this.sumSharedCount = sumSharedCount;
    }

    public long getSumViewedCount() {
        return sumViewedCount;
    }

    public void setSumViewedCount(long sumViewedCount) {
        this.sumViewedCount = sumViewedCount;
    }

    public Set<TagEntity> getSysTags() {
        return sysTags;
    }

    public void setSysTags(Set<TagEntity> sysTags) {
        this.sysTags = sysTags;
    }

    public Media getCoverImage() {
        return coverImage;
    }

    public List<Media> getMediaContent() {
        return mediaContent;
    }

    public void setCoverImage(Media coverImage) {
        this.coverImage = coverImage;
    }

    public void setMediaContent(List<Media> mediaContent) {
        this.mediaContent = mediaContent;
    }

    public String getAnnotationData() {
        return annotationData;
    }

    public void setAnnotationData(String annotationData) {
        this.annotationData = annotationData;
    }

    @Override
    public String toString() {
        return this.getId();
    }

}
