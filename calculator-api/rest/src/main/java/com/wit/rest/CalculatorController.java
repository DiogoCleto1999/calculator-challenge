package com.wit.rest;

import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CalculatorController {
    private final RabbitTemplate rabbitTemplate;

    public CalculatorController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/add")
    public ResponseEntity<Result> add(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        try {
            Object result = sendToQueue("add", a, b);
            return new ResponseEntity<>(new Result(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Result(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subtract")
    public ResponseEntity<Result> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        try {
            Object result = sendToQueue("subtract", a, b);
            return new ResponseEntity<>(new Result(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Result(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/multiply")
    public ResponseEntity<Result> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        try {
            Object result = sendToQueue("multiply", a, b);
            return new ResponseEntity<>(new Result(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Result(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/divide")
    public ResponseEntity<Result> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            return new ResponseEntity<>(new Result("Error: Division by zero is not allowed."), HttpStatus.BAD_REQUEST);
        }
        try {
            Object result = sendToQueue("divide", a, b);
            return new ResponseEntity<>(new Result(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Result(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Object sendToQueue(String operation, BigDecimal a, BigDecimal b) {
        String requestId = MDC.get("requestId");
    
        String request = requestId + "," + operation + "," + a + "," + b;
    
        Object response = rabbitTemplate.convertSendAndReceive("calculatorQueue", request);
    
        if (response == null) {
            return "Error: No response from calculator service.";
        }
    
        if (response instanceof String) {
            try {
                return new BigDecimal((String) response);
            } catch (NumberFormatException e) {
                return "Error: No response from calculator service.";
            }
        }
        return "Unexpected response type from calculator service.";
    }
    
}
