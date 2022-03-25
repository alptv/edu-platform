package edu.platform.unit.dao

import edu.platform.dao.UserDao
import io.qameta.allure.Description
import io.qameta.allure.Epic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import util.database.DataBaseTest

@DisplayName("User data access test")
@Epic("Data layer test")
class UserDaoJdbcTest : DataBaseTest() {

    @Autowired
    private lateinit var userDao: UserDao

    @Test
    @DisplayName("Finding existing user by login")
    @Description("Method findUserByLogin should extract user with given id from database")
    fun `findUserByLogin should return if user with given login exists`() {
        database.executeUpdate(
            "insert into Users (user_login, user_password_hash) values (?, ?)",
            "user", "hash"
        )
        val user = userDao.findUserByLogin("user")
        assertThat(user).isNotNull
    }

    @Test
    @DisplayName("Finding non existing user by login")
    @Description("Method findUserByLogin should return null if user with given login does not exist")
    fun `findUserByLogin should return null if user with given login does not exist`() {
        val user = userDao.findUserByLogin("user")
        assertThat(user).isNull()
    }

    @Test
    @DisplayName("Inserting user in database")
    @Description("Method insertUser should insert user with given credentials in database")
    fun `insertUser should insert in database`() {
        userDao.insertUser("login", "password_hash")
        val count = database.executeScalar<Int>("select count(*) from Users where user_login = ?", "login")
        assertThat(count).isEqualTo(1)
    }
}