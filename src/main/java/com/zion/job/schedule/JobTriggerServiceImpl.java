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

import javax.inject.Inject;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import com.zion.mail.service.SesService;
import com.zion.mongo.db.repository.FeedRepository;
import com.zion.mongo.db.repository.UpgradeRepository;
import com.zion.user.service.DefaultListenser;

public class JobTriggerServiceImpl implements JobTriggerService{
	
	private UpgradeRepository upgradeRepository;
	private SesService sesService;
	private FeedRepository feedRepo;
	
	@Inject
	public JobTriggerServiceImpl(UpgradeRepository upgradeRepository, SesService sesService, FeedRepository feedRepo) {
		this.upgradeRepository = upgradeRepository;
		this.sesService = sesService;
		this.feedRepo = feedRepo;
	}

	@Override
	public void startJob(JobDetail job) throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		if (scheduler.checkExists(job.getKey())) {
		    scheduler.deleteJob(job.getKey());
		}
		
		ListenerManager listenerManager = scheduler.getListenerManager();
		listenerManager.addJobListener(
			new DefaultListenser(), KeyMatcher.keyEquals(job.getKey())
		);
		scheduler.getContext().put(JobScheduleContextKey.UPGRADE_SERVICE.name(), upgradeRepository);
		scheduler.getContext().put(JobScheduleContextKey.EMAIL_SERVICE.name(), sesService);
		scheduler.getContext().put(JobScheduleContextKey.UPDATE_FEED_AUTHOR_ROLES.name(), feedRepo);
		scheduler.start();

		scheduler.scheduleJob(job, this.buildTrigger(job));
	}
	
	private Trigger buildTrigger(JobDetail job){
		TriggerBuilder<Trigger> triggerBuild = TriggerBuilder
				.newTrigger();

		Object expression = job.getJobDataMap().get(JobScheduleContextKey.CRON_EXPRESSION.name());
		if (expression != null) {
			triggerBuild.withSchedule(CronScheduleBuilder.cronSchedule((CronExpression) expression));
		}
		return triggerBuild.build();
	}

}

