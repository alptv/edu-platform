package util.database

import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [PostgreSQLContainerContext::class])
@Import(DataBaseTestConfiguration::class)
class DataBaseTest {
    @Autowired
    protected lateinit var database: DataBase

    @AfterEach
    fun clear() {
        database.clear()
    }
}