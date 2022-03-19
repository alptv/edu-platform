package edu.platform.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import edu.platform.controller.dto.UserCredentials
import edu.platform.security.Public
import edu.platform.security.UserManager
import edu.platform.service.UserService
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession


@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService
) {
    private val objectMapper = jacksonObjectMapper()

    @Public
    @PostMapping("/login")
    fun login(@RequestBody userCredentialsJson: String, httpSession: HttpSession): ResponseEntity<String> {
        val userCredentials = objectMapper.readValue<UserCredentials>(userCredentialsJson)

        return if (!UserManager.isLoggedIn(httpSession) && userService.checkUserCredentials(userCredentials)) {
            val user = userService.loadUserByLogin(userCredentials.login)
            UserManager.setUser(httpSession, user)
            ResponseEntity(OK)
        } else {
            ResponseEntity(BAD_REQUEST)
        }
    }


    @Public
    @PostMapping("/register")
    fun register(@RequestBody userCredentialsJson: String, httpSession: HttpSession): ResponseEntity<String> {
        val userCredentials = objectMapper.readValue<UserCredentials>(userCredentialsJson)

        return if (!userService.hasUserWithLogin(userCredentials.login)) {
            val user = userService.saveUser(userCredentials)
            UserManager.setUser(httpSession, user)
            ResponseEntity<String>(OK)
        } else {
            ResponseEntity<String>(BAD_REQUEST)
        }
    }


    @GetMapping("/logout")
    fun logout(httpSession: HttpSession): ResponseEntity<String> {
        return if (UserManager.isLoggedIn(httpSession)) {
            UserManager.unsetUser(httpSession)
            ResponseEntity(OK)
        } else {
            ResponseEntity(UNAUTHORIZED)
        }
    }
}