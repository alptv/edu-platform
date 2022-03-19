package edu.platform.security

import edu.platform.model.User
import org.springframework.stereotype.Component
import javax.servlet.http.HttpSession

@Component
object UserManager {
    private const val USER_SESSION_KEY = "user"

    fun setUser(httpSession: HttpSession, user: User) {
        httpSession.setAttribute(USER_SESSION_KEY, user)
    }

    fun unsetUser(httpSession: HttpSession) {
        httpSession.removeAttribute(USER_SESSION_KEY)
    }

    fun getUser(httpSession: HttpSession): User {
        val user = httpSession.getAttribute(USER_SESSION_KEY)
        return user as? User ?: throw IllegalStateException("User is not logged in")
    }

    fun isLoggedIn(httpSession: HttpSession): Boolean {
        return httpSession.getAttribute(USER_SESSION_KEY) != null
    }
}