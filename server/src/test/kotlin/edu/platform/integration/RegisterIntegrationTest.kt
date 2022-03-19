package edu.platform.integration

import edu.platform.controller.dto.UserCredentials
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

class RegisterIntegrationTest : IntegrationTest() {

    @Test
    fun `user can register, use private content and logout`() {
        val registerResponse = post("/auth/register", UserCredentials("login", "password"))
        assertThat(registerResponse.statusCode).isEqualTo(HttpStatus.OK)

        val userCount = "select count(*) from Users where user_login = ?"
        assertThat(database.executeScalar<Int>(userCount, "login")).isEqualTo(1)

        val cookieHeaders = HttpHeaders()
        cookieHeaders["Cookie"] = registerResponse.headers.getValue("Set-Cookie")

        val courseResponse = get("/course/all", cookieHeaders)
        assertThat(courseResponse.statusCode).isEqualTo(HttpStatus.OK)

        val lessonResponse = get("/lesson/forCourse/1", cookieHeaders)
        assertThat(lessonResponse.statusCode).isEqualTo(HttpStatus.OK)

        val logoutResponse = get("auth/logout", cookieHeaders)
        assertThat(logoutResponse.statusCode).isEqualTo(HttpStatus.OK)

        val courseUnauthorizedResponse = get("/course/all", cookieHeaders)
        assertThat(courseUnauthorizedResponse.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)

    }


    @Test
    fun `user cannot register if user with given login already exists`() {
        val userInsert = "insert into Users (user_login , user_password_hash) values (?, ?)"
        database.executeUpdate(userInsert, "login", "password")


        val loginResponse = post("/auth/login", UserCredentials("login", "password"))
        assertThat(loginResponse.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)

        val courseResponse = get("/course/all")
        assertThat(courseResponse.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)

        val lessonResponse = get("/lesson/forCourse/1")
        assertThat(lessonResponse.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)

        val logoutResponse = get("auth/logout")
        assertThat(logoutResponse.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)


    }
}