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

import com.zion.morphia.entity.embed.ContactEntity;
import com.zion.morphia.entity.embed.PersonEntity;
import com.zion.morphia.entity.embed.UserServiceEntity;
import com.zion.morphia.entity.embed.UserSystemStat;

import org.mongodb.morphia.annotations.*;

import java.util.*;

@Entity(value = "zion_user", noClassnameStored = true)
@Indexes({ @Index(fields = @Field("username"), options = @IndexOptions(name = "idx_username", unique = true)) })
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = -186536936887839986L;

    private String username;

    private String password;

    /**
     * using for password encryption, this is immutable.
     */
    private String uencrypt;

    private Set<String> userRoles = new HashSet<>();

    @Embedded
    private PersonEntity person = new PersonEntity();

    @Embedded
    private ContactEntity contact = new ContactEntity();

    @Embedded
    private List<UserServiceEntity> service = new ArrayList<>();

    private Set<String> preferredStyles = new HashSet<>();

    private long workCount;

    private int likeCount;

    private long fansCount;

    private String preferName;

    private String locale;

    private String externalUserId;

    private String registrationStatus;
    
    private String registrationSocialMedias;

    private long totalRate;

    private long rateCount;

    private long shortUrlClickCount;

    private long feedLikeCount;

    private long feedSharedCount;

    private long feedViewedCount;
    
    @Embedded
    private UserSystemStat userSystemStat = new UserSystemStat();
    
    private boolean argeeUploadPhotoCondition;
    
    private String source;
    
    private String shortUrl;
    
    private String walletAddress;
    
    private long profileShortUrlClickCount;
    
    /**
     * Super admin update these tags which used for system configuration eg: recommend the user on the top of page
     */
    @Embedded
    private Set<TagEntity> sysTags = new HashSet<>();
    
    /**
     * whether or not the user has uploaded a look, checking this flag for send a email when user upload the first look.
     */
    private boolean uploadLooks;
    
    /**
     * additional information or reject reason which requested when process use registration process.
     */
    private String requestedInfo;

    @Override
    public String toString() {
        return this.username;
    }

    public UserEntity() {
        this.uencrypt = UUID.randomUUID().toString();
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public ContactEntity getContact() {
        return contact;
    }

    public void setContact(ContactEntity contact) {
        this.contact = contact;
    }

    public List<UserServiceEntity> getService() {
        return service;
    }

    public void setService(ArrayList<UserServiceEntity> service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUencrypt() {
        return uencrypt;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }

    public long getWorkCount() {
        return workCount;
    }

    public void setWorkCount(long workCount) {
        this.workCount = workCount;
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

    public long getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(long totalRate) {
        this.totalRate = totalRate;
    }

    public long getRateCount() {
        return rateCount;
    }

    public void setRateCount(long rateCount) {
        this.rateCount = rateCount;
    }

    public Set<String> getPreferredStyles() {
        return preferredStyles;
    }

    public void setPreferredStyles(Set<String> preferredStyles) {
        this.preferredStyles = preferredStyles;
    }

    public String getPreferName() {
        return preferName;
    }

    public void setPreferName(String preferName) {
        this.preferName = preferName;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public long getFansCount() {
        return fansCount;
    }

    public void setFansCount(long fansCount) {
        this.fansCount = fansCount;
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

    public String getRegistrationSocialMedias() {
        return registrationSocialMedias;
    }

    public void setRegistrationSocialMedias(String registrationSocialMedias) {
        this.registrationSocialMedias = registrationSocialMedias;
    }

    public boolean isArgeeUploadPhotoCondition() {
        return argeeUploadPhotoCondition;
    }

    public void setArgeeUploadPhotoCondition(boolean argeeUploadPhotoCondition) {
        this.argeeUploadPhotoCondition = argeeUploadPhotoCondition;
    }

    public UserSystemStat getUserSystemStat() {
        return userSystemStat;
    }

    public void setUserSystemStat(UserSystemStat userSystemStat) {
        this.userSystemStat = userSystemStat;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public long getProfileShortUrlClickCount() {
        return profileShortUrlClickCount;
    }

    public void setProfileShortUrlClickCount(long profileShortUrlClickCount) {
        this.profileShortUrlClickCount = profileShortUrlClickCount;
    }

    public Set<TagEntity> getSysTags() {
        return sysTags;
    }

    public void setSysTags(Set<TagEntity> sysTags) {
        this.sysTags = sysTags;
    }

    public boolean isUploadLooks() {
        return uploadLooks;
    }

    public void setUploadLooks(boolean uploadLooks) {
        this.uploadLooks = uploadLooks;
    }

    public String getRequestedInfo() {
        return requestedInfo;
    }

    public void setRequestedInfo(String requestedInfo) {
        this.requestedInfo = requestedInfo;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}
