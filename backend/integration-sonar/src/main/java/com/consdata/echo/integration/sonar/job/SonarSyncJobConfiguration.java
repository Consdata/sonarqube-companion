package com.consdata.echo.integration.sonar.job;

import com.consdata.echo.SynchronizationStepsOrder;
import com.consdata.echo.integration.sonar.connector.SonarConnector;
import com.consdata.echo.integration.sonar.connector.SqIssue;
import com.consdata.echo.issues.Issue;
import com.consdata.echo.issues.IssueStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SonarSyncJobConfiguration {

    private final SonarConnector sonarConnector;
    private final IssueStorage storage;

    @Bean
    @Order(SynchronizationStepsOrder.ISSUES_FETCH)

    //// AbstractPagingItemReader


    public Step fetchProjectsIssues(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("fetProjectsIssues", jobRepository)
                .allowStartIfComplete(true)
                .<SqIssue, Issue>chunk(100, transactionManager)
                .reader(new AbstractPagingItemReader<SqIssue>() {
                    @Override
                    protected void doReadPage() {
                        results = sonarConnector.search("", getPageSize(), getPage()+1).issues();
                    }
                })
                .processor(SqIssue::toIssue)
                .writer(chunk -> storage.save((List<Issue>) chunk.getItems()))
                .build();
    }

//    @Bean
//    @Order(SynchronizationStepsOrder.ISSUES_PROCESSING)
//    public Step usersSync(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        // process issues, aggregate users and teams summaries
//    }
}
