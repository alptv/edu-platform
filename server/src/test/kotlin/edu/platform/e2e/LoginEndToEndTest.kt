package edu.platform.e2e

import com.codeborne.selenide.Selenide.open
import edu.platform.e2e.pages.LoginPage
import io.qameta.allure.Description
import io.qameta.allure.Epic
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Login E2E test")
@Epic("E2E test")
class LoginEndToEndTest : BaseEndToEndTest() {

    @Test
    @DisplayName("User interaction with authorization")
    @Description("User try incorrect login, then at next try incorrect password and then use correct login and password")
    fun `try incorrect login, then incorrect password and then successful login with redirect to main`() {
        open("/login")
        val loginPage = LoginPage()

        shouldBeOnUrl("/login")
        loginPage.typeLogin("usr")
        loginPage.typePassword("password")
        loginPage.login()
        loginPage.closePopup("Неверный логин или пароль")


        shouldBeOnUrl("/login")
        loginPage.typeLogin("user")
        loginPage.typePassword("psswrd")
        loginPage.login()
        loginPage.closePopup("Неверный логин или пароль")

        shouldBeOnUrl("/login")
        loginPage.typeLogin("user")
        loginPage.typePassword("password")
        loginPage.login()
        shouldBeOnUrl("/")
    }
}