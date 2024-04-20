Feature: Como cliente quiero ver mis monederos para ver el dinero que tengo

  Scenario: Cliente tiene monederos
    Given Lista de monederos
    #Numero 1, Importe 25
    #Numero 2, Importe 12
    When Cliente elige ver sus monederos
    Then Mostrar
    #Numero 1, Importe 25
    #Numero 2, Importe 12


  Scenario: Cliente no tiene tickets
    Given Lista de monederos
    #NULL
    When Cliente elige ver sus monederos
    Then Mostrar mensaje no tienes monederos
