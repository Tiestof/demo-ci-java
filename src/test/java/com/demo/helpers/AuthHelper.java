package com.demo.helpers;

/**
 * Simulación simple de autenticación.
 */
public class AuthHelper {

    public static String login(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return "Credenciales inválidas";
        }
        if (username.equals("admin") && password.equals("1234")) {
            return "Bienvenido admin";
        }
        return "Credenciales inválidas";
    }
}