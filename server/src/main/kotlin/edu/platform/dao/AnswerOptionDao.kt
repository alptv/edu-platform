package edu.platform.dao

import edu.platform.model.AnswerOption
import org.springframework.stereotype.Repository

interface AnswerOptionDao {
    fun findAnswerOptionsForQuestion(questionId: Long): List<AnswerOption>
}