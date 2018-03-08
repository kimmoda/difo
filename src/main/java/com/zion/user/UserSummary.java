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
package com.zion.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zion.common.RegistrationStatus;
import com.zion.common.UserRole;
import com.zion.rate.RateDto;

@JsonInclude(Include.NON_NULL)
public class UserSummary {

    private Person person;

    private Contact contact;

    private String username;

    private String displayName;

    private String id;

    private String source;

    private long workCount;

    private String location;

    private String introSummary;

    private String serviceSummary;

    private String jwt;

    private String locale;

    private String externalUserId;

    private String registrationStatus;

    private int rate;

    private long rateCount;

    private long fansCount;

    private long shortUrlClickCount;
    
    private long profileShortUrlClickCount;
    
    private long feedLikeCount;
    
    private long feedSharedCount;
    
    private long feedViewedCount;

    private Set<String> userRoles = new HashSet<>();

    private Set<String> preferredStyles = new HashSet<>();

    private RateDto loggedInUserRate;

    private boolean loggedInUserFollowed;

    private boolean mine;
    
    private boolean argeeUploadPhotoCondition;
    
    private String shortUrl;
    
    private boolean uploadLooks;
    
    private Date creationDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Person getPerson() {
        return person;
    }

    public Contact getContact() {
        return contact;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getWorkCount() {
        return workCount;
    }

    public void setWorkCount(long workCount) {
        this.workCount = workCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIntroSummary() {
        return introSummary;
    }

    public void setIntroSummary(String introSummary) {
        this.introSummary = introSummary;
    }

    public String getServiceSummary() {
        return serviceSummary;
    }

    public void setServiceSummary(String serviceSummary) {
        this.serviceSummary = serviceSummary;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<String> getPreferredStyles() {
        return preferredStyles;
    }

    public void setPreferredStyles(Set<String> preferredStyles) {
        this.preferredStyles = preferredStyles;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public long getRateCount() {
        return rateCount;
    }

    public void setRateCount(long rateCount) {
        this.rateCount = rateCount;
    }

    public RateDto getLoggedInUserRate() {
        return loggedInUserRate;
    }

    public void setLoggedInUserRate(RateDto loggedInUserRate) {
        this.loggedInUserRate = loggedInUserRate;
    }

    public long getFansCount() {
        return fansCount;
    }

    public void setFansCount(long fansCount) {
        this.fansCount = fansCount;
    }

    public boolean isLoggedInUserFollowed() {
        return loggedInUserFollowed;
    }

    public void setLoggedInUserFollowed(boolean loggedInUserFollowed) {
        this.loggedInUserFollowed = loggedInUserFollowed;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isAdmin() {
        return this.userRoles.contains(UserRole.ADMIN.name());
    }
    
    public boolean isApprovedTrendsetter() {
        if (this.userRoles.contains(UserRole.INFLUENCER.name()) 
                || this.userRoles.contains(UserRole.DESIGNER.name())
                || this.userRoles.contains(UserRole.STYLIST.name())) {
            return RegistrationStatus.APPROVED.name().equals(this.getRegistrationStatus());
        }
        return false;
    }

    public long getShortUrlClickCount() {
        return shortUrlClickCount;
    }

    public void setShortUrlClickCount(long shortUrlClickCount) {
        this.shortUrlClickCount = shortUrlClickCount;
    }

    public long getFeedLikeCount() {
        return feedLikeCount;
    }

    public void setFeedLikeCount(long feedLikeCount) {
        this.feedLikeCount = feedLikeCount;
    }

    public long getFeedSharedCount() {
        return feedSharedCount;
    }

    public void setFeedSharedCount(long feedSharedCount) {
        this.feedSharedCount = feedSharedCount;
    }

    public long getFeedViewedCount() {
        return feedViewedCount;
    }

    public void setFeedViewedCount(long feedViewedCount) {
        this.feedViewedCount = feedViewedCount;
    }

    public boolean isArgeeUploadPhotoCondition() {
        return argeeUploadPhotoCondition;
    }

    public void setArgeeUploadPhotoCondition(boolean argeeUploadPhotoCondition) {
        this.argeeUploadPhotoCondition = argeeUploadPhotoCondition;
    }

    public long getProfileShortUrlClickCount() {
        return profileShortUrlClickCount;
    }

    public void setProfileShortUrlClickCount(long profileShortUrlClickCount) {
        this.profileShortUrlClickCount = profileShortUrlClickCount;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public boolean isUploadLooks() {
        return uploadLooks;
    }

    public void setUploadLooks(boolean uploadLooks) {
        this.uploadLooks = uploadLooks;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
