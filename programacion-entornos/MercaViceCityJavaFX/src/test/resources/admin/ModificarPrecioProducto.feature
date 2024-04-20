Feature: Como gerente quiero modificar el precio de un producto para cambiar su precio de venta

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0;
    And Gerente ha iniciado sesion

  Scenario: Gerente quiere modificar el precio de un producto existente con un precio valido
    When Gerente elige el producto ID 1 (kitkat normal)
    And Gerente indica precio 1 (>0)
    Then Modificar precio de venta en el inventario


  Scenario: Gerente quiere modificar el precio de un producto existente con un precio no valido
    When Gerente elige el producto ID 1 (kitkat normal)
    And Gerente indica precio 0 (<=0)
    Then Modificar precio de venta en el inventario


  Scenario: Gerente quiere modificar el precio de un producto no existente
    When Gerente elige el producto ID 6 (No existe)
    Then Mostrar mensaje de error producto no existe
