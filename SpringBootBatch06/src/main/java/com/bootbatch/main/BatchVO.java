package com.bootbatch.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BatchVO {
	private String job_id;
	private String job_title;
	private int min_salary;
	private int max_salary;	
}
