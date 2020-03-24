package com.bootbatch.job;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SecondStepListner {
	
    SimpleDateFormat format1 = new SimpleDateFormat("_yyyyMMdd");
    Date time = new Date();
    String time1 = format1.format(time);
    private String finResource = "C:\\output\\"+time1+".fin";
    File finFile = new File(finResource);

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
