package com.wit.calculator;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CalculatorApplicationTests {

	private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testAdd() {
        // Convertendo os valores inteiros para BigDecimal antes de chamar o método add.
        BigDecimal a = BigDecimal.valueOf(5);  // Convertendo int para BigDecimal
        BigDecimal b = BigDecimal.valueOf(5);  // Convertendo int para BigDecimal
        assertEquals(10, calculatorService.add(a, b).intValue());
    }

    @Test
    void testSubtract() {
        // Exemplo de subtração
        BigDecimal a = BigDecimal.valueOf(10);  // Convertendo int para BigDecimal
        BigDecimal b = BigDecimal.valueOf(5);   // Convertendo int para BigDecimal
        assertEquals(5, calculatorService.subtract(a, b).intValue());
    }

    @Test
    void testMultiply() {
        // Exemplo de multiplicação
        BigDecimal a = BigDecimal.valueOf(5);  // Convertendo int para BigDecimal
        BigDecimal b = BigDecimal.valueOf(5);  // Convertendo int para BigDecimal
        assertEquals(25, calculatorService.multiply(a, b).intValue());
    }

    @Test
    void testDivide() {
        // Exemplo de divisão
        BigDecimal a = BigDecimal.valueOf(5);  // Convertendo int para BigDecimal
        BigDecimal b = BigDecimal.valueOf(5);  // Convertendo int para BigDecimal
        assertEquals(1, calculatorService.divide(a, b).intValue());
    }

    @Test
    void testDivideByZero() {
        // Teste de exceção de divisão por zero
        BigDecimal a = BigDecimal.valueOf(5);
        BigDecimal b = BigDecimal.valueOf(0);
        assertThrows(ArithmeticException.class, () -> calculatorService.divide(a, b));
    }

}
