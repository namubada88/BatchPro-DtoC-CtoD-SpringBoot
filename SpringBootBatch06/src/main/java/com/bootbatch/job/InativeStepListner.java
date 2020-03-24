package com.bootbatch.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InativeStepListner {
	
    SimpleDateFormat format1 = new SimpleDateFormat("_yyyyMMdd");
    Date time = new Date();
    String time1 = format1.format(time);
    private String outputResource = "C:\\Users\\nuriapp\\git\\SpringBatch-dtoc-ctod\\SpringBootBatch06\\output\\SD"+time1+".csv";
    private String inputResource = "C:\\output\\SD"+time1+".csv";
	private String finResource = "C:\\output\\"+time1+".fin";
    
    
    @BeforeStep
    public void beforeStep() {
    	System.out.println("beforeStep 접속!!");
    }
    
    @AfterStep
    public void afterStep() throws IOException {
    	File in = new File(outputResource);
    	File out = new File(inputResource);
    	FileCopyUtils.copy(in, out);
    	File finFile = new File(finResource);
    	finFile.createNewFile();
    }
}
