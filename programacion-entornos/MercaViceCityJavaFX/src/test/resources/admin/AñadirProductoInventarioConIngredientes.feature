Feature: Como gerente quiero añadir un producto con sus ingredientes al inventario para venderlo

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2, Ingredientes:(CACAO, E-210);
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3, Ingredientes:(CACAO, E-210);
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5, Ingredientes:(CACAO, E-210);
    #Id 4, Nombre Harina, Precio 0.4, Stock 7, Ingredientes:(HARINA);
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0, Ingredientes:(CACAO, E-211);
    #Id 6, Nombre Avion teleddirigido, Precio 25.6, Stock 10
    And Gerente ha iniciado sesion


  Scenario: Gerente quiere añadir un producto ya existente
    When Gerente elige añadir 'kitkat normal'
    Then Mostrar mensaje de error producto ya existente


  Scenario: Gerente quiere añadir un producto nuevo con precio, stock validos y ingredientes
    When Gerente elige añadir 'M&Ms'
    And Gerente indica precio de venta 2 (>0)
    And Gerente indica stock 10 (>0)
    And Gerente indica lista de ingredientes Ingredientes:(CACAO, E-210)
    Then Registrar producto en el inventario


  Scenario: Gerente quiere añadir un producto nuevo con precio o stock no validos y ingredientes
    When Gerente elige añadir 'M&Ms'
    And Gerente indica precio de venta 0 (<=0)
    And Gerente indica stock 0 (<=0)
    And Gerente indica lista de ingredientes Ingredientes:(CACAO, E-210)
    Then Mostrar mensaje de error producto no añadido revisa los datos