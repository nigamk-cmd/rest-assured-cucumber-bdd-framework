package com.kaustubh.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Entry point that ties Cucumber to TestNG.
 *
 * Running this class (via `mvn test` and testng.xml) discovers all
 * .feature files under the glue path, matches each Gherkin step to a
 * method in UserApiSteps, and reports results through TestNG — so this
 * BDD suite fits into the same CI/Maven/TestNG pipeline as any other
 * TestNG-based suite.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.kaustubh.stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true
)
public class TestNGCucumberRunner extends AbstractTestNGCucumberTests {
}
