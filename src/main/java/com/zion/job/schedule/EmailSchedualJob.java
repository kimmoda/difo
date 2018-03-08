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
package com.zion.job.schedule;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zion.mail.EmailPart;
import com.zion.mail.SendEmailException;
import com.zion.mail.service.SesService;

public class EmailSchedualJob implements Job{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSchedualJob.class);
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SesService service;
        JobDetail jobDetail;
        try {
            service = (SesService) context.getScheduler().getContext().get(JobScheduleContextKey.EMAIL_SERVICE.name());
            jobDetail = context.getJobDetail();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String emailFrom = jobDetail.getJobDataMap().getString(EmailPart.FRROM.name());
        String emailTo = jobDetail.getJobDataMap().getString(EmailPart.EMAIL_TO.name());
        String subject = jobDetail.getJobDataMap().getString(EmailPart.SUBJECT.name());
        String body = jobDetail.getJobDataMap().getString(EmailPart.BODY.name());
        //attempt 5 times if send email fail.
        int attempt = 5;
        boolean success = false;
        for (int i=0; i < attempt; i++) {
            try {
                String from = StringUtils.isNotBlank(emailFrom) ? emailFrom : null;
                service.sendEmail(from, emailTo, subject, body);
                LOGGER.info(String.format("Email has been send to '%s' for '%s'", emailTo, subject));
                success = true;
                break;
            } catch (SendEmailException e) {
                LOGGER.warn(String.format("Email fails to reach '%s' for '%s' will try it again.", emailTo, subject));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    LOGGER.warn(String.format("Email fails to reach '%s' for '%s' and waiting 1000 millisecond fail need manual send", emailTo, subject));
                }
            }
        }
        if (!success) {
            LOGGER.warn(String.format("Email fails to reach '%s' for '%s', need manual to complete email.", emailTo, subject));
        }
    }

}

