package edu.platform.integration

import edu.platform.controller.dto.UserCredentials
import io.qameta.allure.Description
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import io.qameta.allure.Epic
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.*
import util.integration.IntegrationTest

@DisplayName("Login integration test")
@Epic("Integration test")
class LoginIntegrationTest : IntegrationTest() {

    @Test
    @DisplayName("User successful integration with login interface")
    @Description("User can login in, then use content which requires authorization and then logout")
    fun `user can login if, use private content and logout`() {
        val userInsert = "insert into Users (user_login , user_password_hash) values (?, ?)"
        val passwordHash = passwordHash("password")
        database.executeUpdate(userInsert, "login", passwordHash)

        val loginResponse = post("/auth/login", UserCredentials("login", "password"))
        assertThat(loginResponse.statusCode).isEqualTo(OK)

        val cookieHeaders = HttpHeaders()
        cookieHeaders["Cookie"] = loginResponse.headers.getValue("Set-Cookie")

        val courseResponse = get("/course/all", cookieHeaders)
        assertThat(courseResponse.statusCode).isEqualTo(OK)

        val lessonResponse = get("/lesson/forCourse/1", cookieHeaders)
        assertThat(lessonResponse.statusCode).isEqualTo(OK)

        val logoutResponse = get("auth/logout", cookieHeaders)
        assertThat(logoutResponse.statusCode).isEqualTo(OK)

        val courseUnauthorizedResponse = get("/course/all", cookieHeaders)
        assertThat(courseUnauthorizedResponse.statusCode).isEqualTo(UNAUTHORIZED)

    }


    @Test
    @DisplayName("User unsuccessful integration with login interface")
    @Description("User can not login with incorrect credentials and then can not use content which requires authorization")
    fun `user cannot login if user credentials are wrong and cannot use private content`() {

        val loginResponse = post("/auth/login", UserCredentials("login", "password"))
        assertThat(loginResponse.statusCode).isEqualTo(BAD_REQUEST)

        val courseResponse = get("/course/all")
        assertThat(courseResponse.statusCode).isEqualTo(UNAUTHORIZED)

        val lessonResponse = get("/lesson/forCourse/1")
        assertThat(lessonResponse.statusCode).isEqualTo(UNAUTHORIZED)

        val logoutResponse = get("auth/logout")
        assertThat(logoutResponse.statusCode).isEqualTo(UNAUTHORIZED)


    }
}