package com.bootbatch.job;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class InactiveJobExecutionDecider implements JobExecutionDecider {

    SimpleDateFormat format1 = new SimpleDateFormat("_yyyyMMdd");
    Date time = new Date();
    String time1 = format1.format(time);
    private String finResource = "C:\\output\\"+time1+".fin";
    File finFile = new File(finResource);
	
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		// TODO Auto-generated method stub
    	if(finFile.exists()) {
    		System.out.println("===>FlowExecutionStatus 파일이 존재합니다.");
    		return FlowExecutionStatus.COMPLETED;
    	} else {
    		System.out.println("===>FlowExecutionStatus 파일이 존재하지않습니다.");
    		return FlowExecutionStatus.FAILED;
    	}
	}
	
}
