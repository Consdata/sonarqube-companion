package com.consdata.echo.storage;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@ComponentScan("com.consdata.echo.storage")
@EnableJpaRepositories
@EntityScan("com.consdata.echo.storage")
public class JpaStorageAutoconfiguration {
}
