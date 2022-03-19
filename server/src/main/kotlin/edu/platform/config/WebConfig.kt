package edu.platform.config

import edu.platform.security.AuthorizationInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    val allowedOrigins = Origins(
        "http://localhost:3000"
    )

    class Origins(vararg origins: String) {
        private val origins = setOf(*origins)

        fun isAllowed(origin: String) = origins.contains(origin)

    }

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