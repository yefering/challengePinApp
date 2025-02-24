package com.qaautomation.pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Método genérico para clic por ID o XPath
    public void clickElement(String locator, String type) {
        WebElement element;
        if (type.equalsIgnoreCase("id")) {
            element = wait.until(ExpectedConditions.elementToBeClickable(By.id(locator)));
        } else if (type.equalsIgnoreCase("xpath")) {
            element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
        } else {
            throw new IllegalArgumentException("Tipo de localizador no soportado: " + type);
        }
        element.click();
    }

    public void takeScreenshot(String filePath) {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(filePath);
        try {
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Captura de pantalla tomada y guardada en: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para navegar a una URL específica
    public void navigateTo(String url) {
        driver.get(url);
    }

    public void teardown() {
        // Cerrar el navegador
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getWebDriver() {
        return this.driver;
    }

    protected void inputTxt(String locator, String type, String txt) {
        WebElement element;
        if (type.equalsIgnoreCase("id")) {
            element = wait.until(ExpectedConditions.elementToBeClickable(By.id(locator)));
        } else if (type.equalsIgnoreCase("xpath")) {
            element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
        } else {
            throw new IllegalArgumentException("Tipo de localizador no soportado: " + type);
        }
        element.clear();
        element.sendKeys(txt);
    }

    protected String getText(String locator, String type) {
        WebElement element;
        if (type.equalsIgnoreCase("id")) {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
        } else if (type.equalsIgnoreCase("xpath")) {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        } else {
            throw new IllegalArgumentException("Tipo de localizador no soportado: " + type);
        }
        return element.getText();
    }

    protected WebElement getWebElement(String locator, String type) {
        WebElement element;
        if (type.equalsIgnoreCase("id")) {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
        } else if (type.equalsIgnoreCase("xpath")) {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        } else {
            throw new IllegalArgumentException("Tipo de localizador no soportado: " + type);
        }
        return element;
    }

    public String popUpAlert() {
        try {
            // Esperar dinámicamente hasta que aparezca el popup de alerta
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            System.out.println("El popup no apareció en el tiempo esperado.");
            return null;
        }
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void hoverOverElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }
}
