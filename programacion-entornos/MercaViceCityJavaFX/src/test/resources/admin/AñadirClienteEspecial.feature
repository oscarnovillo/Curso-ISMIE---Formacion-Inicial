Feature: Como administrador quiero añadir clientes que tengan descuento para poder apoyar a las familias

  Background:
    Given Lista de usuarios: jaime, lucas, rodrigo.
    And Gerente ha iniciado sesion

  Scenario: Registrar nombre nuevo cliente espacial
    When Usuario elige registrar francisco
    And Cliente indica que es un cliente espacial
    Then Crear nuevo cliente espacial francisco

  Scenario: Registrar nombre ya existente
    When Usuario elige registrar jaime
    Then Mostrar mensaje usuario ya existente

  Scenario: Registrar nombre admin
    When Usuario elige registrar admin
    Then Mostrar mensaje de error no puedes registrar ese nombre