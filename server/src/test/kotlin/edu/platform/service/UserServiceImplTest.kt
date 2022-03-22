package edu.platform.service

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import edu.platform.dao.UserDao
import edu.platform.controller.dto.UserCredentials
import edu.platform.model.User
import io.qameta.allure.Description
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import io.qameta.allure.Epic
import org.junit.jupiter.api.Test


@DisplayName("User service test")
@Epic("Service test")
class UserServiceImplTest {
    private val userDao = mock<UserDao>()
    private val userService = UserServiceImpl(userDao)

    @Test
    @DisplayName("Checking correct user credentials")
    @Description("Method checkUserCredentials should return true if user exists in data layer")
    fun `checkUserCredentials should return true if user exists and password is correct`() {
        val passwordHash = Sha256Encoder.encode("password")
        val user = User(1, "login", passwordHash)

        whenever(userDao.findUserByLogin(any())).thenReturn(user)

        val userCredentials = UserCredentials("login", "password")
        assertThat(userService.checkUserCredentials(userCredentials)).isTrue
        verify(userDao).findUserByLogin("login")
    }

    @Test
    @DisplayName("Checking non existing login from user credentials")
    @Description("Method checkUserCredentials should return true if user does not exist in data layer")
    fun `checkUserCredentials should return false if user not exists`() {
        whenever(userDao.findUserByLogin(any())).thenReturn(null)

        val userCredentials = UserCredentials("login", "password")
        assertThat(userService.checkUserCredentials(userCredentials)).isFalse
        verify(userDao).findUserByLogin("login")
    }

    @Test
    @DisplayName("Checking incorrect password from user credentials")
    @Description("Method checkUserCredentials should return false if password does not match")
    fun `checkUserCredentials should return false if password is incorrect`() {
        val passwordHash = Sha256Encoder.encode("password")
        val user = User(1, "login", passwordHash)

        whenever(userDao.findUserByLogin(any())).thenReturn(user)

        val userCredentials = UserCredentials("login", "incorrect_password")
        assertThat(userService.checkUserCredentials(userCredentials)).isFalse
        verify(userDao).findUserByLogin("login")
    }


    @Test
    @DisplayName("Checking incorrect password from user credentials")
    @Description("Method checkUserCredentials should return false if password does not match")
    fun `hasUserWithLogin should return true if user exists`() {
        val passwordHash = Sha256Encoder.encode("password")
        val user = User(1, "login", passwordHash)

        whenever(userDao.findUserByLogin(any())).thenReturn(user)

        assertThat(userService.hasUserWithLogin("login")).isTrue
        verify(userDao).findUserByLogin("login")
    }

    @Test
    @DisplayName("Successful finding user by login")
    @Description("Method loadUserByLogin should return user if data layer contains user with given login")
    fun `loadUserByLogin should return user with given login if user exists`() {
        val user = User(1, "login", "password")

        whenever(userDao.findUserByLogin("login")).thenReturn(user)

        assertThat(userService.loadUserByLogin("login")).isEqualTo(user)
        verify(userDao).findUserByLogin("login")
    }

    @Test
    @DisplayName("Unsuccessful finding user by login")
    @Description("Method loadUserByLogin should throw exception if data layer does not contain user with given login")
    fun `loadUserByLogin should throw exception if user does not exist`() {
        whenever(userDao.findUserByLogin("login")).thenReturn(null)

        assertThatThrownBy { userService.loadUserByLogin("login") }
            .hasMessage("User with login login does not exist")
            .isExactlyInstanceOf(IllegalStateException::class.java)
        verify(userDao).findUserByLogin("login")
    }


    @Test
    @DisplayName("Saving user with given credentials")
    @Description("Method saveUser should insert user with given credentials in data layer")
    fun `saveUser should save user with given login and hashed password`() {
        val passwordHash = Sha256Encoder.encode("password")
        val user = User(1, "login", passwordHash)

        whenever(userDao.findUserByLogin("login")).thenReturn(user)

        val userCredentials = UserCredentials("login", "password")
        assertThat(userService.saveUser(userCredentials)).isEqualTo(user)
        verify(userDao).insertUser("login", passwordHash)
    }
}