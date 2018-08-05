package me.b7w.cucumber.test;

import io.qameta.allure.junit4.Tag;
import me.b7w.cucumber.page.MainPage;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("java")
public class SimpleTest extends FluentTest {

    private static final String SEARCH_QUERY_NAME = "FluentLenium";

    @Page
    MainPage mainPage;

    @Test
    public void title_of_duck_duck_go_should_contain_search_query_name() {
        mainPage.goTo("https://duckduckgo.com");
        mainPage.window().maximize();
        mainPage.getSearchFormInput().fill().with(SEARCH_QUERY_NAME);
        mainPage.getSearchInput().submit();
        await().atMost(5, TimeUnit.SECONDS).until(el("#search_form_homepage")).not().present();
        assertThat(window().title()).contains(SEARCH_QUERY_NAME);
    }
}