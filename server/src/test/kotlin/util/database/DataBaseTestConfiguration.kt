package util.database

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class DataBaseTestConfiguration {
    @Value("\${database.url}")
    private lateinit var url: String

    @Value("\${database.username}")
    private lateinit var username: String

    @Value("\${database.password}")
    private lateinit var password: String

    @Bean
    fun database(): DataBase = DataBase(url, username, password)
}