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
    public ResponseEntity<BigDecimal> add(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = sendToQueue("add", a, b);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/subtract")
    public ResponseEntity<BigDecimal> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = sendToQueue("subtract", a, b);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/multiply")
    public ResponseEntity<BigDecimal> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = sendToQueue("multiply", a, b);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/divide")
    public ResponseEntity<BigDecimal> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Divisão por zero
        }
        BigDecimal result = sendToQueue("divide", a, b);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private BigDecimal sendToQueue(String operation, BigDecimal a, BigDecimal b) {
        // Obter o Request ID do MDC
        String requestId = MDC.get("requestId");

        // Construir o payload com o Request ID
        String request = requestId + "," + operation + "," + a + "," + b;

        // Enviar a mensagem para a fila e esperar a resposta
        return (BigDecimal) rabbitTemplate.convertSendAndReceive("calculatorQueue", request);
    }
}
