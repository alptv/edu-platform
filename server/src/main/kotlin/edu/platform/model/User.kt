package edu.platform.model

data class User(
    val id: Long,
    val login: String,
    val passwordHash: String
)