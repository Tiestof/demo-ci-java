package com.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraTest {

    @Test
    void testSuma() {
        Calculadora calc = new Calculadora();
        int resultado = calc.sumar(3, 2);
        assertEquals(5, resultado, "La suma de 3 + 2 debe ser 5");
    }

    @Test
    void testResta() {
        Calculadora calc = new Calculadora();
        int resultado = calc.restar(10, 4);
        assertEquals(6, resultado, "La resta de 10 - 4 debe ser 6");
    }
}
