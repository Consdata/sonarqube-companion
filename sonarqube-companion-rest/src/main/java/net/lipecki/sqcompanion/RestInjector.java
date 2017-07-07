package net.lipecki.sqcompanion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author gregorry
 */
@Configuration
public class RestInjector {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
