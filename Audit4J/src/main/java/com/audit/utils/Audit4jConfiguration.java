package com.audit.utils;

import javax.sql.DataSource;

import org.audit4j.core.handler.ConsoleAuditHandler;
import org.audit4j.core.layout.SimpleLayout;
import org.audit4j.handler.db.DatabaseAuditHandler;
import org.audit4j.integration.spring.AuditAspect;
import org.audit4j.integration.spring.SpringAudit4jConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.google.common.collect.ImmutableList;

@Configuration
public class Audit4jConfiguration {

    @Bean
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }

    @Bean
    public SpringAudit4jConfig springAudit4jConfig() {
        SpringAudit4jConfig springAudit4jConfig = new SpringAudit4jConfig();
        springAudit4jConfig.setLayout(new SimpleLayout());
        springAudit4jConfig.setHandlers(ImmutableList.of(new ConsoleAuditHandler()));
        //springAudit4jConfig.setHandlers(ImmutableList.of(new DatabaseAuditHandler()));
      //  springAudit4jConfig.setMetaData(() -> "Oom Jannie");
        AuditMetaData metadata=new AuditMetaData();
        springAudit4jConfig.setMetaData(metadata);
        return springAudit4jConfig;
    }
    
    @Bean
	public DataSource dataSource() {
		  DriverManagerDataSource ds = new DriverManagerDataSource();
		    ds.setDriverClassName("com.mysql.jdbc.Driver");
		    ds.setUrl("jdbc:mysql://localhost:3306/Audit4j");
		    ds.setUsername("testuser1");
		    ds.setPassword("testuser1");
		    return ds;
		
	   // Application Datasource configurations.
	}
	 
	@Bean
	public DatabaseAuditHandler databaseHandler() {
	    DatabaseAuditHandler dbHandler = new DatabaseAuditHandler();
	        dbHandler.setEmbedded("true");
	        dbHandler.setDb_connection_type("single");
	        dbHandler.setDataSource(dataSource());
	        return dbHandler;
	}
}