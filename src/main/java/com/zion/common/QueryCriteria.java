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
package com.zion.common;

import java.util.List;

import com.zion.task.TaskStatus;

public class QueryCriteria<E> {
    private int resultSize = 20;
    private String nextPageToken;
    private Boolean includeDisable;
    private String sortBy;
    private Boolean ascOrder;
    private String category;
    private String userId;
    private String feedId;
    private String authorId;
    private Class<E> clazz;
    private String registrationStatus;
    private String destId;
    private String commentDestType;
    private String fanId;
    private String likeStatus;
    private Long workCountBaseLine;
    private List<String> tags;
    private List<String> sysTags;
    private List<String> containUserRoles;
    private List<String> containAuthorRoles;
    private String status;
    private TaskStatus taskStatus;
    private String userCountryCode;
    private String taskId;
    private String txToId;
    
    /**
     * this field is not going to be used for DB query directly. It can be used for aggregate logged in user's other attribute by 
     * query loggedInUserId after find results.
     */
    private String loggedInUserId;

    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(int resultSize) {
        this.resultSize = resultSize;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public Boolean getIncludeDisable() {
        return includeDisable;
    }

    public void setIncludeDisable(Boolean includeDisable) {
        this.includeDisable = includeDisable;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getAscOrder() {
        return ascOrder;
    }

    public void setAscOrder(Boolean ascOrder) {
        this.ascOrder = ascOrder;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Class<E> getClazz() {
        return clazz;
    }

    public void setClazz(Class<E> clazz) {
        this.clazz = clazz;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public List<String> getContainUserRoles() {
        return containUserRoles;
    }

    public void setContainUserRoles(List<String> containUserRoles) {
        this.containUserRoles = containUserRoles;
    }

    public String getFanId() {
        return fanId;
    }

    public void setFanId(String fanId) {
        this.fanId = fanId;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public List<String> getContainAuthorRoles() {
        return containAuthorRoles;
    }

    public void setContainAuthorRoles(List<String> containAuthorRoles) {
        this.containAuthorRoles = containAuthorRoles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommentDestType() {
        return commentDestType;
    }

    public void setCommentDestType(String commentDestType) {
        this.commentDestType = commentDestType;
    }

    public List<String> getSysTags() {
        return sysTags;
    }

    public void setSysTags(List<String> sysTags) {
        this.sysTags = sysTags;
    }

    public Long getWorkCountBaseLine() {
        return workCountBaseLine;
    }

    public void setWorkCountBaseLine(Long workCountBaseLine) {
        this.workCountBaseLine = workCountBaseLine;
    }

    public String getUserCountryCode() {
        return userCountryCode;
    }

    public void setUserCountryCode(String userCountryCode) {
        this.userCountryCode = userCountryCode;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTxToId() {
        return txToId;
    }

    public void setTxToId(String txToId) {
        this.txToId = txToId;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
