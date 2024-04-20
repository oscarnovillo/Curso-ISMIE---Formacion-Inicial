Feature: Como cliente quiero ver los productos disponibles para elegir cuales comprar

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0;
    And Cliente ha iniciado sesion


  Scenario: Cliente quiere ver todos los productos disponibles
    When Cliente elige ver productos disponibles
    Then Mostrar
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
