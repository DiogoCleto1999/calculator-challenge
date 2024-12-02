package com.wit.calculator;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit  

public class CalculatorApplication {
	public static void main(String[] args)
    {
        System.setProperty("server.port", "8081");
        SpringApplication.run(CalculatorApplication.class, args);
    }

}
