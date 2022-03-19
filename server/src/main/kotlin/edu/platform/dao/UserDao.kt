package edu.platform.dao

import edu.platform.model.User

interface UserDao {
    fun findUserByLogin(login: String): User?
    fun insertUser(login: String, encodedPassword: String)
}