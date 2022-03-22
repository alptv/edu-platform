package edu.platform.controller

import edu.platform.controller.dto.UserCredentials
import edu.platform.model.User
import edu.platform.security.UserManager
import edu.platform.service.UserService
import io.qameta.allure.Description
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import io.qameta.allure.Epic
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.MockMvc
import util.controller.ControllerTest

@WebMvcTest(AuthController::class)
@DisplayName("Authentication controller test")
@Epic("Controller test")
class AuthControllerTest : ControllerTest() {
    private val userCredentials = UserCredentials("login", "password")
    private val user = User(1, "login", "password_hash")

    @MockBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("Successful login")
    @Description("POST on /auth/login with correct user credentials should return OK status code")
    fun `login should return OK if user with this credentials exists and not already logged in`() {
        whenever(userService.checkUserCredentials(any())).thenReturn(true)
        whenever(userService.loadUserByLogin(any())).thenReturn(user)

        val mockSession = MockHttpSession()
        mockMvc.httpPost("/auth/login") {
            session = mockSession
            content = toJson(userCredentials)
        }.andExpect {
            status { isOk() }
        }

        assertThat(UserManager.isLoggedIn(mockSession)).isTrue
        assertThat(UserManager.getUser(mockSession)).isEqualTo(user)
        verify(userService).checkUserCredentials(userCredentials)
        verify(userService).loadUserByLogin("login")
    }

    @Test
    @DisplayName("Login with incorrect credentials")
    @Description("POST on /auth/login with incorrect user credentials should return BAD_REQUEST status code")
    fun `login should return BAD_REQUEST if credentials are incorrect`() {
        whenever(userService.checkUserCredentials(any())).thenReturn(false)

        val mockSession = MockHttpSession()
        mockMvc.httpPost("/auth/login") {
            session = mockSession
            content = toJson(userCredentials)
        }.andExpect {
            status { isBadRequest() }
        }

        assertThat(UserManager.isLoggedIn(mockSession)).isFalse
        verify(userService).checkUserCredentials(userCredentials)
    }

    @Test
    @DisplayName("Login if already logged in")
    @Description("POST on /auth/login with user credentials should return BAD_REQUEST status code if user logged in")
    fun `login should return BAD_REQUEST if user is already logged in`() {
        whenever(userService.checkUserCredentials(any())).thenReturn(false)

        mockMvc.httpPost("/auth/login") {
            login()
            content = toJson(userCredentials)
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    @DisplayName("Successful registration")
    @Description("POST on /auth/register with user credentials should return OK status code if user with given login not exists")
    fun `register should return OK if user with this credentials not exists`() {
        whenever(userService.hasUserWithLogin(any())).thenReturn(false)
        whenever(userService.saveUser(any())).thenReturn(user)

        val mockSession = MockHttpSession()
        mockMvc.httpPost("/auth/register") {
            session = mockSession
            content = toJson(userCredentials)
        }.andExpect {
            status { isOk() }
        }

        assertThat(UserManager.isLoggedIn(mockSession)).isTrue
        assertThat(UserManager.getUser(mockSession)).isEqualTo(user)
        verify(userService).hasUserWithLogin("login")
        verify(userService).saveUser(userCredentials)
    }

    @Test
    @DisplayName("Registration with an existing login")
    @Description("POST on /auth/register with user credentials should return BAD_REQUEST status code if user with given login exists")
    fun `register should return BAD_REQUEST if user with this credentials already exists`() {
        whenever(userService.hasUserWithLogin(any())).thenReturn(true)

        val mockSession = MockHttpSession()
        mockMvc.httpPost("/auth/register") {
            session = mockSession
            content = toJson(userCredentials)
        }.andExpect {
            status { isBadRequest() }
        }
        assertThat(UserManager.isLoggedIn(mockSession)).isFalse
        verify(userService).hasUserWithLogin("login")
    }

    @Test
    @DisplayName("Successful logout")
    @Description("GET on /auth/logout should return OK status code if user is authorized")
    fun `logout should return OK if user was logged in`() {
        val mockSession = MockHttpSession()
        mockMvc.httpGet("/auth/logout") {
            login(mockSession)
        }.andExpect {
            status { isOk() }
        }

        assertThat(UserManager.isLoggedIn(mockSession)).isFalse
    }


    @Test
    @DisplayName("Logout if user is unauthorized")
    @Description("GET on /auth/logout should return UNAUTHORIZED status code if user is unauthorized")
    fun `logout should return UNAUTHORIZED if user was not logged in`() {
        mockMvc.httpGet("/auth/logout").andExpect {
            status { isUnauthorized() }
        }
    }
}