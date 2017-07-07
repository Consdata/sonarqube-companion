package net.lipecki.sqcompanion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class SonarqubeCompanionRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SonarqubeCompanionRestApplication.class, args);
	}
	
}