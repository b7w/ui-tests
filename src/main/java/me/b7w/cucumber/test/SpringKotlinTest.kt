package me.b7w.cucumber.test

import io.qameta.allure.junit4.Tag
import me.b7w.cucumber.Properties
import me.b7w.cucumber.page.DuckDuckGoPage
import me.b7w.cucumber.page.ISudoPage
import org.fluentlenium.adapter.junit.FluentTest
import org.fluentlenium.core.annotation.Page
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertTrue


@RunWith(SpringRunner::class)
@SpringBootTest
@Tag("kotlin")
open class SpringKotlinTest : FluentTest() {

    @Autowired
    lateinit var properties: Properties

    @Page
    lateinit var searchPage: DuckDuckGoPage

    @Page
    lateinit var iSudoPage: ISudoPage

    @Before
    fun before() {
        driver.manage().window().maximize()
    }

    @Test
    fun `Title of duckduckgo go should contain search query name`() {
        println("### " + properties.projectName)
        goTo(searchPage)

        searchPage.search("isudo.ru")
        searchPage.clickOn(0)

        iSudoPage.isAt()
        assertTrue { iSudoPage.profileText.text() == "Come to the dark side. We have cookies :-)" }
        assertTrue { iSudoPage.posts.size == 8 }
        iSudoPage.takeScreenshot()

//        val postPage = iSudoPage.openFirstPost()
//        println("#### " + postPage.url())
    }

}