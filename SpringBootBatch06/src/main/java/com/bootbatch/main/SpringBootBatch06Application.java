package com.bootbatch.main;

import java.util.Date;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bootbatch.job.BatchConfiguration;
import com.bootbatch.job.BatchJob;
import com.bootbatch.job.SecondJob;

@SpringBootApplication
public class SpringBootBatch06Application {

	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, InterruptedException {
		SpringApplication.run(SpringBootBatch06Application.class, args);
		
		ApplicationContext context = new AnnotationConfigApplicationContext(BatchConfiguration.class, BatchJob.class);
		
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean(Job.class);

        
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate("date", new Date());
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        
        BatchStatus batchStatus = jobExecution.getStatus();

        while (batchStatus.isRunning()) {
            System.out.println("Still running...");
            Thread.sleep(1000);
        }
        System.out.println("Exit status: " + jobExecution.getExitStatus().getExitCode());

        JobInstance jobInstance = jobExecution.getJobInstance();
        System.out.println("job instance Id: " + jobInstance.getId());
        
		context = new AnnotationConfigApplicationContext(BatchConfiguration.class, SecondJob.class);
		
        jobLauncher = context.getBean(JobLauncher.class);
        job = context.getBean(Job.class);

        
        jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate("Second date", new Date());
        jobParameters = jobParametersBuilder.toJobParameters();

        jobExecution = jobLauncher.run(job, jobParameters);
        
        batchStatus = jobExecution.getStatus();

        while (batchStatus.isRunning()) {
            System.out.println("Still running...");
            Thread.sleep(1000);
        }
        System.out.println("Second Exit status: " + jobExecution.getExitStatus().getExitCode());

        jobInstance = jobExecution.getJobInstance();
        System.out.println("Second job instance Id: " + jobInstance.getId());
        
        
	}
	
	
		
}