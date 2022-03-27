package edu.platform.e2e.pages

import io.qameta.allure.Step

class RegisterPage : UserCredentialsPage() {

    @Step("Submitting registration")
    fun register() = submit()
}