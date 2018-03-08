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
package com.zion.registration.service;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.action.pub.CampaignActionBean;
import com.zion.action.pub.FaqActionBean;
import com.zion.action.pub.InfluencerApplyActionBean;
import com.zion.common.AppConfig;
import com.zion.common.RegistrationStatus;
import com.zion.common.RejectRegistrationDto;
import com.zion.common.UserRole;
import com.zion.job.schedule.EmailSchedualJob;
import com.zion.job.schedule.JobIdentifier;
import com.zion.job.schedule.JobTriggerService;
import com.zion.mail.EmailContentLoadException;
import com.zion.mail.EmailContentModel;
import com.zion.mail.EmailContentType;
import com.zion.mail.EmailPart;
import com.zion.mail.EmailPartDto;
import com.zion.mail.RejectTrendsetterEmailContent;
import com.zion.mail.StylistApprovedEmailContent;
import com.zion.mail.WelcomeUserEmailContent;
import com.zion.mail.service.ApproveStylistEmailContentFactory;
import com.zion.mail.service.BecomeStylistEmailContentFactory;
import com.zion.mail.service.FirstLookEmailContentFactory;
import com.zion.mail.service.RejectTrendsetterEmailContentFactory;
import com.zion.mail.service.WelcomeUserEmailContentFactory;
import com.zion.registration.IllegalUserStatusChangeException;
import com.zion.registration.UserRegistrationDto;
import com.zion.user.ApproveUserDto;
import com.zion.user.Contact;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;
import com.zion.user.service.UserService;

public class StylistRegistrationServiceImpl implements StylistRegistrationService {
    private UserService userService;
    private JobTriggerService jobTriggerSerivce;
    private BecomeStylistEmailContentFactory becomeStylistEmailContentFactory;
    private ApproveStylistEmailContentFactory approveStylistEmailContentFactory;
    private WelcomeUserEmailContentFactory welcomeUserEmailContentFactory;
    private RejectTrendsetterEmailContentFactory rejectTrendsetterEmailContentFactory;
    private FirstLookEmailContentFactory firstLookEmailContentFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(StylistRegistrationServiceImpl.class);

    @Inject
    public StylistRegistrationServiceImpl(UserService userService, JobTriggerService jobTriggerSerivce,
            BecomeStylistEmailContentFactory becomeStylistEmailContentFactory,
            ApproveStylistEmailContentFactory approveStylistEmailContentFactory,
            WelcomeUserEmailContentFactory welcomeUserEmailContentFactory,
            RejectTrendsetterEmailContentFactory rejectTrendsetterEmailContentFactory,
            FirstLookEmailContentFactory firstLookEmailContentFactory) {
        this.userService = userService;
        this.jobTriggerSerivce = jobTriggerSerivce;
        this.becomeStylistEmailContentFactory = becomeStylistEmailContentFactory;
        this.approveStylistEmailContentFactory = approveStylistEmailContentFactory;
        this.welcomeUserEmailContentFactory = welcomeUserEmailContentFactory;
        this.rejectTrendsetterEmailContentFactory = rejectTrendsetterEmailContentFactory;
        this.firstLookEmailContentFactory = firstLookEmailContentFactory;
    }

    @Override
    public void sendWelcomeEmail(User user) {
        if (!hasEmail(user)) {
            return;
        }
        try {
            String appHostUrl = AppConfig.getInstance().getAppHost();
            String applyUrl = appHostUrl + InfluencerApplyActionBean.URL;
            String campaignUrl = appHostUrl + CampaignActionBean.URL;
            WelcomeUserEmailContent model = this.welcomeUserEmailContentFactory.createModel(user.getDisplayName(), campaignUrl, applyUrl);
            this.welcomeUserEmailContentFactory.processContent(model);
            String subject = this.welcomeUserEmailContentFactory.getEmailContents().get(EmailContentType.SUBJECT);
            String body = this.welcomeUserEmailContentFactory.getEmailContents().get(EmailContentType.HTML);
            JobDetail jobDetail = this.buildJobDetail(EmailSchedualJob.class,
                    new EmailPartDto(user.getContact().getEmail(), subject, body));
            this.jobTriggerSerivce.startJob(jobDetail);
        } catch (Exception e) {
            // TODO: AWS may fail sometimes based on my past experience. So we need to create a reschedule task and re-send the email.
            LOGGER.warn(String.format("Fail to send email '%s' to user '%s' when login first time",
                    user.getContact().getEmail(), user.getId()));
        }
    }

