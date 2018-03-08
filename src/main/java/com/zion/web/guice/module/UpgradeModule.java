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
package com.zion.web.guice.module;

import com.google.inject.AbstractModule;
import com.zion.job.schedule.JobTriggerService;
import com.zion.job.schedule.JobTriggerServiceImpl;
import com.zion.mongo.db.repository.UpgradeRepository;
import com.zion.mongo.db.repository.UpgradeRepositoryImpl;

public class UpgradeModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(UpgradeRepository.class).to(UpgradeRepositoryImpl.class);
		bind(JobTriggerService.class).to(JobTriggerServiceImpl.class);
	}

}
