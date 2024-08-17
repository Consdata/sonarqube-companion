package com.consdata.echo.synchronization;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SyncUsersJobConfiguration {

    @Bean
    public Job usersSynchronizationJob(JobRepository jobRepository, List<Step> synchronizationSteps) {
        SimpleJobBuilder jobBuilder = new SimpleJobBuilder(new JobBuilder("sync", jobRepository));
        for (Step step : synchronizationSteps) {
            jobBuilder = jobBuilder.next(step);
        }
        return jobBuilder.build();
    }


}
