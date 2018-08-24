package com.scheduler.service;

import java.util.Date;
import java.util.Map;

public interface JobDetailsForScheduler {

	public String createSchedulerJob(String jobName, Date startTime, Map<String, Object> jobParams) throws Exception;

}
