Feature: Como gerente quiero aumentar y disminuir el stock de un producto para actualizar el inventario

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0;
    And Gerente ha iniciado sesion

  Scenario: Gerente quiere añadir stock valido de un producto existente
    When Gerente elige el producto ID 1 (kitkat normal)
    And Gerente indica nuevas unidades 5 (>0)
    Then Aumentar el stock en el inventario


  Scenario: Gerente quiere añadir stock no valido de un producto existente
    When Gerente elige el producto ID 1 (kitkat normal)
    And Gerente indica nuevas unidades -1 (<=0)
    Then Mostrar mensaje numero de nuevas unidades no valido


  Scenario: Gerente quiere añadir stock de un producto no existente
    When Gerente elige el producto ID 6 (No existe)
    Then Mostrar mensaje de error producto no existente


  Scenario: Gerente quiere reducir stock valido de un producto existente
    When Gerente elige el producto ID 1 (kitkat normal)
    And Gerente indica unidades a retirar 5 (>0)
    Then Disminuir el stock en el inventario

  Scenario: Gerente quiere reducir stock no valido de un producto existente
    When Gerente elige el producto ID 1 (kitkat normal)
    And Gerente indica unidades a retirar -1 (<=0)
    Then Mostrar mensaje de error numero de unidades a retirar no valido


  Scenario: Gerente quiere reducir stock de un producto no existente
    When Gerente elige el producto ID 6 (No existe)
    Then Mostrar mensaje de error producto no existente
