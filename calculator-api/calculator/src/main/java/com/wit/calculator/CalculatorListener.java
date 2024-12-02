package com.wit.calculator;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.MDC;

@Component
public class CalculatorListener {

    @RabbitListener(queues = "calculatorQueue")
    public String handleCalculatorRequest(String request) {
        if (request == null || request.isEmpty()) {
            return "Request cannot be null or empty";
        }

        String[] parts = request.split(",");
        if (parts.length != 4) {
            return "missing values";
        }

        String requestId = parts[0];
        String operation = parts[1];

        BigDecimal a;
        BigDecimal b;

        MDC.put("requestId", requestId);
        System.out.println("Processing request with ID: " + requestId);

        try {
            a = parseBigDecimal(parts[2]);
            b = parseBigDecimal(parts[3]);

            return processOperation(requestId, operation, a, b).toString();

        } catch (IllegalArgumentException | ArithmeticException ex) {
            System.out.println("Error occurred while processing the request with ID: " + requestId + ": " + ex.getMessage());
            return "Invalid numeric value";

        } finally {
            MDC.clear();
        }
    }

    private BigDecimal processOperation(String requestId, String operation, BigDecimal a, BigDecimal b) {
        BigDecimal result;

        switch (operation) {
            case "add":
                result = a.add(b);
                break;
            case "subtract":
                result = a.subtract(b);
                break;
            case "multiply":
                result = a.multiply(b);
                break;
            case "divide":
                if (b.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = a.divide(b, RoundingMode.HALF_UP);
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        System.out.println("Result for request ID " + requestId + ": " + result);
        return result;
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value: " + value);
        }
    }

    
}
