Feature: Como cliente quiero eliminar un monedero para sacarlo de la app

  Background:
    Given Lista monederos cliente
    #Numero 25, Importe 50
    #Numero 35, Importe 55

  Scenario: Cliente quiere eliminar un monedero existente
    When Cliente introduce una numero de tarjeta 25
    Then Lista monederos cliente
    #Numero 35, Importe 55

  Scenario: Cliente quiere eliminar un monedero no existente
    When Cliente introduce una numero de tarjeta 55
    Then Mostrar mensaje no existe el monedero especificado

