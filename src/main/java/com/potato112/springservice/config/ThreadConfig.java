package com.potato112.springservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Provides custom Thread configuration for concurrent services
 */
@Configuration
public class ThreadConfig {

    @Bean
    public ExecutorService myExecutorService() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        return executorService;
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("default_task_executor_thread-");
        executor.initialize();
        return executor;
    }
}



