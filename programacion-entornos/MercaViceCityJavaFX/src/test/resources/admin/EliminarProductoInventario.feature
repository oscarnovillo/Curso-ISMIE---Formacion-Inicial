Feature: Como gerente quiero eliminar un producto para dejar de venderlo

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0;
    And Gerente ha iniciado sesion

  Scenario: Gerente quiere eliminar un producto existente
    When Gerente elige eliminar ID 1 (kitkat normal)
    Then Eliminar producto del inventario


  Scenario: Gerente quiere eliminar un producto no existente
    When Gerente elige eliminar ID 6 (No existe)
    Then Mostrar mensaje producto no existente no se puede eliminar

