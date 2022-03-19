package edu.platform.model

data class Question(
    val id: Long,
    val text: String,
    val correct_answer_option_id: Long
)