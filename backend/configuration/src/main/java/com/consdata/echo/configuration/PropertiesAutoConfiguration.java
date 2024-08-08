package com.consdata.echo.configuration;

import com.consdata.echo.configuration.organization.OrganizationProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@EnableConfigurationProperties(OrganizationProperties.class)
@ComponentScan("com.consdata.echo.configuration")
public class PropertiesAutoConfiguration {
}
