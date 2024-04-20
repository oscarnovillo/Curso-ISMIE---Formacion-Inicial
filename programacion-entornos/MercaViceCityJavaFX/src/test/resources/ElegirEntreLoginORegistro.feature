Feature: Elegir entre registrar o loguear nombre para acceder

  Background:
    Given Menu Login
    #0. Salir
    #1. Registrar nuevo usuario.
    #2. Iniciar sesión.

  Scenario: Usuario quiere registrarse
    When Usuario elige registrarse
    Then Se inicia la funcion registrar nuevo cliente


  Scenario: Usuario quiere iniciar sesión
    When Usuario elige iniciar sesion
    Then Se inicia la funcion de inicio de sesion