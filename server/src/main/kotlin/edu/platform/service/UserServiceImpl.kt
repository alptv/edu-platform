package edu.platform.service

import edu.platform.dao.UserDao
import edu.platform.controller.dto.UserCredentials
import edu.platform.model.User
import java.lang.IllegalStateException

class UserServiceImpl(
    private val userDao: UserDao
) : UserService {

    override fun checkUserCredentials(userCredentials: UserCredentials): Boolean {
        val user = userDao.findUserByLogin(userCredentials.login)
        val encodedPassword = Sha256Encoder.encode(userCredentials.password)
        return user != null && user.passwordHash == encodedPassword
    }

    override fun hasUserWithLogin(login: String): Boolean {
        return userDao.findUserByLogin(login) != null
    }

    override fun saveUser(userCredentials: UserCredentials): User {
        val encodedPassword = Sha256Encoder.encode(userCredentials.password)
        userDao.insertUser(userCredentials.login, encodedPassword)
        return loadUserByLogin(userCredentials.login)
    }

    override fun loadUserByLogin(login: String): User {
        return userDao.findUserByLogin(login) ?: throw IllegalStateException("User with login $login does not exist")
    }
}