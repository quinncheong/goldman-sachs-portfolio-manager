package com.is442g1t4.gpa;

import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@RestController
@SpringBootApplication
@EnableScheduling
public class PortfolioApplication {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
		System.out.println("Application is running!");
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		return builder -> builder.serializerByType(ObjectId.class, new ToStringSerializer());
	}

}
