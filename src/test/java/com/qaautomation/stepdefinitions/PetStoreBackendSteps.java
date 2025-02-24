package com.qaautomation.stepdefinitions;

import com.qaautomation.Services.PetStoreService;
import com.qaautomation.models.User;
import com.qaautomation.utils.TestDataBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;


import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class PetStoreBackendSteps {

    private Response responseCrearUser;
    private Response responseUser;
    private Response responseUpdateUser;
    private Response responseEliminarUsuario;
    private Response responsePet;
    private User user;

    @Given("el servicio se encuentra activo")
    public void elServicioSeEncuentraActivo() {
        Integer statusCode = PetStoreService.statusService();
        assertTrue("[WARNING] El servicio no se encuentra levantado ", statusCode == 200);
    }

    @When("el usuario envia la solicitud de crear un usuario valido")
    public void elUsuarioEnviaLaSolicitudDeCrearUnUsuarioValido() {
        responseCrearUser = PetStoreService.crearUsuario(TestDataBuilder.createDefaultUser());
    }

    @Then("el endpoint crear usuario devuelve {string}")
    public void elEndpointCrearUsuarioDevuelve(String statudCode) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio Crear Usuario", responseCrearUser);
        Assert.assertTrue("[WARNING] No es el codigo esperado " + statudCode, Integer.parseInt(statudCode) == responseCrearUser.getStatusCode());
        System.out.println("Response:\n\t" + responseCrearUser.asString());
    }

    @And("el campo {string} del response tiene el siguiente valor {int}")
    public void elCampoDelResponseTieneElSiguienteValor(String campo, int code) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio Crear Usuario", responseCrearUser);
        int codeBody = responseCrearUser.jsonPath().getInt(campo);
        Assert.assertTrue("[WARNING] No es el codigo esperado " + code, code == codeBody);
    }

    @And("el campo {string} del response tiene el siguiente valor {string}")
    public void elCampoDelResponseTieneElSiguienteValor(String campo, String valor) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio Crear Usuario", responseCrearUser);
        String valorBody = responseCrearUser.jsonPath().getString(campo);
        Assert.assertTrue("[WARNING] No es el valor esperado " + valor, valor.equalsIgnoreCase(valorBody));

    }

    @Given("el usuario {string} existe")
    public void elUsuarioExiste(String usertName) {

        //Busco el usuario
        this.responseUser = PetStoreService.getUsuario(usertName);
        if (responseUser.getStatusCode() == 404) {
            //Si no lo encuentra lo creo
            PetStoreService.crearUsuario(TestDataBuilder.createDefaultUser(usertName));
            this.responseUser = PetStoreService.getUsuario(usertName);
            System.out.println("Se creo usuario : " + responseUser.asPrettyString());
        }

        Long id = responseUser.jsonPath().getLong("id");
        String username = responseUser.jsonPath().getString("username");
        String firstName = responseUser.jsonPath().getString("firstName");
        String lastName = responseUser.jsonPath().getString("lastName");
        String email = responseUser.jsonPath().getString("email");
        String password = responseUser.jsonPath().getString("password");
        String phone = responseUser.jsonPath().getString("phone");
        int userStatus = responseUser.jsonPath().getInt("userStatus");
        this.user = new User(id, username, firstName, lastName, email, password, phone, userStatus);

    }

    @When("el usuario consulta al endpoint user con el nombre de {string}")
    public void elUsuarioConsultaAlEndpointUserConElNombreDe(String usertName) {
        responseUser = PetStoreService.getUsuario(usertName);
    }

    @Then("el endpoint get usuario devuelve {string}")
    public void elEndpointGetUsuarioDevuelve(String code) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio Crear Usuario", responseUser);
        Assert.assertTrue("[WARNING] No es el codigo esperado " + code, Integer.parseInt(code) == responseUser.getStatusCode());
        System.out.println("Response:\n\t" + responseUser.asString());
    }

    @And("El campo {string} del response tiene el siguiente valor {int}")
    public void CampoDelResponseTieneElSiguienteValor(String nomCampo, int Valor) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio Crear Usuario", responseUser);
        int codeBody = responseUser.jsonPath().getInt(nomCampo);
        Assert.assertTrue("[WARNING] No es el codigo esperado " + Valor, Valor == codeBody);
    }

    @And("El campo {string} del response tiene el siguiente valor {string}")
    public void CampoDelResponseTieneElSiguienteValor(String nomCampo, String ValorEsperado) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio Crear Usuario", responseUser);
        String valor = responseUser.jsonPath().getString(nomCampo);
        Assert.assertTrue("[WARNING] No es el codigo esperado " + ValorEsperado, ValorEsperado.equalsIgnoreCase(valor));

    }

    @When("el usuario consulta al endpoint pet con el status {string}")
    public void elUsuarioConsultaAlEndpointPetConElStatus(String status) {
        responsePet = PetStoreService.findPetByStatu(status);
    }

    @Then("el endpoint get pet devuelve {string}")
    public void elEndpointGetPetDevuelve(String statuCode) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio pet", responsePet);
        Assert.assertTrue("[WARNING] No es el codigo esperado " + statuCode, Integer.parseInt(statuCode) == responsePet.getStatusCode());
        System.out.println("Response:\n\t" + responsePet.asPrettyString());
    }

    @And("el usuario busca en la lista el valor {int} del campo {string}")
    public void elUsuarioBuscaEnLaListaElValorDelCampo(int valor, String campo) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio pet", responsePet);

        // Obtener la lista de pets del JSON de la respuesta
        List<Integer> petIds = responsePet.jsonPath().getList("id"); // Lista de IDs de mascotas
        boolean isPetIdPresent = petIds.contains(valor);

        // Validación
        Assert.assertTrue("El petId " + valor + " no está presente en la lista.", isPetIdPresent);

    }

    @And("el usuario valida el {string} de categoria {string} del animal con el id {int}")
    public void elUsuarioValidaElDeCategoriaDelAnimalConElId(String nomCampo, String valorCampo, int idCampo) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio pet", responsePet);

        // Lista completa de mascotas en la respuesta
        var pets = responsePet.jsonPath().getList("$");

        // Buscar el pet con el id 111
        Map<String, Object> foundPet = pets.stream()
                .map(obj -> (Map<String, Object>) obj) // Convertir cada objeto de la lista a un Map
                .filter(pet -> pet.get("id").equals(idCampo)) // Filtrar por id
                .findFirst()
                .orElse(null);

        // Obtener valores específicos del pet
        Map<String, Object> category = (Map<String, Object>) foundPet.get("category");
        String petName = foundPet.get("name").toString();
        String petCategoriName = category.get("name").toString();
        System.out.println("petName " + petName);
        System.out.println("petCategoriName " + petCategoriName);
    }

    @When("el usuario modifica el correo por {string} al usuario {string}")
    public void elUsuarioModificaElCorreoPorAlUsuario(String nuevoEmail, String usuario) {
        this.user.setEmail(nuevoEmail);
        this.responseUpdateUser = PetStoreService.actualizarUsuario(usuario, this.user);
    }

    @Then("el endpoint update usuario devuelve {string}")
    public void elEndpointUpdateUsuarioDevuelve(String code) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio update Usuario", responseUpdateUser);
        Assert.assertTrue("[WARNING] No es el codigo esperado " + code, Integer.parseInt(code) == responseUser.getStatusCode());
        System.out.println("Response:\n\t" + responseUpdateUser.asString());
    }

    @And("el correo del usuario {string} es {string}")
    public void elCorreoDelUsuarioEs(String nomUsuario, String nuevoCorreo) {
        Response respUsuario = PetStoreService.getUsuario(nomUsuario);
        String correo = respUsuario.jsonPath().getString("email");
        Assert.assertEquals("[Warning] no es el correo esperado", nuevoCorreo, correo);
    }

    @When("el usuario elimina al usuario {string}")
    public void elUsuarioEliminaAlUsuario(String nomUsuario) {
        responseEliminarUsuario = PetStoreService.eliminarUsuario(nomUsuario);
    }

    @Then("el endpoint elimina usuario devuelve {string}")
    public void elEndpointEliminaUsuarioDevuelve(String code) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio update Usuario", responseEliminarUsuario);
        Assert.assertTrue("[WARNING] No es el codigo esperado " + code, Integer.parseInt(code) == responseEliminarUsuario.getStatusCode());
    }

    @And("el usuario {string} no se encuentra")
    public void elUsuarioNoSeEncuentra(String nomUsuario) {
        Response rest = PetStoreService.getUsuario(nomUsuario);
        Assert.assertTrue("[WARNING] Se encontro un usuario, cuando no debia de ser", 404 == rest.getStatusCode());
    }
}
