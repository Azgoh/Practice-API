package com.example.PracticeApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class PracticeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeApiApplication.class, args);
	}

}
