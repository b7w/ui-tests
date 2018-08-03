package me.b7w.cucumber.page

import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.annotation.PageUrl
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

@PageUrl("http://isudo.ru")
class ISudoPage : FluentPage() {

    @FindBy(css = "#sidebar-profile .info p")
    lateinit var profileText: FluentWebElement

    @FindBy(css = ".post")
    lateinit var posts: MutableList<FluentWebElement>

    fun openFirstPost(): PostPage {
        posts.first().el("a.title").click()
        return newInstance(PostPage::class.java)
    }

}
