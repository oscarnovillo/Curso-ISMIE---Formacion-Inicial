Feature: Como gerente quiero añadir un producto al inventario para venderlo

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0;
    And Gerente ha iniciado sesion


  Scenario: Gerente quiere añadir un producto ya existente
    When Gerente elige añadir 'kitkat normal'
    Then Mostrar mensaje de error producto ya existente


  Scenario: Gerente quiere añadir un producto nuevo con precio y stock validos
    When Gerente elige añadir 'M&Ms'
    And Gerente indica precio de venta 2 (>0)
    And Gerente indica stock 10 (>0)
    Then Registrar producto en el inventario


  Scenario: Gerente quiere añadir un producto nuevo con precio o stock no validos
    When Gerente elige añadir 'M&Ms'
    And Gerente indica precio de venta 0 (<=0)
    And Gerente indica stock 0 (<=0)
    Then Mostrar mensaje de error producto no añadido revisa los datos