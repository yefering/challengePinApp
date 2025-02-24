package com.qaautomation.runners;

import io.cucumber.java.After;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.qaautomation.stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-report.html"},
        tags = "@testRegresion"
)
public class TestRunner {

}
