package edu.platform.e2e.pages

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Selectors.byClassName
import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import com.codeborne.selenide.SelenideElement
import io.qameta.allure.Step
import org.junit.jupiter.api.AfterEach

open class BasePage {

    fun SelenideElement.type(text: String) = sendKeys(text)

    @Step("Picking tab an menu")
    fun pickTab(name: String) = element(byText(name)).click()

    @Step("Closing popup")
    fun closePopup(text : String) {
        element(byClassName("PopupMessage")).shouldHave(text(text))
        element(byClassName("DeleteButton")).click()
    }
}
