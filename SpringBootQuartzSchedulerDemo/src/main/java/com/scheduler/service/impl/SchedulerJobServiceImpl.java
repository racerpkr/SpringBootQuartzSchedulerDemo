package com.scheduler.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import com.scheduler.job.SimpleTriggerJob;
import com.scheduler.service.SchedulerJobService;

@Component
public class SchedulerJobServiceImpl implements SchedulerJobService{

	private final static Logger log = LogManager.getLogger(SchedulerJobServiceImpl.class.getName());
	
	@Autowired
	@Lazy
	SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	ApplicationContext applicationContext;

	@Override
	public boolean createSchedule(String jobName, Date startTime, Map<String,Object> jobParams, String groupName) {

		boolean response = false;
		try {
			log.info("create Scheduler");
			log.debug("jobName "+jobName,"startTime "+startTime,"groupName "+groupName);
			String group = groupName;

			Trigger trigger = getSimpleTriggerFactoryBean(jobName, startTime, 0);

			JobDetail jobDetail = getJobDetail(jobName, group, SimpleTriggerJob.class, applicationContext,
					jobParams);
			
			Date scheduledDate = schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);
			log.debug("Scheduled Date "+scheduledDate);
			response = true;

		} catch (Exception e) {
			log.error("create Schedule Error : ",e.getMessage());
			e.printStackTrace();
		}
		return response;

	}
	
	
	@Override
	public boolean unSchedule(String jobName){
		boolean response = false;
		try{
		TriggerKey triggerKey = new TriggerKey(jobName);
		
		response = schedulerFactoryBean.getScheduler().unscheduleJob(triggerKey);
		log.info("Unscheduler Response  "+response);
		
		}catch(Exception e){
			log.error("unSchedule Error : ",e.getMessage());
			e.printStackTrace();
		}
		return response;
		
	}


	/**
	 * 
	 * @param name
	 * @param group
	 * @param jobClass
	 * @param applicationContext
	 * @param jobDataAsMap
	 * @return
	 */
	private JobDetail getJobDetail(String name, String group, Class<? extends QuartzJobBean> jobClass,
			ApplicationContext applicationContext, Map<String, Object> jobDataAsMap) {
		JobDetailFactoryBean jobDetailBean = new JobDetailFactoryBean();
		jobDetailBean.setName(name);
		jobDetailBean.setGroup(group);
		jobDetailBean.setJobClass(jobClass);
		jobDetailBean.setApplicationContext(applicationContext);
		jobDetailBean.setJobDataAsMap(jobDataAsMap);

		return jobDetailBean.getObject();

	}

	/**
	 * 
	 * 
	 * @param name
	 * @param startTime
	 * @param misfireInstruction
	 * @return
	 */
	private Trigger getSimpleTriggerFactoryBean(String name, Date startTime, int misfireInstruction) {
		SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
		simpleTriggerFactoryBean.setName(name);
		simpleTriggerFactoryBean.setStartTime(startTime);
		simpleTriggerFactoryBean.setRepeatCount(0);
		simpleTriggerFactoryBean.setMisfireInstruction(misfireInstruction);
		simpleTriggerFactoryBean.afterPropertiesSet();
		/*
		 * simpleTriggerFactoryBean.setGroup(group);
		 * simpleTriggerFactoryBean.setJobDetail(jobDetail);
		 */

		return simpleTriggerFactoryBean.getObject();

	}

	/**
	 * 
	 * @param jobName
	 * @param groupKey
	 * @return
	 */
	public boolean isJobPresent(String jobName,String groupKey) {
		try {

			JobKey jobKey = new JobKey(jobName, groupKey);
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			if (scheduler.checkExists(jobKey)) {
				return true;
			}
		} catch (SchedulerException e) {
			log.error("Job Already Exist Error :" , e.getMessage());
			e.printStackTrace();
		}
		return false;
	}




}
