Feature: Como cliente quiero ver los productos añadidos al carrito para comprobar si son correctos

  Background:
    Given Cliente ha iniciado sesion

  Scenario: Cliente tiene productos en el carrito
    Given Carrito de la compra
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    When Cliente elige ver los productos del carrito
    Then Mostrar
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;


  Scenario: Cliente no tiene productos en el carrito
    Given Carrito de la compra
    #NULL
    When Cliente elige ver los productos del carrito
    Then Mostrar mensaje no hay productos en el carrito