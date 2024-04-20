Feature: Como gerente quiero eliminar un cliente para que no inicie sesion

  Background:
    Given Lista de clientes
    #Javier DNI 1234
    #Lucas DNI 1235
    #Fernando DNI 1232

  Scenario: Gerente elimina un cliente existente
    When Gerente elige elimar Javier
    Then Eliminar Javier de la lista


  Scenario: Gerente elimina un cliente no existente
    When Gerente elige elimar Marisa
    Then Mostrar mensaje de error cliente no existe