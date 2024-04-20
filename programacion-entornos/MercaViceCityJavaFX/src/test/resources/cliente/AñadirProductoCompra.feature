Feature: Como cliente quiero añadir un producto al carrito para comprarlo

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0;
    And Cliente ha iniciado sesion


  Scenario: Cliente quiere añadir stock disponible de un producto existente al carrito
    When Cliente elige el prductocon ID 1 (kitkat normal)
    And Elige 2 de cantidad a comprar
    Then Añadir producto a lista compra
    And Reducir stock del inventario


  Scenario: Cliente quiere añadir stock no disponible de un producto existente al carrito
    When Cliente elige el prductocon ID 1 (kitkat normal)
    And Elige 3 de cantidad a comprar
    Then Mostrar error no disponemos de las unidades requeridas indica una cantidad disponible


  Scenario: Cliente quiere añadir un producto no existente al carrito
    When Cliente elige el prductocon ID 6 (No existe)
    Then Mostrar mensaje ID no relaccionado con ningun producto