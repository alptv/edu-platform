package edu.platform.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource


@Configuration
class DatabaseJdbcConfiguration : AbstractJdbcConfiguration() {
    @Value("\${database.url}")
    private lateinit var url: String

    @Value("\${database.username}")
    private lateinit var username: String

    @Value("\${database.password}")
    private lateinit var password: String

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.username = username
        dataSource.url = url
        dataSource.password = password
        return dataSource
    }

}