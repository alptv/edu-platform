package edu.platform.dao

import edu.platform.model.User
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport

class UserDaoJdbc : JdbcDaoSupport(), UserDao {
    override fun findUserByLogin(login: String): User? {
        val sql = "select user_id as id, user_login as login, user_password_hash as password_hash " +
                "from Users where user_login = ?;"
        return jdbcTemplate!!.query(sql, DataClassRowMapper(User::class.java), login).firstOrNull()
    }

    override fun insertUser(login: String, encodedPassword: String) {
        val sql = "insert into Users(user_login, user_password_hash)  values (?, ?)"
        jdbcTemplate!!.update(sql, login, encodedPassword);
    }
}