package com.demo.steps;

import com.demo.helpers.AuthHelper;
import io.cucumber.java.es.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    private String resultado;

    @Dado("que el usuario está en la pantalla de login")
    public void que_el_usuario_esta_en_la_pantalla_de_login() {
        // Simulación de navegación (sin efecto real)
        System.out.println("Usuario en pantalla de login");
    }

    @Cuando("ingresa usuario {string} y contraseña {string}")
    public void ingresa_usuario_y_contrasena(String usuario, String clave) {
        resultado = AuthHelper.login(usuario, clave);
    }

    @Entonces("el sistema muestra el mensaje {string}")
    public void el_sistema_muestra_el_mensaje(String mensajeEsperado) {
        assertEquals(mensajeEsperado, resultado, "El mensaje mostrado no coincide con lo esperado");
    }
}
