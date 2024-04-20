Feature: Como cliente quiero buscar un producto por nombre para filtrar

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;
    #Id 3, Nombre Kitkat morado, Precio 1.4, Stock 0;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0;
    And Cliente ha iniciado sesion


  Scenario: Cliente quiere buscar un producto existente
    When Cliente busca la palabra 'kitkat'
    Then Mostrar productos coincidentes con stock disponible
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3;
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5;


  Scenario: Cliente quiere buscar un producto no existente
    When Cliente busca la palabra 'pate'
    Then Mostrar mensaje el nombre introducido no coincide con ningun producto