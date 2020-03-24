package com.bootbatch.job;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.ClassUtils;

import com.bootbatch.main.BatchVO;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecondJob {
	private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactoy;
    private final SecondStepListner secondStepListner;
    
    @Autowired
    private DataSource dataSource;
    
    SimpleDateFormat format1 = new SimpleDateFormat("_yyyyMMdd");
    Date time = new Date();
    
    String time1 = format1.format(time);
    
    private Resource outputResource = new FileSystemResource("output/SD"+time1+".csv");
    
    @Bean
    public Job jdbcCursorItemReaderJob(Flow incativeJobFlow) throws Exception {
    	return jobBuilderFactory.get("jdbcCursorItemReaderJob")
    			.start(incativeJobFlow)
    			.end()
    			.build();
    }
    
    @Bean 
    public Flow incativeJobFlow(Step step1) {
    	FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("incativeJobFlow");
    	return flowBuilder
    			.start(new InactiveJobExecutionDecider())
    			.on(FlowExecutionStatus.FAILED.getName()).end()
    			.on(FlowExecutionStatus.COMPLETED.getName()).to(step1)
    			.end();
    }
    
    @Bean
    public Step step1() throws Exception {
    			return stepBuilderFactoy.get("jdbcCursorItemReaderStep")
    			.<BatchVO, BatchVO>chunk(10)
    			//reader에서 받아서
    			.reader(csvFileReader())
    			//writer에 넣는다.
    			.writer(jdbcCursorItemWriter())
    			.listener(secondStepListner)
    			.build();
    }
    
	@Bean
    public JdbcBatchItemWriter<BatchVO> jdbcCursorItemWriter(){
    	JdbcBatchItemWriter<BatchVO> itemWriter = new JdbcBatchItemWriter<BatchVO>();
    	itemWriter.setDataSource(dataSource);
    	itemWriter.setSql("INSERT INTO SECOND_JOBS values(:job_id, :job_title, :min_salary, :max_salary)");
    	itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<BatchVO>());
    	return itemWriter;
    }
    
    @Bean
    public FlatFileItemReader<BatchVO> csvFileReader() throws Exception {
        return new FlatFileItemReaderBuilder<BatchVO>()
                .name(ClassUtils.getShortName(FlatFileItemReader.class))
                .resource(outputResource)
                .targetType(BatchVO.class)
                .delimited()
                .names(new String[]{"job_id", "job_title", "min_salary", "max_salary"})
                .build();
    }
    
}
