package util.database

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import util.Properties

class PostgreSQLContainerContext : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val postgreSQLContainer = PostgreSQLContainer("postgres:14.2")
        .withExposedPorts(5432)
        .withInitScript("schema.sql")

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        postgreSQLContainer.start()
        val map = mapOf(
            "database.url" to postgreSQLContainer.jdbcUrl,
            "database.username" to postgreSQLContainer.username,
            "database.password" to postgreSQLContainer.password,
            "origins.allowed" to Properties.ALLOWED_ORIGINS
        )

        applicationContext.environment.propertySources.addFirst(
            MapPropertySource("TestConfigProperties", map)
        )

        applicationContext.beanFactory.registerSingleton(DataBase::class.java.canonicalName, DataBase::class.java)
    }
}