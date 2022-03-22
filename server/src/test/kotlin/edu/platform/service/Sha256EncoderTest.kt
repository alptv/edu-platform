package edu.platform.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import io.qameta.allure.Epic
import org.junit.jupiter.api.Test

@DisplayName("Sha256 encoder test")
@Epic("Security test")
class Sha256EncoderTest {

    @Test
    fun `encode should return sha256 hash of string`() {
        val actualHash = Sha256Encoder.encode("string to hash")
        val expectedHash = "4904d96e05c2ba8ab5e28bfba3c31c2ca0ea6da94aa4245e79ee47107dbb683e"

        assertThat(actualHash).isEqualTo(expectedHash)
    }
}