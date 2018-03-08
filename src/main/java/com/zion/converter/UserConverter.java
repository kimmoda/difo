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

import com.zion.user.*;
import org.apache.commons.lang3.StringUtils;

import com.zion.common.AuthorUpdatable;
import com.zion.morphia.entity.TagEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.morphia.entity.embed.UserSystemStat;
import com.zion.socialmedia.SocialMediaUserProfile;
import com.zion.tag.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserConverter {

    private ContactConverter contactConverter = new ContactConverter();
    private PersonConverter personConverter = new PersonConverter();
    private ServiceConverter serviceConverter = new ServiceConverter();
    
    public String convertToUserName(SocialMediaUserProfile profile) {
        if (StringUtils.isNotBlank(profile.getEmail())) {
            return profile.getEmail().trim();
        }else if(StringUtils.isNotBlank(profile.getExternalUserId()) && StringUtils.isNotBlank(profile.getSource())) {
            return profile.getExternalUserId().trim() + "@" + profile.getSource();
        }else {
            throw new IllegalArgumentException("Cannot convert to user name with the social media profile");
        }
        
    }

    public User convertToModel(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        Contact contact = contactConverter.convertToModel(userEntity.getContact());
        Person person = personConverter.convertToModel(userEntity.getPerson());
        List<UserService> service = serviceConverter.convertToModel(userEntity.getService());
        User user = new User(userEntity.getUsername(), userEntity.getPassword(), person, contact, service, userEntity.isEnabled(),
                userEntity.getUserRoles(), userEntity.getPreferredStyles());
        user.setId(userEntity.getId());
        user.setWorkCount(userEntity.getWorkCount());
        user.setExternalUserId(userEntity.getExternalUserId());
        user.setLocale(userEntity.getLocale());
        user.setRegistrationStatus(userEntity.getRegistrationStatus());
        if (userEntity.getPerson() != null) {
            String introduction = userEntity.getPerson().getIntroduce();
            if (StringUtils.isNotBlank(introduction)) {
                if (introduction.length() > 300) {
                    String introSummary = introduction.substring(0, 300);
                    user.setIntroSummary(introSummary + "...");
                } else {
                    user.setIntroSummary(introduction);
                }
            }
        }
        StringBuilder locationBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(contact.getCity())) {
            locationBuilder.append(contact.getCity());
        }
        if (StringUtils.isNotBlank(contact.getCity()) && contact.getCountry() != null
                && StringUtils.isNotBlank(contact.getCountry().getValue())) {
            locationBuilder.append(", ");
        }
        if (contact.getCountry() != null && StringUtils.isNotBlank(contact.getCountry().getValue())) {
            locationBuilder.append(contact.getCountry().getValue());
        }
        user.setLocation(locationBuilder.toString());

        List<String> serviceNames = new ArrayList<>();
        if (user.getService() != null && !user.getService().isEmpty()) {
            for (UserService s : user.getService()) {
                serviceNames.add(s.getServiceName());
            }
        }
        if (!serviceNames.isEmpty()) {
            String serviceSummary = String.join("/", serviceNames);
            user.setServiceSummary(serviceSummary);
        }
        if (userEntity.getTotalRate() > 0 && userEntity.getRateCount() > 0) {
            long totalRate = userEntity.getTotalRate();
            long rateCount = userEntity.getRateCount();
            double rateDecimal = (double) totalRate / rateCount;
            user.setRate((int) rateDecimal);
            user.setRateCount(rateCount);
        }
        UserSystemStat systemStat = userEntity.getUserSystemStat();
        user.setShortUrlClickCount(userEntity.getShortUrlClickCount() + systemStat.getShortUrlClickCount());
        user.setFeedLikeCount(userEntity.getFeedLikeCount() + systemStat.getFeedLikeCount());
        user.setFeedViewedCount(userEntity.getFeedViewedCount() + systemStat.getFeedViewedCount());
        user.setFeedSharedCount(userEntity.getFeedSharedCount() + systemStat.getFeedSharedCount());
        user.setProfileShortUrlClickCount(userEntity.getProfileShortUrlClickCount() + systemStat.getProfileShortUrlClickCount());
        user.setFansCount(userEntity.getFansCount() + systemStat.getFansCount());
        user.setDisplayName(UserNameFormatter.formatUserName(person));
        user.setRegistrationSocialMedias(userEntity.getRegistrationSocialMedias());
        user.setArgeeUploadPhotoCondition(userEntity.isArgeeUploadPhotoCondition());
        user.setShortUrl(userEntity.getShortUrl());
        Set<TagEntity> tagEntities = userEntity.getSysTags();
        Set<Tag> tags = new HashSet<>();
        for(TagEntity entity: tagEntities) {
            tags.add(new Tag(entity.getLabel(), entity.getCode(),entity.getCodeSystem(), entity.getSource()));
        }
        user.setTags(tags);
        user.setUploadLooks(userEntity.isUploadLooks());
        user.setRequestedInfo(userEntity.getRequestedInfo());
        user.setCreationDate(userEntity.getCreationDate());
        user.setWalletAddress(userEntity.getWalletAddress());
        return user;
    }

    public User convertToModel(SocialMediaUserProfile profile) {
        if (profile == null) {
            return null;
        }
        Person person = new Person();
        person.setAvatar(profile.getAvatar());
        person.setFirstName(profile.getFirstName());
        person.setLastName(profile.getLastName());
        Contact contact = new Contact();
        String email = profile.getEmail();
        if (StringUtils.isNotBlank(email)) {
            contact.setEmail(email.trim());
        }
        User user = new User();
        String userName = this.convertToUserName(profile);
        user.setUsername(userName);
        user.setContact(contact);
        user.setPerson(person);
        user.setSource(profile.getSource());
        user.setLocale(profile.getLocale());
        user.setExternalUserId(profile.getExternalUserId());

        return user;
    }

    public UserEntity convertToEntity(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user is missing when convert to userEntity");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            throw new IllegalArgumentException("username is missing when convert to userEntity");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername().trim());
        userEntity.setExternalUserId(user.getExternalUserId());
        userEntity.setSource(user.getSource());

        this.updateUserDetails(userEntity, user);
        return userEntity;
    }

    public Author convertToAuthor(User user) {
        Author author = new Author();
        Person person = user.getPerson();
        author.setAvatar(person.getAvatar());
        author.setDisplayName(UserNameFormatter.formatUserName(person));
        author.setUserId(user.getId());
        return author;
    }

    public UserEntity updateUserDetails(UserEntity userEntity, User user) {
        if (user != null) {
            userEntity.setLocale(user.getLocale());
            userEntity.setPreferredStyles(user.getPreferredStyles());
            userEntity.setContact(contactConverter.convertToEntity(user.getContact()));
            userEntity.setPerson(personConverter.convertToEntity(user.getPerson()));
            userEntity.setService(serviceConverter.convertToEntity(user.getService()));
            userEntity.setWalletAddress(user.getWalletAddress());
            return userEntity;
        } else {
            throw new IllegalArgumentException("user is missing when convert to userEntity");
        }
    }

    public <T extends AuthorUpdatable> void dynaUpdateAuthor(List<T> authorDtos, List<UserEntity> userEntities) {
        UserConverter userConverter = new UserConverter();
        Map<String, UserEntity> userMap = new HashMap<>();
        for (UserEntity userEntity : userEntities) {
            userMap.put(userEntity.getId(), userEntity);
        }
        for (AuthorUpdatable authorUpdatable : authorDtos) {
            String id = authorUpdatable.getAuthor().getUserId();
            UserEntity userEntity = userMap.get(id);
            if (userEntity != null) {
                Author author = new Author(userConverter.convertToModel(userEntity));
                authorUpdatable.setAuthor(author);
            }
        }
    }
}