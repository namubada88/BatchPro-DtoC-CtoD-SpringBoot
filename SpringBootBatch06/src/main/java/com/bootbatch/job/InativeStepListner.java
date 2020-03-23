package com.bootbatch.job;

import java.io.File;
import java.io.IOException;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InativeStepListner {
    @BeforeStep
    public void beforeStep() {
    	System.out.println("beforeStep 접속!!");
    }
    
    @AfterStep
    public void afterStep() throws IOException {
    	File in = new File("C:\\NuriProject\\SpringBootBatch06\\output\\outputData.csv");
    	File out = new File("C:\\output\\outputData.csv");
    	FileCopyUtils.copy(in, out);
    }
}
