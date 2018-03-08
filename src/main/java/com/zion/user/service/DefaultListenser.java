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

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultListenser implements JobListener{
	public static final String LISTENER_NAME = DefaultListenser.class.getName();
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultListenser.class);
	
	public DefaultListenser() {
	}

	@Override
	public String getName() {
		return LISTENER_NAME;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		LOGGER.info(String.format("Job '%s' start---------", context.getJobDetail().getKey().getName()));
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
	    LOGGER.info(String.format("Job '%s' vetoed---------", context.getJobDetail().getKey().getName()));
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		LOGGER.info("Job end---------");
	}

}

