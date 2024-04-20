Feature: Como cliente quiero ver cual es mi gasto total en la tienda.

  Background:
    Given Cliente ha iniciado sesion
    And Lista de listas de compras antiguas
    #Lista 1
    #Id 1, Nombre Kitkat normal, Precio 1, Cantidad 4;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Cantidad 5;
    #Lista 2
    #Id 1, Nombre Kitkat normal, Precio 1, Cantidad 4;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Cantidad 5;

  Scenario: Cliente quiere ver su gasto total
    When Cliente elige ver su gasto total
    Then Mostrar Tu gasto ha sido de 20€