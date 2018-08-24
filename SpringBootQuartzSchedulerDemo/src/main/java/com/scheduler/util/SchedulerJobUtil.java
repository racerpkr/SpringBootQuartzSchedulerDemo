package com.scheduler.util;

import java.util.Date;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * 
 * @author praveenkumar
 *
 */
public class SchedulerJobUtil {

	public static JobDetail getJobDetail(String name,String group,Class<? extends QuartzJobBean> jobClass,
			ApplicationContext applicationContext,Map<String,Object> jobDataAsMap){
		JobDetailFactoryBean jobDetailBean = new JobDetailFactoryBean();
		jobDetailBean.setName(name);
		jobDetailBean.setGroup(group);
		jobDetailBean.setJobClass(jobClass);
		jobDetailBean.setApplicationContext(applicationContext);
		jobDetailBean.setJobDataAsMap(jobDataAsMap);
		
		return jobDetailBean.getObject();
			
	}
	
	
	public static Trigger getTrigger(String name,Date startTime,int misfireInstruction){
		SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
		simpleTriggerFactoryBean.setName(name);
		simpleTriggerFactoryBean.setStartTime(startTime);
		simpleTriggerFactoryBean.setRepeatCount(0);
		simpleTriggerFactoryBean.setMisfireInstruction(misfireInstruction);
		simpleTriggerFactoryBean.afterPropertiesSet();
/*		simpleTriggerFactoryBean.setGroup(group);
		simpleTriggerFactoryBean.setJobDetail(jobDetail);*/
		
		return simpleTriggerFactoryBean.getObject();
	
		
	}
	
	
}
