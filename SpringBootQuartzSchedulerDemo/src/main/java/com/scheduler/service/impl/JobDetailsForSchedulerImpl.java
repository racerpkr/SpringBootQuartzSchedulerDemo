package com.scheduler.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.service.JobDetailsForScheduler;
import com.scheduler.service.SchedulerJobService;

@Service
public class JobDetailsForSchedulerImpl implements JobDetailsForScheduler{
	
	private final static Logger log = LogManager.getLogger(JobDetailsForSchedulerImpl.class.getName());

	
	@Autowired SchedulerJobService schedulerJobService;
	
	@Override
	public String createSchedulerJob(String jobName, Date startTime, Map<String, Object> jobParams) throws Exception{
		
		log.info("createSchedulerJob");
		
		boolean schedulerResponse = false;
		String response = "Failure";
		String groupName = jobName+"group1";
		schedulerResponse = schedulerJobService.createSchedule(jobName, startTime, jobParams, groupName);
		if(schedulerResponse)
			response = "Success";
			
		return response;
		
	}

	public String unscheduleJob(String jobName){
		String groupName = jobName+"group1";
		schedulerJobService.unSchedule(groupName);
		return jobName;
		
	}
	
}
