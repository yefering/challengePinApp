@frontend @testRegresion
Feature: Inicio de sesion en DemoBlaze

  Background: el cliente ingresa a DemoBlaze
    Given el usuario se encuentra en al home

  @logingBlazeOk @front
  Scenario: Usuario se loguea de forma correcta
    When el usuario hace clic en el boton "LogIn"
    And el usuario ingresa "userPinApp" en el campo "Username"
    And el usuario ingresa "userPinApp" en el campo "Password"
    And el usuario hace clic en el boton "Log In"
    Then el usuario visualiza "Welcome userPinApp"

  @logingBlazeFail @front
  Scenario: Usuario no se loguea de forma correcta
    When el usuario hace clic en el boton "LogIn"
    And el usuario ingresa "userasap" en el campo "Username"
    And el usuario ingresa "userasappp" en el campo "Password"
    And el usuario hace clic en el boton "Log In"
    Then el usuario visualiza el Popup con el mensaje "Wrong password."

  @ListaDeItems @front
  Scenario: Visualizar los items en front
    When se llama al endpoint entries del servicio demoblaze
    Then los productos devueltos por el API se visualizan en la página de inicio

  @ListaDeItemsCategoria @front
  Scenario Outline: Visualizar los items por categoria "<Categoria>"
    When se llama al endpoint bycat del servicio demoblaze con la cateogira "<Categoria>"
    And el usuario se encuentra en la pagina de la categoria "<Categoria>"
    Then los productos devueltos por el demoblaze bycat se visualizan en la categoria "<Categoria>"
    Examples:
      | Categoria |
      | phone     |
      | notebook  |
      | monitor   |