package com.consdata.echo.configuration.organization.job;

import com.consdata.echo.SynchronizationStepsOrder;
import com.consdata.echo.configuration.organization.StaticOrganizationStructureProvider;
import com.consdata.echo.organization.OrganizationStorage;
import com.consdata.echo.organization.OrganizationalUnit;
import com.consdata.echo.organization.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class OrganizationSyncJobConfiguration {
    private final StaticOrganizationStructureProvider usersProvider;
    private final OrganizationStorage organizationStorage;

    @Bean
    @Order(SynchronizationStepsOrder.ORGANIZATION_STRUCTURE_SYNCHRONIZATION)
    public Step organizationStructureSync(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("organizationStructureStep", jobRepository)
                .allowStartIfComplete(true)
                .<OrganizationalUnit, OrganizationalUnit>chunk(1, transactionManager)
                .reader(usersProvider::nextUnit)
                .processor(item -> item)
                .writer(chunk -> organizationStorage.saveOrganizationRoot(chunk.getItems().getFirst()))
                .build();
    }

    @Bean
    @Order(SynchronizationStepsOrder.USERS_SYNCHRONIZATION)
    public Step usersSync(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("usersStep", jobRepository)
                .<User, User>chunk(20, transactionManager)
                .allowStartIfComplete(true)
                .reader(usersProvider::next)
                .processor(item -> item)
                .writer(chunk -> organizationStorage.saveUsers(chunk.getItems()))
                .build();
    }
}
