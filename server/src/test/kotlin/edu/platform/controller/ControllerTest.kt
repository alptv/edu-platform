package edu.platform.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import edu.platform.model.User
import edu.platform.security.UserManager
import org.springframework.mock.web.MockHttpSession
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.ContentResultMatchersDsl

open class ControllerTest {
    private val objectMapper = jacksonObjectMapper()
    private val defaultOrigin = "http://localhost:3000"


    fun MockMvc.httpGet(url: String, dsl: MockHttpServletRequestDsl.() -> Unit = {}) = get(url) {
        dsl()
        headers {
            origin = defaultOrigin
        }
    }

    fun MockMvc.httpPost(url: String, dsl: MockHttpServletRequestDsl.() -> Unit = {}) = post(url) {
        dsl()
        headers {
            origin = defaultOrigin
        }
    }

    fun MockHttpServletRequestDsl.login() {
        initSessionIfNotPresent()
        UserManager.setUser(session!!, User(1, "login", "password"))
    }


    fun MockHttpServletRequestDsl.login(newSession: MockHttpSession) {
        session = newSession
        UserManager.setUser(session!!, User(1, "login", "password"))
    }

    fun ContentResultMatchersDsl.hasSameJsonAs(obj: Any) {
        json(objectMapper.writeValueAsString(obj))
    }


    fun toJson(obj: Any): String {
        return objectMapper.writeValueAsString(obj)
    }

    private fun MockHttpServletRequestDsl.initSessionIfNotPresent() {
        session = if (session == null) MockHttpSession() else session
    }
}
