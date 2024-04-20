Feature: Como cliente o gerente quiero registrar un nombre para poder iniciar sesion

  Background:
    Given Lista de usuarios: jaime, lucas, rodrigo.

  Scenario: Registrar nombre nuevo
    When Usuario elige registrar francisco
    Then Crear nuevo cliente francisco

  Scenario: Registrar nombre ya existente
    When Usuario elige registrar jaime
    Then Mostrar mensaje usuario ya existente
    And Solicitar otro nombre

  Scenario: Registrar nombre admin
    When Usuario elige registrar admin
    Then Mostrar mensaje de error no puedes registrar ese nombre
    And Solicitar otro nombre