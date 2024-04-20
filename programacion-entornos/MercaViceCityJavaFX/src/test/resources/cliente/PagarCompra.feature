Feature: Como cliente quiero pagar los productos del carrito para comprarlos

  Scenario: Cliente no tiene productos en el carrito
    Given Lista carrito
    #NULL
    When Cliente elige pagar
    Then Mostrar mensaje No hay productos en el carrito

  Scenario: Cliente tiene productos en el carrito y dinero suficiente en algun monedero
    Given Lista carrito
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    And Lista monederos cliente
    #Numero monedero 32423, Importe 40€
    When Cliente elige pagar
    Then Restar (precio*stock) de cada producto del monedero
    #Numero monedero 32423, Importe 35,2€

  Scenario: Cliente tiene productos en el carrito y pero no dinero suficiente en ningun monedero
    Given Lista carrito
    #Id 1, Nombre Kitkat normal, Precio 1, Stock 2;
    #Id 4, Nombre Harina, Precio 0.4, Stock 7;
    And Lista monederos cliente
    #Numero monedero 32423, Importe 4€
    When Cliente elige pagar
    Then Mostrar mensaje ningun monedero tiene saldo suficiente

