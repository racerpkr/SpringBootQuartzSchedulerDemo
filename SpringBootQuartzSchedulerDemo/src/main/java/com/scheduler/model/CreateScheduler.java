package com.scheduler.model;

import java.util.Date;
import java.util.Map;

public class CreateScheduler {
	
	private String jobName;
	private Date startTime;
	private Map<String, Object> jobParams;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Map<String, Object> getJobParams() {
		return jobParams;
	}
	public void setJobParams(Map<String, Object> jobParams) {
		this.jobParams = jobParams;
	}
	

}
