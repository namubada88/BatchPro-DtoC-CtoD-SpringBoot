package com.bootbatch.job;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.bootbatch.main.BatchVO;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class BatchJob {

	private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactoy;
    private final InativeStepListner inativeStepListner;
    
    private Resource outputResource = new FileSystemResource("output/outputData.csv");
       
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public Job jdbcCursorItemReaderJob() {
    	return jobBuilderFactory.get("jdbcCursorItemReaderJob")
    			.start(step1())
    			.build();
    }

    @Bean
    public Step step1() {
    			return stepBuilderFactoy.get("jdbcCursorItemReaderStep")
    			.<BatchVO, BatchVO>chunk(10)
    			//reader에서 받아서
    			.reader(jdbcCursorItemReader())
    			//writer에 넣는다.
    			.writer(jdbcCursorItemWriter())
    			.listener(inativeStepListner)
    			.build();
    }
    
	@Bean
    public FlatFileItemWriter<BatchVO> jdbcCursorItemWriter(){
    	FlatFileItemWriter<BatchVO> itemWriter = new FlatFileItemWriter<>();
    	itemWriter.setResource(outputResource);
    	System.out.println("===>outputResource : "+outputResource);
    	itemWriter.setAppendAllowed(true);
    	itemWriter.setLineAggregator(new DelimitedLineAggregator<BatchVO>() {
    		{
    			setDelimiter(",");
    			setFieldExtractor(new BeanWrapperFieldExtractor<BatchVO>() {
                {
                    setNames(new String[] { "job_id", "job_title", "min_salary", "max_salary" });
                }
           
            });
    		}
    	});
    	return itemWriter;
    }
    
    @Bean
    public JdbcCursorItemReader<BatchVO> jdbcCursorItemReader(){
    	System.out.println("===>jdbcCursorItemReader 접속!!");
    	return new JdbcCursorItemReaderBuilder<BatchVO>()
    			.fetchSize(10)
    			.dataSource(dataSource)
    			.rowMapper(new BeanPropertyRowMapper<>(BatchVO.class))
    			.sql("SELECT * FROM JOBS")
    			.name("jdbcCursorItemWriter")
    			.build(); 
    }
 
}
