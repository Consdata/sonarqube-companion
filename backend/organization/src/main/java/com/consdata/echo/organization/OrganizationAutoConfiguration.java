package com.consdata.echo.organization;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@EnableConfigurationProperties(OrganizationProperties.class)
@ComponentScan("com.consdata.echo.organization")
public class OrganizationAutoConfiguration {
}
