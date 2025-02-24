package com.qaautomation.pages.BlazePage;

import com.qaautomation.config.Properties;
import com.qaautomation.pages.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BlazeHomePage extends BasePage {


    private String byIdLogin = "login2";
    private String byIdUsername = "loginusername";
    private String byIdPassword = "loginpassword";
    private String byIdNameofuser = "nameofuser";
    private String byXpathLogin = "//button[@type=\"button\" and text()='Log in']";


    public BlazeHomePage(WebDriver driver) {
        super(driver);
    }

    public static BlazeHomePage getBlazeHome() {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", Properties.pathChromeDriver);
        options.addArguments("start-maximized");
        options.addArguments("incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.demoblaze.com/");
        return new BlazeHomePage(driver);
    }

    public void clickBtn(String nameBtn) {
        switch (nameBtn) {
            case "LogIn":
                this.clickElement(byIdLogin, "id");
                break;
            case "Log In":
                this.clickElement(byXpathLogin, "xpath");
                break;
            default:
                System.out.println("[WARNING] No se encontro boton");
        }
    }

    public void ingresaDatoCampo(String nomImput, String inputTxt) {
        switch (nomImput) {
            case "Username":
                this.inputTxt(byIdUsername, "id", inputTxt);
                break;
            case "Password":
                this.inputTxt(byIdPassword, "id", inputTxt);
                break;
            default:
                System.out.println("[WARNING] No se encontro boton");
        }
    }

    public String getUsuarioLogeado() {
        return this.getText(byIdNameofuser, "id");
    }

    public boolean buscarItems(String title) {
        return !(this.getText("//a[text()='" + title + "']", "xpath").isEmpty());
    }

    public void focoElement(String title) {
        WebElement webElement = this.getWebElement("//a[text()='" + title + "']", "xpath");
        // Desplazar la vista hasta el elemento
        scrollToElement(this.driver, webElement);
        // O mover el mouse sobre el elemento
        hoverOverElement(this.driver, webElement);
    }

    public void ingresarCategoria(String categoria) {
        String categoriaXpath = "//a[@onclick=\"byCat('" + categoria + "')\"]";
        this.clickElement(categoriaXpath, "xpath");
        System.out.println("");
    }
}
