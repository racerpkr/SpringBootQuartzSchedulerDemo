package com.scheduler.job;

import org.quartz.JobDataMap;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
/**
 * 
 * @author praveenkumar
 *
 *SimpleTriggerJob Class is called when scheduler job reached the scheduled date
 *
 */
public class SimpleTriggerJob extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		System.out.println(" Single Scheduler "+jobDataMap.getString(""));
		
	}

}
