package util.controller

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import util.Properties

class ControllerTestContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val map = mapOf(
            "server.port" to Properties.PORT,
            "origins.allowed" to Properties.ALLOWED_ORIGINS
        )
        applicationContext.environment.propertySources.addFirst(
            MapPropertySource("TestConfigProperties", map)
        )
    }
}