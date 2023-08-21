package com.sciencematch.sciencematch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScienceMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScienceMatchApplication.class, args);
	}

}
