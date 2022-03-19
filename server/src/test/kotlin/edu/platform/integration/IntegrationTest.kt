package edu.platform.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import edu.platform.service.Sha256Encoder
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import util.database.DataBaseTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTest : DataBaseTest() {
    private val defaultOrigin = "http://localhost:3000"
    private val objectMapper = jacksonObjectMapper()
    private val restTemplate = RestTemplateBuilder().build().apply {
        errorHandler = object : ResponseErrorHandler {
            override fun hasError(response: ClientHttpResponse): Boolean {
                return false
            }

            override fun handleError(response: ClientHttpResponse) {

            }
        }
    }

    @LocalServerPort
    private var port: Int = 0


    @BeforeAll
    fun allowRestrictedHeaders() {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true")
    }

    fun url(path: String): String {
        val slashed = path.startsWith("/")
        val slashedPath = (if (slashed) "" else "/") + path
        return "http://localhost:$port$slashedPath"
    }

    fun post(path: String, request: Any? = null, headers: HttpHeaders = HttpHeaders()): ResponseEntity<String> {
        headers.origin = defaultOrigin
        val requestJson =
            if (request == null) null
            else objectMapper.writeValueAsString(request)
        val httpEntity = HttpEntity(requestJson, headers)
        return restTemplate.exchange(url(path), POST, httpEntity, String::class.java)
    }


    fun get(path: String, headers: HttpHeaders = HttpHeaders()): ResponseEntity<String> {
        headers.origin = defaultOrigin
        return restTemplate.exchange(url(path), GET, HttpEntity<String>(headers), String::class.java)
    }


    fun passwordHash(password: String) =
        Sha256Encoder.encode(password)

}