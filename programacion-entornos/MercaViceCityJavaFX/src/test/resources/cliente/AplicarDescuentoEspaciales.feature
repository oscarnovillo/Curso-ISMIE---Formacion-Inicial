Feature: Como cliente con descuento quiero que al comprar se me aplique el descuento para no pagar de mas

  Background:
    Given Descuento clientes espaciales 50
    And Cliente espacial ha iniciado sesion
    And Lista de la compra
    #Id 1, Nombre Kitkat normal, Precio 1, Cantidad 4;
    #Id 2, Nombre Kitkat blanco, Precio 1.2, Cantidad 5;
    And Lista monederos
    #Numero 1, Imprte 25

  Scenario: Cliente quiere pagar su lista de la compra
    When Cliente elige pagar compra
    Then Añadir la lista compra a la lista de compras antiguas
    And Limpiar lista de la compra
    And Restar Importe de la lista de la compra aplicando descuento de el/los monederos

