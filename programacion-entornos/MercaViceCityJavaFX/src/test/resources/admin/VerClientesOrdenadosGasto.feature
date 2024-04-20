Feature: Como administrador quiero añadir clientes que tengan descuento para poder apoyar a las familias

  Background:
    Given Lista de usuarios:
    #123, jaime(Gasto total 25€)
    #121, lucas(Gasto total 10€)
    #111, rodrigo(Gasto total 100€)
    And Gerente ha iniciado sesion

  Scenario: Gerente quiere ver la lista de clientes ordenados por gasto
    When Gerente elige ver la lista de clientes ordenada por gasto
    Then Mostrar
      #111, rodrigo
      #123, jaime
      #121, lucas
