package com.bootbatch.job;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SecondStepListner {

    @BeforeStep
    public void beforeStep() {
    	System.out.println("===>secondStepListner로 beforeStep 접속!!");
    	ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    	populator.addScript(new ClassPathResource("truncate_secondjob.sql"));
    	DatabasePopulatorUtils.execute(populator, dataSource);
    }
    
    @Autowired
    private DataSource dataSource;
    
    @AfterStep
    public void afterStep() throws IOException {
    	System.out.println("===>secondStepListner로 afterStep에 접속");

    }
	
}
