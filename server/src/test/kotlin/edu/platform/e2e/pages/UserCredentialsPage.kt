package edu.platform.e2e.pages

import com.codeborne.selenide.Selectors.byClassName
import com.codeborne.selenide.Selectors.byText
import com.codeborne.selenide.Selenide.element
import io.qameta.allure.Step

open class UserCredentialsPage : BasePage() {

    @Step("Typing login")
    fun typeLogin(login: String) {
        val loginField = findInput("Логин")
        loginField.type(login)
    }

    @Step("Typing password")
    fun typePassword(password: String) {
        val passwordField = findInput("Пароль")
        passwordField.type(password)
    }


    protected fun submit() = element(byClassName("Submit")).click()

    private fun findInput(label: String) =
        element(byText(label))
            .parent()
            .find(byClassName("Input"))
}