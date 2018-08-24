package com.scheduler.service;

import java.util.Date;
import java.util.Map;

public interface SchedulerJobService {
	

	public boolean createSchedule(String jobName, Date startTime, Map<String, Object> jobParams, String groupName);

	public boolean unSchedule(String jobName);

}
