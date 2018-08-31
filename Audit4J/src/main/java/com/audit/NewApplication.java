package com.audit;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.audit4j.handler.db.DatabaseAuditHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
@EnableAutoConfiguration
@SpringBootApplication
public class NewApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewApplication.class, args);
	}
	

}
