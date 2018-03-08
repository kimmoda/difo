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
package com.zion.user.service;

import com.zion.auth.AuthToken;
import com.zion.auth.EncryptService;
import com.zion.auth.InvalidAuthToken;
import com.zion.common.AppConfig;
import com.zion.common.AuthorUpdatable;
import com.zion.common.CodeSet;
import com.zion.common.DtoListWrapper;
import com.zion.common.EmptyEmailException;
import com.zion.common.QueryCriteria;
import com.zion.common.RegistrationStatus;
import com.zion.common.RejectRegistrationDto;
import com.zion.common.TagCodeSystem;
import com.zion.common.TagSourceType;
import com.zion.common.UserRole;
import com.zion.common.UsernameChangeDto;
import com.zion.converter.ContactConverter;
import com.zion.converter.UserConverter;
import com.zion.job.schedule.JobIdentifier;
import com.zion.job.schedule.JobScheduleContextKey;
import com.zion.job.schedule.JobTriggerService;
import com.zion.job.schedule.UpdateFeedAuthorRolesJob;
import com.zion.mongo.db.repository.EntityListWrapper;
import com.zion.mongo.db.repository.TagRepository;
import com.zion.mongo.db.repository.UserRepository;
import com.zion.morphia.entity.TagEntity;
import com.zion.morphia.entity.UserEntity;
import com.zion.morphia.entity.embed.ContactEntity;
import com.zion.registration.UserRegistrationDto;
import com.zion.registration.service.StylistRegistrationService;
import com.zion.socialmedia.FacebookUserService;
import com.zion.socialmedia.GoogleUserService;
import com.zion.socialmedia.SocialMediaAuthException;
import com.zion.socialmedia.SocialMediaUserProfile;
import com.zion.socialmedia.SocialSource;
import com.zion.tag.Tag;
import com.zion.user.User;
import com.zion.user.UserAuthDto;
import com.zion.user.UserTagDto;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private TagRepository tagRepository;
    private UserConverter userConverter = new UserConverter();
    private EncryptService encryptService;
    private FacebookUserService facebookService;
    private GoogleUserService googleUserService;
    private JobTriggerService jobTriggerSerivce;
    private StylistRegistrationService regService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    public UserServiceImpl(UserRepository repo, TagRepository tagRepository, EncryptService encryptService,
            FacebookUserService facebookService,
            GoogleUserService googleUserService, JobTriggerService jobTriggerSerivce, StylistRegistrationService regService) {
        this.userRepository = repo;
        this.tagRepository = tagRepository;
        this.encryptService = encryptService;
        this.facebookService = facebookService;
        this.googleUserService = googleUserService;
        this.jobTriggerSerivce = jobTriggerSerivce;
        this.regService = regService;
    }

    @Override
    public List<User> listNoAdminUsers(Boolean includeDisabled) {
        List<UserEntity> entities = this.userRepository.getNonAdminUsers(includeDisabled);
        return entities.stream().map(e -> userConverter.convertToModel(e)).collect(Collectors.toList());
    }

    @Override
    public List<User> listAllUsers() {
        List<UserEntity> entities = this.userRepository.listAllUsers();
        return entities.stream().map(e -> userConverter.convertToModel(e)).collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) throws DuplicatedUserException {
        return this.createUser(user, true);
    }

    @Override
    public User createUserWithoutPassword(User user) throws DuplicatedUserException {
        return this.createUser(user, false);
    }

    private User createUser(User user, boolean requiredPassword) throws DuplicatedUserException {
        Validate.notNull(user);
        Validate.notBlank(user.getUsername());
        if (requiredPassword) {
            Validate.notBlank(user.getPassword());
        }
        if (this.userRepository.getUserByUsername(user.getUsername()) != null) {
            throw new DuplicatedUserException("User exists with username: " + user.getUsername());
        }
        UserEntity entity = this.userConverter.convertToEntity(user);
        if (requiredPassword) {
            updatePassword(user.getPassword(), entity);
        }
        if (entity.getUserRoles() == null || entity.getUserRoles().isEmpty()) {
            Set<String> roles = new HashSet<>();
            roles.add(UserRole.DEFAULT.name());
            entity.setUserRoles(roles);
        }
        UserEntity persistent = this.userRepository.createUser(entity);
        return this.userConverter.convertToModel(persistent);
    }

    private void updatePassword(String newPassowrd, UserEntity entity) {
        String encryptedPassword = this.encryptService.encrypt(entity.getUencrypt(), newPassowrd);
        entity.setPassword(encryptedPassword);
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        Validate.notNull(user);
        Validate.notBlank(user.getUsername());
        UserEntity entity = this.getUserEntity(user.getUsername());
        UserEntity persistent = this.userRepository.updateUser(this.userConverter.updateUserDetails(entity, user));
        return this.userConverter.convertToModel(persistent);
    }

    @Override
    public User updateUserPassword(UserAuthDto dto, String newPassword) throws UserNotFoundException, InvalidPasswordException {
        UserEntity entity = this.getUserEntity(dto.getUsername());
        if (!entity.getPassword().equals(this.encryptService.encrypt(entity.getUencrypt(), dto.getPassword()))) {
            throw new InvalidPasswordException("cannot update user password due to old password not corrected.");
        }
        String newEncryptedPassword = this.encryptService.encrypt(entity.getUencrypt(), newPassword);
        entity.setPassword(newEncryptedPassword);
        UserEntity persistent = this.userRepository.updateUser(entity);
        return this.userConverter.convertToModel(persistent);
    }

    @Override
    public User disableUser(String username) throws UserNotFoundException {
        return this.updateUserStatus(username, false);
    }

    @Override
    public User enableUser(String username) throws UserNotFoundException {
        return updateUserStatus(username, true);
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        UserEntity entity = getUserEntity(username);
        return this.userConverter.convertToModel(entity);
    }

    @Override
    public SocialMediaUserProfile getSocialMediaUserProfile(AuthToken authToken)
            throws InvalidAuthToken, SocialMediaAuthException, EmptyEmailException {
        SocialMediaUserProfile userProfile;
        SocialSource socialSource = SocialSource.valueOf(authToken.getSource());
        switch (socialSource) {
            case GOOGLE:
                userProfile = this.googleUserService.getSocialMediaUserProfile(authToken);
                break;
            case FACEBOOK:
                userProfile = this.facebookService.getSocialMediaUserProfile(authToken.getAuthToken());
                break;
            default:
                throw new IllegalArgumentException("cannot support user source from " + socialSource);
        }
        return userProfile;
    }

    @Override
    public User getValidUser(UserAuthDto dto) {
        if (dto == null || StringUtils.isBlank(dto.getPassword()) || StringUtils.isBlank(dto.getUsername())) {
            return null;
        }
        UserEntity entity = this.userRepository.getUserByUsername(dto.getUsername().trim());
        if (entity == null) {
            return null;
        }
        String encryptedPassword = this.encryptService.encrypt(entity.getUencrypt(), dto.getPassword());
        if (!encryptedPassword.equals(entity.getPassword())) {
            return null;
        }
        return this.userConverter.convertToModel(entity);
    }

    @Override
    public User updateUserRoles(String username, Set<String> roles) throws UserNotFoundException {
        UserEntity entity = this.getUserEntity(username);
        if (roles == null || roles.isEmpty()) {
            Set<String> defaultRole = new HashSet<String>();
            defaultRole.add(UserRole.DEFAULT.name());
            entity.setUserRoles(defaultRole);
        } else {
            entity.setUserRoles(roles);
        }
        UserEntity persistent = this.userRepository.updateUser(entity);
        if (persistent != null) {
            JobDetail job = buildJobDetail(persistent.getId());
            try {
                this.jobTriggerSerivce.startJob(job);
            } catch (SchedulerException e) {
                LOGGER.warn(e.getMessage());
            }
        }
        return this.userConverter.convertToModel(persistent);
    }

    private JobDetail buildJobDetail(String feedAuthorId) {
        JobDetail detail = JobBuilder.newJob(UpdateFeedAuthorRolesJob.class).withIdentity(JobIdentifier.UPDATE_DATA.getJobKey())
                .setJobData(new JobDataMap()).build();
        detail.getJobDataMap().put(JobScheduleContextKey.FEED_AUTHOR_ID.name(), feedAuthorId);
        return detail;
    }

    @Override
    public User updateRegistrationStatus(String userId, RegistrationStatus status) throws UserNotFoundException {
        return this.updateRegistrationStatus(userId, status, null);
    }

    private User updateRegistrationStatus(String userId, RegistrationStatus status, String info) throws UserNotFoundException {
        UserEntity entity = this.userRepository.getUser(userId);
        if (entity == null) {
            throw new UserNotFoundException(
                    String.format("cannot with user with id: '%s' when updating registration status '%s' ", userId, status));
        }
        if (status != null) {
            entity.setRegistrationStatus(status.name());
        } else {
            entity.setRegistrationStatus(null);
        }

        if (StringUtils.isNotBlank(info)) {
            entity.setRequestedInfo(info);
        }
        UserEntity updatedUser = this.userRepository.updateUser(entity);
        return this.userConverter.convertToModel(updatedUser);
    }

    @Override
    public User rejectToBeTrendsetter(RejectRegistrationDto dto) throws UserNotFoundException {
        return this.updateRegistrationStatus(dto.getRejectedUserId(), RegistrationStatus.DEFAULT, dto.getRequestedInfo());
    }

    @Override
    public void setDefaultPassword(String username) throws UserNotFoundException {
        UserEntity entity = this.getUserEntity(username);
        if (StringUtils.isBlank(entity.getPassword())) {
            String newEncryptedPassword = this.encryptService.encrypt(entity.getUencrypt(), AppConfig.getInstance().getDefaultPassword());
            entity.setPassword(newEncryptedPassword);
            this.userRepository.updateUser(entity);
        }
    }

    private User updateUserStatus(String username, boolean enabled) throws UserNotFoundException {
        UserEntity entity = this.getUserEntity(username);
        entity.setEnabled(enabled);
        UserEntity persistent = this.userRepository.updateUser(entity);
        return this.userConverter.convertToModel(persistent);
    }

    private UserEntity getUserEntity(String username) throws UserNotFoundException {
        Validate.notBlank(username);
        UserEntity entity = this.userRepository.getUserByUsername(username.trim());
        if (entity == null) {
            throw new UserNotFoundException(String.format("user '%s' cannot be found in DB", username));
        }
        return entity;
    }

    @Override
    public User getUserById(String id) throws UserNotFoundException {
        Validate.notBlank(id);
        UserEntity entity = this.userRepository.getUser(id);
        if (entity == null) {
            throw new UserNotFoundException(String.format("user '%s' cannot be found in DB", id));
        }
        return this.userConverter.convertToModel(entity);
    }

    @Override
    public User increaseWorkCount(String id) {
        Validate.notBlank(id);
        UserEntity entity = this.userRepository.increaseWorkCount(id);
        return this.userConverter.convertToModel(entity);
    }

    @Override
    public User decreaseWorkCount(String id) {
        Validate.notBlank(id);
        UserEntity entity = this.userRepository.decreaseWorkCount(id);
        return this.userConverter.convertToModel(entity);
    }

    @Override
    public DtoListWrapper<User> list(QueryCriteria<UserEntity> criteria) {
        EntityListWrapper<UserEntity> entityWrapper = this.userRepository.list(criteria);
        DtoListWrapper<User> wrapper = new DtoListWrapper<>();
        List<UserEntity> entities = entityWrapper.getEntities();
        List<User> users = entities.stream().map(f -> this.userConverter.convertToModel(f)).collect(Collectors.toList());
        wrapper.setDtos(users);
        wrapper.setNextPageToken(entityWrapper.getNextPageToken());
        return wrapper;
    }

    @Override
    public <T extends AuthorUpdatable> void dynaUpdateAuthor(List<T> authorUpdatableList) {
        if (!authorUpdatableList.isEmpty()) {
            List<String> userIds = authorUpdatableList.stream().map(authorUpdatable -> authorUpdatable.getAuthor().getUserId())
                    .collect(Collectors.toList());
            List<UserEntity> userEntities = this.userRepository.getUsers(userIds);
            userConverter.dynaUpdateAuthor(authorUpdatableList, userEntities);
        }
    }

    @Override
    public User updateUserName(UsernameChangeDto userNameChangeDto) {
        if (StringUtils.isBlank(userNameChangeDto.getOldName())) {
            throw new IllegalArgumentException(String.format("cannot find old user name '%s'", userNameChangeDto.getOldName()));
        }
        if (StringUtils.isBlank(userNameChangeDto.getNewName())) {
            throw new IllegalArgumentException(String.format("cannot find new user name '%s'", userNameChangeDto.getNewName()));
        }
        UserEntity newUser = this.userRepository.getUserByUsername(userNameChangeDto.getNewName());
        UserEntity oldUser = this.userRepository.getUserByUsername(userNameChangeDto.getOldName());
        if (newUser != null) {
            throw new IllegalArgumentException(String.format("Cannot update user name to an new name '%s' as this name has been taken.",
                    userNameChangeDto.getNewName()));
        }
        if (oldUser == null) {
            throw new IllegalArgumentException(
                    String.format("Cannot update user name '%s' as this user cannot be found", userNameChangeDto.getOldName()));
        }
        oldUser.setUsername(userNameChangeDto.getNewName());
        UserEntity updatedUser = this.userRepository.updateUser(oldUser);
        return this.userConverter.convertToModel(updatedUser);
    }

    @Override
    public User increaseFeedViewedCount(String id) {
        Validate.notBlank(id);

        UserEntity userEntity = this.userRepository.getUser(id);
        if (userEntity != null) {
            long count = userEntity.getFeedViewedCount() + 1;
            userEntity.setFeedViewedCount(count);
            try {
                UserEntity updateUser = this.userRepository.updateUser(userEntity);
                return userConverter.convertToModel(updateUser);
            } catch (Exception e) {
                // It may fail as the concurrent user to view the feed.
                LOGGER.warn(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public User registerTrendsetter(UserRegistrationDto reg) {
        Validate.notBlank(reg.getUserId());
        Validate.notBlank(reg.getSocialMediaUrls());
        UserEntity userEntity = this.userRepository.getUser(reg.getUserId());
        if (userEntity != null) {
            userEntity.setRegistrationSocialMedias(reg.getSocialMediaUrls() + " contact email:" + reg.getContactEmail());
            userEntity.setRegistrationStatus(RegistrationStatus.PENDING.name());
            // Give default role is influencer and admin can decide which role to give the user when approve this user.
            userEntity.setUserRoles(new HashSet<String>(Arrays.asList(UserRole.INFLUENCER.name())));
            ContactEntity contact = userEntity.getContact() != null ? userEntity.getContact() : new ContactEntity();
            if (StringUtils.isBlank(contact.getEmail())) {
                contact.setEmail(reg.getContactEmail());
            }
            userEntity.setContact(contact);
            UserEntity updatedEntity = this.userRepository.updateUser(userEntity);
            return userConverter.convertToModel(updatedEntity);
        } else {
            return null;
        }
    }

    @Override
    public User agreeUploadPhotoCondition(String id) {
        Validate.notBlank(id);
        UserEntity entity = this.userRepository.agreeUploadPhotoCondition(id);
        return entity != null ? userConverter.convertToModel(entity) : null;
    }

    @Override
    public User increaseTrackingCount(String id) {
        UserEntity userEntity = this.userRepository.getUser(id);
        if (userEntity != null) {
            long clickingcreased = userEntity.getProfileShortUrlClickCount() + 1;
            userEntity.setProfileShortUrlClickCount(clickingcreased);
            try {
                userEntity = this.userRepository.updateUser(userEntity);
            } catch (Exception e) {
                // It may fail as concurrent users updating user.
                LOGGER.warn(e.getMessage());
            }

            return userConverter.convertToModel(userEntity);
        } else {
            return null;
        }
    }

    @Override
    public User updateUserTags(UserTagDto dto) {
        UserEntity userEntity = this.userRepository.getUser(dto.getUserId());
        if (userEntity != null) {
            Set<TagEntity> tagEntities = new HashSet<>();
            for (Tag tag : dto.getTags()) {
                TagCodeSystem codeSystem = TagCodeSystem.findTagCodeSystemByCode(tag.getCodeSystem());
                TagSourceType source = TagSourceType.findTagSourceTypeByCode(tag.getSource());
                if (StringUtils.isNotBlank(tag.getCode()) && codeSystem != null && source != null) {
                    TagEntity entity = this.tagRepository.findTag(tag.getCode(), codeSystem, source);
                    if (entity != null) {
                        tagEntities.add(entity);
                    }
                }
            }
            userEntity.setSysTags(tagEntities);
            userEntity = this.userRepository.updateUser(userEntity);
            return userConverter.convertToModel(userEntity);
        } else {
            return null;
        }
    }

    @Override
    public User setFirstLookStatus(String id) {
        UserEntity entity = this.userRepository.setFirstLookStatus(id);
        User user = userConverter.convertToModel(entity);
        this.regService.sendEmailAfterFirstLook(user);
        return user;
    }

    @Override
    public List<CodeSet> getUserCountries() {
        ContactConverter contactConverter = new ContactConverter();
        List<String> countryCodes = this.userRepository.getUserCountries();
        List<CodeSet> countryCodeSets = new ArrayList<>();
        for (String code : countryCodes) {
            CodeSet codeSet = contactConverter.converterToContryCodeSet(code);
            if (codeSet != null && !countryCodeSets.contains(codeSet)) {
                countryCodeSets.add(codeSet);
            }
        }

        countryCodeSets.sort(new Comparator<CodeSet>() {
            @Override
            public int compare(CodeSet o1, CodeSet o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        return countryCodeSets;
    }

}
