package edu.platform.config

import edu.platform.security.AuthorizationInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebMvc
class WebConfig(
    @Value("\${origins.allowed}") allowedOrigins: List<String>
) : WebMvcConfigurer {
    class Origins(origins: List<String>) {
        private val origins = HashSet(origins)
        fun isAllowed(origin: String) = origins.contains(origin) || origin.contains("$origin/")
    }
    val allowedOrigins = Origins(allowedOrigins)

    @Bean
    fun allowedOrigins() = allowedOrigins

    @Bean
    fun adapter(allowedOrigins: Origins): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addInterceptors(registry: InterceptorRegistry) {
                registry.addInterceptor(AuthorizationInterceptor(allowedOrigins))
            }
        }
    }
}