Feature: Como cliente o gerente quiero iniciar sesion para comprar o gestionar

  Background:
    Given Lista de usuarios: jaime, lucas, rodrigo.

  Scenario: Usuario inicia sesion con un nombre registrado
    When Usuario elige iniciar sesion como jaime
    Then Se inicia la sesion como jaime rol cliente

  Scenario: Usuario inicia sesion con un nombre no registrado
    When Usuario elige iniciar sesion como francisco
    Then Mostrar mensaje de error usuario no registrado
    And Se pide el nombre para registrarlo

  Scenario: Usuario inicia sesion con admin
    When Usuario elige iniciar admin
    And Se inicia sesion como gerente de la tienda
