package com.consdata.echo.integration.sonar.job;

import com.consdata.echo.SynchronizationStepsOrder;
import com.consdata.echo.integration.sonar.connector.Facet;
import com.consdata.echo.integration.sonar.connector.SonarConnector;
import com.consdata.echo.integration.sonar.reader.UsersFacetReader;
import com.consdata.echo.issues.UserMetric;
import com.consdata.echo.issues.UserMetricsStorage;
import com.consdata.echo.issues.metrics.SeverityMetric;
import com.consdata.echo.organization.User;
import com.consdata.echo.organization.UsersProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SonarSyncJobConfiguration {

    private final SonarConnector sonarConnector;
    private final UsersProvider usersProvider;
    private final UserMetricsStorage userMetricsStorage;

    @Bean
    @Order(SynchronizationStepsOrder.ISSUES_FETCH)

    //// AbstractPagingItemReader


    public Step fetchProjectsIssues(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        UsersFacetReader reader = new UsersFacetReader(new ArrayList<>(usersProvider.getUsers().stream().map(User::id).toList()), null, LocalDate.now(), sonarConnector);
        AtomicInteger c = new AtomicInteger();
        return new StepBuilder("fetProjectsIssues", jobRepository)
                .allowStartIfComplete(true)
                .<List<Facet>, UserMetric>chunk(1, transactionManager)
                .reader(reader)
                .processor(e -> e.stream().filter(i -> i.property().equals("severities")).findFirst().map(cd -> new UserMetric(UUID.randomUUID().toString(), "sq9", LocalDate.now(), new SeverityMetric(
                        Integer.parseInt(cd.values().get(0).count()),
                        Integer.parseInt(cd.values().get(1).count()),
                        Integer.parseInt(cd.values().get(2).count()),
                        Integer.parseInt(cd.values().get(3).count()),
                        Integer.parseInt(cd.values().get(4).count())
                ))).get())
                .writer(chunk -> {
                    UserMetric metric = chunk.getItems().get(0);
                    userMetricsStorage.saveUserSeverityMetrics(metric);
                })
                .build();
    }

//    @Bean
//    @Order(SynchronizationStepsOrder.ISSUES_PROCESSING)
//    public Step usersSync(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        // process issues, aggregate users and teams summaries
//    }
}
