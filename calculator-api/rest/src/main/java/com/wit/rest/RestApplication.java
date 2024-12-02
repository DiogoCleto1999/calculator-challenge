package com.wit.rest;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit 

public class RestApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", "8080");
        SpringApplication.run(RestApplication.class, args);
	}

}
