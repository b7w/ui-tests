package me.b7w.cucumber.page;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends FluentPage {

    @FindBy(id = "search_form_input_homepage")
    private FluentWebElement searchFormInput;

    @FindBy(id = "search_button_homepage")
    private FluentWebElement searchInput;

    public FluentWebElement getSearchFormInput() {
        return searchFormInput;
    }

    public FluentWebElement getSearchInput() {
        return searchInput;
    }
}
