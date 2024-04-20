Feature: Como cliente quiero poder meter una lista de alergenos para que la tienda sepa que venderme

  Background:
    Given Cliente ha iniciado sesion

  Scenario: Cliente elige añadir un ingrediente a su lista de alergenos
    When Cliente indica añadir alergenos
    And Cliente indica que quiere añadir un nuevo alergeno
    And Cliente indica nombre alergeno
    And Cliente indica que no quiere añadir mas alergenos
    Then Mostrar mensaje alergeno añadido

  Scenario: Cliente elige añadir mas de un ingrediente a su lista de alergenos
    When Cliente indica añadir alergenos
    And Cliente indica que quiere añadir un nuevo alergeno
    And Cliente indica nombre alergeno
    And Cliente indica que quiere añadir mas alergenos
    And Cliente indica nombre alergeno
    And Cliente indica que no quiere añadir mas alergenos
    Then Mostrar mensaje por cada alergeno añadido
