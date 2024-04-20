Feature: Como gerente quiero ver los clientes que se han registrado para tener un control de mis clientes

  Scenario: Hay clientes registrados
    Given Lista de clientes
    #Javier DNI 1234
    #Lucas DNI 1235
    #Fernando DNI 1232
    When Gerente elige ver lista de clientes
    Then Mostrar
    #Javier DNI 1234
    #Lucas DNI 1235
    #Fernando DNI 1232

  Scenario: No hay clientes registrados
    Given Lista de clientes
    #NULL
    When Gerente elige ver lista de clientes
    Then Mostrar mensaje no hay clientes registrados