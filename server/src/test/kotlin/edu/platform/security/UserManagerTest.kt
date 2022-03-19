package edu.platform.security

import edu.platform.model.User
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.mock.web.MockHttpSession
import java.lang.IllegalStateException
import javax.servlet.http.HttpSession

class UserManagerTest {
    private val user = User(1, "login", "password")
    private lateinit var session : HttpSession

    @BeforeEach
    fun setUp() {
        session = MockHttpSession()
    }

    @Test
    fun `isLoggedIn should return true after setUser`() {
        UserManager.setUser(session, user)

        assertThat(UserManager.isLoggedIn(session)).isTrue
    }

    @Test
    fun `isLoggedIn should return false if user was not set`() {
        assertThat(UserManager.isLoggedIn(session)).isFalse
    }

    @Test
    fun `isLoggedIn should return false after unsetUser`() {
        UserManager.setUser(session, user)
        UserManager.unsetUser(session)

        assertThat(UserManager.isLoggedIn(session)).isFalse
    }


    @Test
    fun `getUser should return same user which was used in setUser`() {
        UserManager.setUser(session, user)

        assertThat(UserManager.getUser(session)).isEqualTo(user)
    }

    @Test
    fun `getUser should throw exception if user was not set`() {
        assertThatThrownBy {
            UserManager.getUser(session)
        }.hasMessage("User is not logged in")
            .isExactlyInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `getUser should throw exception after unsetUser`() {
        UserManager.setUser(session, user)
        UserManager.unsetUser(session)

        assertThatThrownBy {
            UserManager.getUser(session)
        }.hasMessage("User is not logged in")
            .isExactlyInstanceOf(IllegalStateException::class.java)

    }
}