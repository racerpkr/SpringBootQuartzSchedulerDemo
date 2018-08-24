package com.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.model.CreateScheduler;
import com.scheduler.service.JobDetailsForScheduler;


@RestController
@RequestMapping(value = "/scheduler")
public class SchedulerController {

	@Autowired JobDetailsForScheduler jobDetailsForScheduler;
	
	@PostMapping(value = "/createScheduler")
	public String createScheduler(@RequestBody CreateScheduler createScheduler) throws Exception{
		
		return jobDetailsForScheduler.createSchedulerJob(createScheduler.getJobName(), createScheduler.getStartTime(), createScheduler.getJobParams());
	}
}
