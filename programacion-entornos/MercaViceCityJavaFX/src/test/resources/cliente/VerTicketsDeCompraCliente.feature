Feature: Como cliente quiero ver mis ticket de compra para controlar mi gasto

  Scenario: Cliente tiene tickets
    Given Lista de tickets
    #Ticket1 (kitkat 3u 3€)
    #Ticket2 (kitkat 2u 2€)
    When Cliente elige ver sus ultimas compras
    Then Mostrar
    #Ticket1 (kitkat 3u 3€)
    #Ticket2 (kitkat 2u 2€)


  Scenario: Cliente no tiene tickets
    Given Lista de tickets
    #NULL
    When Cliente elige ver sus ultimas compras
    Then Mostrar mensaje todavia no has realizado ninguna compra