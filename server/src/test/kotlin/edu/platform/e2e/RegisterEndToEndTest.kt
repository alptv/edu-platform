package edu.platform.e2e

import com.codeborne.selenide.Selenide.open
import edu.platform.e2e.pages.RegisterPage
import io.qameta.allure.Description
import io.qameta.allure.Epic
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Register E2E test")
@Epic("E2E test")
class RegisterEndToEndTest : BaseEndToEndTest() {

    @Test
    @DisplayName("User interaction with registration")
    @Description("User try already existing login, then choose new and successfully finish registration")
    fun `try existing login, then successful registering`() {
        open("/register")
        val registerPage = RegisterPage()

        shouldBeOnUrl("/register")
        registerPage.typeLogin("user")
        registerPage.typePassword("password")
        registerPage.register()
        registerPage.closePopup("Пользователь с таким логином уже существует")


        shouldBeOnUrl("/register")
        registerPage.typeLogin("new_user")
        registerPage.typePassword("password")
        registerPage.register()
        shouldBeOnUrl("/")
    }
}