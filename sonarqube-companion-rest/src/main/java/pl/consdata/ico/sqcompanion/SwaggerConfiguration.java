package pl.consdata.ico.sqcompanion;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket restApiSpec() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("SonarQube Companion REST API")
                        .version("v1")
                        .build())
                .ignoredParameterTypes(Authentication.class)
                .select()
                .paths(regex("/api/v1/.*"))
                .build();
    }

}
