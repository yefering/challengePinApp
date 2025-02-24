@backend @testRegresion
Feature: Validar petstore.

  Background: crud de petstore usuario.
    Given el servicio se encuentra activo


  @petstoreAddNewUser
  Scenario: Se crea un nuevo usuario en petstore
    When el usuario envia la solicitud de crear un usuario valido
    Then el endpoint crear usuario devuelve "200"
    And el campo "code" del response tiene el siguiente valor 200
    And el campo "type" del response tiene el siguiente valor "unknown"

  @petstoreBuscarUsuario
  Scenario Outline: Buscar usuario por nombre usuario "<usuario>"
    Given el usuario "<usuario>" existe
    When el usuario consulta al endpoint user con el nombre de "<usuario>"
    Then el endpoint get usuario devuelve "200"
    And El campo "email" del response tiene el siguiente valor "UserPepe@gmail.com"
    Examples:
      | usuario       |
      | nuevoUserPinApp |


  @petstoreActualizarUsuario
  Scenario Outline: Actualiza el correo "<correo>" del usuario "<usuario>"
    Given el usuario "<usuario>" existe
    When el usuario modifica el correo por "<correo>" al usuario "<usuario>"
    Then el endpoint update usuario devuelve "200"
    And el correo del usuario "<usuario>" es "<correo>"
    Examples:
      | usuario  | correo         |
      | userPinApp| PinApp@gmail.com |


  @petstoreEliminarUsuario
  Scenario Outline: Elimina el "<usuario>"
    Given el usuario "<usuario>" existe
    When el usuario elimina al usuario "<usuario>"
    Then el endpoint elimina usuario devuelve "200"
    And el usuario "<usuario>" no se encuentra

    Examples:
      | usuario          |
      | userPinAppEliminar |



