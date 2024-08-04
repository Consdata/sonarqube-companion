package com.consdata.echo.configuration;

import com.consdata.echo.configuration.organization.OrganizationProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(OrganizationProperties.class)
public class PropertiesAutoConfiguration {
}
