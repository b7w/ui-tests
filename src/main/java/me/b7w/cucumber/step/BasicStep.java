package me.b7w.cucumber.step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import me.b7w.cucumber.Properties;
import me.b7w.cucumber.page.MainPage;
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicStep extends FluentCucumberTest {

    private static final String SEARCH_QUERY_NAME = "FluentLenium";

    @Autowired
    Properties properties;

    @Page
    MainPage mainPage;

    @Given(value = "Visit duckduckgo")
    public void step1() {
        mainPage.goTo("https://duckduckgo.com");
    }

    @When(value = "I search FluentLenium")
    public void step2() {
        mainPage.getSearchFormInput().fill().with(SEARCH_QUERY_NAME);
        mainPage.getSearchInput().submit();
    }

    @Then(value = "Title contains FluentLenium")
    public void step3() {
        await().atMost(5, TimeUnit.SECONDS).until(el("#search_form_homepage")).not().present();
        assertThat(window().title()).contains("FluentLenium");
    }

    @Before
    public void before(Scenario scenario) {
        super.before(scenario);
        System.out.println("### " + properties.projectName);
    }

    @After
    public void after(Scenario scenario) {
        super.after(scenario);
    }
}
