package com.razi.ubbtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.razi.ubbtt")
@EnableJpaRepositories
@ComponentScan({"com.razi.ubbtt"})
public class UbbttApplication {

	public static void main(String[] args) {
		SpringApplication.run(UbbttApplication.class, args);
	}

}
