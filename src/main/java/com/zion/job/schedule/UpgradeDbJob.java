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

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zion.mongo.db.repository.UpgradeRepository;

public class UpgradeDbJob implements Job{
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			UpgradeRepository service = (UpgradeRepository) context.getScheduler().getContext().get(JobScheduleContextKey.UPGRADE_SERVICE.name());
			service.dbupgrade();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

