package com.consdata.echo.integration.sonar;


import com.consdata.echo.integration.sonar.configuration.SonarProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@EnableConfigurationProperties(SonarProperties.class)
@ComponentScan("com.consdata.echo.integration.sonar")
@RequiredArgsConstructor
public class SonarIntegrationAutoConfiguration {

    @Bean("sonarRestClient")
    public RestClient sonarRestClient(SonarProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.url())
                .messageConverters(c -> c.add(new MappingJackson2HttpMessageConverter()))
                .defaultHeader(HttpHeaders.AUTHORIZATION, properties.auth().asBasic())
                .build();
    }
}
