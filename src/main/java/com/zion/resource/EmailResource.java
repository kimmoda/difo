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
package com.zion.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.auth.InvalidJwtToken;
import com.zion.common.AppConfig;
import com.zion.common.Payload;
import com.zion.job.schedule.EmailSchedualJob;
import com.zion.job.schedule.JobIdentifier;
import com.zion.job.schedule.JobTriggerService;
import com.zion.mail.EmailPart;
import com.zion.mail.EmailPartDto;
import com.zion.permission.PermissionService;
import com.zion.user.User;
import com.zion.user.service.UserNotFoundException;

@Path("/email")
public class EmailResource extends BaseResource{
    private PermissionService permissionService;
    private JobTriggerService jobTriggerSerivce;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailResource.class);

    @Inject
    public EmailResource(PermissionService permissionService,  JobTriggerService jobTriggerSerivce) {
        this.permissionService = permissionService;
        this.jobTriggerSerivce = jobTriggerSerivce;
    }
    
    @POST
    @Path("/v1/feedback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Payload<String> feedback(@HeaderParam(PARAM_JWT_TOKEN) String token, String feedback) {
        Payload<String> payload = new Payload<>();
        try {
            User user = this.permissionService.getUser(token);
            if(user == null || !user.isEnabled()) {
                payload.setMsg("Cannot send feedback as user is not valid");
                payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return payload;
            }else {
                String supportEmail = AppConfig.getInstance().getSesConfiguration().getSupport();
                String userName = user.getUsername();
                String userId = user.getId();
                StringBuilder builder = new StringBuilder();
                builder.append("User name: " + userName);
                builder.append(" userId: " + userId);
                builder.append(" Feedback Message: " + feedback);
                
                JobDetail jobDetail = this.buildJobDetail(EmailSchedualJob.class,
                        new EmailPartDto(supportEmail, "Feedback", builder.toString()));
                this.jobTriggerSerivce.startJob(jobDetail);
                payload.setMsg("ok");
                payload.setData("ok");
                payload.setStatus(Response.Status.OK.getStatusCode());
                return payload;
            }
        } catch (InvalidJwtToken e) {
            payload.setMsg("Cannot send feedback as invlaid jwt token");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (UserNotFoundException e) {
            payload.setMsg("Cannot send feedback as user cannot be found");
            payload.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return payload;
        } catch (SchedulerException e) {
            String supportEmail = AppConfig.getInstance().getSesConfiguration().getSupport();
            LOGGER.warn("fail to send email to " + supportEmail);
            payload.setMsg("schedual job fail");
            payload.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            return payload;
        }
    }
    
    private JobDetail buildJobDetail(Class<? extends Job> jobClass, EmailPartDto emailPartDto) {
        JobDataMap newJobDataMap = new JobDataMap();
        newJobDataMap.put(EmailPart.EMAIL_TO.name(), emailPartDto.getEmailTo());
        newJobDataMap.put(EmailPart.SUBJECT.name(), emailPartDto.getSubject());
        newJobDataMap.put(EmailPart.BODY.name(), emailPartDto.getBody());
        return JobBuilder.newJob(jobClass).withIdentity(JobIdentifier.SEND_EMAIL.getJobKey()).setJobData(newJobDataMap).build();
    }
}

