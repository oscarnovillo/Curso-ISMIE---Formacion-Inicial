Feature: Como administrador quiero la lista de productos que contienen un ingrediente para estadisticas alimenticias.

  Background:
    Given Lista de productos
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2, Ingredientes:(CACAO, E-210);
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3, Ingredientes:(CACAO, E-210);
    #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5, Ingredientes:(CACAO, E-210);
    #Id 4, Nombre Harina, Precio 0.4, Stock 7, Ingredientes:(HARINA);
    #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0, Ingredientes:(CACAO, E-211);
    #Id 6, Nombre Avion teleddirigido, Precio 25.6, Stock 10
    And Gerente ha iniciado sesion

  Scenario: Gerente quiere ver los productos que contienen un ingrediente
    When gerente elige ver los productos que contienen CACAO
    Then Mostrar
      #Id 1, Nombre Kitkat normal, Precio 1, Stock 2, Ingredientes:(CACAO, E-210);
      #Id 2, Nombre Kitkat blanco, Precio 1.2, Stock 3, Ingredientes:(CACAO, E-210);
      #Id 3, Nombre Kitkat negro, Precio 1.4, Stock 5, Ingredientes:(CACAO, E-210);
      #Id 5, Nombre Lacasitos, Precio 1.6, Stock 0, Ingredientes:(CACAO, E-211);