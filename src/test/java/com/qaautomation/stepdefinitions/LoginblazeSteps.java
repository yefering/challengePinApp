package com.qaautomation.stepdefinitions;

import com.qaautomation.Services.DemoblazeService;
import com.qaautomation.models.Response.EntriesResponse.Item;
import com.qaautomation.pages.BlazePage.BlazeHomePage;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class LoginblazeSteps {
    private BlazeHomePage blazeHomePage;
    private WebDriver driver;
    private Response ResponseEntries;
    private Response ResponseByCat;

    @Given("el usuario se encuentra en al home")
    public void elUsuarioSeEncuentraEnAlHome() {
        blazeHomePage = BlazeHomePage.getBlazeHome();

    }

    //@After("@logingBlazeOk or @ListaDeItems or logingBlazeFail")
    @After("@front")
    public void tearDown() {
        blazeHomePage.teardown();
    }

    @When("el usuario hace clic en el boton {string}")
    public void elUsuarioHaceClicEnElBoton(String nameBtn) {
        blazeHomePage.clickBtn(nameBtn);
    }

    @And("el usuario ingresa {string} en el campo {string}")
    public void elUsuarioIngresaEnElCampo(String inputTxt, String nomImput) {
        blazeHomePage.ingresaDatoCampo(nomImput, inputTxt);
    }

    @Then("el usuario visualiza {string}")
    public void elUsuarioVisualiza(String txtBuscar) {
        String userLogin = blazeHomePage.getUsuarioLogeado();
        Assert.assertTrue("[WARNING] El usuario no logeo", userLogin.contains(txtBuscar));
    }

    @Then("el usuario visualiza el Popup con el mensaje {string}")
    public void elUsuarioVisualizaElPopupConElMensaje(String mensaje) {
        String msj = blazeHomePage.popUpAlert();
        Assert.assertTrue("[WARNING] No es el mensaje esperado ", mensaje.equalsIgnoreCase(msj));

    }

    @When("se llama al endpoint entries del servicio demoblaze")
    public void seLlamaAlEndpointEntriesDelServicioDemoblaze() {
        this.ResponseEntries = DemoblazeService.entries();
        Assert.assertTrue("[WARNING] El endpoint entries del servicio demoblaze No esta levantado", this.ResponseEntries.getStatusCode() == 200);
    }

    @Then("los productos devueltos por el API se visualizan en la página de inicio")
    public void losProductosDevueltosPorElAPISeVisualizanEnLaPáginaDeInicio() {
        Assert.assertNotNull("[WARNING] No se llamo al servicio", this.ResponseEntries);
        List<Item> listItems = DemoblazeService.getListItems(this.ResponseEntries);
        for (Item item : listItems) {
            boolean flag = blazeHomePage.buscarItems(item.getTitle());
            Assert.assertTrue("[WARNING] No se encontro el producto " + item.getTitle(), flag);
            blazeHomePage.focoElement(item.getTitle());
        }
    }

    @When("se llama al endpoint bycat del servicio demoblaze con la cateogira {string}")
    public void seLlamaAlEndpointBycatDelServicioDemoblazeConLaCateogira(String categoria) {
        this.ResponseByCat = DemoblazeService.bycat(categoria);
        Assert.assertTrue("[WARNING] El endpoint bycat del servicio demoblaze no encontro resultado", this.ResponseByCat.getStatusCode() == 200);
    }

    @And("el usuario se encuentra en la pagina de la categoria {string}")
    public void elUsuarioSeEncuentraEnLaPaginaDeLaCategoria(String categoria) {
        this.blazeHomePage.ingresarCategoria(categoria);
    }


    @Then("los productos devueltos por el demoblaze bycat se visualizan en la categoria {string}")
    public void losProductosDevueltosPorElDemoblazeBycatSeVisualizanEnLaCategoria(String categoria) {
        Assert.assertNotNull("[WARNING] No se llamo al servicio", this.ResponseByCat);
        List<Item> listItems = DemoblazeService.getListItems(this.ResponseByCat);
        for (Item item : listItems) {
            boolean flag = blazeHomePage.buscarItems(item.getTitle());
            Assert.assertTrue("[WARNING] No se encontro el producto " + item.getTitle(), flag);
            blazeHomePage.focoElement(item.getTitle());
        }
    }
}
