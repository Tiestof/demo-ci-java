package com.demo;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Runner clásico de Cucumber + JUnit4.
 * Ejecuta los features en src/test/resources/com/demo/features
 * y usa los steps en com.demo.steps.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/com/demo/features",
    glue = "com.demo.steps",
    plugin = {"pretty", "html:target/cucumber-report.html"},
    monochrome = true
)
public class RunCucumberTest { }
