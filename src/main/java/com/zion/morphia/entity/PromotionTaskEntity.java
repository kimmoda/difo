/*************************************************************************
 *
 * Forward Thinking CONFIDENTIAL
 * __________________
 *
 *  2013 - 2018 Forward Thinking Ltd
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

import java.util.Date;
import java.util.Map;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "zion_promotion_task", noClassnameStored = true)
public class PromotionTaskEntity extends BaseEntity {

    private static final long serialVersionUID = 4845869724761988449L;

    /**
     * the user creates this task
     */
    @Reference
    private UserEntity author;

    private String title;

    private String description;

    /**
     * this task creator ask other people help to promote his or her post. this postUrl is his or her post's url.
     */
    private String postUrl;

    private Map<String, String> postUrlPreview;

    private Date expiredDate;

    private String shortUrl;

    @Reference
    private TaskTypeEntity taskType;

    public UserEntity getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public TaskTypeEntity getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskTypeEntity taskTypeEntity) {
        this.taskType = taskTypeEntity;
    }

    public Map<String, String> getPostUrlPreview() {
        return postUrlPreview;
    }

    public void setPostUrlPreview(Map<String, String> postUrlPreview) {
        this.postUrlPreview = postUrlPreview;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.getId();
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

}
