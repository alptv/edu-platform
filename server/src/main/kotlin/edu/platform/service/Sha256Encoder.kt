package edu.platform.service

import java.math.BigInteger
import java.security.MessageDigest


object Sha256Encoder {
    fun encode(string: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(string.toByteArray())
        return BigInteger(1, hashBytes).toString(16)
    }
}