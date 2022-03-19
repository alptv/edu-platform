package edu.platform.security

import edu.platform.config.WebConfig.Origins
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import javax.servlet.http.HttpServletResponse.SC_CONFLICT
import javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED

@Component
class AuthorizationInterceptor(private val allowedOrigins: Origins) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val origin = request.getHeader("origin")
        if (origin == null || !allowedOrigins.isAllowed(origin)) {
            response.status = SC_CONFLICT
            return false
        }

        response.addHeader("Access-Control-Allow-Origin", origin)
        response.addHeader("Access-Control-Allow-Credentials", "true")

        if (handler is HandlerMethod) {
            val method = handler.method
            val private = method.getAnnotation(Public::class.java) == null
            val loggedIn = UserManager.isLoggedIn(request.session)
            if (private && !loggedIn) {
                response.status = SC_UNAUTHORIZED
                return false
            }
        }
        return true
    }

}