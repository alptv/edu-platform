package edu.platform.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import util.database.DataBaseTest

class UserDaoJdbcTest : DataBaseTest() {

    @Autowired
    private lateinit var userDao: UserDao

    @Test
    fun `findUserByLogin should return if user with given login exists`() {
        database.executeUpdate(
            "insert into Users (user_login, user_password_hash) values (?, ?)",
            "user", "hash"
        )
        val user = userDao.findUserByLogin("user")
        assertThat(user).isNotNull
    }

    @Test
    fun `findUserByLogin should return null if user with given login does not exist`() {
        val user = userDao.findUserByLogin("user")
        assertThat(user).isNull()
    }

    @Test
    fun `insertUser should insert in database`() {
        userDao.insertUser("login", "password_hash")
        val count = database.executeScalar<Int>("select count(*) from Users where user_login = ?", "login")
        assertThat(count).isEqualTo(1)
    }
}