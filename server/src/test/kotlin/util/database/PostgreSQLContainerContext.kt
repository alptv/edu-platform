package util.database

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.testcontainers.containers.PostgreSQLContainer

class PostgreSQLContainerContext : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val postgreSQLContainer = PostgreSQLContainer("postgres:14.2")
        .withExposedPorts(5432)
        .withInitScript("schema.sql")

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        postgreSQLContainer.start()
        val map: MutableMap<String, Any> = HashMap()
        map["database.url"] = postgreSQLContainer.jdbcUrl
        map["database.username"] = postgreSQLContainer.username
        map["database.password"] = postgreSQLContainer.password
        applicationContext.environment.propertySources.addFirst(
            MapPropertySource("TestConfigProperties", map)
        )
        applicationContext.beanFactory.registerSingleton(DataBase::class.java.canonicalName, DataBase::class.java)
    }
}