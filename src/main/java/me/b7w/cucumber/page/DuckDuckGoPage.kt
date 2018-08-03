package me.b7w.cucumber.page

import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.annotation.PageUrl
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.By
import org.openqa.selenium.support.FindBy

import java.util.concurrent.TimeUnit

@PageUrl("https://duckduckgo.com")
class DuckDuckGoPage : FluentPage() {

    @FindBy(id = "search_form_input_homepage")
    lateinit var searchFormInput: FluentWebElement

    @FindBy(id = "search_button_homepage")
    lateinit var searchInput: FluentWebElement

    fun search(query: String) {
        searchFormInput.fill().with(query)
        searchFormInput.submit()
        await().atMost(5, TimeUnit.SECONDS).until(el("#search_form_homepage")).not().present()
    }

    fun clickOn(id: Int) {
        el(By.id("r1-$id")).click()
    }
}
