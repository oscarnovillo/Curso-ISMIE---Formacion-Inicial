Feature: Como cliente quiero ver los productos disponibles sin caducar para elegir cuales comprar

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2, Caducidad 2022-02-15T15:51:00;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3, Caducidad 2022-02-15T15:58:00;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0;
    And Cliente ha iniciado sesion
    And Son las 2022-02-15T15:54:00


  Scenario: Cliente quiere ver todos los productos disponibles
    When Cliente elige ver productos disponibles
    Then Mostrar
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3, Caducidad 2022-02-15T15:58:00;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;