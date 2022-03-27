package edu.platform.e2e.pages

import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide.element
import io.qameta.allure.Step

class CoursesPage : BasePage() {
    @Step("Picking course")
    fun pickCourse(name : String) = element(byText(name)).click()
}