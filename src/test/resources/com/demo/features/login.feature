Feature: Login de usuario
  Como usuario del sistema
  Quiero iniciar sesión con mis credenciales
  Para acceder a mis funcionalidades

  Background:
    Given que el usuario está en la pantalla de login

  Scenario: Login exitoso con credenciales válidas
    When ingresa usuario "admin" y contraseña "1234"
    Then el sistema muestra el mensaje "Bienvenido admin"

  Scenario Outline: Login fallido con credenciales inválidas o vacías
    When ingresa usuario "<usuario>" y contraseña "<clave>"
    Then el sistema muestra el mensaje "Credenciales inválidas"

    Examples:
      | usuario | clave |
      | admin   | 0000  |
      | root    | 1234  |
      |         | 1234  |
      | admin   |       |