package edu.platform.e2e.pages

import com.codeborne.selenide.Selectors.byClassName
import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import io.qameta.allure.Step

class LessonPage : BasePage() {

    @Step("Picking lesson")
    fun pickLesson(name: String) = element(byText(name)).click()

    @Step("Picking answer")
    fun pickAnswer(questionNumber: Int, answerNumber: Int) {
        elements(byClassName("Task"))[questionNumber]
            .find(byClassName("Answer"), answerNumber).find("input[type=radio]").click()
    }

    @Step("Submitting test")
    fun submit() = element(byClassName("Submit")).click()
}