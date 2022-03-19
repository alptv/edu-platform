package edu.platform.service

import edu.platform.model.AnswerOption
import org.springframework.stereotype.Service

interface AnswerOptionService {
    fun loadAllAnswerOptionsForQuestion(questionId : Long) : List<AnswerOption>
}