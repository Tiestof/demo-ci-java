package com.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimerTest {

    @Test
    void sumaBasica() {
        int resultado = 2 + 3;
        assertEquals(5, resultado, "La suma debe ser 5");
    }
}
