package com.bo.spring.controller.init;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


/**
定时任务并行执行
**/
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(setTaskExecutors());
		
	}
	
	@Bean(destroyMethod="shutdown")
	public Executor setTaskExecutors() {
		return Executors.newScheduledThreadPool(3);
	}

}
