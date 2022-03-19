package edu.platform.service

import edu.platform.controller.dto.UserCredentials
import edu.platform.model.User

interface UserService {
    fun checkUserCredentials(userCredentials: UserCredentials): Boolean
    fun saveUser(userCredentials: UserCredentials): User
    fun loadUserByLogin(login: String): User
    fun hasUserWithLogin(login: String): Boolean
}