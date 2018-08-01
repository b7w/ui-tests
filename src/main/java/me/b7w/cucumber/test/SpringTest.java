package me.b7w.cucumber.test;

import me.b7w.cucumber.Properties;
import me.b7w.cucumber.page.MainPage;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTest extends FluentTest {

    private static final String SEARCH_QUERY_NAME = "FluentLenium";

    @Autowired
    Properties properties;

    @Page
    MainPage mainPage;

    @Test
    public void title_of_duck_duck_go_should_contain_search_query_name() {
        System.out.println("### " + properties.projectName);

        mainPage.goTo("https://duckduckgo.com");
        mainPage.window().maximize();
        mainPage.getSearchFormInput().fill().with(SEARCH_QUERY_NAME);
        mainPage.getSearchInput().submit();
        await().atMost(5, TimeUnit.SECONDS).until(el("#search_form_homepage")).not().present();
        assertThat(window().title()).contains(SEARCH_QUERY_NAME);
    }
}