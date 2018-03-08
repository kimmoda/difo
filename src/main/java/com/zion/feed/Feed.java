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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zion.media.Media;
import com.zion.media.Photo;
import com.zion.tag.Tag;
import com.zion.user.Author;

@JsonInclude(Include.NON_NULL)
public class Feed {
    private String id;

    private String title;

    private String content;

    private Photo photo;
    
    private Media coverImage;
    
    private List<Media> mediaContent = new ArrayList<>();
    
    /**
     * used for media images annotation data. x and y coordinator position and url links
     */
    private String annotationData;

    // image video
    private String type;

    private Author author;

    private Set<String> authorRoles = new HashSet<>();

    private Date publishAt;

    private Date lastChange;

    private String category;

    private String status;

    private boolean enabled;

    private String tags;

    private List<ShopTag> shopTags;

    private Set<Tag> feedTags = new HashSet<>();

    private Set<Tag> sysTags = new HashSet<>();

    private long likeCount;

    private long dislikeCount;

    private long shortUrlClickCount;

    private long sharedCount;

    private long viewedCount;

    private boolean like;

    private String shortUrl;
    
    private Date CreationDate;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<ShopTag> getShopTags() {
        return shopTags;
    }

    public void setShopTags(List<ShopTag> shopTags) {
        this.shopTags = shopTags;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
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
        this.shortUrlClickCount = shortUrlClickCount;
    }

    public long getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(long sharedCount) {
        this.sharedCount = sharedCount;
    }

    public long getViewedCount() {
        return viewedCount;
    }

    public void setViewedCount(long viewedCount) {
        this.viewedCount = viewedCount;
    }

    public Set<Tag> getSysTags() {
        return sysTags;
    }

    public void setSysTags(Set<Tag> sysTags) {
        this.sysTags = sysTags;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
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

}
