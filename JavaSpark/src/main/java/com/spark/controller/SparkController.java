package com.spark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spark.service.SparkConnection;

@RestController
public class SparkController {
	
	
	@Autowired SparkConnection sparkConnection;
	
	@RequestMapping(value="/test",method=RequestMethod.POST)
	public String cassandraSpark(){
		try{
			String response=sparkConnection.cassandraSparkCall();
			System.out.println(response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
		
	}
	
}
