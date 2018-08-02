package me.b7w.cucumber.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = "me.b7w.cucumber.step"
)
public class CucumberTest {
}