    @Override
    public User register(UserRegistrationDto userRegistration) throws UserNotFoundException {
        String userId = userRegistration.getUserId();
        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("Cannot register the user as missing user id.");
        }

        User registeredUser = userService.registerTrendsetter(userRegistration);
        if (registeredUser != null) {
            // Assume user want to be notified by an email from registration UI
            Contact contact = registeredUser.getContact() != null ? registeredUser.getContact() : new Contact();
            contact.setEmail(userRegistration.getContactEmail());
            registeredUser.setContact(contact);
            sendEmailAfterRegister(registeredUser);
            return registeredUser;
        } else {
            return null;
        }
    }

    private void sendEmailAfterRegister(User user) {
        if (!hasEmail(user)) {
            return;
        }
        EmailContentModel model = this.becomeStylistEmailContentFactory.createModel(user.getDisplayName());
        try {
            this.becomeStylistEmailContentFactory.processContent(model);
        } catch (EmailContentLoadException e) {
            LOGGER.warn("Fail to load email template file.");
        }
        String subject = this.becomeStylistEmailContentFactory.getEmailContents().get(EmailContentType.SUBJECT);
        String body = this.becomeStylistEmailContentFactory.getEmailContents().get(EmailContentType.HTML);
        try {
            JobDetail jobDetail = this.buildJobDetail(EmailSchedualJob.class,
                    new EmailPartDto(user.getContact().getEmail(), subject, body));
            this.jobTriggerSerivce.startJob(jobDetail);
        } catch (Exception e) {
            // TODO: AWS may fail sometimes based on my past experience. So we need to create a reschedule task and re-send the email.
            LOGGER.warn(String.format("Fail to send email '%s' to user '%s' when register to become a stylist",
                    user.getContact().getEmail(), user.getId()));
        }
    }

    @Override
    public User approve(ApproveUserDto approveUserDto) throws UserNotFoundException, IllegalUserStatusChangeException {
        User updatedUser = updateUserStatus(approveUserDto.getUserId(), RegistrationStatus.PENDING, RegistrationStatus.APPROVED);
        this.sendEmailAfterApprove(updatedUser, approveUserDto.getRedirectUrl());
        return updatedUser;
    }

    private void sendEmailAfterApprove(User user, String redirectUrl) {
        if (!hasEmail(user)) {
            return;
        }
        try {
            StylistApprovedEmailContent model = this.approveStylistEmailContentFactory.createModel(user.getDisplayName(), redirectUrl);
            this.approveStylistEmailContentFactory.processContent(model);
            String subject = this.approveStylistEmailContentFactory.getEmailContents().get(EmailContentType.SUBJECT);
            String body = this.approveStylistEmailContentFactory.getEmailContents().get(EmailContentType.HTML);
            JobDetail jobDetail = this.buildJobDetail(EmailSchedualJob.class,
                    new EmailPartDto(user.getContact().getEmail(), subject, body));
            this.jobTriggerSerivce.startJob(jobDetail);
        } catch (Exception e) {
            // TODO: AWS may fail sometimes based on my past experience. So we need to create a reschedule task and re-send the email.
            LOGGER.warn(String.format("Fail to send email '%s' to user '%s' when register to become a stylist",
                    user.getContact().getEmail(), user.getId()));
        }
    }

    private boolean hasEmail(User user) {
        boolean hasEmail =  user.getContact() != null &&StringUtils.isNotBlank(user.getContact().getEmail());
        if (!hasEmail) {
            LOGGER.warn(String.format("Fail to send email to user '%s' as missing email", user.getId()));
            return false;
        }else {
            return true;
        }
    }

    private User updateUserStatus(String userId, RegistrationStatus from, RegistrationStatus to)
            throws UserNotFoundException, IllegalUserStatusChangeException {
        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("Cannot update the user status as missing user id.");
        }
        User user = userService.getUserById(userId);

        if (!from.name().equals(user.getRegistrationStatus())) {
            String msg = String.format("Cannot change user '%s' from '%s' status to '%s' as user status is not '%s'",
                    userId, from, to, from);
            throw new IllegalUserStatusChangeException(msg);
        }
        return this.userService.updateRegistrationStatus(userId, to);
    }

    private JobDetail buildJobDetail(Class<? extends Job> jobClass, EmailPartDto emailPartDto) {
        JobDataMap newJobDataMap = new JobDataMap();
        if (StringUtils.isNotBlank(emailPartDto.getEmailFrom())) {
            newJobDataMap.put(EmailPart.FRROM.name(), emailPartDto.getEmailFrom());
        }
        newJobDataMap.put(EmailPart.EMAIL_TO.name(), emailPartDto.getEmailTo());
        newJobDataMap.put(EmailPart.SUBJECT.name(), emailPartDto.getSubject());
        newJobDataMap.put(EmailPart.BODY.name(), emailPartDto.getBody());
        return JobBuilder.newJob(jobClass).withIdentity(JobIdentifier.SEND_EMAIL.getJobKey()).setJobData(newJobDataMap).build();
    }

    @Override
    public User reject(RejectRegistrationDto dto) throws UserNotFoundException {
        String userId = dto.getRejectedUserId();
        User user = userService.getUserById(userId);
        Set<String> userRoles = user.getUserRoles();
        Set<String> newRoles = new HashSet<>();
        if (userRoles.contains(UserRole.ADMIN.name())) {
            newRoles.add(UserRole.ADMIN.name());
        }
        if (userRoles.contains(UserRole.SYSADMIN.name())) {
            newRoles.add(UserRole.SYSADMIN.name());
        }
        userService.updateUserRoles(user.getUsername(), newRoles);
        user = userService.rejectToBeTrendsetter(dto);
        if (user != null) {
            this.sendEmailAfterReject(user, dto.getRequestedInfo());
        }
        return user;
    }

    private void sendEmailAfterReject(User user, String requestInfo) {
        if (!hasEmail(user)) {
            return;
        }
        try {
            AppConfig appConfig = AppConfig.getInstance();
            String appHostUrl = appConfig.getAppHost();
            String faqUrl = appHostUrl + FaqActionBean.URL;
            RejectTrendsetterEmailContent model = this.rejectTrendsetterEmailContentFactory.createModel(user.getDisplayName(), faqUrl,
                    requestInfo);
            this.rejectTrendsetterEmailContentFactory.processContent(model);
            String subject = this.rejectTrendsetterEmailContentFactory.getEmailContents().get(EmailContentType.SUBJECT);
            String body = this.rejectTrendsetterEmailContentFactory.getEmailContents().get(EmailContentType.HTML);
            JobDetail jobDetail = this.buildJobDetail(EmailSchedualJob.class,
                    new EmailPartDto(appConfig.getSesConfiguration().getSupport(), user.getContact().getEmail(), subject, body));
            this.jobTriggerSerivce.startJob(jobDetail);
        } catch (Exception e) {
            // TODO: AWS may fail sometimes based on my past experience. So we need to create a reschedule task and re-send the email.
            LOGGER.warn(String.format("Fail to send email '%s' to user '%s' when reject user to be trendsetter. reason: '%d'",
                    user.getContact().getEmail(), user.getId()), e.getMessage());
        }
    }

    @Override
    public void sendEmailAfterFirstLook(User user) {
        if (!hasEmail(user)) {
            return;
        }
        try {
            EmailContentModel model = this.firstLookEmailContentFactory.createModel(user.getDisplayName());
            this.firstLookEmailContentFactory.processContent(model);
            String subject = this.firstLookEmailContentFactory.getEmailContents().get(EmailContentType.SUBJECT);
            String body = this.firstLookEmailContentFactory.getEmailContents().get(EmailContentType.HTML);
            JobDetail jobDetail = this.buildJobDetail(EmailSchedualJob.class,
                    new EmailPartDto(AppConfig.getInstance().getSesConfiguration().getFromAdmin(), user.getContact().getEmail(), subject,
                            body));
            this.jobTriggerSerivce.startJob(jobDetail);
        } catch (Exception e) {
            // TODO: AWS may fail sometimes based on my past experience. So we need to create a reschedule task and re-send the email.
            LOGGER.warn(String.format("Fail to send email '%s' to user '%s' when login first time",
                    user.getContact().getEmail(), user.getId()));
        }
    }
}
