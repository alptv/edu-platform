package edu.platform.e2e

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selectors
import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.*
import com.codeborne.selenide.WebDriverConditions.url
import org.junit.jupiter.api.AfterEach

open class BaseEndToEndTest {

    fun shouldBeOnUrl(path: String) {
        val expectedUrl = Configuration.baseUrl + path
        webdriver().shouldHave(url(expectedUrl))
    }

    @AfterEach
    fun logout() {
        val logoutTab = elements(byText("Выход"))
        if (logoutTab.size == 1) {
            logoutTab[0].click()
        }
    }

}