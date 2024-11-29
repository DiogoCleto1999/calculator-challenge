package com.wit.calculator;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.MDC;


@Component
public class CalculatorListener {

    @RabbitListener(queues = "calculatorQueue")
    public BigDecimal handleCalculatorRequest(String request) {
        // Dividir a mensagem em partes: requestId, operação e operandos.
        String[] parts = request.split(",");
        String requestId = parts[0];
        String operation = parts[1];
        BigDecimal a = new BigDecimal(parts[2]);
        BigDecimal b = new BigDecimal(parts[3]);

        // Configurar o requestId no MDC para rastreamento.
        MDC.put("requestId", requestId);
        System.out.println("Processing request with ID: " + requestId);

        BigDecimal result = null;
        try {
            // Processar a operação.
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
                    if (b.compareTo(BigDecimal.ZERO) != 0) {
                        result = a.divide(b,RoundingMode.UP);
                    } else {
                        System.out.println("Error: Division by zero");
                        throw new ArithmeticException("Division by zero");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation: " + operation);
            }
        } finally {
            // Limpar o MDC após o processamento.
            MDC.clear();
        }

        System.out.println("Result for request ID " + requestId + ": " + result);

        // Retornar o resultado para o módulo REST.
        return result;
    }
}
