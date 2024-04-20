Feature: Como cliente quiero eliminar un producto del carrito para no comprarlo

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0;
    And Lista compra
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 1;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    And Cliente ha iniciado sesion

  Scenario: Cliente quiere eliminar unidades validas de un producto que tiene en la lista
    When Cliente elige eliminar producto Id 2
    And  Cliente elige eliminar una unidad de su compra
    Then Eliminar kitkat blanco de la lista compra


  Scenario: Cliente quiere eliminar unidades no validas de un producto que tiene en la lista
    When Cliente elige eliminar producto Id 2
    And  Cliente elige eliminar 4 unidad de su compra
    Then Mostrar mensaje no tienes esos productos en tu lista de compra


  Scenario: Cliente quiere eliminar un producto que no tiene en la lista
    When Cliente elige eliminar producto Id 4
    Then Mostrar mensaje no has comprado< ese producto