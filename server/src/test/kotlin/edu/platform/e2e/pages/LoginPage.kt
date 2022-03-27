package edu.platform.e2e.pages

import io.qameta.allure.Step

class LoginPage : UserCredentialsPage() {

    @Step("Submitting login")
    fun login() = submit()
}