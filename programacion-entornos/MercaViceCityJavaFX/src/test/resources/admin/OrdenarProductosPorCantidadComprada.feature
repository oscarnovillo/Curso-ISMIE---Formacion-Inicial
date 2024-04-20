Feature: Como administrador quiero saber el lista de productos mas comprado ordenado por cantidad para adelantarme a la compra de stock

  Background:
    Given Listas de compras antiguas
    #Cliente 123, ramon
      #Lista 1:
        #Id 1, Nombre Kitkat normal, Precio 1, Cantidad 4;
        #Id 2, Nombre Kitkat blanco, Precio 1.2, Cantidad 5;
      #Lista 2:
        #Id 1, Nombre Kitkat normal, Precio 1, Cantidad 4;
    #Cliente 124, lucas
      #Lista 1:
        #Id 4, Nombre Harina, Precio 0.4, Cantidad 7
      #Lista 2:
        #Id 6, Nombre Avion teleddirigido, Precio 25.6, Cantidad 1;
    And Gerente ha iniciado sesion

  Scenario: Gerente quiere ver los productos mas comprados
    When Gerente elige ver la lista de productos mas comprados
    Then Mostrar
      #Id 1, Nombre Kitkat normal
      #Id 4, Nombre Harina
      #Id 2, Nombre Kitkat blanco
      #Id 6, Nombre Avion teleddirigido
