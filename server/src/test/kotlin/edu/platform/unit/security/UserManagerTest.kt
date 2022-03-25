package edu.platform.unit.security

import edu.platform.model.User
import edu.platform.security.UserManager
import io.qameta.allure.Description
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import io.qameta.allure.Epic
import org.springframework.mock.web.MockHttpSession
import java.lang.IllegalStateException
import javax.servlet.http.HttpSession

@DisplayName("User authentication manager test")
@Epic("Security test")
class UserManagerTest {
    private val user = User(1, "login", "password")
    private lateinit var session : HttpSession

    @BeforeEach
    fun setUp() {
        session = MockHttpSession()
    }

    @Test
    @DisplayName("Successful logged user check")
    @Description("Method isLoggedIn should return true if user was in session")
    fun `isLoggedIn should return true after setUser`() {
        UserManager.setUser(session, user)

        assertThat(UserManager.isLoggedIn(session)).isTrue
    }

    @Test
    @DisplayName("Unsuccessful logged user check")
    @Description("Method isLoggedIn should return false if user was not in session")
    fun `isLoggedIn should return false if user was not set`() {
        assertThat(UserManager.isLoggedIn(session)).isFalse
    }

    @Test
    @DisplayName("Successful logged user check after unsetting")
    @Description("Method isLoggedIn should return false after removing from  user session")
    fun `isLoggedIn should return false after unsetUser`() {
        UserManager.setUser(session, user)
        UserManager.unsetUser(session)

        assertThat(UserManager.isLoggedIn(session)).isFalse
    }


    @Test
    @DisplayName("Successful extracting user from session")
    @Description("Method getUser should return user which was in session")
    fun `getUser should return same user which was used in setUser`() {
        UserManager.setUser(session, user)

        assertThat(UserManager.getUser(session)).isEqualTo(user)
    }

    @Test
    @DisplayName("Unsuccessful extracting user from session")
    @Description("Method getUser should throw exception if session does not have user")
    fun `getUser should throw exception if user was not set`() {
        assertThatThrownBy {
            UserManager.getUser(session)
        }.hasMessage("User is not logged in")
            .isExactlyInstanceOf(IllegalStateException::class.java)
    }

    @Test
    @DisplayName("Unsuccessful extracting user from session after unsetting")
    @Description("Method getUser should throw exception if user from session was removed")
    fun `getUser should throw exception after unsetUser`() {
        UserManager.setUser(session, user)
        UserManager.unsetUser(session)

        assertThatThrownBy {
            UserManager.getUser(session)
        }.hasMessage("User is not logged in")
            .isExactlyInstanceOf(IllegalStateException::class.java)
    }
}