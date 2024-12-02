package com.wit.calculator;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue calculatorQueue() {
        return new Queue("calculatorQueue");
    }
}