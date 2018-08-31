package com.audit.utils;



import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.audit4j.core.handler.ConsoleAuditHandler;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.layout.SimpleLayout;
import org.audit4j.handler.db.DatabaseAuditHandler;
import org.audit4j.integration.spring.AuditAspect;
import org.audit4j.integration.spring.SpringAudit4jConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * 
 * @author praveenkumar
 *
 */
@Configuration
public class Audit4jConfig {
	
	@Autowired 
	private DataSource dataSource;
	
    @Bean
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }

    @Bean
    public SpringAudit4jConfig springAudit4jConfig() {
        SpringAudit4jConfig springAudit4jConfig = new SpringAudit4jConfig();
        
        springAudit4jConfig.setLayout(new SimpleLayout());
        ConsoleAuditHandler console= new ConsoleAuditHandler(); 
              
        DatabaseAuditHandler datahandler=new DatabaseAuditHandler();
        datahandler.setEmbedded("false");     
        datahandler.setDataSource(dataSource);
        
        List<Handler> handlers = new ArrayList<>();
        handlers.add(console);
        handlers.add(datahandler);
        
        springAudit4jConfig.setHandlers(handlers);
        AuditMetaData metadata=new AuditMetaData();
        springAudit4jConfig.setMetaData(metadata);
        
        return springAudit4jConfig;
    }
    
     
}